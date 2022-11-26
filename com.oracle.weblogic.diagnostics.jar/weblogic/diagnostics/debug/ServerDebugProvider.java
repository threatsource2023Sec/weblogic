package weblogic.diagnostics.debug;

import com.bea.logging.LoggingService;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.AccessController;
import java.util.logging.Logger;
import weblogic.kernel.KernelLogManager;
import weblogic.management.configuration.ServerDebugMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ServerDebugProvider implements DebugProvider {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private DebugScopeTree debugTree = null;
   private ServerDebugMBean myDebugBean = null;

   public String getName() {
      return this.getClass().getName();
   }

   public String getCommandLineOverridePrefix() {
      return "weblogic.debug.";
   }

   public void intializeDebugScopes() throws DebugScopeInitializationException {
      ObjectInputStream ois = null;

      try {
         InputStream is = this.getClass().getResourceAsStream("DebugScopeTree.ser");
         ois = new ObjectInputStream(is);
         this.debugTree = (DebugScopeTree)ois.readObject();
      } catch (Exception var10) {
         throw new DebugScopeInitializationException(var10.getMessage(), var10);
      } finally {
         if (ois != null) {
            try {
               ois.close();
            } catch (IOException var9) {
               throw new DebugScopeInitializationException(var9.getMessage(), var9);
            }
         }

      }

   }

   public DebugScopeTree getDebugScopeTree() throws DebugScopeInitializationException {
      if (this.debugTree == null) {
         this.intializeDebugScopes();
      }

      return this.debugTree;
   }

   public Object getDebugConfiguration() throws DebugBeanConfigurationException {
      if (this.myDebugBean == null) {
         this.myDebugBean = ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getServerDebug();
      }

      return this.myDebugBean;
   }

   public Logger getLogger() {
      Logger logger = KernelLogManager.getLogger();
      String loggerClassName = logger.getClass().getName();
      return loggerClassName.equals("weblogic.logging.log4j.JDKLog4jAdapter") ? logger : LoggingService.getInstance().getDebugDelegateLogger();
   }
}
