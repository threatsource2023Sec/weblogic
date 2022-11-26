package weblogic.cluster;

class CompositeKey {
   public String name;
   public String appId;
   public String partitionName;

   public CompositeKey(String name, String appId, String partitionName) {
      this.name = name;
      this.appId = appId;
      this.partitionName = partitionName;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof CompositeKey)) {
         return false;
      } else {
         CompositeKey key = (CompositeKey)o;
         return this.name.equals(key.name) && (this.appId == null && key.appId == null || this.appId.equals(key.appId)) && (this.partitionName == null && key.partitionName == null || this.partitionName.equals(key.partitionName));
      }
   }

   public int hashCode() {
      if (this.appId == null) {
         return this.partitionName == null ? this.name.hashCode() : this.name.hashCode() ^ this.partitionName.hashCode();
      } else {
         return this.partitionName == null ? this.name.hashCode() ^ this.appId.hashCode() : this.name.hashCode() ^ this.appId.hashCode() ^ this.partitionName.hashCode();
      }
   }
}
