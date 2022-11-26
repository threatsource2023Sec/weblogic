package com.octetstring.vde;

import com.octetstring.nls.Messages;
import com.octetstring.vde.acl.ACLChecker;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.frontend.ListenerHandler;
import com.octetstring.vde.replication.Replication;
import com.octetstring.vde.schema.InitSchema;
import com.octetstring.vde.util.LogRotateTask;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import com.octetstring.vde.util.TimedActivityThread;

public class VDEServer extends Thread {
   private int numThreads;
   private static final String INTERGINE_BUILD = "2201C";
   private static final String INTERGINE_VERSION = "v1.5.1";
   private static Replication replication = null;

   public VDEServer() {
      this.setPriority(2);
   }

   public static Replication getReplication() {
      return replication;
   }

   public static void main(String[] args) throws Exception {
      printCopyright();
      ServerConfig.getInstance().init();
      VDEServer ds = new VDEServer();
      Logger.getInstance().log(5, ds, Messages.getString("VDE_Engine_Starting_3"));
      (new InitSchema()).init();
      ACLChecker.getInstance().initialize();
      BackendHandler.getInstance();
      replication = new Replication();
      replication.init();
      ds.start();
   }

   private static void printCopyright() {
      System.out.println("OctetString VDE - v1.5.1 build 2201C");
      System.out.println("Copyright (c) 2001 Octet String, Inc.");
      System.out.println("All Rights Reserved");
      System.out.println("");
   }

   public void run() {
      try {
         License myLicense = new License();
         if (!myLicense.checkLicense("Intergine") && !myLicense.checkLicense("VDE")) {
            Logger.getInstance().log(0, this, Messages.getString("License_Key_Invalid!_Email__11") + Messages.getString("sales@octetstring.com_for_an_updated_12") + Messages.getString("key._13"));
            Logger.getInstance().flush();
            System.exit(-1);
         }

         TimedActivityThread.getInstance().start();
         ServerConfig sci = ServerConfig.getInstance();
         String loghour = (String)sci.get("vde.logrotate.hour");
         String logmin = (String)sci.get("vde.logrotate.minute");
         String logmax = (String)sci.get("vde.logrotate.maxlogs");
         TimedActivityThread.getInstance().addActivity(new LogRotateTask(Integer.parseInt(loghour), Integer.parseInt(logmin), Integer.parseInt(logmax)));
         ListenerHandler.getInstance().init();
      } catch (Exception var6) {
         Logger.getInstance().log(0, this, Messages.getString("Critical_Error__Printing_Stack_Trace._14"));
         Logger.getInstance().printStackTraceLog(var6);
         Logger.getInstance().printStackTraceConsole(var6);
      }

   }
}
