package ru.korepanov.contacts.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.korepanov.contacts.repository.ContactRepository;
import ru.korepanov.contacts.repository.model.Contact;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с контактами.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository repository;

    /**
     * @return Возвращает количество контактов.
     */
    public long getCountContact() {
        log.info("-> getCountContact()");
        return repository.getCountContact();
    }

    /**
     * Находит контакт по ID.
     *
     * @param id контакта
     * @return найденный контакт или null, если контакт не найден
     */
    public Contact findContactById(UUID id) {
        log.info("-> findContactById({})", id);
        return repository.findContactById(id);
    }

    /**
     * Возвращает список всех контактов, отсортированный по имени.
     *
     * @return список контактов, отсортированный по имени
     */
    public List<Contact> findAllContact() {
        log.info("-> findAllContact()");
        List<Contact> contacts = repository.findAllContact();
        contacts.sort((Comparator.comparing(Contact::getFirstname)));
        return contacts;
    }

    /**
     * @param contact Сохраняет контакт.
     */
    public void saveContact(Contact contact) {
        log.info("-> saveContact(): {}", contact);
        repository.saveContact(contact);
    }

    /**
     * @param contact контакт для обновления
     */
    public void updateContact(Contact contact) {
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
