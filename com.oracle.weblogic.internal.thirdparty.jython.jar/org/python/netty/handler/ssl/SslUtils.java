package org.python.netty.handler.ssl;

import java.nio.ByteBuffer;
import javax.net.ssl.SSLHandshakeException;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.base64.Base64;
import org.python.netty.handler.codec.base64.Base64Dialect;

final class SslUtils {
   static final int SSL_CONTENT_TYPE_CHANGE_CIPHER_SPEC = 20;
   static final int SSL_CONTENT_TYPE_ALERT = 21;
   static final int SSL_CONTENT_TYPE_HANDSHAKE = 22;
   static final int SSL_CONTENT_TYPE_APPLICATION_DATA = 23;
   static final int SSL_CONTENT_TYPE_EXTENSION_HEARTBEAT = 24;
   static final int SSL_RECORD_HEADER_LENGTH = 5;
   static final int NOT_ENOUGH_DATA = -1;
   static final int NOT_ENCRYPTED = -2;

   static SSLHandshakeException toSSLHandshakeException(Throwable e) {
      return e instanceof SSLHandshakeException ? (SSLHandshakeException)e : (SSLHandshakeException)(new SSLHandshakeException(e.getMessage())).initCause(e);
   }

   static int getEncryptedPacketLength(ByteBuf buffer, int offset) {
      int packetLength = 0;
      boolean tls;
      switch (buffer.getUnsignedByte(offset)) {
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
            tls = true;
            break;
         default:
            tls = false;
      }

      int headerLength;
      if (tls) {
         headerLength = buffer.getUnsignedByte(offset + 1);
         if (headerLength == 3) {
            packetLength = buffer.getUnsignedShort(offset + 3) + 5;
            if (packetLength <= 5) {
               tls = false;
            }
         } else {
            tls = false;
         }
      }

      if (!tls) {
         headerLength = (buffer.getUnsignedByte(offset) & 128) != 0 ? 2 : 3;
         int majorVersion = buffer.getUnsignedByte(offset + headerLength + 1);
         if (majorVersion != 2 && majorVersion != 3) {
            return -2;
         }

         if (headerLength == 2) {
            packetLength = (buffer.getShort(offset) & 32767) + 2;
         } else {
            packetLength = (buffer.getShort(offset) & 16383) + 3;
         }

         if (packetLength <= headerLength) {
            return -1;
         }
      }

      return packetLength;
   }

   private static short unsignedByte(byte b) {
      return (short)(b & 255);
   }

   private static int unsignedShort(short s) {
      return s & '\uffff';
   }

   static int getEncryptedPacketLength(ByteBuffer[] buffers, int offset) {
      ByteBuffer buffer = buffers[offset];
      if (buffer.remaining() >= 5) {
         return getEncryptedPacketLength(buffer);
      } else {
         ByteBuffer tmp = ByteBuffer.allocate(5);

         do {
            buffer = buffers[offset++].duplicate();
            if (buffer.remaining() > tmp.remaining()) {
               buffer.limit(buffer.position() + tmp.remaining());
            }

            tmp.put(buffer);
         } while(tmp.hasRemaining());

         tmp.flip();
         return getEncryptedPacketLength(tmp);
      }
   }

   private static int getEncryptedPacketLength(ByteBuffer var0) {
      // $FF: Couldn't be decompiled
   }

   static void notifyHandshakeFailure(ChannelHandlerContext ctx, Throwable cause) {
      ctx.flush();
      ctx.fireUserEventTriggered(new SslHandshakeCompletionEvent(cause));
      ctx.close();
   }

   static void zeroout(ByteBuf buffer) {
      if (!buffer.isReadOnly()) {
         buffer.setZero(0, buffer.capacity());
      }

   }

   static void zerooutAndRelease(ByteBuf buffer) {
      zeroout(buffer);
      buffer.release();
   }

   static ByteBuf toBase64(ByteBufAllocator allocator, ByteBuf src) {
      ByteBuf dst = Base64.encode(src, src.readerIndex(), src.readableBytes(), true, Base64Dialect.STANDARD, allocator);
      src.readerIndex(src.writerIndex());
      return dst;
   }

   private SslUtils() {
   }
}
