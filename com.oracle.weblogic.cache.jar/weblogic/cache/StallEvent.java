package weblogic.cache;

public class StallEvent {
   private int stallTime;
   private String scope;
   private String key;

   public StallEvent(int stallTime, String scope, String key) {
      this.stallTime = stallTime;
      this.scope = scope;
      this.key = key;
   }

   public int getStallTime() {
      return this.stallTime;
   }

   public String getScope() {
      return this.scope;
   }

   public String getKey() {
      return this.key;
   }
}
