package weblogic.health;

final class MonitoredSystemTableKey {
   private String id;
   private String partition;

   MonitoredSystemTableKey(String id, String partition) {
      this.id = id.trim();
      this.partition = partition.trim();
   }

   String getId() {
      return this.id;
   }

   String getPartition() {
      return this.partition;
   }

   public boolean equals(Object other) {
      if (!(other instanceof MonitoredSystemTableKey)) {
         return false;
      } else {
         MonitoredSystemTableKey otherKey = (MonitoredSystemTableKey)other;
         return this.id.equals(otherKey.id) && this.partition.equals(otherKey.partition);
      }
   }

   public int hashCode() {
      return this.id.hashCode() + 31 * this.partition.hashCode();
   }

   public String toString() {
      return this.id + ":" + this.partition;
   }
}
