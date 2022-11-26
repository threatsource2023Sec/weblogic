package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferFactory;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.MimeType;
import com.bea.core.repackaged.springframework.util.MimeTypeUtils;
import java.util.Map;
import reactor.core.publisher.Flux;

public class ResourceEncoder extends AbstractSingleValueEncoder {
   public static final int DEFAULT_BUFFER_SIZE = 4096;
   private final int bufferSize;

   public ResourceEncoder() {
      this(4096);
   }

   public ResourceEncoder(int bufferSize) {
      super(MimeTypeUtils.APPLICATION_OCTET_STREAM, MimeTypeUtils.ALL);
      Assert.isTrue(bufferSize > 0, "'bufferSize' must be larger than 0");
      this.bufferSize = bufferSize;
   }

   public boolean canEncode(ResolvableType elementType, @Nullable MimeType mimeType) {
      Class clazz = elementType.toClass();
      return super.canEncode(elementType, mimeType) && Resource.class.isAssignableFrom(clazz);
   }

   protected Flux encode(Resource resource, DataBufferFactory bufferFactory, ResolvableType type, @Nullable MimeType mimeType, @Nullable Map hints) {
      if (this.logger.isDebugEnabled() && !Hints.isLoggingSuppressed(hints)) {
         String logPrefix = Hints.getLogPrefix(hints);
         this.logger.debug(logPrefix + "Writing [" + resource + "]");
      }

      return DataBufferUtils.read(resource, bufferFactory, this.bufferSize);
   }
}
