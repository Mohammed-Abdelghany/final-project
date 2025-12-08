package com.example.ecommerce.service.imp;

import com.example.ecommerce.dto.ContactMessageDto;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.helper.UserAuthenticated;
import com.example.ecommerce.mapper.ContactMessageMapper;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.model.ContactMessage;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.ContactMessageRepo;
import com.example.ecommerce.repo.UserRepo;
import com.example.ecommerce.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContactMessageServiceImp implements ContactMessageService {
    private final ContactMessageService contactMessageService;
    private final ContactMessageMapper contactMessageMapper;
    private final ContactMessageRepo contactMessageRepo;
   private final UserMapper userMapper;
    private final UserRepo userRepo;

    @Autowired
    public ContactMessageServiceImp(ContactMessageService contactMessageService, ContactMessageMapper contactMessageMapper, ContactMessageRepo contactMessageRepo, UserMapper userMapper, UserRepo userRepo) {
        this.contactMessageService = contactMessageService;
        this.contactMessageMapper = contactMessageMapper;
        this.contactMessageRepo = contactMessageRepo;
        this.userMapper = userMapper;
        this.userRepo = userRepo;
    }
    @Override
    public ResponseEntity<Void> saveMessage(ContactMessageDto contactMessageDto) {
        UserDto userDto = UserAuthenticated.getUserDtoAuthenticated();
        ContactMessage contactMessage = contactMessageMapper.toContactMessage(contactMessageDto);
        User user =userRepo.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("user.notfound"));
        contactMessage.setUser(user);
                contactMessageRepo.save(contactMessage);
        return ResponseEntity.ok().build();
    }


    @Override
    public List<ContactMessageDto> getMessages() {
        return List.of();
    }
}
