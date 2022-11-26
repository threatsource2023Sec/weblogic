package org.apache.xml.security.stax.securityEvent;

import org.apache.xml.security.stax.securityToken.SecurityToken;

public abstract class TokenSecurityEvent extends SecurityEvent {
   private SecurityToken securityToken;

   public TokenSecurityEvent(SecurityEventConstants.Event securityEventType) {
      super(securityEventType);
   }

   public SecurityToken getSecurityToken() {
      return this.securityToken;
   }

   public void setSecurityToken(SecurityToken securityToken) {
      this.securityToken = securityToken;
   }
}
