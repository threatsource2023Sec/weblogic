package weblogic.buzzmessagebus;

import com.oracle.buzzmessagebus.api.BuzzMessageToken;
import com.oracle.buzzmessagebus.api.BuzzSender;
import com.oracle.buzzmessagebus.api.BuzzSubprotocolReceiver;
import com.oracle.buzzmessagebus.api.BuzzTypes;
import com.oracle.buzzmessagebus.api.BuzzTypes.BuzzMessageState;
import com.oracle.buzzmessagebus.impl.internalapi.BuzzMessageTokenInternalApi;
import com.oracle.common.collections.ConcurrentLinkedStack;
import com.oracle.common.collections.Stack;
import com.oracle.common.io.BufferManagers;
import com.oracle.common.io.BufferSequence;
import com.oracle.common.io.BufferSequenceInputStream;
import com.oracle.common.io.BufferSequenceOutputStream;
import com.oracle.common.net.InetSocketAddress32;
import com.oracle.common.net.exabus.EndPoint;
import com.oracle.common.net.exabus.util.UrlEndPoint;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.Loggable;
import weblogic.socket.MuxableSocket;
import weblogic.socket.NIOOutputSink;
import weblogic.socket.WriteHandler;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.io.Chunk;
import weblogic.work.WorkManagerFactory;

class BuzzSubprotocolReceiverHttpWLSImpl implements BuzzSubprotocolReceiver {
   private static final String TIMER_MANAGER_NAME = "MuxerTimerManager";
   private static final int TIMER_MANAGER_INTERVAL = 5000;
   private static final int MS_CACHE_SIZE = Integer.getInteger("weblogic.buzzmessagebus.msCacheSize", 1024);
   private static final DebugLogger debugBuzzProtocol = DebugLogger.getDebugLogger("DebugBuzzProtocolHttp");
   private static final Set messageBusSockets = Collections.newSetFromMap(new ConcurrentHashMap());
   private static final ConcurrentMap endPointInfoMap = new ConcurrentHashMap();
   private static final EndPointInfo endPointInfoGlobal = new EndPointInfo();
   private static final Stack mbsStack = new ConcurrentLinkedStack();
   private BuzzHTTPFactory buzzHTTPFactory;
   private BuzzSender buzzSender;

   BuzzSubprotocolReceiverHttpWLSImpl(BuzzHTTPFactory bhf) {
      this.buzzHTTPFactory = bhf;
      TimerManager tm = TimerManagerFactory.getTimerManagerFactory().getTimerManager("MuxerTimerManager", WorkManagerFactory.getInstance().getSystem());
      tm.scheduleAtFixedRate(new TimerListenerImpl(), 0L, 5000L);
   }

   public void setBuzzSender(BuzzSender b) {
      this.buzzSender = b;
   }

   public void receipt(EndPoint endPoint, Object receiptCookie) {
      Loggable loggable = BuzzMessageBusHttpLogger.logHttpBuzzSubprotocolHandlerReceiptErrorLoggable(endPoint.getCanonicalName(), receiptCookie != null ? receiptCookie.toString() : "null");
      loggable.log();
      throw new RuntimeException(loggable.getMessage());
   }

   public void receive(byte frameType, BuzzMessageToken buzzMessageToken) {
      BufferSequence bufSeq = null;

      try {
         bufSeq = buzzMessageToken.getBufferSequence();
         switch (frameType) {
            case -1:
               BufferSequenceInputStream inputStream = getBufferSequenceInputStream(bufSeq);
               MessageBusSocket messageBusSocket = this.registerStartMessage(this.buzzSender, inputStream, buzzMessageToken);
               BuzzHTTP bHttp = messageBusSocket.buzzHTTP;
               boolean messageComplete = false;
               int headerLength;
               if (!buzzMessageToken.containsSubprotocolHeaderLength(bufSeq)) {
                  int len = true;

                  do {
                     headerLength = readIntoBuffer(bHttp, inputStream, -1);
                  } while(headerLength >= 0 && !(messageComplete = bHttp.isMessageComplete()));
               } else {
                  headerLength = inputStream.readInt();
                  if (headerLength <= 0) {
                     this.errorAndCleanup(BuzzMessageBusHttpLogger.logInvalidHttpHeaderLengthLoggable(buzzMessageToken.getEndPoint().getCanonicalName(), headerLength), bufSeq);
                  }

                  int len;
                  for(int bytesToRead = headerLength; bytesToRead > 0; bytesToRead -= len) {
                     len = readIntoBuffer(bHttp, inputStream, bytesToRead);
                     if (len < 0) {
                        break;
                     }
                  }

                  messageComplete = bHttp.isMessageComplete();
               }

               if (!messageComplete) {
                  this.errorAndCleanup(BuzzMessageBusHttpLogger.logIncompleteHttpHeaderLoggable(buzzMessageToken.getEndPoint().getCanonicalName()), bufSeq);
               }

               bHttp.dispatch();
               break;
            case 0:
               MessageBusSocket messageBusSocket = (MessageBusSocket)buzzMessageToken.getSubprotocolCookie();
               BuzzHTTP bHttp = messageBusSocket != null ? messageBusSocket.buzzHTTP : null;
               if (bHttp != null && bHttp.isDispatchOnRequestData()) {
                  synchronized(bHttp) {
                     this.registerData(messageBusSocket, buzzMessageToken.getMessageState(), getBufferSequenceInputStream(bufSeq));
                     if (!messageBusSocket.registerForRead) {
                        return;
                     }

                     InputStream inputStream = messageBusSocket.getInputStream();
                     readIntoBuffer(bHttp, inputStream, inputStream.available());
                     if (bHttp.isMessageComplete()) {
                        messageBusSocket.messageCompleted();
                        bHttp.dispatch();
                     } else {
                        messageBusSocket.messageInitiated();
                     }

                     messageBusSocket.registerForRead = false;
                  }
               } else {
                  this.registerData(messageBusSocket, buzzMessageToken.getMessageState(), getBufferSequenceInputStream(bufSeq));
               }
               break;
            default:
               this.errorAndCleanup(BuzzMessageBusHttpLogger.logInvalidFrameTypeForHttpBuzzSubprotocolHandlerLoggable(buzzMessageToken.getEndPoint().getCanonicalName(), frameType), bufSeq);
         }
      } catch (BuzzSubprotocolReceiverException var13) {
         throw var13;
      } catch (Throwable var14) {
         this.errorAndCleanup(BuzzMessageBusHttpLogger.logHttpBuzzSubprotocolHandlerReceiveErrorLoggable(buzzMessageToken.getEndPoint().getCanonicalName(), var14), bufSeq);
      }

   }

   public void flush() {
   }

   public void connect(EndPoint endPoint) {
      endPointInfoMap.putIfAbsent(endPoint, new EndPointInfo());
   }

   public void release(EndPoint endPoint) {
      endPointInfoMap.remove(endPoint);
   }

   public void backlog(boolean isExcessive, EndPoint endPoint) {
      EndPointInfo epInfo;
      if (isExcessive) {
         if (endPoint == null) {
            endPointInfoGlobal.backlog = true;
         } else if (!endPoint.equals(this.buzzSender.getBuzzAdmin().getLocalEndPoint())) {
            epInfo = (EndPointInfo)endPointInfoMap.get(endPoint);
            epInfo.backlog = true;
         }
      } else if (endPoint == null) {
         synchronized(endPointInfoGlobal.monitor) {
            endPointInfoGlobal.backlog = false;
            endPointInfoGlobal.monitor.notifyAll();
         }
      } else if (!endPoint.equals(this.buzzSender.getBuzzAdmin().getLocalEndPoint())) {
         epInfo = (EndPointInfo)endPointInfoMap.get(endPoint);
         if (epInfo != null) {
            synchronized(epInfo.monitor) {
               epInfo.backlog = false;
               epInfo.monitor.notifyAll();
            }
         }
      }

   }

   public void closeMyId(BuzzMessageToken buzzMessageToken) {
      MessageBusSocket messageBusSocket = (MessageBusSocket)buzzMessageToken.getSubprotocolCookie();
      if (messageBusSocket != null) {
         if (messageBusSocket.needCleanup) {
            messageBusSockets.remove(messageBusSocket);
         }

         try {
            messageBusSocket.close();
         } catch (Throwable var4) {
            if (debugBuzzProtocol.isDebugEnabled()) {
               debugBuzzProtocol.debug("Throwable in MessageInfo#closeId", var4);
            }
         }
      }

   }

   public void closeYourId(BuzzMessageToken buzzMessageToken) {
      Loggable loggable = BuzzMessageBusHttpLogger.logUnsupportedBuzzHttpCloseYourIdLoggable(buzzMessageToken.getEndPoint().getCanonicalName(), ((BuzzMessageTokenInternalApi)buzzMessageToken).getMessageId());
      loggable.log();
      throw new IllegalStateException(loggable.getMessage());
   }

   void cleanUp() {
      Iterator var1 = messageBusSockets.iterator();

      while(var1.hasNext()) {
         MessageBusSocket messageBusSocket = (MessageBusSocket)var1.next();

         try {
            messageBusSocket.close();
         } catch (Throwable var4) {
            if (debugBuzzProtocol.isDebugEnabled()) {
               debugBuzzProtocol.debug("Throwable in MessageInfo#gracefulShutdown", var4);
            }
         }
      }

      messageBusSockets.clear();
   }

   public byte getSubprotocolId() {
      return 0;
   }

   private MessageBusSocket registerStartMessage(BuzzSender buzzSender, BufferSequenceInputStream inputStream, BuzzMessageToken buzzMessageToken) throws IOException {
      MessageBusSocket messageBusSocket = (MessageBusSocket)mbsStack.pop();
      if (messageBusSocket == null) {
         messageBusSocket = new MessageBusSocket(buzzSender, inputStream, buzzMessageToken);
         Chunk head = Chunk.getChunk();
         BuzzHTTP bHttp = this.buzzHTTPFactory.create(head, messageBusSocket, messageBusSocket, false);
         messageBusSocket.setBuzzHTTP(bHttp);
      } else {
         messageBusSocket.init(buzzSender, inputStream, buzzMessageToken);
      }

      buzzMessageToken.setSubprotocolCookie(messageBusSocket);
      return messageBusSocket;
   }

   private void registerData(MessageBusSocket messageBusSocket, BuzzTypes.BuzzMessageState buzzMessageState, BufferSequenceInputStream inputStream) {
      if (messageBusSocket != null && !messageBusSocket.inputMessageEnd) {
         messageBusSocket.add(inputStream, buzzMessageState == BuzzMessageState.HALF_CLOSED);
      } else if (debugBuzzProtocol.isDebugEnabled()) {
         debugBuzzProtocol.debug("More that one DATA frame with end flag for a given ID");
      }

   }

   private void errorAndCleanup(Loggable loggable, BufferSequence bufSeq) {
      loggable.log();
      if (bufSeq != null) {
         bufSeq.dispose();
      }

      throw new BuzzSubprotocolReceiverException(loggable.getMessage());
   }

   private static BufferSequenceOutputStream newBufferSequenceOutputStream() {
      return new BufferSequenceOutputStream(BufferManagers.getNetworkDirectManager());
   }

   private static BufferSequenceInputStream getBufferSequenceInputStream(BufferSequence bufSeq) throws IOException {
      BufferSequenceInputStream inputStream = new BufferSequenceInputStream(bufSeq, true);
      if (inputStream.skip(12L) != 12L) {
         throw new IOException();
      } else {
         return inputStream;
      }
   }

   private static int readIntoBuffer(BuzzHTTP bHttp, InputStream inputStream, int maxBytesRead) throws IOException {
      byte[] buf = bHttp.getBuffer();
      int offset = bHttp.getBufferOffset();
      int dataLen = buf.length - offset;
      if (maxBytesRead >= 0 && dataLen > maxBytesRead) {
         dataLen = maxBytesRead;
      }

      int len = inputStream.read(buf, offset, dataLen);
      if (len > 0) {
         bHttp.incrementBufferOffset(len);
      }

      return len;
   }

   private static class TimerListenerImpl implements TimerListener {
      private TimerListenerImpl() {
      }

      public void timerExpired(Timer timer) {
         Iterator var2 = BuzzSubprotocolReceiverHttpWLSImpl.messageBusSockets.iterator();

         while(var2.hasNext()) {
            MessageBusSocket mbSocket = (MessageBusSocket)var2.next();
            long lastMessageReadingStartedTimeMillis = mbSocket.lastMessageReadingStartedTimeMillis;
            if (lastMessageReadingStartedTimeMillis != -1L) {
               BuzzHTTP bHttp = mbSocket.buzzHTTP;
               if (bHttp != null) {
                  long msgTimeout = (long)bHttp.getCompleteMessageTimeoutMillis();
                  if (msgTimeout > 0L && System.currentTimeMillis() - lastMessageReadingStartedTimeMillis > msgTimeout) {
                     Loggable loggable = BuzzMessageBusHttpLogger.logCompleteMessageTimeoutLoggable(mbSocket.endPoint.getCanonicalName(), mbSocket.toString(), msgTimeout / 1000L);
                     loggable.log();
                     String msg = loggable.getMessage();
                     bHttp.hasException(new IOException(msg));

                     try {
                        mbSocket.buzzSender.closeYourId(mbSocket.buzzMessageToken, new String[]{msg});
                     } catch (Exception var13) {
                        if (BuzzSubprotocolReceiverHttpWLSImpl.debugBuzzProtocol.isDebugEnabled()) {
                           BuzzSubprotocolReceiverHttpWLSImpl.debugBuzzProtocol.debug("Cannot close Your Id", var13);
                        }
                     }

                     try {
                        mbSocket.close();
                     } catch (Exception var12) {
                     }
                  }
               }
            }
         }

      }

      // $FF: synthetic method
      TimerListenerImpl(Object x0) {
         this();
      }
   }

   private static class BuzzSubprotocolReceiverException extends RuntimeException {
      BuzzSubprotocolReceiverException(String message) {
         super(message);
      }
   }

   private static class EndPointInfo {
      private volatile boolean backlog;
      private final Object monitor;

      private EndPointInfo() {
         this.backlog = false;
         this.monitor = new Object();
      }

      // $FF: synthetic method
      EndPointInfo(Object x0) {
         this();
      }
   }

   private static class InputStreamInfo {
      private BufferSequenceInputStream inputStream;
      private boolean end;

      private InputStreamInfo(BufferSequenceInputStream in, boolean e) {
         this.inputStream = in;
         this.end = e;
      }

      // $FF: synthetic method
      InputStreamInfo(BufferSequenceInputStream x0, boolean x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class MBOutputStream extends OutputStream implements NIOOutputSink {
      private static final long MAX_BUFFER_SIZE = 500000L;
      private EndPoint endPoint;
      private BuzzSender buzzSender;
      private BuzzMessageToken buzzMessageToken;
      private MessageBusSocket mbSocket;
      private EndPointInfo epInfo;
      private boolean blocking;
      private final AtomicReference writeHandlerRef;
      private volatile boolean closed;
      private volatile BufferSequenceOutputStream out;
      private volatile long bufferSize;

      private MBOutputStream() {
         this.blocking = true;
         this.writeHandlerRef = new AtomicReference();
         this.closed = false;
         this.bufferSize = 0L;
      }

      private void init(MessageBusSocket mbSocket) {
         this.buzzSender = mbSocket.buzzSender;
         this.endPoint = mbSocket.endPoint;
         this.buzzMessageToken = mbSocket.buzzMessageToken;
         this.mbSocket = mbSocket;
         this.epInfo = (EndPointInfo)BuzzSubprotocolReceiverHttpWLSImpl.endPointInfoMap.get(this.endPoint);
         if (this.epInfo == null) {
            if (BuzzSubprotocolReceiverHttpWLSImpl.debugBuzzProtocol.isDebugEnabled()) {
               BuzzSubprotocolReceiverHttpWLSImpl.debugBuzzProtocol.debug("EndPointInfo is null");
            }

            throw new IllegalStateException();
         }
      }

      public void write(int b) throws IOException {
         if (!this.mbSocket.closed && !this.closed) {
            if (this.out == null) {
               this.out = BuzzSubprotocolReceiverHttpWLSImpl.newBufferSequenceOutputStream();
            }

            this.out.write(b);
            this.bufferSize += 4L;
            if (this.bufferSize >= 500000L) {
               this.send(false);
            }

         } else {
            throw new IOException();
         }
      }

      public void write(byte[] b, int off, int len) throws IOException {
         if (!this.closed && !this.mbSocket.closed) {
            if (this.out == null) {
               this.out = BuzzSubprotocolReceiverHttpWLSImpl.newBufferSequenceOutputStream();
            }

            this.out.write(b, off, len);
            this.bufferSize += (long)len;
            if (this.bufferSize >= 500000L) {
               this.send(false);
            }

         } else {
            throw new IOException();
         }
      }

      public void flush() throws IOException {
         this.send(true);
         super.flush();
      }

      private void send(boolean flush) throws IOException {
         if (!this.closed && !this.mbSocket.closed) {
            if (this.out != null) {
               boolean isCommit = this.isCommitCalled();

               try {
                  short flags = isCommit ? 1 : 0;
                  BufferSequence bSeq = this.out.toBufferSequence();
                  this.buzzSender.send(this.buzzMessageToken, (byte)-3, (short)flags, bSeq);
                  this.out = null;
                  this.bufferSize = 0L;
               } catch (IOException var11) {
                  throw var11;
               } catch (Throwable var12) {
                  if (BuzzSubprotocolReceiverHttpWLSImpl.debugBuzzProtocol.isDebugEnabled()) {
                     BuzzSubprotocolReceiverHttpWLSImpl.debugBuzzProtocol.debug("Throwable while calling flush", var12);
                  }
               }

               if (BuzzSubprotocolReceiverHttpWLSImpl.endPointInfoGlobal.backlog) {
                  synchronized(BuzzSubprotocolReceiverHttpWLSImpl.endPointInfoGlobal.monitor) {
                     if (BuzzSubprotocolReceiverHttpWLSImpl.endPointInfoGlobal.backlog) {
                        try {
                           BuzzSubprotocolReceiverHttpWLSImpl.endPointInfoGlobal.monitor.wait();
                        } catch (InterruptedException var9) {
                           Thread.currentThread().interrupt();
                           throw new IOException(var9);
                        }
                     }
                  }
               } else if (this.epInfo.backlog) {
                  synchronized(this.epInfo.monitor) {
                     if (this.epInfo.backlog) {
                        try {
                           this.epInfo.monitor.wait();
                        } catch (InterruptedException var7) {
                           Thread.currentThread().interrupt();
                           throw new IOException(var7);
                        }
                     }
                  }
               }
            }

            if (flush) {
               this.buzzSender.flush();
            }

         } else {
            throw new IOException();
         }
      }

      public void close() throws IOException {
         if (!this.closed && !this.mbSocket.closed) {
            try {
               this.flush();
            } finally {
               this.closed = true;
               super.close();
               this.resetStream();
            }

         }
      }

      public boolean canWrite() {
         return this.blocking || this.mbSocket.closed || !BuzzSubprotocolReceiverHttpWLSImpl.endPointInfoGlobal.backlog && !this.epInfo.backlog;
      }

      public void notifyWritePossible(WriteHandler writeHandler) {
         if (this.canWrite()) {
            this.notifyWriteHandler(writeHandler);
         } else if (!this.writeHandlerRef.compareAndSet((Object)null, writeHandler)) {
            throw new IllegalStateException("WriteHandler is already set");
         } else {
            if (this.canWrite() && this.writeHandlerRef.compareAndSet(writeHandler, (Object)null)) {
               this.notifyWriteHandler(writeHandler);
            }

         }
      }

      public boolean isBlocking() {
         return this.blocking;
      }

      public void configureBlocking() throws InterruptedException {
         try {
            this.configureBlocking(-1L, TimeUnit.MILLISECONDS);
         } catch (TimeoutException var2) {
         }

      }

      public void configureBlocking(long timeout, TimeUnit timeUnit) throws TimeoutException, InterruptedException {
         if (!this.blocking) {
            if (!this.canWrite()) {
               final CountDownLatch latch = new CountDownLatch(1);
               this.notifyWritePossible(new WriteHandler() {
                  public void onWritable() throws Exception {
                     latch.countDown();
                  }

                  public void onError(Throwable t) {
                     latch.countDown();
                  }
               });
               if (timeout >= 0L) {
                  if (!latch.await(timeout, timeUnit)) {
                     throw new TimeoutException();
                  }
               } else {
                  latch.await();
               }

               this.blocking = true;
            }
         }
      }

      public void configureNonBlocking(MuxableSocket ms) {
         if (this.blocking) {
            this.blocking = false;
         }
      }

      private boolean isCommitCalled() {
         boolean result = true;
         BuzzHTTP bHttp = this.mbSocket.buzzHTTP;
         if (bHttp != null) {
            result = bHttp.isServletResponseCommitCalled();
         }

         return result;
      }

      private void notifyWriteHandler(WriteHandler writeHandler) {
         try {
            writeHandler.onWritable();
         } catch (Exception var3) {
            writeHandler.onError(var3);
         }

      }

      private void resetStream() {
         this.blocking = true;
         WriteHandler writeHandler = (WriteHandler)this.writeHandlerRef.get();
         if (writeHandler != null) {
            writeHandler.onError(new IOException("The stream is closed"));
         }

      }

      // $FF: synthetic method
      MBOutputStream(Object x0) {
         this();
      }
   }

   private static class MBInputStream extends InputStream {
      private MessageBusSocket mbSocket;
      private volatile boolean closed;
      private volatile BufferSequenceInputStream inputStream;

      private MBInputStream() {
         this.closed = false;
         this.inputStream = null;
      }

      private void init(MessageBusSocket mbSocket) {
         this.mbSocket = mbSocket;
         this.inputStream = mbSocket.getCurrentInputStream();
      }

      public int read() throws IOException {
         BuzzHTTP bHttp = null;
         if (this.mbSocket != null && (bHttp = this.mbSocket.buzzHTTP) != null && bHttp.isDispatchOnRequestData()) {
            synchronized(bHttp) {
               this.mbSocket.registerForRead = false;
               return this.internalRead();
            }
         } else {
            return this.internalRead();
         }
      }

      private int internalRead() throws IOException {
         if (!this.mbSocket.closed && !this.closed) {
            int b = -1;
            if (this.inputStream == null && !this.mbSocket.isInputEnd()) {
               this.processNextBufferSequenceInputStream();
            }

            if (this.inputStream != null) {
               b = this.inputStream.read();
               if (b == -1 && !this.mbSocket.isInputEnd()) {
                  this.processNextBufferSequenceInputStream();
                  b = this.inputStream.read();
               }

               if (this.inputStream.available() == 0) {
                  this.inputStream.close();
                  this.inputStream = null;
               }
            }

            return b;
         } else {
            throw new IOException();
         }
      }

      public int read(byte[] b, int off, int len) throws IOException {
         BuzzHTTP bHttp = null;
         if (this.mbSocket != null && (bHttp = this.mbSocket.buzzHTTP) != null && bHttp.isDispatchOnRequestData()) {
            synchronized(bHttp) {
               this.mbSocket.registerForRead = false;
               return this.internalRead(b, off, len);
            }
         } else {
            return this.internalRead(b, off, len);
         }
      }

      private int internalRead(byte[] b, int off, int len) throws IOException {
         if (!this.mbSocket.closed && !this.closed) {
            int l = -1;
            if (this.inputStream == null && !this.mbSocket.isInputEnd()) {
               this.processNextBufferSequenceInputStream();
            }

            if (this.inputStream != null) {
               for(l = this.inputStream.read(b, off, len); l == -1 && !this.mbSocket.isInputEnd(); l = this.inputStream.read(b, off, len)) {
                  this.processNextBufferSequenceInputStream();
               }

               if (this.inputStream.available() == 0) {
                  this.inputStream.close();
                  this.inputStream = null;
               }
            }

            return l;
         } else {
            throw new IOException();
         }
      }

      public int available() throws IOException {
         BuzzHTTP bHttp = null;
         if (this.mbSocket != null && (bHttp = this.mbSocket.buzzHTTP) != null && bHttp.isDispatchOnRequestData()) {
            synchronized(bHttp) {
               return this.internalAvailable();
            }
         } else {
            return this.internalAvailable();
         }
      }

      private int internalAvailable() throws IOException {
         int aLen = this.inputStream != null ? this.inputStream.available() : 0;
         if (aLen == 0 && !this.mbSocket.isInputEnd() && this.mbSocket.hasMoreInputStream()) {
            this.processNextBufferSequenceInputStream();
            if (this.inputStream != null) {
               aLen = this.inputStream.available();
            }
         }

         return aLen;
      }

      public void close() throws IOException {
         if (!this.closed && !this.mbSocket.closed) {
            this.closed = true;

            try {
               if (this.inputStream != null) {
                  this.inputStream.close();
               }
            } finally {
               this.mbSocket.closeInputStreams();
               super.close();
            }

         }
      }

      private void processNextBufferSequenceInputStream() throws IOException {
         if (this.inputStream != null) {
            this.inputStream.close();
         }

         try {
            this.inputStream = this.mbSocket.nextInputStream();
         } catch (InterruptedException var2) {
            Thread.currentThread().interrupt();
            throw new IOException(var2);
         }
      }

      private void resetStream() {
         this.closed = false;
         this.inputStream = null;
      }

      // $FF: synthetic method
      MBInputStream(Object x0) {
         this();
      }
   }

   private static class MessageBusSocket extends Socket implements BuzzHTTPSupport {
      private BuzzSender buzzSender;
      private EndPoint endPoint;
      private BuzzMessageToken buzzMessageToken;
      private BuzzHTTP buzzHTTP;
      private volatile boolean closed;
      private volatile boolean inputMessageEnd;
      private volatile InputStreamInfo inputStreamInfo;
      private volatile BlockingQueue queue;
      private volatile boolean registerForRead;
      private volatile boolean needCleanup;
      private SocketAddress remoteSocketAddress;
      private final MBInputStream mbIn;
      private final MBOutputStream mbOut;
      private long lastMessageReadingStartedTimeMillis;

      private MessageBusSocket(BuzzSender buzzSender, BufferSequenceInputStream bSeqInputStream, BuzzMessageToken buzzMessageToken) {
         this.inputMessageEnd = false;
         this.registerForRead = false;
         this.needCleanup = false;
         this.remoteSocketAddress = null;
         this.mbIn = new MBInputStream();
         this.mbOut = new MBOutputStream();
         this.lastMessageReadingStartedTimeMillis = -1L;
         this.init(buzzSender, bSeqInputStream, buzzMessageToken);
      }

      private void init(BuzzSender buzzSender, BufferSequenceInputStream bSeqInputStream, BuzzMessageToken buzzMessageToken) {
         this.buzzSender = buzzSender;
         this.endPoint = buzzMessageToken.getEndPoint();
         this.buzzMessageToken = buzzMessageToken;
         this.closed = false;
         this.registerForRead = false;
         this.needCleanup = false;
         this.inputMessageEnd = buzzMessageToken.getMessageState() == BuzzMessageState.HALF_CLOSED;
         this.inputStreamInfo = new InputStreamInfo(bSeqInputStream, this.inputMessageEnd);
         this.lastMessageReadingStartedTimeMillis = -1L;
         if (this.endPoint instanceof UrlEndPoint) {
            this.remoteSocketAddress = ((UrlEndPoint)this.endPoint).getAddress();
         } else {
            try {
               URI uri = new URI(this.endPoint.getCanonicalName());
               this.remoteSocketAddress = new InetSocketAddress(uri.getHost(), uri.getPort());
            } catch (URISyntaxException var5) {
               if (BuzzSubprotocolReceiverHttpWLSImpl.debugBuzzProtocol.isDebugEnabled()) {
                  BuzzSubprotocolReceiverHttpWLSImpl.debugBuzzProtocol.debug("Throwable in MessageBusSocket constructor", var5);
               }
            }
         }

         if (BuzzSubprotocolReceiverHttpWLSImpl.debugBuzzProtocol.isDebugEnabled()) {
            BuzzSubprotocolReceiverHttpWLSImpl.debugBuzzProtocol.debug("SocketAddress of remote endpoint is " + this.remoteSocketAddress);
         }

         this.mbIn.init(this);
         this.mbOut.init(this);
      }

      public InputStream getInputStream() throws IOException {
         return this.mbIn;
      }

      public OutputStream getOutputStream() throws IOException {
         return this.mbOut;
      }

      public int getPort() {
         if (!this.isConnected()) {
            return 0;
         } else {
            return this.remoteSocketAddress instanceof InetSocketAddress ? ((InetSocketAddress)this.remoteSocketAddress).getPort() : ((InetSocketAddress32)this.remoteSocketAddress).getPort();
         }
      }

      public InetAddress getInetAddress() {
         if (!this.isConnected()) {
            return null;
         } else {
            return this.remoteSocketAddress instanceof InetSocketAddress ? ((InetSocketAddress)this.remoteSocketAddress).getAddress() : ((InetSocketAddress32)this.remoteSocketAddress).getAddress();
         }
      }

      public boolean isConnected() {
         return this.remoteSocketAddress != null;
      }

      public void close() throws IOException {
         try {
            this.mbOut.close();
            this.mbIn.close();
         } finally {
            this.remoteSocketAddress = null;

            try {
               if (this.needCleanup) {
                  BuzzSubprotocolReceiverHttpWLSImpl.messageBusSockets.remove(this);
               }
            } finally {
               super.close();
            }

         }

      }

      public BuzzHTTP register(BuzzHTTP bhttp) {
         BuzzHTTP oldBuzzHTTP = this.buzzHTTP;
         this.buzzHTTP = bhttp;
         if (this.buzzHTTP != null && this.buzzHTTP.isDispatchOnRequestData()) {
            this.needCleanup = true;
            BuzzSubprotocolReceiverHttpWLSImpl.messageBusSockets.add(this);
         }

         return oldBuzzHTTP;
      }

      public void registerForRead() {
         this.registerForRead = true;
      }

      public void messageCompleted() {
         this.lastMessageReadingStartedTimeMillis = -1L;
      }

      public void messageInitiated() {
         if (this.lastMessageReadingStartedTimeMillis == -1L) {
            this.lastMessageReadingStartedTimeMillis = System.currentTimeMillis();
         }

      }

      public void requeue() {
         try {
            this.buzzSender = null;
            this.endPoint = null;
            this.inputStreamInfo = null;
            if (this.queue != null) {
               this.queue.clear();
            }

            this.mbOut.resetStream();
            this.mbIn.resetStream();
         } finally {
            this.remoteSocketAddress = null;
            if (this.needCleanup) {
               BuzzSubprotocolReceiverHttpWLSImpl.messageBusSockets.remove(this);
            }

         }

         if (BuzzSubprotocolReceiverHttpWLSImpl.mbsStack.size() <= BuzzSubprotocolReceiverHttpWLSImpl.MS_CACHE_SIZE) {
            BuzzSubprotocolReceiverHttpWLSImpl.mbsStack.push(this);
         }

      }

      private void setBuzzHTTP(BuzzHTTP bh) {
         this.buzzHTTP = bh;
      }

      private void add(BufferSequenceInputStream input, boolean e) {
         this.inputMessageEnd = this.inputMessageEnd || e;
         this.getQueue().add(new InputStreamInfo(input, e));
      }

      private BufferSequenceInputStream nextInputStream() throws InterruptedException, SocketException, SocketTimeoutException {
         int timeout = this.getSoTimeout();
         this.inputStreamInfo = (InputStreamInfo)this.getQueue().poll((long)timeout, TimeUnit.MILLISECONDS);
         if (this.inputStreamInfo == null) {
            throw new SocketTimeoutException("Read time out after " + timeout + " millis");
         } else {
            return this.inputStreamInfo.inputStream;
         }
      }

      private boolean hasMoreInputStream() {
         return this.queue != null && this.queue.size() > 0;
      }

      private BufferSequenceInputStream getCurrentInputStream() {
         return this.inputStreamInfo.inputStream;
      }

      private boolean isInputEnd() {
         return this.inputStreamInfo != null && this.inputStreamInfo.end;
      }

      private synchronized BlockingQueue getQueue() {
         if (this.queue == null) {
            this.queue = new LinkedBlockingQueue();
         }

         return this.queue;
      }

      private void closeInputStreams() throws IOException {
         RuntimeException rtex = null;
         if (this.inputStreamInfo != null) {
            try {
               this.inputStreamInfo.inputStream.close();
            } catch (RuntimeException var5) {
               rtex = var5;
            }
         }

         InputStreamInfo isInfo = null;
         if (this.queue != null) {
            while((isInfo = (InputStreamInfo)this.queue.poll()) != null) {
               try {
                  isInfo.inputStream.close();
               } catch (RuntimeException var4) {
                  rtex = var4;
               }
            }
         }

         if (rtex != null) {
            throw rtex;
         }
      }

      // $FF: synthetic method
      MessageBusSocket(BuzzSender x0, BufferSequenceInputStream x1, BuzzMessageToken x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
