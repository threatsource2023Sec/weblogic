package weblogic.upgrade;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ResourceBundleManager {
   public static final String UPGRADE_RB_NAME = "weblogic.upgrade.UpgradeResourceBundle";
   private static final ResourceBundle m_upgradeResBundle = _init();

   private static ResourceBundle _init() {
      ResourceBundle retVal = null;

      try {
         retVal = UpgradeResourceBundle.getResBundle();
      } catch (Exception var2) {
         System.err.println("Failed to find weblogic.upgrade.UpgradeResourceBundle. Text messages will not be localized.");
         var2.printStackTrace();
      }

      return retVal;
   }

   public static String getUpgradeString(String key) {
      String msgId = getUpgradeMessageId(key);
      if (m_upgradeResBundle != null) {
         try {
            return msgId != null && !msgId.equals(key) ? msgId + ": " + m_upgradeResBundle.getString(key) : m_upgradeResBundle.getString(key);
         } catch (Exception var3) {
            return var3.getLocalizedMessage();
         }
      } else {
         return key;
      }
   }

   public static String getUpgradeString(String key, Object[] args) {
      String msgId = getUpgradeMessageId(key);
      MessageFormat formatter = new MessageFormat("");

      try {
         formatter.applyPattern(m_upgradeResBundle.getString(key));
         return msgId != null && !msgId.equals(key) ? msgId + ": " + formatter.format(args) : formatter.format(args);
      } catch (Exception var5) {
         return var5.getLocalizedMessage();
      }
   }

   private static String getUpgradeMessageId(String key) {
      if (m_upgradeResBundle != null) {
         try {
            if (m_upgradeResBundle instanceof UpgradeResourceBundle) {
               UpgradeResourceBundle rb = (UpgradeResourceBundle)m_upgradeResBundle;
               return rb.getMessageId(key);
            }
         } catch (Exception var2) {
            return key;
         }
      }

      return key;
   }
}
