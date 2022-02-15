package org.br.foodjet.resource.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;
import lombok.Data;
import org.br.foodjet.repository.entity.Burguer;
import org.br.foodjet.repository.entity.Inventory;

@RegisterForReflection
@Data
public class BurguerDTO {

    public Burguer burguer;

    public List<Inventory> inventory;
}
