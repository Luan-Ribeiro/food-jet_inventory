package org.br.foodjet.service.mapper;

import java.util.List;
import org.br.foodjet.repository.entity.BurguerInventory;
import org.br.foodjet.resource.response.BurguerInventoryResponse;
import org.mapstruct.Mapper;

@Mapper
public interface BurguerInventoryMapper {

    BurguerInventoryResponse toResponse(BurguerInventory to);

    List<BurguerInventoryResponse> toResponseList(List<BurguerInventory> to);
}