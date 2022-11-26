package javax.servlet.jsp.jstl.fmt;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationContext {
   private final ResourceBundle bundle;
   private final Locale locale;

   public LocalizationContext() {
      this.bundle = null;
      this.locale = null;
   }

   public LocalizationContext(ResourceBundle bundle, Locale locale) {
      this.bundle = bundle;
      this.locale = locale;
   }

   public LocalizationContext(ResourceBundle bundle) {
      this.bundle = bundle;
      this.locale = bundle.getLocale();
   }

   public ResourceBundle getResourceBundle() {
      return this.bundle;
   }

   public Locale getLocale() {
      return this.locale;
   }
}
