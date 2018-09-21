package com.czxy.bos.service.base;

import com.czxy.bos.dao.base.CourierMapper;
import com.czxy.bos.dao.base.StandardMapper;
import com.czxy.bos.domain.base.Courier;
import com.czxy.bos.domain.base.Standard;
import com.czxy.bos.exception.BosException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CourierService {
    //注入mapper
    @Resource
    private CourierMapper courierMapper;

    @Resource
    private StandardMapper standardMapper;

    /**
     * 保存快递员
     * @param courier
     * @return
     */
    public Integer saveCourier(Courier courier){
        //1.对数据进行校验
        //1.1 非空
        if(StringUtils.isBlank(courier.getCourierNum())){
            throw new BosException("操作的快递员工号不能为空");
        }
        //1.2 快递员工号，唯一校验--查询
        // 创建一个查询条件对象
        Example example = new Example(Courier.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("courierNum" , courier.getCourierNum());

        Courier findCourier = courierMapper.selectOneByExample(example);
        //如果存在，抛异常
        if(findCourier != null){
            throw new BosException("快递员工号已存在");
        }

        //2.保存
        return courierMapper.insert(courier);
    }

    /**
     * 查询所有，含分页
     * @param page 第几页
     * @param rows 每页显示个数
     * @return 含有分页信息的封装对象
     */
    public PageInfo<Courier> findAll(Integer page ,Integer rows){
        //1.设置分页数据
        PageHelper.startPage( page ,rows );
        //2.查询所有
        List<Courier> list = courierMapper.selectAll();
        // 遍历所有的快递员，获得关联 收派标准id值，根据id查询标准，并将标准对象赋值给快递员
        for(Courier courier : list){
            Integer standardId = courier.getStandardId();
            Standard standard = standardMapper.selectByPrimaryKey( standardId );
            courier.setStandard(standard);
        }
        //3.封装
        return new PageInfo<>( list );
    }

    /**
     * 批量作废快递员，如果有一个没有操作成功，都将抛异常
     * @param ids
     * @return 操作成功快递员个数
     */
    public Integer deleteCourier(Integer[] ids){
        for(Integer id : ids){
            //查询
            Courier courier = courierMapper.selectByPrimaryKey(id);
            if(courier == null){
                throw new BosException("操作对象不存在");
            }
            //修改标记
            courier.setDeltag('1');   //1表示作废
            //更新操作
            courierMapper.updateByPrimaryKey(courier);
        }
        return ids.length;
    }

    /**
     * 查询没有关联的快递员，此处处理显示数据
     * @return
     */
    public List<Courier> findNoAssociation() {
        //通过mapper查询即可
        List<Courier> list = courierMapper.findNoAssociation();
        //处理显示数据info (也可以修改javabean的 getInfo() )
        for(Courier courier : list){
            courier.setInfo( courier.getName() + "("+courier.getCompany()+")");
        }
        return list;
    }

    /**
     * 查询定区关联的快递员
     * @param fixedAreaId
     * @return
     */
    public List<Courier> findAssociationCourier(String fixedAreaId){
        return courierMapper.findAssociationCourier(fixedAreaId);
    }

    /**
     * 还原功能
     * @param ids
     * @return
     */
    public int restore(Integer[] ids) {
        for (Integer id : ids) {
            Courier courier = courierMapper.selectByPrimaryKey(id);
            courier.setDeltag(null);
            courierMapper.updateByPrimaryKey(courier);
        }
        return ids.length;
    }

}
