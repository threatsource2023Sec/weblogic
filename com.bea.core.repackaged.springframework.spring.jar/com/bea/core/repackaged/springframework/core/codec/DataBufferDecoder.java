package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBuffer;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MimeType;
import com.bea.core.repackaged.springframework.util.MimeTypeUtils;
import java.util.Map;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public class DataBufferDecoder extends AbstractDataBufferDecoder {
   public DataBufferDecoder() {
      super(MimeTypeUtils.ALL);
   }

   public boolean canDecode(ResolvableType elementType, @Nullable MimeType mimeType) {
      return DataBuffer.class.isAssignableFrom(elementType.toClass()) && super.canDecode(elementType, mimeType);
   }

   public Flux decode(Publisher input, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      return Flux.from(input);
   }

   protected DataBuffer decodeDataBuffer(DataBuffer buffer, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(Hints.getLogPrefix(hints) + "Read " + buffer.readableByteCount() + " bytes");
      }

      return buffer;
   }
}
