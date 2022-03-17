package org.br.foodjet.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.exception.BusinessException;
import org.br.foodjet.repository.BurgerInventoryRepository;
import org.br.foodjet.repository.BurgerRepository;
import org.br.foodjet.repository.InventoryRepository;
import org.br.foodjet.repository.entity.Burger;
import org.br.foodjet.repository.entity.BurgerInventory;
import org.br.foodjet.resource.response.BurgerResponse;
import org.br.foodjet.resource.to.BurgerTO;
import org.br.foodjet.service.mapper.BurgerMapper;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class BurgerService {

    private final BurgerRepository repository;
    private final InventoryRepository inventoryRepository;
    private final BurgerInventoryRepository burgerInventoryRepository;
    private final BurgerMapper burgerMapper;

    public List<BurgerResponse> listAll() {
        return burgerMapper.toBurgerResponseList(repository.listAll());
    }


    public Response save(BurgerTO burger) {
        if (ingrendientsHasValid(burger)) {
            repository.save(burger.getBurger());

            burger.getInventory().forEach(inventory -> {
                BurgerInventory burgerInventory = BurgerInventory.builder()
                    .burger(burger.getBurger())
                    .quantity(inventory.getQuantity())
                    .inventory(inventory).build();

                burgerInventoryRepository.save(burgerInventory);
            });

            BurgerResponse burgerResponse = burgerMapper.toBurgerResponse(burger.getBurger());
            return Response.created(URI.create("/burger" + burgerResponse.getId())).entity(burgerResponse).build();
        }
        throw new BusinessException("Ingredient invalid, please verify");
    }

    private boolean ingrendientsHasValid(BurgerTO burger) {
        var ingredientInventory = inventoryRepository.listAll();
        List<Long> invalidIngredient = new ArrayList<>();

        burger.getInventory().forEach(inventoryBurger -> {
            var isValid = ingredientInventory.stream()
                .anyMatch(ingredientsInventory -> ingredientsInventory.getId().equals(inventoryBurger.getId()));
            if (!isValid) {
                invalidIngredient.add(inventoryBurger.getId());
            }
        });

        return invalidIngredient.isEmpty();
    }

    public BurgerResponse findByName(String nameFood) {

        Burger burger = repository.findByName(nameFood);
        if (Objects.isNull(burger)) {
            throw new BusinessException("Burger not found");
        }

        return burgerMapper.toBurgerResponse(burger);
    }
}
