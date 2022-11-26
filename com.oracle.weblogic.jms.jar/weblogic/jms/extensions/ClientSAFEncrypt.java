package weblogic.jms.extensions;

import weblogic.jms.common.SecHelper;
import weblogic.security.internal.ServerAuthenticate;

public class ClientSAFEncrypt {
   private static final String QUIT_WORD = "quit";
   private static final String TAG_START = "<password-encrypted>";
   private static final String TAG_END = "</password-encrypted>";

   private String composeLine(String encryptedValue) {
      return "<password-encrypted>" + encryptedValue + "</password-encrypted>";
   }

   private void go(String[] args) throws Throwable {
      char[] key;
      String password;
      if (args.length <= 0) {
         password = ServerAuthenticate.promptValue("Password Key (\"quit\" to end): ", false);
         if (password == null || "quit".equals(password)) {
            return;
         }

         key = password.toCharArray();
      } else {
         key = args[0].toCharArray();
      }

      if (args.length <= 1) {
         while(true) {
            password = ServerAuthenticate.promptValue("Password (\"quit\" to end): ", false);
            if (password == null || "quit".equals(password)) {
               break;
            }

            System.out.println(this.composeLine(SecHelper.encryptString(key, password)));
         }
      } else {
         for(int lcv = 1; lcv < args.length; ++lcv) {
            String password = args[lcv];
            System.out.println(this.composeLine(SecHelper.encryptString(key, password)));
         }
      }

   }

   public static void main(String[] args) {
      ClientSAFEncrypt gen = new ClientSAFEncrypt();

      try {
         gen.go(args);
      } catch (Throwable var5) {
         int lcv = 0;

         for(Throwable cause = var5; cause != null; cause = cause.getCause()) {
            System.err.println("\nERROR: run threw an exception: level " + lcv);
            ++lcv;
            cause.printStackTrace();
         }
      }

   }
}
