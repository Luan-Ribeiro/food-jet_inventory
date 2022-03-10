package org.br.foodjet.repository;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.repository.entity.BurguerInventory;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@Transactional
public class BurguerInventoryRepository {

    private final EntityManager entityManager;

    public void save(BurguerInventory burguer) {
        log.info("Save burguer : {}", burguer);
        burguer.persist();
    }

    public List<BurguerInventory> findByName(String burguerName) {
        log.info("Loading Burguer by name: {}", burguerName);
        return entityManager.createNativeQuery("select id,quantity,burguer_id,inventory_id from burguer_inventory\n"
                    + "where burguer_id in (select id from burguer where name = '" + burguerName + "')",
                BurguerInventory.class)
            .getResultList();
    }
}
