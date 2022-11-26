package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBuffer;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferFactory;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferUtils;
import com.bea.core.repackaged.springframework.core.log.LogFormatUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MimeType;
import com.bea.core.repackaged.springframework.util.MimeTypeUtils;
import java.nio.charset.Charset;
import java.nio.charset.CoderMalfunctionError;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public final class CharSequenceEncoder extends AbstractEncoder {
   public static final Charset DEFAULT_CHARSET;
   private final ConcurrentMap charsetToMaxBytesPerChar = new ConcurrentHashMap(3);

   private CharSequenceEncoder(MimeType... mimeTypes) {
      super(mimeTypes);
   }

   public boolean canEncode(ResolvableType elementType, @Nullable MimeType mimeType) {
      Class clazz = elementType.toClass();
      return super.canEncode(elementType, mimeType) && CharSequence.class.isAssignableFrom(clazz);
   }

   public Flux encode(Publisher inputStream, DataBufferFactory bufferFactory, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      Charset charset = this.getCharset(mimeType);
      return Flux.from(inputStream).map((charSequence) -> {
         if (!Hints.isLoggingSuppressed(hints)) {
            LogFormatUtils.traceDebug(this.logger, (traceOn) -> {
               String formatted = LogFormatUtils.formatValue(charSequence, !traceOn);
               return Hints.getLogPrefix(hints) + "Writing " + formatted;
            });
         }

         boolean release = true;
         int capacity = this.calculateCapacity(charSequence, charset);
         DataBuffer dataBuffer = bufferFactory.allocateBuffer(capacity);

         try {
            dataBuffer.write(charSequence, charset);
            release = false;
         } catch (CoderMalfunctionError var12) {
            throw new EncodingException("String encoding error: " + var12.getMessage(), var12);
         } finally {
            if (release) {
               DataBufferUtils.release(dataBuffer);
            }

         }

         return dataBuffer;
      });
   }

   int calculateCapacity(CharSequence sequence, Charset charset) {
      float maxBytesPerChar = (Float)this.charsetToMaxBytesPerChar.computeIfAbsent(charset, (cs) -> {
         return cs.newEncoder().maxBytesPerChar();
      });
      float maxBytesForSequence = (float)sequence.length() * maxBytesPerChar;
      return (int)Math.ceil((double)maxBytesForSequence);
   }

   private Charset getCharset(@Nullable MimeType mimeType) {
      return mimeType != null && mimeType.getCharset() != null ? mimeType.getCharset() : DEFAULT_CHARSET;
   }

   public static CharSequenceEncoder textPlainOnly() {
      return new CharSequenceEncoder(new MimeType[]{new MimeType("text", "plain", DEFAULT_CHARSET)});
   }

   public static CharSequenceEncoder allMimeTypes() {
      return new CharSequenceEncoder(new MimeType[]{new MimeType("text", "plain", DEFAULT_CHARSET), MimeTypeUtils.ALL});
   }

   static {
      DEFAULT_CHARSET = StandardCharsets.UTF_8;
   }
}
