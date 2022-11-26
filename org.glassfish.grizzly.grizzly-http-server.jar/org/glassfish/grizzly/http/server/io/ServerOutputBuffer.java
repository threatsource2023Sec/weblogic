package org.glassfish.grizzly.http.server.io;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.io.OutputBuffer;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.localization.LogMessages;

public class ServerOutputBuffer extends OutputBuffer {
   private Response serverResponse;

   public void initialize(Response response, FilterChainContext ctx) {
      super.initialize(response.getResponse(), response.isSendFileEnabled(), ctx);
      this.serverResponse = response;
   }

   public void sendfile(File file, long offset, long length, CompletionHandler handler) {
      if (!this.sendfileEnabled) {
         throw new IllegalStateException("sendfile support isn't available.");
      } else {
         boolean suspendedAtStart = this.serverResponse.isSuspended();
         CompletionHandler ch;
         if (suspendedAtStart && handler != null) {
            ch = handler;
         } else if (!suspendedAtStart && handler != null) {
            ch = this.suspendAndCreateHandler(handler);
         } else {
            ch = this.createInternalCompletionHandler(file, suspendedAtStart);
         }

         super.sendfile(file, offset, length, ch);
      }
   }

   public void recycle() {
      this.serverResponse = null;
      super.recycle();
   }

   protected Executor getThreadPool() {
      return this.serverResponse.getRequest().getRequestExecutor();
   }

   private CompletionHandler createInternalCompletionHandler(final File file, boolean suspendedAtStart) {
      if (!suspendedAtStart) {
         this.serverResponse.suspend();
      }

      CompletionHandler ch = new CompletionHandler() {
         public void cancelled() {
            if (ServerOutputBuffer.LOGGER.isLoggable(Level.WARNING)) {
               ServerOutputBuffer.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_SERVEROUTPUTBUFFER_FILE_TRANSFER_CANCELLED(file.getAbsolutePath()));
            }

            ServerOutputBuffer.this.serverResponse.resume();
         }

         public void failed(Throwable throwable) {
            if (ServerOutputBuffer.LOGGER.isLoggable(Level.SEVERE)) {
               ServerOutputBuffer.LOGGER.log(Level.SEVERE, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_SERVEROUTPUTBUFFER_FILE_TRANSFER_FAILED(file.getAbsolutePath(), throwable.getMessage()), throwable);
            }

            ServerOutputBuffer.this.serverResponse.resume();
         }

         public void completed(WriteResult result) {
            ServerOutputBuffer.this.serverResponse.resume();
         }

         public void updated(WriteResult result) {
         }
      };
      return ch;
   }

   private CompletionHandler suspendAndCreateHandler(final CompletionHandler handler) {
      this.serverResponse.suspend();
      return new CompletionHandler() {
         public void cancelled() {
            handler.cancelled();
            ServerOutputBuffer.this.serverResponse.resume();
         }

         public void failed(Throwable throwable) {
            handler.failed(throwable);
            ServerOutputBuffer.this.serverResponse.resume();
         }

         public void completed(WriteResult result) {
            handler.completed(result);
            ServerOutputBuffer.this.serverResponse.resume();
         }

         public void updated(WriteResult result) {
            handler.updated(result);
         }
      };
   }
}
