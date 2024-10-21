package org.okten.springdemo.dto;

import lombok.Builder;

// У цьому прикладі ProductDeletedEvent повинен лежати у тому ж самому package що і в ProductService (event producer)
@Builder
public record ProductDeletedEvent(Long productId) {
}
