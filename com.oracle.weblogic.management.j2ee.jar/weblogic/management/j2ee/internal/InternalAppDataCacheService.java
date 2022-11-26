package weblogic.management.j2ee.internal;

import java.io.File;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.Home;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public class InternalAppDataCacheService extends AbstractServerService {
   @Inject
   @Named("DeploymentServerService")
   private ServerService dependencyOnDeploymentServerService;
   private static final Map appToSource = new HashMap(7);
   private static final String LIB;
   private static AuthenticatedSubject kernelId;

   public void start() throws ServiceFailureException {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
         AppDeploymentMBean[] apps = AppDeploymentHelper.getAppsAndLibs(ManagementService.getRuntimeAccess(kernelId).getDomain());

         for(int i = 0; i < apps.length; ++i) {
            AppDeploymentMBean app = apps[i];
            if (app.isInternalApp()) {
               appToSource.put(app.getName(), app.getAbsoluteSourcePath());
            }
         }

         appToSource.put("bea_wls_cluster_internal", LIB);
      }
   }

   public void stop() throws ServiceFailureException {
   }

   public void halt() throws ServiceFailureException {
   }

   public static String getSourceLocation(String app) {
      return (String)appToSource.get(app);
   }

   public static boolean isInternalApp(String appName) {
      return appToSource.containsKey(appName);
   }

   static {
      LIB = Home.getPath() + File.separator + "lib";
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
