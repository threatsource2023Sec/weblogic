package weblogic.management.rest.lib.bean.resources;

import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import weblogic.management.rest.lib.bean.utils.AsyncJobHelper;
import weblogic.management.rest.lib.bean.utils.AsyncJobUtils;
import weblogic.management.rest.lib.bean.utils.QueryUtils;

public abstract class AbstractJobSingletonChildBeanResource extends SingletonChildBeanResource {
   public RestJsonResponseBody getRB() throws Exception {
      JSONObject originalQuery = this.invocationContext().query();
      JSONObject allPropertiesQuery = QueryUtils.getAllPropertiesQuery(originalQuery);

      RestJsonResponseBody var6;
      try {
         this.invocationContext().setQuery(allPropertiesQuery);
         RestJsonResponseBody rb = super.getRB();
         JSONObject entity = rb.getEntity();
         AsyncJobHelper helper = this.createAsyncJobHelper();
         AsyncJobUtils.standardizeResponse(this.invocationContext(), helper, entity, rb);
         QueryUtils.getPropertiesFilter(this.invocationContext().request(), originalQuery).newScope().trim(entity);
         var6 = rb;
      } finally {
         this.invocationContext().setQuery(originalQuery);
      }

      return var6;
   }

   protected abstract AsyncJobHelper createAsyncJobHelper() throws Exception;
}
