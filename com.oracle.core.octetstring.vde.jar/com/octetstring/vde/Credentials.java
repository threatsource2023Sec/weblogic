package com.octetstring.vde;

import com.octetstring.vde.syntax.DirectoryString;

public class Credentials {
   private DirectoryString user = null;
   private byte authType = 0;
   private String ipaddress = null;
   private String saslMech = null;
   private Object sasltmp = null;
   private String sasltmpdn = null;
   private boolean root = false;
   private boolean ldap2 = false;
   public static final byte AUTH_NONE = 0;
   public static final byte AUTH_SIMPLE = 1;
   public static final byte AUTH_SASL = 2;
   private static final DirectoryString ANON_USER = new DirectoryString("");

   public Credentials() {
      this.setUser(ANON_USER);
   }

   public byte getAuthType() {
      return this.authType;
   }

   public void setAuthType(byte newAuthType) {
      this.authType = newAuthType;
   }

   public String getSaslMech() {
      return this.saslMech;
   }

   public void setSaslMech(String newSaslMech) {
      this.saslMech = newSaslMech;
   }

   public DirectoryString getUser() {
      return this.user;
   }

   public void setUser(DirectoryString newUser) {
      this.user = newUser;
   }

   public String toString() {
      return this.getUser().toString();
   }

   public void setIPAddress(String ipaddress) {
      this.ipaddress = ipaddress;
   }

   public String getIPAddress() {
      return this.ipaddress;
   }

   public Object getSaslTmp() {
      return this.sasltmp;
   }

   public void setSaslTmp(Object sasltmp) {
      this.sasltmp = sasltmp;
   }

   public String getSaslTmpDN() {
      return this.sasltmpdn;
   }

   public void setSaslTmpDN(String dn) {
      this.sasltmpdn = dn;
   }

   public boolean isRoot() {
      return this.root;
   }

   public void setRoot(boolean root) {
      this.root = root;
   }

   public void setLdap2(boolean ldap2) {
      this.ldap2 = ldap2;
   }

   public boolean isLdap2() {
      return this.ldap2;
   }
}
