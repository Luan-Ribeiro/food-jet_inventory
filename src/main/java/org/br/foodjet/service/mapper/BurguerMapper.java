package org.br.foodjet.service.mapper;

import java.util.List;
import org.br.foodjet.repository.entity.Burguer;
import org.br.foodjet.repository.entity.Inventory;
import org.br.foodjet.resource.response.BurguerResponse;
import org.br.foodjet.resource.response.InventoryResponse;
import org.mapstruct.Mapper;

@Mapper
public interface BurguerMapper {

    BurguerResponse toBurguerResponse(Burguer to);

    List<BurguerResponse> toBurguerResponseList(List<Burguer> to);
}