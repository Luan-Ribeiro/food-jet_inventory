package org.br.foodjet.resource.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;
import javax.validation.Valid;
import lombok.Data;

@Data
@RegisterForReflection
public class OrderRequestTO {

    @Valid
    private List<ItemTO> items;
}
