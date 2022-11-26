package com.rsa.certj.provider.db;

import java.net.InetAddress;
import java.net.UnknownHostException;

/** @deprecated */
public final class LDAPConfiguration {
   /** @deprecated */
   public static final int LDAP_AUTH_NONE = 0;
   /** @deprecated */
   public static final int LDAP_AUTH_SIMPLE = 1;
   /** @deprecated */
   public static final int LDAP_DEFAULT_AUTH_TYPE = 0;
   /** @deprecated */
   public static final int LDAP_DEFAULT_PORT_NUMBER = 389;
   /** @deprecated */
   public static final int LDAP_DEFAULT_SIZE_LIMIT = 200;
   /** @deprecated */
   public static final int LDAP_DEFAULT_TIME_LIMIT = 0;
   /** @deprecated */
   public static final String LDAP_DEFAULT_BASE_DN_ATTRS = "o,ou";
   /** @deprecated */
   public static final String LDAP_DEFAULT_SEARCH_FILTER_ATTRS = "cn";
   /** @deprecated */
   public static final String LDAP_DEFAULT_CERTIFICATE_ATTRS = "usercertificate;binary,caCertificate;binary";
   /** @deprecated */
   public static final String LDAP_DEFAULT_CERTIFICATE_REVOCATION_ATTRS = "authorityRevocationList;binary,certificateRevocationList;binary";
   /** @deprecated */
   public static final int LDAP_SEARCH_SCOPE_EXACT = 1;
   /** @deprecated */
   public static final int LDAP_SEARCH_SCOPE_FILTER = 2;
   /** @deprecated */
   public static final int LDAP_SEARCH_SCOPE_BASEDN = 3;
   /** @deprecated */
   public static final int LDAP_SEARCH_SCOPE_ALL = 4;
   /** @deprecated */
   public static final int LDAP_DEFAULT_SEARCH_SCOPE = 4;
   /** @deprecated */
   public static final boolean LDAP_DEFAULT_DISCONNECT_BEFORE_CONNECT = true;
   private String descriptiveName;
   private InetAddress networkAddress;
   private int portNumber;
   private int authType;
   private int sizeLimit;
   private int timeLimit;
   private String baseDNAttrs;
   private String searchRoot;
   private String searchFilterAttrs;
   private String certificateAttrs;
   private String certificateRevocationAttrs;
   private int searchScope;
   private boolean disconnectBeforeConnect;

   /** @deprecated */
   public LDAPConfiguration(String var1, InetAddress var2, int var3) {
      this.portNumber = 389;
      this.authType = 0;
      this.sizeLimit = 200;
      this.timeLimit = 0;
      this.baseDNAttrs = "o,ou";
      this.searchFilterAttrs = "cn";
      this.certificateAttrs = "usercertificate;binary,caCertificate;binary";
      this.certificateRevocationAttrs = "authorityRevocationList;binary,certificateRevocationList;binary";
      this.searchScope = 4;
      this.disconnectBeforeConnect = true;
      this.descriptiveName = var1;
      this.networkAddress = var2;
      this.portNumber = var3;
   }

   /** @deprecated */
   public LDAPConfiguration(String var1) throws UnknownHostException {
      this(var1, InetAddress.getByName(var1), 389);
   }

   /** @deprecated */
   public String getDescriptiveName() {
      return this.descriptiveName;
   }

   /** @deprecated */
   public InetAddress getNetworkAddress() {
      return this.networkAddress;
   }

   /** @deprecated */
   public int getPortNumber() {
      return this.portNumber;
   }

   /** @deprecated */
   public int getAuthType() {
      return this.authType;
   }

   /** @deprecated */
   public void setAuthType(int var1) {
      this.authType = var1;
   }

   /** @deprecated */
   public int getSizeLimit() {
      return this.sizeLimit;
   }

   /** @deprecated */
   public void setSizeLimit(int var1) {
      this.sizeLimit = var1;
   }

   /** @deprecated */
   public int getTimeLimit() {
      return this.timeLimit;
   }

   /** @deprecated */
   public void setTimeLimit(int var1) {
      this.timeLimit = var1;
   }

   /** @deprecated */
   public String getBaseDNAttrs() {
      return this.baseDNAttrs;
   }

   /** @deprecated */
   public void setBaseDNAttrs(String var1) {
      this.baseDNAttrs = var1;
   }

   /** @deprecated */
   public String getSearchRoot() {
      return this.searchRoot;
   }

   /** @deprecated */
   public void setSearchRoot(String var1) {
      this.searchRoot = var1;
   }

   /** @deprecated */
   public int getSearchScope() {
      return this.searchScope;
   }

   /** @deprecated */
   public void setSearchScope(int var1) {
      this.searchScope = var1;
   }

   /** @deprecated */
   public String getSearchFilterAttrs() {
      return this.searchFilterAttrs;
   }

   /** @deprecated */
   public void setSearchFilterAttrs(String var1) {
      this.searchFilterAttrs = var1;
   }

   /** @deprecated */
   public String getCertificateAttrs() {
      return this.certificateAttrs;
   }

   /** @deprecated */
   public void setCertificateAttrs(String var1) {
      this.certificateAttrs = var1;
   }

   /** @deprecated */
   public String getCertificateRevocationAttrs() {
      return this.certificateRevocationAttrs;
   }

   /** @deprecated */
   public void setCertificateRevocationAttrs(String var1) {
      this.certificateRevocationAttrs = var1;
   }

   /** @deprecated */
   public boolean getDisconnectBeforeConnect() {
      return this.disconnectBeforeConnect;
   }

   /** @deprecated */
   public void setDisconnectBeforeConnect(boolean var1) {
      this.disconnectBeforeConnect = var1;
   }
}
