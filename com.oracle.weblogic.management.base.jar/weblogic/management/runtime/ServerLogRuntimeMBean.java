package weblogic.management.runtime;

import java.util.Map;

public interface ServerLogRuntimeMBean extends LogRuntimeMBean {
   Map getLoggedMessagesCountbySeverity();
}
