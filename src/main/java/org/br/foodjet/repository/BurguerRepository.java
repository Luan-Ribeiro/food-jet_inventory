package org.br.foodjet.repository;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.repository.entity.Burguer;
import org.br.foodjet.repository.entity.Inventory;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@Transactional
public class BurguerRepository {


    public List<Burguer> listAll() {
        return Burguer.listAll();
    }

    public void save(Burguer burguer) {
        log.info("Save burguer : {}", burguer);
        burguer.persist();
    }

    public Burguer findByName(String burguerName) {
        log.info("Loading Burguer by Name: {}", burguerName);
        return Burguer.find("name", burguerName).firstResult();
    }

    public void updateList(List<Burguer> burguerList) {
        if (burguerList == null) {
            return;
        }

        log.info("Update List of Ingredients : {}", burguerList);
        for (Burguer burguer : burguerList) {
            burguer.persistAndFlush();
        }
    }

    public void update(Burguer burguer) {
        burguer.persistAndFlush();
    }

    private void persistIngredients(List<Inventory> ingredients) {
        try {
            if (ingredients == null) {
                return;
            }

            for (Inventory ingredient : ingredients) {
                ingredient.persist();
            }
        } catch (Exception ex) {
//            handleHttpResponse();
        }
    }

}
