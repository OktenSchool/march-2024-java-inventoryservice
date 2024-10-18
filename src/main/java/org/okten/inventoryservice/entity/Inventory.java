package org.okten.inventoryservice.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@Document("inventories")
public class Inventory {

    @MongoId
    private ObjectId id;

    private Long storeId;

    private Long productId;

    private Integer quantity;
}
