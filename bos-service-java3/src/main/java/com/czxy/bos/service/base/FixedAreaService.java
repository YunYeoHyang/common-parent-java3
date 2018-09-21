package com.czxy.bos.service.base;

import com.czxy.bos.dao.base.CourierMapper;
import com.czxy.bos.dao.base.FixedAreaCourierMapper;
import com.czxy.bos.dao.base.FixedAreaMapper;
import com.czxy.bos.domain.base.Courier;
import com.czxy.bos.domain.base.FixedArea;
import com.czxy.bos.domain.base.FixedAreaCourier;
import com.czxy.bos.exception.BosException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * Created by liangtong on 2018/9/7.
 */
@Service
@Transactional
public class FixedAreaService {

    @Resource
    private FixedAreaMapper fixedAreaMapper;

    @Resource
    private CourierMapper courierMapper;

    @Resource
    private FixedAreaCourierMapper fixedAreaCourierMapper;

    public Integer saveFixedArea(FixedArea fixedArea ){
        //1 校验
        // 1.1 名称必须唯一
        Example nameExample = new Example(FixedArea.class);
        Example.Criteria nameCriteria = nameExample.createCriteria();
        nameCriteria.andEqualTo("fixedAreaName" ,fixedArea.getFixedAreaName());
        FixedArea nameFixedArea = fixedAreaMapper.selectOneByExample(nameExample);
        if(nameFixedArea != null){
            throw new BosException("定区名称已存在！");
        }
        // 1.2 负责人必须唯一
        Example leaderExample = new Example(FixedArea.class);
        Example.Criteria leaderCriteria = leaderExample.createCriteria();
        leaderCriteria.andEqualTo("fixedAreaLeader" ,fixedArea.getFixedAreaLeader() );
        FixedArea leaderFixedArea = fixedAreaMapper.selectOneByExample(leaderExample);
        if(leaderFixedArea != null){
            throw new BosException("定区的负责人已存在！");
        }
        // 1.3 数据完整性
        fixedArea.setId( UUID.randomUUID().toString() );

        //2 添加
        return fixedAreaMapper.insert(fixedArea);
    }

    /**
     * 分页查询所有
     * @param fixedArea 条件，需自己完善!!!
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<FixedArea> findAllByPage(FixedArea fixedArea , Integer page , Integer rows){
        //TODO 0 条件
        //1 分页
        PageHelper.startPage( page ,rows );
        //2 查询
        List<FixedArea> list = fixedAreaMapper.selectByExample( null );
        //3 封装
        return new PageInfo<>( list );
    }

    /**
     * 定区关联快递员
     * @param fixedAreaId 定区id
     * @param courierId 快递员id
     * @param takeTimeId 收派时间id
     */
    public void associationCourierToFixedArea(String fixedAreaId, Integer courierId, Integer takeTimeId) {
        //1 更新快递员的收派时间
        // 1.1 快递员查询
        Courier courier = courierMapper.selectByPrimaryKey(courierId);
        //1.2 设置收派时间
        courier.setTakeTimeId(takeTimeId);
        //1.3 更新快要
        courierMapper.updateByPrimaryKey(courier);

        //2 快递员定区表中添加一条关联数据
        FixedAreaCourier fixedAreaCourier = new FixedAreaCourier();
        fixedAreaCourier.setCourierId(courierId);
        fixedAreaCourier.setFixedAreaId(fixedAreaId);
        fixedAreaCourierMapper.insert(fixedAreaCourier);

    }

}
