package org.br.foodjet.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.br.foodjet.exception.to.ErrorDetailTO;
import org.br.foodjet.resource.response.BurgerResponse;
import org.br.foodjet.resource.to.BurgerTO;
import org.br.foodjet.service.BurgerService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.jboss.resteasy.reactive.ResponseStatus;

@Path("/burger")
@RequiredArgsConstructor
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Tag(name = "Burger", description = "Manage Burgers")
public class BurgerResource {

    private final BurgerService service;

    @GET
    @Path("/all")
    @APIResponse(responseCode = "200", description = "Gets all burgers", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = BurgerResponse.class)
    ))
    @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @Operation(
        summary = "getAll",
        description = "Get all burger"
    )
    public List<BurgerResponse> getAll() {
        return service.listAll();
    }

    @POST
    @ResponseStatus(value = HttpStatus.SC_CREATED)
    @APIResponse(responseCode = "201", description = "Saved Burger", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = BurgerResponse.class)
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
        summary = "saveBurger",
        description = "Save burger in inventory"
    )
    public Response save(@NotNull @Valid BurgerTO request) {
        return service.save(request);
    }

    @GET
    @APIResponse(responseCode = "200", description = "Gets a burger", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = BurgerResponse.class)
    ))
    @APIResponse(responseCode = "404", description = "Not found", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @Operation(
        summary = "getBurgerByName",
        description = "Get burger by name"
    )
    public BurgerResponse findByName(@NotBlank @QueryParam("nameBurger") String nameBurger) {
        return service.findByName(nameBurger);
    }
}
