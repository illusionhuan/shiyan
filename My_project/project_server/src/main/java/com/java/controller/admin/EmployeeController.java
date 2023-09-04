package com.java.controller.admin;

import com.java.constant.JwtClaimsConstant;
import com.java.constant.MessageConstant;
import com.java.dto.EmployeeDTO;
import com.java.dto.EmployeeLoginDTO;
import com.java.dto.EmployeePageQueryDTO;
import com.java.entity.Employee;
import com.java.properties.JwtProperties;
import com.java.result.PageResult;
import com.java.result.Result;
import com.java.service.EmployeeService;
import com.java.utils.JwtUtil;
import com.java.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO 员工登录dto
     * @return {@link Result}<{@link EmployeeLoginVO}>
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        log.info("员工登录:{}",employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功,返回jwt令牌
        Map<String,Object> claims = new HashMap<String,Object>();

//        System.out.println(employeeLoginDTO);
//        System.out.println(employee);

        claims.put(JwtClaimsConstant.EMP_ID,employee.getId());

        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     * @return {@link Result}
     */
    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout(){
        return Result.success();
    }


    /**
     * 新增员工
     *
     * @param employeeDTO 员工登录dto
     * @return {@link Result}
     */
    @PostMapping
    @ApiOperation("新增员工接口")
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工:{}",employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }
    /*
    *
    * 分页查询员工
    * */
    @GetMapping("/page")
    @ApiOperation("分页管理接口")
    public Result<PageResult> page(@RequestBody EmployeePageQueryDTO employeePageQueryDTO){
        log.info("分页查询:{}",employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 功能描述:
     *
     * @param status
     * @param id
     * @return
     */

    @PostMapping("/status/{status}")
    @ApiOperation("启用/禁用员工账号")
    public Result<String> startOrStop(@PathVariable Integer status,Long id){
        log.info("启用/禁用员工账号:{},{}",status,id);
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 功能描述：按id查询员工
     *
     * @return
     * @date 2023/09/03
     */

    @GetMapping("/{id}")
    @ApiOperation("按id查询员工")
    public Result<Employee> getById(@PathVariable Long id){
        log.info("查询员工信息:{}",id);
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return Result.success(employee);
        }else{
            return Result.error(MessageConstant.ACCOUNT_NOT_FOUND);
        }
    }

    /**
     * 功能描述:编辑员工信息
     *
     * @param employeeDTO
     * @return
     * @date 2023/09/03
     */

    @PutMapping
    @ApiOperation("编辑员工信息")
    public Result updateEmloyee(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑员工信息:{}", employeeDTO);
        employeeService.updateEmployee(employeeDTO);

        return Result.success();
    }


}
