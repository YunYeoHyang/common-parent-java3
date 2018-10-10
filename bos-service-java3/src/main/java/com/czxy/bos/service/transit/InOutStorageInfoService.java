package com.czxy.bos.service.transit;

import com.czxy.bos.dao.transit.InOutStorageInfoMapper;
import com.czxy.bos.dao.transit.TransitInfoMapper;
import com.czxy.bos.domain.transit.InOutStorageInfo;
import com.czxy.bos.domain.transit.TransitInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class InOutStorageInfoService {

    @Resource
    private InOutStorageInfoMapper inOutStorageInfoMapper;

    @Resource
    private TransitInfoMapper transitInfoMapper;

    public void save(InOutStorageInfo inOutStorageInfo) {
        // 保存出入库信息
        inOutStorageInfoMapper.insert(inOutStorageInfo);

        // 查询TransitInfo的运输信息
        TransitInfo transitInfo = transitInfoMapper.selectByPrimaryKey(inOutStorageInfo.getTransitInfoId());

        // 关联出入库信息 到 运输配送对象，生成数据列的集合索引，@OrderColumn(name = "C_IN_OUT_INDEX")
        if (inOutStorageInfo.getOperation().equals("到达网点")) {
            // 设置运输状态为到达网点
            transitInfo.setStatus("到达网点");
            // 更新网点地址 ，显示配送路径
            transitInfo.setOutletAddress(inOutStorageInfo.getAddress());

            transitInfoMapper.updateByPrimaryKey(transitInfo);
        }
    }
}
