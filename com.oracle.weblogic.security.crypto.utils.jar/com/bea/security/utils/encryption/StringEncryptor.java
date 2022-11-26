package com.bea.security.utils.encryption;

import com.bea.common.security.utils.encoders.BASE64Decoder;
import com.bea.common.security.utils.encoders.BASE64Encoder;
import javax.crypto.Cipher;

public class StringEncryptor {
   private Cipher ecipher;
   private Cipher dcipher;
   private String prefix;

   public StringEncryptor(String passPhrase, String algorithm) throws Exception {
      this.ecipher = EncryptedStreamFactory.createCipher(passPhrase.toCharArray(), algorithm, 1);
      this.dcipher = EncryptedStreamFactory.createCipher(passPhrase.toCharArray(), algorithm, 2);
      this.prefix = "{" + this.ecipher.getAlgorithm() + "}";
   }

   public String encrypt(String str) throws Exception {
      byte[] utf8 = str.getBytes("UTF8");
      byte[] enc = this.ecipher.doFinal(utf8);
      return this.prefix + (new BASE64Encoder()).encodeBuffer(enc);
   }

   public String decrypt(String str) throws Exception {
      if (!str.startsWith(this.prefix)) {
         throw new Exception("Encrypted string format error");
      } else {
         byte[] dec = (new BASE64Decoder()).decodeBuffer(str.substring(this.prefix.length()));
         byte[] utf8 = this.dcipher.doFinal(dec);
         return new String(utf8, "UTF8");
      }
   }
}
