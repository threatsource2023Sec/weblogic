package org.python.bouncycastle.crypto;

public enum PasswordConverter implements CharToByteConverter {
   ASCII {
      public String getType() {
         return "ASCII";
      }

      public byte[] convert(char[] var1) {
         return PBEParametersGenerator.PKCS5PasswordToBytes(var1);
      }
   },
   UTF8 {
      public String getType() {
         return "UTF8";
      }

      public byte[] convert(char[] var1) {
         return PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(var1);
      }
   },
   PKCS12 {
      public String getType() {
         return "PKCS12";
      }

      public byte[] convert(char[] var1) {
         return PBEParametersGenerator.PKCS12PasswordToBytes(var1);
      }
   };

   private PasswordConverter() {
   }

   // $FF: synthetic method
   PasswordConverter(Object var3) {
      this();
   }
}
