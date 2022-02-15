package org.br.foodjet.resource.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.foodjet.service.InventoryService.OrderStatusEnum;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class OrderRequestResponse {

    public OrderStatusEnum Status;
}