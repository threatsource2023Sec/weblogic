package weblogic.cluster.migration;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import weblogic.cluster.ClusterLogger;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleServiceGenerator;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class ScriptExecutor {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static boolean isNMAvailable(ServerMBean server) {
      return server.getMachine() != null;
   }

   public static boolean runNMScript(String filePath, MigratableTargetMBean target) {
      return runNMScript(filePath, target, ManagementService.getRuntimeAccess(kernelId).getServer());
   }

   public static boolean runNMScript(String filePath, MigratableTargetMBean target, ServerMBean server) {
      if (target == null) {
         return false;
      } else if (server == null) {
         return false;
      } else if (filePath != null && filePath.trim().length() != 0) {
         if (!isNMAvailable(server)) {
            ClusterLogger.logMissingMachine(server.getName());
            return false;
         } else {
            try {
               long timeout = 0L;
               NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
               NodeManagerLifecycleService nm = generator.getInstance(server.getMachine());
               nm.runScript(new File(filePath), timeout);
               return true;
            } catch (IOException var7) {
               ClusterLogger.logScriptFailed(filePath, var7);
               return false;
            }
         }
      } else {
         return true;
      }
   }
}
