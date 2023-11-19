package ru.korepanov.contacts.repository;

import ru.korepanov.contacts.repository.model.Contact;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс для работы с базой данных
 */
public interface ContactRepository {

    /**
     * @return все контакты в базе данных
     */
    List<Contact> findAllContact();

    /**
     * @param id uuid контакта
     * @return объект контакта
     */
    Contact findContactById(UUID id);

    /**
     * @param contact объект контакта
     */
    void saveContact(Contact contact);

    /**
     * @param contact объект контакта
     */
    void updateContact(Contact contact);

    /**
     * @param id uuid контакта
     */
    void deleteContactById(UUID id);

    /**
     * @param Contact список контактов
     */
    void batchInsertContact(List<Contact> contact);

    /**
     * @return количество контактов
     */
    long getCountContact();

    /**
     * @param id uuid
     * @return содержится ли контакт с заданным id в базе данных
     */
    boolean existsContact(UUID id);
}
