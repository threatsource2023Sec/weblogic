package weblogic.management.runtime;

import weblogic.management.configuration.EJBComponentMBean;

public interface EJBComponentRuntimeMBean extends ComponentConcurrentRuntimeMBean {
   EJBRuntimeMBean[] getEJBRuntimes();

   EJBRuntimeMBean getEJBRuntime(String var1);

   /** @deprecated */
   @Deprecated
   EJBComponentMBean getEJBComponent();

   KodoPersistenceUnitRuntimeMBean[] getKodoPersistenceUnitRuntimes();

   KodoPersistenceUnitRuntimeMBean getKodoPersistenceUnitRuntime(String var1);

   void setSpringRuntimeMBean(SpringRuntimeMBean var1);

   SpringRuntimeMBean getSpringRuntimeMBean();

   CoherenceClusterRuntimeMBean getCoherenceClusterRuntime();

   void setCoherenceClusterRuntime(CoherenceClusterRuntimeMBean var1);

   WseeClientRuntimeMBean[] getWseeClientRuntimes();

   WseeClientRuntimeMBean lookupWseeClientRuntime(String var1);

   WseeV2RuntimeMBean[] getWseeV2Runtimes();

   WseeV2RuntimeMBean lookupWseeV2Runtime(String var1);

   WseeClientConfigurationRuntimeMBean[] getWseeClientConfigurationRuntimes();

   WseeClientConfigurationRuntimeMBean lookupWseeClientConfigurationRuntime(String var1);
}
