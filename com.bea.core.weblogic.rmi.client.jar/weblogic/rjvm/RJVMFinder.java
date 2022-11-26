package weblogic.rjvm;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.security.AccessController;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.socket.UnrecoverableConnectException;

final class RJVMFinder implements PeerGoneListener {
   private static final int BACKOFF_INTERVAL = 1000;
   private static final DebugLogger debugConnection = DebugLogger.getDebugLogger("DebugConnection");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private RJVM localRJVM = null;
   private InetAddress[] hostList = null;
   private ConcurrentHashMap jvmIdToInetAddressMap = null;
   private int hostIdx = 0;
   private final String hostName;
   private int[] portList;
   private final ClientServerURL url;
   private boolean isApplet;
   private int hash;
   private static String thisHost;
   private IOException error;
   private int currentHostIdx = 0;
   private String currentPartitionUrl;
   private InetAddress[] dnsEntries = null;

   RJVMFinder(ClientServerURL url) {
      this.url = url;
      Protocol protocol = ProtocolManager.getProtocolByName(url.getProtocol());
      this.hostName = url.getHost();
      this.portList = new int[9];
      int i = this.portList.length;

      while(true) {
         --i;
         if (i < 0) {
            if (!protocol.isUnknown()) {
               this.portList[protocol.toByte()] = url.getPort();
            }

            for(i = url.getAddressCount() - 1; i >= 0; --i) {
               this.hash ^= url.getLowerCaseHost(i).hashCode();
               this.hash ^= url.getPort(i);
            }

            this.hash ^= protocol.toByte();
            this.jvmIdToInetAddressMap = new ConcurrentHashMap();
            return;
         }

         this.portList[i] = -1;
      }
   }

   public int hashCode() {
      return this.hash;
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (!(other instanceof RJVMFinder)) {
         return false;
      } else {
         ClientServerURL otherUrl = ((RJVMFinder)other).url;
         if (!otherUrl.getProtocol().equals(this.url.getProtocol())) {
            return false;
         } else if (otherUrl.getAddressCount() != this.url.getAddressCount()) {
            return false;
         } else {
            for(int i = this.url.getAddressCount() - 1; i >= 0; --i) {
               if (otherUrl.getPort(i) != this.url.getPort(i) || !otherUrl.getLowerCaseHost(i).equals(this.url.getLowerCaseHost(i))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   synchronized RJVM findOrCreate(boolean preferExistingRJVM, String channel, HostID hostIDtoIgnore, int connectionTimeout) throws IOException {
      if (this.error != null) {
         throw this.error;
      } else {
         try {
            return this.findOrCreateInternal(preferExistingRJVM, channel, hostIDtoIgnore, connectionTimeout);
         } catch (IOException var6) {
            this.error = var6;
            throw var6;
         }
      }
   }

   synchronized RJVM findRJVM() throws IOException {
      RJVM rjvm = null;
      if (this.hostList == null) {
         this.hostList = this.getDnsEntries();
      }

      try {
         if (this.isHostedByLocalRJVM()) {
            this.localRJVM = RJVMManager.getLocalRJVM();
            rjvm = this.localRJVM;
         } else if (this.hostList.length == 1 || this.isApplet) {
            return RJVMManager.getRJVMManager().findRemoteRJVM(this.hostList[0], this.url.getPort(0), this.url.getProtocol());
         }

         return rjvm;
      } catch (IOException var3) {
         this.error = var3;
         throw var3;
      }
   }

   private RJVM findOrCreateInternal(boolean preferExistingRJVM, String channel, HostID hostIDtoIgnore, int connectionTimeout) throws IOException {
      if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
         RJVMLogger.logDebug("RJVMFinder trying to find rjvm for " + this + '\n' + RJVMManager.getRJVMManager().toString());
      }

      if (this.localRJVM != null) {
         return this.localRJVM;
      } else {
         if (this.hostList == null) {
            this.hostList = this.getDnsEntries();
         }

         RJVM rjvm;
         try {
            if (this.isHostedByLocalRJVM()) {
               this.localRJVM = RJVMManager.getLocalRJVM();
               rjvm = this.localRJVM;
            } else if (this.hostList.length != 1 && !this.isApplet) {
               try {
                  rjvm = this.findOrCreateRemoteCluster(preferExistingRJVM, channel, hostIDtoIgnore, connectionTimeout);
               } catch (SecurityException var8) {
                  this.isApplet = true;
                  rjvm = this.findOrCreateRemoteServer(this.getHostName(0), this.hostList[0], this.url.getPort(0), channel, connectionTimeout, this.url.getUrlString(0));
                  this.addPeerGoneListener(rjvm);
               }
            } else {
               rjvm = this.findOrCreateRemoteServer(this.getHostName(0), this.hostList[0], this.url.getPort(0), channel, connectionTimeout, this.url.getUrlString(0));
               this.addPeerGoneListener(rjvm);
            }
         } catch (ConnectException var9) {
            String str = this.url.asUnsyncStringBuffer().append(": ").append(var9.getMessage()).toString();
            throw (java.net.ConnectException)(new java.net.ConnectException(str)).initCause(var9);
         }

         if (rjvm != null) {
            this.portList = rjvm.getID().ports();
         } else {
            this.hostList = null;
         }

         return rjvm;
      }
   }

   private RJVM findOrCreateRemoteServer(String sHost, InetAddress host, int port, String channelName, int connectionTimeout, String partitionUrl) throws IOException {
      try {
         this.currentPartitionUrl = partitionUrl;
         return RJVMManager.getRJVMManager().findOrCreate(sHost, host, port, this.url.getProtocol(), channelName, connectionTimeout, partitionUrl);
      } catch (IOException var8) {
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug("RJVMFinder can't find server at " + this.url.getProtocol() + "://" + host.getHostAddress() + ':' + port);
         }

         throw var8;
      }
   }

   private RJVM findExistingRemoteServer() {
      if (this.jvmIdToInetAddressMap == null) {
         return null;
      } else {
         Iterator var1 = this.jvmIdToInetAddressMap.keySet().iterator();

         RJVM rjvm;
         do {
            if (!var1.hasNext()) {
               return null;
            }

            JVMID jvmid = (JVMID)var1.next();
            rjvm = RJVMManager.getRJVMManager().find(jvmid);
         } while(rjvm == null);

         return rjvm;
      }
   }

   private boolean isKernelOrServer(AuthenticatedSubject subject) {
      if (subject == kernelId) {
         return true;
      } else {
         return KernelStatus.isServer() && SecurityServiceManager.isServerIdentity(subject);
      }
   }

   private RJVM findOrCreateRemoteCluster(boolean preferExistingRJVM, String channel, HostID hostIDToIgnore, int connectionTimeout) throws IOException {
      IOException lastException = null;
      boolean[] unreachable = null;
      JVMID ignoreID = null;
      if (hostIDToIgnore instanceof JVMID) {
         ignoreID = (JVMID)hostIDToIgnore;
      }

      AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
      if (preferExistingRJVM || this.isKernelOrServer(subject)) {
         RJVM rjvm = this.findExistingRemoteServer();
         if (rjvm != null && !rjvm.getID().equals(ignoreID)) {
            return rjvm;
         }
      }

      int i;
      RJVM rjvm;
      RJVM var11;
      for(i = 0; i < this.hostList.length; ++i) {
         try {
            if (this.hostList[this.hostIdx] != null) {
               rjvm = this.findOrCreateRemoteServer(this.getHostName(this.hostIdx), this.hostList[this.hostIdx], this.url.getAddressCount() == 1 ? this.url.getPort(0) : this.url.getPort(this.hostIdx), channel, connectionTimeout, this.url.getAddressCount() == 1 ? this.url.getUrlString(0) : this.url.getUrlString(this.hostIdx));
               if (!rjvm.getID().equals(ignoreID)) {
                  this.addPeerGoneListener(rjvm);
                  var11 = rjvm;
                  return var11;
               }

               if (unreachable == null) {
                  unreachable = new boolean[this.hostList.length];
               }

               unreachable[this.hostIdx] = true;
               this.hostList[this.hostIdx] = null;
            }
         } catch (UnrecoverableConnectException var32) {
            throw var32;
         } catch (IOException var33) {
            if (unreachable == null) {
               unreachable = new boolean[this.hostList.length];
            }

            unreachable[this.hostIdx] = true;
            this.hostList[this.hostIdx] = null;
            lastException = var33;
         } finally {
            this.currentHostIdx = this.hostIdx;
            this.hostIdx = (this.hostIdx + 1) % this.hostList.length;
         }
      }

      try {
         Thread.sleep((long)(Math.random() * 1000.0));
      } catch (InterruptedException var28) {
      }

      this.hostList = this.getDnsEntries();

      for(i = 0; i < this.hostList.length; ++i) {
         try {
            if (unreachable == null || !unreachable[this.hostIdx]) {
               rjvm = this.findOrCreateRemoteServer(this.getHostName(this.hostIdx), this.hostList[this.hostIdx], this.url.getAddressCount() == 1 ? this.url.getPort(0) : this.url.getPort(this.hostIdx), (String)null, connectionTimeout, this.url.getAddressCount() == 1 ? this.url.getUrlString(0) : this.url.getUrlString(this.hostIdx));
               this.addPeerGoneListener(rjvm);
               var11 = rjvm;
               return var11;
            }

            this.hostList[this.hostIdx] = null;
         } catch (UnrecoverableConnectException var29) {
            throw var29;
         } catch (IOException var30) {
            this.hostList[this.hostIdx] = null;
            lastException = var30;
         } finally {
            this.currentHostIdx = this.hostIdx;
            this.hostIdx = (this.hostIdx + 1) % this.hostList.length;
         }
      }

      if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
         RJVMLogger.logDebug("RJVMFinder can't find any servers at " + this);
      }

      if (lastException == null) {
         lastException = new ConnectException("bad mojo");
      }

      throw lastException;
   }

   private InetAddress[] getDnsEntries() throws UnknownHostException {
      if (this.dnsEntries == null) {
         try {
            if (this.url.getAddressCount() > 1) {
               InetAddress[] entries = new InetAddress[this.url.getAddressCount()];

               for(int i = 0; i < this.url.getAddressCount(); ++i) {
                  entries[i] = InetAddress.getByName(this.getHostName(i));
               }

               this.dnsEntries = entries;
               double dHostIdx = Math.random() * (double)this.dnsEntries.length + 0.5;
               this.hostIdx = (int)Math.round(dHostIdx) - 1;
               this.currentHostIdx = this.hostIdx;
            } else if (thisHost.equalsIgnoreCase(this.hostName)) {
               this.dnsEntries = new InetAddress[1];
               this.dnsEntries[0] = InetAddress.getByName(this.hostName);
            } else {
               this.dnsEntries = InetAddress.getAllByName(this.hostName);
            }
         } catch (SecurityException var4) {
            throw var4;
         }
      }

      return (InetAddress[])this.dnsEntries.clone();
   }

   public String getHostName(int hostIdx) {
      return this.url.getAddressCount() > 1 ? this.url.getHost(hostIdx) : this.hostName;
   }

   synchronized boolean isHostedByLocalRJVM() throws UnknownHostException {
      if (this.hostList == null) {
         this.hostList = this.getDnsEntries();
      }

      if (RJVMEnvironment.getEnvironment().isServer()) {
         for(int i = 0; i < this.hostList.length; ++i) {
            InetAddress address = this.hostList[i];
            if (address != null) {
               int port = this.url.getAddressCount() == 1 ? this.url.getPort(0) : this.url.getPort(i);
               if (RJVMEnvironment.getEnvironment().isLocalChannel(address, port)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public String toString() {
      StringBuilder buf = new StringBuilder(this.url.toString());
      buf.append("->");
      int i;
      if (this.portList != null) {
         buf.append(this.hostName).append(":[");
         i = 0;

         while(true) {
            buf.append(this.portList[i]);
            ++i;
            if (i >= this.portList.length) {
               buf.append(']');
               break;
            }

            buf.append(", ");
         }
      }

      if (this.hostList != null) {
         buf.append("->[");

         for(i = 0; i < this.hostList.length; ++i) {
            if (this.hostList[i] != null) {
               buf.append(this.hostList[i].getHostAddress());
               if (i == this.hostIdx) {
                  buf.append('*');
               }

               if (i + 1 < this.hostList.length) {
                  buf.append(", ");
               }
            }
         }

         buf.append(']');
      }

      return buf.toString();
   }

   private void addPeerGoneListener(RJVM newRjvm) {
      if (!this.jvmIdToInetAddressMap.containsKey(newRjvm.getID())) {
         this.jvmIdToInetAddressMap.put(newRjvm.getID(), this.hostList[this.hostIdx]);
         newRjvm.addPeerGoneListener(this);
      }

   }

   private synchronized void invalidateHostListEntry(JVMID jvmid) {
      InetAddress address = (InetAddress)this.jvmIdToInetAddressMap.remove(jvmid);
      if (address != null) {
         if (this.hostList != null) {
            if (this.hostList.length == 1) {
               this.hostList = null;
               this.dnsEntries = null;
               return;
            }

            for(int i = 0; i < this.hostList.length; ++i) {
               if (address.equals(this.hostList[i])) {
                  this.hostList[i] = null;
               }
            }
         }

      }
   }

   public void peerGone(PeerGoneEvent pe) {
      this.invalidateHostListEntry(pe.getID());
   }

   ClientServerURL getURL() {
      return this.url;
   }

   String getPartitionUrl() {
      return this.currentPartitionUrl;
   }

   int getCurrentHostIdx() {
      return this.currentHostIdx;
   }

   static {
      try {
         thisHost = InetAddress.getLocalHost().getHostName();
      } catch (UnknownHostException var1) {
         thisHost = "localhost";
      }

   }
}
