package weblogic.management.runtime;

public interface PageFlowRuntimeMBean extends RuntimeMBean {
   /** @deprecated */
   @Deprecated
   String getClassName();

   /** @deprecated */
   @Deprecated
   long getCreateCount();

   /** @deprecated */
   @Deprecated
   long getDestroyCount();

   /** @deprecated */
   @Deprecated
   PageFlowActionRuntimeMBean[] getActions();

   /** @deprecated */
   @Deprecated
   PageFlowActionRuntimeMBean getAction(String var1);

   /** @deprecated */
   @Deprecated
   void reset();

   /** @deprecated */
   @Deprecated
   long getLastResetTime();

   /** @deprecated */
   @Deprecated
   void setNumExceptionsToKeep(int var1);
}
