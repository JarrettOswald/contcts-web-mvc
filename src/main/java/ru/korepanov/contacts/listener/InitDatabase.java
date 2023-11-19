package ru.korepanov.contacts.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.korepanov.contacts.repository.JdbcTemplateRepository;
import ru.korepanov.contacts.repository.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс с обработчиком события ApplicationStartedEvent
 */
@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty("app.init.enabled")
class InitDatabase {

    private final JdbcTemplateRepository jdbcTemplateRepository;

    /**
     * Количество контактов которые задаётся в application.yml
     */
    @Value("${app.init.contacts}")
    private String countContacts;

    /**
     * Метод обрабатывающий событие ApplicationStartedEvent.
     * Заполняет базу данных сгенерированными контактами.
     */
    @EventListener(ApplicationStartedEvent.class)
    private void initDataBase() {
        log.info("initDataBase -> contacts: {}", countContacts);
        jdbcTemplateRepository.findAllContact().forEach(contacts -> jdbcTemplateRepository.deleteContactById(contacts.getId()));
        jdbcTemplateRepository.batchInsertContact(generateContacts(Integer.parseInt(countContacts)));
    }

    /**
     * @param n количество контактов
     * @return рандомные контакты
     */
    private List<Contact> generateContacts(int n) {
        List<Contact> contactsList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            Contact c = new Contact(
                    UUID.randomUUID(),
                    "Name: " + i,
                    "Lastname: " + i,
                    "email" + i + "@mail.ru",
                    "88002006" + i
            );
            contactsList.add(c);
        }
        return contactsList;
    }
}

