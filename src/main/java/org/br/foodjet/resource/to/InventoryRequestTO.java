package org.br.foodjet.resource.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RegisterForReflection
@RequiredArgsConstructor
public class InventoryRequestTO {

    public String name;
    public String quantity;

}
