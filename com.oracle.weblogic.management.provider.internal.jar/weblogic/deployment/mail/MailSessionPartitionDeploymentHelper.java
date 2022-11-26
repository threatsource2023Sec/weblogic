package weblogic.deployment.mail;

import java.util.Properties;
import javax.management.InvalidAttributeValueException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.MailSessionMBean;
import weblogic.management.configuration.MailSessionOverrideMBean;
import weblogic.management.configuration.PartitionMBean;

public class MailSessionPartitionDeploymentHelper {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");

   public static void processPartition(PartitionMBean partition, MailSessionMBean bean, MailSessionMBean clone) {
      MailSessionOverrideMBean[] mailSessionOverrides = partition == null ? new MailSessionOverrideMBean[0] : partition.getMailSessionOverrides();
      MailSessionOverrideMBean[] var4 = mailSessionOverrides;
      int var5 = mailSessionOverrides.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         MailSessionOverrideMBean override = var4[var6];
         if (override.getName().equals(bean.getName())) {
            overrideAttributes(partition, clone, override.getProperties(), override.getSessionUsername(), override.getSessionPassword());
            break;
         }
      }

   }

   private static void overrideAttributes(PartitionMBean partition, MailSessionMBean bean, Properties props, String username, String password) {
      if (props != null) {
         try {
            bean.setProperties(props);
         } catch (InvalidAttributeValueException var6) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Error occurred overriding mail session attributes", var6);
            }
         }
      }

      if (username != null) {
         bean.setSessionUsername(username);
      }

      if (password != null) {
         bean.setSessionPassword(password);
      }

   }
}
