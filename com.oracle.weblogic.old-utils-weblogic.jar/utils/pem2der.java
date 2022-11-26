package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import weblogic.security.PEMInputStream;

public class pem2der {
   static String usage = "java utils.pem2der pemFile\n\nWrites file of same name with .der extension to the same directory \nas the .pem file.";

   public static void main(String[] argv) throws Exception {
      String orgFileName = null;
      String newFileName = null;
      if (argv.length < 1) {
         System.out.println(usage);
         System.exit(1);
      }

      try {
         orgFileName = argv[0];
         newFileName = argv[0];
         if (!orgFileName.substring(orgFileName.lastIndexOf("."), orgFileName.length()).equalsIgnoreCase(".pem")) {
            System.out.println("File [" + orgFileName + "] must end in .pem\n\n" + usage);
            System.exit(1);
         }
      } catch (ArrayIndexOutOfBoundsException var7) {
         System.out.println("File [" + orgFileName + "] must end in .pem\n\n" + usage);
         System.exit(1);
      } catch (StringIndexOutOfBoundsException var8) {
         System.out.println("File [" + orgFileName + "] must end in .pem\n\n" + usage);
         System.exit(1);
      }

      newFileName = orgFileName.substring(0, orgFileName.lastIndexOf(".")) + ".der";
      PEMInputStream in = null;

      try {
         in = new PEMInputStream(new FileInputStream(orgFileName));
      } catch (FileNotFoundException var6) {
         System.err.println(var6.toString());
         System.exit(1);
      }

      FileOutputStream out = new FileOutputStream(newFileName);
      System.out.println("Decoding");

      while(true) {
         int c = in.read();
         System.out.print(".");
         if (c == -1) {
            System.out.println("");
            out.close();
            return;
         }

         out.write(c);
      }
   }
}
