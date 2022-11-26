package com.bea.security.saml2.service.sso;

import java.io.Serializable;

public class AuthnRequestWrapper implements Serializable {
   private static final long serialVersionUID = 3602036581930159623L;
   private String ID;
   private String issuer;
   private boolean forceAuth;
   private boolean passive;
   private String protocolBinding;
   private String assertionConsumerServiceURL;
   private Integer assertionConsumerServiceIndex;

   public AuthnRequestWrapper(String ID, String protocolBinding, String assertionConsumerServiceURL, Integer assertionConsumerServiceIndex, String issuer, boolean forceAuth, boolean passive) {
      this.ID = ID;
      this.protocolBinding = protocolBinding;
      this.assertionConsumerServiceURL = assertionConsumerServiceURL;
      this.assertionConsumerServiceIndex = assertionConsumerServiceIndex;
      this.issuer = issuer;
      this.forceAuth = forceAuth;
      this.passive = passive;
   }

   public String getIssuer() {
      return this.issuer;
   }

   public boolean isForceAuthn() {
      return this.forceAuth;
   }

   public boolean isPassive() {
      return this.passive;
   }

   public String getID() {
      return this.ID;
   }

   public String getProtocolBinding() {
      return this.protocolBinding;
   }

   public String getAssertionConsumerServiceURL() {
      return this.assertionConsumerServiceURL;
   }

   public Integer getAssertionConsumerServiceIndex() {
      return this.assertionConsumerServiceIndex;
   }
}
