package weblogic.management.rest.lib.bean.resources;

import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;

public abstract class AbstractCustomActionResource extends AbstractCustomBaseActionResource {
   protected Response post(JSONObject params) throws Exception {
      this.verifyPost();
      return this.act(params);
   }

   protected Response act(JSONObject params) throws Exception {
      RestJsonResponseBody rb = this._act(params);
      if (rb == null) {
         throw this.notFound();
      } else {
         return this.acted(rb);
      }
   }

   protected RestJsonResponseBody _act(JSONObject params) throws Exception {
      throw new AssertionError("_act must be implemented");
   }
}
