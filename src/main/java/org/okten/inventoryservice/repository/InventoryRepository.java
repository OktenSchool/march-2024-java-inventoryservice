package org.okten.inventoryservice.repository;

import org.bson.types.ObjectId;
import org.okten.inventoryservice.entity.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, ObjectId> {

    Optional<Inventory> findByStoreId(Long storeId);

    List<Inventory> findAllByProductId(Long productId);
}
