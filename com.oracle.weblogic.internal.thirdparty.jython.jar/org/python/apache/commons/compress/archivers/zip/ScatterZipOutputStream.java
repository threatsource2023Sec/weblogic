package org.python.apache.commons.compress.archivers.zip;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.python.apache.commons.compress.parallel.FileBasedScatterGatherBackingStore;
import org.python.apache.commons.compress.parallel.ScatterGatherBackingStore;
import org.python.apache.commons.compress.utils.BoundedInputStream;

public class ScatterZipOutputStream implements Closeable {
   private final Queue items = new ConcurrentLinkedQueue();
   private final ScatterGatherBackingStore backingStore;
   private final StreamCompressor streamCompressor;

   public ScatterZipOutputStream(ScatterGatherBackingStore backingStore, StreamCompressor streamCompressor) {
      this.backingStore = backingStore;
      this.streamCompressor = streamCompressor;
   }

   public void addArchiveEntry(ZipArchiveEntryRequest zipArchiveEntryRequest) throws IOException {
      InputStream payloadStream = zipArchiveEntryRequest.getPayloadStream();
      Throwable var3 = null;

      try {
         this.streamCompressor.deflate(payloadStream, zipArchiveEntryRequest.getMethod());
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (payloadStream != null) {
            if (var3 != null) {
               try {
                  payloadStream.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               payloadStream.close();
            }
         }

      }

      this.items.add(new CompressedEntry(zipArchiveEntryRequest, this.streamCompressor.getCrc32(), this.streamCompressor.getBytesWrittenForLastEntry(), this.streamCompressor.getBytesRead()));
   }

   public void writeTo(ZipArchiveOutputStream target) throws IOException {
      this.backingStore.closeForWriting();
      InputStream data = this.backingStore.getInputStream();
      Throwable var3 = null;

      try {
         Iterator var4 = this.items.iterator();

         while(var4.hasNext()) {
            CompressedEntry compressedEntry = (CompressedEntry)var4.next();
            BoundedInputStream rawStream = new BoundedInputStream(data, compressedEntry.compressedSize);
            Throwable var7 = null;

            try {
               target.addRawArchiveEntry(compressedEntry.transferToArchiveEntry(), rawStream);
            } catch (Throwable var30) {
               var7 = var30;
               throw var30;
            } finally {
               if (rawStream != null) {
                  if (var7 != null) {
                     try {
                        rawStream.close();
                     } catch (Throwable var29) {
                        var7.addSuppressed(var29);
                     }
                  } else {
                     rawStream.close();
                  }
               }

            }
         }
      } catch (Throwable var32) {
         var3 = var32;
         throw var32;
      } finally {
         if (data != null) {
            if (var3 != null) {
               try {
                  data.close();
               } catch (Throwable var28) {
                  var3.addSuppressed(var28);
               }
            } else {
               data.close();
            }
         }

      }

   }

   public void close() throws IOException {
      this.backingStore.close();
      this.streamCompressor.close();
   }

   public static ScatterZipOutputStream fileBased(File file) throws FileNotFoundException {
      return fileBased(file, -1);
   }

   public static ScatterZipOutputStream fileBased(File file, int compressionLevel) throws FileNotFoundException {
      ScatterGatherBackingStore bs = new FileBasedScatterGatherBackingStore(file);
      StreamCompressor sc = StreamCompressor.create(compressionLevel, bs);
      return new ScatterZipOutputStream(bs, sc);
   }

   private static class CompressedEntry {
      final ZipArchiveEntryRequest zipArchiveEntryRequest;
      final long crc;
      final long compressedSize;
      final long size;

      public CompressedEntry(ZipArchiveEntryRequest zipArchiveEntryRequest, long crc, long compressedSize, long size) {
         this.zipArchiveEntryRequest = zipArchiveEntryRequest;
         this.crc = crc;
         this.compressedSize = compressedSize;
         this.size = size;
      }

      public ZipArchiveEntry transferToArchiveEntry() {
         ZipArchiveEntry entry = this.zipArchiveEntryRequest.getZipArchiveEntry();
         entry.setCompressedSize(this.compressedSize);
         entry.setSize(this.size);
         entry.setCrc(this.crc);
         entry.setMethod(this.zipArchiveEntryRequest.getMethod());
         return entry;
      }
   }
}
