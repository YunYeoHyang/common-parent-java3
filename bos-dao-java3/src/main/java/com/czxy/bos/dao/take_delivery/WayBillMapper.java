package com.czxy.bos.dao.take_delivery;

import com.czxy.bos.domain.report.HighChart;
import com.czxy.bos.domain.take_delivery.WayBill;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface WayBillMapper extends Mapper<WayBill> {

    @Select("SELECT COUNT(*) as data , " +
            "CASE " +
            "  WHEN sign_status = 1 THEN '待发货' " +
            "  WHEN sign_status = 2 THEN '派送中' " +
            "  WHEN sign_status = 3 THEN '已签收' " +
            "  ELSE '异常' " +
            "END AS name " +
            "FROM t_way_bill " +
            "GROUP BY sign_status " +
            "ORDER BY sign_status ")
    public List<HighChart> findBySignStatus();
}

