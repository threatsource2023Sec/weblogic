package weblogic.cluster.replication;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.naming.NamingException;
import weblogic.cluster.ClusterLogger;
import weblogic.jndi.Environment;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.server.RemoteDomainSecurityHelper;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;

public class SecureReplicationInvocationHandler implements InvocationHandler {
   private static final Class[] interfaces = new Class[]{ReplicationServicesInternal.class};
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final ReplicationServicesInternal delegate;
   private final boolean useSecuredChannel;
   private final String url;
   private AuthenticatedSubject cachedSubject;

   public static ReplicationServicesInternal lookupService(final String url, final String channelName, final int timeout, final Class replicationServiceClass, boolean securedChannel) throws NamingException {
      ReplicationServicesInternal repMan = null;
      AuthenticatedSubject user = getSubjectForReplicationCalls(url, securedChannel);
      final PrivilegedExceptionAction action = new PrivilegedExceptionAction() {
         public ReplicationServicesInternal run() throws NamingException {
            Environment env = new Environment();
            env.setProviderUrl(url);
            env.setProviderChannel(channelName);
            if (timeout >= 0) {
               env.setConnectionTimeout((long)timeout);
               env.setResponseReadTimeout((long)timeout);
            }

            return (ReplicationServicesInternal)PortableRemoteObject.narrow(env.getInitialReference(replicationServiceClass), ReplicationServicesInternal.class);
         }
      };
      PrivilegedExceptionAction wrapperAction = new PrivilegedExceptionAction() {
         public ReplicationServicesInternal run() throws NamingException {
            try {
               return (ReplicationServicesInternal)AccessController.doPrivileged(action);
            } catch (PrivilegedActionException var2) {
               SecureReplicationInvocationHandler.throwNamingException(var2);
               return null;
            }
         }
      };

      try {
         repMan = (ReplicationServicesInternal)SecurityManager.runAs(KERNEL_ID, user, wrapperAction);
         return makeSecureService(repMan, securedChannel, url);
      } catch (PrivilegedActionException var10) {
         throwNamingException(var10);
         return null;
      }
   }

   private static void throwNamingException(PrivilegedActionException pae) throws NamingException {
      Exception ex = pae.getException();
      if (ex instanceof NamingException) {
         throw (NamingException)ex;
      } else if (ex instanceof RuntimeException) {
         throw (RuntimeException)ex;
      } else {
         NamingException ne = new NamingException(pae.getMessage());
         ne.setRootCause(pae.getCause());
         throw ne;
      }
   }

   public static void checkPriviledges(AuthenticatedSubject subject, boolean secureChannel) throws SecurityException {
      if (secureChannel) {
         checkPriviledgesForSecuredChannel(subject);
      } else {
         checkPriviledgesForUnsecuredChannel(subject);
      }

   }

   private static ReplicationServicesInternal makeSecureService(ReplicationServicesInternal rs, boolean useSecuredChannel, String url) {
      return (ReplicationServicesInternal)Proxy.newProxyInstance(rs.getClass().getClassLoader(), interfaces, new SecureReplicationInvocationHandler(rs, useSecuredChannel, url));
   }

   private static AuthenticatedSubject getSubjectForReplicationCalls(String url, boolean securedChannel) {
      try {
         AuthenticatedSubject subject = RemoteDomainSecurityHelper.getSubject(url);
         if (subject != null) {
            return subject;
         }
      } catch (IOException var3) {
      }

      return securedChannel ? KERNEL_ID : getCurrentSubject();
   }

   private AuthenticatedSubject getSubjectForReplicationCalls() {
      AuthenticatedSubject subject = this.cachedSubject;
      if (subject == null) {
         try {
            subject = RemoteDomainSecurityHelper.getSubject(this.url);
         } catch (IOException var3) {
         }

         if (subject != null) {
            this.cachedSubject = subject;
         }
      }

      return subject != null ? subject : (this.useSecuredChannel ? KERNEL_ID : getCurrentSubject());
   }

   private static AuthenticatedSubject getCurrentSubject() {
      AuthenticatedSubject currentUser = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      return SubjectUtils.isUserAnAdministrator(currentUser) ? SubjectUtils.getAnonymousSubject() : currentUser;
   }

   private static void checkPriviledgesForSecuredChannel(AuthenticatedSubject subject) throws SecurityException {
      if (!SubjectUtils.isUserAnAdministrator(subject)) {
         ClusterLogger.logWrongPriviledgesForReplicationCalls("users with Admin priviledges", "secured");
         SecurityException se = new SecurityException("Insufficient priviledges for doing replication.");
         throw se;
      }
   }

   private static void checkPriviledgesForUnsecuredChannel(AuthenticatedSubject subject) throws SecurityException {
      if (SubjectUtils.isUserAnAdministrator(subject)) {
         ClusterLogger.logWrongPriviledgesForReplicationCalls("users without Admin priviledges", "unsecured");
         SecurityException se = new SecurityException("Insufficient priviledges for doing replication.");
         throw se;
      }
   }

   private SecureReplicationInvocationHandler(ReplicationServicesInternal rs, boolean useSecuredChannel, String url) {
      this.delegate = rs;
      this.useSecuredChannel = useSecuredChannel;
      this.url = url;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      ReplicationServicesInvocationAction action = new ReplicationServicesInvocationAction(this.delegate, method, args);
      AuthenticatedSubject user = this.getSubjectForReplicationCalls();

      try {
         Object retValue = SecurityServiceManager.runAs(KERNEL_ID, user, action);
         if (action.getInvocationException() != null) {
            throw action.getInvocationException();
         } else {
            return retValue;
         }
      } catch (InvocationTargetException var7) {
         if (var7.getCause() != null) {
            throw var7.getCause();
         } else {
            throw new RemoteException(var7.getMessage());
         }
      }
   }

   public class ReplicationServicesInvocationAction implements PrivilegedAction {
      private Object targetObject;
      private Method targetMethod;
      private Object[] targetArgs;
      private Throwable exception = null;

      public ReplicationServicesInvocationAction(Object obj, Method m, Object[] args) {
         this.targetObject = obj;
         this.targetMethod = m;
         this.targetArgs = args;
      }

      public Object run() {
         try {
            return this.targetMethod.invoke(this.targetObject, this.targetArgs);
         } catch (Throwable var2) {
            this.exception = var2;
            return null;
         }
      }

      public Throwable getInvocationException() {
         return this.exception;
      }
   }
}
