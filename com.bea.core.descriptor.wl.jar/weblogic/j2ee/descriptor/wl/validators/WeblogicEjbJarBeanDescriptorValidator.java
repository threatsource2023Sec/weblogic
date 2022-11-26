package weblogic.j2ee.descriptor.wl.validators;

import java.util.HashSet;
import java.util.Set;

public class WeblogicEjbJarBeanDescriptorValidator {
   private static final String JMS_INITIALCONTEXT_FATORY = "weblogic.jms.WLInitialContextFactory";
   static Set validWarnings = new HashSet();

   private WeblogicEjbJarBeanDescriptorValidator() {
   }

   public static void validateDisableWarnings(String[] values) {
      if (values != null && values.length != 0) {
         for(int i = 0; i < values.length; ++i) {
            String value = values[i];
            if (!validWarnings.contains(value)) {
               throw new IllegalArgumentException(value + " is not a legal value for the disable-warning element");
            }
         }

      }
   }

   public static void validateMDBInitialContextFactory(String initialContextFactory) throws IllegalArgumentException {
      if (initialContextFactory != null && initialContextFactory.length() != 0) {
         if (initialContextFactory.equals("weblogic.jms.WLInitialContextFactory")) {
            throw new IllegalArgumentException("The InitialContextFactory weblogic.jms.WLInitialContextFactory is not supported by MDBs because it enables object based security");
         }
      }
   }

   static {
      validWarnings.add("BEA-010054");
      validWarnings.add("BEA-010202");
      validWarnings.add("BEA-010001");
      validWarnings.add("BEA-010200");
   }
}
