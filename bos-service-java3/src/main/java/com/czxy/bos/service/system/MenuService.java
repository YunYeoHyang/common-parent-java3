package com.czxy.bos.service.system;

import com.czxy.bos.dao.system.MenuMapper;
import com.czxy.bos.domain.system.Menu;
import com.czxy.bos.domain.system.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

    public PageInfo<Menu> findAll(Integer page , Integer rows) {
        PageHelper.startPage(page ,rows);
        List<Menu> list = menuMapper.selectAll();
        return new PageInfo<>( list );
    }

    /**
     * 保存
     * @param menu
     */
    public void save(Menu menu) {
        //如果没有设置优先级priority，将使用当前分类中最大值+1
        if(menu.getPriority() == null){
            Example example = new Example(Menu.class);
            Example.Criteria criteria = example.createCriteria();
            if(menu.getPid() == null){
                //第一级别
                criteria.andIsNull("pid");
            }else{
                //其他级别
                criteria.andEqualTo("pid", menu.getPid());
            }
            int count = menuMapper.selectCountByExample(example);
            menu.setPriority(count + 1);
        }

        menuMapper.insert(menu);
    }

    public List<Menu> findAll() {
        return menuMapper.selectAll();
    }

    /**
     * 动态菜单
     * @param user
     * @return
     */
    public List<Menu> findByUser(User user) {
        // 针对admin用户显示所有的菜单
        if(user.getUsername().equals("admin")){
            return menuMapper.selectAll();
        } else {
            // 使用用户ID，查询当前用户具有的菜单列表
            return menuMapper.findByUser(user.getId());
        }
    }
}
