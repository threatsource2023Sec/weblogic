package weblogic.diagnostics.snmp.agent.monfox;

import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.ext.acm.AppAcm;
import monfox.toolkit.snmp.agent.ext.audit.SnmpAuditTrailLogger;
import weblogic.diagnostics.snmp.agent.SNMPSecurityManager;

public class WLSSnmpSecurityManager implements SNMPSecurityManager {
   private SnmpAgent snmpAgent;

   WLSSnmpSecurityManager(SnmpAgent snmpAgent) {
      this.snmpAgent = snmpAgent;
   }

   public int getFailedAuthenticationCount() {
      int retVal = 0;
      SnmpAuditTrailLogger auditTrailLogger = this.snmpAgent.getAuditTrailLogger();
      if (auditTrailLogger instanceof WLSAuditTrailLogger) {
         retVal = ((WLSAuditTrailLogger)auditTrailLogger).getFailedAuthenticationCount();
      }

      return retVal;
   }

   public int getFailedAuthorizationCount() {
      AppAcm acm = (AppAcm)this.snmpAgent.getAccessControlModel();
      WLSAccessController wlsAccessController = (WLSAccessController)acm.getAccessController();
      return wlsAccessController.getFailedAuthorizationCount();
   }

   public int getFailedEncryptionCount() {
      int retVal = 0;
      SnmpAuditTrailLogger auditTrailLogger = this.snmpAgent.getAuditTrailLogger();
      if (auditTrailLogger instanceof WLSAuditTrailLogger) {
         retVal = ((WLSAuditTrailLogger)auditTrailLogger).getFailedEncryptionCount();
      }

      return retVal;
   }

   public void invalidateLocalizedKeyCache(String username) {
      WLSSecurityExtension.getInstance().invalidateLocalizedKeyCache(username);
   }
}
