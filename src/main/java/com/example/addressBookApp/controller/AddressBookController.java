package com.example.addressBookApp.controller;

import com.example.addressBookApp.model.AddressBookEntry;
import com.example.addressBookApp.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {

    @Autowired
    private AddressBookService service;

    @GetMapping
    public ResponseEntity<List<AddressBookEntry>> getAllContacts() {
        return ResponseEntity.ok(service.getAllContacts());
    }
    //curl -X GET "http://localhost:8080/api/addressbook"

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AddressBookEntry>> getContactById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getContactById(id));
    }
    //curl -X GET "http://localhost:8080/api/addressbook/1"

    @PostMapping
    public ResponseEntity<AddressBookEntry> addContact(@RequestBody AddressBookEntry contact) {
        return ResponseEntity.ok(service.saveContact(contact));
    }
    //curl -X POST "http://localhost:8080/api/addressbook" -H "Content-Type: application/json" -d '{
    //  "name": "John Doe",
    //  "phoneNumber": "1234567890",
    //  "email": "john@example.com",
    //  "address": "123 Main Street"
    //}'

    @PutMapping("/{id}")
    public ResponseEntity<AddressBookEntry> updateContact(@PathVariable Long id, @RequestBody AddressBookEntry updatedContact) {
        Optional<AddressBookEntry> existingContact = service.getContactById(id);
        if (existingContact.isPresent()) {
            updatedContact.setId(id);
            return ResponseEntity.ok(service.saveContact(updatedContact));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //curl -X PUT "http://localhost:8080/api/addressbook/1" -H "Content-Type: application/json" -d '{
    //  "name": "John Doe Updated",
    //  "phoneNumber": "0987654321",
    //  "email": "john.doe@example.com",
    //  "address": "456 Elm Street"
    //}'

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        service.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
    //curl -X DELETE "http://localhost:8080/api/addressbook/1"
}