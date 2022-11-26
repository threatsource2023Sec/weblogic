package org.apache.taglibs.standard.resources;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Resources {
   private static final String RESOURCE_LOCATION = "org.apache.taglibs.standard.resources.Resources";
   private static ResourceBundle rb = ResourceBundle.getBundle("org.apache.taglibs.standard.resources.Resources");

   public static String getMessage(String name) throws MissingResourceException {
      return rb.getString(name);
   }

   public static String getMessage(String name, Object[] a) throws MissingResourceException {
      String res = rb.getString(name);
      return MessageFormat.format(res, a);
   }

   public static String getMessage(String name, Object a1) throws MissingResourceException {
      return getMessage(name, new Object[]{a1});
   }

   public static String getMessage(String name, Object a1, Object a2) throws MissingResourceException {
      return getMessage(name, new Object[]{a1, a2});
   }

   public static String getMessage(String name, Object a1, Object a2, Object a3) throws MissingResourceException {
      return getMessage(name, new Object[]{a1, a2, a3});
   }

   public static String getMessage(String name, Object a1, Object a2, Object a3, Object a4) throws MissingResourceException {
      return getMessage(name, new Object[]{a1, a2, a3, a4});
   }

   public static String getMessage(String name, Object a1, Object a2, Object a3, Object a4, Object a5) throws MissingResourceException {
      return getMessage(name, new Object[]{a1, a2, a3, a4, a5});
   }

   public static String getMessage(String name, Object a1, Object a2, Object a3, Object a4, Object a5, Object a6) throws MissingResourceException {
      return getMessage(name, new Object[]{a1, a2, a3, a4, a5, a6});
   }
}
