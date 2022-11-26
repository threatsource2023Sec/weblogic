package weblogic.upgrade;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class UpgradeResourceBundle extends ListResourceBundle {
   static ResourceBundle ms_rbBundle = null;
   static final Object[][] contents = new Object[][]{{"UNEXPECTED_EXCEPTION", "Unexpected exception occurred during the domain upgrade."}, {"UNEXPECTED_EXCEPTION-ID", "UPGWLS-03501"}, {"UNEXPECTED_EXCEPTION-CAUSE", "An unexpected error occurred while a WLS component was being upgraded."}, {"UNEXPECTED_EXCEPTION-ACTION", "Check the log file for more details and contact Oracle Support Services, if needed."}, {"CONFIG_NOT_FOUND", "The domain configuration file {0} does not exist."}, {"CONFIG_NOT_FOUND-ID", "UPGWLS-03502"}, {"CONFIG_NOT_FOUND-CAUSE", "The specified domain does not contain the WLS config.xml file."}, {"CONFIG_NOT_FOUND-ACTION", "Check the specified domain directory is correct and contains a config.xml file."}, {"VALIDATION_ERRORS", "The domain configuration file {0} is not valid."}, {"VALIDATION_ERRORS-ID", "UPGWLS-03503"}, {"VALIDATION_ERRORS-CAUSE", "The config.xml file contains invalid elements and failed validation during parsing."}, {"VALIDATION_ERRORS-ACTION", "Check the validation errors and update the config.xml appropriately."}};

   public String getMessageId(String key) {
      String msgIdKey = key + "-ID";
      return this.getString(msgIdKey);
   }

   public Object[][] getContents() {
      return contents;
   }

   public static ResourceBundle getResBundle() {
      if (ms_rbBundle != null) {
         return ms_rbBundle;
      } else {
         try {
            ms_rbBundle = ResourceBundle.getBundle("weblogic.upgrade.UpgradeResourceBundle", Locale.getDefault());
         } catch (Exception var1) {
            ms_rbBundle = null;
         }

         return ms_rbBundle;
      }
   }
}
