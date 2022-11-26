package weblogic.management.rest.lib.bean.utils;

import java.net.URI;
import org.codehaus.jettison.json.JSONObject;

public abstract class AbstractAsyncJobHelper implements AsyncJobHelper {
   public void preStandardize(InvocationContext jobIc, JSONObject jobEntity) throws Exception {
   }

   public void postStandardize(InvocationContext jobIc, JSONObject jobEntity) throws Exception {
   }

   public int getIntervalToPoll(InvocationContext jobIc, JSONObject jobEntity) throws Exception {
      return 1000;
   }

   protected String abortActionName() throws Exception {
      return "cancel";
   }

   public URI getAbortUri(InvocationContext jobIc, JSONObject jobEntity) throws Exception {
      String action = this.abortActionName();
      return action != null ? PathUtils.getSubUri(jobIc, action) : null;
   }
}
