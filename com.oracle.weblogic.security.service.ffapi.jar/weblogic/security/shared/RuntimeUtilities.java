package weblogic.security.shared;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface RuntimeUtilities {
   LoggerAdapter getLoggerAdapter();

   SecurityPlatformID getSecurityPlatformID();
}
