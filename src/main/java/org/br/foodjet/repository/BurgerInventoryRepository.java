package org.br.foodjet.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
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

public class BurgerInventoryRepository implements PanacheRepository<BurgerInventory> {

    public void save(BurgerInventory burgerInventory) {
        persist(burgerInventory);
    }

    public List<BurgerInventory> findByName(String burgerName) {
        log.info("Loading Burger by name: {}", burgerName);
        return getEntityManager().createNativeQuery("select id,quantity,burger_id,inventory_id from burger_inventory\n"
                    + "where burger_id in (select id from burger where name = '" + burgerName + "')",
                BurgerInventory.class)
            .getResultList();
    }
}
