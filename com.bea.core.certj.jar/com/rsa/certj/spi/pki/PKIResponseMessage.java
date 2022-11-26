package com.rsa.certj.spi.pki;

import com.rsa.certj.cert.Certificate;
import com.rsa.jsafe.JSAFE_PrivateKey;
import java.util.Properties;

/** @deprecated */
public class PKIResponseMessage extends PKIMessage {
   private PKIStatusInfo statusInfo;
   private Certificate certificate;
   private JSAFE_PrivateKey privateKey;
   private Properties regInfo;
   private Certificate[] caCerts;

   /** @deprecated */
   public PKIResponseMessage(PKIStatusInfo var1) {
      this.statusInfo = var1;
   }

   /** @deprecated */
   public PKIStatusInfo getStatusInfo() {
      return this.statusInfo;
   }

   /** @deprecated */
   public Certificate getCertificate() {
      return this.certificate;
   }

   /** @deprecated */
   public void setCertificate(Certificate var1) {
      this.certificate = var1;
   }

   /** @deprecated */
   public JSAFE_PrivateKey getPrivateKey() {
      return this.privateKey;
   }

   /** @deprecated */
   public void setPrivateKey(JSAFE_PrivateKey var1) {
      this.privateKey = var1;
   }

   /** @deprecated */
   public Properties getRegInfo() {
      return this.regInfo;
   }

   /** @deprecated */
   public void setRegInfo(Properties var1) {
      this.regInfo = var1;
   }

   /** @deprecated */
   public Certificate[] getCACerts() {
      return this.caCerts;
   }

   /** @deprecated */
   public void setCACerts(Certificate[] var1) {
      this.caCerts = var1;
   }
}
