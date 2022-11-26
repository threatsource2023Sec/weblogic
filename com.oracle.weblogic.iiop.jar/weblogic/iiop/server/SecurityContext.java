package weblogic.iiop.server;

import weblogic.iiop.contexts.EstablishContext;
import weblogic.security.acl.internal.AuthenticatedSubject;

public final class SecurityContext {
   private long clientContextId;
   private EstablishContext establishedContext;
   private AuthenticatedSubject subject;

   public SecurityContext() {
   }

   public SecurityContext(long id, EstablishContext establishContext, AuthenticatedSubject subject) {
      this.clientContextId = id;
      this.establishedContext = establishContext;
      this.subject = subject;
   }

   public long getClientContextId() {
      return this.clientContextId;
   }

   public AuthenticatedSubject getSubject() {
      return this.subject;
   }

   public EstablishContext getEstablishContext() {
      return this.establishedContext;
   }

   public String toString() {
      return "SecurityContext (clientContext = " + this.clientContextId + ", subject = " + this.subject + ", ctx = " + this.establishedContext + ")";
   }
}
