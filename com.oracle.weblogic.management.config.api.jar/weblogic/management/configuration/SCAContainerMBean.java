package weblogic.management.configuration;

public interface SCAContainerMBean extends ConfigurationMBean {
   int getTimeout();

   void setTimeout(int var1);

   boolean isAutowire();

   void setAutowire(boolean var1);

   boolean isAllowsPassByReference();

   void setAllowsPassByReference(boolean var1);

   boolean isRemotable();

   void setRemotable(boolean var1);

   String getMaxIdleTime();

   void setMaxIdleTime(String var1);

   String getMaxAge();

   void setMaxAge(String var1);

   boolean isSinglePrincipal();

   void setSinglePrincipal(boolean var1);
}
