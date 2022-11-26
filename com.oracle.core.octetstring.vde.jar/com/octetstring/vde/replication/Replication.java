package com.octetstring.vde.replication;

import com.octetstring.nls.Messages;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.LDIF;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import javax.security.auth.Subject;

public class Replication extends Thread {
   BackendChangeLog bcl = null;
   Vector agreements = null;
   Consumer currentAgreement = null;
   String replicadataPath = null;
   private boolean reloadConfig = false;
   private Properties reloadOverrideProps;
   private Object lock = new Object();
   private Subject sslSubject = null;
   private boolean running = false;

   public Replication() {
      super("VDE Replication Thread");
   }

   public void init() {
      this.init((Properties)null, (Subject)null);
   }

   public void init(Properties overrideProps, Subject sslSubject) {
      this.sslSubject = sslSubject;
      if (((String)ServerConfig.getInstance().get("vde.changelog")).equals("1")) {
         Hashtable bclConfig = new Hashtable();
         DirectoryString suffix = new DirectoryString((String)ServerConfig.getInstance().get("vde.changelog.suffix"));
         bclConfig.put("suffix", suffix);
         this.bcl = new BackendChangeLog(bclConfig, this);
         BackendHandler.getInstance().registerBackend(suffix, this.bcl);
         BackendHandler.getInstance().registerEntryChangesListener(this.bcl);
      }

      this.initAgreements(overrideProps);
      if (this.bcl != null) {
         this.start();
      }

   }

   private void initAgreements(Properties overrideProps) {
      ServerConfig serverConfig = ServerConfig.getInstance();
      String myid = (String)serverConfig.get("vde.server.name");
      String ihome = System.getProperty("vde.home");
      Properties replicaProp = new Properties();

      String nag;
      try {
         String name = (String)serverConfig.get("vde.replicas");
         nag = ihome == null ? name : ihome + "/" + name;
         this.replicadataPath = ihome == null ? "replicadata" : ihome + "/replicadata";
         FileInputStream is = new FileInputStream(nag);
         replicaProp.load(is);
         is.close();
      } catch (Exception var18) {
         Logger.getInstance().log(0, this, Messages.getString("Error_parsing_Replication_properties._7"));
      }

      if (overrideProps != null) {
         Iterator it = overrideProps.keySet().iterator();

         while(it.hasNext()) {
            nag = (String)it.next();
            replicaProp.setProperty(nag, overrideProps.getProperty(nag));
         }
      }

      File rdpf = new File(this.replicadataPath);
      if (this.bcl != null && !rdpf.exists()) {
         rdpf.mkdir();
         Logger.getInstance().log(5, this, Messages.getString("Created_Replica_Data_Directory._8"));
      }

      nag = System.getProperty("replica.num");
      if (nag == null) {
         nag = (String)replicaProp.get("replica.num");
      }

      int rags = new Integer(nag);
      this.agreements = new Vector();

      for(int raCount = 0; raCount < rags; ++raCount) {
         String replicaKeyPrefix = "replica." + raCount + ".";
         int prefixLen = replicaKeyPrefix.length();
         Enumeration keys = replicaProp.keys();
         Hashtable raConfig = new Hashtable();

         String key;
         String configKey;
         while(keys.hasMoreElements()) {
            key = (String)keys.nextElement();
            if (key.startsWith(replicaKeyPrefix)) {
               configKey = key.substring(prefixLen);
               raConfig.put(configKey, replicaProp.get(key));
            }
         }

         keys = System.getProperties().keys();

         while(keys.hasMoreElements()) {
            key = (String)keys.nextElement();
            if (key.startsWith(replicaKeyPrefix)) {
               configKey = key.substring(prefixLen);
               raConfig.put(configKey, System.getProperty(key));
            }
         }

         BackendHandler.getInstance().clearReplicas();
         Consumer con = new Consumer(raConfig);
         if (con.getMasterID().equals(myid)) {
            try {
               DataInputStream dis = new DataInputStream(new FileInputStream(this.replicadataPath + "/" + con.getAgreementName() + ".status"));
               int lastSent = dis.readInt();
               con.setChangeSent(lastSent);
               dis.close();
            } catch (IOException var17) {
               Logger.getInstance().log(3, this, Messages.getString("Consumer____17") + con.getAgreementName() + Messages.getString("____No_record_of_changes_transmitted._18"));
            }

            this.agreements.addElement(con);
         } else if (con.getConsumerID().equals(myid)) {
            BackendHandler.getInstance().addReplica(con.getReplicaBase(), con);
         }
      }

   }

   public void setupAgreement(String agreementname) {
      this.setupAgreement(agreementname, (String)null);
   }

   public void setupAgreement(String agreementname, String filename) {
      BackendHandler.getInstance().lockWrites();

      try {
         int ch = this.getChangeHigh();
         DirectoryString base = this.getReplicaBase(agreementname);
         String sbase = null;
         if (base == null) {
            Logger.getInstance().log(0, this, Messages.getString("Unable_to_locate_agreement___19") + agreementname);
            return;
         }

         sbase = base.toString();
         String ihome = System.getProperty("vde.home");
         String replicadataPath = ihome == null ? "replicadata" : ihome + "/replicadata";

         try {
            FileOutputStream fos = new FileOutputStream(replicadataPath + "/" + agreementname + ".status");
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeInt(ch);
            dos.close();
            Consumer ag = this.getReplicaByName(agreementname);
            ag.setChangeSent(ch);
         } catch (IOException var14) {
            Logger.getInstance().log(0, this, Messages.getString("Could_not_update_replica_status_file_for__25") + agreementname);
            Logger.getInstance().printStackTrace(var14);
            return;
         }

         String replicaLDIF = filename != null ? filename : replicadataPath + "/" + agreementname + ".init.ldif";
         (new LDIF()).exportLDIF(sbase, replicaLDIF);
      } finally {
         BackendHandler.getInstance().unlockWrites();
      }

   }

   public int getChangeHigh() {
      return this.bcl != null ? this.bcl.getChangeHigh() : -1;
   }

   public DirectoryString getReplicaBase(String agName) {
      Enumeration agEnum = this.agreements.elements();

      Consumer oneAg;
      do {
         if (!agEnum.hasMoreElements()) {
            return null;
         }

         oneAg = (Consumer)agEnum.nextElement();
      } while(!oneAg.getAgreementName().equals(agName));

      return oneAg.getReplicaBase();
   }

   public Consumer getReplicaByName(String agName) {
      Enumeration agEnum = this.agreements.elements();

      Consumer oneAg;
      do {
         if (!agEnum.hasMoreElements()) {
            return null;
         }

         oneAg = (Consumer)agEnum.nextElement();
      } while(!oneAg.getAgreementName().equals(agName));

      return oneAg;
   }

   public void run() {
      if (this.agreements != null && !this.agreements.isEmpty()) {
         this.running = true;

         while(this.running) {
            try {
               if (this.reloadConfig) {
                  this.reloadConfig = false;
                  this.initAgreements(this.reloadOverrideProps);
                  this.reloadOverrideProps = null;
                  if (this.agreements == null || this.agreements.isEmpty()) {
                     this.running = false;
                     return;
                  }
               }

               int high = this.bcl.getChangeHigh();
               Enumeration agEnum = this.agreements.elements();
               int curlowkeep = -1;

               while(agEnum.hasMoreElements()) {
                  Consumer oneAg = (Consumer)agEnum.nextElement();
                  if (curlowkeep == -1 || oneAg.getChangeSent() < curlowkeep) {
                     curlowkeep = oneAg.getChangeSent();
                  }

                  if (oneAg.isActive() && oneAg.isImmediate() && high > oneAg.getChangeSent()) {
                     synchronized(this.lock) {
                        if (!this.running) {
                           return;
                        }

                        Replicator repl = oneAg.getReplicator();
                        if (repl == null) {
                           repl = new Replicator(this.replicadataPath, oneAg, this.bcl);
                           repl.setSSLSubject(this.sslSubject);
                           oneAg.setReplicator(repl);
                        }

                        try {
                           this.setCurrentAgreement(oneAg);
                           repl.run();
                        } catch (Exception var9) {
                           oneAg.setReplicator((Replicator)null);
                           throw var9;
                        }
                     }
                  }
               }

               this.bcl.setLowKeep(curlowkeep);
               this.wait30sec(high);
            } catch (Exception var11) {
               Logger.getInstance().log(3, this, Messages.getString("Error_replicating_error_is") + var11.getMessage());
               Logger.getInstance().printStackTraceLog(var11);
            }
         }

      }
   }

   public void reload() {
      this.reload((Properties)null);
   }

   public void reload(Properties overrideProps) {
      this.reloadOverrideProps = overrideProps;
      this.reloadConfig = true;
      if (!this.running) {
         this.initAgreements(overrideProps);
         overrideProps = null;
         this.start();
      }

   }

   private synchronized void wait30sec(int high) {
      try {
         if (this.bcl.getChangeHigh() > high) {
            return;
         }

         this.wait(30000L);
      } catch (InterruptedException var3) {
      }

   }

   public void addReplica(Hashtable raConfig) {
      String myid = (String)ServerConfig.getInstance().get("vde.server.name");
      Consumer con = new Consumer(raConfig);
      if (con.getMasterID().equals(myid)) {
         try {
            String statusFile = this.replicadataPath + "/" + con.getAgreementName() + ".status";
            DataInputStream dis = new DataInputStream(new FileInputStream(statusFile));
            int lastSent = dis.readInt();
            con.setChangeSent(lastSent);
            dis.close();
         } catch (IOException var7) {
            Logger.getInstance().log(3, this, Messages.getString("Consumer____17") + con.getAgreementName() + Messages.getString("____No_record_of_changes_transmitted._18"));
         }

         this.agreements.addElement(con);
      }

   }

   public void setCurrentAgreement(Consumer agreement) {
      this.currentAgreement = agreement;
   }

   public String getCurrentAgreementName() {
      return this.currentAgreement.getAgreementName();
   }

   public synchronized void wakeUp() {
      this.notifyAll();
   }

   public void shutdown() {
      synchronized(this.lock) {
         this.running = false;
         if (this.bcl != null) {
            this.bcl.shutdown();
         }

      }
   }
}
