package com.example.ecommerce.service;

import com.example.ecommerce.dto.ContactMessageDto;
import com.example.ecommerce.model.ContactMessage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContactMessageService {
    ResponseEntity<Void> saveMessage(ContactMessageDto contactMessageDto);
    List<ContactMessageDto> getMessages();

}
