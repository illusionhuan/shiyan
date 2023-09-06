package com.java.controller.user;

import com.java.context.BaseContext;
import com.java.entity.AddressBook;
import com.java.result.Result;
import com.java.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "地址簿接口")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("新增地址")
    public Result save(@RequestBody AddressBook addressBook){
        log.info("新增地址:{}",addressBook);
        addressBookService.save(addressBook);

        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询所有地址")
    public Result<List<AddressBook>> list(){
        log.info("查询所有地址");
        AddressBook addressBook = new AddressBook();
//        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setUserId(1L);
        return Result.success(addressBookService.list(addressBook));
    }

    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> selectDefault(){
        log.info("查询默认地址");
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);
        //        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setUserId(1L);

        List<AddressBook> list = addressBookService.list(addressBook);
        if(list != null && list.size() == 1){
            return Result.success(list.get(0));
        }

        return Result.error("没有查询到默认地址");
    }

    @GetMapping("/{id}")
    @ApiOperation("按照ID查询地址")
    public Result<AddressBook> selectById(@PathVariable Long id){
        log.info("按Id查询地址:{}",id);
        AddressBook addressBook = addressBookService.getById(id);

        return Result.success(addressBook);
    }

    @DeleteMapping
    @ApiOperation("按Id删除地址")
    public Result deleteById(@RequestParam Long id){
        log.info("删除地址:{}",id);
        addressBookService.deleteById(id);

        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改地址")
    public Result updateById(@RequestBody AddressBook addressBook){
        log.info("修改地址:{}",addressBook);
        addressBookService.updateById(addressBook);

        return Result.success();
    }

    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result updateByIdSetDefault(@RequestBody AddressBook addressBook){
        addressBookService.setDefault(addressBook);

        return Result.success();
    }
}
