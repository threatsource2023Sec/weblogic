package org.opensaml.xmlsec.crypto;

import javax.annotation.Nonnull;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.crypto.SigningUtil;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;

public final class XMLSigningUtil {
   private XMLSigningUtil() {
   }

   public static byte[] signWithURI(@Nonnull Credential signingCredential, @Nonnull String algorithmURI, @Nonnull byte[] input) throws SecurityException {
      String jcaAlgorithmID = AlgorithmSupport.getAlgorithmID(algorithmURI);
      if (jcaAlgorithmID == null) {
         throw new SecurityException("Could not derive JCA algorithm identifier from algorithm URI");
      } else {
         boolean isHMAC = AlgorithmSupport.isHMAC(algorithmURI);
         return SigningUtil.sign(signingCredential, jcaAlgorithmID, isHMAC, input);
      }
   }

   public static boolean verifyWithURI(@Nonnull Credential verificationCredential, @Nonnull String algorithmURI, @Nonnull byte[] signature, @Nonnull byte[] input) throws SecurityException {
      String jcaAlgorithmID = AlgorithmSupport.getAlgorithmID(algorithmURI);
      if (jcaAlgorithmID == null) {
         throw new SecurityException("Could not derive JCA algorithm identifier from algorithm URI");
      } else {
         boolean isHMAC = AlgorithmSupport.isHMAC(algorithmURI);
         return SigningUtil.verify(verificationCredential, jcaAlgorithmID, isHMAC, signature, input);
      }
   }
}
