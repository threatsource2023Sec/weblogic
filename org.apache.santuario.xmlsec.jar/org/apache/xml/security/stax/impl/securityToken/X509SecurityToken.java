package org.apache.xml.security.stax.impl.securityToken;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class X509SecurityToken extends AbstractInboundSecurityToken {
   private final SecurityTokenConstants.TokenType tokenType;

   protected X509SecurityToken(SecurityTokenConstants.TokenType tokenType, InboundSecurityContext inboundSecurityContext, String id, SecurityTokenConstants.KeyIdentifier keyIdentifier, boolean includedInMessage) {
      super(inboundSecurityContext, id, keyIdentifier, includedInMessage);
      this.tokenType = tokenType;
   }

   public boolean isAsymmetric() throws XMLSecurityException {
      return true;
   }

   public SecurityTokenConstants.TokenType getTokenType() {
      return this.tokenType;
   }
}
