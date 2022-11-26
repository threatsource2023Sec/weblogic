package weblogic.management.rest.lib.bean.resources;

import javax.ws.rs.core.Response;
import org.glassfish.admin.rest.model.RestJsonResponseBody;

public abstract class AbstractCustomNoArgActionResource extends AbstractCustomBaseActionResource {
   protected Response post() throws Exception {
      this.verifyPost();
      return this.act();
   }

   protected Response act() throws Exception {
      RestJsonResponseBody rb = this._act();
      if (rb == null) {
         throw this.notFound();
      } else {
         return this.acted(rb);
      }
   }

   protected RestJsonResponseBody _act() throws Exception {
      throw new AssertionError("_act must be implemented");
   }
}
