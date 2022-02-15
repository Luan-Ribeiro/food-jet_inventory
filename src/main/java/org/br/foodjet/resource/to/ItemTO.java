package org.br.foodjet.resource.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class ItemTO {

    private String nameFood;

    private int quantity;

}
