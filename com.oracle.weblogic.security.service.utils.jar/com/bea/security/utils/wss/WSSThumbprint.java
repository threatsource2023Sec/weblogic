package com.bea.security.utils.wss;

import com.bea.common.security.ApiLogger;
import com.bea.security.utils.DigestUtils;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

public class WSSThumbprint {
   private WSSThumbprint() {
   }

   public static String generateThumbprint(X509Certificate certificate) throws WSSThumbprintException {
      if (certificate == null) {
         throw new IllegalArgumentException(ApiLogger.getIllegalArgumentSpecified("generateThumbprint", "X.509 certificate", "null"));
      } else {
         byte[] data;
         try {
            data = certificate.getEncoded();
         } catch (CertificateEncodingException var3) {
            throw new WSSThumbprintException(ApiLogger.getFailedToGenerateThumbprint("certificate encoding error"));
         }

         return generateThumbprint(data);
      }
   }

   public static String generateThumbprint(byte[] data) throws WSSThumbprintException {
      if (data != null && data.length >= 1) {
         try {
            String thumbprint = DigestUtils.digest(data, "SHA1");
            return thumbprint;
         } catch (NoSuchAlgorithmException var3) {
            throw new WSSThumbprintException(ApiLogger.getFailedToGenerateThumbprint(var3.getMessage()));
         }
      } else {
         throw new IllegalArgumentException(ApiLogger.getIllegalArgumentSpecified("generateThumbprint", "Certificate data", "null or empty"));
      }
   }
}
