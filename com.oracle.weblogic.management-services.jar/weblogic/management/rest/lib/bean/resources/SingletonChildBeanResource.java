package weblogic.management.rest.lib.bean.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;

public class SingletonChildBeanResource extends AbstractSingletonChildBeanResource {
   @POST
   @Consumes({"application/json"})
   @Produces({"application/json"})
   public Response jaxrsPost(JSONObject entity) throws Exception {
      return this.post(entity);
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

   @Path("{subResourceName}")
   public Object jaxrsGetSubResource(@PathParam("subResourceName") String subResourceName) throws Exception {
      return this.getSubResource(subResourceName, (JSONObject)null);
   }
}
