package org.br.foodjet.resource.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigDecimal;
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

    private OrderStatusEnum Status;

    @JsonInclude(Include.NON_NULL)
    private BigDecimal valueTotal;

    @JsonInclude(Include.NON_EMPTY)
    private String reason;
}
