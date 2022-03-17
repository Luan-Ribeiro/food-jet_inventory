package org.br.foodjet.resource.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigDecimal;
import lombok.Data;

@Data
@RegisterForReflection
public class BurgerResponse {

    private Long id;
    private String name;
    private BigDecimal value;
}
