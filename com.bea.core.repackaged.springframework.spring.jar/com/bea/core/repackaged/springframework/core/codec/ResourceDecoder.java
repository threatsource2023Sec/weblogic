package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.io.ByteArrayResource;
import com.bea.core.repackaged.springframework.core.io.InputStreamResource;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBuffer;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MimeType;
import com.bea.core.repackaged.springframework.util.MimeTypeUtils;
import java.io.ByteArrayInputStream;
import java.util.Map;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public class ResourceDecoder extends AbstractDataBufferDecoder {
   public ResourceDecoder() {
      super(MimeTypeUtils.ALL);
   }

   public boolean canDecode(ResolvableType elementType, @Nullable MimeType mimeType) {
      return Resource.class.isAssignableFrom(elementType.toClass()) && super.canDecode(elementType, mimeType);
   }

   public Flux decode(Publisher inputStream, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      return Flux.from(this.decodeToMono(inputStream, elementType, mimeType, hints));
   }

   protected Resource decodeDataBuffer(DataBuffer dataBuffer, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      byte[] bytes = new byte[dataBuffer.readableByteCount()];
      dataBuffer.read(bytes);
      DataBufferUtils.release(dataBuffer);
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(Hints.getLogPrefix(hints) + "Read " + bytes.length + " bytes");
      }

      Class clazz = elementType.toClass();
      if (clazz == InputStreamResource.class) {
         return new InputStreamResource(new ByteArrayInputStream(bytes));
      } else if (Resource.class.isAssignableFrom(clazz)) {
         return new ByteArrayResource(bytes);
      } else {
         throw new IllegalStateException("Unsupported resource class: " + clazz);
      }
   }
}
