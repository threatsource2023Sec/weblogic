package weblogic.management.configuration;

public class OptionalFeatureValidator {
   public static void validateName(String name) throws IllegalArgumentException {
      if (!"WSAT".equals(name) && !"JAXRPC_ASYNC_RESPONSE".equals(name)) {
         throw new IllegalArgumentException("illegal optional name: " + name);
      }
   }
}
