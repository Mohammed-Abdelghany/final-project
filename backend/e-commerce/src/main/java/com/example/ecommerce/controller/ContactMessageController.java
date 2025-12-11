package com.example.ecommerce.controller;

import com.example.ecommerce.controller.vm.PageResponse;
import com.example.ecommerce.dto.ContactMessageDto;
import com.example.ecommerce.model.ContactMessage;
import com.example.ecommerce.service.ContactMessageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact-messages")
public class ContactMessageController {
    private final ContactMessageService contactMessageService;

    public ContactMessageController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @PostMapping
    private ResponseEntity<Void> createContactMessage(@RequestBody @Valid ContactMessageDto contactMessageDto) {

        return contactMessageService.saveMessage(contactMessageDto);
    }

    @GetMapping
    private PageResponse<ContactMessageDto> getContactMessages(@Param("page") int page, @Param("size") int size) {
        Page <ContactMessageDto> contactMessages = contactMessageService.getMessages(page, size);
        return new PageResponse<>(
                contactMessages.getContent(),
                contactMessages.getNumber(),
                contactMessages.getTotalPages(),
                contactMessages.getTotalElements(),
                contactMessages.getSize(),
                contactMessages.isFirst(),
                contactMessages.isLast()
        );
    }



}
