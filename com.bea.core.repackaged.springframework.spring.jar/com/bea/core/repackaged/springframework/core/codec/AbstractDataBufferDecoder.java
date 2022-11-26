package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBuffer;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MimeType;
import java.util.Map;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractDataBufferDecoder extends AbstractDecoder {
   private int maxInMemorySize = -1;

   protected AbstractDataBufferDecoder(MimeType... supportedMimeTypes) {
      super(supportedMimeTypes);
   }

   public void setMaxInMemorySize(int byteCount) {
      this.maxInMemorySize = byteCount;
   }

   public int getMaxInMemorySize() {
      return this.maxInMemorySize;
   }

   public Flux decode(Publisher input, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      return Flux.from(input).map((buffer) -> {
         return this.decodeDataBuffer(buffer, elementType, mimeType, hints);
      });
   }

   public Mono decodeToMono(Publisher input, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      return DataBufferUtils.join(input, this.maxInMemorySize).map((buffer) -> {
         return this.decodeDataBuffer(buffer, elementType, mimeType, hints);
      });
   }

   protected abstract Object decodeDataBuffer(DataBuffer var1, ResolvableType var2, @Nullable MimeType var3, @Nullable Map var4);
}
