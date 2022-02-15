package org.br.foodjet.repository;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.repository.entity.Burguer;
import org.br.foodjet.repository.entity.BurguerInventory;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@Transactional
public class BurguerInventoryRepository {

    public void save(BurguerInventory burguer) {
        log.info("Save burguer : {}", burguer);
        burguer.persist();
    }

    public List<BurguerInventory> findById(Long burguerId) {
        log.info("Loading Burguer by id: {}", burguerId);
        return BurguerInventory.find("burguer_id",burguerId).list();
    }


}
