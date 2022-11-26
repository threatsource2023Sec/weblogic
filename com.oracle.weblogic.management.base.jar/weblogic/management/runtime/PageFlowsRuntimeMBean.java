package weblogic.management.runtime;

public interface PageFlowsRuntimeMBean extends RuntimeMBean {
   /** @deprecated */
   @Deprecated
   String getServerName();

   /** @deprecated */
   @Deprecated
   String getHttpServerName();

   /** @deprecated */
   @Deprecated
   String getContextPath();

   /** @deprecated */
   @Deprecated
   String getAppName();

   /** @deprecated */
   @Deprecated
   PageFlowRuntimeMBean[] getPageFlows();

   /** @deprecated */
   @Deprecated
   PageFlowRuntimeMBean getPageFlow(String var1);

   /** @deprecated */
   @Deprecated
   void reset();
}
