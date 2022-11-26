package weblogic.work.concurrent.context;

import java.util.Map;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.ContextServiceImpl;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;

public class StateCheckerProvider implements ContextProvider {
   private static final long serialVersionUID = -4178663477607681475L;
   private final String contextServiceID;
   private final String contextServiceInfo;

   public StateCheckerProvider(String contextServiceID, String contextServiceInfo) {
      this.contextServiceID = contextServiceID;
      this.contextServiceInfo = contextServiceInfo;
   }

   public String getContextType() {
      return "internal";
   }

   public int getConcurrentObjectType() {
      return 1;
   }

   public ContextHandle save(Map executionProperties) {
      return null;
   }

   public ContextHandle setup(ContextHandle contextHandle) throws IllegalStateException {
      if (!ContextServiceImpl.isStarted(this.contextServiceID)) {
         throw new IllegalStateException(ConcurrencyLogger.logStateCheckerFailedLoggable(this.contextServiceInfo).getMessage());
      } else {
         return null;
      }
   }

   public void reset(ContextHandle contextHandle) {
   }
}
