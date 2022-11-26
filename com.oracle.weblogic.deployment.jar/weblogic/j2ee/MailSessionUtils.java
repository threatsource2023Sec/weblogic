package weblogic.j2ee;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.application.naming.MailSessionReference;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.NoAccessRuntimeException;
import weblogic.management.configuration.MailSessionMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.MailSessionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class MailSessionUtils {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugMailSessionDeployment");
   private static final AuthenticatedSubject KERNEL = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final Map runtimes = new HashMap();
   private static Context _ctx;

   public static void deployMailSession(MailSessionMBean mbean) throws DeploymentException {
      checkIfKernel();
      Properties props = mbean.getProperties();
      if (props == null) {
         props = new Properties();
      }

      MailSessionReference sessionReference = new MailSessionReference(mbean.getSessionUsername(), mbean.getSessionPassword(), props);
      String jndiName = mbean.getJNDIName();

      try {
         Context ctx = getContext();
         ctx.bind(jndiName, sessionReference);
      } catch (NamingException var6) {
         throw new DeploymentException("Could not bind a mail session to JNDI name " + jndiName + " ", var6);
      }

      J2EELogger.logDeployedMailSession(jndiName);

      try {
         MailSessionRuntimeMBeanImpl rtmb = new MailSessionRuntimeMBeanImpl(jndiName);
         runtimes.put(jndiName, rtmb);
         if (rtmb.getParent() instanceof PartitionRuntimeMBean) {
            ((PartitionRuntimeMBean)rtmb.getParent()).addMailSessionRuntime(rtmb);
         } else {
            ManagementService.getRuntimeAccess(KERNEL).getServerRuntime().addMailSessionRuntime(rtmb);
         }

      } catch (ManagementException var5) {
         undeployMailSession(mbean);
         throw new DeploymentException(var5);
      }
   }

   public static void undeployMailSession(MailSessionMBean mbean) throws DeploymentException {
      checkIfKernel();
      String jndiName = mbean.getJNDIName();
      boolean var11 = false;

      try {
         var11 = true;
         Context jndiContext = getContext();
         jndiContext.unbind(jndiName);
         var11 = false;
      } catch (NamingException var14) {
         throw new DeploymentException("Could not unbind a mail session from JNDI name " + jndiName + " ", var14);
      } finally {
         if (var11) {
            MailSessionRuntimeMBeanImpl rtmb = (MailSessionRuntimeMBeanImpl)runtimes.get(jndiName);
            if (rtmb != null) {
               try {
                  ManagementService.getRuntimeAccess(KERNEL).getServerRuntime().removeMailSessionRuntime(rtmb);
                  rtmb.unregister();
               } catch (ManagementException var12) {
                  throw new DeploymentException(var12);
               }
            }

         }
      }

      MailSessionRuntimeMBeanImpl rtmb = (MailSessionRuntimeMBeanImpl)runtimes.get(jndiName);
      if (rtmb != null) {
         try {
            ManagementService.getRuntimeAccess(KERNEL).getServerRuntime().removeMailSessionRuntime(rtmb);
            rtmb.unregister();
         } catch (ManagementException var13) {
            throw new DeploymentException(var13);
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Undeployed MailSession named " + jndiName);
      }

      J2EELogger.logUndeployedMailSession(jndiName);
   }

   private static void checkIfKernel() {
      AuthenticatedSubject curSubject = SecurityServiceManager.getCurrentSubject(KERNEL);
      if (!SecurityServiceManager.isKernelIdentity(curSubject)) {
         throw new NoAccessRuntimeException(curSubject.toString());
      }
   }

   private static Context getContext() {
      try {
         Hashtable env = new Hashtable();
         env.put("weblogic.jndi.createIntermediateContexts", "true");
         env.put("weblogic.jndi.replicateBindings", "false");
         _ctx = new InitialContext(env);
      } catch (Exception var1) {
         throw new AssertionError("Cannot intialize Resource Manager Connection Factory resources because could not get JNDI context: " + var1.toString());
      }

      return _ctx;
   }

   static MailSessionRuntimeMBean lookupSessionRuntime(String jndiName) {
      return jndiName == null ? null : (MailSessionRuntimeMBean)runtimes.get(jndiName);
   }
}
