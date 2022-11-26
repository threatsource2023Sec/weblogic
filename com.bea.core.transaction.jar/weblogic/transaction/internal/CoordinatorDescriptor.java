package weblogic.transaction.internal;

import java.net.URI;
import java.util.Hashtable;
import java.util.zip.CRC32;
import weblogic.transaction.ChannelInterface;
import weblogic.transaction.PeerExchangeTransaction;

public class CoordinatorDescriptor {
   protected static final Hashtable knownServers = new Hashtable();
   protected String coordinatorURL;
   protected String coordinatorSecureURL;
   protected String coordinatorNonSecureURL;
   protected String serverURL;
   protected String domainName;
   protected String serverName;
   protected String protocol;
   protected String adminProtocol;
   protected String adminPort;
   protected boolean adminPortEnabled = false;
   private volatile byte[] urlHash;
   private volatile byte[] urlHash60;
   private static final String DEFAULT_PROTOCOL = "t3";
   private volatile String siteName;
   private volatile byte[] siteNameHash;
   private URI secureURL;
   private URI publicURL;
   private URI publicSecureURL;

   protected CoordinatorDescriptor() {
   }

   protected CoordinatorDescriptor(String aCoURL) {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("CoordinatorDescriptor(" + aCoURL + ")");
      }

      this.init(aCoURL);
   }

   CoordinatorDescriptor(String aHostPort, String aDomainName, String aServerName, String aProtocol) {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("CoordinatorDescriptor(" + aHostPort + "," + aDomainName + "," + aServerName + "," + aProtocol + ")");
      }

      this.init(getCoordinatorURL(aHostPort, aDomainName, aServerName, aProtocol));
   }

   CoordinatorDescriptor(String domainName, String serverName, URI primaryURL, URI publicURL, URI secureURL, URI publicSecureURL) {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("CoordinatorDescriptor(" + domainName + ", " + serverName + "," + primaryURL + "," + publicURL + "," + secureURL + "," + publicSecureURL + ")");
      }

      this.init(getCoordinatorURL(domainName, serverName, primaryURL, secureURL, publicURL, publicSecureURL));
   }

   protected CoordinatorDescriptor(String aCoURL, String aAdminPort) {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("CoordinatorDescriptor(" + aCoURL + ", " + aAdminPort + ")");
      }

      this.adminPortEnabled = true;
      String adminProtocol = getAdminProtocol(aCoURL);
      if (adminProtocol == null) {
         this.init(getAdminCoordinatorURL(aCoURL, "admin", aAdminPort));
      } else {
         this.init(aCoURL);
      }

   }

   protected final void init(String aCoURL) {
      if (aCoURL != null) {
         String hostIP = getHost(aCoURL);
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("CoordinatorDescriptor.init(" + aCoURL + ") hostIP:" + hostIP);
         }

         this.coordinatorURL = aCoURL;
         this.serverURL = getServerURL(this.coordinatorURL);
         this.serverName = getServerName(this.coordinatorURL);
         this.domainName = getDomainName(this.coordinatorURL);
         this.protocol = getProtocol(this.coordinatorURL);
         this.adminProtocol = getAdminProtocol(this.coordinatorURL);
         this.adminPort = getAdminPort(this.coordinatorURL);
         if (this.protocol == null) {
            this.protocol = "t3";
         }

         this.setSSLCoordinatorURL(this.coordinatorURL);
         this.setNonSSLCoordinatorURL(this.coordinatorURL);
         this.initExtensionURLs(this.coordinatorURL);
         this.dumpCoordinator("==>CoordinatorDescriptor.init");
         knownServers.put(this.getServerID(), this);
      }
   }

   private void initExtensionURLs(String courl) {
      String[] tokens = courl.split("\\+");
      if (tokens.length > 6 && tokens[6] != null && !tokens[6].equals("")) {
         this.secureURL = URI.create(tokens[6]);
      }

      if (tokens.length > 7 && tokens[7] != null && !tokens[7].equals("")) {
         this.publicURL = URI.create(tokens[7]);
      }

      if (tokens.length > 8 && tokens[8] != null && !tokens[8].equals("")) {
         this.publicSecureURL = URI.create(tokens[8]);
      }

   }

   void reinitialize(String courl) {
      this.init(courl);
   }

   public final String getServerID() {
      return getServerID(this.domainName, this.serverName);
   }

   public final String getCoordinatorURL() {
      return this.coordinatorURL;
   }

   public final String getCoordinatorURL(boolean useSecureURL) {
      this.dumpCoordinator("==>CoordinatorDescriptor.getCoordinatorURL useSecureURL:" + useSecureURL);
      String retCoURL = null;
      if (isCoURLExtendedWithChannelAddresses(this.coordinatorURL)) {
         retCoURL = this.coordinatorURL;
      } else if (useSecureURL) {
         retCoURL = this.coordinatorSecureURL;
      } else {
         retCoURL = this.coordinatorNonSecureURL;
      }

      if (retCoURL == null) {
         retCoURL = this.coordinatorURL;
      }

      return retCoURL;
   }

   private String get60CoordinatorURL() {
      StringBuilder coorURL = new StringBuilder();
      coorURL.append(this.getServerName());
      coorURL.append('+');
      coorURL.append(this.getHostPort());
      return coorURL.toString();
   }

   public final String getServerURL(PeerExchangeTransaction tx) {
      if (tx == null) {
         return this.serverURL;
      } else {
         String protocol = (String)tx.getProperty("weblogic.transaction.protocol");
         String aServerURL;
         if (protocol == null) {
            if (!PlatformHelper.getPlatformHelper().isServer()) {
               return this.serverURL;
            } else {
               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("CoordinatorDescriptor.getServerURL coordinatorSecureURL:" + this.coordinatorSecureURL + "coordinatorNonSecureURL:" + this.coordinatorNonSecureURL + " serverURL:" + this.serverURL + " ssl:" + tx.isSSLEnabled());
               }

               aServerURL = null;
               if (tx.isSSLEnabled()) {
                  if (this.coordinatorSecureURL != null) {
                     aServerURL = getServerURL(this.coordinatorSecureURL);
                  }
               } else if (this.coordinatorNonSecureURL != null) {
                  aServerURL = getServerURL(this.coordinatorNonSecureURL);
               }

               return aServerURL == null ? this.serverURL : aServerURL;
            }
         } else {
            aServerURL = null;
            if (protocol != "t3s" && protocol != "https") {
               aServerURL = getHostPort(this.getCoordinatorURL(tx.isSSLEnabled()));
            } else {
               aServerURL = getHostPort(this.getCoordinatorURL(true));
            }

            if (aServerURL == null) {
               return null;
            } else {
               StringBuilder serverURL = new StringBuilder();
               serverURL.append(protocol);
               serverURL.append("://");
               serverURL.append(aServerURL);
               return serverURL.toString();
            }
         }
      }
   }

   public final String getServerName() {
      return this.serverName;
   }

   public final String getDomainName() {
      return this.domainName;
   }

   private String getHostPort() {
      return getHostPort(this.coordinatorURL);
   }

   public final String getProtocol(PeerExchangeTransaction tx) {
      if (tx != null) {
         String protocol = (String)tx.getProperty("weblogic.transaction.protocol");
         return protocol != null ? protocol : getProtocol(this.getCoordinatorURL(tx.isSSLEnabled()));
      } else {
         return getProtocol(this.coordinatorURL);
      }
   }

   static CoordinatorDescriptor getOrCreate(String aCoURL) {
      if (aCoURL == null) {
         return null;
      } else {
         CoordinatorDescriptor cd = (CoordinatorDescriptor)knownServers.get(getServerID(aCoURL));
         if (cd == null) {
            cd = new CoordinatorDescriptor(aCoURL);
         }

         if (TxDebug.JTANaming.isDebugEnabled() && TxDebug.JTANamingStackTrace.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTANamingStackTrace, "CoordinatorDescriptor.getOrcreate sCoURL:" + aCoURL);
         }

         return cd;
      }
   }

   static CoordinatorDescriptor getOrCreate(Object server, ChannelInterface channel) {
      if (TxDebug.JTANaming.isDebugEnabled() && TxDebug.JTANamingStackTrace.isDebugEnabled()) {
         TxDebug.debugStack(TxDebug.JTANamingStackTrace, "CoordinatorDescriptor.getOrCreate server:" + server + " channel:" + channel.getProtocolPrefix());
      }

      return PlatformHelper.getPlatformHelper().getOrCreateCoordinatorDescriptor(knownServers, server, channel);
   }

   final byte[] getURLHash() {
      if (this.urlHash == null) {
         this.initializeURLHash();
      }

      return this.urlHash;
   }

   final synchronized void initializeURLHash() {
      if (this.urlHash == null) {
         this.urlHash = getURLHash(this.getServerID());
      }

   }

   final byte[] get60URLHash() {
      if (this.urlHash60 == null) {
         this.initializeURLHash60();
      }

      return this.urlHash60;
   }

   final synchronized void initializeURLHash60() {
      if (this.urlHash60 == null) {
         this.urlHash60 = getURLHash(this.get60CoordinatorURL());
      }

   }

   static byte[] getURLHash(String input) {
      CRC32 crc = new CRC32();
      crc.update(input.getBytes());
      int hashVal = (int)crc.getValue();
      byte[] hash = new byte[4];
      XidImpl.writeInt(hash, 0, hashVal);
      return hash;
   }

   final boolean representsCoordinatorURL(String aCoURL) {
      return this.getServerID().equals(getServerID(aCoURL));
   }

   private static String getPosition(String aCoUrl, int pos) {
      if (aCoUrl == null) {
         return null;
      } else {
         String[] tokens = aCoUrl.split("\\+", pos + 1);
         if (tokens.length < pos) {
            return null;
         } else {
            return tokens[pos - 1].equals("") ? null : tokens[pos - 1];
         }
      }
   }

   static final String getServerName(String aCoUrl) {
      return getPosition(aCoUrl, 1);
   }

   private static final String getDomainName(String aCoUrl) {
      return getPosition(aCoUrl, 3);
   }

   static final String getServerID(String aCoUrl) {
      return getServerID(getDomainName(aCoUrl), getServerName(aCoUrl));
   }

   static final String getServerID(String aDomainName, String aServerName) {
      StringBuilder serverID = new StringBuilder();
      serverID.append(aDomainName);
      serverID.append('+');
      serverID.append(aServerName);
      return serverID.toString();
   }

   public static String getServerURL(String aCoUrl) {
      String hostPort = getHostPort(aCoUrl);
      if (hostPort == null) {
         return null;
      } else {
         String proto = getProtocol(aCoUrl);
         return (proto != null ? proto : "t3") + "://" + hostPort;
      }
   }

   static final boolean isCoURLExtendedWithChannelAddresses(String aCoUrl) {
      if (aCoUrl == null) {
         return false;
      } else {
         return getPosition(aCoUrl, 7) != null || getPosition(aCoUrl, 8) != null || getPosition(aCoUrl, 9) != null;
      }
   }

   private static final String getHostPort(String aCoUrl) {
      return getPosition(aCoUrl, 2);
   }

   private static final String getHost(String aCoUrl) {
      String hostPort = getHostPort(aCoUrl);
      int colonPos = hostPort.indexOf(58);
      return colonPos == -1 ? null : hostPort.substring(0, colonPos);
   }

   protected static String getPort(String aCoUrl) {
      String hostPort = getHostPort(aCoUrl);
      int colonPos = hostPort.indexOf(58);
      return colonPos == -1 ? null : hostPort.substring(colonPos + 1);
   }

   private static final String getProtocol(String aCoUrl) {
      return getPosition(aCoUrl, 4);
   }

   private static final String getAdminProtocol(String aCoUrl) {
      return getPosition(aCoUrl, 5);
   }

   private static final String getAdminPort(String aCoUrl) {
      return getPosition(aCoUrl, 6);
   }

   protected static String getCoordinatorURL(String aHostPort, String aDomainName, String aServerName, String aProtocol) {
      StringBuilder coorURL = new StringBuilder();
      coorURL.append(aServerName);
      coorURL.append('+');
      coorURL.append(aHostPort);
      coorURL.append('+');
      coorURL.append(aDomainName);
      coorURL.append('+');
      coorURL.append(aProtocol);
      coorURL.append('+');
      return coorURL.toString();
   }

   protected static String getAdminCoordinatorURL(String aCoURL, String aAdminProtocol, String aAdminPort) {
      StringBuilder adminCoorURL = new StringBuilder();
      adminCoorURL.append(aCoURL);
      adminCoorURL.append(aAdminProtocol);
      adminCoorURL.append('+');
      adminCoorURL.append(aAdminPort);
      adminCoorURL.append('+');
      return adminCoorURL.toString();
   }

   protected static String getCoordinatorURL(String domainName, String serverName, URI primaryURL, URI secureURL, URI publicURL, URI publicSecureURL) {
      if (primaryURL == null) {
         throw new IllegalArgumentException("Primary URL is null");
      } else {
         StringBuilder sb = new StringBuilder();
         sb.append(serverName).append("+");
         sb.append(primaryURL.getHost() + ":" + primaryURL.getPort()).append("+");
         sb.append(domainName).append("+");
         sb.append(primaryURL.getScheme()).append("+");
         if (secureURL != null || publicURL != null || publicSecureURL != null) {
            sb.append("+");
            sb.append("+");
            if (secureURL != null) {
               sb.append(secureURL.toString());
            }

            sb.append("+");
            if (publicURL != null) {
               sb.append(publicURL.toString());
            }

            sb.append("+");
            if (publicSecureURL != null) {
               sb.append(publicSecureURL.toString());
            }

            sb.append("+");
         }

         return sb.toString();
      }
   }

   public static String getAdminCoordinatorURL(String aCoURL) {
      if (aCoURL == null) {
         return null;
      } else {
         String aServerName = getServerName(aCoURL);
         String aDomainName = getDomainName(aCoURL);
         String aProtocol = getAdminProtocol(aCoURL);
         if (aProtocol == null) {
            return null;
         } else {
            String aPort = getAdminPort(aCoURL);
            if (aPort == null) {
               return null;
            } else {
               StringBuilder adminCoorURL = new StringBuilder();
               adminCoorURL.append(aServerName);
               adminCoorURL.append('+');
               adminCoorURL.append(getHost(aCoURL));
               adminCoorURL.append(':');
               adminCoorURL.append(aPort);
               adminCoorURL.append('+');
               adminCoorURL.append(aDomainName);
               adminCoorURL.append('+');
               adminCoorURL.append(aProtocol);
               adminCoorURL.append('+');
               return adminCoorURL.toString();
            }
         }
      }
   }

   protected void setAdminCoordinatorURL(String aCoURL, String aAdminProtocol, String aAdminPort) {
      String adminProtocol = getAdminProtocol(aCoURL);
      if (adminProtocol == null) {
         StringBuilder coURL = new StringBuilder();
         coURL.append(aCoURL);
         coURL.append(aAdminProtocol);
         coURL.append('+');
         coURL.append(aAdminPort);
         coURL.append('+');
         this.coordinatorURL = coURL.toString();
      } else {
         this.coordinatorURL = aCoURL;
      }

      this.dumpCoordinator("==>CoordinatorDescriptor.setAdminCoordinatorURL");
   }

   protected void setAdminCoordinatorURL(String aCoURL) {
      if (getAdminProtocol(aCoURL) != null && getAdminPort(aCoURL) != null) {
         this.coordinatorURL = aCoURL;
      }

      this.dumpCoordinator("==>CoordinatorDescriptor.setAdminCoordinatorURL aCoURL:" + aCoURL);
   }

   protected boolean isAdminPortEnabled() {
      return this.adminPortEnabled;
   }

   public boolean isCoURLExtendedWithAdminPort(String aCoURL) {
      String adminProtocol = getAdminProtocol(aCoURL);
      return adminProtocol != null;
   }

   protected void setSSLCoordinatorURL(String aCoURL) {
      if (aCoURL != null && PlatformHelper.getPlatformHelper().isSSLURL(aCoURL)) {
         this.coordinatorSecureURL = aCoURL;
         this.coordinatorURL = aCoURL;
      }

      this.dumpCoordinator("==>CoordinatorDescriptor.setSSLCoordinatorURL " + aCoURL);
   }

   protected void setOnlySSLCoordinatorURL(String aCoURL) {
      if (aCoURL != null && PlatformHelper.getPlatformHelper().isSSLURL(aCoURL)) {
         this.coordinatorSecureURL = aCoURL;
      }

      this.dumpCoordinator("==>CoordinatorDescriptor.setOnlySSLCoordinatorURL " + aCoURL);
   }

   protected String getSSLCoordinatorURL() {
      return this.coordinatorSecureURL;
   }

   protected void setNonSSLCoordinatorURL(String aCoURL) {
      if (aCoURL != null && !PlatformHelper.getPlatformHelper().isSSLURL(aCoURL)) {
         this.coordinatorNonSecureURL = aCoURL;
      }

      this.dumpCoordinator("==>CoordinatorDescriptor.setNonSSLCoordinatorURL " + aCoURL);
   }

   protected String getNonSSLCoordinatorURL() {
      return this.coordinatorNonSecureURL != null ? this.coordinatorNonSecureURL : this.coordinatorURL;
   }

   protected void updateCoordinatorURL(String aCoURL) {
      if (aCoURL != null) {
         this.coordinatorURL = aCoURL;
      }

      this.dumpCoordinator("==>CoordinatorDescriptor.updateCoordinatorURL " + aCoURL);
   }

   protected String getDefaultCoordinatorURL() {
      return this.coordinatorURL;
   }

   public String getSiteName() {
      return this.siteName;
   }

   public void setSiteName(String siteName) {
      this.siteName = siteName;
      this.siteNameHash = getURLHash(siteName);
   }

   public byte[] getSiteNameHash() {
      return this.siteNameHash;
   }

   public String getPrimaryURL() {
      return this.serverURL;
   }

   public String getPublicURL() {
      return this.publicURL == null ? null : this.publicURL.toString();
   }

   public String getPublicSecureURL() {
      return this.publicSecureURL == null ? null : this.publicSecureURL.toString();
   }

   public String getSecureURL() {
      return this.secureURL == null ? null : this.secureURL.toString();
   }

   public boolean isPublicURLSet() {
      return this.publicURL != null;
   }

   public boolean isSecureURLSet() {
      return this.secureURL != null;
   }

   public boolean isPublicSecureURLSet() {
      return this.publicSecureURL != null;
   }

   public final boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof CoordinatorDescriptor)) {
         return false;
      } else {
         CoordinatorDescriptor cd = (CoordinatorDescriptor)o;
         return this.getServerID().equals(cd.getServerID());
      }
   }

   public final int hashCode() {
      return this.getServerID().hashCode();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer("CoordinatorDescriptor=(");
      sb.append("CoordinatorURL=");
      sb.append(this.coordinatorURL);
      sb.append(" CoordinatorNonSecureURL=");
      sb.append(this.coordinatorNonSecureURL);
      sb.append(" coordinatorSecureURL=");
      sb.append(this.coordinatorSecureURL);
      sb.append(" siteName=");
      sb.append(this.siteName);
      sb.append(")");
      return sb.toString();
   }

   public void dumpCoordinator(String caller) {
      if (TxDebug.JTANaming.isDebugEnabled() && TxDebug.JTANamingStackTrace.isDebugEnabled()) {
         String arg = caller + " threadInfo:" + Thread.currentThread().getName() + "  coordinatorSecureURL:" + this.coordinatorSecureURL + ",coordinatorNonSecureURL:" + this.coordinatorNonSecureURL + ",coordinatorURL:" + this.coordinatorURL + ",serverURL:" + this.serverURL + ",domainName:" + this.domainName + ",serverName:" + this.serverName + ",protocol:" + this.protocol + ",adminProtocol:" + this.adminProtocol + ",adminPort:" + this.adminPort + ",adminPortEnabled:" + this.adminPortEnabled + ",secureURL:" + this.secureURL + ",publicURL:" + this.publicURL + ",publicSecureURL:" + this.publicSecureURL;
         TxDebug.debugStack(TxDebug.JTANamingStackTrace, "CoordinatorDescriptor.dump=" + arg);
      }

   }
}
