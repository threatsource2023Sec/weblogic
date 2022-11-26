package weblogic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.StandardSocketOptions;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.Kernel;
import weblogic.socket.internal.SocketEnvironment;
import weblogic.socket.utils.SDPSocketUtils;
import weblogic.utils.collections.ConcurrentPool;
import weblogic.utils.io.Chunk;

class NIOSocketMuxer extends ServerSocketMuxer {
   private static final int MULTIPLIER = 1540483477;
   private static final int OP_READ_IDX = op2Idx(1);
   private static final int OP_WRITE_IDX = op2Idx(4);
   private final Selector[] selectors;
   private final ArrayList[][] registerLists;
   private final ArrayList[][] interestLists;
   private final AtomicInteger[] willNotice;
   private int nextId;
   public static final DebugLogger debugPPP;
   protected static final int MUXERS;
   protected final int SELECTORS;
   private final ConcurrentPool selectorPool;

   public NIOSocketMuxer() throws IOException {
      this(MUXERS);
      this.startSocketReaderThreads("weblogic.socket.Muxer");
   }

   protected NIOSocketMuxer(int nSelectors) throws IOException {
      this.selectorPool = new ConcurrentPool(128);
      this.SELECTORS = nSelectors;
      this.selectors = new Selector[this.SELECTORS];
      this.registerLists = (ArrayList[][])(new ArrayList[this.SELECTORS][2]);
      this.interestLists = (ArrayList[][])(new ArrayList[this.SELECTORS][2]);
      this.willNotice = new AtomicInteger[this.SELECTORS];

      for(int i = 0; i < this.SELECTORS; ++i) {
         this.selectors[i] = Selector.open();
         this.willNotice[i] = new AtomicInteger(1);

         for(int j = 0; j < 2; ++j) {
            this.registerLists[i][j] = new ArrayList();
            this.interestLists[i][j] = new ArrayList();
         }
      }

   }

   private int selectorIndex(SocketChannel sc) {
      if (this.SELECTORS > 1 && sc != null) {
         int start = sc.hashCode();
         int result = start * 1540483477;
         result ^= result >> 24;
         result *= 1540483477;
         result ^= result >> 13;
         result *= 1540483477;
         result ^= result >> 15;
         int index = result % this.SELECTORS;
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
            SocketLogger.logDebug("NIOSocketMuxer | hash start = " + start + " | hash result = " + result + " | index = " + index);
         }

         return index;
      } else {
         return 0;
      }
   }

   private int selectorIndex(NIOSocketInfo nInfo) {
      return this.SELECTORS > 1 ? nInfo.getSelectorIndex() : 0;
   }

   public boolean isAsyncMuxer() {
      return true;
   }

   public void register(MuxableSocket ms) throws IOException {
      SocketChannel sc = ms.getSocket().getChannel();
      if (sc == null) {
         throw new IllegalArgumentException(SocketChannel.class + ": " + sc);
      } else {
         sc.configureBlocking(false);
         int selectorIndex = this.selectorIndex(sc);
         ms.setSocketInfo(new NIOSocketInfo(ms, selectorIndex));
         super.register(ms);
      }
   }

   public void read(MuxableSocket ms) {
      this.internalRead(ms, (NIOSocketInfo)ms.getSocketInfo());
   }

   public void register(Collection muxableSockets) {
      Iterator mSockets = muxableSockets.iterator();

      while(mSockets.hasNext()) {
         MuxableSocket ms = (MuxableSocket)mSockets.next();
         SocketChannel sc = ms.getSocket().getChannel();
         if (sc == null) {
            mSockets.remove();
            this.cancelIo(ms);
            throw new IllegalArgumentException(SocketChannel.class + ": " + sc);
         }

         try {
            sc.configureBlocking(false);
         } catch (IOException var6) {
            SocketLogger.logDebugThrowable("Unexpected socket channel state", var6);
            mSockets.remove();
            this.cancelIo(ms);
            continue;
         }

         int selectorIndex = this.selectorIndex(sc);
         ms.setSocketInfo(new NIOSocketInfo(ms, selectorIndex));
      }

      super.register(muxableSockets);
   }

   public void read(Collection muxableSockets) {
      ArrayList[] tmpRegisterLists = (ArrayList[])(new ArrayList[this.SELECTORS]);

      for(int i = 0; i < this.SELECTORS; ++i) {
         tmpRegisterLists[i] = new ArrayList();
      }

      Iterator var11 = muxableSockets.iterator();

      while(true) {
         while(true) {
            MuxableSocket ms;
            NIOSocketInfo nInfo;
            do {
               if (!var11.hasNext()) {
                  return;
               }

               ms = (MuxableSocket)var11.next();
               nInfo = (NIOSocketInfo)ms.getSocketInfo();
            } while(!this.initiateIO(nInfo));

            int index;
            try {
               if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
                  SocketLogger.logDebug("read: sockInfo=" + nInfo);
               }

               SocketChannel sc = nInfo.getSocketChannel();
               if (sc != null) {
                  if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail() && sc.isBlocking()) {
                     SocketLogger.logDebugThrowable("Unexpected socket channel state", new Exception("The SocketChannel is in  blocking mode!"));
                  }

                  index = this.selectorIndex(nInfo);
                  if (nInfo.getSelectionKey() == null) {
                     tmpRegisterLists[index].add(nInfo);
                  } else {
                     if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
                        SocketLogger.logDebug("set interest ops for: sockInfo=" + nInfo);
                     }

                     nInfo.getSelectionKey().interestOps(1);
                  }
                  break;
               }

               ms.hasException(new IOException("SocketChannel not available"));
            } catch (Throwable var10) {
               if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
                  SocketLogger.logDebugThrowable("register for Selection failed for ms=" + ms.getSocketInfo() + " with: ", (Exception)var10);
               }

               index = this.selectorIndex(nInfo);
               tmpRegisterLists[index].remove(nInfo);
               this.deliverHasException(ms, var10);
               break;
            }
         }

         for(int i = 0; i < this.SELECTORS; ++i) {
            if (!tmpRegisterLists[i].isEmpty()) {
               synchronized(this.registerLists[i][OP_READ_IDX]) {
                  this.registerLists[i][OP_READ_IDX].addAll(tmpRegisterLists[i]);
               }

               this.wakeup(i);
            }
         }
      }
   }

   protected void cancelIo(MuxableSocket ms) {
      super.cancelIo(ms);
      if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
         SocketLogger.logDebug("explicitly calling cleanupSocket for ms=" + ms);
      }

      this.cleanupSocket(ms, ms.getSocketInfo());
   }

   protected void closeSocket(Socket s) {
      if (!s.isOutputShutdown()) {
         try {
            s.shutdownOutput();
         } catch (Exception var6) {
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
               SocketLogger.logDebugThrowable("shutdownOutput error for socket=" + s, var6);
            }
         }
      }

      SocketChannel sc = s.getChannel();
      if (sc != null) {
         if (sc.isOpen()) {
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
               SocketLogger.logDebug("close socket=" + s);
            }

            try {
               sc.close();
            } catch (Exception var5) {
               if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
                  SocketLogger.logDebugThrowable("close channel error for socket=" + s, var5);
               }
            }
         }

         this.wakeup(this.selectorIndex(sc));
      } else {
         for(int i = 0; i < this.selectors.length; ++i) {
            this.wakeup(i);
         }
      }

      try {
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerConnection()) {
            SocketLogger.logDebug("Closing raw socket " + s);
         }

         s.getOutputStream().close();
         s.getInputStream().close();
         s.close();
      } catch (Exception var4) {
      }

   }

   private void wakeup(int i) {
      if (this.willNotice[i].get() == 0 && this.willNotice[i].compareAndSet(0, 1)) {
         this.selectors[i].wakeup();
      }
   }

   private void internalRead(MuxableSocket ms, NIOSocketInfo nInfo) {
      this.enableSelectionKeyInterest(ms, nInfo, 1);
   }

   void enableSelectionKeyInterest(MuxableSocket ms, NIOSocketInfo nInfo, int selectionKeyInterest) {
      if (this.initiateIO(nInfo)) {
         try {
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
               SocketLogger.logDebug("read: sockInfo=" + nInfo);
            }

            SocketChannel sc = nInfo.getSocketChannel();
            if (sc == null) {
               ms.hasException(new IOException("SocketChannel not available"));
               return;
            }

            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail() && sc.isBlocking()) {
               SocketLogger.logDebugThrowable("Unexpected socket channel state", new Exception("The SocketChannel is in  blocking mode!"));
            }

            int index = this.selectorIndex(nInfo);
            int opIdx = op2Idx(selectionKeyInterest);
            if (nInfo.getSelectionKey() == null) {
               synchronized(this.registerLists[index][opIdx]) {
                  this.registerLists[index][opIdx].add(nInfo);
               }
            } else {
               if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
                  SocketLogger.logDebug("set interest ops for: sockInfo=" + nInfo);
               }

               synchronized(this.interestLists[index][opIdx]) {
                  this.interestLists[index][opIdx].add(nInfo);
               }
            }

            this.wakeup(index);
         } catch (ThreadDeath var12) {
            throw var12;
         } catch (Throwable var13) {
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
               SocketLogger.logDebugThrowable("register for Selection failed for ms=" + ms.getSocketInfo() + " with: ", var13);
            }

            this.deliverHasException(ms, var13);
         }

      }
   }

   public Socket newSocket(InetAddress address, int port, int timeout) throws IOException {
      return this.newSocket(address, port, (InetAddress)null, -1, timeout);
   }

   Selector findOrCreateSelector() throws IOException {
      Selector selector = (Selector)this.selectorPool.poll();
      if (selector == null) {
         selector = Selector.open();
      }

      return selector;
   }

   void release(Selector selector) {
      if (selector == null) {
         throw new IllegalArgumentException("Selector is null");
      } else {
         Set keys = selector.keys();
         Iterator var3 = keys.iterator();

         while(var3.hasNext()) {
            SelectionKey key = (SelectionKey)var3.next();
            key.cancel();
         }

         try {
            selector.selectNow();
         } catch (IOException var6) {
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
               SocketLogger.logDebugThrowable("failed to selectNow on selector " + selector + " with: ", var6);
            }
         }

         if (!this.selectorPool.offer(selector)) {
            try {
               selector.close();
            } catch (IOException var5) {
               if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
                  SocketLogger.logDebugThrowable("failed to close selector " + selector + " with: ", var5);
               }
            }

         }
      }
   }

   public Socket newSocket(InetAddress address, int port, InetAddress localAddr, int localPort, int connectTimeoutMillis) throws IOException {
      if (connectTimeoutMillis < 0) {
         throw new IllegalArgumentException("newSocket: timeout can't be negative");
      } else {
         SocketChannel sc = SocketChannel.open();
         if (localAddr != null) {
            sc.socket().bind(new InetSocketAddress(localAddr, localPort));
         }

         Socket sock = sc.socket();
         initSocket(sock);
         sc.configureBlocking(false);
         boolean connected = sc.connect(new InetSocketAddress(address, port));
         if (!connected) {
            Selector selector = this.findOrCreateSelector();

            assert sc.keyFor(selector) == null;

            sc.register(selector, 8);
            boolean closeNeeded = false;

            try {
               if (selector.select((long)connectTimeoutMillis) == 0) {
                  closeNeeded = true;
                  throw new SocketTimeoutException("newSocket: Couldn't connect to (" + address + ", " + port + ") even after " + connectTimeoutMillis + " millisecs");
               }
            } finally {
               selector.selectedKeys().clear();
               this.release(selector);
               if (closeNeeded) {
                  sc.close();
               }

            }
         }

         if (sc.isConnectionPending()) {
            sc.finishConnect();
         }

         return this.createWeblogicSocket(sock);
      }
   }

   public Socket newSDPSocket(InetAddress address, int port, InetAddress localAddr, int localPort, int connectTimeoutMillis) throws IOException {
      Socket sock = SDPSocketUtils.createSDPSocket();
      sock = this.initSocket(sock, address, port, localAddr, localPort, connectTimeoutMillis);
      return this.createWeblogicSocket(sock);
   }

   public WeblogicSocket newWeblogicSocket(Socket sock) throws IOException {
      initSocket(sock);
      return SocketEnvironment.getSocketEnvironment().serverThrottleEnabled() ? this.createWeblogicSocketImpl(sock) : this.createWeblogicSocket(sock);
   }

   private WeblogicSocket createWeblogicSocketImpl(Socket sock) {
      final NetworkInterfaceInfo nwInfo = NetworkInterfaceInfo.getNetworkInterfaceInfo(sock.getLocalAddress());
      return new WeblogicSocketImpl(sock) {
         NIOInputStream nioIn;
         NIOOutputStream nioOut;

         public InputStream getInputStream() throws IOException {
            if (this.nioIn == null) {
               this.nioIn = new NIOInputStream(NIOSocketMuxer.this, this.getSocket().getChannel(), nwInfo);
            }

            return this.nioIn;
         }

         public OutputStream getOutputStream() throws IOException {
            if (this.nioOut == null) {
               this.nioOut = new NIOOutputStream(NIOSocketMuxer.this, this.getSocket().getChannel(), nwInfo);
            }

            return this.nioOut;
         }
      };
   }

   private WeblogicSocket createWeblogicSocket(Socket sock) {
      final NetworkInterfaceInfo nwInfo = NetworkInterfaceInfo.getNetworkInterfaceInfo(sock.getLocalAddress());
      return new WeblogicSocket(sock) {
         NIOInputStream nioIn;
         NIOOutputStream nioOut;

         public InputStream getInputStream() throws IOException {
            if (this.nioIn == null) {
               this.nioIn = new NIOInputStream(NIOSocketMuxer.this, this.getSocket().getChannel(), nwInfo);
            }

            return this.nioIn;
         }

         public OutputStream getOutputStream() throws IOException {
            if (this.nioOut == null) {
               this.nioOut = new NIOOutputStream(NIOSocketMuxer.this, this.getSocket().getChannel(), nwInfo);
            }

            return this.nioOut;
         }
      };
   }

   public ServerSocket newServerSocket(InetAddress listenAddress, int port, int backlog) throws IOException {
      ServerSocketChannel ssc = ServerSocketChannel.open();

      try {
         ssc.setOption(StandardSocketOptions.SO_REUSEADDR, true);
      } catch (IOException var6) {
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
            SocketLogger.logDebugThrowable("Error setting SO_REUSEADDR option to ServerSocket listening on " + listenAddress + ":" + port, var6);
         }
      }

      if (listenAddress == null) {
         ssc.socket().bind(new InetSocketAddress(port), backlog);
      } else {
         ssc.socket().bind(new InetSocketAddress(listenAddress, port), backlog);
      }

      ssc.configureBlocking(true);
      return new WeblogicServerSocket(ssc.socket());
   }

   public void processSockets() {
      int id;
      synchronized(this) {
         id = this.nextId++;
      }

      if (id > this.SELECTORS) {
         throw new IllegalStateException("index > SELECTORS | " + id + " > " + this.SELECTORS);
      } else {
         ArrayList al = new ArrayList(0);

         while(true) {
            try {
               this.selectFrom(id, al);

               for(int i = al.size() - 1; i >= 0; --i) {
                  this.process((SelectionKey)al.remove(i));
               }
            } catch (ThreadDeath var5) {
               throw var5;
            } catch (Throwable var6) {
               SocketLogger.logUncaughtThrowable(var6);
            }

            this.takeABreak();
         }
      }
   }

   void takeABreak() {
      try {
         if (DELAYPOLLWAKEUP > 0L) {
            Thread.sleep(DELAYPOLLWAKEUP);
         }
      } catch (InterruptedException var2) {
         Thread.interrupted();
      }

   }

   void process(SelectionKey key) {
      NIOSocketInfo info = (NIOSocketInfo)key.attachment();
      if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
         SocketLogger.logDebug("clear interest ops for: sockInfo=" + info);
      }

      MuxableSocket ms = info.getMuxableSocket();
      if (this.completeIO(ms, info)) {
         try {
            int readyOps = key.readyOps();
            if ((readyOps & 1) != 0) {
               this.readReadySocket(ms, info, 0L);
            }

            if ((readyOps & 4) != 0) {
               this.writeReadySocket(info);
            }
         } catch (Throwable var5) {
            this.deliverHasException(ms, var5);
         }

      }
   }

   void selectFrom(int idx, ArrayList al) throws IOException {
      Selector selector = this.selectors[idx];
      ArrayList registerReadList = this.registerLists[idx][OP_READ_IDX];
      ArrayList registerWriteList = this.registerLists[idx][OP_WRITE_IDX];
      ArrayList readList = this.interestLists[idx][OP_READ_IDX];
      ArrayList writeList = this.interestLists[idx][OP_WRITE_IDX];
      AtomicInteger noticeId = this.willNotice[idx];

      do {
         int nSelected;
         do {
            noticeId.set(0);
            noticeId.get();
            if (registerReadList.size() > 0) {
               this.registerNewSockets(idx, registerReadList, 1);
            }

            if (registerWriteList.size() > 0) {
               this.registerNewSockets(idx, registerWriteList, 4);
            }

            if (readList.size() > 0) {
               this.setInterestOps(readList, 1);
            }

            if (writeList.size() > 0) {
               this.setInterestOps(writeList, 4);
            }

            nSelected = selector.select();
            if (Thread.interrupted() && Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
               SocketLogger.logDebug("NIOSocketMuxer [" + this + "] has been interrupted.");
            }
         } while(nSelected == 0);

         noticeId.set(1);
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
            SocketLogger.logDebug("select returns " + nSelected + " keys");
         }

         Set sk = selector.selectedKeys();
         Iterator var11 = sk.iterator();

         while(var11.hasNext()) {
            SelectionKey k = (SelectionKey)var11.next();

            try {
               k.interestOps(k.interestOps() & ~k.readyOps());
               al.add(k);
            } catch (CancelledKeyException var15) {
               NIOSocketInfo info = (NIOSocketInfo)k.attachment();
               this.deliverEndOfStream(info.getMuxableSocket());
            }
         }

         sk.clear();
      } while(al.size() == 0);

   }

   private void setInterestOps(ArrayList interestList, int op) {
      synchronized(interestList) {
         Iterator var4 = interestList.iterator();

         while(var4.hasNext()) {
            NIOSocketInfo nInfo = (NIOSocketInfo)var4.next();

            try {
               nInfo.getSelectionKey().interestOps(op);
            } catch (Exception var8) {
               if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
                  SocketLogger.logDebugThrowable("Couldn't set interest " + (op == 1 ? "read" : "write") + " for ms = " + nInfo.getMuxableSocket() + " with: ", var8);
               }

               this.deliverHasException(nInfo.getMuxableSocket(), var8);
            }
         }

         interestList.clear();
      }
   }

   private void registerNewSockets(int idx, ArrayList registerList, int selectionKeyOp) {
      NIOSocketInfo[] curRegisterList;
      int newSocketCount;
      synchronized(registerList) {
         newSocketCount = registerList.size();
         if (newSocketCount == 0) {
            return;
         }

         curRegisterList = (NIOSocketInfo[])registerList.toArray(new NIOSocketInfo[newSocketCount]);
         registerList.clear();
      }

      NIOSocketInfo[] var5 = curRegisterList;
      newSocketCount = curRegisterList.length;

      for(int var7 = 0; var7 < newSocketCount; ++var7) {
         NIOSocketInfo info = var5[var7];
         SelectionKey key = info.getSelectionKey();
         if (key == null) {
            SocketChannel sc = info.getSocketChannel();
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
               SocketLogger.logDebug("SocketChannel.register: sockInfo=" + info);
            }

            MuxableSocket ms;
            try {
               key = sc.register(this.selectors[idx], selectionKeyOp, info);
               info.setSelectionKey(key);
            } catch (CancelledKeyException var13) {
               ms = info.getMuxableSocket();
               this.deliverHasException(ms, var13);
            } catch (ClosedChannelException var14) {
               ms = info.getMuxableSocket();
               this.deliverHasException(ms, var14);
            }
         }
      }

   }

   protected int readFromSocket(MuxableSocket ms) throws IOException {
      InputStream is = ms.getSocketInputStream();
      if (is == null) {
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
            SocketLogger.logDebug("Socket has been closed and cleaned up: " + ms + " will return EOS from read");
         }

         return -1;
      } else {
         return ms.supportsScatteredRead() && is instanceof NIOInputStream && ((NIOConnection)is).supportsScatteredReads() ? (int)ms.read((NIOConnection)is) : super.readFromSocket(ms);
      }
   }

   private void writeReadySocket(NIOSocketInfo info) throws IOException {
      OutputStream os = info.getMuxableSocket().getSocket().getOutputStream();

      assert os instanceof NIOOutputStream;

      ((NIOOutputStream)os).onWritable();
   }

   protected void internalWrite(AsyncOutputStream s) {
      OutputStream os = s.getOutputStream();
      if (os == null) {
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
            SocketLogger.logDebug("Socket has been closed and cleaned up");
         }

      } else {
         Chunk c = s.getOutputBuffer();
         if (c != null && c.next != null && s.supportsGatheringWrite() && os instanceof NIOConnection && ((NIOConnection)os).supportsGatheredWrites()) {
            try {
               s.write((NIOConnection)os);
            } catch (IOException var7) {
               s.handleException(var7);
            }
         } else if (os instanceof NIOOutputStream) {
            NIOOutputStream nos = (NIOOutputStream)os;

            while((c = s.getOutputBuffer()) != null) {
               try {
                  nos.write(c.getWriteByteBuffer());
                  s.handleWrite(c);
               } catch (IOException var6) {
                  s.handleException(var6);
                  return;
               }
            }
         } else {
            super.internalWrite(s);
         }

      }
   }

   private static int op2Idx(int selectionKeyOp) {
      return selectionKeyOp == 1 ? 0 : 1;
   }

   static {
      initThreadCount(3, "weblogic.socket.Muxer", "");
      debugPPP = DebugLogger.getDebugLogger("DebugNIO");
      MUXERS = rdrThreads;
   }
}
