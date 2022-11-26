package weblogic.rjvm;

import java.net.UnknownHostException;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.AdminServerIdentity;
import weblogic.protocol.configuration.ProtocolHelper;
import weblogic.rjvm.t3.ProtocolHandlerT3;
import weblogic.rjvm.t3.ProtocolHandlerT3S;
import weblogic.rmi.utils.io.Codebase;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public class RJVMService extends AbstractServerService {
   @Inject
   @Named("BootService")
   private ServerService dependencyOnBootService;
   @Inject
   @Named("EarlySecurityInitializationService")
   private ServerService dependencyOnEarlySecurityInitializationService;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void start() throws ServiceFailureException {
      try {
         this.setJVMID();
         this.initGenericClassLoader();
      } catch (UnknownHostException var2) {
         throw new ServiceFailureException(var2);
      } catch (ConfigurationException var3) {
         throw new ServiceFailureException(var3);
      }
   }

   public void setJVMID() throws UnknownHostException, ConfigurationException {
      String host = ManagementService.getRuntimeAccess(kernelId).getServer().getListenAddress();
      String dnsName = ManagementService.getRuntimeAccess(kernelId).getServer().getExternalDNSName();
      ProtocolHandlerT3.getProtocolHandler();
      ProtocolHandlerT3S.getProtocolHandler();
      JVMID.setLocalID(host, dnsName, ManagementService.getRuntimeAccess(kernelId).getDomainName(), ManagementService.getRuntimeAccess(kernelId).getServerName());
      if (RJVMManager.getRJVMManager() != null && AdminServerIdentity.getBootstrapIdentity() != null) {
         RJVMManager.getRJVMManager().setAdminID(AdminServerIdentity.getBootstrapIdentity().getDomainName() + AdminServerIdentity.getBootstrapIdentity().getServerName());
      }

   }

   private void initGenericClassLoader() {
      Codebase.setDefaultCodebase(this.getServerURL(false));
   }

   private String getServerURL(boolean secure) {
      JVMID localID = JVMID.localID();
      String host = localID.getClusterAddress();
      if (host == null) {
         host = localID.address().getHostAddress();
      }

      int port = secure ? ManagementService.getRuntimeAccess(kernelId).getServer().getSSL().getListenPort() : ManagementService.getRuntimeAccess(kernelId).getServer().getListenPort();
      return ProtocolHelper.getCodebase(secure, host, port, (String)null);
   }
}
