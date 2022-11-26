package org.opensaml.saml.common.messaging.context;

public abstract class AbstractAuthenticatableSAMLEntityContext extends AbstractSAMLEntityContext {
   private boolean authenticated;

   public boolean isAuthenticated() {
      return this.authenticated;
   }

   public void setAuthenticated(boolean flag) {
      this.authenticated = flag;
   }
}
