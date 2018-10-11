package com.czxy.bos.service.transit;

import com.czxy.bos.dao.take_delivery.WayBillMapper;
import com.czxy.bos.dao.transit.SignInfoMapper;
import com.czxy.bos.dao.transit.TransitInfoMapper;
import com.czxy.bos.domain.take_delivery.WayBill;
import com.czxy.bos.domain.transit.SignInfo;
import com.czxy.bos.domain.transit.TransitInfo;
import com.czxy.bos.es.domain.EsWayBill;
import com.czxy.bos.es.repository.WayBillRepository;
import com.czxy.bos.exception.BosException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class SignInfoService {

    @Resource
    private SignInfoMapper signInfoMapper;

    @Resource
    private TransitInfoMapper transitInfoMapper;

    @Resource
    private WayBillRepository wayBillRepository;

    @Resource
    private WayBillMapper wayBillMapper;

    public void save(String transitInfoId, SignInfo signInfo) {

        //1 查询运输信息
        TransitInfo transitInfo = this.transitInfoMapper.selectByPrimaryKey( transitInfoId );

        //2 校验
        // 非空
        if(transitInfo == null){
            throw new BosException("对象不存在");
        }
        // 不是“开始配送”
        if(!"开始配送".equals(transitInfo.getStatus())){
            throw new BosException("状态不正确");
        }

        //3 保存签收信息 （注意：id注解修饰，必须JDBC）
        this.signInfoMapper.insert( signInfo );


        WayBill wayBill = wayBillMapper.selectByPrimaryKey( transitInfo.getWayBillId() );

        if("正常".equals(signInfo.getSignType())){
            transitInfo.setStatus("正常签收");
            wayBill.setSignStatus(3);   //已签收
        } else {
            transitInfo.setStatus("异常");
            wayBill.setSignStatus(4);   //异常
        }
        //4 更新运输信息状态、关联签收对象
        transitInfo.setSignInfoId( signInfo.getId() );
        transitInfoMapper.updateByPrimaryKey(transitInfo);

        //5 更新运单
        wayBillMapper.updateByPrimaryKey(wayBill);

        //6 同步es
        EsWayBill esWayBill = new EsWayBill();
        BeanUtils.copyProperties(wayBill , esWayBill );

        this.wayBillRepository.save( esWayBill );
    }
}
