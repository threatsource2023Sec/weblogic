package com.rsa.certj.provider.pki.cmp;

import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.AttributeValueAssertion;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.crmf.CertTemplate;
import com.rsa.certj.spi.pki.PKIMessage;
import com.rsa.certj.spi.pki.PKIResponseMessage;
import com.rsa.certj.spi.pki.ProtectInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/** @deprecated */
public final class CMPDebug extends CMP {
   private File messageStore;
   private String requestCN;
   private static final String UNKNOWN = "unknown";

   /** @deprecated */
   public CMPDebug(String var1, File var2, File var3) throws InvalidParameterException {
      super(var1, var2);
      this.messageStore = var3;
   }

   /** @deprecated */
   public void saveMessage(byte[] var1, PKIMessage var2, ProtectInfo var3) throws CMPException {
      if (var1 != null) {
         int var4;
         String var5;
         if (var2 instanceof CMPRequestCommon) {
            var4 = ((CMPRequestCommon)var2).getMessageType();
            if (var3 == null) {
               var5 = "unknown";
            } else {
               var5 = this.getCN((CMPRequestCommon)var2, (CMPProtectInfo)var3);
            }

            this.requestCN = var5;
         } else {
            if (!(var2 instanceof CMPResponseCommon)) {
               throw new CMPException("CMPDebug.saveMessage: message should be an instance of either CMPRequestCommon or CMPResponseCommon.");
            }

            var4 = ((CMPResponseCommon)var2).getMessageType();
            var5 = this.requestCN;
         }

         this.writeDataToFile(var1, this.getOutputFileName(var5, var4));
      }
   }

   /** @deprecated */
   public void saveCertificate(PKIResponseMessage var1) throws CMPException {
      if (var1 != null) {
         Certificate var2 = var1.getCertificate();
         if (var2 != null) {
            byte[] var3 = new byte[((X509Certificate)var2).getDERLen(0)];

            try {
               ((X509Certificate)var2).getDEREncoding(var3, 0, 0);
            } catch (CertificateException var7) {
            }

            int var4 = 0;
            String var5 = this.getCN((X509Certificate)var2);

            File var10002;
            StringBuilder var10003;
            File var6;
            for(var6 = new File(this.messageStore, var5 + "." + var4 + ".cer"); var6.exists(); var6 = new File(var10002, var10003.append(var4).append(".cer").toString())) {
               var10002 = this.messageStore;
               var10003 = (new StringBuilder()).append(var5).append(".");
               ++var4;
            }

            this.writeDataToFile(var3, var6);
         }
      }
   }

   /** @deprecated */
   public void saveData(byte[] var1, String var2) throws CMPException {
      if (var1 != null) {
         if (var2 == null) {
            throw new CMPException("CMPDebug.saveData: fileName should not be null.");
         } else {
            this.writeDataToFile(var1, new File(this.messageStore, var2));
         }
      }
   }

   private String messageTypeString(int var1) {
      switch (var1) {
         case 0:
            return "ir";
         case 1:
            return "ip";
         case 2:
            return "cr";
         case 3:
            return "cp";
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 20:
         case 21:
         case 22:
         default:
            return "unknown";
         case 11:
            return "rr";
         case 12:
            return "rp";
         case 19:
            return "pkiconf";
         case 23:
            return "error";
         case 24:
            return "certconf";
      }
   }

   private int messageStartIndex(int var1) {
      switch (var1) {
         case 0:
         case 1:
            return -1;
         case 2:
         case 3:
         case 11:
         case 12:
            return 1;
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         default:
            return 0;
      }
   }

   private File getOutputFileName(String var1, int var2) {
      String var3 = this.messageTypeString(var2);
      int var4 = this.messageStartIndex(var2);
      if (var4 < 0) {
         return new File(this.messageStore, var1 + "." + var3 + ".ber");
      } else {
         File var10002;
         StringBuilder var10003;
         File var5;
         for(var5 = new File(this.messageStore, var1 + "." + var3 + var4 + ".ber"); var5.exists(); var5 = new File(var10002, var10003.append(var4).append(".ber").toString())) {
            var10002 = this.messageStore;
            var10003 = (new StringBuilder()).append(var1).append(".").append(var3);
            ++var4;
         }

         return var5;
      }
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

   private String getCN(CMPRequestCommon var1, CMPProtectInfo var2) {
      switch (var1.getMessageType()) {
         case 0:
            CertTemplate var3 = ((CMPCertRequestCommon)var1).getCertTemplate();
            return this.getCN(var3.getSubjectName());
         case 2:
         case 11:
            return this.getCN(var2.getSenderCert());
         case 24:
            X509Certificate var4 = (X509Certificate)((CMPCertConfirmMessage)var1).getCertificateReturned();
            if (var4 != null) {
               return this.getCN(var4);
            } else {
               if (!var2.pbmProtected()) {
                  return this.getCN(var2.getSenderCert());
               }

               return "unknown";
            }
         default:
            return "unknown";
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
            return "unknown";
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
