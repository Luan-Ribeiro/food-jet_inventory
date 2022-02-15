package org.br.foodjet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.exception.BusinessException;
import org.br.foodjet.repository.BurguerInventoryRepository;
import org.br.foodjet.repository.BurguerRepository;
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
    private final BurguerRepository burguerRepository;

    public enum OrderStatusEnum {
        ACCEPTED, RECUSED, FINALIZED
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

        List<Inventory> inventoryList = findIngredientsOfBurguerCompose(itemsRequest);

        updateListInventory(inventoryList);

        return new OrderRequestResponse().builder().Status(OrderStatusEnum.ACCEPTED).build();
    }

    private void updateListInventory(List<Inventory> inventoryList) {
        inventoryList.forEach(repository::update);
        log.info("Updated");
    }

    private List<Inventory> findIngredientsOfBurguerCompose(List<ItemTO> items) {
        List<Inventory> listOfIngredients = new ArrayList<>();

        for (ItemTO item : items) {

            var quantityItem = item.getQuantity();

            Long burguerId = burguerRepository.findByName(item.getNameFood()).id;

            List<BurguerInventory> burguerCompose = burguerInventoryRepository.findById(burguerId);
            if (Objects.isNull(burguerCompose)) {
                throw new BusinessException("Resources not found");
            }
            listOfIngredients = readAndMakeListOfIngredients(burguerCompose, quantityItem, listOfIngredients);
        }

        return listOfIngredients;
    }

    private List<Inventory> readAndMakeListOfIngredients(List<BurguerInventory> burguerCompose, int quantityItem,
        List<Inventory> listOfIngredients) {

        for (BurguerInventory burguerInventory : burguerCompose) {
            var ingrendientInventory = burguerInventory.inventory.name;
            var quantity = (burguerInventory.getQuantity() * quantityItem);
            var isPresent = listOfIngredients.stream().anyMatch(inventory -> inventory.name.equals(ingrendientInventory));

            if (!isPresent) {
                var inventory = burguerInventory.getInventory();
                inventory.setQuantity(inventory.getQuantity() - quantity);
                listOfIngredients.add(inventory);
            } else {
                listOfIngredients.stream().filter(inventory -> inventory.name.equals(ingrendientInventory))
                    .findFirst()
                    .ifPresent(inventory -> inventory.setQuantity(inventory.quantity - quantity));
            }
        }

        return listOfIngredients;
    }
}
