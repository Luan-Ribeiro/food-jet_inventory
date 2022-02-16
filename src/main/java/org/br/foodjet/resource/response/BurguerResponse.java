package org.br.foodjet.resource.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class BurguerResponse {

    private Long id;
    private String name;
    private Double value;
}
