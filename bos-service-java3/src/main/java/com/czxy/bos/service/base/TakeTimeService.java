package com.czxy.bos.service.base;

import com.czxy.bos.dao.base.TakeTimeMapper;
import com.czxy.bos.domain.base.TakeTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liangtong on 2018/9/11.
 */
@Service
@Transactional
public class TakeTimeService {

    @Resource
    private TakeTimeMapper takeTimeMapper;

    /**
     * 查询所有
     * @return
     */
    public List<TakeTime> findAll() {
        return takeTimeMapper.selectAll();
    }
}
