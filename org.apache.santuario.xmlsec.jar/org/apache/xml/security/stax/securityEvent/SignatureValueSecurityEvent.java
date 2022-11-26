package org.apache.xml.security.stax.securityEvent;

public class SignatureValueSecurityEvent extends SecurityEvent {
   private byte[] signatureValue;

   public SignatureValueSecurityEvent() {
      super(SecurityEventConstants.SignatureValue);
   }

   public byte[] getSignatureValue() {
      return this.signatureValue;
   }

   public void setSignatureValue(byte[] signatureValue) {
      this.signatureValue = signatureValue;
   }
}
