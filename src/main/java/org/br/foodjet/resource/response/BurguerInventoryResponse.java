package org.br.foodjet.resource.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import org.br.foodjet.repository.entity.Inventory;

@Data
@RegisterForReflection
public class BurguerInventoryResponse {

    public Long id;
    public BurguerResponse burguer;
    public Inventory inventory;
    public Long quantity;
}
