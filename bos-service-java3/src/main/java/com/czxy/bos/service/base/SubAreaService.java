package com.czxy.bos.service.base;

import com.czxy.bos.dao.base.SubAreaMapper;
import com.czxy.bos.domain.base.SubArea;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SubAreaService {

    @Resource
    private SubAreaMapper subAreaMapper;

    public List<SubArea> findAllByAreaId(String areaId){
        Example example = new Example(SubArea.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("areaId" , areaId);

        return this.subAreaMapper.selectByExample(example);
    }
}
