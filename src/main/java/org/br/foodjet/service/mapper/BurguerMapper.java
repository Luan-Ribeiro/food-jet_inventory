package org.br.foodjet.service.mapper;

import java.util.List;
import org.br.foodjet.repository.entity.Burguer;
import org.br.foodjet.resource.response.BurguerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface BurguerMapper {

    BurguerResponse toBurguerResponse(Burguer to);

    List<BurguerResponse> toBurguerResponseList(List<Burguer> to);
}