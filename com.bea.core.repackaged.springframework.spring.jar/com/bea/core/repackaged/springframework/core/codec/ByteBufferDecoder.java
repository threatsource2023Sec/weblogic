package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBuffer;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MimeType;
import com.bea.core.repackaged.springframework.util.MimeTypeUtils;
import java.nio.ByteBuffer;
import java.util.Map;

public class ByteBufferDecoder extends AbstractDataBufferDecoder {
   public ByteBufferDecoder() {
      super(MimeTypeUtils.ALL);
   }

   public boolean canDecode(ResolvableType elementType, @Nullable MimeType mimeType) {
      return ByteBuffer.class.isAssignableFrom(elementType.toClass()) && super.canDecode(elementType, mimeType);
   }

   protected ByteBuffer decodeDataBuffer(DataBuffer dataBuffer, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      int byteCount = dataBuffer.readableByteCount();
      ByteBuffer copy = ByteBuffer.allocate(byteCount);
      copy.put(dataBuffer.asByteBuffer());
      copy.flip();
      DataBufferUtils.release(dataBuffer);
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(Hints.getLogPrefix(hints) + "Read " + byteCount + " bytes");
      }

      return copy;
   }
}
