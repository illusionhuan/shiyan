package com.java.service;

import com.java.entity.AddressBook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressBookService {

    void save(AddressBook addressBook);

    List<AddressBook> list(AddressBook addressBook);

    AddressBook getById(Long id);

    void deleteById(Long id);

    void updateById(AddressBook addressBook);

    void setDefault(AddressBook addressBook);
}
