package com.czxy.bos.service.transit;

import com.czxy.bos.dao.take_delivery.WayBillMapper;
import com.czxy.bos.dao.transit.SignInfoMapper;
import com.czxy.bos.dao.transit.TransitInfoMapper;
import com.czxy.bos.domain.take_delivery.WayBill;
import com.czxy.bos.domain.transit.SignInfo;
import com.czxy.bos.domain.transit.TransitInfo;
import com.czxy.bos.es.domain.EsWayBill;
import com.czxy.bos.es.repository.WayBillRepository;
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
        // 保存签收录入信息
        signInfoMapper.insert(signInfo);
        // 关联运输流程
        TransitInfo transitInfo = transitInfoMapper.selectByPrimaryKey(Integer.parseInt(transitInfoId));
        transitInfo.setSignInfo(signInfo);
        transitInfo.setSignInfoId(signInfo.getId());

        WayBill wayBill = wayBillMapper.selectByPrimaryKey(transitInfo.getWayBillId());

        // 更改状态
        if (signInfo.getSignType().equals("正常")) {
            // 正常签收
            transitInfo.setStatus("正常签收");
            // 更改运单状态（3：表示已签收）
            wayBill.setSignStatus(3);

        } else {
            // 异常
            transitInfo.setStatus("异常");
            // 更改运单状态（4：表示异常）
            wayBill.setSignStatus(4);
        }

        transitInfoMapper.updateByPrimaryKey(transitInfo);
        //更新waybill
        wayBillMapper.updateByPrimaryKey(wayBill);

        // 更改索引库（只要运单WayBill的数据发生改变，都需要更新索引库）
        EsWayBill esWayBill = new EsWayBill();
        BeanUtils.copyProperties(wayBill, esWayBill);
        wayBillRepository.save(esWayBill);

    }
}
