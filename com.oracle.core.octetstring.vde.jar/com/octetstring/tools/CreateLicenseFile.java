package com.octetstring.tools;

public class CreateLicenseFile {
   private static final String NONCE = "octstring";

   public CreateLicenseFile(String company, String product, int expire) {
   }

   public static void main(String[] args) {
      new CreateLicenseFile(args[0], args[1], new Integer(args[2]));
   }

   private String keyToHex(byte[] binkey) {
      StringBuffer hexkey = new StringBuffer();

      for(int i = 0; i < binkey.length; ++i) {
         int oneval = binkey[i];
         String onevs = Integer.toHexString(oneval);
         if (onevs.length() > 2) {
            hexkey.append(onevs.substring(onevs.length() - 2));
         } else {
            hexkey.append(onevs);
         }

         if (i < binkey.length - 1) {
            hexkey.append(":");
         }
      }

      return hexkey.toString();
   }
}
