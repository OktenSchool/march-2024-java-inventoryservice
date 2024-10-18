package org.okten.inventoryservice.dto;

import lombok.Builder;

@Builder
public record InventoryDto(Long storeId, Long productId, Integer quantity) {
}
