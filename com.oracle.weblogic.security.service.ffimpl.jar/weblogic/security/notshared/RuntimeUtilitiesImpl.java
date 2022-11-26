package weblogic.security.notshared;

import org.jvnet.hk2.annotations.Service;
import weblogic.security.shared.LoggerAdapter;
import weblogic.security.shared.RuntimeUtilities;
import weblogic.security.shared.SecurityPlatformID;

@Service
public class RuntimeUtilitiesImpl implements RuntimeUtilities {
   LoggerAdapterImpl loggerAdapter = null;
   SecurityPlatformIDImpl securityPlatformId = null;

   public RuntimeUtilitiesImpl() {
      this.loggerAdapter = new LoggerAdapterImpl();
      this.securityPlatformId = new SecurityPlatformIDImpl();
   }

   public LoggerAdapter getLoggerAdapter() {
      return this.loggerAdapter;
   }

   public SecurityPlatformID getSecurityPlatformID() {
      return this.securityPlatformId;
   }
}
