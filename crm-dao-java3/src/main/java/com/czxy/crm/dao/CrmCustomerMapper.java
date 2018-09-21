package com.czxy.crm.dao;

import com.czxy.crm.domain.Customer;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by liangtong on 2018/9/10.
 */
@org.apache.ibatis.annotations.Mapper
public interface CrmCustomerMapper extends Mapper<Customer> {
}
