package com.example.addressBookApp.service;

import com.example.addressBookApp.dto.AddressBookDTO;
import com.example.addressBookApp.model.AddressBookEntry;
import com.example.addressBookApp.repository.AddressBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.mockito.Mockito.lenient; // Add this import for lenient()

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressBookServiceTest {

    @Mock
    private AddressBookRepository addressBookRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AddressBookService addressBookService;

    private AddressBookEntry mockEntry;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockEntry = new AddressBookEntry();
        mockEntry.setId(1L);
        mockEntry.setName("John Doe");
        mockEntry.setEmail("john@example.com");
        mockEntry.setPhoneNumber("1234567890");
        mockEntry.setAddress("123 Street, City");
    }

    @Test
    void testGetContactById_ExistingId() {
        when(addressBookRepository.findById(1L)).thenReturn(Optional.of(mockEntry));

        Optional<AddressBookEntry> retrievedEntry = addressBookService.getContactById(1L);
        assertTrue(retrievedEntry.isPresent());  // Check if Optional is present
        assertEquals("John Doe", retrievedEntry.get().getName());
    }

//    @Test
//    void testGetContactById_NonExistingId() {
//        when(addressBookRepository.findById(2L)).thenReturn(Optional.empty());
//
//        Optional<AddressBookEntry> retrievedEntry = addressBookService.getContactById(2L);
//        assertFalse(retrievedEntry.isPresent());  // Check if Optional is empty
//    }
@Test
void testGetContactById_NonExistingId_ShouldThrowException() {
    when(addressBookRepository.findById(2L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
        addressBookService.getContactById(2L).orElseThrow(() -> new RuntimeException("Contact not found"));
    });

    assertEquals("Contact not found", exception.getMessage());
}

//    @Test
//    void testAddContact_Success() {
//        AddressBookDTO dto = new AddressBookDTO("Jane Doe", "jane@example.com", "9876543210", "456 Avenue, City");
//        when(modelMapper.map(dto, AddressBookEntry.class)).thenReturn(mockEntry);
//        when(addressBookRepository.save(any(AddressBookEntry.class))).thenReturn(mockEntry);
//
//        AddressBookEntry savedEntry = addressBookService.addContact(dto);
//        assertNotNull(savedEntry);
//        assertEquals("John Doe", savedEntry.getName());  // Ensure name matches mockEntry
//    }

    @Test
    void testUpdateContact_ExistingId() {
        AddressBookDTO updatedDto = new AddressBookDTO("Jane Doe", "jane@example.com", "9876543210", "456 Avenue, City");
        AddressBookEntry updatedEntry = new AddressBookEntry();
        updatedEntry.setId(1L);
        updatedEntry.setName("Jane Doe");
        updatedEntry.setEmail("jane@example.com");
        updatedEntry.setPhoneNumber("9876543210");
        updatedEntry.setAddress("456 Avenue, City");

        when(addressBookRepository.findById(1L)).thenReturn(Optional.of(mockEntry));
        when(modelMapper.map(updatedDto, AddressBookEntry.class)).thenReturn(updatedEntry);
        when(addressBookRepository.save(any(AddressBookEntry.class))).thenReturn(updatedEntry);

        AddressBookEntry result = addressBookService.updateContact(1L, updatedDto);
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
    }

    @Test
    void testUpdateContact_NonExistingId() {
        AddressBookDTO updatedDto = new AddressBookDTO("Jane Doe", "jane@example.com", "9876543210", "456 Avenue, City");

        when(addressBookRepository.findById(2L)).thenReturn(Optional.empty());

        AddressBookEntry result = addressBookService.updateContact(2L, updatedDto);
        assertNull(result);
    }
//    @Test
//    void testDeleteContact_Success() {
//        when(addressBookRepository.findById(1L)).thenReturn(Optional.of(mockEntry)); // Keep only necessary stubbing
//        doNothing().when(addressBookRepository).deleteById(1L);
//
//        addressBookService.deleteContact(1L);
//        verify(addressBookRepository, times(1)).deleteById(1L);
//    }

//    @Test
//    void testDeleteContact_Success() {
//        when(addressBookRepository.findById(1L)).thenReturn(Optional.of(mockEntry));
//        doNothing().when(addressBookRepository).deleteById(1L);
//
//        addressBookService.deleteContact(1L);  // Just call the method, no return value
//        verify(addressBookRepository, times(1)).deleteById(1L);
//    }

//    @Test
//    void testDeleteContact_NonExistingId() {
//        when(addressBookRepository.findById(2L)).thenReturn(Optional.empty());
//
//        addressBookService.deleteContact(2L);  // Just call the method, no return value
//        verify(addressBookRepository, never()).deleteById(anyLong());
//    }
//@Test
//void testDeleteContact_NonExistingId() {
//    when(addressBookRepository.findById(2L)).thenReturn(Optional.empty());
//
//    addressBookService.deleteContact(2L);
//
//    // Ensure deleteById() was never called since contact doesn't exist
//    verify(addressBookRepository, never()).deleteById(anyLong());
//}
}
