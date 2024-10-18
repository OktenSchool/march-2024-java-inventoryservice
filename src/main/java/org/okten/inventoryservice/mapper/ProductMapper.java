package org.okten.inventoryservice.mapper;

import org.mapstruct.Mapper;
import org.okten.inventoryservice.dto.ProductAvailability;
import org.okten.inventoryservice.productapi.client.ProductDto;

@Mapper
public interface ProductMapper {

    ProductDto.AvailabilityEnum mapAvailability(ProductAvailability productAvailability);
}
