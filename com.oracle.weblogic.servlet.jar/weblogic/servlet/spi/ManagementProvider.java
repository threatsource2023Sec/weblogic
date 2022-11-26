package weblogic.servlet.spi;

import java.util.List;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.WLDFAccessRuntimeMBean;
import weblogic.management.runtime.WLDFPartitionAccessRuntimeMBean;
import weblogic.management.runtime.WebServerRuntimeMBean;
import weblogic.servlet.internal.WebAppServletContext;

public interface ManagementProvider {
   String getServerName();

   DomainMBean getDomainMBean();

   ServerMBean getServerMBean();

   String getDomainRootDir();

   String getServerState();

   WLDFAccessRuntimeMBean getWLDFAccessRuntime();

   WLDFPartitionAccessRuntimeMBean getWLDFPartitionAccessRuntime(String var1);

   boolean isServiceAvailable(String var1);

   boolean isServerInAdminMode();

   boolean isServerInResumingMode();

   boolean isServerSuspendingShuttingDown();

   boolean isServerShuttingDown();

   void registerWebServerRuntime(WebServerRuntimeMBean var1);

   void unregisterWebServerRuntime(WebServerRuntimeMBean var1);

   int getWebServicesConversationSessionCount(WebAppServletContext var1);

   boolean isMemoryLow();

   void handleOutOfMemory(Throwable var1);

   void addWorkManagerRuntimes(ComponentRuntimeMBean var1, ApplicationRuntimeMBean var2, List var3) throws ManagementException;
}
