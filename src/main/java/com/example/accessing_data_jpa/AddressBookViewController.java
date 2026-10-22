package com.example.accessing_data_jpa;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AddressBookViewController {

    private final AddressBookRepository addressBookRepository;

    public AddressBookViewController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @GetMapping("/addressbooks/{id}")
    public String viewAddressBook(@PathVariable Integer id, Model model) {
        AddressBook book = addressBookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No address book " + id));
        model.addAttribute("addressBook", book);
        return "addressBook";
    }
}
