package com.czxy.bos.service.take_delivery;

import com.czxy.bos.dao.take_delivery.BosOrderMapper;
import com.czxy.bos.domain.take_delivery.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class BosOrderService {

    @Resource
    private BosOrderMapper bosOrderMapper;

    /**
     * 保存订单
     * @param order
     */
    public void addOrder(Order order) {
        bosOrderMapper.insert(order);
    }

}
