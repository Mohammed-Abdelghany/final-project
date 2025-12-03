package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.MediaDto;
import com.example.ecommerce.model.Media;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    MediaDto toMediaDto(Media media);
    Media toMedia(MediaDto mediaDto);
}
