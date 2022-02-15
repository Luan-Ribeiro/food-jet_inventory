package org.br.foodjet.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@RegisterForReflection
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "burguer_inventory")
public class BurguerInventory extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "burguer_id", referencedColumnName = "id")
    public Burguer burguer;

    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    public Inventory inventory;

    public Long quantity;

}