package weblogic.management.security;

public class ProviderValidator {
   public static void validateProviders(ProviderMBean[] providers) throws IllegalArgumentException {
      if (providers != null && providers.length != 0) {
         for(int i = 0; i < providers.length; ++i) {
            String name0 = providers[i].getName();

            for(int j = i + 1; j < providers.length; ++j) {
               String name1 = providers[j].getName();
               if (name0.equals(name1)) {
                  throw new IllegalArgumentException("The provider " + name0 + " already exists");
               }
            }
         }

      }
   }
}
