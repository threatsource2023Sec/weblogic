package com.bea.core.repackaged.springframework.core.io.buffer;

import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

public abstract class DataBufferUtils {
   private static final Consumer RELEASE_CONSUMER = DataBufferUtils::release;

   public static Flux readInputStream(Callable inputStreamSupplier, DataBufferFactory bufferFactory, int bufferSize) {
      Assert.notNull(inputStreamSupplier, (String)"'inputStreamSupplier' must not be null");
      return readByteChannel(() -> {
         return Channels.newChannel((InputStream)inputStreamSupplier.call());
      }, bufferFactory, bufferSize);
   }

   public static Flux readByteChannel(Callable channelSupplier, DataBufferFactory bufferFactory, int bufferSize) {
      Assert.notNull(channelSupplier, (String)"'channelSupplier' must not be null");
      Assert.notNull(bufferFactory, (String)"'dataBufferFactory' must not be null");
      Assert.isTrue(bufferSize > 0, "'bufferSize' must be > 0");
      return Flux.using(channelSupplier, (channel) -> {
         return Flux.generate(new ReadableByteChannelGenerator(channel, bufferFactory, bufferSize));
      }, DataBufferUtils::closeChannel);
   }

   public static Flux readAsynchronousFileChannel(Callable channelSupplier, DataBufferFactory bufferFactory, int bufferSize) {
      return readAsynchronousFileChannel(channelSupplier, 0L, bufferFactory, bufferSize);
   }

   public static Flux readAsynchronousFileChannel(Callable channelSupplier, long position, DataBufferFactory bufferFactory, int bufferSize) {
      Assert.notNull(channelSupplier, (String)"'channelSupplier' must not be null");
      Assert.notNull(bufferFactory, (String)"'dataBufferFactory' must not be null");
      Assert.isTrue(position >= 0L, "'position' must be >= 0");
      Assert.isTrue(bufferSize > 0, "'bufferSize' must be > 0");
      Flux flux = Flux.using(channelSupplier, (channel) -> {
         return Flux.create((sink) -> {
            ReadCompletionHandler handler = new ReadCompletionHandler(channel, sink, position, bufferFactory, bufferSize);
            sink.onCancel(handler::cancel);
            sink.onRequest(handler::request);
         });
      }, (channel) -> {
      });
      return flux.doOnDiscard(PooledDataBuffer.class, DataBufferUtils::release);
   }

   public static Flux read(Resource resource, DataBufferFactory bufferFactory, int bufferSize) {
      return read(resource, 0L, bufferFactory, bufferSize);
   }

   public static Flux read(Resource resource, long position, DataBufferFactory bufferFactory, int bufferSize) {
      try {
         if (resource.isFile()) {
            File file = resource.getFile();
            return readAsynchronousFileChannel(() -> {
               return AsynchronousFileChannel.open(file.toPath(), StandardOpenOption.READ);
            }, position, bufferFactory, bufferSize);
         }
      } catch (IOException var6) {
      }

      Flux result = readByteChannel(resource::readableChannel, bufferFactory, bufferSize);
      return position == 0L ? result : skipUntilByteCount(result, position);
   }

   public static Flux write(Publisher source, OutputStream outputStream) {
      Assert.notNull(source, (String)"'source' must not be null");
      Assert.notNull(outputStream, (String)"'outputStream' must not be null");
      WritableByteChannel channel = Channels.newChannel(outputStream);
      return write(source, channel);
   }

   public static Flux write(Publisher source, WritableByteChannel channel) {
      Assert.notNull(source, (String)"'source' must not be null");
      Assert.notNull(channel, (String)"'channel' must not be null");
      Flux flux = Flux.from(source);
      return Flux.create((sink) -> {
         WritableByteChannelSubscriber subscriber = new WritableByteChannelSubscriber(sink, channel);
         sink.onDispose(subscriber);
         flux.subscribe(subscriber);
      });
   }

   public static Flux write(Publisher source, AsynchronousFileChannel channel) {
      return write(source, channel, 0L);
   }

   public static Flux write(Publisher source, AsynchronousFileChannel channel, long position) {
      Assert.notNull(source, (String)"'source' must not be null");
      Assert.notNull(channel, (String)"'channel' must not be null");
      Assert.isTrue(position >= 0L, "'position' must be >= 0");
      Flux flux = Flux.from(source);
      return Flux.create((sink) -> {
         WriteCompletionHandler handler = new WriteCompletionHandler(sink, channel, position);
         sink.onDispose(handler);
         flux.subscribe(handler);
      });
   }

   static void closeChannel(@Nullable Channel channel) {
      if (channel != null && channel.isOpen()) {
         try {
            channel.close();
         } catch (IOException var2) {
         }
      }

   }

   public static Flux takeUntilByteCount(Publisher publisher, long maxByteCount) {
      Assert.notNull(publisher, (String)"Publisher must not be null");
      Assert.isTrue(maxByteCount >= 0L, "'maxByteCount' must be a positive number");
      AtomicLong countDown = new AtomicLong(maxByteCount);
      return Flux.from(publisher).map((buffer) -> {
         long remainder = countDown.addAndGet((long)(-buffer.readableByteCount()));
         if (remainder < 0L) {
            int length = buffer.readableByteCount() + (int)remainder;
            return buffer.slice(0, length);
         } else {
            return buffer;
         }
      }).takeUntil((buffer) -> {
         return countDown.get() <= 0L;
      });
   }

   public static Flux skipUntilByteCount(Publisher publisher, long maxByteCount) {
      Assert.notNull(publisher, (String)"Publisher must not be null");
      Assert.isTrue(maxByteCount >= 0L, "'maxByteCount' must be a positive number");
      return Flux.defer(() -> {
         AtomicLong countDown = new AtomicLong(maxByteCount);
         return Flux.from(publisher).skipUntil((buffer) -> {
            long remainder = countDown.addAndGet((long)(-buffer.readableByteCount()));
            return remainder < 0L;
         }).map((buffer) -> {
            long remainder = countDown.get();
            if (remainder < 0L) {
               countDown.set(0L);
               int start = buffer.readableByteCount() + (int)remainder;
               int length = (int)(-remainder);
               return buffer.slice(start, length);
            } else {
               return buffer;
            }
         });
      }).doOnDiscard(PooledDataBuffer.class, DataBufferUtils::release);
   }

   public static DataBuffer retain(DataBuffer dataBuffer) {
      return (DataBuffer)(dataBuffer instanceof PooledDataBuffer ? ((PooledDataBuffer)dataBuffer).retain() : dataBuffer);
   }

   public static boolean release(@Nullable DataBuffer dataBuffer) {
      if (dataBuffer instanceof PooledDataBuffer) {
         PooledDataBuffer pooledDataBuffer = (PooledDataBuffer)dataBuffer;
         if (pooledDataBuffer.isAllocated()) {
            return pooledDataBuffer.release();
         }
      }

      return false;
   }

   public static Consumer releaseConsumer() {
      return RELEASE_CONSUMER;
   }

   public static Mono join(Publisher dataBuffers) {
      return join(dataBuffers, -1);
   }

   public static Mono join(Publisher buffers, int maxByteCount) {
      Assert.notNull(buffers, (String)"'dataBuffers' must not be null");
      return buffers instanceof Mono ? (Mono)buffers : Flux.from(buffers).collect(() -> {
         return new LimitedDataBufferList(maxByteCount);
      }, LimitedDataBufferList::add).filter((list) -> {
         return !list.isEmpty();
      }).map((list) -> {
         return ((DataBuffer)list.get(0)).factory().join(list);
      }).doOnDiscard(LimitedDataBufferList.class, LimitedDataBufferList::releaseAndClear).doOnDiscard(PooledDataBuffer.class, DataBufferUtils::release);
   }

   private static class WriteCompletionHandler extends BaseSubscriber implements CompletionHandler {
      private final FluxSink sink;
      private final AsynchronousFileChannel channel;
      private final AtomicBoolean completed = new AtomicBoolean();
      private final AtomicReference error = new AtomicReference();
      private final AtomicLong position;
      private final AtomicReference dataBuffer = new AtomicReference();

      public WriteCompletionHandler(FluxSink sink, AsynchronousFileChannel channel, long position) {
         this.sink = sink;
         this.channel = channel;
         this.position = new AtomicLong(position);
      }

      protected void hookOnSubscribe(Subscription subscription) {
         this.request(1L);
      }

      protected void hookOnNext(DataBuffer value) {
         if (!this.dataBuffer.compareAndSet((Object)null, value)) {
            throw new IllegalStateException();
         } else {
            ByteBuffer byteBuffer = value.asByteBuffer();
            this.channel.write(byteBuffer, this.position.get(), byteBuffer, this);
         }
      }

      protected void hookOnError(Throwable throwable) {
         this.error.set(throwable);
         if (this.dataBuffer.get() == null) {
            this.sink.error(throwable);
         }

      }

      protected void hookOnComplete() {
         this.completed.set(true);
         if (this.dataBuffer.get() == null) {
            this.sink.complete();
         }

      }

      public void completed(Integer written, ByteBuffer byteBuffer) {
         long pos = this.position.addAndGet((long)written);
         if (byteBuffer.hasRemaining()) {
            this.channel.write(byteBuffer, pos, byteBuffer, this);
         } else {
            this.sinkDataBuffer();
            Throwable throwable = (Throwable)this.error.get();
            if (throwable != null) {
               this.sink.error(throwable);
            } else if (this.completed.get()) {
               this.sink.complete();
            } else {
               this.request(1L);
            }

         }
      }

      public void failed(Throwable exc, ByteBuffer byteBuffer) {
         this.sinkDataBuffer();
         this.sink.error(exc);
      }

      private void sinkDataBuffer() {
         DataBuffer dataBuffer = (DataBuffer)this.dataBuffer.get();
         Assert.state(dataBuffer != null, "DataBuffer should not be null");
         this.sink.next(dataBuffer);
         this.dataBuffer.set((Object)null);
      }
   }

   private static class WritableByteChannelSubscriber extends BaseSubscriber {
      private final FluxSink sink;
      private final WritableByteChannel channel;

      public WritableByteChannelSubscriber(FluxSink sink, WritableByteChannel channel) {
         this.sink = sink;
         this.channel = channel;
      }

      protected void hookOnSubscribe(Subscription subscription) {
         this.request(1L);
      }

      protected void hookOnNext(DataBuffer dataBuffer) {
         try {
            ByteBuffer byteBuffer = dataBuffer.asByteBuffer();

            while(byteBuffer.hasRemaining()) {
               this.channel.write(byteBuffer);
            }

            this.sink.next(dataBuffer);
            this.request(1L);
         } catch (IOException var3) {
            this.sink.next(dataBuffer);
            this.sink.error(var3);
         }

      }

      protected void hookOnError(Throwable throwable) {
         this.sink.error(throwable);
      }

      protected void hookOnComplete() {
         this.sink.complete();
      }
   }

   private static class ReadCompletionHandler implements CompletionHandler {
      private final AsynchronousFileChannel channel;
      private final FluxSink sink;
      private final DataBufferFactory dataBufferFactory;
      private final int bufferSize;
      private final AtomicLong position;
      private final AtomicBoolean reading = new AtomicBoolean();
      private final AtomicBoolean disposed = new AtomicBoolean();

      public ReadCompletionHandler(AsynchronousFileChannel channel, FluxSink sink, long position, DataBufferFactory dataBufferFactory, int bufferSize) {
         this.channel = channel;
         this.sink = sink;
         this.position = new AtomicLong(position);
         this.dataBufferFactory = dataBufferFactory;
         this.bufferSize = bufferSize;
      }

      public void read() {
         if (this.sink.requestedFromDownstream() > 0L && this.isNotDisposed() && this.reading.compareAndSet(false, true)) {
            DataBuffer dataBuffer = this.dataBufferFactory.allocateBuffer(this.bufferSize);
            ByteBuffer byteBuffer = dataBuffer.asByteBuffer(0, this.bufferSize);
            this.channel.read(byteBuffer, this.position.get(), dataBuffer, this);
         }

      }

      public void completed(Integer read, DataBuffer dataBuffer) {
         if (this.isNotDisposed()) {
            if (read != -1) {
               this.position.addAndGet((long)read);
               dataBuffer.writePosition(read);
               this.sink.next(dataBuffer);
               this.reading.set(false);
               this.read();
            } else {
               DataBufferUtils.release(dataBuffer);
               DataBufferUtils.closeChannel(this.channel);
               if (this.disposed.compareAndSet(false, true)) {
                  this.sink.complete();
               }

               this.reading.set(false);
            }
         } else {
            DataBufferUtils.release(dataBuffer);
            DataBufferUtils.closeChannel(this.channel);
            this.reading.set(false);
         }

      }

      public void failed(Throwable exc, DataBuffer dataBuffer) {
         DataBufferUtils.release(dataBuffer);
         DataBufferUtils.closeChannel(this.channel);
         if (this.disposed.compareAndSet(false, true)) {
            this.sink.error(exc);
         }

         this.reading.set(false);
      }

      public void request(long n) {
         this.read();
      }

      public void cancel() {
         if (this.disposed.compareAndSet(false, true) && !this.reading.get()) {
            DataBufferUtils.closeChannel(this.channel);
         }

      }

      private boolean isNotDisposed() {
         return !this.disposed.get();
      }
   }

   private static class ReadableByteChannelGenerator implements Consumer {
      private final ReadableByteChannel channel;
      private final DataBufferFactory dataBufferFactory;
      private final int bufferSize;

      public ReadableByteChannelGenerator(ReadableByteChannel channel, DataBufferFactory dataBufferFactory, int bufferSize) {
         this.channel = channel;
         this.dataBufferFactory = dataBufferFactory;
         this.bufferSize = bufferSize;
      }

      public void accept(SynchronousSink sink) {
         boolean release = true;
         DataBuffer dataBuffer = this.dataBufferFactory.allocateBuffer(this.bufferSize);

         try {
            ByteBuffer byteBuffer = dataBuffer.asByteBuffer(0, dataBuffer.capacity());
            int read;
            if ((read = this.channel.read(byteBuffer)) >= 0) {
               dataBuffer.writePosition(read);
               release = false;
               sink.next(dataBuffer);
            } else {
               sink.complete();
            }
         } catch (IOException var9) {
            sink.error(var9);
         } finally {
            if (release) {
               DataBufferUtils.release(dataBuffer);
            }

         }

      }
   }
}
