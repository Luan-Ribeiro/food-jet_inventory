package org.br.foodjet.repository;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.repository.entity.BurgerInventory;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@Transactional
public class BurgerInventoryRepository {

    private final EntityManager entityManager;

    public void save(BurgerInventory burgerInventory) {
        burgerInventory.persist();
    }

    public List<BurgerInventory> findByName(String burgerName) {
        log.info("Loading Burger by name: {}", burgerName);
        return entityManager.createNativeQuery("select id,quantity,burger_id,inventory_id from burger_inventory\n"
                    + "where burger_id in (select id from burger where name = '" + burgerName + "')",
                BurgerInventory.class)
            .getResultList();
    }
}
