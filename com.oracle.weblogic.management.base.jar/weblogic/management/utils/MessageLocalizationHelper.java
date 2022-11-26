package weblogic.management.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageLocalizationHelper {
   private Locale locale_;
   private ResourceBundle resourceBundle_;

   public MessageLocalizationHelper(String resourceBundleLocation, Locale loc) {
      this(resourceBundleLocation, loc, (ClassLoader)null);
   }

   public MessageLocalizationHelper(String resourceBundleLocation, Locale loc, ClassLoader loader) {
      this.locale_ = null;
      this.resourceBundle_ = null;
      this.locale_ = loc;
      if (loader != null) {
         this.resourceBundle_ = ResourceBundle.getBundle(resourceBundleLocation, loc, loader);
      } else {
         this.resourceBundle_ = ResourceBundle.getBundle(resourceBundleLocation, loc, Thread.currentThread().getContextClassLoader());
      }

   }

   public MessageLocalizationHelper(String resourceBundleLocation) {
      this(resourceBundleLocation, Locale.getDefault());
   }

   public MessageLocalizationHelper(ResourceBundle resourceBundle, Locale loc) {
      this.locale_ = null;
      this.resourceBundle_ = null;
      this.locale_ = loc;
      this.resourceBundle_ = resourceBundle;
   }

   public MessageLocalizationHelper(ResourceBundle resourceBundle) {
      this(resourceBundle, Locale.getDefault());
   }

   public Locale getLocale() {
      return this.locale_;
   }

   public final String getLocalizedMessage(String name) {
      return this.resourceBundle_.getString(name);
   }

   public final String getLocalizedMessage(String name, String param) {
      String[] params = new String[]{param};
      return this.getLocalizedMessage(name, params);
   }

   public final String getLocalizedMessage(String name, String param1, String param2) {
      String[] params = new String[]{param1, param2};
      return this.getLocalizedMessage(name, params);
   }

   public final String getLocalizedMessage(String name, String param1, String param2, String param3) {
      String[] params = new String[]{param1, param2, param3};
      return this.getLocalizedMessage(name, params);
   }

   public final String getLocalizedMessage(String name, String[] params) {
      String message = this.resourceBundle_.getString(name);
      return MessageFormat.format(message, (Object[])params);
   }
}
