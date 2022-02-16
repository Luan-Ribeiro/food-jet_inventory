package org.br.foodjet.resource;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.br.foodjet.resource.response.BurguerResponse;
import org.br.foodjet.resource.to.BurguerTO;
import org.br.foodjet.service.BurguerService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.jboss.resteasy.reactive.ResponseStatus;

@RequiredArgsConstructor
@Path("/burguer")
public class BurguerResource {

    private final BurguerService service;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Burguer", description = "FoodJet")
    public List<BurguerResponse> getAll() {
        return service.listAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(value = HttpStatus.SC_CREATED)
    @Tag(name = "Burguer", description = "FoodJet")
    public BurguerResponse save(BurguerTO request) {
        return service.save(request);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Burguer", description = "FoodJet")
    public BurguerResponse findByName(@Valid @NotBlank @QueryParam("nameBurguer") String nameBurguer ) {
        return service.findByName(nameBurguer);
    }
}
