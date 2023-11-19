package ru.korepanov.contacts.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;
import ru.korepanov.contacts.repository.mapper.ContactRowMapper;
import ru.korepanov.contacts.repository.model.Contact;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Класс, представляющий репозиторий для работы с контактами с использованием библиотеки jOOQ.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class JdbcTemplateRepository implements ContactRepository {


    /**
     * Объект для выполнения запросов к базе данных.
     */
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Contact> findAllContact() {
        log.info("-> findAllContact()");
        String sql = "SELECT * FROM contacts";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Contact findContactById(UUID id) {
        log.info("-> findContactById({})", id);

        String sql = "SELECT * FROM contacts WHERE id = ?";

        return DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
                ));
    }

    @Override
    public void saveContact(Contact task) {
        log.info("-> saveContact():{}", task);

        task.setId(UUID.randomUUID());
        String sql = "INSERT INTO contacts (id, firstName, lastName, email, phone) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                task.getId(),
                task.getFirstname(),
                task.getLastname(),
                task.getEmail(),
                task.getPhone());
    }

    @Override
    public void updateContact(Contact task) {
        log.info("-> updateContact():{}", task);

        Contact contact = findContactById(task.getId());

        if (contact != null) {
            String sql = "UPDATE contacts SET firstName = ?, lastName = ?, email= ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    task.getFirstname(),
                    task.getLastname(),
                    task.getEmail(),
                    task.getPhone(),
                    task.getId());
        }
    }

    @Override
    public void deleteContactById(UUID id) {
        log.info("-> deleteContactById({})", id);

        String sql = "DELETE FROM contacts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void batchInsertContact(List<Contact> contacts) {
        log.info("-> batchInsertContact():{}", contacts);
        String sql = "INSERT INTO contacts (id, firstName, lastName, email, phone) VALUES (?::uuid, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(
                sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Contact contact = contacts.get(i);
                        ps.setString(1, contact.getId().toString());
                        ps.setString(2, contact.getFirstname());
                        ps.setString(3, contact.getLastname());
                        ps.setString(4, contact.getEmail());
                        ps.setString(5, contact.getPhone());
                    }

                    @Override
                    public int getBatchSize() {
                        return contacts.size();
                    }
                }
        );
    }

    @Override
    public long getCountContact() {
        log.info("-> getCountContact()");
        return findAllContact().size();
    }

    @Override
    public boolean existsContact(UUID id) {
        log.info("-> existsContact({})", id);
        return findContactById(id) != null;
    }
}
