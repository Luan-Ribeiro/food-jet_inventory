package org.br.foodjet.service.mapper;

import java.util.List;
import org.br.foodjet.entity.Burger;
import org.br.foodjet.resource.response.BurgerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi")
public interface BurgerMapper {

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "name", source = "name"),
        @Mapping(target = "value", source = "value")
    })
    BurgerResponse toBurgerResponse(Burger to);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "name", source = "name"),
        @Mapping(target = "value", source = "value")
    })
    List<BurgerResponse> toBurgerResponseList(List<Burger> to);
}