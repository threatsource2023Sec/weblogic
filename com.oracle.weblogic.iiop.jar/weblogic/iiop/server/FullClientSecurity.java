package weblogic.iiop.server;

import java.security.AccessController;
import weblogic.iiop.Connection;
import weblogic.iiop.csi.ClientSecurity;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.rmi.spi.Channel;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.auth.login.PasswordCredential;
import weblogic.security.service.CredentialManager;
import weblogic.security.service.RemoteResource;
import weblogic.security.subject.SubjectManager;

public class FullClientSecurity extends ClientSecurity {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());

   public FullClientSecurity(Connection connection) {
      super(connection);
   }

   protected PasswordCredential getMappedCredential(AuthenticatedSubject subject, PasswordCredential pc) {
      CredentialManager cm = RmiSecurityFacade.getCredentialManager(KERNEL_ID, RmiSecurityFacade.getDefaultRealm());
      if (cm == null) {
         return pc;
      } else {
         Object[] mappings = cm.getCredentials(KERNEL_ID, subject, this.createRemoteResource(), this.getConnection().getContextHandler(), "weblogic.Password");
         Object[] var5 = mappings;
         int var6 = mappings.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Object cred = var5[var7];
            if (cred instanceof PasswordCredential) {
               return (PasswordCredential)cred;
            }
         }

         return pc;
      }
   }

   private RemoteResource createRemoteResource() {
      return createRemoteResource(this.getConnection().getRemoteChannel());
   }

   private static RemoteResource createRemoteResource(Channel remoteChannel) {
      return new RemoteResource(remoteChannel.getProtocolPrefix(), remoteChannel.getPublicAddress(), Integer.toString(remoteChannel.getPublicPort()), (String)null, (String)null);
   }
}
