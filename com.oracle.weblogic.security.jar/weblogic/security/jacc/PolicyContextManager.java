package weblogic.security.jacc;

import javax.security.jacc.PolicyContext;
import weblogic.kernel.ThreadLocalStack;

public class PolicyContextManager {
   private static final ThreadLocalStack threadContextStorage = new ThreadLocalStack(true);
   private static final ThreadLocalStack threadContextIdStorage = new ThreadLocalStack(true);

   public static void setPolicyContext(PolicyContextHandlerData pchData) {
      threadContextStorage.push(pchData);
      PolicyContext.setHandlerData(pchData);
   }

   public static void setContextID(String id) {
      threadContextIdStorage.push(id);
      PolicyContext.setContextID(id);
   }

   public static void resetPolicyContext() {
      threadContextStorage.pop();
      PolicyContextHandlerData pchPrev = (PolicyContextHandlerData)threadContextStorage.get();
      PolicyContext.setHandlerData(pchPrev);
   }

   public static void resetContextID() {
      threadContextIdStorage.pop();
      String idPrev = (String)threadContextIdStorage.get();
      PolicyContext.setContextID(idPrev);
   }
}
