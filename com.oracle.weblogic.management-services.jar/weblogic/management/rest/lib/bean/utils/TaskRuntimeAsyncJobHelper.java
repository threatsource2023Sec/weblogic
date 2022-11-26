package weblogic.management.rest.lib.bean.utils;

import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.ResponseBody;

public class TaskRuntimeAsyncJobHelper extends AbstractAsyncJobHelper {
   public String getProgress(InvocationContext jobIc, JSONObject jobEntity) throws Exception {
      return jobEntity.getBoolean("running") ? "processing" : jobEntity.getString("progress");
   }

   public void addFailureMessages(ResponseBody rb, InvocationContext jobIc, JSONObject jobEntity) throws Exception {
      JSONObject error = jobEntity.optJSONObject("taskError");
      if (error != null) {
         rb.addFailure(error.getString("message"));
      }

   }
}
