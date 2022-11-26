package weblogic.logging;

import com.bea.logging.BaseLogger;
import com.bea.logging.BaseLoggerFactory;
import com.bea.logging.LoggingService;
import com.bea.logging.LoggingServiceManager;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;

public class WLLogManager extends LoggingServiceManager {
   public WLLogManager() {
      super(new BaseLoggerFactory() {
         public BaseLogger createBaseLogger(String loggerName) {
            return new BaseLogger(loggerName) {
               public boolean isLoggable(Level level) {
                  RuntimeAccess runtimeAccess = ServerLoggingInitializer.getRuntimeAccess();
                  if (runtimeAccess != null) {
                     ServerRuntimeMBean serverRuntime = runtimeAccess.getServerRuntime();
                     if (serverRuntime == null) {
                        return super.isLoggable(level);
                     }

                     PartitionRuntimeMBean[] partitions = serverRuntime.getPartitionRuntimes();
                     if (partitions == null || partitions.length == 0) {
                        return super.isLoggable(level);
                     }
                  }

                  ComponentInvocationContextManager compCtxMgr = ComponentInvocationContextManager.getInstance();
                  if (compCtxMgr != null) {
                     ComponentInvocationContext compCtx = compCtxMgr.getCurrentComponentInvocationContext();
                     if (compCtx != null) {
                        String pname = compCtx.getPartitionName();
                        if (pname != null && !pname.isEmpty()) {
                           LogManager plm = LoggingService.getInstance().getPartitionLogManager(pname);
                           String loggerName = this.getName();
                           if (plm != null && loggerName != null) {
                              Logger logger = plm.getLogger(loggerName);
                              if (logger != null) {
                                 return logger.isLoggable(level);
                              }
                           }
                        }
                     }
                  }

                  return super.isLoggable(level);
               }
            };
         }
      });
   }
}
