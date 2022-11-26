package org.apache.openjpa.event;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.Serialization;
import serp.util.Strings;

public class TCPRemoteCommitProvider extends AbstractRemoteCommitProvider implements Configurable {
   private static final int DEFAULT_PORT = 5636;
   private static final Localizer s_loc = Localizer.forPackage(TCPRemoteCommitProvider.class);
   private static long s_idSequence = System.currentTimeMillis();
   private static final Map s_portListenerMap = new HashMap();
   private long _id;
   private byte[] _localhost;
   private int _port = 5636;
   private int _maxActive = 2;
   private int _maxIdle = 2;
   private int _recoveryTimeMillis = 15000;
   private TCPPortListener _listener;
   private BroadcastQueue _broadcastQueue = new BroadcastQueue();
   private final List _broadcastThreads = Collections.synchronizedList(new LinkedList());
   private ArrayList _addresses = new ArrayList();
   private ReentrantLock _addressesLock;
   private static final long PROTOCOL_VERSION = 338210047L;

   public TCPRemoteCommitProvider() throws UnknownHostException {
      Class var1 = TCPRemoteCommitProvider.class;
      synchronized(TCPRemoteCommitProvider.class) {
         this._id = (long)(s_idSequence++);
      }

      this._localhost = InetAddress.getLocalHost().getAddress();
      this._addressesLock = new ReentrantLock();
      this.setNumBroadcastThreads(2);
   }

   public int getPort() {
      return this._port;
   }

   public void setPort(int port) {
      this._port = port;
   }

   public void setRecoveryTimeMillis(int recoverytime) {
      this._recoveryTimeMillis = recoverytime;
   }

   public int getRecoveryTimeMillis() {
      return this._recoveryTimeMillis;
   }

   public void setMaxActive(int maxActive) {
      this._maxActive = maxActive;
   }

   public int getMaxActive() {
      return this._maxActive;
   }

   public void setMaxIdle(int maxIdle) {
      this._maxIdle = maxIdle;
   }

   public int getMaxIdle() {
      return this._maxIdle;
   }

   public void setNumBroadcastThreads(int numBroadcastThreads) {
      synchronized(this._broadcastThreads) {
         int cur = this._broadcastThreads.size();
         int i;
         BroadcastWorkerThread wt;
         if (cur > numBroadcastThreads) {
            for(i = numBroadcastThreads; i < cur; ++i) {
               wt = (BroadcastWorkerThread)this._broadcastThreads.remove(0);
               wt.setRunning(false);
            }
         } else if (cur < numBroadcastThreads) {
            for(i = cur; i < numBroadcastThreads; ++i) {
               wt = new BroadcastWorkerThread();
               wt.setDaemon(true);
               wt.start();
               this._broadcastThreads.add(wt);
            }
         }

      }
   }

   public int getNumBroadcastThreads() {
      return this._broadcastThreads.size();
   }

   public void setAddresses(String names) throws UnknownHostException {
      this._addressesLock.lock();

      try {
         Iterator iter = this._addresses.iterator();

         while(iter.hasNext()) {
            ((HostAddress)iter.next()).close();
         }

         String[] toks = Strings.split(names, ";", 0);
         this._addresses = new ArrayList(toks.length);
         InetAddress localhost = InetAddress.getLocalHost();
         String localhostName = localhost.getHostName();

         for(int i = 0; i < toks.length; ++i) {
            String host = toks[i];
            int colon = host.indexOf(58);
            String hostname;
            int tmpPort;
            if (colon != -1) {
               hostname = host.substring(0, colon);
               tmpPort = Integer.parseInt(host.substring(colon + 1));
            } else {
               hostname = host;
               tmpPort = 5636;
            }

            InetAddress tmpAddress = (InetAddress)AccessController.doPrivileged(J2DoPrivHelper.getByNameAction(hostname));
            if (localhostName.equals(hostname)) {
               if (this.log.isTraceEnabled()) {
                  this.log.trace(s_loc.get("tcp-address-asself", (Object)(tmpAddress.getHostName() + ":" + tmpPort)));
               }
            } else {
               HostAddress newAddress = new HostAddress(host);
               this._addresses.add(newAddress);
               if (this.log.isTraceEnabled()) {
                  this.log.trace(s_loc.get("tcp-address-set", (Object)(newAddress._address.getHostName() + ":" + newAddress._port)));
               }
            }
         }

      } catch (PrivilegedActionException var16) {
         throw (UnknownHostException)var16.getException();
      } finally {
         this._addressesLock.unlock();
      }
   }

   public void endConfiguration() {
      super.endConfiguration();
      synchronized(s_portListenerMap) {
         this._listener = (TCPPortListener)s_portListenerMap.get(String.valueOf(this._port));
         if (this._listener == null || !this._listener.isRunning() && this._listener._port == this._port) {
            try {
               this._listener = new TCPPortListener(this._port, this.log);
               this._listener.listen();
               s_portListenerMap.put(String.valueOf(this._port), this._listener);
            } catch (Exception var9) {
               throw (new GeneralException(s_loc.get("tcp-init-exception", (Object)String.valueOf(this._port)), var9)).setFatal(true);
            }
         } else {
            if (!this._listener.isRunning()) {
               throw new InternalException(s_loc.get("tcp-listener-broken"));
            }

            if (this._listener._port != this._port) {
               throw (new GeneralException(s_loc.get("tcp-not-equal", (Object)String.valueOf(this._port)))).setFatal(true);
            }
         }

         this._listener.addProvider(this);
      }

      this._addressesLock.lock();

      try {
         Iterator iter = this._addresses.iterator();

         while(iter.hasNext()) {
            HostAddress curAddress = (HostAddress)iter.next();
            curAddress.setMaxActive(this._maxActive);
            curAddress.setMaxIdle(this._maxIdle);
         }
      } finally {
         this._addressesLock.unlock();
      }

   }

   public void broadcast(RemoteCommitEvent event) {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         oos.writeLong(338210047L);
         oos.writeLong(this._id);
         oos.writeInt(this._port);
         oos.writeObject(this._localhost);
         oos.writeObject(event);
         oos.flush();
         byte[] bytes = baos.toByteArray();
         baos.close();
         if (this._broadcastThreads.isEmpty()) {
            this.sendUpdatePacket(bytes);
         } else {
            this._broadcastQueue.addPacket(bytes);
         }
      } catch (IOException var5) {
         if (this.log.isWarnEnabled()) {
            this.log.warn(s_loc.get("tcp-payload-create-error"), var5);
         }
      }

   }

   private void sendUpdatePacket(byte[] bytes) {
      this._addressesLock.lock();

      try {
         Iterator iter = this._addresses.iterator();

         while(iter.hasNext()) {
            ((HostAddress)iter.next()).sendUpdatePacket(bytes);
         }
      } finally {
         this._addressesLock.unlock();
      }

   }

   public void close() {
      if (this._listener != null) {
         this._listener.removeProvider(this);
      }

      this._broadcastQueue.close();

      while(!this._broadcastThreads.isEmpty()) {
         try {
            Thread.sleep(500L);
         } catch (InterruptedException var6) {
         }
      }

      this._addressesLock.lock();

      try {
         Iterator iter = this._addresses.iterator();

         while(iter.hasNext()) {
            ((HostAddress)iter.next()).close();
         }
      } finally {
         this._addressesLock.unlock();
      }

   }

   private class HostAddress {
      private InetAddress _address;
      private int _port;
      private long _timeLastError;
      private boolean _isAvailable;
      private int _infosIssued;
      private GenericObjectPool _socketPool;

      private HostAddress(String host) throws UnknownHostException {
         this._infosIssued = 0;
         int colon = host.indexOf(58);

         try {
            if (colon != -1) {
               this._address = (InetAddress)AccessController.doPrivileged(J2DoPrivHelper.getByNameAction(host.substring(0, colon)));
               this._port = Integer.parseInt(host.substring(colon + 1));
            } else {
               this._address = (InetAddress)AccessController.doPrivileged(J2DoPrivHelper.getByNameAction(host));
               this._port = 5636;
            }
         } catch (PrivilegedActionException var5) {
            throw (UnknownHostException)var5.getException();
         }

         this._socketPool = new GenericObjectPool(new SocketPoolableObjectFactory(), TCPRemoteCommitProvider.this._maxActive, (byte)1, -1L);
         this._isAvailable = true;
      }

      private void setMaxActive(int maxActive) {
         this._socketPool.setMaxActive(maxActive);
      }

      private void setMaxIdle(int maxIdle) {
         this._socketPool.setMaxIdle(maxIdle);
      }

      public void close() {
         try {
            this._socketPool.close();
         } catch (Exception var2) {
            if (TCPRemoteCommitProvider.this.log.isWarnEnabled()) {
               TCPRemoteCommitProvider.this.log.warn(TCPRemoteCommitProvider.s_loc.get("tcp-close-pool-error"), var2);
            }
         }

      }

      private void sendUpdatePacket(byte[] bytes) {
         if (!this._isAvailable) {
            long now = System.currentTimeMillis();
            if (now - this._timeLastError < (long)TCPRemoteCommitProvider.this._recoveryTimeMillis) {
               return;
            }
         }

         Socket s = null;

         try {
            s = this.getSocket();
            OutputStream os = s.getOutputStream();
            os.write(bytes);
            os.flush();
            if (TCPRemoteCommitProvider.this.log.isTraceEnabled()) {
               TCPRemoteCommitProvider.this.log.trace(TCPRemoteCommitProvider.s_loc.get("tcp-sent-update", this._address.getHostAddress() + ":" + this._port, String.valueOf(s.getLocalPort())));
            }

            this._isAvailable = true;
            this._infosIssued = 0;
            this.returnSocket(s);
         } catch (Exception var6) {
            if (s != null) {
               this.closeSocket(s);
            }

            this.clearAllSockets();
            if (this._isAvailable) {
               if (TCPRemoteCommitProvider.this.log.isWarnEnabled()) {
                  TCPRemoteCommitProvider.this.log.warn(TCPRemoteCommitProvider.s_loc.get("tcp-send-error", (Object)(this._address.getHostAddress() + ":" + this._port)), var6);
               }

               this._isAvailable = false;
               this._timeLastError = System.currentTimeMillis();
            } else {
               long nowx = System.currentTimeMillis();
               if (nowx - this._timeLastError > (long)TCPRemoteCommitProvider.this._recoveryTimeMillis && this._infosIssued < 5) {
                  this._timeLastError = System.currentTimeMillis();
                  if (TCPRemoteCommitProvider.this.log.isInfoEnabled()) {
                     TCPRemoteCommitProvider.this.log.info(TCPRemoteCommitProvider.s_loc.get("tcp-send-still-error", (Object)(this._address.getHostAddress() + ":" + this._port)), var6);
                  }

                  ++this._infosIssued;
               }
            }
         }

      }

      private Socket getSocket() throws Exception {
         return (Socket)this._socketPool.borrowObject();
      }

      private void returnSocket(Socket s) throws Exception {
         this._socketPool.returnObject(s);
      }

      private void clearAllSockets() {
         this._socketPool.clear();
      }

      private void closeSocket(Socket s) {
         try {
            this._socketPool.invalidateObject(s);
         } catch (Exception var3) {
         }

      }

      // $FF: synthetic method
      HostAddress(String x1, Object x2) throws UnknownHostException {
         this(x1);
      }

      private class SocketPoolableObjectFactory implements PoolableObjectFactory {
         private SocketPoolableObjectFactory() {
         }

         public Object makeObject() throws IOException {
            try {
               Socket s = (Socket)AccessController.doPrivileged(J2DoPrivHelper.newSocketAction(HostAddress.this._address, HostAddress.this._port));
               if (TCPRemoteCommitProvider.this.log.isTraceEnabled()) {
                  TCPRemoteCommitProvider.this.log.trace(TCPRemoteCommitProvider.s_loc.get("tcp-open-connection", HostAddress.this._address + ":" + HostAddress.this._port, "" + s.getLocalPort()));
               }

               return s;
            } catch (PrivilegedActionException var2) {
               throw (IOException)var2.getException();
            }
         }

         public void destroyObject(Object obj) {
            try {
               Socket s = (Socket)obj;
               if (TCPRemoteCommitProvider.this.log.isTraceEnabled()) {
                  TCPRemoteCommitProvider.this.log.trace(TCPRemoteCommitProvider.s_loc.get("tcp-close-sending-socket", HostAddress.this._address + ":" + HostAddress.this._port, "" + s.getLocalPort()));
               }

               s.close();
            } catch (Exception var3) {
               TCPRemoteCommitProvider.this.log.warn(TCPRemoteCommitProvider.s_loc.get("tcp-close-socket-error", (Object)(HostAddress.this._address.getHostAddress() + ":" + HostAddress.this._port)), var3);
            }

         }

         public boolean validateObject(Object obj) {
            return true;
         }

         public void activateObject(Object value) {
         }

         public void passivateObject(Object value) {
         }

         // $FF: synthetic method
         SocketPoolableObjectFactory(Object x1) {
            this();
         }
      }
   }

   private static class TCPPortListener implements Runnable {
      private final Log _log;
      private ServerSocket _receiveSocket;
      private Thread _acceptThread;
      private Set _receiverThreads;
      private final Set _providers;
      private byte[] _localhost;
      private int _port;
      private boolean _isRunning;

      private TCPPortListener(int port, Log log) throws IOException {
         this._receiverThreads = new HashSet();
         this._providers = new HashSet();
         this._isRunning = false;
         this._port = port;
         this._log = log;

         try {
            this._receiveSocket = (ServerSocket)AccessController.doPrivileged(J2DoPrivHelper.newServerSocketAction(this._port));
         } catch (PrivilegedActionException var4) {
            throw (IOException)var4.getException();
         }

         this._localhost = InetAddress.getLocalHost().getAddress();
         if (this._log.isTraceEnabled()) {
            this._log.info(TCPRemoteCommitProvider.s_loc.get("tcp-start-listener", (Object)String.valueOf(this._port)));
         }

      }

      private void listen() {
         this._acceptThread = new Thread(this);
         this._acceptThread.setDaemon(true);
         this._acceptThread.start();
      }

      private void addProvider(TCPRemoteCommitProvider provider) {
         synchronized(this._providers) {
            this._providers.add(provider);
         }
      }

      private synchronized void removeProvider(TCPRemoteCommitProvider provider) {
         synchronized(this._providers) {
            this._providers.remove(provider);
            if (this._providers.size() == 0) {
               this._isRunning = false;

               try {
                  this._receiveSocket.close();
               } catch (IOException var5) {
                  if (this._log.isWarnEnabled()) {
                     this._log.warn(TCPRemoteCommitProvider.s_loc.get("tcp-close-error"), var5);
                  }
               }

               this._acceptThread.interrupt();
            }

         }
      }

      private boolean isRunning() {
         synchronized(this._providers) {
            return this._isRunning;
         }
      }

      public void run() {
         synchronized(this._providers) {
            this._isRunning = true;
         }

         Socket s = null;

         while(this._isRunning) {
            try {
               s = null;
               s = (Socket)AccessController.doPrivileged(J2DoPrivHelper.acceptAction(this._receiveSocket));
               if (this._log.isTraceEnabled()) {
                  this._log.trace(TCPRemoteCommitProvider.s_loc.get("tcp-received-connection", (Object)(s.getInetAddress().getHostAddress() + ":" + s.getPort())));
               }

               ReceiveSocketHandler sh = new ReceiveSocketHandler(s);
               Thread receiverThread = new Thread(sh);
               receiverThread.setDaemon(true);
               receiverThread.start();
               this._receiverThreads.add(receiverThread);
            } catch (Exception var10) {
               Exception e = var10;
               if (var10 instanceof PrivilegedActionException) {
                  e = ((PrivilegedActionException)var10).getException();
               }

               if ((!(e instanceof SocketException) || this._isRunning) && this._log.isWarnEnabled()) {
                  this._log.warn(TCPRemoteCommitProvider.s_loc.get("tcp-accept-error"), e);
               }

               try {
                  if (s != null) {
                     s.close();
                  }
               } catch (Exception var9) {
                  if (this._log.isWarnEnabled()) {
                     this._log.warn(TCPRemoteCommitProvider.s_loc.get("tcp-close-error"), e);
                  }
               }
            }
         }

         Iterator iter = this._receiverThreads.iterator();

         while(iter.hasNext()) {
            Thread worker = (Thread)iter.next();
            worker.interrupt();
         }

         synchronized(this._providers) {
            try {
               if (this._isRunning) {
                  this._receiveSocket.close();
               }
            } catch (Exception var7) {
               if (this._log.isWarnEnabled()) {
                  this._log.warn(TCPRemoteCommitProvider.s_loc.get("tcp-close-error"), var7);
               }
            }

            this._isRunning = false;
            if (this._log.isTraceEnabled()) {
               this._log.trace(TCPRemoteCommitProvider.s_loc.get("tcp-close-listener", (Object)(this._port + "")));
            }

         }
      }

      // $FF: synthetic method
      TCPPortListener(int x0, Log x1, Object x2) throws IOException {
         this(x0, x1);
      }

      private class ReceiveSocketHandler implements Runnable {
         private InputStream _in;
         private Socket _s;

         private ReceiveSocketHandler(Socket s) {
            this._s = s;

            try {
               this._s.setTcpNoDelay(true);
               this._in = new BufferedInputStream(s.getInputStream());
            } catch (IOException var4) {
               if (TCPPortListener.this._log.isInfoEnabled()) {
                  TCPPortListener.this._log.info(TCPRemoteCommitProvider.s_loc.get("tcp-socket-option-error"), var4);
               }

               this._s = null;
            } catch (Exception var5) {
               if (TCPPortListener.this._log.isWarnEnabled()) {
                  TCPPortListener.this._log.warn(TCPRemoteCommitProvider.s_loc.get("tcp-receive-error"), var5);
               }

               this._s = null;
            }

         }

         public void run() {
            if (this._s != null) {
               while(TCPPortListener.this._isRunning && this._s != null) {
                  try {
                     this.handle(this._in);
                  } catch (EOFException var3) {
                     if (TCPPortListener.this._log.isTraceEnabled()) {
                        TCPPortListener.this._log.trace(TCPRemoteCommitProvider.s_loc.get("tcp-close-socket", (Object)(this._s.getInetAddress().getHostAddress() + ":" + this._s.getPort())));
                     }
                     break;
                  } catch (Throwable var4) {
                     if (TCPPortListener.this._log.isWarnEnabled()) {
                        TCPPortListener.this._log.warn(TCPRemoteCommitProvider.s_loc.get("tcp-receive-error"), var4);
                     }
                     break;
                  }
               }

               try {
                  this._in.close();
                  if (this._s != null) {
                     this._s.close();
                  }
               } catch (IOException var2) {
                  TCPPortListener.this._log.warn(TCPRemoteCommitProvider.s_loc.get("tcp-close-socket-error", (Object)(this._s.getInetAddress().getHostAddress() + ":" + this._s.getPort())), var2);
               }

            }
         }

         private void handle(InputStream in) throws IOException, ClassNotFoundException {
            ObjectInputStream ois = new Serialization.ClassResolvingObjectInputStream(in);
            long protocolVersion = ois.readLong();
            if (protocolVersion != 338210047L && TCPPortListener.this._log.isWarnEnabled()) {
               TCPPortListener.this._log.warn(TCPRemoteCommitProvider.s_loc.get("tcp-wrong-version-error", (Object)(this._s.getInetAddress().getHostAddress() + ":" + this._s.getPort())));
            } else {
               long senderId = ois.readLong();
               int senderPort = ois.readInt();
               byte[] senderAddress = (byte[])((byte[])ois.readObject());
               RemoteCommitEvent rce = (RemoteCommitEvent)ois.readObject();
               if (TCPPortListener.this._log.isTraceEnabled()) {
                  TCPPortListener.this._log.trace(TCPRemoteCommitProvider.s_loc.get("tcp-received-event", (Object)(this._s.getInetAddress().getHostAddress() + ":" + this._s.getPort())));
               }

               boolean fromSelf = senderPort == TCPPortListener.this._port && Arrays.equals(senderAddress, TCPPortListener.this._localhost);
               synchronized(TCPPortListener.this._providers) {
                  Iterator iter = TCPPortListener.this._providers.iterator();

                  while(true) {
                     TCPRemoteCommitProvider provider;
                     do {
                        if (!iter.hasNext()) {
                           return;
                        }

                        provider = (TCPRemoteCommitProvider)iter.next();
                     } while(senderId == provider._id && fromSelf);

                     provider.eventManager.fireEvent(rce);
                  }
               }
            }
         }

         // $FF: synthetic method
         ReceiveSocketHandler(Socket x1, Object x2) {
            this(x1);
         }
      }
   }

   private class BroadcastWorkerThread extends Thread {
      private boolean _keepRunning;

      private BroadcastWorkerThread() {
         this._keepRunning = true;
      }

      public void run() {
         while(true) {
            if (this._keepRunning) {
               try {
                  byte[] bytes = TCPRemoteCommitProvider.this._broadcastQueue.removePacket();
                  if (bytes != null) {
                     TCPRemoteCommitProvider.this.sendUpdatePacket(bytes);
                     continue;
                  }

                  if (TCPRemoteCommitProvider.this._broadcastQueue.isClosed()) {
                     this._keepRunning = false;
                  }
                  continue;
               } catch (InterruptedException var2) {
               }
            }

            this.remove();
            return;
         }
      }

      public void setRunning(boolean keepRunning) {
         this._keepRunning = keepRunning;
      }

      private void remove() {
         TCPRemoteCommitProvider.this._broadcastThreads.remove(this);
      }

      // $FF: synthetic method
      BroadcastWorkerThread(Object x1) {
         this();
      }
   }

   private static class BroadcastQueue {
      private LinkedList _packetQueue;
      private boolean _closed;

      private BroadcastQueue() {
         this._packetQueue = new LinkedList();
         this._closed = false;
      }

      public synchronized void close() {
         this._closed = true;
         this.notifyAll();
      }

      public synchronized boolean isClosed() {
         return this._closed;
      }

      public synchronized void addPacket(byte[] bytes) {
         this._packetQueue.addLast(bytes);
         this.notify();
      }

      public synchronized byte[] removePacket() throws InterruptedException {
         while(!this._closed && this._packetQueue.isEmpty()) {
            this.wait();
         }

         if (this._packetQueue.isEmpty()) {
            return null;
         } else {
            return (byte[])((byte[])this._packetQueue.removeFirst());
         }
      }

      // $FF: synthetic method
      BroadcastQueue(Object x0) {
         this();
      }
   }
}
