package weblogic.management.snmp.agent;

import weblogic.management.snmp.server.ALSBTrapUtil;

public final class TrapUtil {
   private static final boolean DEBUG = true;
   private static String encoding = null;

   public static void sendALAlertTrap(String trapType, String severity, String domainName, String serverName, String alertId, String ruleId, String ruleName, String ruleCondition, String timeStamp, String annotation, String serviceName, String servicePath) {
      try {
         ALSBTrapUtil.sendALSBAlert(trapType, severity, domainName, serverName, alertId, ruleId, ruleName, ruleCondition, timeStamp, annotation, serviceName, servicePath);
      } catch (Exception var13) {
         var13.printStackTrace();
      }

   }

   public static long convertTimeToRFC1213(long timeInMS) {
      long timeInRFCStandard = (timeInMS + 5L) / 10L;
      return timeInRFCStandard;
   }

   private static String getEncoding() {
      if (encoding != null) {
         return encoding;
      } else {
         encoding = System.getProperty("weblogic.management.snmp.encoding");
         if (encoding == null) {
            encoding = System.getProperty("file.encoding");
         }

         return encoding;
      }
   }
}
