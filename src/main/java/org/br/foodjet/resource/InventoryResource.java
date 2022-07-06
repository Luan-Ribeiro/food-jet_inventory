package org.br.foodjet.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.math.BigInteger;
import java.net.URI;
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
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.br.foodjet.exception.to.ErrorDetailTO;
import org.br.foodjet.entity.Inventory;
import org.br.foodjet.resource.response.InventoryResponse;
import org.br.foodjet.resource.response.OrderRequestResponse;
import org.br.foodjet.resource.to.OrderRequestTO;
import org.br.foodjet.service.InventoryService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;


@Path("/inventory")
@RequiredArgsConstructor
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Tag(name = "Inventory", description = "Manage Inventory")
public class InventoryResource {

    private final InventoryService service;

    @GET
    @Path("/all")
    @APIResponse(responseCode = "200", description = "Gets all ingredients", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = InventoryResponse.class)
    ))
    @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @Operation(
        summary = "getAll",
        description = "Get all ingredients"
    )
    public List<InventoryResponse> getAll() {
        return service.listAll();
    }

    @POST
    @APIResponse(responseCode = "201", description = "Saved ingredient", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = InventoryResponse.class)
    ))
    @APIResponse(responseCode = "400", description = "Bad Request", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @Operation(
        summary = "saveIngrendiet",
        description = "Save one ingredient in inventory"
    )
    public Response save(@NotNull @Valid Inventory request) {
        InventoryResponse inventoryResponse = service.saveIngredientsInInventory(request);
        return Response.created(URI.create("/inventory" + inventoryResponse.getId())).entity(inventoryResponse).build();
    }

    @PUT
    @Path("/ingredients")
    @APIResponse(responseCode = "200", description = "Verified completed", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = OrderRequestResponse.class)
    ))
    @APIResponse(responseCode = "400", description = "Bad Request", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @Operation(
        summary = "verifyOrderAndFlushIngredients",
        description = "Verify if accept order is possible and update ingredients of inventory"
    )
    public OrderRequestResponse verifyOrderAndFlushIngredients(@NotNull @Valid OrderRequestTO orderRequestTO) {
        return service.verifyOrderAndFlushIngredients(orderRequestTO);
    }

    @PATCH
    @Path("/{id}")
    @APIResponse(responseCode = "200", description = "Updated", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = InventoryResponse.class)
    ))
    @APIResponse(responseCode = "400", description = "Bad Request", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @Operation(
        summary = "updateById",
        description = "Update ingredient quantity"
    )
    public InventoryResponse updateInventoryById(@NotNull @PathParam("id") Long id,
        @NotNull @QueryParam("newQuantityItem") BigInteger itemQuantity) {
        return service.update(id, itemQuantity);
    }

    @GET
    @APIResponse(responseCode = "200", description = "Gets ingredient", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = InventoryResponse.class)
    ))
    @APIResponse(responseCode = "404", description = "Ingredient name not found", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @Operation(
        summary = "getByName",
        description = "Get ingredient by name"
    )
    public InventoryResponse getByName(@NotBlank @QueryParam("ingredientName") String ingredientName) {
        return service.findByName(ingredientName);
    }
}
