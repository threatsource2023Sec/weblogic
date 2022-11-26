package weblogic.management.configuration;

public interface CoherencePartitionCacheConfigMBean extends ConfigurationMBean {
   boolean isShared();

   void setShared(boolean var1);

   String getCacheName();

   void setCacheName(String var1);

   String getApplicationName();

   void setApplicationName(String var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   CoherencePartitionCachePropertyMBean[] getCoherencePartitionCacheProperties();

   CoherencePartitionCachePropertyMBean lookupCoherencePartitionCacheProperty(String var1);

   CoherencePartitionCachePropertyMBean createCoherencePartitionCacheProperty(String var1);

   void destroyCoherencePartitionCacheProperty(CoherencePartitionCachePropertyMBean var1);
}
