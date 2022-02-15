package org.br.foodjet.resource.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;
import lombok.Data;

@Data
@RegisterForReflection
public class OrderRequestTO {

    public List<ItemTO> items;
}
