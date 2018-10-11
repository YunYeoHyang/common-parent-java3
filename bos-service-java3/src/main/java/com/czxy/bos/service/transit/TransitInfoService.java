package com.czxy.bos.service.transit;

import com.czxy.bos.dao.take_delivery.WayBillMapper;
import com.czxy.bos.dao.transit.TransitInfoMapper;
import com.czxy.bos.domain.take_delivery.WayBill;
import com.czxy.bos.domain.transit.TransitInfo;
import com.czxy.bos.es.domain.EsWayBill;
import com.czxy.bos.es.repository.WayBillRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class TransitInfoService {

    @Resource
    private TransitInfoMapper transitInfoMapper;

    @Resource
    private WayBillMapper wayBillMapper;

    @Resource
    private WayBillRepository wayBillRepository;

    public void createTransits(String wayBillIds) {
        if (StringUtils.isNotBlank(wayBillIds)) {
            // 处理运单
            for (String wayBillId :
                    wayBillIds.split(",")) {
                WayBill wayBill = wayBillMapper.selectByPrimaryKey(Integer.parseInt(wayBillIds));
                // 判断运单状态是否为待发货
                if (wayBill.getSignStatus() == 1) {
                    // 待发货
                    // 生成 TransitInfo 信息
                    TransitInfo transitInfo = new TransitInfo();
                    transitInfo.setWayBillId(wayBill.getId());
                    transitInfo.setStatus("出入库中转");

                    transitInfoMapper.insert(transitInfo);

                    // 更新运单状态
                    wayBill.setSignStatus(2);

                    wayBillMapper.updateByPrimaryKey(wayBill);

                    // 更新索引库
                    EsWayBill esWayBill = new EsWayBill();
                    BeanUtils.copyProperties(wayBill, esWayBill);
                    wayBillRepository.save(esWayBill);
                }
            }
        }
    }

    /**
     * 分页
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<TransitInfo> pageQuery(Integer pageNum, Integer pageSize) {
        // 使用分页助手开始分页,指定两个参数：当前页码，每页条数
        PageHelper.startPage(pageNum, pageSize);
        // 然后分页拦截器会自动对接下来的查询进行分页
        List<TransitInfo> transitInfos = this.transitInfoMapper.select(null);// 不传查询条件
        for (TransitInfo transitInfo : transitInfos) {
            WayBill wayBill = wayBillMapper.selectByPrimaryKey(transitInfo.getWayBillId());
            transitInfo.setWayBill(wayBill);
        }
        // 封装分页信息对象
        return new PageInfo<>(transitInfos);
    }
}
