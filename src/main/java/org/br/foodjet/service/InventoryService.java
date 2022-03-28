package org.br.foodjet.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.exception.BusinessException;
import org.br.foodjet.repository.BurgerInventoryRepository;
import org.br.foodjet.repository.InventoryRepository;
import org.br.foodjet.repository.entity.BurgerInventory;
import org.br.foodjet.repository.entity.Inventory;
import org.br.foodjet.resource.response.InventoryResponse;
import org.br.foodjet.resource.response.OrderRequestResponse;
import org.br.foodjet.resource.to.ItemTO;
import org.br.foodjet.resource.to.OrderRequestTO;
import org.br.foodjet.service.mapper.InventoryMapper;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository repository;
    private final InventoryMapper mapper;
    private final BurgerInventoryRepository burgerInventoryRepository;

    public enum OrderStatusEnum {
        ACCEPTED, RECUSED
    }

    public List<InventoryResponse> listAll() {
        return mapper.toInventoryResponseList(repository.listAll());
    }

    public InventoryResponse saveIngredientsInInventory(Inventory inventory) {
        repository.save(inventory);
        return mapper.toInventoryResponse(inventory);
    }

    @Transactional
    public InventoryResponse update(Long id, BigInteger newQuantityItem) {

        Inventory inventory = repository.findById(id);
        if (Objects.isNull(inventory)) {
            throw new BusinessException("Inventory resource not found");
        }

        inventory.setQuantity(newQuantityItem);

        repository.update(inventory);

        return mapper.toInventoryResponse(inventory);
    }

    public InventoryResponse findByName(String clientName) {

        Inventory listName = repository.findByName(clientName);
        if (Objects.isNull(listName)) {
            throw new BusinessException("Resources not found");
        }

        return mapper.toInventoryResponse(listName);
    }

    @Transactional
    public OrderRequestResponse verifyOrderAndFlushIngredients(OrderRequestTO orderRequestTO) {

        var itemsRequest = orderRequestTO.getItems();
        List<Inventory> listOfIngredients = new ArrayList<>();
        BigDecimal valueFinal = new BigDecimal(0);

        try {

            for (ItemTO item : itemsRequest) {
                var itemQuantity = item.getQuantity();
                List<BurgerInventory> burgerInventoryList = burgerInventoryRepository.findByName(item.getNameFood());

                if (burgerInventoryList.isEmpty()) {
                    throw new BusinessException("Burger not found in the menu");
                }

                readAndMakeListOfIngredients(burgerInventoryList, itemQuantity,
                    listOfIngredients);
                valueFinal = valueFinal.add(calculateItemValue(burgerInventoryList, itemQuantity));
            }

            updateListInventory(listOfIngredients);
            return OrderRequestResponse.builder().Status(OrderStatusEnum.ACCEPTED).valueTotal(valueFinal).build();
        } catch (BusinessException e) {
            log.info("Order was recused because : {}", e.getMessage());
            return OrderRequestResponse.builder().Status(OrderStatusEnum.RECUSED).reason(e.getMessage()).build();
        }
    }

    private void readAndMakeListOfIngredients(List<BurgerInventory> burgerIngredients,
        BigInteger quantityItem,
        List<Inventory> ingredientsInventoryList) {

        burgerIngredients.forEach(burgerInventory -> {
            var inventory = burgerInventory.getInventory();
            var ingredientInventoryName = inventory.getName();
            var quantityIngredientNecessary = (burgerInventory.getQuantity().multiply(quantityItem));

            ingredientsInventoryList.stream()
                .filter(inventoryOnCache -> inventoryOnCache.getName().equals(ingredientInventoryName))
                .findFirst()
                .ifPresentOrElse(inventoryOnCache -> {
                        var quantityFinal =
                            inventoryOnCache.getQuantity().subtract(quantityIngredientNecessary);
                        verifyIfHaveSufficientIngredient(inventoryOnCache, quantityFinal);
                    },
                    () -> {
                        var quantityFinal = inventory.getQuantity().subtract(quantityIngredientNecessary);
                        ingredientsInventoryList.add(verifyIfHaveSufficientIngredient(inventory, quantityFinal));
                    });
        });
    }

    private Inventory verifyIfHaveSufficientIngredient(Inventory inventory, BigInteger quantityFinal) {
        if (quantityFinal.longValue() < 0) {
            throw new BusinessException("Burger unavailable");
        }
        inventory.setQuantity(quantityFinal);
        return inventory;
    }

    private BigDecimal calculateItemValue(List<BurgerInventory> burgerInventoryList, BigInteger itemQuantity) {
        BigDecimal valueBurger = burgerInventoryList.get(0).getBurger().getValue();
        return (valueBurger.multiply(new BigDecimal(itemQuantity)));
    }

    private void updateListInventory(List<Inventory> inventoryList) {
        inventoryList.forEach(repository::update);
        log.info("Inventory Updated");
    }
}
