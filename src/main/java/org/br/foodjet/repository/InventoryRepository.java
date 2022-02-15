package org.br.foodjet.repository;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.repository.entity.Inventory;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@Transactional
public class InventoryRepository {


    public List<Inventory> listAll() {
        return Inventory.listAll();
    }

    public void save(Inventory inventory) {
        log.info("Save request in inventory : {}", inventory);
        inventory.persist();
    }

    public Inventory findByName(String inventoryName) {
        log.info("Loading Inventory by Name: {}", inventoryName);
        return Inventory.find("name", inventoryName).firstResult();
    }

    public void update(Inventory inventory) {
        inventory.persistAndFlush();
    }

}
