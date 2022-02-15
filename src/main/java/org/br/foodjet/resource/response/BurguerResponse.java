package org.br.foodjet.resource.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;
import lombok.Data;
import org.br.foodjet.repository.entity.BurguerInventory;
import org.br.foodjet.repository.entity.Inventory;

@Data
@RegisterForReflection
public class BurguerResponse {

    public Long id;
    public String name;
}
