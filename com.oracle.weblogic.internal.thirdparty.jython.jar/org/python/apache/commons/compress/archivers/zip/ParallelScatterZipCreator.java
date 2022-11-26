package org.python.apache.commons.compress.archivers.zip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.python.apache.commons.compress.parallel.FileBasedScatterGatherBackingStore;
import org.python.apache.commons.compress.parallel.InputStreamSupplier;
import org.python.apache.commons.compress.parallel.ScatterGatherBackingStore;
import org.python.apache.commons.compress.parallel.ScatterGatherBackingStoreSupplier;

public class ParallelScatterZipCreator {
   private final List streams;
   private final ExecutorService es;
   private final ScatterGatherBackingStoreSupplier backingStoreSupplier;
   private final List futures;
   private final long startedAt;
   private long compressionDoneAt;
   private long scatterDoneAt;
   private final ThreadLocal tlScatterStreams;

   private ScatterZipOutputStream createDeferred(ScatterGatherBackingStoreSupplier scatterGatherBackingStoreSupplier) throws IOException {
      ScatterGatherBackingStore bs = scatterGatherBackingStoreSupplier.get();
      StreamCompressor sc = StreamCompressor.create(-1, bs);
      return new ScatterZipOutputStream(bs, sc);
   }

   public ParallelScatterZipCreator() {
      this(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
   }

   public ParallelScatterZipCreator(ExecutorService executorService) {
      this(executorService, new DefaultBackingStoreSupplier());
   }

   public ParallelScatterZipCreator(ExecutorService executorService, ScatterGatherBackingStoreSupplier backingStoreSupplier) {
      this.streams = Collections.synchronizedList(new ArrayList());
      this.futures = new ArrayList();
      this.startedAt = System.currentTimeMillis();
      this.compressionDoneAt = 0L;
      this.tlScatterStreams = new ThreadLocal() {
         protected ScatterZipOutputStream initialValue() {
            try {
               ScatterZipOutputStream scatterStream = ParallelScatterZipCreator.this.createDeferred(ParallelScatterZipCreator.this.backingStoreSupplier);
               ParallelScatterZipCreator.this.streams.add(scatterStream);
               return scatterStream;
            } catch (IOException var2) {
               throw new RuntimeException(var2);
            }
         }
      };
      this.backingStoreSupplier = backingStoreSupplier;
      this.es = executorService;
   }

   public void addArchiveEntry(ZipArchiveEntry zipArchiveEntry, InputStreamSupplier source) {
      this.submit(this.createCallable(zipArchiveEntry, source));
   }

   public void addArchiveEntry(ZipArchiveEntryRequestSupplier zipArchiveEntryRequestSupplier) {
      this.submit(this.createCallable(zipArchiveEntryRequestSupplier));
   }

   public final void submit(Callable callable) {
      this.futures.add(this.es.submit(callable));
   }

   public final Callable createCallable(ZipArchiveEntry zipArchiveEntry, InputStreamSupplier source) {
      int method = zipArchiveEntry.getMethod();
      if (method == -1) {
         throw new IllegalArgumentException("Method must be set on zipArchiveEntry: " + zipArchiveEntry);
      } else {
         final ZipArchiveEntryRequest zipArchiveEntryRequest = ZipArchiveEntryRequest.createZipArchiveEntryRequest(zipArchiveEntry, source);
         return new Callable() {
            public Object call() throws Exception {
               ((ScatterZipOutputStream)ParallelScatterZipCreator.this.tlScatterStreams.get()).addArchiveEntry(zipArchiveEntryRequest);
               return null;
            }
         };
      }
   }

   public final Callable createCallable(final ZipArchiveEntryRequestSupplier zipArchiveEntryRequestSupplier) {
      return new Callable() {
         public Object call() throws Exception {
            ((ScatterZipOutputStream)ParallelScatterZipCreator.this.tlScatterStreams.get()).addArchiveEntry(zipArchiveEntryRequestSupplier.get());
            return null;
         }
      };
   }

   public void writeTo(ZipArchiveOutputStream targetStream) throws IOException, InterruptedException, ExecutionException {
      Iterator var2 = this.futures.iterator();

      while(var2.hasNext()) {
         Future future = (Future)var2.next();
         future.get();
      }

      this.es.shutdown();
      this.es.awaitTermination(60000L, TimeUnit.SECONDS);
      this.compressionDoneAt = System.currentTimeMillis();
      var2 = this.streams.iterator();

      while(var2.hasNext()) {
         ScatterZipOutputStream scatterStream = (ScatterZipOutputStream)var2.next();
         scatterStream.writeTo(targetStream);
         scatterStream.close();
      }

      this.scatterDoneAt = System.currentTimeMillis();
   }

   public ScatterStatistics getStatisticsMessage() {
      return new ScatterStatistics(this.compressionDoneAt - this.startedAt, this.scatterDoneAt - this.compressionDoneAt);
   }

   private static class DefaultBackingStoreSupplier implements ScatterGatherBackingStoreSupplier {
      final AtomicInteger storeNum;

      private DefaultBackingStoreSupplier() {
         this.storeNum = new AtomicInteger(0);
      }

      public ScatterGatherBackingStore get() throws IOException {
         File tempFile = File.createTempFile("parallelscatter", "n" + this.storeNum.incrementAndGet());
         return new FileBasedScatterGatherBackingStore(tempFile);
      }

      // $FF: synthetic method
      DefaultBackingStoreSupplier(Object x0) {
         this();
      }
   }
}
