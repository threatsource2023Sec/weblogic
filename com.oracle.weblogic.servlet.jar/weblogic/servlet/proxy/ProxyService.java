package weblogic.servlet.proxy;

import java.security.AccessController;
import weblogic.management.configuration.HTTPProxyMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.Debug;
import weblogic.utils.StringUtils;

public final class ProxyService extends AbstractServerService {
   private static final String MBEAN_TYPE = "HTTPProxy";
   private static final boolean DEBUG = true;
   private final String localServer;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public ProxyService() {
      this.localServer = ManagementService.getRuntimeAccess(kernelId).getServerName();
   }

   public void initialize() throws ServiceFailureException {
   }

   public void start() throws ServiceFailureException {
   }

   private void parseAndTriggerHealthChecker(String list, HTTPProxyMBean bean) {
      String[] hostPortArray = StringUtils.split(list, ',');

      for(int i = 0; i < hostPortArray.length; ++i) {
         if (hostPortArray[i].length() != 0) {
            String[] url = StringUtils.split(hostPortArray[i].trim(), ':');
            HealthCheckTrigger trigger = new HealthCheckTrigger(url[0].trim(), url[1].trim(), bean.getHealthCheckInterval(), bean.getMaxRetries(), bean.getMaxHealthCheckInterval());
            Debug.say("REGISTERED TRIGGER FOR " + trigger);
         }
      }

   }
}
