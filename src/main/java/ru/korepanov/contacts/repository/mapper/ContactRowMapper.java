package ru.korepanov.contacts.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.korepanov.contacts.repository.model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ContactRowMapper implements RowMapper<Contact> {

    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contacts = new Contact();
        contacts.setId(UUID.fromString(rs.getString(Contact.Fields.id)));
        contacts.setFirstname(rs.getString(Contact.Fields.firstname));
        contacts.setLastname(rs.getString(Contact.Fields.lastname));
        contacts.setEmail(rs.getString(Contact.Fields.email));
        contacts.setPhone(rs.getString(Contact.Fields.phone));
        return contacts;
    }
}
