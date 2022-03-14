package org.br.foodjet.resource.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigInteger;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@RegisterForReflection
public class ItemTO {

    @NotBlank
    private String nameFood;

    @NotNull
    private BigInteger quantity;

}
