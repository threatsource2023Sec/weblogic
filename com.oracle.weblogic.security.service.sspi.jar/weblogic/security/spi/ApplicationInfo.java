package weblogic.security.spi;

public interface ApplicationInfo {
   String getApplicationIdentifier();

   String getComponentName();

   ComponentType getComponentType();

   public static class ComponentType {
      private int value;
      private String name = null;
      public static final ComponentType APPLICATION = new ComponentType(1);
      public static final ComponentType EJB = new ComponentType(2);
      public static final ComponentType WEBAPP = new ComponentType(3);
      public static final ComponentType CONTROL_RESOURCE = new ComponentType(4);

      private ComponentType(int value) {
         this.value = value;
         if (value == 1) {
            this.name = "Application";
         } else if (value == 2) {
            this.name = "EJB";
         } else if (value == 3) {
            this.name = "WebApp";
         } else if (value == 4) {
            this.name = "ControlResource";
         }

      }

      public String getName() {
         return this.name;
      }

      public String toString() {
         return this.name;
      }
   }
}
