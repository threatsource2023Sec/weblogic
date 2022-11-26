package weblogic.diagnostics.debugpatch;

class DebugPatchInfo {
   private String patch;
   private String appName;
   private String moduleName;
   private String partitionName;
   private String encodedName;

   DebugPatchInfo(String patch, String appName, String moduleName, String partitionName) {
      this.patch = patch;
      this.appName = appName;
      this.moduleName = moduleName;
      this.partitionName = partitionName;
   }

   public String getPatch() {
      return this.patch;
   }

   public String getAppName() {
      return this.appName;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public String toString() {
      if (this.encodedName == null) {
         this.encodedName = getEncodedName(this.patch, this.appName, this.moduleName, this.partitionName);
      }

      return this.encodedName;
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public boolean equals(Object o) {
      if (!(o instanceof DebugPatchInfo)) {
         return false;
      } else {
         String o_enc = ((DebugPatchInfo)o).toString();
         return o_enc.equals(this.toString());
      }
   }

   public static String getEncodedName(String patch, String appName, String moduleName, String partitionName) {
      StringBuffer buf = new StringBuffer();
      buf.append(patch).append(":");
      if (appName == null) {
         buf.append("system");
      } else {
         buf.append("app=").append(appName);
         if (moduleName != null) {
            buf.append(",").append(moduleName);
         }
      }

      return buf.toString();
   }
}
