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
import org.glassfish.admin.rest.composite.CompositeResource;
import weblogic.management.rest.lib.bean.utils.BeanType;
import weblogic.management.rest.lib.bean.utils.DescriptorUtils;

public abstract class RootBeanResource extends AbstractBeanResource {
   protected void copyContext(CompositeResource other) {
      super.copyContext(other);

      try {
         this.init(this.getRootBean(), (JSONObject)null);
      } catch (Throwable var3) {
         throw var3 instanceof RuntimeException ? (RuntimeException)var3 : new RuntimeException(var3);
      }
   }

   protected abstract Object getRootBean() throws Exception;

   @POST
   @Consumes({"application/json"})
   @Produces({"application/json"})
   public Response jaxrsPost(JSONObject entity) throws Exception {
      return this.update(entity);
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

   public Response search(JSONObject query) throws Exception {
      this.init(this.invocationContext().bean(), query);
      return this.get();
   }

   protected String getIdentityAsString() throws Exception {
      String type = BeanType.getBeanType(this.getRequest(), this.getBean()).getName();
      type.substring(type.lastIndexOf(".") + 1);
      String beanType = type.substring(0, type.length() - "MBean".length());
      return DescriptorUtils.getRestName(beanType);
   }
}
