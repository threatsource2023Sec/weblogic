package org.python.netty.handler.codec.compression;

import java.nio.ByteBuffer;
import org.python.netty.buffer.ByteBuf;

final class CompressionUtil {
   private CompressionUtil() {
   }

   static void checkChecksum(ByteBufChecksum checksum, ByteBuf uncompressed, int currentChecksum) {
      checksum.reset();
      checksum.update(uncompressed, uncompressed.readerIndex(), uncompressed.readableBytes());
      int checksumResult = (int)checksum.getValue();
      if (checksumResult != currentChecksum) {
         throw new DecompressionException(String.format("stream corrupted: mismatching checksum: %d (expected: %d)", checksumResult, currentChecksum));
      }
   }

   static ByteBuffer safeNioBuffer(ByteBuf buffer) {
      return buffer.nioBufferCount() == 1 ? buffer.internalNioBuffer(buffer.readerIndex(), buffer.readableBytes()) : buffer.nioBuffer();
   }
}
