package com.example.addressBookApp.service;

//UC12

import com.example.addressBookApp.dto.AddressBookDTO;
import com.example.addressBookApp.interfaces.IAddressBookService;
import com.example.addressBookApp.model.AddressBookEntry;
import com.example.addressBookApp.repository.AddressBookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class AddressBookService implements IAddressBookService {

    private static final Logger logger = LoggerFactory.getLogger(AddressBookService.class);

    @Autowired
    private AddressBookRepository repository;

    private final String profileMessage;

    @Autowired
    private ModelMapper modelMapper;  // For DTO Conversion

    // ✅ Inject profile-specific bean
    @Autowired
    public AddressBookService(@Qualifier("devBean") String profileMessage) {
        this.profileMessage = profileMessage;
        logger.info("Active Profile Message: {}", profileMessage);
    }


    // ✅ Cache all contacts
    @Override
    @Cacheable(value = "contacts")
    public List<AddressBookEntry> getAllContacts() {
        logger.info("Fetching all contacts from the database...");
        List<AddressBookEntry> contacts = repository.findAll();
        logger.debug("Retrieved {} contacts from the database.", contacts.size());
        return contacts;
    }

    @Override
    public Optional<AddressBookEntry> getContactById(Long id) {
        logger.info("Fetching contact with ID: {}", id);
        Optional<AddressBookEntry> contact = repository.findById(id);
        if (contact.isPresent()) {
            logger.debug("Contact found: {}", contact.get());
        } else {
            logger.warn("Contact with ID {} not found.", id);
        }
        return contact;
    }

    // ✅ Evict cache when a new contact is added
    @Override
    @CacheEvict(value = "contacts", allEntries = true)
    public AddressBookEntry addContact(AddressBookDTO contactDTO) {
        logger.info("Adding a new contact: {}", contactDTO.getName());
        AddressBookEntry contact = modelMapper.map(contactDTO, AddressBookEntry.class);
        AddressBookEntry savedContact = repository.save(contact);
        logger.info("New contact added successfully with ID: {}", savedContact.getId());
        return savedContact;
    }

    // ✅ Evict cache when a contact is updated
    @Override
    @CacheEvict(value = "contacts", allEntries = true)
    public AddressBookEntry updateContact(Long id, AddressBookDTO updatedContactDTO) {
        logger.info("Updating contact with ID: {}", id);
        Optional<AddressBookEntry> existingContact = repository.findById(id);

        if (existingContact.isPresent()) {
            AddressBookEntry contact = modelMapper.map(updatedContactDTO, AddressBookEntry.class);
            contact.setId(id);
            AddressBookEntry updatedContact = repository.save(contact);
            logger.info("Contact updated successfully: {}", updatedContact);
            return updatedContact;
        } else {
            logger.warn("Contact with ID {} not found for update.", id);
            return null;
        }
    }

    // ✅ Evict cache when a contact is deleted
    @Override
    @CacheEvict(value = "contacts", allEntries = true)
    public void deleteContact(Long id) {
        logger.info("Deleting contact with ID: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            logger.info("Contact deleted successfully.");
        } else {
            logger.warn("Contact with ID {} not found for deletion.", id);
        }
    }
}


//UC7

//import com.example.addressBookApp.dto.AddressBookDTO;
//import com.example.addressBookApp.interfaces.IAddressBookService;
//import com.example.addressBookApp.model.AddressBookEntry;
//import com.example.addressBookApp.repository.AddressBookRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
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
//    // ✅ Cache all contacts
//    @Override
//    @Cacheable(value = "contacts")
//    public List<AddressBookEntry> getAllContacts() {
//        System.out.println("📢 Fetching from Database...");
//        return repository.findAll();
//    }
//
//    @Override
//    public Optional<AddressBookEntry> getContactById(Long id) {
//        return repository.findById(id);
//    }
//
//    // ✅ Evict cache when a new contact is added
//    @Override
//    @CacheEvict(value = "contacts", allEntries = true)
//    public AddressBookEntry addContact(AddressBookDTO contactDTO) {
//        System.out.println("🗑️ Clearing Cache: New contact added!");
//        AddressBookEntry contact = modelMapper.map(contactDTO, AddressBookEntry.class);
//        return repository.save(contact);
//    }
//
//    // ✅ Evict cache when a contact is updated
//    @Override
//    @CacheEvict(value = "contacts", allEntries = true)
//    public AddressBookEntry updateContact(Long id, AddressBookDTO updatedContactDTO) {
//        Optional<AddressBookEntry> existingContact = repository.findById(id);
//        if (existingContact.isPresent()) {
//            AddressBookEntry contact = modelMapper.map(updatedContactDTO, AddressBookEntry.class);
//            contact.setId(id);
//            System.out.println("🗑️ Clearing Cache: Contact updated!");
//            return repository.save(contact);
//        }
//        return null;
//    }
//
//    // ✅ Evict cache when a contact is deleted
//    @Override
//    @CacheEvict(value = "contacts", allEntries = true)
//    public void deleteContact(Long id) {
//        System.out.println("🗑️ Clearing Cache: Contact deleted!");
//        repository.deleteById(id);
//    }
//}


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
