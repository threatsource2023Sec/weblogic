package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBuffer;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferLimitException;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferUtils;
import com.bea.core.repackaged.springframework.core.io.buffer.DefaultDataBufferFactory;
import com.bea.core.repackaged.springframework.core.io.buffer.LimitedDataBufferList;
import com.bea.core.repackaged.springframework.core.io.buffer.PooledDataBuffer;
import com.bea.core.repackaged.springframework.core.log.LogFormatUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.MimeType;
import com.bea.core.repackaged.springframework.util.MimeTypeUtils;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public final class StringDecoder extends AbstractDataBufferDecoder {
   private static final DataBuffer END_FRAME = (new DefaultDataBufferFactory()).wrap(new byte[0]);
   public static final Charset DEFAULT_CHARSET;
   public static final List DEFAULT_DELIMITERS;
   private final List delimiters;
   private final boolean stripDelimiter;
   private final ConcurrentMap delimitersCache = new ConcurrentHashMap();

   private StringDecoder(List delimiters, boolean stripDelimiter, MimeType... mimeTypes) {
      super(mimeTypes);
      Assert.notEmpty((Collection)delimiters, (String)"'delimiters' must not be empty");
      this.delimiters = new ArrayList(delimiters);
      this.stripDelimiter = stripDelimiter;
   }

   public boolean canDecode(ResolvableType elementType, @Nullable MimeType mimeType) {
      return elementType.resolve() == String.class && super.canDecode(elementType, mimeType);
   }

   public Flux decode(Publisher input, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      List delimiterBytes = this.getDelimiterBytes(mimeType);
      Flux inputFlux = Flux.defer(() -> {
         if (this.getMaxInMemorySize() != -1) {
            LimitedDataBufferList limiter = new LimitedDataBufferList(this.getMaxInMemorySize());
            return Flux.from(input).concatMapIterable((buffer) -> {
               return this.splitOnDelimiter(buffer, delimiterBytes, limiter);
            }).bufferUntil((buffer) -> {
               return buffer == END_FRAME;
            }).map(StringDecoder::joinUntilEndFrame).doOnDiscard(PooledDataBuffer.class, DataBufferUtils::release);
         } else {
            ConcatMapIterableDiscardWorkaroundCache cache = new ConcatMapIterableDiscardWorkaroundCache();
            return Flux.from(input).concatMapIterable((buffer) -> {
               return cache.addAll(this.splitOnDelimiter(buffer, delimiterBytes, (LimitedDataBufferList)null));
            }).doOnNext(cache).doOnCancel(cache).bufferUntil((buffer) -> {
               return buffer == END_FRAME;
            }).map(StringDecoder::joinUntilEndFrame).doOnDiscard(PooledDataBuffer.class, DataBufferUtils::release);
         }
      });
      return super.decode(inputFlux, elementType, mimeType, hints);
   }

   private List getDelimiterBytes(@Nullable MimeType mimeType) {
      return (List)this.delimitersCache.computeIfAbsent(getCharset(mimeType), (charset) -> {
         List list = new ArrayList();
         Iterator var3 = this.delimiters.iterator();

         while(var3.hasNext()) {
            String delimiter = (String)var3.next();
            byte[] bytes = delimiter.getBytes(charset);
            list.add(bytes);
         }

         return list;
      });
   }

   private List splitOnDelimiter(DataBuffer buffer, List delimiterBytes, @Nullable LimitedDataBufferList limiter) {
      List frames = new ArrayList();

      try {
         DataBuffer frame;
         try {
            do {
               int length = Integer.MAX_VALUE;
               byte[] matchingDelimiter = null;
               Iterator var18 = delimiterBytes.iterator();

               while(var18.hasNext()) {
                  byte[] delimiter = (byte[])var18.next();
                  int index = indexOf(buffer, delimiter);
                  if (index >= 0 && index < length) {
                     length = index;
                     matchingDelimiter = delimiter;
                  }
               }

               int readPosition = buffer.readPosition();
               if (matchingDelimiter != null) {
                  frame = this.stripDelimiter ? buffer.slice(readPosition, length) : buffer.slice(readPosition, length + matchingDelimiter.length);
                  buffer.readPosition(readPosition + length + matchingDelimiter.length);
                  frames.add(DataBufferUtils.retain(frame));
                  frames.add(END_FRAME);
                  if (limiter != null) {
                     limiter.add(frame);
                     limiter.clear();
                  }
               } else {
                  frame = buffer.slice(readPosition, buffer.readableByteCount());
                  buffer.readPosition(readPosition + buffer.readableByteCount());
                  frames.add(DataBufferUtils.retain(frame));
                  if (limiter != null) {
                     limiter.add(frame);
                  }
               }
            } while(buffer.readableByteCount() > 0);
         } catch (DataBufferLimitException var14) {
            if (limiter != null) {
               limiter.releaseAndClear();
            }

            throw var14;
         } catch (Throwable var15) {
            Iterator var6 = frames.iterator();

            while(var6.hasNext()) {
               frame = (DataBuffer)var6.next();
               DataBufferUtils.release(frame);
            }

            throw var15;
         }
      } finally {
         DataBufferUtils.release(buffer);
      }

      return frames;
   }

   private static int indexOf(DataBuffer buffer, byte[] delimiter) {
      for(int i = buffer.readPosition(); i < buffer.writePosition(); ++i) {
         int bufferPos = i;

         int delimiterPos;
         for(delimiterPos = 0; delimiterPos < delimiter.length && buffer.getByte(bufferPos) == delimiter[delimiterPos]; ++delimiterPos) {
            ++bufferPos;
            boolean endOfBuffer = bufferPos == buffer.writePosition();
            boolean endOfDelimiter = delimiterPos == delimiter.length - 1;
            if (endOfBuffer && !endOfDelimiter) {
               return -1;
            }
         }

         if (delimiterPos == delimiter.length) {
            return i - buffer.readPosition();
         }
      }

      return -1;
   }

   private static DataBuffer joinUntilEndFrame(List dataBuffers) {
      if (!dataBuffers.isEmpty()) {
         int lastIdx = dataBuffers.size() - 1;
         if (dataBuffers.get(lastIdx) == END_FRAME) {
            dataBuffers.remove(lastIdx);
         }
      }

      return ((DataBuffer)dataBuffers.get(0)).factory().join(dataBuffers);
   }

   protected String decodeDataBuffer(DataBuffer dataBuffer, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      Charset charset = getCharset(mimeType);
      CharBuffer charBuffer = charset.decode(dataBuffer.asByteBuffer());
      DataBufferUtils.release(dataBuffer);
      String value = charBuffer.toString();
      LogFormatUtils.traceDebug(this.logger, (traceOn) -> {
         String formatted = LogFormatUtils.formatValue(value, !traceOn);
         return Hints.getLogPrefix(hints) + "Decoded " + formatted;
      });
      return value;
   }

   private static Charset getCharset(@Nullable MimeType mimeType) {
      return mimeType != null && mimeType.getCharset() != null ? mimeType.getCharset() : DEFAULT_CHARSET;
   }

   /** @deprecated */
   @Deprecated
   public static StringDecoder textPlainOnly(boolean ignored) {
      return textPlainOnly();
   }

   public static StringDecoder textPlainOnly() {
      return textPlainOnly(DEFAULT_DELIMITERS, true);
   }

   public static StringDecoder textPlainOnly(List delimiters, boolean stripDelimiter) {
      return new StringDecoder(delimiters, stripDelimiter, new MimeType[]{new MimeType("text", "plain", DEFAULT_CHARSET)});
   }

   /** @deprecated */
   @Deprecated
   public static StringDecoder allMimeTypes(boolean ignored) {
      return allMimeTypes();
   }

   public static StringDecoder allMimeTypes() {
      return allMimeTypes(DEFAULT_DELIMITERS, true);
   }

   public static StringDecoder allMimeTypes(List delimiters, boolean stripDelimiter) {
      return new StringDecoder(delimiters, stripDelimiter, new MimeType[]{new MimeType("text", "plain", DEFAULT_CHARSET), MimeTypeUtils.ALL});
   }

   static {
      DEFAULT_CHARSET = StandardCharsets.UTF_8;
      DEFAULT_DELIMITERS = Arrays.asList("\r\n", "\n");
   }

   private class ConcatMapIterableDiscardWorkaroundCache implements Consumer, Runnable {
      private final List buffers;

      private ConcatMapIterableDiscardWorkaroundCache() {
         this.buffers = new ArrayList();
      }

      public List addAll(List buffersToAdd) {
         this.buffers.addAll(buffersToAdd);
         return buffersToAdd;
      }

      public void accept(DataBuffer dataBuffer) {
         this.buffers.remove(dataBuffer);
      }

      public void run() {
         this.buffers.forEach((buffer) -> {
            try {
               DataBufferUtils.release(buffer);
            } catch (Throwable var2) {
            }

         });
      }

      // $FF: synthetic method
      ConcatMapIterableDiscardWorkaroundCache(Object x1) {
         this();
      }
   }
}
