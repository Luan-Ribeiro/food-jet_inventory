package org.br.foodjet.repository.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@RegisterForReflection
@Entity
@Table(name = "inventory")
public class  Inventory extends PanacheEntityBase {

    @Id
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "quantity")
    public Long quantity;
}
