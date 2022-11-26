package org.apache.xml.security.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.xml.security.Init;

public class I18n {
   public static final String NOT_INITIALIZED_MSG = "You must initialize the xml-security library correctly before you use it. Call the static method \"org.apache.xml.security.Init.init();\" to do that before you use any functionality from that library.";
   private static ResourceBundle resourceBundle;
   private static boolean alreadyInitialized = false;

   private I18n() {
   }

   public static String translate(String message, Object[] args) {
      return getExceptionMessage(message, args);
   }

   public static String translate(String message) {
      return getExceptionMessage(message);
   }

   public static String getExceptionMessage(String msgID) {
      try {
         return resourceBundle.getString(msgID);
      } catch (Throwable var2) {
         return Init.isInitialized() ? "No message with ID \"" + msgID + "\" found in resource bundle \"" + "org/apache/xml/security/resource/xmlsecurity" + "\"" : "You must initialize the xml-security library correctly before you use it. Call the static method \"org.apache.xml.security.Init.init();\" to do that before you use any functionality from that library.";
      }
   }

   public static String getExceptionMessage(String msgID, Exception originalException) {
      try {
         Object[] exArgs = new Object[]{originalException.getMessage()};
         return MessageFormat.format(resourceBundle.getString(msgID), exArgs);
      } catch (Throwable var3) {
         return Init.isInitialized() ? "No message with ID \"" + msgID + "\" found in resource bundle \"" + "org/apache/xml/security/resource/xmlsecurity" + "\". Original Exception was a " + originalException.getClass().getName() + " and message " + originalException.getMessage() : "You must initialize the xml-security library correctly before you use it. Call the static method \"org.apache.xml.security.Init.init();\" to do that before you use any functionality from that library.";
      }
   }

   public static String getExceptionMessage(String msgID, Object[] exArgs) {
      try {
         return MessageFormat.format(resourceBundle.getString(msgID), exArgs);
      } catch (Throwable var3) {
         return Init.isInitialized() ? "No message with ID \"" + msgID + "\" found in resource bundle \"" + "org/apache/xml/security/resource/xmlsecurity" + "\"" : "You must initialize the xml-security library correctly before you use it. Call the static method \"org.apache.xml.security.Init.init();\" to do that before you use any functionality from that library.";
      }
   }

   public static synchronized void init(String languageCode, String countryCode) {
      if (!alreadyInitialized) {
         resourceBundle = ResourceBundle.getBundle("org/apache/xml/security/resource/xmlsecurity", new Locale(languageCode, countryCode));
         alreadyInitialized = true;
      }
   }

   public static synchronized void init(ResourceBundle resourceBundle) {
      if (!alreadyInitialized) {
         I18n.resourceBundle = resourceBundle;
         alreadyInitialized = true;
      }
   }
}
