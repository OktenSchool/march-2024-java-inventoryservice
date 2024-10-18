package org.okten.inventoryservice.service;

import org.okten.inventoryservice.dto.ProductAvailability;

public interface ProductService {

    void updateProductAvailability(Long productId, ProductAvailability value);
}
