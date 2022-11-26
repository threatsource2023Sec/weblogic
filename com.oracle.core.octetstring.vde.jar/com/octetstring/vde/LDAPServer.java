package com.octetstring.vde;

import com.octetstring.nls.Messages;
import com.octetstring.vde.acl.ACLChecker;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.frontend.LDAP;
import com.octetstring.vde.replication.Replication;
import com.octetstring.vde.schema.InitSchema;
import com.octetstring.vde.util.LogRotateTask;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import com.octetstring.vde.util.TimedActivityThread;
import java.net.Socket;
import java.util.Hashtable;

public class LDAPServer extends Thread {
   private int numThreads;
   private static final String INTERGINE_BUILD = "2094C";
   private static final String INTERGINE_VERSION = "v1.5.1 beta";
   private static Replication replication = null;
   private volatile LDAP ldap;

   public LDAPServer() {
      this.setPriority(2);
      this.numThreads = new Integer((String)ServerConfig.getInstance().get("vde.server.threads"));
   }

   public static Replication getReplication() {
      return replication;
   }

   public static void main(String[] args) throws Exception {
      printCopyright();
      ServerConfig.getInstance().init();
      LDAPServer ls = new LDAPServer();
      Logger.getInstance().log(5, ls, Messages.getString("VDE_Engine_Starting_3"));
      (new InitSchema()).init();
      ACLChecker.getInstance().initialize();
      BackendHandler.getInstance();
      replication = new Replication();
      replication.init();
      ls.start();
   }

   private static void printCopyright() {
      System.out.println("OctetString VDE Enterprise Edition - v1.5.1 beta build 2094C");
      System.out.println("Copyright (c) 2001 Octet String, Inc.");
      System.out.println("All Rights Reserved");
      System.out.println("");
   }

   public ConnectionHandler createConnectionHandler(Socket client) {
      while(this.ldap == null && this.isAlive()) {
         try {
            Thread.sleep(10L);
         } catch (Exception var3) {
         }
      }

      if (this.ldap == null) {
         return null;
      } else {
         return this.ldap.createConnectionHandler(client);
      }
   }

   public void run() {
      try {
         String configPort = (String)ServerConfig.getInstance().get("vde.server.port");
         int serverPort = Integer.parseInt(configPort);
         ServerConfig sci = ServerConfig.getInstance();
         String loghour = (String)sci.get("vde.logrotate.hour");
         String logmin = (String)sci.get("vde.logrotate.minute");
         String logmax = (String)sci.get("vde.logrotate.maxlogs");
         TimedActivityThread.getInstance().addActivity(new LogRotateTask(Integer.parseInt(loghour), Integer.parseInt(logmin), Integer.parseInt(logmax)));
         boolean useTLS = false;
         if (((String)ServerConfig.getInstance().get("vde.tls")).equals("1")) {
            useTLS = true;
         }

         String listenaddr = (String)ServerConfig.getInstance().get("vde.server.listenaddr");
         Hashtable config = new Hashtable();
         if (listenaddr != null) {
            config.put("host", listenaddr);
         }

         config.put("port", (new Integer(serverPort)).toString());
         if (useTLS) {
            config.put("secure", "1");
         }

         config.put("threads", (new Integer(this.numThreads)).toString());
         this.ldap = new LDAP(config);
         if (!DoSManager.getInstance().isAlive()) {
            DoSManager.getInstance().start();
         }
      } catch (Exception var10) {
         Logger.getInstance().log(0, this, "Critical Error: Printing Stack Trace.");
         Logger.getInstance().printStackTraceLog(var10);
         Logger.getInstance().printStackTraceConsole(var10);
      }

   }
}
