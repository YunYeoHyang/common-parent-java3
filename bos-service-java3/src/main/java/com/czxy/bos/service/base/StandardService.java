package com.czxy.bos.service.base;

import com.czxy.bos.dao.base.StandardMapper;
import com.czxy.bos.domain.base.Standard;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liangtong on 2018/9/3.
 */
@Service
@Transactional
public class StandardService {

    //注入mapper
    @Resource
    private StandardMapper standardMapper;

    /**
     *
     * @param page 第几页
     * @param rows 每页显示个数
     * @return
     */
    public PageInfo<Standard> queryStandardByPage(int page , int rows){
        //1 分页
        PageHelper.startPage(page ,rows);
        //2 查询所有--内含分页
        List<Standard> list = standardMapper.selectAll();
        //3 封装数据
        return new PageInfo<>( list );
    }

    /**
     * 保存
     * @param standard
     * @return
     */
    public Integer saveStandard( Standard standard ){
        return standardMapper.insert(standard  );
    }

    /**
     * 更新数据
     * @param standard
     * @return
     */
    public Integer updateStandard( Standard standard ){
        return standardMapper.updateByPrimaryKey(standard);

    }

    /**
     * 批量删除
     * @param ids
     */
    public void deleteStandard(String[] ids ){
        for(String idStr : ids){
            Integer id = Integer.parseInt(idStr);
            //TODO 查询“收派标准”是否关联快递员
            // throw new BosException("不能删除，管理快递员");
            standardMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 查询所有
     * @return
     */
    public List<Standard> findAll(){
        return standardMapper.selectAll();
    }


}
