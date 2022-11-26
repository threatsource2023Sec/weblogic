package com.octetstring.vde.tools;

import com.octetstring.nls.Messages;
import com.octetstring.vde.acl.ACLChecker;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.replication.Replication;
import com.octetstring.vde.schema.InitSchema;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.LDIF;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class InitReplica {
   public static void main(String[] args) {
      if (args.length != 2) {
         System.err.println(Messages.getString("Usage__java_InitReplica_AgreementName_filename_1"));
         System.exit(-1);
      }

      InitReplica irobj = new InitReplica();

      try {
         ServerConfig.getInstance().init();
      } catch (IOException var11) {
         System.out.println("Error initializing " + var11);
         System.exit(-1);
      }

      (new InitSchema()).init();
      ACLChecker.getInstance().initialize();
      BackendHandler.getInstance();
      Replication rep = new Replication();
      rep.init();
      int ch = rep.getChangeHigh();
      DirectoryString base = rep.getReplicaBase(args[0]);
      String sbase = null;
      if (base != null) {
         sbase = base.toString();
      } else {
         Logger.getInstance().log(0, irobj, Messages.getString("Unable_to_locate_agreement___2") + args[0]);
         Logger.getInstance().flush();
         System.exit(0);
      }

      String ihome = System.getProperty("vde.home");
      String replicadataPath = null;
      if (ihome == null) {
         replicadataPath = "replicadata";
      } else {
         replicadataPath = ihome + "/replicadata";
      }

      try {
         FileOutputStream fos = new FileOutputStream(replicadataPath + "/" + args[0] + ".status");
         DataOutputStream dos = new DataOutputStream(fos);
         dos.writeInt(ch);
         dos.close();
      } catch (IOException var10) {
         Logger.getInstance().log(0, irobj, Messages.getString("Could_not_update_replica_status_file_for__8") + args[0]);
         Logger.getInstance().printStackTrace(var10);
      }

      (new LDIF()).exportLDIF(sbase, args[1]);
      Logger.getInstance().flush();
      System.exit(0);
   }
}
