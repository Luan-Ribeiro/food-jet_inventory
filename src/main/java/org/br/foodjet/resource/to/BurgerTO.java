package org.br.foodjet.resource.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.br.foodjet.entity.Burger;
import org.br.foodjet.entity.Inventory;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@RegisterForReflection
@Data
@Schema(example = """
        {
           "burger": {
             "name": "CHESSE_BAURU",
             "value": 40
           },
           "inventory": [
             {
               "id": 1,
               "name": "bun",
               "quantity": 2
             },
             {
               "id": 2,
               "name": "ham",
               "quantity": 2
             },
             {
               "id": 3,
               "name": "slice tomatoes",
               "quantity": 3
             },
             {
               "id": 4,
               "name": "lettuce",
               "quantity": 2
             },
             {
               "id": 5,
               "name": "beef",
               "quantity": 2
             },
             {
               "id": 6,
               "name": "bacon",
               "quantity": 10
             },
             {
               "id": 7,
               "name": "cheese",
               "quantity": 4
             },
             {
               "id": 8,
               "name": "slice egg",
               "quantity": 4
             },
             {
               "id": 9,
               "name": "mayonnaise",
               "quantity": 3
             }
           ]
         }
    """)
public class BurgerTO {

    @Valid
    @NotNull
    private Burger burger;

    @Valid
    @NotNull
    private List<Inventory> inventory;
}
