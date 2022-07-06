package org.br.foodjet.resource.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigInteger;
import lombok.Data;
import org.br.foodjet.entity.Inventory;

@Data
@RegisterForReflection
public class BurgerInventoryResponse {

    private Long id;
    private BurgerResponse burger;
    private Inventory inventory;
    private BigInteger quantity;
}
