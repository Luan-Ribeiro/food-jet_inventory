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
import org.br.foodjet.repository.BurguerInventoryRepository;
import org.br.foodjet.repository.InventoryRepository;
import org.br.foodjet.repository.entity.BurguerInventory;
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
    private final BurguerInventoryRepository burguerInventoryRepository;

    public enum OrderStatusEnum {
        ACCEPTED, RECUSED
    }

    public List<InventoryResponse> listAll() {
        return mapper.toInventoryResponseList(repository.listAll());
    }

    @Transactional
    public InventoryResponse saveIngredientsInInventory(Inventory inventory) {
        if (Objects.isNull(inventory)) {
            return null;
        }

        repository.save(inventory);
        return mapper.toInventoryResponse(inventory);
    }

    @Transactional
    public InventoryResponse update(Long id, Inventory request) {
        if (id == null) {
            return null;
        }

        Inventory inventory = Inventory.findById(id);
        if (inventory == null) {
            throw new BusinessException("Inventory resource not found");
        }

        var requestName = request.getName();
        var requestQuantity = request.getQuantity();

        if (Objects.nonNull(requestName) && Objects.nonNull(requestQuantity)) {
            inventory.setName(requestName);
            inventory.setQuantity(requestQuantity);
        }

        repository.update(inventory);

        return mapper.toInventoryResponse(inventory);
    }

    public InventoryResponse findByName(String clientName) {
        if (clientName == null) {
            return null;
        }

        Inventory listName = repository.findByName(clientName);
        if (Objects.isNull(listName)) {
            throw new BusinessException("Resources not found");
        }

        return mapper.toInventoryResponse(listName);
    }

    @Transactional
    public OrderRequestResponse verifyOrderAndFlushIngredients(OrderRequestTO orderRequestTO) {
        if (Objects.isNull(orderRequestTO) || Objects.isNull(orderRequestTO.items)) {
            throw new BusinessException("Order request is invalid");
        }

        var itemsRequest = orderRequestTO.items;
        List<Inventory> listOfIngredients = new ArrayList<>();
        BigDecimal valueFinal = new BigDecimal(0);

        try {

            for (ItemTO item : itemsRequest) {
                var itemQuantity = item.getQuantity();
                List<BurguerInventory> burguerInventoryList = burguerInventoryRepository.findByName(item.getNameFood());

                if (Objects.isNull(burguerInventoryList)) {
                    throw new BusinessException("Resources not found");
                }

                readAndMakeListOfIngredients(burguerInventoryList, itemQuantity,
                    listOfIngredients);
                valueFinal = valueFinal.add(calculateItemValue(burguerInventoryList, itemQuantity));
            }

            updateListInventory(listOfIngredients);
            return OrderRequestResponse.builder().Status(OrderStatusEnum.ACCEPTED).valueTotal(valueFinal).build();
        } catch (BusinessException e) {
            return OrderRequestResponse.builder().Status(OrderStatusEnum.RECUSED).build();
        }
    }

    private List<Inventory> readAndMakeListOfIngredients(List<BurguerInventory> burguerIngredients,
        BigInteger quantityItem,
        List<Inventory> ingredientsInventoryList) {

        burguerIngredients.forEach(burguerInventory -> {
            var inventory = burguerInventory.getInventory();
            var ingredientInventoryName = inventory.getName();
            var quantityIngredientNecessary = (burguerInventory.getQuantity().multiply(quantityItem));

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

        return ingredientsInventoryList;
    }

    private Inventory verifyIfHaveSufficientIngredient(Inventory inventory, BigInteger quantityFinal) {
        if (quantityFinal.longValue() < 0) {
            throw new BusinessException("Ingredients insufficient");
        }
        inventory.setQuantity(quantityFinal);
        return inventory;
    }

    private BigDecimal calculateItemValue(List<BurguerInventory> burguerInventoryList, BigInteger itemQuantity) {
        BigDecimal valueBurguer;
        valueBurguer = burguerInventoryList.get(0).getBurguer().getValue();

        return (valueBurguer.multiply(new BigDecimal(itemQuantity)));
    }

    private void updateListInventory(List<Inventory> inventoryList) {
        inventoryList.forEach(repository::update);
        log.info("Inventory Updated");
    }
}
