package com.rsa.certj.provider.pki.cmp;

import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.spi.pki.ProtectInfo;

/** @deprecated */
public final class CMPProtectInfo extends ProtectInfo {
   private static final String DEFAULT_PBM_ALGORITHM = "PBE/HMAC/SHA1/PKIXPBE-1024";
   boolean pbmProtected;
   private String algorithm;
   private X509Certificate senderCert;
   private X509Certificate recipCert;
   private char[] sharedSecret;
   private byte[] keyID;
   private X509Certificate[] caCerts;
   private DatabaseService database;

   /** @deprecated */
   public CMPProtectInfo(String var1, Certificate var2, Certificate var3, Certificate[] var4, DatabaseService var5) throws InvalidParameterException {
      this.pbmProtected = false;
      if (var1 == null) {
         throw new InvalidParameterException("CMPProtectInfo.CMPProtectInfo: algorithm should not be null.");
      } else {
         this.algorithm = var1;
         if (!(var2 instanceof X509Certificate)) {
            throw new InvalidParameterException("CMPProtectInfo.CMPProtectInfo: senderCert should be an instance of X509Certificate.");
         } else {
            this.senderCert = (X509Certificate)var2;
            if (var3 != null) {
               if (!(var3 instanceof X509Certificate)) {
                  throw new InvalidParameterException("CMPProtectInfo.CMPProtectInfo: recipCert should be an instance of X509Certificate.");
               }

               this.recipCert = (X509Certificate)var3;
            }

            if (var5 == null) {
               throw new InvalidParameterException("CMPProtectInfo.CMPProtectInfo: database should not be null.");
            } else {
               this.database = var5;
               this.setCACerts(var4, true);
            }
         }
      }
   }

   /** @deprecated */
   public CMPProtectInfo(String var1, char[] var2, byte[] var3, Certificate var4, Certificate[] var5, DatabaseService var6) throws InvalidParameterException {
      this.pbmProtected = true;
      if (var1 == null) {
         this.algorithm = "PBE/HMAC/SHA1/PKIXPBE-1024";
      } else {
         this.algorithm = var1;
      }

      if (var2 == null) {
         throw new InvalidParameterException("CMPProtectInfo.CMPProtectInfo: sharedSecret should not be null.");
      } else {
         this.sharedSecret = var2;
         if (var3 == null) {
            throw new InvalidParameterException("CMPProtectInfo.CMPProtectInfo: keyID should not be null.");
         } else {
            this.keyID = var3;
            if (var4 != null) {
               if (!(var4 instanceof X509Certificate)) {
                  throw new InvalidParameterException("CMPProtectInfo.CMPProtectInfo: recipCert should be an instance of X509Certificate.");
               }

               this.recipCert = (X509Certificate)var4;
            }

            this.setCACerts(var5, false);
            this.database = var6;
         }
      }
   }

   private void setCACerts(Certificate[] var1, boolean var2) throws InvalidParameterException {
      if (!var2 || var1 != null && var1.length != 0) {
         if (var1 != null) {
            this.caCerts = new X509Certificate[var1.length];

            for(int var3 = 0; var3 < var1.length; ++var3) {
               Certificate var4 = var1[var3];
               if (!(var4 instanceof X509Certificate)) {
                  throw new InvalidParameterException("CMPProtectInfo.setCACerts: every member of the caCerts array should be an instance of X509Certificate.");
               }

               this.caCerts[var3] = (X509Certificate)var1[var3];
            }

         }
      } else {
         throw new InvalidParameterException("CMPProtectInfo.setCACerts: caCerts should not be empty.");
      }
   }

   boolean pbmProtected() {
      return this.pbmProtected;
   }

   String getAlgorithm() {
      return this.algorithm;
   }

   X509Certificate getSenderCert() {
      return this.senderCert;
   }

   X509Certificate getRecipCert() {
      return this.recipCert;
   }

   X509Certificate[] getCACerts() {
      return this.caCerts;
   }

   char[] getSharedSecret() {
      return this.sharedSecret;
   }

   byte[] getKeyID() {
      return this.keyID;
   }

   DatabaseService getDatabase() {
      return this.database;
   }
}
