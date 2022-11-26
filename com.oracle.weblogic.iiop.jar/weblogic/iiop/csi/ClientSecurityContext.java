package weblogic.iiop.csi;

import weblogic.iiop.contexts.SASServiceContext;
import weblogic.iiop.contexts.ServiceContext;
import weblogic.iiop.contexts.VendorInfoSecurity;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;

public final class ClientSecurityContext {
   private long clientContextId;
   private ServiceContext serviceContext;
   private boolean needCredentials = false;
   private boolean contextEstablished = false;

   static ClientSecurityContext createClientContext(SASServiceContext sasServiceContext) {
      return new ClientSecurityContext(sasServiceContext.getClientContextId(), sasServiceContext);
   }

   private ClientSecurityContext(long id, SASServiceContext serviceContext) {
      this.clientContextId = id;
      this.serviceContext = serviceContext;
      this.needCredentials = true;
   }

   static ClientSecurityContext createWlsProprietaryContext(AuthenticatedSubject subject) {
      return new ClientSecurityContext(subject);
   }

   private ClientSecurityContext(AuthenticatedSubject subject) {
      this.clientContextId = -1L;
      this.needCredentials = false;
      if (!RmiSecurityFacade.isSubjectAnonymous(subject) && !RmiSecurityFacade.isKernelIdentity(subject)) {
         AuthenticatedUser au = RmiSecurityFacade.convertToAuthenticatedUser(subject);
         this.serviceContext = new VendorInfoSecurity(au);
      } else {
         this.serviceContext = VendorInfoSecurity.ANONYMOUS;
      }

   }

   public long getClientContextId() {
      return this.clientContextId;
   }

   public boolean needCredentials() {
      return this.needCredentials;
   }

   public void setNeedCredentials(boolean needCredentials) {
      this.needCredentials = needCredentials;
   }

   void contextEstablished() {
      if (!this.contextEstablished) {
         this.serviceContext = new SASServiceContext(this.clientContextId);
         this.serviceContext.premarshal();
         this.contextEstablished = true;
      }

   }

   public ServiceContext getServiceContext() {
      return this.serviceContext;
   }

   public String toString() {
      return this.clientContextId >= 0L ? this.serviceContext.toString() + " (context id==" + this.clientContextId + ")" : this.serviceContext.toString();
   }
}
