package ru.korepanov.contacts.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.korepanov.contacts.jooq.db.tables.pojos.Contacts;
import ru.korepanov.contacts.repository.ContactRepository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с контактами.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ContactsService {

    private final ContactRepository repository;

    /**
     * @return Возвращает количество контактов.
     */
    public long getCountContacts() {
        log.info("-> getCountContacts()");
        return repository.getCountContacts();
    }

    /**
     * Находит контакт по ID.
     *
     * @param id контакта
     * @return найденный контакт или null, если контакт не найден
     */
    public Contacts findContactById(UUID id) {
        log.info("-> findContactById({})", id);
        return repository.findContactById(id);
    }

    /**
     * Возвращает список всех контактов, отсортированный по имени.
     *
     * @return список контактов, отсортированный по имени
     */
    public List<Contacts> findAllContacts() {
        log.info("-> findAllContacts()");
        List<Contacts> contacts = repository.findAllContacts();
        contacts.sort((Comparator.comparing(Contacts::getFirstname)));
        return contacts;
    }

    /**
     * @param contact Сохраняет контакт.
     */
    public void saveContact(Contacts contact) {
        log.info("-> saveContact(): {}", contact);
        repository.saveContact(contact);
    }

    /**
     * @param contact контакт для обновления
     */
    public void updateContact(Contacts contact) {
        log.info("-> updateContact(): {}", contact);
        repository.updateContact(contact);
    }

    /**
     * @param id Удаляет контакт по ID.
     */
    public void deleteContact(UUID id) {
        log.info("-> findContactById({})", id);
        repository.deleteContactById(id);
    }

    /**
     * @param id контакта
     * @return true, если контакт существует, иначе false
     */
    public boolean hasContactById(UUID id) {
        log.info("-> hasContactById({})", id);
        return repository.existsContact(id);
    }
}
