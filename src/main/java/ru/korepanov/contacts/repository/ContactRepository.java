package ru.korepanov.contacts.repository;


import ru.korepanov.contacts.jooq.db.tables.pojos.Contacts;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс для работы с базой данных
 */
public interface ContactRepository {

    /**
     * @return все контакты в базе данных
     */
    List<Contacts> findAllContacts();

    /**
     * @param id uuid контакта
     * @return объект контакта
     */
    Contacts findContactById(UUID id);

    /**
     * @param contact объект контакта
     */
    void saveContact(Contacts contact);

    /**
     * @param contact объект контакта
     */
    void updateContact(Contacts contact);

    /**
     * @param id uuid контакта
     */
    void deleteContactById(UUID id);

    /**
     * @param contacts список контактов
     */
    void batchInsertContacts(List<Contacts> contacts);

    /**
     * @return количество контактов
     */
    long getCountContacts();

    /**
     * @param id uuid
     * @return содержится ли контакт с заданным id в базе данных
     */
    boolean existsContact(UUID id);
}
