package com.czxy.bos.dao.base;

import com.czxy.bos.domain.base.Area;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface AreaMapper extends Mapper<Area> {

    /**
     * 人工分单
     *
     * @param area
     * @return
     */
    @Select("select * from t_area where province=#{province} and city=#{city} and district=#{district}")
    public Area selectByArea(Area area);
}
