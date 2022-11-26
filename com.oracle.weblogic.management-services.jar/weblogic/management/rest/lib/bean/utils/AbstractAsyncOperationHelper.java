package weblogic.management.rest.lib.bean.utils;

import java.net.URI;
import org.codehaus.jettison.json.JSONObject;

public abstract class AbstractAsyncOperationHelper implements AsyncOperationHelper {
   public int getDefaultSyncMaxWaitMilliSeconds(InvocationContext opIc) throws Exception {
      return 300000;
   }

   public boolean pendingUserAction(InvocationContext opIc, InvocationContext jobIc, JSONObject jobEntity) throws Exception {
      return false;
   }

   public boolean entityReady(InvocationContext opIc, InvocationContext jobIc, JSONObject jobEntity) throws Exception {
      return true;
   }

   public URI getNewItemUri(InvocationContext asyncIc, InvocationContext jobIc, JSONObject jobEntity) throws Exception {
      return null;
   }
}
