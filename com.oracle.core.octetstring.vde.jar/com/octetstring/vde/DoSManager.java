package com.octetstring.vde;

import com.octetstring.nls.Messages;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class DoSManager extends Thread {
   private static DoSManager instance;
   private HashMap activeUsers = new HashMap();
   private HashMap activeIPs = new HashMap();
   private HashSet activeConnections = new HashSet();
   private HashSet exemptIPs = new HashSet();
   private HashSet exemptUsers = new HashSet();
   private boolean enabled = true;
   Logger logger = Logger.getInstance();
   private int maxOpsPerCon = 0;
   private int maxConcOpsPerCon = 0;
   private int maxConPerSubject = 0;
   private int maxConPerIP = 0;
   private int maxConnections = 0;
   private int ratePeriod = 100;

   public DoSManager(String name) {
      super(name);
      instance = this;
      this.setPriority(6);
   }

   public static DoSManager getInstance() {
      if (instance == null) {
         instance = new DoSManager("DoSManager");
      }

      return instance;
   }

   public void setEnabled(boolean enforce) {
      this.enabled = enforce;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public boolean isOpExceeded(Connection con) {
      if (this.enabled) {
         int opcount = con.getLastOp();
         if (this.maxConcOpsPerCon == -1 || this.maxOpsPerCon > 0 && opcount >= this.maxOpsPerCon) {
            String address = con.getAuthCred().getIPAddress();
            String user = con.getAuthCred().getUser().toString().toLowerCase();
            if (!this.exemptUsers.contains(user) && !this.exemptIPs.contains(address)) {
               this.logger.log(3, this, Messages.getString("DoSManager_Maximum_operations_per_connection_exceeded_(_2") + user + "/" + address + ", " + opcount + "/" + this.maxOpsPerCon + ").");
               this.unregisterConnection(con);
               return true;
            }
         }
      }

      return false;
   }

   public boolean isUserConExceeded(DirectoryString user) {
      if (!this.enabled) {
         return false;
      } else if (user != null && this.exemptUsers.contains(user.toString().toLowerCase())) {
         return false;
      } else {
         DoSTracker curUserCons = (DoSTracker)this.activeUsers.get(user);
         if (curUserCons == null) {
            return false;
         } else {
            return this.maxConPerSubject == -1 || curUserCons.getConCount() >= this.maxConPerSubject && this.maxConPerSubject > 0;
         }
      }
   }

   public boolean isIPConExceeded(String ip) {
      if (!this.enabled) {
         return false;
      } else if (ip != null && this.exemptIPs.contains(ip)) {
         return false;
      } else {
         DoSTracker curIPCons = (DoSTracker)this.activeIPs.get(ip);
         if (curIPCons == null) {
            return false;
         } else {
            return this.maxConPerIP == -1 || curIPCons.getConCount() >= this.maxConPerIP && this.maxConPerIP > 0;
         }
      }
   }

   public boolean registerSubject(Connection con) {
      DirectoryString user = con.getAuthCred().getUser();
      if (user == null || user.length() == 0) {
         user = new DirectoryString("cn=Anonymous");
      }

      String strUser = user.toString().toLowerCase();
      if (this.isUserConExceeded(user) && !this.exemptUsers.contains(strUser)) {
         this.logger.log(3, this, Messages.getString("DoSManager_Maximum_connections_per_subject(_8") + user + Messages.getString("DoSManager_)_exceeded._9"));
         return false;
      } else {
         synchronized(this.activeUsers) {
            DoSTracker curUserCons = (DoSTracker)this.activeUsers.get(user);
            if (curUserCons == null) {
               curUserCons = new DoSTracker();
               this.activeUsers.put(user, curUserCons);
            }

            synchronized(curUserCons) {
               curUserCons.add(con);
            }

            return true;
         }
      }
   }

   public boolean registerWebRequest(String address, DirectoryString user) {
      new Connection();
      if (this.enabled && !this.exemptIPs.contains(address) && !this.exemptUsers.contains(user)) {
         if (this.maxConnections > 0 && this.activeConnections.size() > this.maxConnections) {
            this.logger.log(3, this, Messages.getString("DoSManager_Maximum_concurrent_connections(_10") + this.maxConnections + Messages.getString("DoSManager_)_exceeded._11"));
            return false;
         }

         if (this.isIPConExceeded(address)) {
            this.logger.log(3, this, Messages.getString("DoSManager_Maximum_connections_per_IP_address(_12") + address + Messages.getString("DoSManager_)_exceeded._13"));
            return false;
         }
      }

      synchronized(this.activeIPs) {
         DoSTracker curIPCons = (DoSTracker)this.activeIPs.get(address);
         if (curIPCons == null) {
            curIPCons = new DoSTracker();
            this.activeIPs.put(address, curIPCons);
         }

         curIPCons.incrConnections();
      }

      if (user == null || user.length() == 0) {
         user = new DirectoryString("cn=Anonymous");
      }

      String strUser = user.toString().toLowerCase();
      if (this.isUserConExceeded(user) && !this.exemptUsers.contains(strUser)) {
         this.logger.log(3, this, Messages.getString("DoSManager_Maximum_connections_per_subject(_8") + user + Messages.getString("DoSManager_)_exceeded._9"));
         return false;
      } else {
         synchronized(this.activeUsers) {
            DoSTracker curUserCons = (DoSTracker)this.activeUsers.get(user);
            if (curUserCons == null) {
               curUserCons = new DoSTracker();
               this.activeUsers.put(user, curUserCons);
            }

            curUserCons.incrConnections();
            return true;
         }
      }
   }

   public boolean registerConnection(Connection con) {
      String address = con.getAuthCred().getIPAddress();
      String user = con.getAuthCred().getUser().toString().toLowerCase();
      if (this.enabled && !this.exemptIPs.contains(address) && !this.exemptUsers.contains(user)) {
         if (this.maxConnections > 0 && this.activeConnections.size() > this.maxConnections) {
            this.logger.log(3, this, Messages.getString("DoSManager_Maximum_concurrent_connections(_10") + this.maxConnections + Messages.getString("DoSManager_)_exceeded._11"));
            return false;
         }

         if (this.isIPConExceeded(address)) {
            this.logger.log(3, this, Messages.getString("DoSManager_Maximum_connections_per_IP_address(_12") + address + Messages.getString("DoSManager_)_exceeded._13"));
            return false;
         }
      }

      synchronized(this.activeConnections) {
         this.activeConnections.add(con);
      }

      synchronized(this.activeIPs) {
         DoSTracker curIPCons = (DoSTracker)this.activeIPs.get(address);
         if (curIPCons == null) {
            curIPCons = new DoSTracker();
            this.activeIPs.put(address, curIPCons);
         }

         synchronized(curIPCons) {
            curIPCons.add(con);
         }
      }

      if (this.registerSubject(con)) {
         return true;
      } else {
         return this.exemptIPs.contains(address) || this.exemptUsers.contains(user);
      }
   }

   public void unregisterSubject(Connection con, DirectoryString subject) {
      if (subject == null || subject.length() == 0) {
         subject = new DirectoryString("cn=Anonymous");
      }

      String address = con.getAuthCred().getIPAddress();
      this.logger.log(7, this, Messages.getString("DoSManager_UnBind___15") + subject.toString() + "/" + address + ".");
      synchronized(this.activeUsers) {
         DoSTracker curUserCons = (DoSTracker)this.activeUsers.get(subject);
         if (curUserCons != null && curUserCons.contains(con)) {
            curUserCons.remove(con);
         }

      }
   }

   public void unregisterConnection(Connection con) {
      DirectoryString user = con.getAuthCred().getUser();
      if (user == null || user.length() == 0) {
         user = new DirectoryString("cn=Anonymous");
      }

      String address = con.getAuthCred().getIPAddress();
      synchronized(this.activeConnections) {
         if (this.activeConnections.contains(con)) {
            this.activeConnections.remove(con);
         }
      }

      DoSTracker curUserCons = (DoSTracker)this.activeUsers.get(user);
      if (curUserCons != null && curUserCons.contains(con)) {
         this.unregisterSubject(con, user);
         if (curUserCons.getActiveConCount() == 0) {
            this.activeUsers.remove(user);
            curUserCons = null;
         }
      }

      synchronized(this.activeIPs) {
         DoSTracker curIPCons = (DoSTracker)this.activeIPs.get(address);
         if (curIPCons != null) {
            synchronized(curIPCons) {
               if (curIPCons.contains(con)) {
                  curIPCons.remove(con);
               }

               if (curIPCons.getActiveConCount() == 0) {
                  this.activeIPs.remove(address);
                  curIPCons = null;
               }
            }
         }

      }
   }

   public void run() {
      ServerConfig sc = ServerConfig.getInstance();
      String strEnabled = (String)sc.get("vde.quota.check");
      String strMaxOpsPerCon = (String)sc.get("vde.quota.max.opspercon");
      String strMaxConPerSubject = (String)sc.get("vde.quota.max.conpersubject");
      String strMaxConPerIP = (String)sc.get("vde.quota.max.conperip");
      String strRatePeriod = (String)sc.get("vde.quota.period");
      String strMaxConnections = (String)sc.get("vde.quota.max.connections");
      String strExemptIPs = (String)sc.get("vde.quota.exemptips");
      String nextIP;
      if (strExemptIPs != null) {
         StringTokenizer exempts = new StringTokenizer(strExemptIPs, ",", false);

         while(exempts.hasMoreTokens()) {
            nextIP = exempts.nextToken();
            this.exemptIPs.add(nextIP);
         }
      }

      String strExemptUsers = (String)sc.get("vde.quota.exemptusers");
      nextIP = (String)sc.get("vde.rootuser");
      if (strExemptUsers != null) {
         StringTokenizer exempts = new StringTokenizer(strExemptUsers, "|", false);

         while(exempts.hasMoreTokens()) {
            String nextUser = exempts.nextToken();
            this.exemptUsers.add(nextUser.toLowerCase());
         }
      }

      if (strEnabled != null) {
         if (strEnabled.equalsIgnoreCase("1")) {
            this.enabled = true;
         } else {
            this.enabled = false;
         }
      } else {
         this.enabled = false;
      }

      if (strMaxOpsPerCon != null) {
         this.maxOpsPerCon = Integer.parseInt(strMaxOpsPerCon);
      } else {
         this.maxOpsPerCon = 0;
      }

      if (strMaxConPerSubject != null) {
         this.maxConPerSubject = Integer.parseInt(strMaxConPerSubject);
      } else {
         this.maxConPerSubject = 0;
      }

      if (strMaxConPerIP != null) {
         this.maxConPerIP = Integer.parseInt(strMaxConPerIP);
      } else {
         this.maxConPerIP = 0;
      }

      if (strMaxConnections != null) {
         this.maxConnections = Integer.parseInt(strMaxConnections);
      } else {
         this.maxConnections = 0;
      }

      if (strRatePeriod != null) {
         this.ratePeriod = Integer.parseInt(strRatePeriod);
      } else {
         this.ratePeriod = 60000;
      }

      if (this.enabled) {
         this.logger.log(5, this, Messages.getString("DoSManager_Denial_of_Service_monitor_loaded_and_running._21"));
         this.logger.log(7, this, Messages.getString("DoSManager_Period_enforcement_cycle_time____________22") + strRatePeriod + Messages.getString("DoSManager_milleseconds._23"));
         this.logger.log(7, this, Messages.getString("DoSManager_Maximum_ops_per_connection_per_cycle_____24") + this.maxOpsPerCon);
         this.logger.log(7, this, Messages.getString("DoSManager_Maximum_connects_per_subject_per_cycle___25") + this.maxConPerSubject);
         this.logger.log(7, this, Messages.getString("DoSManager_Maximum_connections_per_ip_per_cycle_____26") + this.maxConPerIP);
         this.logger.log(7, this, Messages.getString("DoSManager_Maximum_concurrent_connections___________27") + this.maxConPerIP);
         this.logger.log(5, this, Messages.getString("DoSManager_Exempting_IP_addresses___________________28") + strExemptIPs);
         this.logger.log(5, this, Messages.getString("DoSManager_Exempting_subject_names__________________29") + strExemptUsers);
      } else {
         this.logger.log(5, this, Messages.getString("DoSManager_Denial_of_Service_monitor_DISABLED._30"));
      }

      while(this.ratePeriod > 0) {
         try {
            sleep((long)this.ratePeriod);
            this.logger.log(7, this, Messages.getString("DoSManager_Periodic_tracking_cycle_running._31"));
            synchronized(this.activeConnections) {
               Iterator iter = this.activeConnections.iterator();

               while(true) {
                  if (!iter.hasNext()) {
                     break;
                  }

                  Connection con = (Connection)iter.next();
                  boolean unbound = con.isUnbound();
                  boolean closed = con.getClient().isClosed();
                  if (!unbound && !closed) {
                     int opcount = con.getLastOp();
                     String address = con.getAuthCred().getIPAddress();
                     String user = con.getAuthCred().getUser().toString();
                     this.logger.log(7, this, Messages.getString("DoSManager_Current_operations_per_connection_(_32") + user + "/" + address + ", " + opcount + "/" + this.maxOpsPerCon + ").");
                     con.setLastOp(0);
                  } else {
                     if (unbound) {
                        this.logger.log(3, this, Messages.getString("DoSManager_Found_unbound_connection_from_activeConnections._37") + "[Session: " + con.getAuthCred().getUser().toString() + "/" + con.getAuthCred().getIPAddress() + "]");
                     } else {
                        this.logger.log(3, this, "DoSManager found closed connection from activeConnections. [Session: " + con.getAuthCred().getUser().toString() + "/" + con.getAuthCred().getIPAddress() + "]");
                     }

                     iter.remove();
                  }
               }
            }

            Collection usercons;
            Iterator useriter;
            DoSTracker item;
            synchronized(this.activeIPs) {
               usercons = this.activeIPs.values();
               useriter = usercons.iterator();

               while(useriter.hasNext()) {
                  item = (DoSTracker)useriter.next();
                  item.resetCounters();
                  Iterator conIter = item.getConIterator();

                  while(conIter.hasNext()) {
                     Connection acon = (Connection)conIter.next();
                     if (acon.isUnbound()) {
                        this.logger.log(3, this, Messages.getString("DoSManager_Found_unbound_connection_from_active_ip_addresses._38") + "[Session: " + acon.getAuthCred().getUser().toString() + "/" + acon.getAuthCred().getIPAddress() + "]");
                        conIter.remove();
                     } else if (acon.getClient().isClosed()) {
                        this.logger.log(3, this, Messages.getString("DoSManager found closed connection from active IP addresses.") + "[Session: " + acon.getAuthCred().getUser().toString() + "/" + acon.getAuthCred().getIPAddress() + "]");
                        conIter.remove();
                     }
                  }
               }
            }

            synchronized(this.activeUsers) {
               usercons = this.activeUsers.values();
               useriter = usercons.iterator();

               while(useriter.hasNext()) {
                  item = (DoSTracker)useriter.next();
                  synchronized(item) {
                     item.resetCounters();
                     Iterator conIter = item.getConIterator();

                     while(conIter.hasNext()) {
                        Connection acon = (Connection)conIter.next();
                        if (acon.isUnbound()) {
                           this.logger.log(3, this, Messages.getString("DoSManager_Found_unbound_connection_from_active_users._39") + "[Session: " + acon.getAuthCred().getUser().toString() + "/" + acon.getAuthCred().getIPAddress() + "]");
                           conIter.remove();
                        } else if (acon.getClient().isClosed()) {
                           this.logger.log(3, this, Messages.getString("DoSManager found closed connection from active users") + "[Session: " + acon.getAuthCred().getUser().toString() + "/" + acon.getAuthCred().getIPAddress() + "]");
                           conIter.remove();
                        }
                     }
                  }
               }
            }
         } catch (InterruptedException var27) {
         }
      }

   }
}
