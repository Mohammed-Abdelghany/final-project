package com.example.ecommerce.mapper;


import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class} )

public interface ProductMapper  {

     Product toProduct(ProductDto productDto);
     @Mapping(source = "category", target = "categoryDto")
     ProductDto toProductDto(Product product);

     List<Product> toProductList(List<ProductDto> productDtoList);
     List<ProductDto> toProductDtoList(List<Product> productList);
     @Mapping(target = "id", ignore = true)
     void updateProductFromDto(ProductDto productDto,@MappingTarget Product product);

     void updateProductToDto(Product product,@MappingTarget ProductDto productDto);

}
