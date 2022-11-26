package weblogic.management.configuration;

public interface COMMBean extends ConfigurationMBean {
   String getNTAuthHost();

   void setNTAuthHost(String var1);

   boolean isNativeModeEnabled();

   void setNativeModeEnabled(boolean var1);

   boolean isVerboseLoggingEnabled();

   void setVerboseLoggingEnabled(boolean var1);

   boolean isMemoryLoggingEnabled();

   void setMemoryLoggingEnabled(boolean var1);

   boolean isPrefetchEnums();

   void setPrefetchEnums(boolean var1);

   boolean isApartmentThreaded();

   void setApartmentThreaded(boolean var1);
}
