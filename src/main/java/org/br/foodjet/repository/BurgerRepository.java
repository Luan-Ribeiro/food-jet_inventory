package org.br.foodjet.repository;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.repository.entity.Burger;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@Transactional
public class BurgerRepository {


    public List<Burger> listAll() {
        return Burger.listAll();
    }

    public void save(Burger burger) {
        log.info("Save burger : {}", burger);
        burger.persist();
    }

    public Burger findByName(String burgerName) {
        log.info("Loading Burger by Name: {}", burgerName);
        return Burger.find("name", burgerName).firstResult();
    }

    public void update(Burger burger) {
        burger.persistAndFlush();
    }

}
