package org.br.foodjet.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.math.BigInteger;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.br.foodjet.repository.entity.Inventory;
import org.br.foodjet.resource.response.InventoryResponse;
import org.br.foodjet.resource.response.OrderRequestResponse;
import org.br.foodjet.resource.to.OrderRequestTO;
import org.br.foodjet.service.InventoryService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.jboss.resteasy.reactive.ResponseStatus;


@Path("/inventory")
@RequiredArgsConstructor
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class InventoryResource {

    private final InventoryService service;

    @GET
    @Path("/all")
    @Tag(name = "Inventory", description = "FoodJet")
    public List<InventoryResponse> getAll() {
        return service.listAll();
    }

    @POST
    @ResponseStatus(value = HttpStatus.SC_CREATED)
    @Tag(name = "Inventory", description = "FoodJet")
    public InventoryResponse save(@NotNull @Valid Inventory request) {
        return service.saveIngredientsInInventory(request);
    }

    @PUT
    @Path("/ingredients")
    @Tag(name = "Inventory", description = "FoodJet")
    public OrderRequestResponse verifyOrderAndFlushIngredients(@NotNull @Valid OrderRequestTO orderRequestTO) {
        return service.verifyOrderAndFlushIngredients(orderRequestTO);
    }

    @PATCH
    @Path("/{id}")
    @Tag(name = "Inventory", description = "FoodJet")
    public InventoryResponse updateInventoryById(@NotNull @PathParam("id") Long id,
        @NotNull @QueryParam("newQuantityItem") BigInteger itemQuantity) {
        return service.update(id, itemQuantity);
    }

    @GET
    @Tag(name = "Inventory", description = "FoodJet")
    public InventoryResponse getByName(@NotBlank @QueryParam("ingredientName") String ingredientName) {
        return service.findByName(ingredientName);
    }
}
