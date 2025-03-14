package com.example.addressBookApp.service;

//UC7

import com.example.addressBookApp.dto.AddressBookDTO;
import com.example.addressBookApp.interfaces.IAddressBookService;
import com.example.addressBookApp.model.AddressBookEntry;
import com.example.addressBookApp.repository.AddressBookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressBookService implements IAddressBookService {

    @Autowired
    private AddressBookRepository repository;

    @Autowired
    private ModelMapper modelMapper;  // For DTO Conversion

    // ✅ Cache all contacts
    @Override
    @Cacheable(value = "contacts")
    public List<AddressBookEntry> getAllContacts() {
        System.out.println("📢 Fetching from Database...");
        return repository.findAll();
    }

    @Override
    public Optional<AddressBookEntry> getContactById(Long id) {
        return repository.findById(id);
    }

    // ✅ Evict cache when a new contact is added
    @Override
    @CacheEvict(value = "contacts", allEntries = true)
    public AddressBookEntry addContact(AddressBookDTO contactDTO) {
        System.out.println("🗑️ Clearing Cache: New contact added!");
        AddressBookEntry contact = modelMapper.map(contactDTO, AddressBookEntry.class);
        return repository.save(contact);
    }

    // ✅ Evict cache when a contact is updated
    @Override
    @CacheEvict(value = "contacts", allEntries = true)
    public AddressBookEntry updateContact(Long id, AddressBookDTO updatedContactDTO) {
        Optional<AddressBookEntry> existingContact = repository.findById(id);
        if (existingContact.isPresent()) {
            AddressBookEntry contact = modelMapper.map(updatedContactDTO, AddressBookEntry.class);
            contact.setId(id);
            System.out.println("🗑️ Clearing Cache: Contact updated!");
            return repository.save(contact);
        }
        return null;
    }

    // ✅ Evict cache when a contact is deleted
    @Override
    @CacheEvict(value = "contacts", allEntries = true)
    public void deleteContact(Long id) {
        System.out.println("🗑️ Clearing Cache: Contact deleted!");
        repository.deleteById(id);
    }
}


//UC4
//import com.example.addressBookApp.dto.AddressBookDTO;
//import com.example.addressBookApp.interfaces.IAddressBookService;
//import com.example.addressBookApp.model.AddressBookEntry;
//import com.example.addressBookApp.repository.AddressBookRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class AddressBookService implements IAddressBookService {
//
//    @Autowired
//    private AddressBookRepository repository;
//
//    @Autowired
//    private ModelMapper modelMapper;  // For DTO Conversion
//
//    @Override
//    public List<AddressBookEntry> getAllContacts() {
//        return repository.findAll();
//    }
//
//    @Override
//    public Optional<AddressBookEntry> getContactById(Long id) {
//        return repository.findById(id);
//    }
//
//    @Override
//    public AddressBookEntry addContact(AddressBookDTO contactDTO) {
//        AddressBookEntry contact = modelMapper.map(contactDTO, AddressBookEntry.class);
//        return repository.save(contact);
//    }
//
//    @Override
//    public AddressBookEntry updateContact(Long id, AddressBookDTO updatedContactDTO) {
//        Optional<AddressBookEntry> existingContact = repository.findById(id);
//        if (existingContact.isPresent()) {
//            AddressBookEntry contact = modelMapper.map(updatedContactDTO, AddressBookEntry.class);
//            contact.setId(id);
//            return repository.save(contact);
//        }
//        return null;
//    }
//
//    @Override
//    public void deleteContact(Long id) {
//        repository.deleteById(id);
//    }
//}


//UC3
//import com.example.addressBookApp.model.AddressBookEntry;
//import com.example.addressBookApp.repository.AddressBookRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class AddressBookService {
//
//    @Autowired
//    private AddressBookRepository repository;
//
//    public List<AddressBookEntry> getAllContacts() {
//        return repository.findAll();
//    }
//
//    public Optional<AddressBookEntry> getContactById(Long id) {
//        return repository.findById(id);
//    }
//
//    public AddressBookEntry saveContact(AddressBookEntry contact) {
//        return repository.save(contact);
//    }
//
//    public void deleteContact(Long id) {
//        repository.deleteById(id);
//    }
//}
