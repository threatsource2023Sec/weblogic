package weblogic.security;

import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.ServerAuthenticate;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;

public class Encrypt {
   static EncryptionService es = null;
   static ClearOrEncryptedService ces = null;

   public static void main(String[] args) {
      String password = null;
      if (args.length == 0) {
         password = ServerAuthenticate.promptValue("Password: ", false);
      } else if (args.length == 1) {
         password = args[0];
      } else {
         System.err.println("Usage: java\n\t[ -Dweblogic.RootDirectory=dirname ]\n\t[ -Dweblogic.management.allowPasswordEcho=true ]\n\tweblogic.security.Encrypt\n\t[ password ]");
      }

      es = SerializedSystemIni.getExistingEncryptionService();
      if (es == null) {
         System.err.println("Unable to initialize encryption service, verify you are in the domain directory or have specified the correct value for -Dweblogic.RootDirectory");
      } else {
         ces = new ClearOrEncryptedService(es);
         if (password != null) {
            System.out.println(ces.encrypt(password));
         }

      }
   }
}
