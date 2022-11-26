package weblogic.common.internal;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.cert.X509Certificate;
import javax.security.auth.login.LoginException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.protocol.ChannelHelperBase;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.spi.InboundRequest;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.DefaultUserInfoImpl;
import weblogic.security.acl.SecurityService;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.utils.Debug;

public final class RMIBootServiceImpl implements SecurityService {
   private static boolean turnOffIA;
   private static boolean propogatePE;
   private static final AuthenticatedSubject kernelId;

   public AuthenticatedUser authenticate(UserInfo ui) throws RemoteException {
      throw new AssertionError("authenticate()");
   }

   public AuthenticatedUser authenticate(UserInfo ui, InboundRequest request) throws RemoteException {
      Debug.assertion(request != null, "Request cannot be null");
      if (request == null) {
         throw new RemoteException("Request cannot be null");
      } else {
         ServerChannel channel = request.getServerChannel();
         AuthenticatedSubject subject = null;
         X509Certificate[] cert = request.getCertificateChain();
         Object au;
         if (ui instanceof AuthenticatedUser) {
            au = (AuthenticatedUser)ui;
         } else {
            ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
            String partitionName = cic.getPartitionName();
            String realmName = SecurityServiceManager.getRealmName(partitionName);
            if (realmName == null) {
               realmName = "weblogicDEFAULT";
            }

            PrincipalAuthenticator pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(kernelId, realmName, ServiceType.AUTHENTICATION);
            Debug.assertion(pa != null, "Security system not initialized");
            if (turnOffIA && cert != null) {
               return SecurityServiceManager.getCurrentSubject(kernelId);
            }

            if (cert != null) {
               try {
                  String X509_TYPE = "X.509";
                  subject = pa.assertIdentity(X509_TYPE, cert, request.getContextHandler());
               } catch (LoginException var17) {
               }
            }

            if (subject == null) {
               if (!(ui instanceof DefaultUserInfoImpl)) {
                  SecurityException se1 = new SecurityException("Received bad UserInfo: " + ui.getClass().getName());
                  throw new RemoteException(se1.getMessage(), se1);
               }

               DefaultUserInfoImpl info = (DefaultUserInfoImpl)ui;
               String username = info.getName();
               String password = info.getPassword();
               if (username == null || username.length() == 0) {
                  return SubjectUtils.getAnonymousSubject();
               }

               try {
                  SimpleCallbackHandler handler = new SimpleCallbackHandler(username, password);
                  subject = pa.authenticate(handler, request.getContextHandler());
               } catch (LoginException var16) {
                  SecurityException se = new SecurityException("User failed to be authenticated.");
                  throw new RemoteException(se.getMessage(), se);
               }
            }

            this.checkAdminPort(subject, ChannelHelperBase.isAdminChannel(channel));
            au = subject;
         }

         setQOS((AuthenticatedUser)au, channel);
         return (AuthenticatedUser)au;
      }
   }

   private void checkAdminPort(AuthenticatedSubject subject, boolean isAdminOnlyChannel) throws RemoteException {
      if (ChannelHelperBase.isLocalAdminChannelEnabled() && SubjectUtils.isUserAnAdministrator(subject) && !isAdminOnlyChannel) {
         SecurityException se = new SecurityException("User '" + subject + "' has administration role. All tasks by administrators must go through an Administration Port.");
         throw new RemoteException(se.getMessage(), se);
      }
   }

   private static void setQOS(AuthenticatedUser au, ServerChannel channel) {
      if (ChannelHelperBase.isLocalAdminChannelEnabled() && au instanceof AuthenticatedSubject && SubjectUtils.isUserAnAdministrator((AuthenticatedSubject)au) && ChannelHelperBase.isAdminChannel(channel)) {
         au.setQOS((byte)103);
      } else {
         au.setQOS(channel.getProtocol().getQOS());
      }

   }

   static {
      try {
         turnOffIA = Boolean.getBoolean("weblogic.security.disableIdentityAssertion");
         propogatePE = Boolean.getBoolean("weblogic.security.propogateProviderException");
      } catch (SecurityException var1) {
      }

      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
