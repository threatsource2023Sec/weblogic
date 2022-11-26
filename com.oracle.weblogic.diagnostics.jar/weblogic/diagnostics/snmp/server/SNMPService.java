package weblogic.diagnostics.snmp.server;

import java.security.AccessController;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.agent.SNMPTrapUtil;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SNMPAgentMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public class SNMPService extends AbstractServerService {
   static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private String serverName;

   public void start() throws ServiceFailureException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Starting SNMP Service");
      }

      SNMPAgentDeploymentHandler handler = SNMPAgentDeploymentHandler.getInstance();
      boolean adminServer = false;
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = runtimeAccess.getDomain();
      adminServer = runtimeAccess.isAdminServer();
      this.serverName = runtimeAccess.getServerName();
      if (adminServer) {
         SNMPAgentMBean snmpAgent = domain.getSNMPAgent();
         handler.setDomainAgentConfig(snmpAgent);
      } else {
         SNMPTrapUtil.getInstance().setSNMPTrapSender(new SNMPAdminServerTrapSender());
      }

      try {
         handler.setSNMPServiceStarted(true);
         handler.activateSNMPAgent();
         handler.ensureSNMPAgentRuntimeInitialized();
      } catch (Exception var7) {
         SNMPLogger.logSNMPServiceFailure(var7);
      }

   }

   public void stop() throws ServiceFailureException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Stopping SNMPService");
      }

      SNMPAgentDeploymentHandler handler = SNMPAgentDeploymentHandler.getInstance();

      try {
         SNMPAgent agent = handler.getSNMPAgent();
         if (agent != null) {
            ServerStateTrapUtil.sendServerLifecycleNotification(agent, this.serverName, "wlsServerShutDown");
         }
      } catch (Exception var3) {
      }

      handler.setSNMPServiceStarted(false);
   }

   public void halt() throws ServiceFailureException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Halting SNMPService");
      }

      this.stop();
   }
}
