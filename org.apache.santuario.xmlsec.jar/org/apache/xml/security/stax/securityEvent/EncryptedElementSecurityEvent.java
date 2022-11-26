package org.apache.xml.security.stax.securityEvent;

import java.util.List;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;

public class EncryptedElementSecurityEvent extends AbstractSecuredElementSecurityEvent {
   public EncryptedElementSecurityEvent(InboundSecurityToken inboundSecurityToken, boolean encrypted, List protectionOrder) {
      super(SecurityEventConstants.EncryptedElement, inboundSecurityToken, protectionOrder, false, encrypted);
   }
}
