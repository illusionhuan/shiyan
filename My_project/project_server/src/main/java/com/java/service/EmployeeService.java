package com.java.service;

import com.java.dto.EmployeeDTO;
import com.java.dto.EmployeeLoginDTO;
import com.java.dto.EmployeePageQueryDTO;
import com.java.entity.Employee;
import com.java.result.PageResult;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    /**
     * 登录
     *
     * @param employeeLoginDTO 员工登录dto
     * @return {@link Employee}
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);


    /**
     * 新增员工
     *
     * @param employeeDTO 员工登录dto
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return {@link PageResult}
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
}
