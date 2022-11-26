package weblogic.cache;

public class KeyTuple {
   private String key;
   private String scope;

   public void setKey(String key) {
      this.key = key;
   }

   public String getKey() {
      return this.key;
   }

   public void setScope(String scope) {
      this.scope = scope;
   }

   public String getScope() {
      return this.scope;
   }
}
