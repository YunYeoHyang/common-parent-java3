package com.czxy.bos.service.transit;

import com.czxy.bos.dao.transit.DeliveryInfoMapper;
import com.czxy.bos.dao.transit.TransitInfoMapper;
import com.czxy.bos.domain.transit.DeliveryInfo;
import com.czxy.bos.domain.transit.TransitInfo;
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
        // 保存开始配送信息
        deliveryInfoMapper.insert(deliveryInfo);

        // 查询运输配送对象
        TransitInfo transitInfo = transitInfoMapper.selectByPrimaryKey(Integer.parseInt(transitInfoId));
        transitInfo.setDeliveryInfoId(deliveryInfo.getId());

        // 更改状态
        transitInfo.setStatus("开始配送");
        transitInfoMapper.updateByPrimaryKey(transitInfo);
    }
}
