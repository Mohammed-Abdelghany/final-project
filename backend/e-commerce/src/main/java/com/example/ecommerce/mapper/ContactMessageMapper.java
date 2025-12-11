package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.ContactMessageDto;
import com.example.ecommerce.model.ContactMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ContactMessageMapper {
    @Mapping(source = "userDto", target = "user")
    ContactMessage toContactMessage(ContactMessageDto dto);
    @Mapping(source = "user", target = "userDto")

    ContactMessageDto toContactMessageDto(ContactMessage contactMessage);

}
