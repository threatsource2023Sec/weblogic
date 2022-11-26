package weblogic.management.rest.lib.bean.utils;

import java.net.URI;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;

public interface AsyncOperationHelper {
   int getDefaultSyncMaxWaitMilliSeconds(InvocationContext var1) throws Exception;

   boolean pendingUserAction(InvocationContext var1, InvocationContext var2, JSONObject var3) throws Exception;

   boolean entityReady(InvocationContext var1, InvocationContext var2, JSONObject var3) throws Exception;

   void addMessages(InvocationContext var1, InvocationContext var2, JSONObject var3, RestJsonResponseBody var4) throws Exception;

   URI getNewItemUri(InvocationContext var1, InvocationContext var2, JSONObject var3) throws Exception;
}
