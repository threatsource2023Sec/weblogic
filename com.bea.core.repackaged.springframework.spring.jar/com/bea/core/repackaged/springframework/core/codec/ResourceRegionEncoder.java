package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.io.InputStreamResource;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBuffer;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferFactory;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferUtils;
import com.bea.core.repackaged.springframework.core.io.support.ResourceRegion;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.MimeType;
import com.bea.core.repackaged.springframework.util.MimeTypeUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.OptionalLong;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ResourceRegionEncoder extends AbstractEncoder {
   public static final int DEFAULT_BUFFER_SIZE = 4096;
   public static final String BOUNDARY_STRING_HINT = ResourceRegionEncoder.class.getName() + ".boundaryString";
   private final int bufferSize;

   public ResourceRegionEncoder() {
      this(4096);
   }

   public ResourceRegionEncoder(int bufferSize) {
      super(MimeTypeUtils.APPLICATION_OCTET_STREAM, MimeTypeUtils.ALL);
      Assert.isTrue(bufferSize > 0, "'bufferSize' must be larger than 0");
      this.bufferSize = bufferSize;
   }

   public boolean canEncode(ResolvableType elementType, @Nullable MimeType mimeType) {
      return super.canEncode(elementType, mimeType) && ResourceRegion.class.isAssignableFrom(elementType.toClass());
   }

   public Flux encode(Publisher input, DataBufferFactory bufferFactory, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      Assert.notNull(input, (String)"'inputStream' must not be null");
      Assert.notNull(bufferFactory, (String)"'bufferFactory' must not be null");
      Assert.notNull(elementType, (String)"'elementType' must not be null");
      if (input instanceof Mono) {
         return Mono.from(input).flatMapMany((region) -> {
            return !region.getResource().isReadable() ? Flux.error(new EncodingException("Resource " + region.getResource() + " is not readable")) : this.writeResourceRegion(region, bufferFactory, hints);
         });
      } else {
         String boundaryString = (String)Hints.getRequiredHint(hints, BOUNDARY_STRING_HINT);
         byte[] startBoundary = this.toAsciiBytes("\r\n--" + boundaryString + "\r\n");
         byte[] contentType = mimeType != null ? this.toAsciiBytes("Content-Type: " + mimeType + "\r\n") : new byte[0];
         return Flux.from(input).concatMap((region) -> {
            if (!region.getResource().isReadable()) {
               return Flux.error(new EncodingException("Resource " + region.getResource() + " is not readable"));
            } else {
               Flux prefix = Flux.just(new DataBuffer[]{bufferFactory.wrap(startBoundary), bufferFactory.wrap(contentType), bufferFactory.wrap(this.getContentRangeHeader(region))});
               return prefix.concatWith(this.writeResourceRegion(region, bufferFactory, hints));
            }
         }).concatWithValues(new DataBuffer[]{this.getRegionSuffix(bufferFactory, boundaryString)});
      }
   }

   private Flux writeResourceRegion(ResourceRegion region, DataBufferFactory bufferFactory, @Nullable Map hints) {
      Resource resource = region.getResource();
      long position = region.getPosition();
      long count = region.getCount();
      if (this.logger.isDebugEnabled() && !Hints.isLoggingSuppressed(hints)) {
         this.logger.debug(Hints.getLogPrefix(hints) + "Writing region " + position + "-" + (position + count) + " of [" + resource + "]");
      }

      Flux in = DataBufferUtils.read(resource, position, bufferFactory, this.bufferSize);
      return DataBufferUtils.takeUntilByteCount(in, count);
   }

   private DataBuffer getRegionSuffix(DataBufferFactory bufferFactory, String boundaryString) {
      byte[] endBoundary = this.toAsciiBytes("\r\n--" + boundaryString + "--");
      return bufferFactory.wrap(endBoundary);
   }

   private byte[] toAsciiBytes(String in) {
      return in.getBytes(StandardCharsets.US_ASCII);
   }

   private byte[] getContentRangeHeader(ResourceRegion region) {
      long start = region.getPosition();
      long end = start + region.getCount() - 1L;
      OptionalLong contentLength = this.contentLength(region.getResource());
      if (contentLength.isPresent()) {
         long length = contentLength.getAsLong();
         return this.toAsciiBytes("Content-Range: bytes " + start + '-' + end + '/' + length + "\r\n\r\n");
      } else {
         return this.toAsciiBytes("Content-Range: bytes " + start + '-' + end + "\r\n\r\n");
      }
   }

   private OptionalLong contentLength(Resource resource) {
      if (InputStreamResource.class != resource.getClass()) {
         try {
            return OptionalLong.of(resource.contentLength());
         } catch (IOException var3) {
         }
      }

      return OptionalLong.empty();
   }
}
