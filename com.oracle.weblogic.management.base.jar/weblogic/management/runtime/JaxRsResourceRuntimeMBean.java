package weblogic.management.runtime;

public interface JaxRsResourceRuntimeMBean extends JaxRsUriRuntimeMBean, JaxRsMonitoringInfoRuntimeMBean {
   /** @deprecated */
   @Deprecated
   String TYPE_POJO = "POJO";
   /** @deprecated */
   @Deprecated
   String TYPE_EJB = "EJB";

   String getClassName();

   String getResourceType();
}
