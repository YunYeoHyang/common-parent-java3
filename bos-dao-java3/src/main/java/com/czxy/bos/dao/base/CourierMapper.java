package com.czxy.bos.dao.base;

import com.czxy.bos.domain.base.Courier;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface CourierMapper extends Mapper<Courier> {
    /**
     * 查询没有关联的快递员
     * @return
     */
    @Select("SELECT * FROM t_courier WHERE id NOT IN (SELECT courier_id FROM t_fixedarea_courier)")
    List<Courier> findNoAssociation();

    /**
     * 查询指定定区管理的快递员
     * @return
     */
    @Select("SELECT * FROM t_courier c , t_fixedarea_courier fc " +
            "WHERE c.id = fc.courier_id AND fc.fixed_area_id = #{fixedAreaId}")
    @Results({
            @Result(property="standard",column="standard_id",one=@One(select="com.czxy.bos.dao.base.StandardMapper.selectByPrimaryKey")),
            @Result(property="takeTime",column="taketime_id",one=@One(select="com.czxy.bos.dao.base.TakeTimeMapper.selectByPrimaryKey"))
    }
    )
    List<Courier> findAssociationCourier(@Param("fixedAreaId") String fixedAreaId);

}
