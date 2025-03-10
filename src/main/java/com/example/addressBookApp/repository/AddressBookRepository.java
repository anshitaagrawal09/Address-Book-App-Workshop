package com.example.addressBookApp.repository;

import com.example.addressBookApp.model.AddressBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AddressBookRepository extends JpaRepository<AddressBookEntry, Long> {
    List<AddressBookEntry> findByNameContaining(String name);
}
