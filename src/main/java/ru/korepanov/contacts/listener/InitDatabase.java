package ru.korepanov.contacts.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.korepanov.contacts.jooq.db.tables.pojos.Contacts;
import ru.korepanov.contacts.repository.JooqContactsRepository;

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

    private final JooqContactsRepository jooqContactsRepository;

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
        jooqContactsRepository.findAll().forEach(contacts -> jooqContactsRepository.deleteById(contacts.getId()));
        jooqContactsRepository.insert(generateContacts(Integer.parseInt(countContacts)));
    }

    /**
     * @param n количество контактов
     * @return рандомные контакты
     */
    private List<Contacts> generateContacts(int n) {
        List<Contacts> contactsList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            Contacts c = new Contacts(
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

