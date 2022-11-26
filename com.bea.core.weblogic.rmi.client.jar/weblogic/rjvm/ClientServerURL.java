package weblogic.rjvm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Hashtable;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerURL;
import weblogic.protocol.UnknownProtocolException;
import weblogic.rmi.spi.HostID;

public final class ClientServerURL extends ServerURL {
   private static final Hashtable finderMap = new Hashtable();
   private static boolean protocolRegistry = RJVMManager.ensureInitialized();
   private RJVMFinder finder;
   private String currentPartitionURL;

   public ClientServerURL(String urlString) throws MalformedURLException {
      super(urlString);
   }

   public ClientServerURL(ServerURL context, String urlString) throws MalformedURLException {
      super(context, urlString);
   }

   public ClientServerURL(String protocol, String host, int port, String file) throws MalformedURLException {
      super(protocol, host, port, file);
   }

   public RJVM findOrCreateRJVM() throws IOException {
      return this.findOrCreateRJVM(false, (String)null, (HostID)null, 0);
   }

   public RJVM findRJVM() throws IOException {
      try {
         RJVMFinder rjvmfinder = new RJVMFinder(this);
         return rjvmfinder.findRJVM();
      } catch (IOException var2) {
         throw var2;
      }
   }

   public RJVM findOrCreateRJVM(String channel) throws IOException {
      return this.findOrCreateRJVM(false, channel, (HostID)null, 0);
   }

   public RJVM findOrCreateRJVM(boolean preferExistingRJVM, String channelName) throws IOException {
      return this.findOrCreateRJVM(preferExistingRJVM, channelName, (HostID)null, 0);
   }

   public RJVM findOrCreateRJVM(boolean preferExistingRJVM, String channelName, HostID hostIDToIgnore) throws IOException {
      return this.findOrCreateRJVM(preferExistingRJVM, channelName, hostIDToIgnore, 0);
   }

   public RJVM findOrCreateRJVM(boolean preferExistingRJVM, String channelName, HostID hostIDToIgnore, int requestTimeout) throws IOException {
      return this.findOrCreateRJVM(preferExistingRJVM, channelName, hostIDToIgnore, requestTimeout, false);
   }

   public RJVM findOrCreateRJVM(boolean preferExistingRJVM, String channelName, HostID hostIDToIgnore, int requestTimeout, boolean createNewFinder) throws IOException {
      try {
         RJVMFinder rjvmfinder;
         if (createNewFinder) {
            rjvmfinder = new RJVMFinder(this);
         } else {
            rjvmfinder = this.getFinder();
         }

         RJVM rjvm;
         synchronized(rjvmfinder) {
            rjvm = rjvmfinder.findOrCreate(preferExistingRJVM, channelName, hostIDToIgnore, requestTimeout);
            this.setCurrentPartitionURL(rjvmfinder.getPartitionUrl());
         }

         return rjvm;
      } catch (IOException var11) {
         if (!createNewFinder) {
            finderMap.remove(this.finder);
         }

         throw var11;
      }
   }

   private final RJVMFinder getFinder() throws IOException {
      RJVMEnvironment.getEnvironment().ensureInitialized();
      if (this.finder == null) {
         synchronized(this) {
            if (this.finder == null) {
               synchronized(finderMap) {
                  RJVMFinder newFinder = new RJVMFinder(this);
                  this.finder = (RJVMFinder)finderMap.get(newFinder);
                  if (this.finder == null) {
                     this.finder = newFinder;
                     finderMap.put(this.finder, this.finder);
                  } else {
                     try {
                        Protocol protocol = ProtocolManager.findProtocol(this.getProtocol());
                        byte newQOS = protocol.getQOS();
                        byte oldQOS = ProtocolManager.findProtocol(this.finder.getURL().getProtocol()).getQOS();
                        if (oldQOS == 101 && newQOS > oldQOS) {
                           throw new IOException("Unable to open a secure connection to a non secure port");
                        }
                     } catch (UnknownProtocolException var9) {
                     }
                  }
               }
            }
         }
      }

      return this.finder;
   }

   public boolean isHostedByLocalRJVM() throws UnknownHostException {
      if (this.finder != null) {
         return this.finder.isHostedByLocalRJVM();
      } else {
         try {
            return this.getFinder().isHostedByLocalRJVM();
         } catch (IOException var3) {
            UnknownHostException exp = new UnknownHostException(var3.getMessage());
            exp.initCause(var3);
            throw exp;
         }
      }
   }

   public String getCurrentURL() {
      int indx;
      if (this.finder != null) {
         indx = this.finder.getCurrentHostIdx();
         return this.getAddressCount() == 1 ? this.getUrlString(0) : this.getUrlString(indx);
      } else {
         try {
            indx = this.getFinder().getCurrentHostIdx();
            return this.getAddressCount() == 1 ? this.getUrlString(0) : this.getUrlString(indx);
         } catch (IOException var2) {
            return this.getUrlString();
         }
      }
   }

   public String getCurrentPartitionURL() {
      return this.currentPartitionURL;
   }

   public void setCurrentPartitionURL(String currentPartitionURL) {
      this.currentPartitionURL = currentPartitionURL;
   }
}
