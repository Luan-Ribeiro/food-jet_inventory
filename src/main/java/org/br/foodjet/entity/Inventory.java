package org.br.foodjet.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@RegisterForReflection
@Entity
@Table(name = "inventory")
@Schema(example = """
    {
          "id": 1,
          "name": "bun",
          "quantity": 500
    }
    """)
public class Inventory extends PanacheEntityBase {

    @Id
    @NotNull
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Column(name = "quantity")
    private BigInteger quantity;
}
