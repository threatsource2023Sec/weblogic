package org.apache.xml.security.stax.impl.securityToken;

import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class KeyNameSecurityToken extends AbstractInboundSecurityToken {
   private String keyName;

   public KeyNameSecurityToken(String keyName, InboundSecurityContext inboundSecurityContext) {
      super(inboundSecurityContext, IDGenerator.generateID((String)null), SecurityTokenConstants.KeyIdentifier_KeyName, false);
      this.keyName = keyName;
   }

   public SecurityTokenConstants.TokenType getTokenType() {
      return SecurityTokenConstants.KeyNameToken;
   }

   public String getKeyName() {
      return this.keyName;
   }
}
