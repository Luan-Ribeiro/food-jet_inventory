package org.br.foodjet.resource;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.br.foodjet.repository.entity.Inventory;
import org.br.foodjet.resource.response.InventoryResponse;
import org.br.foodjet.resource.response.OrderRequestResponse;
import org.br.foodjet.resource.to.OrderRequestTO;
import org.br.foodjet.service.InventoryService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.jboss.resteasy.reactive.ResponseStatus;

@RequiredArgsConstructor
@Path("/inventory")
public class InventoryResource {

    private final InventoryService service;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Inventory", description = "FoodJet")
    public List<InventoryResponse> getAll() {
        return service.listAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(value = HttpStatus.SC_CREATED)
    @Tag(name = "Inventory", description = "FoodJet")
    public InventoryResponse save(Inventory request) {
        return service.saveIngredientsInInventory(request);
    }

    @PUT
    @Path("/ingredients")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Inventory", description = "FoodJet")
    public OrderRequestResponse verifyOrderAndFlushIngredients(@RequestBody OrderRequestTO orderRequestTO) {
        return service.verifyOrderAndFlushIngredients(orderRequestTO);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Inventory", description = "FoodJet")
    public InventoryResponse updateInventoryById(@PathParam("id") Long id, @RequestBody Inventory inventoryRequestTO) {
        return service.update(id, inventoryRequestTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Inventory", description = "FoodJet")
    public InventoryResponse getByName(@Valid @NotBlank @QueryParam("ingredient_name") String ingredientName) {
        return service.findByName(ingredientName);
    }
}
