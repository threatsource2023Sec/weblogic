package javax.jdo.spi;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.jdo.JDOFatalInternalException;

public class I18NHelper {
   private static Hashtable bundles = new Hashtable();
   private static Hashtable helpers = new Hashtable();
   private static Locale locale = Locale.getDefault();
   private ResourceBundle bundle = null;
   private Throwable failure = null;
   private static final String bundleSuffix = ".Bundle";

   private I18NHelper() {
   }

   private I18NHelper(String bundleName, ClassLoader loader) {
      try {
         this.bundle = loadBundle(bundleName, loader);
      } catch (Throwable var4) {
         this.failure = var4;
      }

   }

   public static I18NHelper getInstance(String bundleName) {
      return getInstance(bundleName, I18NHelper.class.getClassLoader());
   }

   public static I18NHelper getInstance(final Class cls) {
      ClassLoader classLoader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public ClassLoader run() {
            return cls.getClassLoader();
         }
      });
      String bundle = getPackageName(cls.getName()) + ".Bundle";
      return getInstance(bundle, classLoader);
   }

   public static I18NHelper getInstance(String bundleName, ClassLoader loader) {
      I18NHelper helper = (I18NHelper)helpers.get(bundleName);
      if (helper != null) {
         return helper;
      } else {
         helper = new I18NHelper(bundleName, loader);
         helpers.put(bundleName, helper);
         return (I18NHelper)helpers.get(bundleName);
      }
   }

   public String msg(String messageKey) {
      this.assertBundle(messageKey);
      return getMessage(this.bundle, messageKey);
   }

   public String msg(String messageKey, Object arg1) {
      this.assertBundle(messageKey);
      return getMessage(this.bundle, messageKey, arg1);
   }

   public String msg(String messageKey, Object arg1, Object arg2) {
      this.assertBundle(messageKey);
      return getMessage(this.bundle, messageKey, arg1, arg2);
   }

   public String msg(String messageKey, Object arg1, Object arg2, Object arg3) {
      this.assertBundle(messageKey);
      return getMessage(this.bundle, messageKey, arg1, arg2, arg3);
   }

   public String msg(String messageKey, Object[] args) {
      this.assertBundle(messageKey);
      return getMessage(this.bundle, messageKey, args);
   }

   public String msg(String messageKey, int arg) {
      this.assertBundle(messageKey);
      return getMessage(this.bundle, messageKey, arg);
   }

   public String msg(String messageKey, boolean arg) {
      this.assertBundle(messageKey);
      return getMessage(this.bundle, messageKey, arg);
   }

   public ResourceBundle getResourceBundle() {
      this.assertBundle();
      return this.bundle;
   }

   private static final ResourceBundle loadBundle(String bundleName, ClassLoader loader) {
      ResourceBundle messages = (ResourceBundle)bundles.get(bundleName);
      if (messages == null) {
         if (loader != null) {
            messages = ResourceBundle.getBundle(bundleName, locale, loader);
         } else {
            messages = ResourceBundle.getBundle(bundleName, locale, getSystemClassLoaderPrivileged());
         }

         bundles.put(bundleName, messages);
      }

      return messages;
   }

   private void assertBundle() {
      if (this.failure != null) {
         throw new JDOFatalInternalException("No resources could be found for bundle:\"" + this.bundle + "\" ", this.failure);
      }
   }

   private void assertBundle(String key) {
      if (this.failure != null) {
         throw new JDOFatalInternalException("No resources could be found to annotate error message key:\"" + key + "\"", this.failure);
      }
   }

   private static final String getMessage(ResourceBundle messages, String messageKey) {
      return messages.getString(messageKey);
   }

   private static final String getMessage(ResourceBundle messages, String messageKey, Object[] msgArgs) {
      for(int i = 0; i < msgArgs.length; ++i) {
         if (msgArgs[i] == null) {
            msgArgs[i] = "";
         }
      }

      MessageFormat formatter = new MessageFormat(messages.getString(messageKey));
      return formatter.format(msgArgs);
   }

   private static final String getMessage(ResourceBundle messages, String messageKey, Object arg) {
      Object[] args = new Object[]{arg};
      return getMessage(messages, messageKey, args);
   }

   private static final String getMessage(ResourceBundle messages, String messageKey, Object arg1, Object arg2) {
      Object[] args = new Object[]{arg1, arg2};
      return getMessage(messages, messageKey, args);
   }

   private static final String getMessage(ResourceBundle messages, String messageKey, Object arg1, Object arg2, Object arg3) {
      Object[] args = new Object[]{arg1, arg2, arg3};
      return getMessage(messages, messageKey, args);
   }

   private static final String getMessage(ResourceBundle messages, String messageKey, int arg) {
      Object[] args = new Object[]{new Integer(arg)};
      return getMessage(messages, messageKey, args);
   }

   private static final String getMessage(ResourceBundle messages, String messageKey, boolean arg) {
      Object[] args = new Object[]{String.valueOf(arg)};
      return getMessage(messages, messageKey, args);
   }

   private static final String getPackageName(String className) {
      int index = className.lastIndexOf(46);
      return index != -1 ? className.substring(0, index) : "";
   }

   private static ClassLoader getSystemClassLoaderPrivileged() {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public ClassLoader run() {
            return ClassLoader.getSystemClassLoader();
         }
      });
   }
}
