package weblogic.transaction.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.PrivilegedExceptionAction;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import javax.transaction.Synchronization;
import javax.transaction.xa.XAException;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.PackageInfo;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.kernel.AuditableThread;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.protocol.ServerIdentity;
import weblogic.transaction.ChannelInterface;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.collections.ArraySet;
import weblogic.utils.io.UnsyncByteArrayInputStream;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public abstract class PlatformHelperCommon extends PlatformHelper {
   private static final String NEW_METHOD_TEMP_PATCH = "CR111924";

   public String throwable2StackTrace(Throwable se) {
      return StackTraceUtilsClient.throwable2StackTrace(se);
   }

   public ClassLoader getContextClassLoader(ClassLoader contextClassLoader, Synchronization synchronization) {
      Annotation annotation = null;
      if (contextClassLoader instanceof GenericClassLoader) {
         annotation = ((GenericClassLoader)contextClassLoader).getAnnotation();
      }

      GenericClassLoader genericClassLoader = new GenericClassLoader(synchronization.getClass().getClassLoader());
      if (annotation != null) {
         genericClassLoader.setAnnotation(annotation);
      }

      return genericClassLoader;
   }

   public PlatformHelper.ArraySet newArraySet() {
      return new CloneableArraySet();
   }

   public String getEOLConstant() {
      return PlatformConstants.EOL;
   }

   public InputStream newUnsyncByteArrayInputStream(byte[] record) {
      return new UnsyncByteArrayInputStream(record);
   }

   public PlatformHelper.UnsyncByteArrayOutputStream newUnsyncByteArrayOutputStream() {
      return new PlatformHelper.UnsyncByteArrayOutputStream() {
         UnsyncByteArrayOutputStream delegate = new UnsyncByteArrayOutputStream();

         public byte[] toByteArray() {
            return this.delegate.toByteArray();
         }

         public void write(int i) throws IOException {
            this.delegate.write(i);
         }
      };
   }

   public String readAbbrevString(ObjectInput oi) throws IOException {
      return oi instanceof WLObjectInput ? ((WLObjectInput)oi).readAbbrevString() : oi.readUTF();
   }

   public void writeAbbrevString0(ObjectOutput oo, String s) throws IOException {
      if (oo instanceof WLObjectOutput) {
         ((WLObjectOutput)oo).writeAbbrevString(s);
      } else {
         oo.writeUTF(s);
      }

   }

   public int getVersion(ObjectOutput oo) throws IOException {
      int version = 4;
      if (oo instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)oo).getPeerInfo();
         if (pi.getMajor() < 6) {
            throw new IOException("Transaction context versions 1, 2, 3, 4 not compatible with peer: " + pi);
         }

         if (pi.getMajor() == 6 && pi.getMinor() < 1) {
            version = 1;
         }

         if (pi.getMajor() == 6 && pi.getMinor() == 1) {
            if (pi.getServicePack() != 0 && pi.getServicePack() != 1 && (pi.getServicePack() != 2 || pi.getRollingPatch() != 0)) {
               version = 3;
            } else {
               version = 2;
            }
         }
      }

      if (TxDebug.JTAPropagate.isDebugEnabled()) {
         String peerDesc;
         if (oo instanceof PeerInfoable) {
            PeerInfo pi = ((PeerInfoable)oo).getPeerInfo();
            peerDesc = pi.getReleaseVersion();
         } else {
            peerDesc = "<unknown>";
         }

         TxDebug.JTAPropagate.debug("PropagationContext: Peer=" + peerDesc + ", Version=" + version);
      }

      return version;
   }

   public CoordinatorFactory getCoordinatorFactory() {
      return new CoordinatorFactory();
   }

   public void initLoggingResourceRetry() {
   }

   public void registerFailedLLRTransactionLoggingResourceRetry(Object serverTransaction) {
   }

   public void txtrace(Object logger, TransactionImpl tx, String msg) {
      StringBuffer sb = new StringBuffer(100);
      if (tx != null) {
         sb.append(tx.getXID()).append(": ").append(tx.getName()).append(": ");
      }

      sb.append(msg);
      ((TxDebug)logger).debug(sb.toString());
   }

   public Object getTxThreadPropertyFromTxThreadLocal(Object txThreadLocal) {
      return ((AuditableThreadLocal)txThreadLocal).get();
   }

   public CoordinatorDescriptor findServerInDomains(String resName, Collection exceptServerNames, Collection remoteDescriptors) {
      return null;
   }

   public String[] getAllServerNamesInDomain() {
      return null;
   }

   public boolean resourceCheck(String name, String serverName, String providerURL) {
      return false;
   }

   public TransactionImpl getTransactionImplFromTxThreadLocal(Object txThreadLocal, Thread thread) {
      TransactionManagerImpl.TxThreadProperty txProp = (TransactionManagerImpl.TxThreadProperty)((AuditableThreadLocal)txThreadLocal).get((AuditableThread)thread);
      return txProp == null ? null : txProp.current;
   }

   public boolean useNewMethod(Object obj) {
      PeerInfo otherPeerInfo = null;
      if (obj instanceof PeerInfoable) {
         otherPeerInfo = ((PeerInfoable)obj).getPeerInfo();
      }

      if (otherPeerInfo != null) {
         if (TxDebug.JTAPropagate.isDebugEnabled()) {
            TxDebug.JTAPropagate.debug(" +++ otherPeerInfo.getMajor() :: " + otherPeerInfo.getMajor());
            TxDebug.JTAPropagate.debug(" +++ otherPeerInfo.getMinor() :: " + otherPeerInfo.getMinor());
            TxDebug.JTAPropagate.debug(" +++ otherPeerInfo.getServicePack() :: " + otherPeerInfo.getServicePack());
            TxDebug.JTAPropagate.debug(" +++ otherPeerInfo.getRollingPatch() :: " + otherPeerInfo.getRollingPatch());
            TxDebug.JTAPropagate.debug(" +++ otherPeerInfo.hasTemporaryPatch() :: " + otherPeerInfo.hasTemporaryPatch());
         }

         int major = otherPeerInfo.getMajor();
         int servicePack = otherPeerInfo.getServicePack();
         if ((major != 6 || servicePack < 6) && (major != 7 || servicePack < 5) && (major != 8 || servicePack < 2) && major < 9) {
            if (otherPeerInfo.hasTemporaryPatch()) {
               PackageInfo[] allPackages = otherPeerInfo.getPackages();

               for(int i = 0; i < allPackages.length; ++i) {
                  if (allPackages[i].getImplementationTitle().toUpperCase(Locale.ENGLISH).indexOf("CR111924") != -1) {
                     return true;
                  }
               }
            }

            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public Object getPeerInfo(ObjectInput oi) {
      return oi instanceof PeerInfoable ? ((PeerInfoable)oi).getPeerInfo() : null;
   }

   public void xatxtrace(Object logger, TransactionImpl tx, String msg, XAException ex) {
      StringBuffer sb = new StringBuffer(100);
      if (tx != null) {
         sb.append(tx.getXID()).append(": ").append(tx.getName()).append(": ");
      }

      sb.append(msg);
      if (ex != null) {
         sb.append(", error code: ").append(XAResourceHelper.xaErrorCodeToString(ex.errorCode)).append(", message: ").append(ex.getMessage());
      }

      ((TxDebug)logger).debug(tx, sb.toString(), ex);
   }

   public CoordinatorDescriptor getOrCreateCoordinatorDescriptor(Hashtable knownServers, Object serverObject, ChannelInterface channel) {
      ServerIdentity server = (ServerIdentity)serverObject;
      String serverID = CoordinatorDescriptor.getServerID(server.getDomainName(), server.getServerName());
      CoordinatorDescriptor cd = (CoordinatorDescriptor)knownServers.get(serverID);
      ChannelInterface sc;
      if (cd != null && this.isServer()) {
         sc = PlatformHelper.getPlatformHelper().findAdminChannel(server);
         if (sc != null && this.getInteropMode() != 1) {
            channel = sc;
         }

         String coURL = CoordinatorDescriptor.getCoordinatorURL(channel.getPublicAddress() + ":" + channel.getPublicPort(), server.getDomainName(), server.getServerName(), channel.getProtocolPrefix().toLowerCase(Locale.ENGLISH));
         cd.setSSLCoordinatorURL(coURL);
         cd.setNonSSLCoordinatorURL(coURL);
      } else {
         if (this.isServer() && this.getInteropMode() != 1) {
            sc = PlatformHelper.getPlatformHelper().findAdminChannel(server);
            if (sc != null) {
               channel = sc;
            }
         }

         cd = new CoordinatorDescriptor(channel.getPublicAddress() + ":" + channel.getPublicPort(), server.getDomainName(), server.getServerName(), channel.getProtocolPrefix().toLowerCase(Locale.ENGLISH));
      }

      return cd;
   }

   public boolean isSSLEnabled(Object serverObject, ChannelInterface channel) {
      ServerIdentity server = (ServerIdentity)serverObject;
      if (this.isServer() && this.getInteropMode() != 1) {
         ChannelInterface sc = this.findAdminChannel(server);
         if (sc != null) {
            channel = sc;
         }
      }

      String protocol = channel.getProtocolPrefix().toLowerCase(Locale.ENGLISH);
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("PlatformHelperCommon.isSSLEnabled channel:" + channel + " server:" + server + " protocol:" + protocol);
      }

      return protocol != null && (protocol.equalsIgnoreCase("t3s") || protocol.equalsIgnoreCase("https"));
   }

   public void runKernelAction(PrivilegedExceptionAction privilegedExceptionAction, String advertiseResource) throws Exception {
   }

   public JTARecoveryRuntime registerCrossDomainJTARecoveryRuntime(String siteName, String serverName) {
      return null;
   }

   public String getClusterName() {
      return null;
   }

   private class CloneableArraySet extends PlatformHelper.ArraySet implements Cloneable {
      ArraySet delegate;

      private CloneableArraySet() {
         super(PlatformHelperCommon.this);
         this.delegate = new ArraySet();
      }

      public Object clone() {
         return this.delegate.clone();
      }

      public int size() {
         return this.delegate.size();
      }

      public boolean isEmpty() {
         return this.delegate.isEmpty();
      }

      public boolean contains(Object o) {
         return this.delegate.contains(o);
      }

      public Iterator iterator() {
         return this.delegate.iterator();
      }

      public Object[] toArray() {
         return this.delegate.toArray();
      }

      public boolean add(Object o) {
         return this.delegate.add(o);
      }

      public boolean remove(Object o) {
         return this.delegate.remove(o);
      }

      public boolean containsAll(Collection objects) {
         return this.delegate.contains(objects);
      }

      public boolean addAll(Collection collection) {
         return this.delegate.addAll(collection);
      }

      public boolean retainAll(Collection objects) {
         return this.delegate.retainAll(objects);
      }

      public boolean removeAll(Collection objects) {
         return this.delegate.removeAll(objects);
      }

      public void clear() {
         this.delegate.clear();
      }

      public Object[] toArray(Object[] objects) {
         return this.delegate.toArray();
      }

      // $FF: synthetic method
      CloneableArraySet(Object x1) {
         this();
      }
   }
}
