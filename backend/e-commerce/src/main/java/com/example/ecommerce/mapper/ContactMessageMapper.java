package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.ContactMessageDto;
import com.example.ecommerce.model.ContactMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ContactMessageMapper {

    ContactMessage toContactMessage(ContactMessageDto dto);

    ContactMessageDto toContactMessageDto(ContactMessage contactMessage);

}
