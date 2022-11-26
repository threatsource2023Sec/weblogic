package weblogic.transaction.internal;

public final class HealthEvent {
   public static final int TLOG_FAILURE = 1;
   public static final int TLOG_OK = 2;
   public static final int TXMAP_FULL = 3;
   public static final int TXMAP_OK = 4;
   public static final int RESOURCE_UNHEALTHY = 5;
   public static final int RESOURCE_HEALTHY = 6;
   public static final int RESOURCE_UNREGISTERED = 7;
   public static final int LLR_MISSING = 8;
   public static final int LLR_AVAILABLE = 9;
   public static final String JDBCSTORE_FAILURE = "JDBCSTORE_FAILURE";
   public static final String JDBCSTORE_RECOVERED = "JDBCSTORE_RECOVERED";
   private final int type;
   private final String name;
   private final String description;
   private final long timestamp;

   public HealthEvent(int aType, String aName, String aDescription) {
      this.type = aType;
      this.name = aName;
      this.description = aDescription;
      this.timestamp = System.currentTimeMillis();
   }

   public int getType() {
      return this.type;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return o instanceof HealthEvent ? ((HealthEvent)o).getKey().equals(this.getKey()) : false;
      }
   }

   public int hashCode() {
      return this.getKey().hashCode();
   }

   private String getKey() {
      return this.type + this.name + this.timestamp;
   }
}
