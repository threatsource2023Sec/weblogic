package weblogic.management.configuration;

import com.bea.core.descriptor.wl.descriptorWlLogger;

public class LogicalStoreNameValidator {
   public static void validateDefaultLogicalStoreName(WebServicePersistenceMBean wspb, String name) throws IllegalArgumentException {
      WebServiceLogicalStoreMBean[] createdLogicalStores = wspb.getWebServiceLogicalStores();
      if (null != createdLogicalStores && createdLogicalStores.length > 0 && wspb.lookupWebServiceLogicalStore(name) == null) {
         throw new IllegalArgumentException("The store " + name + " does not exist");
      } else {
         validateLogicalStoreName("DefaultLogicalStoreName", name);
      }
   }

   public static void validateLogicalStoreName(WebServicePersistenceMBean wspb, String name) throws IllegalArgumentException {
      validateLogicalStoreName("Name", name);
   }

   private static void validateLogicalStoreName(String attr, String name) throws IllegalArgumentException {
      boolean valid = !isEmpty(name);
      if (valid) {
         valid = Character.isJavaIdentifierStart(name.charAt(0));

         for(int i = 1; i < name.length(); ++i) {
            valid &= Character.isJavaIdentifierPart(name.charAt(i));
         }
      }

      if (!valid) {
         throw new IllegalArgumentException(descriptorWlLogger.logLogicalStoreNameNotValidLoggable(attr, name).getMessage());
      }
   }

   private static boolean isEmpty(String s) {
      return s == null || s.trim().length() == 0;
   }
}
