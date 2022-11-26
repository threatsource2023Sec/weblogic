package org.apache.xml.security.stax.securityEvent;

import java.util.List;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;

public class SignedElementSecurityEvent extends AbstractSecuredElementSecurityEvent {
   public SignedElementSecurityEvent(InboundSecurityToken inboundSecurityToken, boolean signed, List protectionOrder) {
      super(SecurityEventConstants.SignedElement, inboundSecurityToken, protectionOrder, signed, false);
   }
}
