package com.octetstring.vde.replication;

import com.octetstring.vde.syntax.DirectoryString;
import java.util.Hashtable;

public class Consumer {
   private String agreementName = null;
   private DirectoryString replicaBase = null;
   private String hostname = null;
   private String masterid = null;
   private String masterurl = null;
   private String consumerid = null;
   private int port = 389;
   private DirectoryString binddn = null;
   private String bindpw = null;
   private int changeSent = -1;
   private boolean active = false;
   private boolean immediate = true;
   private byte[] hours = null;
   private byte[] minutes = null;
   private byte[] days = null;
   private byte[] dates = null;
   private boolean running = false;
   private boolean secure = false;
   private Replicator replicator = null;
   private static final String CFG_AGREEMENT_NAME = "name";
   private static final String CFG_REPLICA_BASE = "base";
   private static final String CFG_HOSTNAME = "hostname";
   private static final String CFG_PORT = "port";
   private static final String CFG_BINDDN = "binddn";
   private static final String CFG_BINDPW = "bindpw";
   private static final String CFG_MASTERID = "masterid";
   private static final String CFG_CONSUMERID = "consumerid";
   private static final String CFG_MASTERURL = "masterurl";
   private static final String CFG_SECURE = "secure";

   public Consumer() {
   }

   public Consumer(Hashtable config) {
      this.setAgreementName((String)config.get("name"));
      this.setReplicaBase(new DirectoryString((String)config.get("base")));
      this.setHostname((String)config.get("hostname"));
      this.setPort(new Integer((String)config.get("port")));
      this.setBinddn(new DirectoryString((String)config.get("binddn")));
      this.setBindpw((String)config.get("bindpw"));
      this.setMasterID((String)config.get("masterid"));
      this.setConsumerID((String)config.get("consumerid"));
      this.setMasterURL((String)config.get("masterurl"));
      if ("1".equals((String)config.get("secure"))) {
         this.setSecure(true);
      }

      this.setActive(true);
      this.setImmediate(true);
   }

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean newActive) {
      this.active = newActive;
   }

   public String getAgreementName() {
      return this.agreementName;
   }

   public void setAgreementName(String newAgreementName) {
      this.agreementName = newAgreementName;
   }

   public DirectoryString getBinddn() {
      return this.binddn;
   }

   public void setBinddn(DirectoryString newBinddn) {
      this.binddn = newBinddn;
   }

   public String getBindpw() {
      return this.bindpw;
   }

   public void setBindpw(String newBindpw) {
      this.bindpw = newBindpw;
   }

   public int getChangeSent() {
      return this.changeSent;
   }

   public void setChangeSent(int newChangeSent) {
      this.changeSent = newChangeSent;
   }

   public byte[] getDates() {
      return this.dates;
   }

   public void setDates(byte[] newDates) {
      this.dates = newDates;
   }

   public byte[] getDays() {
      return this.days;
   }

   public void setDays(byte[] newDays) {
      this.days = newDays;
   }

   public String getHostname() {
      return this.hostname;
   }

   public void setHostname(String newHostname) {
      this.hostname = newHostname;
   }

   public byte[] getHours() {
      return this.hours;
   }

   public void setHours(byte[] newHours) {
      this.hours = newHours;
   }

   public boolean isImmediate() {
      return this.immediate;
   }

   public void setImmediate(boolean newImmediate) {
      this.immediate = newImmediate;
   }

   public byte[] getMinutes() {
      return this.minutes;
   }

   public void setMinutes(byte[] newMinutes) {
      this.minutes = newMinutes;
   }

   public int getPort() {
      return this.port;
   }

   public void setPort(int newPort) {
      this.port = newPort;
   }

   public DirectoryString getReplicaBase() {
      return this.replicaBase;
   }

   public void setReplicaBase(DirectoryString newReplicaBase) {
      this.replicaBase = newReplicaBase;
   }

   public void setConsumerID(String consumerid) {
      this.consumerid = consumerid;
   }

   public String getConsumerID() {
      return this.consumerid;
   }

   public void setMasterID(String masterid) {
      this.masterid = masterid;
   }

   public String getMasterID() {
      return this.masterid;
   }

   public void setMasterURL(String masterurl) {
      this.masterurl = masterurl;
   }

   public String getMasterURL() {
      return this.masterurl;
   }

   public boolean isRunning() {
      return this.running;
   }

   public void setRunning(boolean newRunning) {
      this.running = newRunning;
   }

   public boolean isSecure() {
      return this.secure;
   }

   public void setSecure(boolean secure) {
      this.secure = secure;
   }

   public Replicator getReplicator() {
      return this.replicator;
   }

   public void setReplicator(Replicator newReplicator) {
      this.replicator = newReplicator;
   }
}
