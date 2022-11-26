package weblogic.cluster.messaging.internal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class ServerConfigurationInformationImpl implements ServerConfigurationInformation {
   private static final long serialVersionUID = -7511929101139272497L;
   private static final boolean DEBUG;
   private InetAddress address;
   private final String addressHostname;
   private final int port;
   private final String serverName;
   private final long creationTime;
   private final boolean usingSSL;
   private final String clusterName;
   private final ServerNameComponents nameComponents;

   public ServerConfigurationInformationImpl(InetAddress address, int port, String serverName, long creationTime, String clusterName) {
      this(address, port, serverName, creationTime, false, (String)null, clusterName);
   }

   public ServerConfigurationInformationImpl(InetAddress address, int port, String serverName, long creationTime, boolean usingSSL, String clusterName) {
      this(address, port, serverName, creationTime, usingSSL, (String)null, clusterName);
   }

   public ServerConfigurationInformationImpl(InetAddress address, int port, String serverName, long creationTime, boolean usingSSL, String addressHostname, String clusterName) {
      this.address = address;
      this.port = port;
      this.serverName = serverName;
      this.creationTime = creationTime;
      this.usingSSL = usingSSL;
      this.addressHostname = addressHostname;
      this.nameComponents = ServerNameComponents.parseServername(serverName);
      this.clusterName = clusterName;
      if (this.address == null && this.addressHostname == null) {
         throw new AssertionError("Both of address and addressHostname are null. It's fatal error.");
      }
   }

   public InetAddress getAddress() throws IOException {
      Exception exception = null;
      if (this.address == null && this.addressHostname != null) {
         try {
            this.address = InetAddress.getByName(this.addressHostname);
         } catch (UnknownHostException var3) {
            exception = var3;
         }
      }

      if (this.address == null) {
         if (this.addressHostname == null) {
            throw new AssertionError("Both of address and addressHostname are null. It's fatal error.");
         } else {
            IOException ioe = new IOException("address is null. failed to resolve folloiwng hostname: " + this.addressHostname);
            ioe.initCause(exception);
            throw ioe;
         }
      } else {
         return this.address;
      }
   }

   public void refreshAddress() {
      InetAddress addressFromHostname = null;
      if (this.addressHostname != null) {
         addressFromHostname = Environment.getInetAddressFromHostname(this.addressHostname);
      }

      if (this.address == null || this.addressHostname != null && !this.address.equals(addressFromHostname)) {
         this.address = addressFromHostname;
         if (DEBUG) {
            this.debug("Successfully updated " + this.serverName + " address to " + this.address);
         }
      }

   }

   public String getAddressHostname() {
      return this.addressHostname;
   }

   public int getPort() {
      return this.port;
   }

   public String getServerName() {
      return this.serverName;
   }

   public ServerNameComponents getNameComponents() {
      return this.nameComponents;
   }

   public long getCreationTime() {
      return this.creationTime;
   }

   public boolean isUsingSSL() {
      return this.usingSSL;
   }

   public int compareTo(Object other) {
      assert other instanceof ServerConfigurationInformationImpl;

      ServerConfigurationInformationImpl otherInfo = (ServerConfigurationInformationImpl)other;
      return this.nameComponents.compareTo(otherInfo.getNameComponents());
   }

   public boolean equals(Object other) {
      if (!(other instanceof ServerConfigurationInformationImpl)) {
         return false;
      } else {
         ServerConfigurationInformationImpl otherInfo = (ServerConfigurationInformationImpl)other;
         return this.serverName.equals(otherInfo.serverName);
      }
   }

   public int hashCode() {
      return (int)((long)this.serverName.hashCode() ^ this.creationTime);
   }

   public String toString() {
      return this.address + ":" + this.port + (this.usingSSL ? "(SSL):" : ":") + this.serverName + ":" + this.creationTime;
   }

   public String getClusterName() {
      return this.clusterName;
   }

   private void debug(String s) {
      Environment.getLogService().debug("[ServerConfigurationInformation] " + s);
   }

   static {
      DEBUG = Environment.DEBUG;
   }
}
