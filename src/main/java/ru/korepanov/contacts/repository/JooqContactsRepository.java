package ru.korepanov.contacts.repository;

import lombok.extern.slf4j.Slf4j;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.korepanov.contacts.jooq.db.tables.daos.ContactsDao;
import ru.korepanov.contacts.jooq.db.tables.pojos.Contacts;

import java.util.List;
import java.util.UUID;

/**
 * Класс, представляющий репозиторий для работы с контактами с использованием библиотеки jOOQ.
 */
@Repository
@Slf4j
public class JooqContactsRepository extends ContactsDao implements ContactRepository {

    /**
     * Объект для выполнения запросов к базе данных с использованием jOOQ.
     */
    private final DSLContext context;

    /**
     * Конфигурация для инициализации jOOQ.
     */
    private final Configuration configuration;

    public JooqContactsRepository(DSLContext context, Configuration configuration) {
        super(configuration);
        this.context = context;
        this.configuration = configuration;
    }

    @Override
    public List<Contacts> findAllContacts() {
        log.info("-> findAllContacts()");
        return findAll();
    }

    @Override
    public Contacts findContactById(UUID id) {
        log.info("-> findContactById({})", id);
        return findById(id);
    }

    @Override
    public void saveContact(Contacts task) {
        log.info("-> saveContact():{}", task);
        insert(task);
    }

    @Override
    public void updateContact(Contacts task) {
        log.info("-> updateContact():{}", task);
        update(task);
    }

    @Override
    public void deleteContactById(UUID id) {
        log.info("-> deleteContactById({})", id);
        deleteById(id);
    }

    @Override
    public void batchInsertContacts(List<Contacts> tasks) {
        log.info("-> batchInsertContacts():{}", tasks);
        insert(tasks);
    }

    @Override
    public long getCountContacts() {
        log.info("-> getCountContacts()");
        return count();
    }

    @Override
    public boolean existsContact(UUID id) {
        log.info("-> existsContact({})", id);
        return existsById(id);
    }
}
