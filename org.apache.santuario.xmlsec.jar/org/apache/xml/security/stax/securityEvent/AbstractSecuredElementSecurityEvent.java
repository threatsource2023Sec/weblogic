package org.apache.xml.security.stax.securityEvent;

import java.util.List;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public abstract class AbstractSecuredElementSecurityEvent extends AbstractElementSecurityEvent {
   private boolean attachment;
   private boolean signed;
   private boolean encrypted;
   private SecurityToken securityToken;
   private List protectionOrder;

   public AbstractSecuredElementSecurityEvent(SecurityEventConstants.Event securityEventType, SecurityToken securityToken, List protectionOrder) {
      this(securityEventType, securityToken, protectionOrder, false, false);
   }

   public AbstractSecuredElementSecurityEvent(SecurityEventConstants.Event securityEventType, SecurityToken securityToken, List protectionOrder, boolean signed, boolean encrypted) {
      super(securityEventType);
      this.securityToken = securityToken;
      this.protectionOrder = protectionOrder;
      this.signed = signed;
      this.encrypted = encrypted;
   }

   public SecurityToken getSecurityToken() {
      return this.securityToken;
   }

   public void setSecurityToken(InboundSecurityToken securityToken) {
      this.securityToken = securityToken;
   }

   public boolean isSigned() {
      return this.signed;
   }

   public void setSigned(boolean signed) {
      this.signed = signed;
   }

   public List getProtectionOrder() {
      return this.protectionOrder;
   }

   public void setProtectionOrder(List protectionOrder) {
      this.protectionOrder = protectionOrder;
   }

   public boolean isEncrypted() {
      return this.encrypted;
   }

   public void setEncrypted(boolean encrypted) {
      this.encrypted = encrypted;
   }

   public boolean isAttachment() {
      return this.attachment;
   }

   public void setAttachment(boolean attachment) {
      this.attachment = attachment;
   }
}
