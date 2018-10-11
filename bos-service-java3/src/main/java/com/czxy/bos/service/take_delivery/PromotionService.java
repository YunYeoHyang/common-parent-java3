package com.czxy.bos.service.take_delivery;

import com.czxy.bos.dao.take_delivery.PromotionMapper;
import com.czxy.bos.domain.take_delivery.Promotion;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PromotionService {

    @Resource
    private PromotionMapper promotionMapper;

    public void save(Promotion promotion) {
        promotionMapper.insert(promotion);
    }

    /**
     * 查询所有，含分页
     *
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<Promotion> queryPromotionListByPage(Integer page, Integer rows) {
        //1 分页
        PageHelper.startPage(page, rows);
        //2 查询
        List<Promotion> list = this.promotionMapper.selectAll();
        //3 封装
        return new PageInfo<>(list);
    }

    /**
     * 查询所有，不含分页
     *
     * @return
     */
    public List<Promotion> findAll() {
        Example example = new Example(Promotion.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", "1");

        //查询
        return this.promotionMapper.selectByExample(example);
    }

    /**
     * 更新过期时间
     */
    public void updateWithEndDate() {
        this.promotionMapper.updateWithEndDate();
    }


}
