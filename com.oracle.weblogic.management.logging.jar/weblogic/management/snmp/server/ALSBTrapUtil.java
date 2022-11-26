package weblogic.management.snmp.server;

import java.util.LinkedList;
import java.util.List;
import weblogic.diagnostics.snmp.agent.SNMPTrapException;
import weblogic.diagnostics.snmp.agent.SNMPTrapSender;
import weblogic.diagnostics.snmp.agent.SNMPTrapUtil;

public class ALSBTrapUtil {
   public static void sendALSBAlert(String trapType, String severity, String domainName, String serverName, String alertId, String ruleId, String ruleName, String ruleCondition, String alertTime, String annotation, String serviceName, String servicePath) throws SNMPTrapException {
      SNMPTrapSender ts = SNMPTrapUtil.getInstance().getSNMPTrapSender();
      if (ts != null) {
         List varBindList = new LinkedList();
         varBindList.add(new Object[]{"trapALSBAlertTrapType", trapType});
         varBindList.add(new Object[]{"trapALSBAlertSeverity", severity});
         varBindList.add(new Object[]{"trapALSBAlertDomainName", domainName});
         varBindList.add(new Object[]{"trapALSBAlertServerName", serverName});
         varBindList.add(new Object[]{"trapALSBAlertAlertId", alertId});
         varBindList.add(new Object[]{"trapALSBAlertRuleId", ruleId});
         varBindList.add(new Object[]{"trapALSBAlertRuleName", ruleName});
         varBindList.add(new Object[]{"trapALSBAlertRuleCondition", ruleCondition});
         varBindList.add(new Object[]{"trapALSBAlertAlertTime", alertTime});
         varBindList.add(new Object[]{"trapALSBAlertAnnotation", annotation});
         varBindList.add(new Object[]{"trapALSBAlertServiceName", serviceName});
         varBindList.add(new Object[]{"trapALSBAlertServicePath", servicePath});
         ts.sendTrap("wlsALSBAlert", varBindList);
      }

   }
}
