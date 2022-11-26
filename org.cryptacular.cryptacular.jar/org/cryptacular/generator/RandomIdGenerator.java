package org.cryptacular.generator;

import java.security.SecureRandom;

public class RandomIdGenerator implements IdGenerator {
   public static final String DEFAULT_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
   private final int length;
   private final String charset;
   private final SecureRandom secureRandom;

   public RandomIdGenerator(int length) {
      this(length, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
   }

   public RandomIdGenerator(int length, String charset) {
      if (length < 1) {
         throw new IllegalArgumentException("Length must be positive");
      } else {
         this.length = length;
         if (charset != null && charset.length() >= 2 && charset.length() <= 128) {
            this.charset = charset;
            this.secureRandom = new SecureRandom();
            this.secureRandom.nextBytes(new byte[1]);
         } else {
            throw new IllegalArgumentException("Charset length must be in the range 2 - 128");
         }
      }
   }

   public String generate() {
      StringBuilder id = new StringBuilder(this.length);
      byte[] output = new byte[this.length];
      this.secureRandom.nextBytes(output);

      for(int i = 0; i < output.length && id.length() < this.length; ++i) {
         int index = 127 & output[i];
         id.append(this.charset.charAt(index % this.charset.length()));
      }

      return id.toString();
   }
}
