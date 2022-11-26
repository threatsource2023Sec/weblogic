package org.glassfish.grizzly.http.server.filecache;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.CompressionConfig;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.util.ContentType;

public final class FileCacheEntry implements Runnable {
   private static final Logger LOGGER = Grizzly.logger(FileCacheEntry.class);
   public FileCacheKey key;
   public String host;
   public String requestURI;
   public long lastModified = -1L;
   public ContentType contentType;
   ByteBuffer bb;
   File plainFile;
   long plainFileSize = -1L;
   private boolean canBeCompressed;
   private AtomicBoolean isCompressed;
   volatile File compressedFile;
   ByteBuffer compressedBb;
   long compressedFileSize = -1L;
   public String xPoweredBy;
   public FileCache.CacheType type;
   public String date;
   public String Etag;
   public String lastModifiedHeader;
   public String server;
   public volatile long timeoutMillis;
   private final FileCache fileCache;

   public FileCacheEntry(FileCache fileCache) {
      this.fileCache = fileCache;
   }

   void setCanBeCompressed(boolean canBeCompressed) {
      this.canBeCompressed = canBeCompressed;
      if (canBeCompressed) {
         this.isCompressed = new AtomicBoolean();
      }

   }

   public boolean canServeCompressed(HttpRequestPacket request) {
      if (this.canBeCompressed && CompressionConfig.isClientSupportCompression(this.fileCache.getCompressionConfig(), request, FileCache.COMPRESSION_ALIASES)) {
         if (this.isCompressed.compareAndSet(false, true)) {
            this.fileCache.compressFile(this);
         }

         return this.compressedFile != null;
      } else {
         return false;
      }
   }

   public long getFileSize(boolean isCompressed) {
      return isCompressed ? this.compressedFileSize : this.plainFileSize;
   }

   public File getFile(boolean isCompressed) {
      return isCompressed ? this.compressedFile : this.plainFile;
   }

   public ByteBuffer getByteBuffer(boolean isCompressed) {
      return isCompressed ? this.compressedBb : this.bb;
   }

   public void run() {
      this.fileCache.remove(this);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("FileCacheEntry");
      sb.append("{host='").append(this.host).append('\'');
      sb.append(", requestURI='").append(this.requestURI).append('\'');
      sb.append(", lastModified=").append(this.lastModified);
      sb.append(", contentType='").append(this.contentType).append('\'');
      sb.append(", type=").append(this.type);
      sb.append(", plainFileSize=").append(this.plainFileSize);
      sb.append(", canBeCompressed=").append(this.canBeCompressed);
      sb.append(", compressedFileSize=").append(this.compressedFileSize);
      sb.append(", timeoutMillis=").append(this.timeoutMillis);
      sb.append(", fileCache=").append(this.fileCache);
      sb.append(", server=").append(this.server);
      sb.append('}');
      return sb.toString();
   }

   protected void finalize() throws Throwable {
      if (this.compressedFile != null && !this.compressedFile.delete()) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Unable to delete file {0}.  Will try to delete again upon VM exit.", this.compressedFile.getCanonicalPath());
         }

         this.compressedFile.deleteOnExit();
      }

      super.finalize();
   }
}
