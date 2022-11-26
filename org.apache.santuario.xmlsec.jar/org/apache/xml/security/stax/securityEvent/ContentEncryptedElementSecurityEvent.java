package org.apache.xml.security.stax.securityEvent;

import java.util.List;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;

public class ContentEncryptedElementSecurityEvent extends AbstractSecuredElementSecurityEvent {
   public ContentEncryptedElementSecurityEvent(InboundSecurityToken inboundSecurityToken, boolean encrypted, List protectionOrder) {
      super(SecurityEventConstants.ContentEncrypted, inboundSecurityToken, protectionOrder, false, encrypted);
   }
}
