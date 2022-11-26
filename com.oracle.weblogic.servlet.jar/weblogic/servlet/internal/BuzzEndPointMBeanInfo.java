package weblogic.servlet.internal;

import java.security.AccessController;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;
import weblogic.buzzmessagebus.BuzzMessageBusEndPointMBeanInfo;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@Named
public final class BuzzEndPointMBeanInfo implements BuzzMessageBusEndPointMBeanInfo {
   private ServerMBean server = null;

   public BuzzEndPointMBeanInfo() {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      this.server = ManagementService.getRuntimeAccess(kernelId).getServer();
   }

   public String getBuzzAddress() {
      return this.server.getBuzzAddress();
   }

   public int getBuzzPort() {
      return this.server.getBuzzPort();
   }

   public boolean isBuzzEnabled() {
      return this.server.isBuzzEnabled();
   }
}
