package weblogic.descriptor;

public class BooleanSystemProperty {
   private String name;
   private boolean enabled;

   BooleanSystemProperty(String name) {
      this.name = name;
      this.enabled = Boolean.getBoolean(name);
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }

   public boolean isEnabled() {
      return this.enabled;
   }
}
