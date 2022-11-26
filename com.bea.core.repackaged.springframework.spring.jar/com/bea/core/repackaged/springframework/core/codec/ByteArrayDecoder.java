package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBuffer;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MimeType;
import com.bea.core.repackaged.springframework.util.MimeTypeUtils;
import java.util.Map;

public class ByteArrayDecoder extends AbstractDataBufferDecoder {
   public ByteArrayDecoder() {
      super(MimeTypeUtils.ALL);
   }

   public boolean canDecode(ResolvableType elementType, @Nullable MimeType mimeType) {
      return elementType.resolve() == byte[].class && super.canDecode(elementType, mimeType);
   }

   protected byte[] decodeDataBuffer(DataBuffer dataBuffer, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      byte[] result = new byte[dataBuffer.readableByteCount()];
      dataBuffer.read(result);
      DataBufferUtils.release(dataBuffer);
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(Hints.getLogPrefix(hints) + "Read " + result.length + " bytes");
      }

      return result;
   }
}
