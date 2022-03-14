package org.br.foodjet.service;

import java.util.List;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.exception.BusinessException;
import org.br.foodjet.repository.BurguerInventoryRepository;
import org.br.foodjet.repository.BurguerRepository;
import org.br.foodjet.repository.entity.Burguer;
import org.br.foodjet.repository.entity.BurguerInventory;
import org.br.foodjet.resource.response.BurguerResponse;
import org.br.foodjet.resource.to.BurguerTO;
import org.br.foodjet.service.mapper.BurguerMapper;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class BurguerService {

    private final BurguerRepository repository;
    private final BurguerInventoryRepository burguerInventoryRepository;
    private final BurguerMapper burguerMapper;

    public List<BurguerResponse> listAll() {
        return burguerMapper.toBurguerResponseList(repository.listAll());
    }


    public BurguerResponse save(BurguerTO burguer) {

        repository.save(burguer.getBurguer());

        burguer.getInventory().forEach(inventory -> {
            BurguerInventory burguerInventory = BurguerInventory.builder()
                .burguer(burguer.getBurguer())
                .quantity(inventory.getQuantity())
                .inventory(inventory).build();

            burguerInventoryRepository.save(burguerInventory);
        });

        return burguerMapper.toBurguerResponse(burguer.getBurguer());
    }

    public BurguerResponse findByName(String nameFood) {

        Burguer burguer = repository.findByName(nameFood);
        if (Objects.isNull(burguer)) {
            throw new BusinessException("Burguer not found");
        }

        return burguerMapper.toBurguerResponse(burguer);
    }
}
