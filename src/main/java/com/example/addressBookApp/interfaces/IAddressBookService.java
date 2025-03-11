package com.example.addressBookApp.interfaces;

import com.example.addressBookApp.dto.AddressBookDTO;
import com.example.addressBookApp.model.AddressBookEntry;

import java.util.List;
import java.util.Optional;

public interface IAddressBookService {
    List<AddressBookEntry> getAllContacts();
    Optional<AddressBookEntry> getContactById(Long id);
    AddressBookEntry addContact(AddressBookDTO contactDTO);
    AddressBookEntry updateContact(Long id, AddressBookDTO updatedContactDTO);
    void deleteContact(Long id);
}
