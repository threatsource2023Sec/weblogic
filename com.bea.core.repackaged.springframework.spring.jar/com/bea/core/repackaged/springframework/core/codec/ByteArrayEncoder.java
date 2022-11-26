package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBuffer;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MimeType;
import com.bea.core.repackaged.springframework.util.MimeTypeUtils;
import java.util.Map;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public class ByteArrayEncoder extends AbstractEncoder {
   public ByteArrayEncoder() {
      super(MimeTypeUtils.ALL);
   }

   public boolean canEncode(ResolvableType elementType, @Nullable MimeType mimeType) {
      Class clazz = elementType.toClass();
      return super.canEncode(elementType, mimeType) && byte[].class.isAssignableFrom(clazz);
   }

   public Flux encode(Publisher inputStream, DataBufferFactory bufferFactory, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      return Flux.from(inputStream).map((bytes) -> {
         DataBuffer dataBuffer = bufferFactory.wrap(bytes);
         if (this.logger.isDebugEnabled() && !Hints.isLoggingSuppressed(hints)) {
            String logPrefix = Hints.getLogPrefix(hints);
            this.logger.debug(logPrefix + "Writing " + dataBuffer.readableByteCount() + " bytes");
         }

         return dataBuffer;
      });
   }
}
