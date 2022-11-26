package org.python.netty.handler.ssl;

import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.security.cert.X509Certificate;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.internal.tcnative.Buffer;
import org.python.netty.internal.tcnative.SSL;
import org.python.netty.util.AbstractReferenceCounted;
import org.python.netty.util.ReferenceCounted;
import org.python.netty.util.ResourceLeakDetector;
import org.python.netty.util.ResourceLeakDetectorFactory;
import org.python.netty.util.ResourceLeakTracker;
import org.python.netty.util.internal.EmptyArrays;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.ThrowableUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class ReferenceCountedOpenSslEngine extends SSLEngine implements ReferenceCounted {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslEngine.class);
   private static final SSLException BEGIN_HANDSHAKE_ENGINE_CLOSED = (SSLException)ThrowableUtil.unknownStackTrace(new SSLException("engine closed"), ReferenceCountedOpenSslEngine.class, "beginHandshake()");
   private static final SSLException HANDSHAKE_ENGINE_CLOSED = (SSLException)ThrowableUtil.unknownStackTrace(new SSLException("engine closed"), ReferenceCountedOpenSslEngine.class, "handshake()");
   private static final SSLException RENEGOTIATION_UNSUPPORTED = (SSLException)ThrowableUtil.unknownStackTrace(new SSLException("renegotiation unsupported"), ReferenceCountedOpenSslEngine.class, "beginHandshake()");
   private static final ResourceLeakDetector leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ReferenceCountedOpenSslEngine.class);
   private static final int DEFAULT_HOSTNAME_VALIDATION_FLAGS = 0;
   static final int MAX_PLAINTEXT_LENGTH = 16384;
   static final int MAX_TLS_RECORD_OVERHEAD_LENGTH = 90;
   static final int MAX_ENCRYPTED_PACKET_LENGTH = 16474;
   private static final AtomicIntegerFieldUpdater DESTROYED_UPDATER = AtomicIntegerFieldUpdater.newUpdater(ReferenceCountedOpenSslEngine.class, "destroyed");
   private static final String INVALID_CIPHER = "SSL_NULL_WITH_NULL_NULL";
   private static final SSLEngineResult NEED_UNWRAP_OK;
   private static final SSLEngineResult NEED_UNWRAP_CLOSED;
   private static final SSLEngineResult NEED_WRAP_OK;
   private static final SSLEngineResult NEED_WRAP_CLOSED;
   private static final SSLEngineResult CLOSED_NOT_HANDSHAKING;
   private long ssl;
   private long networkBIO;
   private boolean certificateSet;
   private HandshakeState handshakeState;
   private boolean renegotiationPending;
   private boolean receivedShutdown;
   private volatile int destroyed;
   private final ResourceLeakTracker leak;
   private final AbstractReferenceCounted refCnt;
   private volatile ClientAuth clientAuth;
   private volatile long lastAccessed;
   private String endPointIdentificationAlgorithm;
   private Object algorithmConstraints;
   private List sniHostNames;
   private volatile Collection matchers;
   private boolean isInboundDone;
   private boolean outboundClosed;
   private final boolean clientMode;
   private final ByteBufAllocator alloc;
   private final OpenSslEngineMap engineMap;
   private final OpenSslApplicationProtocolNegotiator apn;
   private final boolean rejectRemoteInitiatedRenegotiation;
   private final OpenSslSession session;
   private final Certificate[] localCerts;
   private final ByteBuffer[] singleSrcBuffer;
   private final ByteBuffer[] singleDstBuffer;
   private final OpenSslKeyMaterialManager keyMaterialManager;
   private final boolean enableOcsp;
   SSLHandshakeException handshakeException;

   ReferenceCountedOpenSslEngine(ReferenceCountedOpenSslContext context, ByteBufAllocator alloc, String peerHost, int peerPort, boolean leakDetection) {
      super(peerHost, peerPort);
      this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED;
      this.refCnt = new AbstractReferenceCounted() {
         public ReferenceCounted touch(Object hint) {
            if (ReferenceCountedOpenSslEngine.this.leak != null) {
               ReferenceCountedOpenSslEngine.this.leak.record(hint);
            }

            return ReferenceCountedOpenSslEngine.this;
         }

         protected void deallocate() {
            ReferenceCountedOpenSslEngine.this.shutdown();
            if (ReferenceCountedOpenSslEngine.this.leak != null) {
               boolean closed = ReferenceCountedOpenSslEngine.this.leak.close(ReferenceCountedOpenSslEngine.this);

               assert closed;
            }

         }
      };
      this.clientAuth = ClientAuth.NONE;
      this.lastAccessed = -1L;
      this.singleSrcBuffer = new ByteBuffer[1];
      this.singleDstBuffer = new ByteBuffer[1];
      OpenSsl.ensureAvailability();
      this.leak = leakDetection ? leakDetector.track(this) : null;
      this.alloc = (ByteBufAllocator)ObjectUtil.checkNotNull(alloc, "alloc");
      this.apn = (OpenSslApplicationProtocolNegotiator)context.applicationProtocolNegotiator();
      this.session = new OpenSslSession(context.sessionContext());
      this.clientMode = context.isClient();
      this.engineMap = context.engineMap;
      this.rejectRemoteInitiatedRenegotiation = context.getRejectRemoteInitiatedRenegotiation();
      this.localCerts = context.keyCertChain;
      this.keyMaterialManager = context.keyMaterialManager();
      this.enableOcsp = context.enableOcsp;
      this.ssl = SSL.newSSL(context.ctx, !context.isClient());

      try {
         this.networkBIO = SSL.bioNewByteBuffer(this.ssl, context.getBioNonApplicationBufferSize());
         this.setClientAuth(this.clientMode ? ClientAuth.NONE : context.clientAuth);
         if (context.protocols != null) {
            this.setEnabledProtocols(context.protocols);
         }

         if (this.clientMode && peerHost != null) {
            SSL.setTlsExtHostName(this.ssl, peerHost);
         }

         if (this.enableOcsp) {
            SSL.enableOcsp(this.ssl);
         }
      } catch (Throwable var7) {
         SSL.freeSSL(this.ssl);
         PlatformDependent.throwException(var7);
      }

   }

   public void setOcspResponse(byte[] response) {
      if (!this.enableOcsp) {
         throw new IllegalStateException("OCSP stapling is not enabled");
      } else if (this.clientMode) {
         throw new IllegalStateException("Not a server SSLEngine");
      } else {
         synchronized(this) {
            SSL.setOcspResponse(this.ssl, response);
         }
      }
   }

   public byte[] getOcspResponse() {
      if (!this.enableOcsp) {
         throw new IllegalStateException("OCSP stapling is not enabled");
      } else if (!this.clientMode) {
         throw new IllegalStateException("Not a client SSLEngine");
      } else {
         synchronized(this) {
            return SSL.getOcspResponse(this.ssl);
         }
      }
   }

   public final int refCnt() {
      return this.refCnt.refCnt();
   }

   public final ReferenceCounted retain() {
      this.refCnt.retain();
      return this;
   }

   public final ReferenceCounted retain(int increment) {
      this.refCnt.retain(increment);
      return this;
   }

   public final ReferenceCounted touch() {
      this.refCnt.touch();
      return this;
   }

   public final ReferenceCounted touch(Object hint) {
      this.refCnt.touch(hint);
      return this;
   }

   public final boolean release() {
      return this.refCnt.release();
   }

   public final boolean release(int decrement) {
      return this.refCnt.release(decrement);
   }

   public final synchronized SSLSession getHandshakeSession() {
      switch (this.handshakeState) {
         case NOT_STARTED:
         case FINISHED:
            return null;
         default:
            return this.session;
      }
   }

   public final synchronized long sslPointer() {
      return this.ssl;
   }

   public final synchronized void shutdown() {
      if (DESTROYED_UPDATER.compareAndSet(this, 0, 1)) {
         this.engineMap.remove(this.ssl);
         SSL.freeSSL(this.ssl);
         this.ssl = this.networkBIO = 0L;
         this.isInboundDone = this.outboundClosed = true;
      }

      SSL.clearError();
   }

   private int writePlaintextData(ByteBuffer src, int len) {
      int pos = src.position();
      int limit = src.limit();
      int sslWrote;
      if (src.isDirect()) {
         sslWrote = SSL.writeToSSL(this.ssl, Buffer.address(src) + (long)pos, len);
         if (sslWrote > 0) {
            src.position(pos + sslWrote);
         }
      } else {
         ByteBuf buf = this.alloc.directBuffer(len);

         try {
            src.limit(pos + len);
            buf.setBytes(0, (ByteBuffer)src);
            src.limit(limit);
            sslWrote = SSL.writeToSSL(this.ssl, OpenSsl.memoryAddress(buf), len);
            if (sslWrote > 0) {
               src.position(pos + sslWrote);
            } else {
               src.position(pos);
            }
         } finally {
            buf.release();
         }
      }

      return sslWrote;
   }

   private ByteBuf writeEncryptedData(ByteBuffer src, int len) {
      int pos = src.position();
      if (src.isDirect()) {
         SSL.bioSetByteBuffer(this.networkBIO, Buffer.address(src) + (long)pos, len, false);
      } else {
         ByteBuf buf = this.alloc.directBuffer(len);

         try {
            int limit = src.limit();
            src.limit(pos + len);
            buf.writeBytes(src);
            src.position(pos);
            src.limit(limit);
            SSL.bioSetByteBuffer(this.networkBIO, OpenSsl.memoryAddress(buf), len, false);
            return buf;
         } catch (Throwable var6) {
            buf.release();
            PlatformDependent.throwException(var6);
         }
      }

      return null;
   }

   private int readPlaintextData(ByteBuffer dst) {
      int pos = dst.position();
      int sslRead;
      if (dst.isDirect()) {
         sslRead = SSL.readFromSSL(this.ssl, Buffer.address(dst) + (long)pos, dst.limit() - pos);
         if (sslRead > 0) {
            dst.position(pos + sslRead);
         }
      } else {
         int limit = dst.limit();
         int len = Math.min(16474, limit - pos);
         ByteBuf buf = this.alloc.directBuffer(len);

         try {
            sslRead = SSL.readFromSSL(this.ssl, OpenSsl.memoryAddress(buf), len);
            if (sslRead > 0) {
               dst.limit(pos + sslRead);
               buf.getBytes(buf.readerIndex(), dst);
               dst.limit(limit);
            }
         } finally {
            buf.release();
         }
      }

      return sslRead;
   }

   public final SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int length, ByteBuffer dst) throws SSLException {
      if (srcs == null) {
         throw new IllegalArgumentException("srcs is null");
      } else if (dst == null) {
         throw new IllegalArgumentException("dst is null");
      } else if (offset < srcs.length && offset + length <= srcs.length) {
         if (dst.isReadOnly()) {
            throw new ReadOnlyBufferException();
         } else {
            synchronized(this) {
               if (this.isOutboundDone()) {
                  return !this.isInboundDone() && !this.isDestroyed() ? NEED_UNWRAP_CLOSED : CLOSED_NOT_HANDSHAKING;
               } else {
                  int bytesProduced = 0;
                  ByteBuf bioReadCopyBuf = null;

                  try {
                     if (dst.isDirect()) {
                        SSL.bioSetByteBuffer(this.networkBIO, Buffer.address(dst) + (long)dst.position(), dst.remaining(), true);
                     } else {
                        bioReadCopyBuf = this.alloc.directBuffer(dst.remaining());
                        SSL.bioSetByteBuffer(this.networkBIO, OpenSsl.memoryAddress(bioReadCopyBuf), bioReadCopyBuf.writableBytes(), true);
                     }

                     int bioLengthBefore = SSL.bioLengthByteBuffer(this.networkBIO);
                     if (this.outboundClosed) {
                        bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
                        SSLEngineResult var26;
                        if (bytesProduced <= 0) {
                           var26 = this.newResultMayFinishHandshake(HandshakeStatus.NOT_HANDSHAKING, 0, 0);
                           return var26;
                        } else if (!this.doSSLShutdown()) {
                           var26 = this.newResultMayFinishHandshake(HandshakeStatus.NOT_HANDSHAKING, 0, bytesProduced);
                           return var26;
                        } else {
                           bytesProduced = bioLengthBefore - SSL.bioLengthByteBuffer(this.networkBIO);
                           var26 = this.newResultMayFinishHandshake(HandshakeStatus.NEED_WRAP, 0, bytesProduced);
                           return var26;
                        }
                     } else {
                        SSLEngineResult.HandshakeStatus status = HandshakeStatus.NOT_HANDSHAKING;
                        if (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.FINISHED) {
                           if (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY) {
                              this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.STARTED_IMPLICITLY;
                           }

                           bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
                           SSLEngineResult var25;
                           if (bytesProduced > 0 && this.handshakeException != null) {
                              var25 = this.newResult(HandshakeStatus.NEED_WRAP, 0, bytesProduced);
                              return var25;
                           }

                           status = this.handshake();
                           if (this.renegotiationPending && status == HandshakeStatus.FINISHED) {
                              this.renegotiationPending = false;
                              SSL.setState(this.ssl, SSL.SSL_ST_ACCEPT);
                              this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY;
                              status = this.handshake();
                           }

                           bytesProduced = bioLengthBefore - SSL.bioLengthByteBuffer(this.networkBIO);
                           if (bytesProduced > 0) {
                              var25 = this.newResult(this.mayFinishHandshake(status != HandshakeStatus.FINISHED ? this.getHandshakeStatus(SSL.bioLengthNonApplication(this.networkBIO)) : HandshakeStatus.FINISHED), 0, bytesProduced);
                              return var25;
                           }

                           if (status == HandshakeStatus.NEED_UNWRAP) {
                              var25 = this.isOutboundDone() ? NEED_UNWRAP_CLOSED : NEED_UNWRAP_OK;
                              return var25;
                           }

                           if (this.outboundClosed) {
                              bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
                              var25 = this.newResultMayFinishHandshake(status, 0, bytesProduced);
                              return var25;
                           }
                        }

                        int srcsLen = 0;
                        int endOffset = offset + length;

                        int bytesConsumed;
                        ByteBuffer src;
                        for(bytesConsumed = offset; bytesConsumed < endOffset; ++bytesConsumed) {
                           src = srcs[bytesConsumed];
                           if (src == null) {
                              throw new IllegalArgumentException("srcs[" + bytesConsumed + "] is null");
                           }

                           if (srcsLen != 16384) {
                              srcsLen += src.remaining();
                              if (srcsLen > 16384 || srcsLen < 0) {
                                 srcsLen = 16384;
                              }
                           }
                        }

                        if (dst.remaining() < calculateOutNetBufSize(srcsLen, 1)) {
                           SSLEngineResult var28 = new SSLEngineResult(Status.BUFFER_OVERFLOW, this.getHandshakeStatus(), 0, 0);
                           return var28;
                        } else {
                           int bytesConsumed = 0;

                           for(bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO); offset < endOffset; ++offset) {
                              src = srcs[offset];
                              int remaining = src.remaining();
                              if (remaining != 0) {
                                 int bytesWritten = this.writePlaintextData(src, Math.min(remaining, 16384 - bytesConsumed));
                                 int sslError;
                                 SSLEngineResult var17;
                                 if (bytesWritten > 0) {
                                    bytesConsumed = bytesConsumed + bytesWritten;
                                    sslError = SSL.bioLengthByteBuffer(this.networkBIO);
                                    bytesProduced += bioLengthBefore - sslError;
                                    var17 = this.newResultMayFinishHandshake(status, bytesConsumed, bytesProduced);
                                    return var17;
                                 }

                                 sslError = SSL.getError(this.ssl, bytesWritten);
                                 if (sslError == SSL.SSL_ERROR_ZERO_RETURN) {
                                    if (!this.receivedShutdown) {
                                       this.closeAll();
                                       bytesProduced += bioLengthBefore - SSL.bioLengthByteBuffer(this.networkBIO);
                                       SSLEngineResult.HandshakeStatus hs = this.mayFinishHandshake(status != HandshakeStatus.FINISHED ? this.getHandshakeStatus(SSL.bioLengthNonApplication(this.networkBIO)) : HandshakeStatus.FINISHED);
                                       SSLEngineResult var18 = this.newResult(hs, bytesConsumed, bytesProduced);
                                       return var18;
                                    }

                                    var17 = this.newResult(HandshakeStatus.NOT_HANDSHAKING, bytesConsumed, bytesProduced);
                                    return var17;
                                 }

                                 if (sslError != SSL.SSL_ERROR_WANT_READ) {
                                    if (sslError == SSL.SSL_ERROR_WANT_WRITE) {
                                       var17 = this.newResult(HandshakeStatus.NEED_WRAP, bytesConsumed, bytesProduced);
                                       return var17;
                                    }

                                    throw this.shutdownWithError("SSL_write");
                                 }

                                 var17 = this.newResult(HandshakeStatus.NEED_UNWRAP, bytesConsumed, bytesProduced);
                                 return var17;
                              }
                           }

                           SSLEngineResult var29 = this.newResultMayFinishHandshake(status, bytesConsumed, bytesProduced);
                           return var29;
                        }
                     }
                  } finally {
                     SSL.bioClearByteBuffer(this.networkBIO);
                     if (bioReadCopyBuf == null) {
                        dst.position(dst.position() + bytesProduced);
                     } else {
                        assert bioReadCopyBuf.readableBytes() <= dst.remaining() : "The destination buffer " + dst + " didn't have enough remaining space to hold the encrypted content in " + bioReadCopyBuf;

                        dst.put(bioReadCopyBuf.internalNioBuffer(bioReadCopyBuf.readerIndex(), bytesProduced));
                        bioReadCopyBuf.release();
                     }

                  }
               }
            }
         }
      } else {
         throw new IndexOutOfBoundsException("offset: " + offset + ", length: " + length + " (expected: offset <= offset + length <= srcs.length (" + srcs.length + "))");
      }
   }

   private SSLEngineResult newResult(SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) {
      return this.newResult(Status.OK, hs, bytesConsumed, bytesProduced);
   }

   private SSLEngineResult newResult(SSLEngineResult.Status status, SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) {
      if (this.isOutboundDone()) {
         if (this.isInboundDone()) {
            hs = HandshakeStatus.NOT_HANDSHAKING;
            this.shutdown();
         }

         return new SSLEngineResult(Status.CLOSED, hs, bytesConsumed, bytesProduced);
      } else {
         return new SSLEngineResult(status, hs, bytesConsumed, bytesProduced);
      }
   }

   private SSLEngineResult newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) throws SSLException {
      return this.newResult(this.mayFinishHandshake(hs != HandshakeStatus.FINISHED ? this.getHandshakeStatus() : HandshakeStatus.FINISHED), bytesConsumed, bytesProduced);
   }

   private SSLEngineResult newResultMayFinishHandshake(SSLEngineResult.Status status, SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) throws SSLException {
      return this.newResult(status, this.mayFinishHandshake(hs != HandshakeStatus.FINISHED ? this.getHandshakeStatus() : HandshakeStatus.FINISHED), bytesConsumed, bytesProduced);
   }

   private SSLException shutdownWithError(String operations) {
      String err = SSL.getLastError();
      return this.shutdownWithError(operations, err);
   }

   private SSLException shutdownWithError(String operation, String err) {
      if (logger.isDebugEnabled()) {
         logger.debug("{} failed: OpenSSL error: {}", operation, err);
      }

      this.shutdown();
      return (SSLException)(this.handshakeState == ReferenceCountedOpenSslEngine.HandshakeState.FINISHED ? new SSLException(err) : new SSLHandshakeException(err));
   }

   public final SSLEngineResult unwrap(ByteBuffer[] srcs, int srcsOffset, int srcsLength, ByteBuffer[] dsts, int dstsOffset, int dstsLength) throws SSLException {
      if (srcs == null) {
         throw new NullPointerException("srcs");
      } else if (srcsOffset < srcs.length && srcsOffset + srcsLength <= srcs.length) {
         if (dsts == null) {
            throw new IllegalArgumentException("dsts is null");
         } else if (dstsOffset < dsts.length && dstsOffset + dstsLength <= dsts.length) {
            long capacity = 0L;
            int dstsEndOffset = dstsOffset + dstsLength;

            int srcsEndOffset;
            for(srcsEndOffset = dstsOffset; srcsEndOffset < dstsEndOffset; ++srcsEndOffset) {
               ByteBuffer dst = dsts[srcsEndOffset];
               if (dst == null) {
                  throw new IllegalArgumentException("dsts[" + srcsEndOffset + "] is null");
               }

               if (dst.isReadOnly()) {
                  throw new ReadOnlyBufferException();
               }

               capacity += (long)dst.remaining();
            }

            srcsEndOffset = srcsOffset + srcsLength;
            long len = 0L;

            for(int i = srcsOffset; i < srcsEndOffset; ++i) {
               ByteBuffer src = srcs[i];
               if (src == null) {
                  throw new IllegalArgumentException("srcs[" + i + "] is null");
               }

               len += (long)src.remaining();
            }

            synchronized(this) {
               if (this.isInboundDone()) {
                  return !this.isOutboundDone() && !this.isDestroyed() ? NEED_WRAP_CLOSED : CLOSED_NOT_HANDSHAKING;
               } else {
                  SSLEngineResult.HandshakeStatus status = HandshakeStatus.NOT_HANDSHAKING;
                  if (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.FINISHED) {
                     if (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY) {
                        this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.STARTED_IMPLICITLY;
                     }

                     status = this.handshake();
                     if (status == HandshakeStatus.NEED_WRAP) {
                        return NEED_WRAP_OK;
                     }

                     if (this.isInboundDone) {
                        return NEED_WRAP_CLOSED;
                     }
                  }

                  if (len < 5L) {
                     return this.newResultMayFinishHandshake(Status.BUFFER_UNDERFLOW, status, 0, 0);
                  } else {
                     int packetLength = SslUtils.getEncryptedPacketLength(srcs, srcsOffset);
                     if (packetLength == -2) {
                        throw new NotSslRecordException("not an SSL/TLS record");
                     } else if ((long)(packetLength - 5) > capacity) {
                        return this.newResultMayFinishHandshake(Status.BUFFER_OVERFLOW, status, 0, 0);
                     } else if (len < (long)packetLength) {
                        return this.newResultMayFinishHandshake(Status.BUFFER_UNDERFLOW, status, 0, 0);
                     } else {
                        assert srcsOffset < srcsEndOffset;

                        assert capacity > 0L;

                        int bytesProduced = 0;
                        int bytesConsumed = 0;

                        try {
                           for(; srcsOffset < srcsEndOffset; ++srcsOffset) {
                              ByteBuffer src = srcs[srcsOffset];
                              int remaining = src.remaining();
                              if (remaining != 0) {
                                 int pendingEncryptedBytes = Math.min(packetLength, remaining);
                                 ByteBuf bioWriteCopyBuf = this.writeEncryptedData(src, pendingEncryptedBytes);

                                 try {
                                    for(; dstsOffset < dstsEndOffset; ++dstsOffset) {
                                       ByteBuffer dst = dsts[dstsOffset];
                                       if (dst.hasRemaining()) {
                                          int bytesRead = this.readPlaintextData(dst);
                                          int localBytesConsumed = pendingEncryptedBytes - SSL.bioLengthByteBuffer(this.networkBIO);
                                          bytesConsumed += localBytesConsumed;
                                          packetLength -= localBytesConsumed;
                                          pendingEncryptedBytes -= localBytesConsumed;
                                          src.position(src.position() + localBytesConsumed);
                                          SSLEngineResult sslError;
                                          if (bytesRead <= 0) {
                                             sslError = SSL.getError(this.ssl, bytesRead);
                                             if (sslError != SSL.SSL_ERROR_WANT_READ && sslError != SSL.SSL_ERROR_WANT_WRITE) {
                                                SSLEngineResult var27;
                                                if (sslError == SSL.SSL_ERROR_ZERO_RETURN) {
                                                   if (!this.receivedShutdown) {
                                                      this.closeAll();
                                                   }

                                                   var27 = this.newResultMayFinishHandshake(this.isInboundDone() ? Status.CLOSED : Status.OK, status, bytesConsumed, bytesProduced);
                                                   return var27;
                                                }

                                                var27 = this.sslReadErrorResult(SSL.getLastErrorNumber(), bytesConsumed, bytesProduced);
                                                return var27;
                                             }
                                             break;
                                          }

                                          bytesProduced += bytesRead;
                                          if (dst.hasRemaining()) {
                                             if (packetLength == 0) {
                                                sslError = this.newResultMayFinishHandshake(this.isInboundDone() ? Status.CLOSED : Status.OK, status, bytesConsumed, bytesProduced);
                                                return sslError;
                                             }
                                             break;
                                          }
                                       }
                                    }

                                    if (dstsOffset >= dstsEndOffset || packetLength == 0) {
                                       break;
                                    }
                                 } finally {
                                    if (bioWriteCopyBuf != null) {
                                       bioWriteCopyBuf.release();
                                    }

                                 }
                              }
                           }
                        } finally {
                           SSL.bioClearByteBuffer(this.networkBIO);
                           this.rejectRemoteInitiatedRenegotiation();
                        }

                        if (!this.receivedShutdown && (SSL.getShutdown(this.ssl) & SSL.SSL_RECEIVED_SHUTDOWN) == SSL.SSL_RECEIVED_SHUTDOWN) {
                           this.closeAll();
                        }

                        return this.newResultMayFinishHandshake(this.isInboundDone() ? Status.CLOSED : Status.OK, status, bytesConsumed, bytesProduced);
                     }
                  }
               }
            }
         } else {
            throw new IndexOutOfBoundsException("offset: " + dstsOffset + ", length: " + dstsLength + " (expected: offset <= offset + length <= dsts.length (" + dsts.length + "))");
         }
      } else {
         throw new IndexOutOfBoundsException("offset: " + srcsOffset + ", length: " + srcsLength + " (expected: offset <= offset + length <= srcs.length (" + srcs.length + "))");
      }
   }

   private SSLEngineResult sslReadErrorResult(int err, int bytesConsumed, int bytesProduced) throws SSLException {
      String errStr = SSL.getErrorString((long)err);
      if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
         if (this.handshakeException == null && this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.FINISHED) {
            this.handshakeException = new SSLHandshakeException(errStr);
         }

         return new SSLEngineResult(Status.OK, HandshakeStatus.NEED_WRAP, bytesConsumed, bytesProduced);
      } else {
         throw this.shutdownWithError("SSL_read", errStr);
      }
   }

   private void closeAll() throws SSLException {
      this.receivedShutdown = true;
      this.closeOutbound();
      this.closeInbound();
   }

   private void rejectRemoteInitiatedRenegotiation() throws SSLHandshakeException {
      if (this.rejectRemoteInitiatedRenegotiation && SSL.getHandshakeCount(this.ssl) > 1) {
         this.shutdown();
         throw new SSLHandshakeException("remote-initiated renegotiation not allowed");
      }
   }

   public final SSLEngineResult unwrap(ByteBuffer[] srcs, ByteBuffer[] dsts) throws SSLException {
      return this.unwrap(srcs, 0, srcs.length, dsts, 0, dsts.length);
   }

   private ByteBuffer[] singleSrcBuffer(ByteBuffer src) {
      this.singleSrcBuffer[0] = src;
      return this.singleSrcBuffer;
   }

   private void resetSingleSrcBuffer() {
      this.singleSrcBuffer[0] = null;
   }

   private ByteBuffer[] singleDstBuffer(ByteBuffer src) {
      this.singleDstBuffer[0] = src;
      return this.singleDstBuffer;
   }

   private void resetSingleDstBuffer() {
      this.singleDstBuffer[0] = null;
   }

   public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts, int offset, int length) throws SSLException {
      SSLEngineResult var5;
      try {
         var5 = this.unwrap(this.singleSrcBuffer(src), 0, 1, dsts, offset, length);
      } finally {
         this.resetSingleSrcBuffer();
      }

      return var5;
   }

   public final synchronized SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
      SSLEngineResult var3;
      try {
         var3 = this.wrap(this.singleSrcBuffer(src), dst);
      } finally {
         this.resetSingleSrcBuffer();
      }

      return var3;
   }

   public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
      SSLEngineResult var3;
      try {
         var3 = this.unwrap(this.singleSrcBuffer(src), this.singleDstBuffer(dst));
      } finally {
         this.resetSingleSrcBuffer();
         this.resetSingleDstBuffer();
      }

      return var3;
   }

   public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts) throws SSLException {
      SSLEngineResult var3;
      try {
         var3 = this.unwrap(this.singleSrcBuffer(src), dsts);
      } finally {
         this.resetSingleSrcBuffer();
      }

      return var3;
   }

   public final Runnable getDelegatedTask() {
      return null;
   }

   public final synchronized void closeInbound() throws SSLException {
      if (!this.isInboundDone) {
         this.isInboundDone = true;
         if (this.isOutboundDone()) {
            this.shutdown();
         }

         if (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED && !this.receivedShutdown) {
            throw new SSLException("Inbound closed before receiving peer's close_notify: possible truncation attack?");
         }
      }
   }

   public final synchronized boolean isInboundDone() {
      return this.isInboundDone;
   }

   public final synchronized void closeOutbound() {
      if (!this.outboundClosed) {
         this.outboundClosed = true;
         if (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED && !this.isDestroyed()) {
            int mode = SSL.getShutdown(this.ssl);
            if ((mode & SSL.SSL_SENT_SHUTDOWN) != SSL.SSL_SENT_SHUTDOWN) {
               this.doSSLShutdown();
            }
         } else {
            this.shutdown();
         }

      }
   }

   private boolean doSSLShutdown() {
      if (SSL.isInInit(this.ssl) != 0) {
         return false;
      } else {
         int err = SSL.shutdownSSL(this.ssl);
         if (err < 0) {
            int sslErr = SSL.getError(this.ssl, err);
            if (sslErr == SSL.SSL_ERROR_SYSCALL || sslErr == SSL.SSL_ERROR_SSL) {
               if (logger.isDebugEnabled()) {
                  logger.debug("SSL_shutdown failed: OpenSSL error: {}", (Object)SSL.getLastError());
               }

               this.shutdown();
               return false;
            }

            SSL.clearError();
         }

         return true;
      }
   }

   public final synchronized boolean isOutboundDone() {
      return this.outboundClosed && (this.networkBIO == 0L || SSL.bioLengthNonApplication(this.networkBIO) == 0);
   }

   public final String[] getSupportedCipherSuites() {
      return (String[])OpenSsl.AVAILABLE_CIPHER_SUITES.toArray(new String[OpenSsl.AVAILABLE_CIPHER_SUITES.size()]);
   }

   public final String[] getEnabledCipherSuites() {
      String[] enabled;
      synchronized(this) {
         if (this.isDestroyed()) {
            return EmptyArrays.EMPTY_STRINGS;
         }

         enabled = SSL.getCiphers(this.ssl);
      }

      if (enabled == null) {
         return EmptyArrays.EMPTY_STRINGS;
      } else {
         synchronized(this) {
            for(int i = 0; i < enabled.length; ++i) {
               String mapped = this.toJavaCipherSuite(enabled[i]);
               if (mapped != null) {
                  enabled[i] = mapped;
               }
            }

            return enabled;
         }
      }
   }

   public final void setEnabledCipherSuites(String[] cipherSuites) {
      ObjectUtil.checkNotNull(cipherSuites, "cipherSuites");
      StringBuilder buf = new StringBuilder();
      String[] var3 = cipherSuites;
      int var4 = cipherSuites.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String c = var3[var5];
         if (c == null) {
            break;
         }

         String converted = CipherSuiteConverter.toOpenSsl(c);
         if (converted == null) {
            converted = c;
         }

         if (!OpenSsl.isCipherSuiteAvailable(converted)) {
            throw new IllegalArgumentException("unsupported cipher suite: " + c + '(' + converted + ')');
         }

         buf.append(converted);
         buf.append(':');
      }

      if (buf.length() == 0) {
         throw new IllegalArgumentException("empty cipher suites");
      } else {
         buf.setLength(buf.length() - 1);
         String cipherSuiteSpec = buf.toString();
         synchronized(this) {
            if (!this.isDestroyed()) {
               try {
                  SSL.setCipherSuites(this.ssl, cipherSuiteSpec);
               } catch (Exception var9) {
                  throw new IllegalStateException("failed to enable cipher suites: " + cipherSuiteSpec, var9);
               }

            } else {
               throw new IllegalStateException("failed to enable cipher suites: " + cipherSuiteSpec);
            }
         }
      }
   }

   public final String[] getSupportedProtocols() {
      return (String[])OpenSsl.SUPPORTED_PROTOCOLS_SET.toArray(new String[OpenSsl.SUPPORTED_PROTOCOLS_SET.size()]);
   }

   public final String[] getEnabledProtocols() {
      List enabled = new ArrayList(6);
      enabled.add("SSLv2Hello");
      int opts;
      synchronized(this) {
         if (this.isDestroyed()) {
            return (String[])enabled.toArray(new String[1]);
         }

         opts = SSL.getOptions(this.ssl);
      }

      if (isProtocolEnabled(opts, SSL.SSL_OP_NO_TLSv1, "TLSv1")) {
         enabled.add("TLSv1");
      }

      if (isProtocolEnabled(opts, SSL.SSL_OP_NO_TLSv1_1, "TLSv1.1")) {
         enabled.add("TLSv1.1");
      }

      if (isProtocolEnabled(opts, SSL.SSL_OP_NO_TLSv1_2, "TLSv1.2")) {
         enabled.add("TLSv1.2");
      }

      if (isProtocolEnabled(opts, SSL.SSL_OP_NO_SSLv2, "SSLv2")) {
         enabled.add("SSLv2");
      }

      if (isProtocolEnabled(opts, SSL.SSL_OP_NO_SSLv3, "SSLv3")) {
         enabled.add("SSLv3");
      }

      return (String[])enabled.toArray(new String[enabled.size()]);
   }

   private static boolean isProtocolEnabled(int opts, int disableMask, String protocolString) {
      return (opts & disableMask) == 0 && OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(protocolString);
   }

   public final void setEnabledProtocols(String[] protocols) {
      if (protocols == null) {
         throw new IllegalArgumentException();
      } else {
         boolean sslv2 = false;
         boolean sslv3 = false;
         boolean tlsv1 = false;
         boolean tlsv1_1 = false;
         boolean tlsv1_2 = false;
         String[] var7 = protocols;
         int opts = protocols.length;

         for(int var9 = 0; var9 < opts; ++var9) {
            String p = var7[var9];
            if (!OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(p)) {
               throw new IllegalArgumentException("Protocol " + p + " is not supported.");
            }

            if (p.equals("SSLv2")) {
               sslv2 = true;
            } else if (p.equals("SSLv3")) {
               sslv3 = true;
            } else if (p.equals("TLSv1")) {
               tlsv1 = true;
            } else if (p.equals("TLSv1.1")) {
               tlsv1_1 = true;
            } else if (p.equals("TLSv1.2")) {
               tlsv1_2 = true;
            }
         }

         synchronized(this) {
            if (!this.isDestroyed()) {
               SSL.clearOptions(this.ssl, SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_NO_TLSv1 | SSL.SSL_OP_NO_TLSv1_1 | SSL.SSL_OP_NO_TLSv1_2);
               opts = 0;
               if (!sslv2) {
                  opts |= SSL.SSL_OP_NO_SSLv2;
               }

               if (!sslv3) {
                  opts |= SSL.SSL_OP_NO_SSLv3;
               }

               if (!tlsv1) {
                  opts |= SSL.SSL_OP_NO_TLSv1;
               }

               if (!tlsv1_1) {
                  opts |= SSL.SSL_OP_NO_TLSv1_1;
               }

               if (!tlsv1_2) {
                  opts |= SSL.SSL_OP_NO_TLSv1_2;
               }

               SSL.setOptions(this.ssl, opts);
            } else {
               throw new IllegalStateException("failed to enable protocols: " + Arrays.asList(protocols));
            }
         }
      }
   }

   public final SSLSession getSession() {
      return this.session;
   }

   public final synchronized void beginHandshake() throws SSLException {
      switch (this.handshakeState) {
         case FINISHED:
            if (this.clientMode) {
               throw RENEGOTIATION_UNSUPPORTED;
            }

            int status;
            if ((status = SSL.renegotiate(this.ssl)) != 1 || (status = SSL.doHandshake(this.ssl)) != 1) {
               int err = SSL.getError(this.ssl, status);
               if (err != SSL.SSL_ERROR_WANT_READ && err != SSL.SSL_ERROR_WANT_WRITE) {
                  throw this.shutdownWithError("renegotiation failed");
               } else {
                  this.renegotiationPending = true;
                  this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY;
                  this.lastAccessed = System.currentTimeMillis();
                  return;
               }
            }

            SSL.setState(this.ssl, SSL.SSL_ST_ACCEPT);
            this.lastAccessed = System.currentTimeMillis();
         case NOT_STARTED:
            this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY;
            this.handshake();
            break;
         case STARTED_IMPLICITLY:
            this.checkEngineClosed(BEGIN_HANDSHAKE_ENGINE_CLOSED);
            this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY;
         case STARTED_EXPLICITLY:
            break;
         default:
            throw new Error();
      }

   }

   private void checkEngineClosed(SSLException cause) throws SSLException {
      if (this.isDestroyed()) {
         throw cause;
      }
   }

   private static SSLEngineResult.HandshakeStatus pendingStatus(int pendingStatus) {
      return pendingStatus > 0 ? HandshakeStatus.NEED_WRAP : HandshakeStatus.NEED_UNWRAP;
   }

   private static boolean isEmpty(Object[] arr) {
      return arr == null || arr.length == 0;
   }

   private static boolean isEmpty(byte[] cert) {
      return cert == null || cert.length == 0;
   }

   private SSLEngineResult.HandshakeStatus handshake() throws SSLException {
      if (this.handshakeState == ReferenceCountedOpenSslEngine.HandshakeState.FINISHED) {
         return HandshakeStatus.FINISHED;
      } else {
         this.checkEngineClosed(HANDSHAKE_ENGINE_CLOSED);
         SSLHandshakeException exception = this.handshakeException;
         if (exception != null) {
            if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
               return HandshakeStatus.NEED_WRAP;
            } else {
               this.handshakeException = null;
               this.shutdown();
               throw exception;
            }
         } else {
            this.engineMap.add(this);
            if (this.lastAccessed == -1L) {
               this.lastAccessed = System.currentTimeMillis();
            }

            if (!this.certificateSet && this.keyMaterialManager != null) {
               this.certificateSet = true;
               this.keyMaterialManager.setKeyMaterial(this);
            }

            int code = SSL.doHandshake(this.ssl);
            if (code <= 0) {
               if (this.handshakeException != null) {
                  exception = this.handshakeException;
                  this.handshakeException = null;
                  this.shutdown();
                  throw exception;
               } else {
                  int sslError = SSL.getError(this.ssl, code);
                  if (sslError != SSL.SSL_ERROR_WANT_READ && sslError != SSL.SSL_ERROR_WANT_WRITE) {
                     throw this.shutdownWithError("SSL_do_handshake");
                  } else {
                     return pendingStatus(SSL.bioLengthNonApplication(this.networkBIO));
                  }
               }
            } else {
               this.session.handshakeFinished();
               this.engineMap.remove(this.ssl);
               return HandshakeStatus.FINISHED;
            }
         }
      }
   }

   private SSLEngineResult.HandshakeStatus mayFinishHandshake(SSLEngineResult.HandshakeStatus status) throws SSLException {
      return status == HandshakeStatus.NOT_HANDSHAKING && this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.FINISHED ? this.handshake() : status;
   }

   public final synchronized SSLEngineResult.HandshakeStatus getHandshakeStatus() {
      return this.needPendingStatus() ? pendingStatus(SSL.bioLengthNonApplication(this.networkBIO)) : HandshakeStatus.NOT_HANDSHAKING;
   }

   private SSLEngineResult.HandshakeStatus getHandshakeStatus(int pending) {
      return this.needPendingStatus() ? pendingStatus(pending) : HandshakeStatus.NOT_HANDSHAKING;
   }

   private boolean needPendingStatus() {
      return this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED && !this.isDestroyed() && (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.FINISHED || this.isInboundDone() || this.isOutboundDone());
   }

   private String toJavaCipherSuite(String openSslCipherSuite) {
      if (openSslCipherSuite == null) {
         return null;
      } else {
         String prefix = toJavaCipherSuitePrefix(SSL.getVersion(this.ssl));
         return CipherSuiteConverter.toJava(openSslCipherSuite, prefix);
      }
   }

   private static String toJavaCipherSuitePrefix(String protocolVersion) {
      char c;
      if (protocolVersion != null && !protocolVersion.isEmpty()) {
         c = protocolVersion.charAt(0);
      } else {
         c = 0;
      }

      switch (c) {
         case 'S':
            return "SSL";
         case 'T':
            return "TLS";
         default:
            return "UNKNOWN";
      }
   }

   public final void setUseClientMode(boolean clientMode) {
      if (clientMode != this.clientMode) {
         throw new UnsupportedOperationException();
      }
   }

   public final boolean getUseClientMode() {
      return this.clientMode;
   }

   public final void setNeedClientAuth(boolean b) {
      this.setClientAuth(b ? ClientAuth.REQUIRE : ClientAuth.NONE);
   }

   public final boolean getNeedClientAuth() {
      return this.clientAuth == ClientAuth.REQUIRE;
   }

   public final void setWantClientAuth(boolean b) {
      this.setClientAuth(b ? ClientAuth.OPTIONAL : ClientAuth.NONE);
   }

   public final boolean getWantClientAuth() {
      return this.clientAuth == ClientAuth.OPTIONAL;
   }

   public final synchronized void setVerify(int verifyMode, int depth) {
      SSL.setVerify(this.ssl, verifyMode, depth);
   }

   private void setClientAuth(ClientAuth mode) {
      if (!this.clientMode) {
         synchronized(this) {
            if (this.clientAuth != mode) {
               switch (mode) {
                  case NONE:
                     SSL.setVerify(this.ssl, 0, 10);
                     break;
                  case REQUIRE:
                     SSL.setVerify(this.ssl, 2, 10);
                     break;
                  case OPTIONAL:
                     SSL.setVerify(this.ssl, 1, 10);
                     break;
                  default:
                     throw new Error(mode.toString());
               }

               this.clientAuth = mode;
            }
         }
      }
   }

   public final void setEnableSessionCreation(boolean b) {
      if (b) {
         throw new UnsupportedOperationException();
      }
   }

   public final boolean getEnableSessionCreation() {
      return false;
   }

   public final synchronized SSLParameters getSSLParameters() {
      SSLParameters sslParameters = super.getSSLParameters();
      int version = PlatformDependent.javaVersion();
      if (version >= 7) {
         sslParameters.setEndpointIdentificationAlgorithm(this.endPointIdentificationAlgorithm);
         Java7SslParametersUtils.setAlgorithmConstraints(sslParameters, this.algorithmConstraints);
         if (version >= 8) {
            if (this.sniHostNames != null) {
               Java8SslUtils.setSniHostNames(sslParameters, this.sniHostNames);
            }

            if (!this.isDestroyed()) {
               Java8SslUtils.setUseCipherSuitesOrder(sslParameters, (SSL.getOptions(this.ssl) & SSL.SSL_OP_CIPHER_SERVER_PREFERENCE) != 0);
            }

            Java8SslUtils.setSNIMatchers(sslParameters, this.matchers);
         }
      }

      return sslParameters;
   }

   public final synchronized void setSSLParameters(SSLParameters sslParameters) {
      int version = PlatformDependent.javaVersion();
      if (version >= 7) {
         if (sslParameters.getAlgorithmConstraints() != null) {
            throw new IllegalArgumentException("AlgorithmConstraints are not supported.");
         }

         if (version >= 8) {
            if (!this.isDestroyed()) {
               if (this.clientMode) {
                  List sniHostNames = Java8SslUtils.getSniHostNames(sslParameters);
                  Iterator var4 = sniHostNames.iterator();

                  while(var4.hasNext()) {
                     String name = (String)var4.next();
                     SSL.setTlsExtHostName(this.ssl, name);
                  }

                  this.sniHostNames = sniHostNames;
               }

               if (Java8SslUtils.getUseCipherSuitesOrder(sslParameters)) {
                  SSL.setOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
               } else {
                  SSL.clearOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
               }
            }

            this.matchers = sslParameters.getSNIMatchers();
         }

         String endPointIdentificationAlgorithm = sslParameters.getEndpointIdentificationAlgorithm();
         boolean endPointVerificationEnabled = endPointIdentificationAlgorithm != null && !endPointIdentificationAlgorithm.isEmpty();
         SSL.setHostNameValidation(this.ssl, 0, endPointVerificationEnabled ? this.getPeerHost() : null);
         if (this.clientMode && endPointVerificationEnabled) {
            SSL.setVerify(this.ssl, 2, -1);
         }

         this.endPointIdentificationAlgorithm = endPointIdentificationAlgorithm;
         this.algorithmConstraints = sslParameters.getAlgorithmConstraints();
      }

      super.setSSLParameters(sslParameters);
   }

   private boolean isDestroyed() {
      return this.destroyed != 0;
   }

   static int calculateOutNetBufSize(int pendingBytes, int numComponents) {
      return (int)Math.min(16474L, (long)pendingBytes + 90L * (long)numComponents);
   }

   final boolean checkSniHostnameMatch(String hostname) {
      return Java8SslUtils.checkSniHostnameMatch(this.matchers, hostname);
   }

   static {
      NEED_UNWRAP_OK = new SSLEngineResult(Status.OK, HandshakeStatus.NEED_UNWRAP, 0, 0);
      NEED_UNWRAP_CLOSED = new SSLEngineResult(Status.CLOSED, HandshakeStatus.NEED_UNWRAP, 0, 0);
      NEED_WRAP_OK = new SSLEngineResult(Status.OK, HandshakeStatus.NEED_WRAP, 0, 0);
      NEED_WRAP_CLOSED = new SSLEngineResult(Status.CLOSED, HandshakeStatus.NEED_WRAP, 0, 0);
      CLOSED_NOT_HANDSHAKING = new SSLEngineResult(Status.CLOSED, HandshakeStatus.NOT_HANDSHAKING, 0, 0);
   }

   private final class OpenSslSession implements SSLSession, ApplicationProtocolAccessor {
      private final OpenSslSessionContext sessionContext;
      private X509Certificate[] x509PeerCerts;
      private Certificate[] peerCerts;
      private String protocol;
      private String applicationProtocol;
      private String cipher;
      private byte[] id;
      private long creationTime;
      private Map values;

      OpenSslSession(OpenSslSessionContext sessionContext) {
         this.sessionContext = sessionContext;
      }

      public byte[] getId() {
         synchronized(ReferenceCountedOpenSslEngine.this) {
            return this.id == null ? EmptyArrays.EMPTY_BYTES : (byte[])this.id.clone();
         }
      }

      public SSLSessionContext getSessionContext() {
         return this.sessionContext;
      }

      public long getCreationTime() {
         synchronized(ReferenceCountedOpenSslEngine.this) {
            if (this.creationTime == 0L && !ReferenceCountedOpenSslEngine.this.isDestroyed()) {
               this.creationTime = SSL.getTime(ReferenceCountedOpenSslEngine.this.ssl) * 1000L;
            }
         }

         return this.creationTime;
      }

      public long getLastAccessedTime() {
         long lastAccessed = ReferenceCountedOpenSslEngine.this.lastAccessed;
         return lastAccessed == -1L ? this.getCreationTime() : lastAccessed;
      }

      public void invalidate() {
         synchronized(ReferenceCountedOpenSslEngine.this) {
            if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
               SSL.setTimeout(ReferenceCountedOpenSslEngine.this.ssl, 0L);
            }

         }
      }

      public boolean isValid() {
         synchronized(ReferenceCountedOpenSslEngine.this) {
            if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
               return System.currentTimeMillis() - SSL.getTimeout(ReferenceCountedOpenSslEngine.this.ssl) * 1000L < SSL.getTime(ReferenceCountedOpenSslEngine.this.ssl) * 1000L;
            } else {
               return false;
            }
         }
      }

      public void putValue(String name, Object value) {
         if (name == null) {
            throw new NullPointerException("name");
         } else if (value == null) {
            throw new NullPointerException("value");
         } else {
            Map values = this.values;
            if (values == null) {
               values = this.values = new HashMap(2);
            }

            Object old = values.put(name, value);
            if (value instanceof SSLSessionBindingListener) {
               ((SSLSessionBindingListener)value).valueBound(new SSLSessionBindingEvent(this, name));
            }

            this.notifyUnbound(old, name);
         }
      }

      public Object getValue(String name) {
         if (name == null) {
            throw new NullPointerException("name");
         } else {
            return this.values == null ? null : this.values.get(name);
         }
      }

      public void removeValue(String name) {
         if (name == null) {
            throw new NullPointerException("name");
         } else {
            Map values = this.values;
            if (values != null) {
               Object old = values.remove(name);
               this.notifyUnbound(old, name);
            }
         }
      }

      public String[] getValueNames() {
         Map values = this.values;
         return values != null && !values.isEmpty() ? (String[])values.keySet().toArray(new String[values.size()]) : EmptyArrays.EMPTY_STRINGS;
      }

      private void notifyUnbound(Object value, String name) {
         if (value instanceof SSLSessionBindingListener) {
            ((SSLSessionBindingListener)value).valueUnbound(new SSLSessionBindingEvent(this, name));
         }

      }

      void handshakeFinished() throws SSLException {
         synchronized(ReferenceCountedOpenSslEngine.this) {
            if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
               this.id = SSL.getSessionId(ReferenceCountedOpenSslEngine.this.ssl);
               this.cipher = ReferenceCountedOpenSslEngine.this.toJavaCipherSuite(SSL.getCipherForSSL(ReferenceCountedOpenSslEngine.this.ssl));
               this.protocol = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
               this.initPeerCerts();
               this.selectApplicationProtocol();
               ReferenceCountedOpenSslEngine.this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.FINISHED;
            } else {
               throw new SSLException("Already closed");
            }
         }
      }

      private void initPeerCerts() {
         byte[][] chain = SSL.getPeerCertChain(ReferenceCountedOpenSslEngine.this.ssl);
         if (ReferenceCountedOpenSslEngine.this.clientMode) {
            if (ReferenceCountedOpenSslEngine.isEmpty((Object[])chain)) {
               this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
               this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
            } else {
               this.peerCerts = new Certificate[chain.length];
               this.x509PeerCerts = new X509Certificate[chain.length];
               this.initCerts(chain, 0);
            }
         } else {
            byte[] clientCert = SSL.getPeerCertificate(ReferenceCountedOpenSslEngine.this.ssl);
            if (ReferenceCountedOpenSslEngine.isEmpty(clientCert)) {
               this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
               this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
            } else if (ReferenceCountedOpenSslEngine.isEmpty((Object[])chain)) {
               this.peerCerts = new Certificate[]{new OpenSslX509Certificate(clientCert)};
               this.x509PeerCerts = new X509Certificate[]{new OpenSslJavaxX509Certificate(clientCert)};
            } else {
               this.peerCerts = new Certificate[chain.length + 1];
               this.x509PeerCerts = new X509Certificate[chain.length + 1];
               this.peerCerts[0] = new OpenSslX509Certificate(clientCert);
               this.x509PeerCerts[0] = new OpenSslJavaxX509Certificate(clientCert);
               this.initCerts(chain, 1);
            }
         }

      }

      private void initCerts(byte[][] chain, int startPos) {
         for(int i = 0; i < chain.length; ++i) {
            int certPos = startPos + i;
            this.peerCerts[certPos] = new OpenSslX509Certificate(chain[i]);
            this.x509PeerCerts[certPos] = new OpenSslJavaxX509Certificate(chain[i]);
         }

      }

      private void selectApplicationProtocol() throws SSLException {
         ApplicationProtocolConfig.SelectedListenerFailureBehavior behavior = ReferenceCountedOpenSslEngine.this.apn.selectedListenerFailureBehavior();
         List protocols = ReferenceCountedOpenSslEngine.this.apn.protocols();
         String applicationProtocol;
         switch (ReferenceCountedOpenSslEngine.this.apn.protocol()) {
            case NONE:
               break;
            case ALPN:
               applicationProtocol = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
               if (applicationProtocol != null) {
                  this.applicationProtocol = this.selectApplicationProtocol(protocols, behavior, applicationProtocol);
               }
               break;
            case NPN:
               applicationProtocol = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
               if (applicationProtocol != null) {
                  this.applicationProtocol = this.selectApplicationProtocol(protocols, behavior, applicationProtocol);
               }
               break;
            case NPN_AND_ALPN:
               applicationProtocol = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
               if (applicationProtocol == null) {
                  applicationProtocol = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
               }

               if (applicationProtocol != null) {
                  this.applicationProtocol = this.selectApplicationProtocol(protocols, behavior, applicationProtocol);
               }
               break;
            default:
               throw new Error();
         }

      }

      private String selectApplicationProtocol(List protocols, ApplicationProtocolConfig.SelectedListenerFailureBehavior behavior, String applicationProtocol) throws SSLException {
         if (behavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT) {
            return applicationProtocol;
         } else {
            int size = protocols.size();

            assert size > 0;

            if (protocols.contains(applicationProtocol)) {
               return applicationProtocol;
            } else if (behavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL) {
               return (String)protocols.get(size - 1);
            } else {
               throw new SSLException("unknown protocol " + applicationProtocol);
            }
         }
      }

      public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
         synchronized(ReferenceCountedOpenSslEngine.this) {
            if (ReferenceCountedOpenSslEngine.isEmpty((Object[])this.peerCerts)) {
               throw new SSLPeerUnverifiedException("peer not verified");
            } else {
               return (Certificate[])this.peerCerts.clone();
            }
         }
      }

      public Certificate[] getLocalCertificates() {
         return ReferenceCountedOpenSslEngine.this.localCerts == null ? null : (Certificate[])ReferenceCountedOpenSslEngine.this.localCerts.clone();
      }

      public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
         synchronized(ReferenceCountedOpenSslEngine.this) {
            if (ReferenceCountedOpenSslEngine.isEmpty((Object[])this.x509PeerCerts)) {
               throw new SSLPeerUnverifiedException("peer not verified");
            } else {
               return (X509Certificate[])this.x509PeerCerts.clone();
            }
         }
      }

      public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
         Certificate[] peer = this.getPeerCertificates();
         return ((java.security.cert.X509Certificate)peer[0]).getSubjectX500Principal();
      }

      public Principal getLocalPrincipal() {
         Certificate[] local = ReferenceCountedOpenSslEngine.this.localCerts;
         return local != null && local.length != 0 ? ((java.security.cert.X509Certificate)local[0]).getIssuerX500Principal() : null;
      }

      public String getCipherSuite() {
         synchronized(ReferenceCountedOpenSslEngine.this) {
            return this.cipher == null ? "SSL_NULL_WITH_NULL_NULL" : this.cipher;
         }
      }

      public String getProtocol() {
         String protocol = this.protocol;
         if (protocol == null) {
            synchronized(ReferenceCountedOpenSslEngine.this) {
               if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                  protocol = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
               } else {
                  protocol = "";
               }
            }
         }

         return protocol;
      }

      public String getApplicationProtocol() {
         synchronized(ReferenceCountedOpenSslEngine.this) {
            return this.applicationProtocol;
         }
      }

      public String getPeerHost() {
         return ReferenceCountedOpenSslEngine.this.getPeerHost();
      }

      public int getPeerPort() {
         return ReferenceCountedOpenSslEngine.this.getPeerPort();
      }

      public int getPacketBufferSize() {
         return 16474;
      }

      public int getApplicationBufferSize() {
         return 16384;
      }
   }

   private static enum HandshakeState {
      NOT_STARTED,
      STARTED_IMPLICITLY,
      STARTED_EXPLICITLY,
      FINISHED;
   }
}
