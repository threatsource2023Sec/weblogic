package org.glassfish.grizzly.http.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.OutputSink;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.HttpContext;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.filecache.FileCache;
import org.glassfish.grizzly.http.server.filecache.FileCacheEntry;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.memory.Buffers;

public class FileCacheFilter extends BaseFilter {
   private static final Logger LOGGER = Grizzly.logger(FileCacheFilter.class);
   private final FileCache fileCache;

   public FileCacheFilter(FileCache fileCache) {
      this.fileCache = fileCache;
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      HttpContent requestContent = (HttpContent)ctx.getMessage();
      HttpRequestPacket request = (HttpRequestPacket)requestContent.getHttpHeader();
      if (this.fileCache.isEnabled() && Method.GET.equals(request.getMethod())) {
         FileCacheEntry cacheEntry = this.fileCache.get(request);
         if (cacheEntry != null) {
            HttpResponsePacket response = request.getResponse();
            this.prepareResponse(cacheEntry, response);
            if (response.getStatus() != 200) {
               ctx.write(HttpContent.builder(response).content(Buffers.EMPTY_BUFFER).last(true).build());
               return this.flush(ctx);
            }

            boolean isServeCompressed = cacheEntry.canServeCompressed(request);
            this.prepareResponseWithPayload(cacheEntry, response, isServeCompressed);
            if (cacheEntry.type != FileCache.CacheType.FILE) {
               Buffer buffer = Buffers.wrap(ctx.getMemoryManager(), cacheEntry.getByteBuffer(isServeCompressed).duplicate());
               ctx.write(HttpContent.builder(response).content(buffer).last(true).build());
               return this.flush(ctx);
            }

            return this.fileCache.isFileSendEnabled() && !request.isSecure() ? this.sendFileZeroCopy(ctx, response, cacheEntry, isServeCompressed) : this.sendFileUsingBuffers(ctx, response, cacheEntry, isServeCompressed);
         }
      }

      return ctx.getInvokeAction();
   }

   public FileCache getFileCache() {
      return this.fileCache;
   }

   private void prepareResponse(FileCacheEntry entry, HttpResponsePacket response) throws IOException {
      response.setContentType(entry.contentType.prepare());
      if (entry.server != null) {
         response.addHeader(Header.Server, entry.server);
      }

   }

   private void prepareResponseWithPayload(FileCacheEntry entry, HttpResponsePacket response, boolean isServeCompressed) throws IOException {
      response.addHeader(Header.ETag, entry.Etag);
      response.addHeader(Header.LastModified, entry.lastModifiedHeader);
      response.setContentLengthLong(entry.getFileSize(isServeCompressed));
      if (isServeCompressed) {
         response.addHeader(Header.ContentEncoding, "gzip");
      }

   }

   private NextAction sendFileUsingBuffers(FilterChainContext ctx, HttpResponsePacket response, FileCacheEntry cacheEntry, boolean isServeCompressed) {
      try {
         FileSendEntry sendEntry = FileCacheFilter.FileSendEntry.create(ctx, response, cacheEntry.getFile(isServeCompressed), cacheEntry.getFileSize(isServeCompressed));
         ctx.suspend();
         sendEntry.send();
         return ctx.getSuspendAction();
      } catch (IOException var6) {
         return ctx.getInvokeAction();
      }
   }

   private NextAction sendFileZeroCopy(FilterChainContext ctx, HttpResponsePacket response, final FileCacheEntry cacheEntry, boolean isServeCompressed) {
      ctx.write(response);
      FileTransfer f = new FileTransfer(cacheEntry.getFile(isServeCompressed), 0L, cacheEntry.getFileSize(isServeCompressed));
      ctx.write(f, new EmptyCompletionHandler() {
         public void failed(Throwable throwable) {
            FileCacheFilter.LOGGER.log(Level.FINE, "Error reported during file-send entry: " + cacheEntry, throwable);
         }
      });
      return this.flush(ctx);
   }

   private NextAction flush(final FilterChainContext ctx) {
      HttpContext httpContext = HttpContext.get(ctx);

      assert httpContext != null;

      OutputSink output = httpContext.getOutputSink();
      if (output.canWrite()) {
         return ctx.getStopAction();
      } else {
         NextAction suspendAction = ctx.getSuspendAction();
         ctx.suspend();
         output.notifyCanWrite(new WriteHandler() {
            public void onWritePossible() throws Exception {
               this.finish();
            }

            public void onError(Throwable t) {
               this.finish();
            }

            private void finish() {
               ctx.completeAndRecycle();
            }
         });
         return suspendAction;
      }
   }

   private static class FileSendEntry implements WriteHandler {
      private final FilterChainContext ctx;
      private final FileChannel fc;
      private final FileInputStream fis;
      private final HttpResponsePacket response;
      private final OutputSink output;
      private long remaining;

      public static FileSendEntry create(FilterChainContext ctx, HttpResponsePacket response, File file, long size) throws IOException {
         FileInputStream fis = new FileInputStream(file);
         FileChannel fc = fis.getChannel();
         return new FileSendEntry(ctx, response, fis, fc, size);
      }

      public FileSendEntry(FilterChainContext ctx, HttpResponsePacket response, FileInputStream fis, FileChannel fc, long size) {
         this.ctx = ctx;
         this.response = response;
         this.fis = fis;
         this.fc = fc;
         this.remaining = size;
         HttpContext httpContext = response.getProcessingState().getHttpContext();

         assert httpContext != null;

         this.output = httpContext.getOutputSink();
      }

      public void close() {
         try {
            this.fis.close();
         } catch (IOException var2) {
         }

      }

      private void send() {
         int chunkSize = true;

         try {
            boolean isLast;
            do {
               Buffer buffer = this.ctx.getMemoryManager().allocate(8192);
               buffer.allowBufferDispose(true);
               long readNow = Buffers.readFromFileChannel(this.fc, buffer);
               isLast = readNow <= 0L || (this.remaining -= readNow) <= 0L;
               buffer.trim();
               this.ctx.write(HttpContent.builder(this.response).content(buffer).last(isLast).build());
            } while(!isLast && this.output.canWrite());

            if (isLast) {
               this.done();
            } else {
               this.output.notifyCanWrite(this);
            }
         } catch (IOException var6) {
            this.done();
         }

      }

      private void done() {
         this.close();
         this.ctx.resume(this.ctx.getStopAction());
      }

      public void onWritePossible() throws Exception {
         this.send();
      }

      public void onError(Throwable t) {
         this.done();
      }
   }
}
