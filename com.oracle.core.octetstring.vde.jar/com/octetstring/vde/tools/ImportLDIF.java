package com.octetstring.vde.tools;

import com.octetstring.vde.acl.ACLChecker;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.schema.InitSchema;
import com.octetstring.vde.util.LDIF;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.IOException;

public class ImportLDIF {
   public static void main(String[] args) {
      if (args.length != 1) {
         System.err.println("Usage: ImportLDIF filename.ldif");
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
      (new LDIF()).importLDIF(args[0]);
      Logger.getInstance().flush();
      System.exit(0);
   }
}
