package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferFactory;
import com.bea.core.repackaged.springframework.core.io.buffer.PooledDataBuffer;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MimeType;
import java.util.Map;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public abstract class AbstractSingleValueEncoder extends AbstractEncoder {
   public AbstractSingleValueEncoder(MimeType... supportedMimeTypes) {
      super(supportedMimeTypes);
   }

   public final Flux encode(Publisher inputStream, DataBufferFactory bufferFactory, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      return Flux.from(inputStream).take(1L).concatMap((value) -> {
         return this.encode(value, bufferFactory, elementType, mimeType, hints);
      }).doOnDiscard(PooledDataBuffer.class, PooledDataBuffer::release);
   }

   protected abstract Flux encode(Object var1, DataBufferFactory var2, ResolvableType var3, @Nullable MimeType var4, @Nullable Map var5);
}
