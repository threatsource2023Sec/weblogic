package com.rsa.certj.spi.pki;

import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.x.b;
import com.rsa.certj.x.d;
import java.util.Properties;

/** @deprecated */
public class PKIRequestMessage extends PKIMessage {
   /** @deprecated */
   public static final int PKI_POP_RA_VERIFIED = 0;
   /** @deprecated */
   public static final int PKI_POP_SIGNATURE = 1;
   /** @deprecated */
   public static final int PKI_POP_ENCRYPTION = 2;
   /** @deprecated */
   public static final int PKI_POP_KEY_AGREE = 3;
   private Certificate certificateTemplate;
   private int popType = -1;
   private Properties regInfo;

   /** @deprecated */
   public PKIRequestMessage(Certificate var1, Properties var2, boolean var3) {
      this.initialize(var1, var2, var3);
   }

   /** @deprecated */
   public PKIRequestMessage(Certificate var1, Properties var2) {
      this.initialize(var1, var2, b.c());
   }

   private void initialize(Certificate var1, Properties var2, boolean var3) {
      this.certificateTemplate = var1;
      this.regInfo = var2;
      if (var3 && var1 != null) {
         this.autoGenerateSerialNumber(this.certificateTemplate);
      }

   }

   /** @deprecated */
   public int getPopType() {
      return this.popType;
   }

   /** @deprecated */
   public void setPopType(int var1) {
      this.popType = var1;
   }

   /** @deprecated */
   public Certificate getCertificateTemplate() {
      return this.certificateTemplate;
   }

   /** @deprecated */
   public Properties getRegInfo() {
      return this.regInfo;
   }

   private void autoGenerateSerialNumber(Certificate var1) {
      X500Name var2 = ((X509Certificate)var1).getSubjectName();
      d.a(var2);

      try {
         ((X509Certificate)var1).setSubjectName(var2);
      } catch (CertificateException var4) {
      }

   }
}
