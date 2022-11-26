package org.glassfish.tyrus.container.jdk.client;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.spi.CompletionHandler;

class SslFilter extends Filter {
   private static final ByteBuffer emptyBuffer = ByteBuffer.allocate(0);
   private final ByteBuffer applicationInputBuffer;
   private final ByteBuffer networkOutputBuffer;
   private final SSLEngine sslEngine;
   private final HostnameVerifier customHostnameVerifier;
   private final String serverHost;
   private final WriteQueue writeQueue = new WriteQueue();
   private volatile State state;
   private Runnable pendingApplicationWrite;

   SslFilter(Filter downstreamFilter, org.glassfish.tyrus.client.SslEngineConfigurator sslEngineConfigurator, String serverHost) {
      super(downstreamFilter);
      this.state = SslFilter.State.NOT_STARTED;
      this.pendingApplicationWrite = null;
      this.serverHost = serverHost;
      this.sslEngine = sslEngineConfigurator.createSSLEngine(serverHost);
      this.customHostnameVerifier = sslEngineConfigurator.getHostnameVerifier();
      if (sslEngineConfigurator.isHostVerificationEnabled() && sslEngineConfigurator.getHostnameVerifier() == null) {
         SSLParameters sslParameters = this.sslEngine.getSSLParameters();
         sslParameters.setEndpointIdentificationAlgorithm("HTTPS");
         this.sslEngine.setSSLParameters(sslParameters);
      }

      this.applicationInputBuffer = ByteBuffer.allocate(this.sslEngine.getSession().getApplicationBufferSize());
      this.networkOutputBuffer = ByteBuffer.allocate(this.sslEngine.getSession().getPacketBufferSize());
   }

   /** @deprecated */
   SslFilter(Filter downstreamFilter, SslEngineConfigurator sslEngineConfigurator) {
      super(downstreamFilter);
      this.state = SslFilter.State.NOT_STARTED;
      this.pendingApplicationWrite = null;
      this.sslEngine = sslEngineConfigurator.createSSLEngine();
      this.applicationInputBuffer = ByteBuffer.allocate(this.sslEngine.getSession().getApplicationBufferSize());
      this.networkOutputBuffer = ByteBuffer.allocate(this.sslEngine.getSession().getPacketBufferSize());
      this.customHostnameVerifier = null;
      this.serverHost = null;
   }

   synchronized void write(ByteBuffer applicationData, CompletionHandler completionHandler) {
      switch (this.state) {
         case NOT_STARTED:
            this.writeQueue.write(applicationData, completionHandler);
            return;
         case HANDSHAKING:
            completionHandler.failed(new IllegalStateException("Cannot write until SSL handshake has been completed"));
            break;
         case REHANDSHAKING:
            this.storePendingApplicationWrite(applicationData, completionHandler);
            break;
         case DATA:
            this.handleWrite(applicationData, completionHandler);
            break;
         case CLOSED:
            completionHandler.failed(new IllegalStateException("SSL session has been closed"));
      }

   }

   private void handleWrite(final ByteBuffer applicationData, final CompletionHandler completionHandler) {
      try {
         this.networkOutputBuffer.clear();
         SSLEngineResult result = this.sslEngine.wrap(applicationData, this.networkOutputBuffer);
         switch (result.getStatus()) {
            case BUFFER_OVERFLOW:
               throw new IllegalStateException("SSL packet does not fit into the network buffer: " + this.networkOutputBuffer + "\n" + this.getDebugState());
            case BUFFER_UNDERFLOW:
               throw new IllegalStateException("SSL engine underflow with the following application input: " + applicationData + "\n" + this.getDebugState());
            case CLOSED:
               this.state = SslFilter.State.CLOSED;
               break;
            case OK:
               if (result.getHandshakeStatus() != HandshakeStatus.NOT_HANDSHAKING) {
                  this.state = SslFilter.State.REHANDSHAKING;
               }

               this.networkOutputBuffer.flip();
               if (this.networkOutputBuffer.hasRemaining()) {
                  this.writeQueue.write(this.networkOutputBuffer, new CompletionHandler() {
                     public void completed(ByteBuffer result) {
                        SslFilter.this.handlePostWrite(applicationData, completionHandler);
                     }

                     public void failed(Throwable throwable) {
                        completionHandler.failed(throwable);
                     }
                  });
               } else {
                  this.handlePostWrite(applicationData, completionHandler);
               }
         }
      } catch (SSLException var4) {
         this.handleSslError(var4);
      }

   }

   private synchronized void handlePostWrite(ByteBuffer applicationData, CompletionHandler completionHandler) {
      if (this.state == SslFilter.State.REHANDSHAKING) {
         if (applicationData.hasRemaining()) {
            this.storePendingApplicationWrite(applicationData, completionHandler);
            this.doHandshakeStep(emptyBuffer);
         }
      } else if (applicationData.hasRemaining()) {
         this.handleWrite(applicationData, completionHandler);
      } else {
         completionHandler.completed(applicationData);
      }

   }

   private void storePendingApplicationWrite(final ByteBuffer applicationData, final CompletionHandler completionHandler) {
      if (this.pendingApplicationWrite != null) {
         throw new IllegalStateException("Only one write operation can be in progress\n" + this.getDebugState());
      } else {
         this.pendingApplicationWrite = new Runnable() {
            public void run() {
               SslFilter.this.write(applicationData, completionHandler);
            }
         };
      }
   }

   synchronized void close() {
      if (this.state == SslFilter.State.NOT_STARTED) {
         this.downstreamFilter.close();
      } else {
         this.sslEngine.closeOutbound();

         try {
            LazyBuffer lazyBuffer = new LazyBuffer();

            ByteBuffer buffer;
            while(this.sslEngine.getHandshakeStatus() == HandshakeStatus.NEED_WRAP) {
               buffer = lazyBuffer.get();
               SSLEngineResult result = this.sslEngine.wrap(emptyBuffer, buffer);
               switch (result.getStatus()) {
                  case BUFFER_OVERFLOW:
                     lazyBuffer.resize();
                     break;
                  case BUFFER_UNDERFLOW:
                     throw new IllegalStateException("SSL engine underflow while close operation \n" + this.getDebugState());
               }
            }

            if (lazyBuffer.isAllocated()) {
               buffer = lazyBuffer.get();
               buffer.flip();
               this.writeQueue.write(buffer, new CompletionHandler() {
                  public void completed(ByteBuffer result) {
                     SslFilter.this.downstreamFilter.close();
                  }

                  public void failed(Throwable throwable) {
                     SslFilter.this.downstreamFilter.close();
                  }
               });
            } else {
               this.downstreamFilter.close();
            }
         } catch (Exception var4) {
            this.handleSslError(var4);
         }

      }
   }

   boolean processRead(ByteBuffer networkData) {
      boolean readMore = true;

      while(networkData.hasRemaining() && readMore) {
         switch (this.state) {
            case NOT_STARTED:
               return true;
            case HANDSHAKING:
            case REHANDSHAKING:
               readMore = this.doHandshakeStep(networkData);
               break;
            case DATA:
               readMore = this.handleRead(networkData);
               break;
            case CLOSED:
               networkData.clear();
               readMore = false;
         }
      }

      return false;
   }

   private boolean handleRead(ByteBuffer networkData) {
      try {
         this.applicationInputBuffer.clear();
         SSLEngineResult result = this.sslEngine.unwrap(networkData, this.applicationInputBuffer);
         switch (result.getStatus()) {
            case BUFFER_OVERFLOW:
               throw new IllegalStateException("Contents of a SSL packet did not fit into buffer: " + this.applicationInputBuffer + "\n" + this.getDebugState());
            case BUFFER_UNDERFLOW:
               return false;
            case CLOSED:
            case OK:
               if (result.bytesProduced() > 0) {
                  this.applicationInputBuffer.flip();
                  this.upstreamFilter.onRead(this.applicationInputBuffer);
                  this.applicationInputBuffer.compact();
               }

               if (this.sslEngine.isInboundDone()) {
                  return false;
               }

               if (result.getHandshakeStatus() != HandshakeStatus.NOT_HANDSHAKING && !this.sslEngine.isOutboundDone()) {
                  this.state = SslFilter.State.REHANDSHAKING;
                  return this.doHandshakeStep(networkData);
               }
         }
      } catch (SSLException var3) {
         this.handleSslError(var3);
      }

      return true;
   }

   private boolean doHandshakeStep(ByteBuffer networkData) {
      LazyBuffer inputBuffer = new LazyBuffer();
      boolean handshakeFinished = false;
      synchronized(this) {
         if (HandshakeStatus.NOT_HANDSHAKING.equals(this.sslEngine.getHandshakeStatus())) {
            return true;
         }

         try {
            LazyBuffer outputBuffer = new LazyBuffer();
            boolean stepFinished = false;

            while(true) {
               if (stepFinished) {
                  if (outputBuffer.isAllocated()) {
                     ByteBuffer buffer = outputBuffer.get();
                     buffer.flip();
                     this.writeQueue.write(buffer, (CompletionHandler)null);
                  }
                  break;
               }

               SSLEngineResult.HandshakeStatus hs = this.sslEngine.getHandshakeStatus();
               Runnable delegatedTask;
               switch (hs) {
                  case NOT_HANDSHAKING:
                     throw new IllegalStateException("Trying to handshake, but SSL engine not in HANDSHAKING state.SSL filter state: \n" + this.getDebugState());
                  case FINISHED:
                     stepFinished = true;
                     handshakeFinished = true;
                     break;
                  case NEED_WRAP:
                     ByteBuffer byteBuffer = outputBuffer.get();
                     SSLEngineResult result = this.sslEngine.wrap(emptyBuffer, byteBuffer);
                     if (result.getHandshakeStatus() == HandshakeStatus.FINISHED) {
                        stepFinished = true;
                        handshakeFinished = true;
                     }

                     switch (result.getStatus()) {
                        case BUFFER_OVERFLOW:
                           outputBuffer.resize();
                           continue;
                        case BUFFER_UNDERFLOW:
                           throw new IllegalStateException("SSL engine underflow with the following SSL filter state: \n" + this.getDebugState());
                        case CLOSED:
                           stepFinished = true;
                           this.state = SslFilter.State.CLOSED;
                        default:
                           continue;
                     }
                  case NEED_UNWRAP:
                     SSLEngineResult result = this.sslEngine.unwrap(networkData, this.applicationInputBuffer);
                     this.applicationInputBuffer.flip();
                     if (this.applicationInputBuffer.hasRemaining()) {
                        inputBuffer.append(this.applicationInputBuffer);
                     }

                     this.applicationInputBuffer.compact();
                     if (result.getHandshakeStatus() == HandshakeStatus.FINISHED) {
                        stepFinished = true;
                        handshakeFinished = true;
                     }

                     switch (result.getStatus()) {
                        case BUFFER_OVERFLOW:
                           throw new IllegalStateException("SSL packet does not fit into the network buffer: " + this.getDebugState());
                        case BUFFER_UNDERFLOW:
                           stepFinished = true;
                           continue;
                        case CLOSED:
                           stepFinished = true;
                           this.state = SslFilter.State.CLOSED;
                        default:
                           continue;
                     }
                  case NEED_TASK:
                     while((delegatedTask = this.sslEngine.getDelegatedTask()) != null) {
                        delegatedTask.run();
                     }
               }
            }
         } catch (Exception var11) {
            this.handleSslError(var11);
         }
      }

      if (inputBuffer.isAllocated()) {
         ByteBuffer buffer = inputBuffer.get();
         this.upstreamFilter.onRead(buffer);
      }

      if (handshakeFinished) {
         this.handleHandshakeFinished();
         return true;
      } else {
         return false;
      }
   }

   private void handleHandshakeFinished() {
      if (this.customHostnameVerifier != null && !this.customHostnameVerifier.verify(this.serverHost, this.sslEngine.getSession())) {
         this.handleSslError(new SSLException("Server host name verification using " + this.customHostnameVerifier.getClass() + " has failed"));
      } else {
         if (this.state == SslFilter.State.HANDSHAKING) {
            this.state = SslFilter.State.DATA;
            this.upstreamFilter.onSslHandshakeCompleted();
         } else if (this.state == SslFilter.State.REHANDSHAKING) {
            this.state = SslFilter.State.DATA;
            if (this.pendingApplicationWrite != null) {
               Runnable write = this.pendingApplicationWrite;
               this.pendingApplicationWrite = null;
               write.run();
            }
         }

      }
   }

   private void handleSslError(Throwable t) {
      this.onError(t);
   }

   void startSsl() {
      try {
         this.state = SslFilter.State.HANDSHAKING;
         this.sslEngine.beginHandshake();
         this.doHandshakeStep(emptyBuffer);
      } catch (SSLException var2) {
         this.handleSslError(var2);
      }

   }

   void rehandshake() {
      try {
         this.sslEngine.beginHandshake();
      } catch (SSLException var2) {
         this.handleSslError(var2);
      }

   }

   private String getDebugState() {
      return "SslFilter{\napplicationInputBuffer=" + this.applicationInputBuffer + ",\nnetworkOutputBuffer=" + this.networkOutputBuffer + ",\nsslEngineStatus=" + this.sslEngine.getHandshakeStatus() + ",\nsslSession=" + this.sslEngine.getSession() + ",\nstate=" + this.state + ",\npendingApplicationWrite=" + this.pendingApplicationWrite + ",\npendingWritesSize=" + this.writeQueue + '}';
   }

   private class WriteQueue {
      private final Queue pendingWrites;

      private WriteQueue() {
         this.pendingWrites = new LinkedList();
      }

      void write(final ByteBuffer data, final CompletionHandler completionHandler) {
         synchronized(SslFilter.this) {
            Runnable r = new Runnable() {
               public void run() {
                  SslFilter.this.downstreamFilter.write(data, new CompletionHandler() {
                     public void completed(ByteBuffer result) {
                        if (completionHandler != null) {
                           completionHandler.completed(result);
                        }

                        WriteQueue.this.onWriteCompleted();
                     }

                     public void failed(Throwable throwable) {
                        if (completionHandler != null) {
                           completionHandler.failed(throwable);
                        }

                        WriteQueue.this.onWriteCompleted();
                     }
                  });
               }
            };
            this.pendingWrites.offer(r);
            if (this.pendingWrites.peek() == r) {
               r.run();
            }

         }
      }

      private void onWriteCompleted() {
         synchronized(SslFilter.this) {
            this.pendingWrites.poll();
            Runnable next = (Runnable)this.pendingWrites.peek();
            if (next != null) {
               next.run();
            }

         }
      }

      public String toString() {
         synchronized(SslFilter.this) {
            return "WriteQueue{pendingWrites=" + this.pendingWrites.size() + '}';
         }
      }

      // $FF: synthetic method
      WriteQueue(Object x1) {
         this();
      }
   }

   private class LazyBuffer {
      private ByteBuffer buffer;

      private LazyBuffer() {
         this.buffer = null;
      }

      ByteBuffer get() {
         if (this.buffer == null) {
            this.buffer = ByteBuffer.allocate(SslFilter.this.sslEngine.getSession().getPacketBufferSize());
         }

         return this.buffer;
      }

      boolean isAllocated() {
         return this.buffer != null;
      }

      void resize() {
         int increment = SslFilter.this.sslEngine.getSession().getPacketBufferSize();
         int newSize = this.buffer.position() + increment;
         ByteBuffer newBuffer = ByteBuffer.allocate(newSize);
         this.buffer.flip();
         newBuffer.flip();
         this.buffer = Utils.appendBuffers(newBuffer, this.buffer, newBuffer.limit(), 50);
         this.buffer.compact();
      }

      void append(ByteBuffer b) {
         if (this.buffer == null) {
            this.buffer = ByteBuffer.allocate(b.remaining());
            this.buffer.flip();
         }

         int newSize = this.buffer.limit() + b.remaining();
         this.buffer = Utils.appendBuffers(this.buffer, b, newSize, 50);
      }

      // $FF: synthetic method
      LazyBuffer(Object x1) {
         this();
      }
   }

   private static enum State {
      NOT_STARTED,
      HANDSHAKING,
      REHANDSHAKING,
      DATA,
      CLOSED;
   }
}
