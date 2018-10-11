package com.czxy.bos.service.transit;

import com.czxy.bos.dao.transit.DeliveryInfoMapper;
import com.czxy.bos.dao.transit.TransitInfoMapper;
import com.czxy.bos.domain.transit.DeliveryInfo;
import com.czxy.bos.domain.transit.TransitInfo;
import com.czxy.bos.exception.BosException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class DeliveryInfoService {

    @Resource
    private DeliveryInfoMapper deliveryInfoMapper;

    @Resource
    private TransitInfoMapper transitInfoMapper;


    public void save(String transitInfoId, DeliveryInfo deliveryInfo) {

        //1 根据“运输配送信息”id查询详情
        TransitInfo transitInfo = transitInfoMapper.selectByPrimaryKey( transitInfoId );

        //2 校验，如果不是“到达网点”，将不允许操作
        if(transitInfo == null){
            throw new BosException("操作对象不存在");
        }
        if(! "到达网点".equals(transitInfo.getStatus())){
            throw new BosException("状态不是‘到达网点’");
        }

        //3 保存“配送信息”
        deliveryInfoMapper.insert(deliveryInfo);

        //4 将“运输配送信息”的状态修改成“开始配送”，并在外键中记录“配送信息”id，最后更新
        transitInfo.setStatus("开始配送");
        transitInfo.setDeliveryInfoId( deliveryInfo.getId() );
        transitInfoMapper.updateByPrimaryKey(transitInfo);

    }
}
