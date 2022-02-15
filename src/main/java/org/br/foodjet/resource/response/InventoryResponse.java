package org.br.foodjet.resource.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class InventoryResponse {

    public Long id;
    public String name;
    public Long quantity;
}
