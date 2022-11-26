package weblogic.management.rest.lib.bean.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

public abstract class CustomSingletonChildCreateFormResource extends AbstractCustomCreateFormResource {
   protected String getPropertyName() throws Exception {
      return this.getSingularPropertyName();
   }

   @OPTIONS
   @Produces({"application/json"})
   public Response jaxrsOptions() throws Exception {
      return this.options();
   }

   @GET
   @Produces({"application/json"})
   public Response jaxrsGet() throws Exception {
      return this.get();
   }

   @POST
   @Produces({"application/json"})
   public Response jaxrsPost() throws Exception {
      return this.post();
   }

   @PUT
   @Produces({"application/json"})
   public Response jaxrsPut() throws Exception {
      return this.put();
   }

   @DELETE
   @Produces({"application/json"})
   public Response jaxrsDelete() throws Exception {
      return this.delete();
   }
}
