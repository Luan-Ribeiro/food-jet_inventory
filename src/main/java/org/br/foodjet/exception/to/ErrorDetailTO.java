package org.br.foodjet.exception.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailTO {

    private String code;
    private String message;

}
