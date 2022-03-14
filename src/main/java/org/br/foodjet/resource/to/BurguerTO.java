package org.br.foodjet.resource.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.br.foodjet.repository.entity.Burguer;
import org.br.foodjet.repository.entity.Inventory;

@RegisterForReflection
@Data
public class BurguerTO {

    @Valid
    @NotNull
    private Burguer burguer;

    @Valid
    @NotNull
    private List<Inventory> inventory;
}
