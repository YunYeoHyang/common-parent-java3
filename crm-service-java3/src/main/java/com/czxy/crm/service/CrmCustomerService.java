package com.czxy.crm.service;

import com.czxy.bos.exception.BosException;
import com.czxy.crm.dao.CrmCustomerMapper;
import com.czxy.crm.domain.Customer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CrmCustomerService {

    @Resource
    private CrmCustomerMapper crmCustomerMapper;

    /**
     * 查询没有关联定区的客户 （客户表中，定区外键为null）
     * @return
     */
    public List<Customer> findNoAssociationCustomers(){
        //拼凑条件，fixedAreaId需要为null
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIsNull("fixedAreaId");

        //查询所有
        return crmCustomerMapper.selectByExample( example );

    }

    /**
     * 查询定区已经关联的客户 （客户表中，定区外键=xxx）
     * @param fixedAreaId
     * @return
     */
    public List<Customer> findHasAssociationFixedAreaCustomers(String fixedAreaId){
        //拼凑条件 fixedAreaId = ?
        Example example = new Example( Customer.class );
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fixedAreaId",fixedAreaId);

        //查询所有
        return crmCustomerMapper.selectByExample( example );
    }


    /**
     * 将指定的定区关联到多个客户中
     * @param fixedAreaId
     * @param customerIds
     */
    public void associationCustomersToFixedArea(String fixedAreaId , String customerIds){
        //1 获得指定定区关联的所有客户，并将外键更新成null
        List<Customer> list = findHasAssociationFixedAreaCustomers(fixedAreaId);
        for(Customer customer : list ){
            customer.setFixedAreaId(null);
            crmCustomerMapper.updateByPrimaryKey( customer );
        }

        //2 将一组客户id拆分数组，遍历依次关联（通过客户id查询客户，将关联定区外键设置成当前的定区）
        String[] ids = customerIds.split(",");
        for(String idStr : ids ){
            Integer id = Integer.parseInt(idStr);
            // 通过id查询
            Customer customer = crmCustomerMapper.selectByPrimaryKey(id);
            // 设置外键为 当前定区
            customer.setFixedAreaId(fixedAreaId);
            // 更新对象数据
            crmCustomerMapper.updateByPrimaryKey(customer);

        }
    }


    public void saveCustomer(Customer customer) {
        //1 校验
        // 1.1 非空校验
        if(StringUtils.isBlank(customer.getTelephone())){
            throw new BosException("手机不能为空！");
        }
        // 1.2 手机号不能重复
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("telephone",customer.getTelephone());
        Customer findCustomer = crmCustomerMapper.selectOneByExample(example);
        if(findCustomer != null){
            throw new BosException("手机已被占用！");
        }

        //2 添加
        crmCustomerMapper.insert(customer);

    }

    /**
     * 通过手机号查询客户
     * @param telephone
     * @return
     */
    public Customer findByTelephone(String telephone){
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("telephone",telephone);

        return crmCustomerMapper.selectOneByExample(example);
    }

    /**
     * 激活操作 (根据电话号码修改用户的激活状态)
     * @param telephone
     */
    public void updateType(String telephone){
        //1 查询
        Customer customer = findByTelephone(telephone);
        if(customer == null){
            throw new BosException("操作对象不存在");
        }
        //2 在更新
        customer.setType(1);    //激活
        crmCustomerMapper.updateByPrimaryKey(customer);
    }

    /**
     * 通过手机号和密码查看客户
     * @param telephone
     * @param password
     * @return
     */
    public Customer findCustomerTelephoneAndPassword(String telephone , String password){
        Example example = new Example(Customer.class);
        example.createCriteria()
                .andEqualTo("telephone",telephone)
                .andEqualTo("password",password);

        return this.crmCustomerMapper.selectOneByExample( example );
    }

    /**
     * 通过地址和客户id查询定区id
     * @param address
     * @param customerId
     * @return
     */
    public String findFixdAreaIdByAddressAndID(String address, String customerId) {
        Example example = new Example(Customer.class);
        example.createCriteria()
                .andEqualTo("address", address)
                .andEqualTo("id", customerId);
        Customer customer = crmCustomerMapper.selectOneByExample(example);
        if(customer != null){
            return customer.getFixedAreaId();
        }
        return null;
    }
}













