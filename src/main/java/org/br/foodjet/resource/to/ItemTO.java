package org.br.foodjet.resource.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigInteger;
import lombok.Data;

@Data
@RegisterForReflection
public class ItemTO {

    private String nameFood;

    private BigInteger quantity;

}
