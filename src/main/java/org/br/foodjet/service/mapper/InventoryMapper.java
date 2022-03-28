package org.br.foodjet.service.mapper;

import java.util.List;
import org.br.foodjet.repository.entity.Inventory;
import org.br.foodjet.resource.response.InventoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi")
public interface InventoryMapper {

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "name", source = "name"),
        @Mapping(target = "quantity", source = "quantity")
    })
    InventoryResponse toInventoryResponse(Inventory to);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "name", source = "name"),
        @Mapping(target = "quantity", source = "quantity")
    })
    List<InventoryResponse> toInventoryResponseList(List<Inventory> to);
}