package org.br.foodjet.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.repository.entity.Burger;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class BurgerRepository implements PanacheRepository<Burger> {


    public List<Burger> listAll() {
        return findAll().list();
    }

    public void save(Burger burger) {
        log.info("Save burger : {}", burger);
        persist(burger);
    }

    public Burger findByName(String burgerName) {
        log.info("Loading Burger by Name: {}", burgerName);
        return find("name", burgerName).firstResult();
    }

}
