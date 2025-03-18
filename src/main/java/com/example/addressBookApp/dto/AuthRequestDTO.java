//UC11
package com.example.addressBookApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // Generates a no-arg constructor
@AllArgsConstructor // Generates a constructor with all fields
public class AuthRequestDTO {
    private String email;
    private String password;
}



//UC10
//package com.example.addressBookApp.dto;
//
//import lombok.Data;
//
//@Data
//public class AuthRequestDTO {
//    private String email;
//    private String password;
//}
