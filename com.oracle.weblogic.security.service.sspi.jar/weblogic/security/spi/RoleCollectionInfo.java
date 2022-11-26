package weblogic.security.spi;

public interface RoleCollectionInfo {
   String getName();

   String getVersion();

   String getTimestamp();

   Resource[] getResourceTypes();
}
