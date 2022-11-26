package weblogic.diagnostics.descriptor.validation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import weblogic.diagnostics.descriptor.WLDFRESTNotificationBean;

public class RESTNotificationBeanValidator {
   private static final WLDFDescriptorValidationTextTextFormatter TXT_FORMATTER = WLDFDescriptorValidationTextTextFormatter.getInstance();

   public static void validateRESTNotificationBean(WLDFRESTNotificationBean restConfig) {
      String authMode = restConfig.getHttpAuthenticationMode();
      if (authMode != null && authMode.equals("Basic")) {
         String userName = restConfig.getHttpAuthenticationUserName();
         String pwd = restConfig.getHttpAuthenticationPassword();
         if (isEmpty(userName) || isEmpty(pwd)) {
            throw new IllegalArgumentException(TXT_FORMATTER.getRestEmptyUserNameorPasswordMsg());
         }
      }

      Properties customProps = restConfig.getCustomNotificationProperties();
      if (customProps != null) {
         try {
            Set keys = customProps.stringPropertyNames();
            Iterator var4 = keys.iterator();

            String key;
            String value;
            do {
               if (!var4.hasNext()) {
                  return;
               }

               key = (String)var4.next();
               value = customProps.getProperty(key);
            } while(!isEmpty(key) && !isEmpty(value));

            throw new IllegalArgumentException(TXT_FORMATTER.getRestEmptyCustoNotificationKeyOrValueMsg());
         } catch (Exception var7) {
            throw new IllegalArgumentException(var7);
         }
      }
   }

   private static boolean isEmpty(String s) {
      return s == null || s.trim().length() == 0;
   }

   public static void validateEndpointURL(String url) {
      try {
         new URL(url);
      } catch (MalformedURLException var2) {
         throw new IllegalArgumentException(var2);
      }
   }
}
