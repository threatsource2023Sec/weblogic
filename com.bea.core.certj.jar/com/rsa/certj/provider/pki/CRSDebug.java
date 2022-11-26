package com.rsa.certj.provider.pki;

import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.AttributeValueAssertion;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.spi.pki.PKIException;
import com.rsa.certj.spi.pki.PKIMessage;
import com.rsa.certj.spi.pki.PKIRequestMessage;
import com.rsa.certj.spi.pki.PKIResponseMessage;
import com.rsa.certj.spi.pki.ProtectInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/** @deprecated */
public final class CRSDebug extends CRS {
   private File messageStore;
   private String requestCN;

   /** @deprecated */
   public CRSDebug(String var1, File var2, File var3) throws InvalidParameterException {
      super(var1, var2);
      this.messageStore = var3;
   }

   /** @deprecated */
   public void saveMessage(byte[] var1, PKIMessage var2, ProtectInfo var3) throws PKIException {
      String var4;
      String var5;
      if (var2 instanceof PKIRequestMessage) {
         var5 = "req";
         var4 = this.getCN((X509Certificate)((PKIRequestMessage)var2).getCertificateTemplate());
         this.requestCN = var4;
      } else {
         if (!(var2 instanceof PKIResponseMessage)) {
            throw new PKIException("CRSDebug.saveMessage: message should be an instance of either PKIRequestMessage or PKIResponseMessage.");
         }

         var5 = "res";
         var4 = this.requestCN;
      }

      this.writeDataToFile(var1, this.getOutputFileName(var4, var5));
   }

   /** @deprecated */
   public void saveCertificate(PKIResponseMessage var1) throws PKIException {
      Certificate var2 = var1.getCertificate();
      if (var2 != null) {
         byte[] var3 = new byte[((X509Certificate)var2).getDERLen(0)];

         try {
            ((X509Certificate)var2).getDEREncoding(var3, 0, 0);
         } catch (CertificateException var7) {
         }

         String var4 = this.getCN((X509Certificate)var2);
         File var5 = new File(this.messageStore, var4 + ".cer");

         File var10002;
         StringBuilder var10003;
         for(int var6 = 0; var5.exists(); var5 = new File(var10002, var10003.append(var6).append(".cer").toString())) {
            var10002 = this.messageStore;
            var10003 = (new StringBuilder()).append(var4).append(".");
            ++var6;
         }

         this.writeDataToFile(var3, var5);
      }
   }

   /** @deprecated */
   public void saveData(byte[] var1, String var2) throws PKIException {
      this.writeDataToFile(var1, new File(this.messageStore, var2));
   }

   private File getOutputFileName(String var1, String var2) {
      File var3 = new File(this.messageStore, var1 + "." + var2 + ".ber");

      File var10002;
      StringBuilder var10003;
      for(int var4 = 0; var3.exists(); var3 = new File(var10002, var10003.append(var4).append(".ber").toString())) {
         var10002 = this.messageStore;
         var10003 = (new StringBuilder()).append(var1).append(".").append(var2);
         ++var4;
      }

      return var3;
   }

   private void writeDataToFile(byte[] var1, File var2) {
      FileOutputStream var3 = null;

      try {
         var3 = new FileOutputStream(var2);
         var3.write(var1);
      } catch (IOException var13) {
      } finally {
         if (var3 != null) {
            try {
               var3.close();
            } catch (IOException var12) {
            }
         }

      }

   }

   private String getCN(X509Certificate var1) {
      return var1 == null ? "unknown" : this.getCN(var1.getSubjectName());
   }

   private String getCN(X500Name var1) {
      if (var1 == null) {
         return "unknown";
      } else {
         AttributeValueAssertion var2 = var1.getAttribute(0);
         if (var2 == null) {
            return "unknwon";
         } else {
            try {
               return var2.getStringAttribute();
            } catch (NameException var4) {
               return "unknown";
            }
         }
      }
   }
}
