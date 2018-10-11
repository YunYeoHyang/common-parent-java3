package com.czxy.bos.service.base;

import com.czxy.bos.dao.base.AreaMapper;
import com.czxy.bos.domain.base.Area;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AreaService {

    @Resource
    private AreaMapper areaMapper;

    /**
     * 对上传数据进行校验，如果存在忽略，只记录成功个数。
     *
     * @param list
     * @return
     */
    public Integer saveAreas(List<Area> list) {
        int count = 0;
        for (Area area : list) {
            //TODO 没有校验是否重复，如果重复忽略，方法返回值整数（本次添加个数）
            Area temp = areaMapper.selectByPrimaryKey(area.getId());

            if (temp != null) {
                continue;
            }
            count++;
            areaMapper.insert(area);
        }
        return count;
    }

    /**
     * 带条件的分页查询
     *
     * @param area 条件
     * @param page 第几页
     * @param rows 每页显示个数
     * @return
     */
    public PageInfo<Area> findAll(Area area, Integer page, Integer rows) {
        //1 拼凑条件
        Example example = new Example(Area.class);
        Example.Criteria criteria = example.createCriteria();
        if (area != null) {
            //省
            if (StringUtils.isNotBlank(area.getProvince())) {
                criteria.andLike("province", "%" + area.getProvince() + "%");

            }
            //TODO 市 、县
        }
        //2 分页设置
        PageHelper.startPage(page, rows);
        //3 查询所有(带条件)
        List<Area> list = areaMapper.selectByExample(example);
        //4 封装
        return new PageInfo<>(list);
    }

    /**
     * 添加功能
     *
     * @param area
     * @return
     */
    public Integer save(Area area) {
        area.setId(UUID.randomUUID().toString());
        return areaMapper.insert(area);
    }

    /**
     * 更新功能
     *
     * @param area
     * @return
     */
    public Integer update(Area area) {
        return this.areaMapper.updateByPrimaryKey(area);
    }

    /**
     * 删除功能
     *
     * @param ids
     * @return
     */
    public Integer deleteArea(String[] ids) {

        int count = 0;
        for (String idstr : ids) {
            areaMapper.deleteByPrimaryKey(idstr);
            count++;
        }
        return count;
    }

    /**
     * 人工分单
     * 通过省市县查询地区
     *
     * @param area
     * @return
     */
    public Area selectByArea(Area area) {
        return areaMapper.selectByArea(area);
    }
}
