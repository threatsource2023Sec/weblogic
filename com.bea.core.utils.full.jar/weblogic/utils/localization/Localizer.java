package weblogic.utils.localization;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Localizer {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private ResourceBundle res;

   public Localizer(String bundleBaseName) throws MissingResourceException {
      this.res = ResourceBundle.getBundle(bundleBaseName);
   }

   public Localizer(ResourceBundle bundle) {
      this.res = bundle;
   }

   public String getFormattedMsg(String errorTextKey) throws MissingResourceException {
      return this.res.getString(errorTextKey);
   }

   public String getFormattedMsg(String errorTextKey, String arg1) throws MissingResourceException {
      String locStr = this.res.getString(errorTextKey);
      return MessageFormat.format(locStr, arg1);
   }

   public String getFormattedMsg(String errorTextKey, String arg1, String arg2) {
      String locStr = this.res.getString(errorTextKey);
      return MessageFormat.format(locStr, arg1, arg2);
   }

   public String getFormattedMsg(String errorTextKey, String arg1, String arg2, String arg3) {
      String locStr = this.res.getString(errorTextKey);
      return MessageFormat.format(locStr, arg1, arg2, arg3);
   }
}
