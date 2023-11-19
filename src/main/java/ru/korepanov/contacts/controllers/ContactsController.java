package ru.korepanov.contacts.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.korepanov.contacts.repository.model.Contact;
import ru.korepanov.contacts.service.ContactService;

import java.util.List;
import java.util.UUID;

import static ru.korepanov.contacts.controllers.ConstantsModelAttribute.*;


/**
 * Концепция фронт контроллера в архитектуре MVC
 */

@Controller
@Slf4j
@RequiredArgsConstructor
public class ContactsController {

    private final ContactService service;
    public static final String REDIRECT = "redirect:/";

    /**
     * В модель добавляются контакты и количество контактов, для отображения на странице
     *
     * @param model - модель представляет атрибуты, используемые для рендеринга страницы
     * @return имя отображаемой html страницы находящейся по пути src/main/resources/templates
     */
    @GetMapping("/")
    public String index(Model model) {
        log.info("Open index page");
        List<Contact> contacts = service.findAllContact();
        model.addAttribute(CONTACTS, contacts);
        model.addAttribute(NUMBER_OF_CONTACTS, service.getCountContact());
        return "index";
    }

    /**
     * В модель передается операция с контактом для отображения пользователю.
     *
     * @param model модель представляет атрибуты, используемые для рендеринга страницы
     * @param id    UUID пользователя
     * @return имя отображаемой html страницы находящейся по пути src/main/resources/templates
     */
    @GetMapping("/contact/update/{id}")
    public String contactsUpdate(Model model, @PathVariable UUID id) {
        log.info("Open contact page -> update");
        model.addAttribute(OPERATION, "Update");
        model.addAttribute(CONTACT, service.findContactById(id));
        return "contact";
    }

    /**
     * В модель передается контакт с уже сгенерированным id. И операция с контактом для отображения пользователю
     *
     * @param model модель представляет атрибуты, используемые для рендеринга страницы
     * @return имя отображаемой html страницы находящейся по пути src/main/resources/templates
     */
    @GetMapping("/contact/create")
    public String contactsCreate(Model model) {
        log.info("Open contact page -> create");
        Contact contacts = new Contact();
        contacts.setId(UUID.randomUUID());
        model.addAttribute(CONTACT, contacts);
        model.addAttribute(OPERATION, "Create");
        return "contact";
    }

    /**
     * Метод определяет по id, был ли контакт в базе данных.
     * Из ходя из этих данных выбирает метод update/save
     *
     * @param contact объект контакта. Который пользователь ввел в from
     * @return редирект на главную страницу
     */
    @PostMapping("/contact/process")
    public String contactProcess(@ModelAttribute("contact") Contact contact) {
        log.info("Processing -> {}", contact);
        boolean isExist = service.hasContactById(contact.getId());
        if (isExist) {
            log.info("update -> {}", contact);
            service.updateContact(contact);
            return REDIRECT;
        }
        log.info("save -> {}", contact);
        service.saveContact(contact);
        return REDIRECT;
    }

    /**
     * @param id UUID удаляемого пользователя
     * @return редирект на главную страницу
     */
    @GetMapping("/contact/delete/{id}")
    public String contactsDelete(@PathVariable UUID id) {
        log.info("Delete by {}", id);
        service.deleteContact(id);
        return REDIRECT;
    }
}
