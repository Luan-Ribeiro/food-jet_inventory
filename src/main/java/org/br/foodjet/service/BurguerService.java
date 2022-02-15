package org.br.foodjet.service;

import java.util.List;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.exception.BusinessException;
import org.br.foodjet.repository.BurguerInventoryRepository;
import org.br.foodjet.repository.BurguerRepository;
import org.br.foodjet.repository.entity.Burguer;
import org.br.foodjet.repository.entity.BurguerInventory;
import org.br.foodjet.repository.entity.Inventory;
import org.br.foodjet.resource.response.BurguerInventoryResponse;
import org.br.foodjet.resource.response.BurguerResponse;
import org.br.foodjet.resource.to.BurguerDTO;
import org.br.foodjet.service.mapper.BurguerInventoryMapper;
import org.br.foodjet.service.mapper.BurguerMapper;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class BurguerService {

    private final BurguerRepository repository;
    private final BurguerInventoryRepository burguerInventoryRepository;
    private final BurguerMapper burguerMapper;
    private final BurguerInventoryMapper burguerInventoryMapper;

    public List<BurguerResponse> listAll() {
        return burguerMapper.toBurguerResponseList(repository.listAll());
    }


    public BurguerResponse save(BurguerDTO burguer) {
        if (Objects.isNull(burguer)) {
            return null;
        }

        repository.save(burguer.getBurguer());

        for(Inventory bi : burguer.getInventory()){
            BurguerInventory biBuilder = BurguerInventory.builder()
                .burguer(burguer.getBurguer())
                .quantity(bi.getQuantity())
                .inventory(bi).build();

            burguerInventoryRepository.save(biBuilder);
        }

       return burguerMapper.toBurguerResponse(burguer.getBurguer());
    }


//    @Transactional
//    public BurguerResponse update(Long id, Inventory to) {
//        if (id == null) {
//            return null;
//        }
//
//        Burguer burguer = Burguer.findById(id);
//        if (burguer == null) {
//            throw new BusinessException("Inventory resource not found");
//        }
//
//        if (Objects.nonNull(to.name)) {
//            burguer.setName(to.name);
//        }
//
//        if (Objects.nonNull(to.quantity)) {
//            burguer.setQuantity(to.quantity);
//        }
//
//        repository.update(burguer);
//
//        return burguerMapper.toBurguerResponse(burguer);
//    }

    public List<BurguerInventoryResponse> findById(Long id) {
        if (id == null) {
            return null;
        }

        List<BurguerInventory> burguer = burguerInventoryRepository.findById(id);
        if (Objects.isNull(burguer)) {
            throw new BusinessException("Resources not found");
        }

        return burguerInventoryMapper.toResponseList(burguer);
    }
}
