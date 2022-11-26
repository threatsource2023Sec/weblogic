package org.glassfish.grizzly.http.server.io;

import java.io.IOException;
import java.util.concurrent.Executor;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.HttpBrokenContent;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.io.InputBuffer;
import org.glassfish.grizzly.http.server.Request;

public class ServerInputBuffer extends InputBuffer {
   private volatile long totalReadContentInBytes;
   private volatile Request serverRequest;

   public void initialize(Request serverRequest, FilterChainContext ctx) {
      this.serverRequest = serverRequest;
      super.initialize(serverRequest.getRequest(), ctx);
   }

   public void initiateAsyncronousDataReceiving() {
      if (!this.checkChunkedMaxPostSize()) {
         HttpContent brokenContent = HttpBrokenContent.builder(this.serverRequest.getRequest()).error(new IOException("The HTTP request content exceeds max post size")).build();

         try {
            this.append(brokenContent);
         } catch (IOException var3) {
         }

      } else {
         super.initiateAsyncronousDataReceiving();
      }
   }

   protected HttpContent blockingRead() throws IOException {
      if (!this.checkChunkedMaxPostSize()) {
         throw new IOException("The HTTP request content exceeds max post size");
      } else {
         return super.blockingRead();
      }
   }

   protected void updateInputContentBuffer(Buffer buffer) {
      this.totalReadContentInBytes += (long)buffer.remaining();
      super.updateInputContentBuffer(buffer);
   }

   public void recycle() {
      this.serverRequest = null;
      this.totalReadContentInBytes = 0L;
      super.recycle();
   }

   protected Executor getThreadPool() {
      return this.serverRequest.getRequestExecutor();
   }

   private boolean checkChunkedMaxPostSize() {
      if (!this.serverRequest.getRequest().isChunked()) {
         return true;
      } else {
         long maxPostSize = this.serverRequest.getHttpFilter().getConfiguration().getMaxPostSize();
         return maxPostSize < 0L || maxPostSize > this.totalReadContentInBytes;
      }
   }
}
