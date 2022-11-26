package weblogic.iiop.csi;

import java.security.AccessController;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.corba.cos.security.GSSUtil;
import weblogic.iiop.Connection;
import weblogic.iiop.contexts.EstablishContext;
import weblogic.iiop.contexts.SASServiceContext;
import weblogic.iiop.contexts.ServiceContext;
import weblogic.iiop.contexts.VendorInfoSecurity;
import weblogic.iiop.ior.CompoundSecMechList;
import weblogic.kernel.Kernel;
import weblogic.rmi.client.facades.RmiClientSecurityFacade;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.auth.login.PasswordCredential;
import weblogic.security.service.PrivilegedActions;

public class ClientSecurity {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private Connection connection;
   private long nextClientContextId = 0L;
   private final Map statefulClientContextIdTable = new HashMap();
   private final Map statefulClientContextTable = new HashMap();

   public ClientSecurity(Connection connection) {
      this.connection = connection;
   }

   public void handleSASReply(SASServiceContext sasServiceContext) {
      if (sasServiceContext.shouldEstablishContext()) {
         this.establishSASClientContext(sasServiceContext.getClientContextId());
      } else if (sasServiceContext.shouldDiscardContext()) {
         this.removeSASClientContext(sasServiceContext.getClientContextId());
      }

   }

   public ServiceContext getServiceContext(AuthenticatedSubject subject, CompoundSecMechList mechList) {
      ClientSecurityContext clientContext = this.getClientContext(mechList, subject);
      if (this.useExistingContext(clientContext, mechList, subject)) {
         return clientContext.getServiceContext();
      } else if (supportsCSIv2(mechList) && this.canSendViaCSIv2(subject)) {
         return this.createCSIv2Context(mechList, subject);
      } else {
         return Kernel.isServer() ? this.createWlsProprietaryContext(subject) : null;
      }
   }

   private boolean useExistingContext(ClientSecurityContext clientContext, CompoundSecMechList mechList, AuthenticatedSubject subject) {
      return !this.needToCreateNewContext(clientContext, mechList, subject);
   }

   private boolean needToCreateNewContext(ClientSecurityContext clientContext, CompoundSecMechList mechList, AuthenticatedSubject subject) {
      return clientContext == null || this.newCredentialsAvailable(clientContext, subject) || canReplaceProprietaryCredentials(clientContext, mechList);
   }

   private long getClientContextId(CompoundSecMechList mechList) {
      return mechList.isGSSUPTargetStateful() ? this.getNextClientContextId() : 0L;
   }

   private String getTargetName(CompoundSecMechList mechList) {
      return mechList.hasGSSUP() ? GSSUtil.extractGSSUPGSSNTExportedName(mechList.getGSSUPTarget()) : null;
   }

   private PasswordCredential getPasswordCredential(AuthenticatedSubject subject) {
      if (subject != null && !RmiClientSecurityFacade.isSubjectAnonymous(subject)) {
         PasswordCredential pc = null;
         Set creds = subject.getPrivateCredentials(KERNEL_ID, PasswordCredential.class);
         Iterator iter = creds.iterator();
         if (iter.hasNext()) {
            pc = (PasswordCredential)iter.next();
         }

         if (!Kernel.isServer()) {
            return pc;
         } else {
            return subject.getPrincipals(UserInfo.class).size() > 0 ? pc : this.getMappedCredential(subject, pc);
         }
      } else {
         return null;
      }
   }

   protected PasswordCredential getMappedCredential(AuthenticatedSubject subject, PasswordCredential pc) {
      return pc;
   }

   private boolean newCredentialsAvailable(ClientSecurityContext clientContext, AuthenticatedSubject subject) {
      return this.isProprietarySecuritySupported() && clientContext.needCredentials() && hasPassword(subject);
   }

   private static boolean canReplaceProprietaryCredentials(ClientSecurityContext clientContext, CompoundSecMechList mechList) {
      return supportsCSIv2(mechList) && clientContext.getServiceContext() instanceof VendorInfoSecurity;
   }

   private static boolean supportsCSIv2(CompoundSecMechList mechList) {
      return mechList != null && mechList.useSAS();
   }

   private boolean canSendViaCSIv2(AuthenticatedSubject subject) {
      return RmiClientSecurityFacade.isSubjectAnonymous(subject) || hasPassword(subject) || !this.isProprietarySecuritySupported();
   }

   private static boolean hasPassword(AuthenticatedSubject subject) {
      Set creds = subject.getPrivateCredentials(KERNEL_ID, PasswordCredential.class);
      return !creds.isEmpty();
   }

   public boolean mayIgnoreCredentials(AuthenticatedSubject subject) {
      return this.isPeerWls() && RmiClientSecurityFacade.isSubjectAnonymous(subject);
   }

   private synchronized ServiceContext createWlsProprietaryContext(AuthenticatedSubject subject) {
      ClientSecurityContext ctx = ClientSecurityContext.createWlsProprietaryContext(subject);
      this.putClientContext((CompoundSecMechList)null, subject, ctx);
      return ctx.getServiceContext();
   }

   public boolean isProprietarySecuritySupported() {
      return this.isPeerWls() && Kernel.isServer();
   }

   private boolean isPeerWls() {
      return this.connection.getPeerInfo() != null;
   }

   public synchronized ServiceContext createCSIv2Context(CompoundSecMechList mechList, AuthenticatedSubject subject) {
      ClientSecurityContext ctx = this.getClientContext(mechList, subject);
      if (ctx == null || ctx.needCredentials() && hasPassword(subject) || ctx.getServiceContext() instanceof VendorInfoSecurity) {
         ctx = ClientSecurityContext.createClientContext(this.createSASServiceContext(mechList, subject));
         ctx.setNeedCredentials(!RmiClientSecurityFacade.isSubjectAnonymous(subject) && !hasPassword(subject));
         this.putClientContext(mechList, subject, ctx);
      }

      return ctx.getServiceContext();
   }

   private synchronized ClientSecurityContext getClientContext(CompoundSecMechList mechList, AuthenticatedSubject subject) {
      Key key = ClientSecurity.Key.create(mechList, subject);
      return (ClientSecurityContext)this.statefulClientContextTable.get(key);
   }

   private void putClientContext(CompoundSecMechList mechList, AuthenticatedSubject subject, ClientSecurityContext ctx) {
      Key key = ClientSecurity.Key.create(mechList, subject);
      this.statefulClientContextTable.put(key, ctx);
      if (mechList != null) {
         this.statefulClientContextIdTable.put(ctx.getClientContextId(), key);
      }

   }

   private SASServiceContext createSASServiceContext(CompoundSecMechList mechList, AuthenticatedSubject subject) {
      return new SASServiceContext(this.createEstablishContext(mechList, subject));
   }

   private EstablishContext createEstablishContext(CompoundSecMechList mechList, AuthenticatedSubject subject) {
      PasswordCredential pc = this.getPasswordCredential(subject);
      long clientContextId = this.getClientContextId(mechList);
      if (mechList.hasGSSUP() && pc != null) {
         return EstablishContext.createForUserPassword(clientContextId, pc, this.getTargetName(mechList));
      } else if (!mechList.hasGSSUPIdentity()) {
         return EstablishContext.createWithAbsentIdentity(clientContextId);
      } else {
         return !RmiClientSecurityFacade.isSubjectAnonymous(subject) && !RmiClientSecurityFacade.isKernelIdentity(subject) ? EstablishContext.createForPrincipalIdentity(clientContextId, RmiClientSecurityFacade.getUsername(subject), this.getTargetName(mechList)) : EstablishContext.createForAnonymousIdentity(clientContextId);
      }
   }

   protected Connection getConnection() {
      return this.connection;
   }

   synchronized long getNextClientContextId() {
      return ++this.nextClientContextId;
   }

   public synchronized void removeSASClientContext(long clientContextId) {
      Key key = (Key)this.statefulClientContextIdTable.remove(clientContextId);
      if (key != null) {
         this.statefulClientContextTable.remove(key);
      }

   }

   public synchronized void establishSASClientContext(long clientContextId) {
      ClientSecurityContext ctx = (ClientSecurityContext)this.statefulClientContextTable.get(this.statefulClientContextIdTable.get(clientContextId));
      if (ctx != null) {
         ctx.contextEstablished();
      }

   }

   private static class Key {
      private static final byte[] EMPTY_TARGET = new byte[0];
      private AuthenticatedSubject subject;
      private byte[] target;

      private static Key create(CompoundSecMechList mechList, AuthenticatedSubject subject) {
         return new Key(subject, mechList == null ? EMPTY_TARGET : mechList.getGSSUPTarget());
      }

      private Key(AuthenticatedSubject subject, byte[] target) {
         this.subject = subject;
         this.target = target;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            Key key = (Key)o;
            return this.subject.equals(key.subject) && Arrays.equals(this.target, key.target);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.subject.hashCode();
         result = 31 * result + Arrays.hashCode(this.target);
         return result;
      }
   }
}
