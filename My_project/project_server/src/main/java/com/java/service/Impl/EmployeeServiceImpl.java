package com.java.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.java.constant.MessageConstant;
import com.java.constant.PasswordConstant;
import com.java.context.BaseContext;
import com.java.dto.EmployeeDTO;
import com.java.dto.EmployeeLoginDTO;
import com.java.dto.EmployeePageQueryDTO;
import com.java.entity.Employee;
import com.java.exception.AccountLockedException;
import com.java.exception.AccountNotFoundException;
import com.java.exception.PasswordErrorException;
import com.java.mapper.EmployeeMapper;
import com.java.result.PageResult;
import com.java.service.EmployeeService;
import org.apache.commons.codec.cli.Digest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {

        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);


        if(employee == null){
            // 账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //密码比对
        //对前端传过来的明文密码进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if(employee.getStatus() == 0){
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        return employee;
    }


    /**
     * 功能描述：新增员工
     *
     * @param employeeDTO
     * @author illusion
     * @date 2023/09/03
     */

    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        //属性拷贝
        BeanUtils.copyProperties(employeeDTO,employee);
        //账号状态默认为1，正常状态
        employee.setStatus(1);
        // 设置默认密码为为 "123456"
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert(employee);

    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return {@link PageResult}
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }


    /**
     * 功能描述：
     *
     * @param status
     * @param id
     * @date 2023/09/03
     */
    @Override
    public void startOrStop(Integer status,Long id){
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        employeeMapper.updateEmployee(employee);
    }

    /**
     * 功能描述:按id查询员工
     *
     * @param id
     * @return
     * @date 2023/09/03
     */

    public Employee getById(Long id){
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("****");
        return employee;
    }

    /**
     * 功能描述:按id编辑员工信息
     *
     * @param employeeDTO
     * @return
     * @date 2023/09/03
     */

    public void updateEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        //设置修改人和时间
//        employee.setUpdateUser(BaseContext.getCurrentId());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        System.out.println(LocalDateTime.now());

        employeeMapper.updateEmployee(employee);
    }
}
