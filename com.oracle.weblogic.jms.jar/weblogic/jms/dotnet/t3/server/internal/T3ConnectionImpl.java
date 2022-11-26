package weblogic.jms.dotnet.t3.server.internal;

import java.io.IOException;
import java.rmi.RemoteException;
import weblogic.common.internal.PackageInfo;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.VersionInfo;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.dotnet.t3.server.JMSCSharp;
import weblogic.jms.dotnet.t3.server.spi.T3Connection;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionGoneListener;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionHandle;
import weblogic.rjvm.JVMID;
import weblogic.rjvm.PeerGoneEvent;
import weblogic.rjvm.PeerGoneListener;
import weblogic.rjvm.RequestStream;
import weblogic.utils.io.ChunkedDataOutputStream;

public final class T3ConnectionImpl implements T3Connection, PeerGoneListener {
   private T3RJVM t3rjvm;
   private T3ConnectionHandleID id;
   private boolean closed = false;
   private boolean closedByClient = false;
   private PeerInfo peerInfo;
   private T3ConnectionGoneListener listener;

   public T3ConnectionImpl(T3RJVM t3rjvm, T3ConnectionHandleID id, int major, int minor, int servicePack, int rollingPatch, boolean temporaryPatch) throws RemoteException {
      this.t3rjvm = t3rjvm;
      this.id = id;
      this.peerInfo = new PeerInfo(major, minor, servicePack, rollingPatch, 0, temporaryPatch, (PackageInfo[])null);
      String version = "" + this.peerInfo.getMajor() + "." + this.peerInfo.getMinor() + "." + this.peerInfo.getServicePack() + "." + this.peerInfo.getRollingPatch() + "." + this.peerInfo.getPatchUpdate();
      if (!VersionInfo.theOne().compatible(version)) {
         throw new IllegalArgumentException("Remote version is not compatible");
      }
   }

   public PeerInfo getPeerInfo() {
      return this.peerInfo;
   }

   public ChunkedDataOutputStream getRequestStream() throws IOException {
      this.check_close();
      RequestStream req = this.t3rjvm.getRJVM().getRequestStream(this.t3rjvm.getServerChannel());
      req.writeByte(3);
      return (ChunkedDataOutputStream)req;
   }

   public void send(ChunkedDataOutputStream output) throws IOException {
      this.check_close();
      RequestStream req = (RequestStream)output;

      try {
         req.sendOneWay(100);
      } catch (RemoteException var5) {
         IOException e = new IOException(var5.toString());
         e.initCause(var5.getCause());
         throw e;
      }
   }

   public void setT3ConnectionGoneListener(T3ConnectionGoneListener listener) {
      boolean alreadyClosed = false;
      boolean closedByClient = false;
      synchronized(this) {
         alreadyClosed = this.closed;
         closedByClient = this.closedByClient;
         this.listener = listener;
      }

      if (alreadyClosed && !closedByClient) {
         ((T3ConnectionHandle)listener).onPeerGone(new T3ConnectionGoneEventImpl(new IOException("Remote RJMV is closed already")));
      }

   }

   public void peerGone(PeerGoneEvent event) {
      T3ConnectionGoneListener listenerLocal = null;
      synchronized(this) {
         if (this.closed) {
            return;
         }

         this.closed = true;
         if (JMSDebug.JMSDotNetT3Server.isDebugEnabled()) {
            JMSDebug.JMSDotNetT3Server.debug("T3 connection [" + this.id.getValue() + "] is terminated");
         }

         try {
            this.t3rjvm.getRJVM().disconnect();
         } catch (Throwable var7) {
            if (JMSDebug.JMSDotNetT3Server.isDebugEnabled()) {
               JMSDebug.JMSDotNetT3Server.debug("Failed to disconnect RJVM.", var7);
            }
         }

         JMSCSharp.getInstance().remove_connection(this.id);
         listenerLocal = this.listener;
         this.listener = null;
      }

      if (listenerLocal != null) {
         try {
            listenerLocal.onPeerGone(new T3ConnectionGoneEventImpl(event));
         } catch (Throwable var6) {
            if (JMSDebug.JMSDotNetT3Server.isDebugEnabled()) {
               JMSDebug.JMSDotNetT3Server.debug("Failed to notify listener. ", var6);
            }
         }
      }

   }

   public synchronized void shutdown() {
      if (!this.closed) {
         this.closedByClient = true;
         this.listener = null;
         this.peerGone((PeerGoneEvent)null);
      }
   }

   private synchronized void check_close() throws IOException {
      if (this.closed) {
         throw new IOException("T3 connection is closed");
      }
   }

   public synchronized boolean isClosed() {
      return this.closed;
   }

   public JVMID getRJVMId() {
      return this.t3rjvm.getRJVM().getID();
   }
}
