package org.br.foodjet.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.entity.Inventory;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class InventoryRepository implements PanacheRepository<Inventory> {


    public List<Inventory> listAll() {
        return findAll().list();
    }

    public void save(Inventory inventory) {
        log.info("Save request in inventory : {}", inventory);
        persist(inventory);
    }

    public Inventory findByName(String inventoryName) {
        log.info("Loading Inventory by Name: {}", inventoryName);
        return find("name", inventoryName).firstResult();
    }

    public void update(Inventory inventory) {
        persistAndFlush(inventory);
    }

}
