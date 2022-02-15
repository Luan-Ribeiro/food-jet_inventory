package org.br.foodjet.resource.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class InventoryResponse {

    private Long id;
    private String name;
    private Long quantity;
}
