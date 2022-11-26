package com.bea.security.providers.xacml;

public class StoredRoleCollectionInfo {
   private String name;
   private String version;
   private String timeStamp;

   public StoredRoleCollectionInfo(String name, String version, String timeStamp) {
      this.name = name;
      this.version = version;
      this.timeStamp = timeStamp;
   }

   public String getName() {
      return this.name;
   }

   public String getVersion() {
      return this.version;
   }

   public String getTimestamp() {
      return this.timeStamp;
   }
}
