package com.java.service.Impl;

import com.java.context.BaseContext;
import com.java.entity.AddressBook;
import com.java.mapper.AddressBookMapper;
import com.java.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 功能描述:新增地址
     *
     * @param addressBook
     * @return
     */

    @Override
    public void save(AddressBook addressBook) {
        addressBook.setUserId(1L);
        addressBook.setIsDefault(0);
        addressBookMapper.insert(addressBook);
    }

    /**
     * 功能描述:查询所有地址
     *
     * @return
     */

    @Override
    public List<AddressBook> list(AddressBook addressBook) {
        return addressBookMapper.list(addressBook);
    }

    /**
     * 功能描述:按Id查询地址
     *
     * @return
     */
    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    /**
     * 功能描述:按Id删除地址
     *
     * @return
     */
    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }

    /**
     * 功能描述:更新地址
     *
     * @return
     */
    @Override
    public void updateById(AddressBook addressBook) {
        addressBookMapper.updateById(addressBook);
    }

    @Override
    @Transactional
    public void setDefault(AddressBook addressBook) {
        //1、将当前用户的所有地址修改为非默认地址
        addressBook.setIsDefault(0);
//        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setUserId(1L);
        addressBookMapper.updateIsDefaultByUserId(addressBook);

        //2、将当前地址改为默认地址 update address_book set is_default = ? where id = ?
        addressBook.setIsDefault(1);
        addressBookMapper.updateById(addressBook);
    }
}
