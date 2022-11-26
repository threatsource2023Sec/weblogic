package com.octetstring.vde.tools;

import com.octetstring.vde.acl.ACLChecker;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.schema.InitSchema;
import com.octetstring.vde.util.LDIF;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.IOException;

public class ExportLDIF {
   public static void main(String[] args) {
      if (args.length != 2) {
         System.err.println("Usage: ExportLDIF suffix filename.ldif");
         System.exit(-1);
      }

      try {
         ServerConfig.getInstance().init();
      } catch (IOException var2) {
         System.out.println("Error initializing " + var2);
         System.exit(-1);
      }

      (new InitSchema()).init();
      ACLChecker.getInstance().initialize();
      BackendHandler.getInstance();
      (new LDIF()).exportLDIF(args[0], args[1]);
      Logger.getInstance().flush();
      System.exit(0);
   }
}
