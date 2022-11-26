package weblogic.management.runtime;

import java.util.Map;
import java.util.Set;
import weblogic.management.DeploymentException;

public interface WebAppComponentRuntimeMBean extends ComponentConcurrentRuntimeMBean {
   String getApplicationIdentifier();

   String getName();

   String getComponentName();

   String getContextRoot();

   String getConfiguredContextRoot();

   String getModuleURI();

   String getStatus();

   String getSourceInfo();

   ServletRuntimeMBean[] getServlets();

   EJBRuntimeMBean[] getEJBRuntimes();

   EJBRuntimeMBean getEJBRuntime(String var1);

   JaxRsApplicationRuntimeMBean[] getJaxRsApplications();

   JaxRsApplicationRuntimeMBean lookupJaxRsApplication(String var1);

   WebsocketApplicationRuntimeMBean getWebsocketApplicationRuntimeMBean();

   int getOpenSessionsCurrentCount();

   int getOpenSessionsHighCount();

   int getSessionsOpenedTotalCount();

   Set getAllServletSessions();

   /** @deprecated */
   @Deprecated
   ServletSessionRuntimeMBean[] getServletSessions();

   /** @deprecated */
   @Deprecated
   ServletSessionRuntimeMBean getServletSession(String var1);

   String[] getServletSessionsMonitoringIds();

   void invalidateServletSession(String var1) throws IllegalStateException;

   long getSessionLastAccessedTime(String var1) throws IllegalStateException;

   long getSessionMaxInactiveInterval(String var1) throws IllegalStateException;

   String getMonitoringId(String var1);

   void deleteInvalidSessions();

   int getSessionTimeoutSecs();

   int getSessionInvalidationIntervalSecs();

   int getSessionIDLength();

   String getSessionCookieComment();

   String getSessionCookieDomain();

   String getSessionCookiePath();

   String getSessionCookieName();

   int getSessionCookieMaxAgeSecs();

   boolean isFilterDispatchedRequestsEnabled();

   boolean isIndexDirectoryEnabled();

   int getServletReloadCheckSecs();

   int getSingleThreadedServletPoolSize();

   boolean isSessionMonitoringEnabled();

   boolean isJSPKeepGenerated();

   boolean isJSPVerbose();

   boolean isJSPDebug();

   long getJSPPageCheckSecs();

   String getJSPCompileCommand();

   String getLogFilename();

   void registerServlet(String var1, String var2, String[] var3, Map var4, int var5) throws DeploymentException;

   void registerFilter(String var1, String var2, String[] var3, String[] var4, Map var5, String[] var6) throws DeploymentException;

   LogRuntimeMBean getLogRuntime();

   LibraryRuntimeMBean[] getLibraryRuntimes();

   PageFlowsRuntimeMBean getPageFlows();

   void setSpringRuntimeMBean(SpringRuntimeMBean var1);

   SpringRuntimeMBean getSpringRuntimeMBean();

   /** @deprecated */
   @Deprecated
   KodoPersistenceUnitRuntimeMBean[] getKodoPersistenceUnitRuntimes();

   /** @deprecated */
   @Deprecated
   KodoPersistenceUnitRuntimeMBean getKodoPersistenceUnitRuntime(String var1);

   PersistenceUnitRuntimeMBean[] getPersistenceUnitRuntimes();

   PersistenceUnitRuntimeMBean getPersistenceUnitRuntime(String var1);

   WebPubSubRuntimeMBean getWebPubSubRuntime();

   void setWebPubSubRuntime(WebPubSubRuntimeMBean var1);

   CoherenceClusterRuntimeMBean getCoherenceClusterRuntime();

   void setCoherenceClusterRuntime(CoherenceClusterRuntimeMBean var1);

   WseeClientRuntimeMBean[] getWseeClientRuntimes();

   WseeClientRuntimeMBean lookupWseeClientRuntime(String var1);

   WseeV2RuntimeMBean[] getWseeV2Runtimes();

   WseeV2RuntimeMBean lookupWseeV2Runtime(String var1);

   WseeClientConfigurationRuntimeMBean[] getWseeClientConfigurationRuntimes();

   WseeClientConfigurationRuntimeMBean lookupWseeClientConfigurationRuntime(String var1);
}
