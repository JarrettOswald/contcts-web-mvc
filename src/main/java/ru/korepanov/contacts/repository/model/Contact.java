package ru.korepanov.contacts.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.UUID;

@Data
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
