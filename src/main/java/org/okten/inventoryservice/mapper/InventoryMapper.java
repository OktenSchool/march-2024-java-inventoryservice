package org.okten.inventoryservice.mapper;

import org.mapstruct.Mapper;
import org.okten.inventoryservice.dto.InventoryDto;
import org.okten.inventoryservice.entity.Inventory;

@Mapper
public interface InventoryMapper {

    Inventory mapToInventory(InventoryDto inventoryDto);

    InventoryDto mapToDto(Inventory inventory);
}
