package com.bea.security.utils.authentication;

import com.bea.common.security.utils.encoders.BASE64Encoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordHashAlgorithm {
   String name = null;
   String nameInJCE = null;
   int hashSize = -1;
   int b64Size = -1;

   PasswordHashAlgorithm(String theName, String theJCEName, int theHashSize) {
      this.name = theName;
      this.nameInJCE = theJCEName;
      this.hashSize = theHashSize;
      this.calculateB64Size();
   }

   public PasswordHashAlgorithm(String theName) throws NoSuchAlgorithmException {
      this.name = theName;
      this.nameInJCE = theName;
      this.calculateHashSize();
      this.calculateB64Size();
   }

   private void calculateHashSize() throws NoSuchAlgorithmException {
      if (this.nameInJCE != null) {
         MessageDigest digest = MessageDigest.getInstance(this.nameInJCE);
         this.hashSize = digest.getDigestLength();
         if (this.hashSize <= 0) {
            digest.update("Just some stuff".getBytes());
            byte[] sampleHash = digest.digest();
            this.hashSize = sampleHash.length;
         }
      }

   }

   private void calculateB64Size() {
      if (this.hashSize != -1 && this.hashSize != 0) {
         byte[] testBytes = new byte[this.hashSize];

         for(int i = 0; i < this.hashSize; ++i) {
            testBytes[i] = 127;
         }

         String testEncoded = (new BASE64Encoder()).encodeBuffer(testBytes);
         this.b64Size = testEncoded.length();
      } else {
         this.b64Size = -1;
      }

   }

   public int getHashSize() {
      return this.hashSize;
   }

   public int getB64Size() {
      return this.b64Size;
   }

   public String getName() {
      return this.name;
   }

   public MessageDigest getMessageDigestInstance() throws NoSuchAlgorithmException {
      return MessageDigest.getInstance(this.nameInJCE);
   }
}
