package com.octetstring.nls;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
   private static final String BUNDLE_NAME = "com.octetstring.nls.vde";
   private static ResourceBundle RESOURCE_BUNDLE = null;

   private Messages() {
   }

   public static String getString(String key) {
      try {
         if (RESOURCE_BUNDLE == null) {
            try {
               RESOURCE_BUNDLE = ResourceBundle.getBundle("com.octetstring.nls.vde");
            } catch (Exception var2) {
               System.err.println("Unable to open resource bundle: com.octetstring.nls.vde");
            }
         }

         return RESOURCE_BUNDLE.getString(key);
      } catch (MissingResourceException var3) {
         return '!' + key + '!';
      }
   }
}
