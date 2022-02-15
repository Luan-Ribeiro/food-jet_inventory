package org.br.foodjet.service.mapper;

import java.util.List;
import org.br.foodjet.repository.entity.Inventory;
import org.br.foodjet.resource.response.InventoryResponse;
import org.mapstruct.Mapper;

@Mapper
public interface InventoryMapper {

    InventoryResponse toInventoryResponse(Inventory to);

    List<InventoryResponse> toInventoryResponseList(List<Inventory> to);
}