package org.br.foodjet.service.mapper;

import java.util.List;
import org.br.foodjet.repository.entity.Burger;
import org.br.foodjet.resource.response.BurgerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface BurgerMapper {

    BurgerResponse toBurgerResponse(Burger to);

    List<BurgerResponse> toBurgerResponseList(List<Burger> to);
}