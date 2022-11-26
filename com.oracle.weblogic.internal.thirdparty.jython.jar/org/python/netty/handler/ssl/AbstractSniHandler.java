package org.python.netty.handler.ssl;

import java.net.IDN;
import java.net.SocketAddress;
import java.util.List;
import java.util.Locale;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufUtil;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelOutboundHandler;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.handler.codec.ByteToMessageDecoder;
import org.python.netty.handler.codec.DecoderException;
import org.python.netty.util.CharsetUtil;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.FutureListener;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public abstract class AbstractSniHandler extends ByteToMessageDecoder implements ChannelOutboundHandler {
   private static final int MAX_SSL_RECORDS = 4;
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractSniHandler.class);
   private boolean handshakeFailed;
   private boolean suppressRead;
   private boolean readPending;

   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      if (!this.suppressRead && !this.handshakeFailed) {
         int writerIndex = in.writerIndex();

         try {
            int i = 0;

            label99:
            while(i < 4) {
               int readerIndex = in.readerIndex();
               int readableBytes = writerIndex - readerIndex;
               if (readableBytes < 5) {
                  return;
               }

               int command = in.getUnsignedByte(readerIndex);
               switch (command) {
                  case 20:
                  case 21:
                     int len = SslUtils.getEncryptedPacketLength(in, readerIndex);
                     if (len == -2) {
                        this.handshakeFailed = true;
                        NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(in));
                        in.skipBytes(in.readableBytes());
                        SslUtils.notifyHandshakeFailure(ctx, e);
                        throw e;
                     }

                     if (len != -1 && writerIndex - readerIndex - 5 >= len) {
                        in.skipBytes(len);
                        ++i;
                        break;
                     }

                     return;
                  case 22:
                     int majorVersion = in.getUnsignedByte(readerIndex + 1);
                     if (majorVersion == 3) {
                        int packetLength = in.getUnsignedShort(readerIndex + 3) + 5;
                        if (readableBytes < packetLength) {
                           return;
                        }

                        int endOffset = readerIndex + packetLength;
                        int offset = readerIndex + 43;
                        if (endOffset - offset >= 6) {
                           int sessionIdLength = in.getUnsignedByte(offset);
                           offset += sessionIdLength + 1;
                           int cipherSuitesLength = in.getUnsignedShort(offset);
                           offset += cipherSuitesLength + 2;
                           int compressionMethodLength = in.getUnsignedByte(offset);
                           offset += compressionMethodLength + 1;
                           int extensionsLength = in.getUnsignedShort(offset);
                           offset += 2;
                           int extensionsLimit = offset + extensionsLength;
                           if (extensionsLimit <= endOffset) {
                              while(extensionsLimit - offset >= 4) {
                                 int extensionType = in.getUnsignedShort(offset);
                                 offset += 2;
                                 int extensionLength = in.getUnsignedShort(offset);
                                 offset += 2;
                                 if (extensionsLimit - offset < extensionLength) {
                                    break label99;
                                 }

                                 if (extensionType == 0) {
                                    offset += 2;
                                    if (extensionsLimit - offset >= 3) {
                                       int serverNameType = in.getUnsignedByte(offset);
                                       ++offset;
                                       if (serverNameType == 0) {
                                          int serverNameLength = in.getUnsignedShort(offset);
                                          offset += 2;
                                          if (extensionsLimit - offset >= serverNameLength) {
                                             String hostname = in.toString(offset, serverNameLength, CharsetUtil.UTF_8);

                                             try {
                                                this.select(ctx, IDN.toASCII(hostname, 1).toLowerCase(Locale.US));
                                             } catch (Throwable var25) {
                                                PlatformDependent.throwException(var25);
                                             }

                                             return;
                                          }
                                       }
                                    }
                                    break label99;
                                 }

                                 offset += extensionLength;
                              }
                           }
                        }
                     }
                  default:
                     break label99;
               }
            }
         } catch (Throwable var26) {
            if (logger.isDebugEnabled()) {
               logger.debug("Unexpected client hello packet: " + ByteBufUtil.hexDump(in), var26);
            }
         }

         this.select(ctx, (String)null);
      }

   }

   private void select(final ChannelHandlerContext ctx, final String hostname) throws Exception {
      Future future = this.lookup(ctx, hostname);
      if (future.isDone()) {
         this.onLookupComplete(ctx, hostname, future);
      } else {
         this.suppressRead = true;
         future.addListener(new FutureListener() {
            public void operationComplete(Future future) throws Exception {
               try {
                  AbstractSniHandler.this.suppressRead = false;

                  try {
                     AbstractSniHandler.this.onLookupComplete(ctx, hostname, future);
                  } catch (DecoderException var7) {
                     ctx.fireExceptionCaught(var7);
                  } catch (Throwable var8) {
                     ctx.fireExceptionCaught(new DecoderException(var8));
                  }
               } finally {
                  if (AbstractSniHandler.this.readPending) {
                     AbstractSniHandler.this.readPending = false;
                     ctx.read();
                  }

               }

            }
         });
      }

   }

   protected abstract Future lookup(ChannelHandlerContext var1, String var2) throws Exception;

   protected abstract void onLookupComplete(ChannelHandlerContext var1, String var2, Future var3) throws Exception;

   public void read(ChannelHandlerContext ctx) throws Exception {
      if (this.suppressRead) {
         this.readPending = true;
      } else {
         ctx.read();
      }

   }

   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      ctx.bind(localAddress, promise);
   }

   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      ctx.connect(remoteAddress, localAddress, promise);
   }

   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      ctx.disconnect(promise);
   }

   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      ctx.close(promise);
   }

   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      ctx.deregister(promise);
   }

   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      ctx.write(msg, promise);
   }

   public void flush(ChannelHandlerContext ctx) throws Exception {
      ctx.flush();
   }
}
