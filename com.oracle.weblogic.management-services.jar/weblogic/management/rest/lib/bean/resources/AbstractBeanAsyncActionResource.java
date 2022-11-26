package weblogic.management.rest.lib.bean.resources;

import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import weblogic.management.rest.lib.bean.utils.AsyncOperationHandler;
import weblogic.management.rest.lib.bean.utils.AsyncOperationHelper;

public abstract class AbstractBeanAsyncActionResource extends BeanActionResource {
   protected Response act(JSONObject params) throws Exception {
      RestJsonResponseBody asyncRb = this._act(params);
      if (asyncRb == null) {
         throw this.notFound();
      } else {
         return AsyncOperationHandler.getResponse(this.invocationContext(), asyncRb, this.createAsyncOperationHelper());
      }
   }

   protected abstract AsyncOperationHelper createAsyncOperationHelper() throws Exception;

   protected boolean supportsPost() throws Exception {
      return true;
   }
}
