package org.br.foodjet.resource.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigInteger;
import lombok.Data;
import org.br.foodjet.repository.entity.Inventory;

@Data
@RegisterForReflection
public class BurguerInventoryResponse {

    private Long id;
    private BurguerResponse burguer;
    private Inventory inventory;
    private BigInteger quantity;
}
