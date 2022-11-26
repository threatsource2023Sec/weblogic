package weblogic.deployment.jms;

import java.security.AccessController;
import java.util.Hashtable;
import java.util.Map;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.common.resourcepool.ResourcePool;
import weblogic.jms.utils.PartitionUtils;
import weblogic.jndi.ClientEnvironmentFactory;
import weblogic.jndi.internal.ClientEnvironmentFactoryImpl;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

public class PlatformHelperImpl extends PlatformHelper {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ClientEnvironmentFactory environmentFactory = new ClientEnvironmentFactoryImpl();
   private static boolean ResNameEqual = false;

   public boolean getResNameEqualProp() {
      return ResNameEqual;
   }

   public JMSSessionPoolRuntime createJMSSessionPoolRuntime(String poolName, ResourcePool pool, JMSSessionPool sessionPool) throws ManagementException {
      return new JMSSessionPoolRuntimeImpl(poolName, pool, sessionPool);
   }

   String getXAResourceName(String poolName) {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
      String domainName = runtimeAccess.getDomainName();
      String serverName = runtimeAccess.getServerName();
      String xaResourceName;
      if (!ResNameEqual) {
         xaResourceName = domainName + "." + serverName + ".JMSXASessionPool." + poolName;
      } else {
         xaResourceName = domainName + ".JMSXASessionPool." + poolName;
      }

      return PartitionUtils.appendPartitionName(xaResourceName, PartitionUtils.getPartitionName());
   }

   PlatformHelper.ForeignRefReturn checkForeignRef(Map poolProps) throws JMSException {
      Context context = null;
      PlatformHelper.ForeignRefReturn ret = new PlatformHelper.ForeignRefReturn(this);
      boolean pushLocal = false;
      ret.subject = null;
      ret.foundCreds = false;
      ret.userNameBuf = new StringBuffer();
      ret.passwdBuf = new StringBuffer();
      ret.connectionHealthChecking = "enabled";
      AbstractSubject localSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      if (localSubject.equals(KERNEL_ID)) {
         localSubject = SubjectManager.getSubjectManager().getAnonymousSubject();
      }

      Hashtable jndiEnv = JMSConnectionHelper.getJNDIEnvironment(poolProps);
      Hashtable foreignJNDIEnv = null;

      try {
         String jndiName = JMSConnectionHelper.getJNDIName(poolProps);
         context = new InitialContext(jndiEnv);
         AbstractSubject currentSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
         if (currentSubject.equals(KERNEL_ID)) {
            pushLocal = true;
            SubjectManager.getSubjectManager().pushSubject(KERNEL_ID, (AbstractSubject)localSubject);
         }

         Object obj;
         try {
            obj = context.lookupLink(jndiName);
         } finally {
            if (pushLocal) {
               SubjectManager.getSubjectManager().popSubject(KERNEL_ID);
            }

            context.close();
         }

         SecurityServiceManager.pushSubject(KERNEL_ID, KERNEL_ID);

         try {
            if (obj instanceof ForeignOpaqueReference) {
               ForeignOpaqueReference foRef = (ForeignOpaqueReference)obj;
               if (foRef.isFactory()) {
                  if (foRef.getUsername() != null && foRef.getUsername().length() > 0) {
                     ret.foundCreds = true;
                     ret.userNameBuf.append(foRef.getUsername());
                     if (JMSPoolDebug.logger.isDebugEnabled()) {
                        JMSPoolDebug.logger.debug("Found credentials for connection factory with username " + ret.userNameBuf.toString());
                     }
                  }

                  if (foRef.getPassword() != null && foRef.getPassword().length() > 0) {
                     ret.foundCreds = true;
                     ret.passwdBuf.append(foRef.getPassword());
                  }

                  ret.connectionHealthChecking = foRef.getConnectionHealthChecking();
                  foreignJNDIEnv = foRef.getJNDIEnvironment();
               }
            }
         } finally {
            SecurityServiceManager.popSubject(KERNEL_ID);
         }

         if (foreignJNDIEnv != null) {
            Context context = this.getForeignInitialContext(foreignJNDIEnv, ret);
            context.close();
         }

         if (!ret.foundCreds && JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("No credentials associated with connection factory");
         }

         return ret;
      } catch (NamingException var21) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Can't get credentials associated with connection factory: " + var21);
         }

         return ret;
      }
   }

   private Context getForeignInitialContext(Hashtable jndiEnv, PlatformHelper.ForeignRefReturn retForeignRef) throws NamingException {
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("JMSConnectionHelper.getForeignInitialContext() + jndiEnv ");
      }

      Context ret = new InitialContext(jndiEnv);
      String ctxUserName = (String)jndiEnv.get("java.naming.security.principal");
      String ctxPassword = (String)jndiEnv.get("java.naming.security.credentials");
      if (ctxUserName != null || ctxPassword != null) {
         synchronized(this) {
            retForeignRef.subject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("JMSConnectionHelper.getForeignInitialContext(), subject " + retForeignRef.subject);
            }
         }
      }

      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("JMSConnectionHelper.getForeignInitialContext()  subject ****** url " + (String)jndiEnv.get("java.naming.provider.url"));
      }

      return ret;
   }

   static {
      try {
         ResNameEqual = Boolean.getBoolean("weblogic.deployment.jms.ResourceNameEqualityInClusterEnvForRecovery");
      } catch (SecurityException var1) {
      }

   }
}
