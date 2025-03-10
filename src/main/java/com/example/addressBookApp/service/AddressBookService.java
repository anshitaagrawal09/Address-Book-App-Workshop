package com.example.addressBookApp.service;

import com.example.addressBookApp.model.AddressBookEntry;
import com.example.addressBookApp.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AddressBookService {

    @Autowired
    private AddressBookRepository repository;

    public List<AddressBookEntry> getAllContacts() {
        return repository.findAll();
    }

    public Optional<AddressBookEntry> getContactById(Long id) {
        return repository.findById(id);
    }

    public AddressBookEntry saveContact(AddressBookEntry contact) {
        return repository.save(contact);
    }

    public void deleteContact(Long id) {
        repository.deleteById(id);
    }
}
