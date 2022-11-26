package weblogic.management.rest.lib.bean.utils;

import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import org.glassfish.admin.rest.utils.JsonUtil;

public class TaskRuntimeAsyncOperationHelper extends AbstractAsyncOperationHelper {
   public void addMessages(InvocationContext opIc, InvocationContext jobIc, JSONObject jobEntity, RestJsonResponseBody opRb) throws Exception {
      if (jobIc.bean() != null) {
         JSONObject error = jobEntity.optJSONObject("taskError");
         if (error != null) {
            String message = error.getString("message");
            if ("failed".equals(JsonUtil.getString(jobEntity, "progress"))) {
               opRb.addFailure(message);
            } else {
               opRb.addWarning(message);
            }
         }

      }
   }
}
