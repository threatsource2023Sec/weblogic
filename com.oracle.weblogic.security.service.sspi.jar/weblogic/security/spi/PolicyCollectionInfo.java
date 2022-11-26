package weblogic.security.spi;

public interface PolicyCollectionInfo {
   String getName();

   String getVersion();

   String getTimestamp();

   Resource[] getResourceTypes();
}
