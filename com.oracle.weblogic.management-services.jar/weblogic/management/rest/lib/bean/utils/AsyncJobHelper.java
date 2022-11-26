package weblogic.management.rest.lib.bean.utils;

import java.net.URI;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.ResponseBody;

public interface AsyncJobHelper {
   void preStandardize(InvocationContext var1, JSONObject var2) throws Exception;

   String getProgress(InvocationContext var1, JSONObject var2) throws Exception;

   void postStandardize(InvocationContext var1, JSONObject var2) throws Exception;

   int getIntervalToPoll(InvocationContext var1, JSONObject var2) throws Exception;

   void addFailureMessages(ResponseBody var1, InvocationContext var2, JSONObject var3) throws Exception;

   URI getAbortUri(InvocationContext var1, JSONObject var2) throws Exception;
}
