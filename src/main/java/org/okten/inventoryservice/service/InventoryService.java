package org.okten.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.okten.inventoryservice.dto.InventoryDto;
import org.okten.inventoryservice.dto.ProductAvailability;
import org.okten.inventoryservice.entity.Inventory;
import org.okten.inventoryservice.mapper.InventoryMapper;
import org.okten.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    private final InventoryMapper inventoryMapper;

    private final ProductService productService;

    public List<InventoryDto> getProductInventories(Long productId) {
        return inventoryRepository
                .findAllByProductId(productId)
                .stream()
                .map(inventoryMapper::mapToDto)
                .toList();
    }

    public void updateInventory(InventoryDto inventoryDto) {
        inventoryRepository
                .findByStoreIdAndProductId(inventoryDto.storeId(), inventoryDto.productId())
                .ifPresentOrElse(
                        existingInventory -> {
                            log.info("Updating inventory for store '{}'", inventoryDto.storeId());
                            existingInventory.setQuantity(inventoryDto.quantity());
                            inventoryRepository.save(existingInventory);
                        },
                        () -> {
                            log.info("Creating new inventory for store '{}'", inventoryDto.storeId());
                            inventoryRepository.save(inventoryMapper.mapToInventory(inventoryDto));
                        });

        log.info("Checking other stores with product '{}'", inventoryDto.productId());
        int totalProductCount = inventoryRepository
                .findAllByProductId(inventoryDto.productId())
                .stream()
                .mapToInt(Inventory::getQuantity)
                .sum();
        log.info("The product '{}' has {} quantity", inventoryDto.productId(), totalProductCount);

        if (totalProductCount == 0) {
            log.info("Updating product status to OUT_OF_STOCK...");
            productService.updateProductAvailability(inventoryDto.productId(), ProductAvailability.OUT_OF_STOCK);
        } else {
            log.info("Updating product status to AVAILABLE...");
            productService.updateProductAvailability(inventoryDto.productId(), ProductAvailability.AVAILABLE);
        }
    }

    public void deleteInventoryOfProduct(Long productId) {
        inventoryRepository.deleteInventoryByProductId(productId);
    }
}
