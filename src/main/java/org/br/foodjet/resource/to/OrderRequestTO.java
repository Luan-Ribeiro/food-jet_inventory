package org.br.foodjet.resource.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;
import javax.validation.Valid;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@RegisterForReflection
@Schema(example = """
    {
      "items": [
        {
          "nameFood": "CHESSE_BACON",
          "quantity": 2
        },
        {
          "nameFood": "CHESSE_EGG",
          "quantity": 1
        }
      ]
    }
    """)
public class OrderRequestTO {

    @Valid
    private List<ItemTO> items;
}
