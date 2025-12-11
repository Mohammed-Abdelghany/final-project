package com.example.ecommerce.service.imp;

import com.example.ecommerce.dto.ContactMessageDto;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.helper.Pagination;
import com.example.ecommerce.helper.UserAuthenticated;
import com.example.ecommerce.mapper.ContactMessageMapper;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.model.ContactMessage;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.ContactMessageRepo;
import com.example.ecommerce.repo.UserRepo;
import com.example.ecommerce.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ContactMessageServiceImp implements ContactMessageService {
    private final ContactMessageMapper contactMessageMapper;
    private final ContactMessageRepo contactMessageRepo;
    private final UserRepo userRepo;
    private final UserMapper userMapper ;

    @Autowired
    public ContactMessageServiceImp( ContactMessageMapper contactMessageMapper, ContactMessageRepo contactMessageRepo, UserMapper userMapper, UserRepo userRepo) {
        this.contactMessageMapper = contactMessageMapper;
        this.contactMessageRepo = contactMessageRepo;
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }
    @Override
    @Transactional
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
    public Page<ContactMessageDto> getMessages(int page, int size) {
       Page<ContactMessage> contactMessages = contactMessageRepo.findAllWithUser(Pagination.getPageRequest(page, size));
       contactMessages.forEach(contactMessage -> {
              User user = contactMessage.getUser();
              if (user != null) {
                contactMessage.setUser(user);
              }
       });
         return contactMessages.map(contactMessageMapper::toContactMessageDto);

    }
}

