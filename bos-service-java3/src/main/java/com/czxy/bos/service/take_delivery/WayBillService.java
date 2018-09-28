package com.czxy.bos.service.take_delivery;

import com.czxy.bos.dao.take_delivery.WayBillMapper;
import com.czxy.bos.domain.take_delivery.WayBill;
import com.czxy.bos.es.domain.EsWayBill;
import com.czxy.bos.es.repository.WayBillRepository;
import com.czxy.bos.exception.BosException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class WayBillService {

    @Resource
    private WayBillMapper wayBillMapper;

    @Resource
    private WayBillRepository wayBillRepository;

    /**
     * 分页
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<WayBill> pageQuickQuery(Integer page, Integer rows) {
        // 使用分页助手开始分页,指定两个参数：当前页码，每页条数
        PageHelper.startPage(page, rows);
        // 然后分页拦截器会自动对接下来的查询进行分页
        List<WayBill> wayBills = this.wayBillMapper.selectAll();
        // 封装分页信息对象
        return new PageInfo<>(wayBills);
    }

    /**
     * 运单列表显示需求
     * @param wayBill
     * @param page
     * @param rows
     * @return
     */
    public Page<EsWayBill> pageQuery(EsWayBill wayBill , Integer page, Integer rows) {
        //1 条件封装对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //运单号查询
        if(StringUtils.isNotBlank(wayBill.getWayBillNum())){
            boolQueryBuilder.must(QueryBuilders.termQuery("wayBillNum",wayBill.getWayBillNum()));
        }

        //发货地，模糊查询
        if(StringUtils.isNotBlank(wayBill.getSendAddress())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("sendAddress",wayBill.getSendAddress()));
        }

        //收货地
        if(StringUtils.isNotBlank(wayBill.getRecAddress())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("recAddress",wayBill.getSendAddress()));
        }

        //速运类型
        if(StringUtils.isNotBlank(wayBill.getSendProNum())){
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("sendProNum",wayBill.getSendProNum()));
        }

        //签到状态
        if(wayBill.getSignStatus() != null && wayBill.getSignStatus() != 0){
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("signStatus",wayBill.getSignStatus()));
        }


        //2 搜索封装对象
        NativeSearchQuery searchQuery = new NativeSearchQuery(boolQueryBuilder);
        //2.1 设置分页
        searchQuery.setPageable(PageRequest.of(page - 1 ,rows ));

        //3 查询
        return this.wayBillRepository.search(searchQuery);
    }


    /**
     * 添加运单
     * 如果有id更新 ，没有就添加
     * @param wayBill
     */
    public void saveWayBill(WayBill wayBill){

        if (wayBill.getId() != null){
            wayBillMapper.updateByPrimaryKey(wayBill);
        }else {
            WayBill temp = findByWayBillNum(wayBill.getWayBillNum());
            if (temp != null) {
                throw new BosException("运单号【" + wayBill.getWayBillNum() + "】已存在");
            }
            //运单的状态需要初始化
            wayBill.setSignStatus(1);

            wayBillMapper.insert(wayBill);
        }
        //无论添加还是更新，都需要添加到elasticsearch中一份
        EsWayBill esWayBill = new EsWayBill();
        BeanUtils.copyProperties(wayBill, esWayBill);
        wayBillRepository.save(esWayBill);

    }

    /**
     * 根据运单号查询
     * @param wayBillNum
     * @return
     */
    public WayBill findByWayBillNum(String wayBillNum){

        Example example = new Example(WayBill.class);
        example.createCriteria().andEqualTo("wayBillNum" , wayBillNum);
        WayBill wayBill = wayBillMapper.selectOneByExample(example);

        return wayBill;
    }
}
