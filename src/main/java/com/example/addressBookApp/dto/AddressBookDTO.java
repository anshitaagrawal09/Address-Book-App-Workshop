//UC11
package com.example.addressBookApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor   // Required for deserialization
@AllArgsConstructor  // ✅ Add this constructor to fix the issue
public class AddressBookDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Address cannot be empty")
    private String address;
}



//UC10
//package com.example.addressBookApp.dto;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import lombok.Getter;
//import lombok.Setter;
//
//
//@Getter
//@Setter
//public class AddressBookDTO {
//
//    @NotBlank(message = "Name cannot be empty")
//    private String name;
//
//    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
//    private String phoneNumber;
//
//    @Email(message = "Invalid email format")
//    private String email;
//
//    @NotBlank(message = "Address cannot be empty")
//    private String address;
//}