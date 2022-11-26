package org.glassfish.grizzly.servlet;

import java.io.IOException;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import org.glassfish.grizzly.ReadHandler;
import org.glassfish.grizzly.http.io.NIOInputStream;
import org.glassfish.grizzly.localization.LogMessages;

public class ServletInputStreamImpl extends ServletInputStream {
   private final HttpServletRequestImpl servletRequest;
   private NIOInputStream inputStream;
   private ReadHandler readHandler = null;
   private boolean hasSetReadListener = false;
   private boolean prevIsReady = true;
   private static final ThreadLocal IS_READY_SCOPE = new ThreadLocal();

   protected ServletInputStreamImpl(HttpServletRequestImpl servletRequest) {
      this.servletRequest = servletRequest;
   }

   public void initialize() throws IOException {
      this.inputStream = this.servletRequest.getRequest().createInputStream();
   }

   public int read() throws IOException {
      if (!this.prevIsReady) {
         throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_NON_BLOCKING_ERROR());
      } else {
         return this.inputStream.read();
      }
   }

   public int available() throws IOException {
      return !this.prevIsReady ? 0 : this.inputStream.available();
   }

   public int read(byte[] b) throws IOException {
      if (!this.prevIsReady) {
         throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_NON_BLOCKING_ERROR());
      } else {
         return this.inputStream.read(b, 0, b.length);
      }
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (!this.prevIsReady) {
         throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_NON_BLOCKING_ERROR());
      } else {
         return this.inputStream.read(b, off, len);
      }
   }

   public long skip(long n) throws IOException {
      return this.inputStream.skip(n);
   }

   public void mark(int readlimit) {
      this.inputStream.mark(readlimit);
   }

   public void reset() throws IOException {
      this.inputStream.reset();
   }

   public boolean markSupported() {
      return this.inputStream.markSupported();
   }

   public void close() throws IOException {
      this.inputStream.close();
   }

   void recycle() {
      this.inputStream = null;
      this.prevIsReady = true;
      this.hasSetReadListener = false;
      this.readHandler = null;
   }

   public boolean isFinished() {
      return this.inputStream.isFinished();
   }

   public boolean isReady() {
      if (!this.hasSetReadListener) {
         throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_INPUTSTREAM_ISREADY_ERROR());
      } else if (!this.prevIsReady) {
         return false;
      } else {
         boolean result = this.inputStream.isReady();
         if (!result) {
            if (this.hasSetReadListener) {
               this.prevIsReady = false;
               IS_READY_SCOPE.set(Boolean.TRUE);

               try {
                  this.inputStream.notifyAvailable(this.readHandler);
               } finally {
                  IS_READY_SCOPE.remove();
               }
            } else {
               this.prevIsReady = true;
            }
         }

         return result;
      }
   }

   public void setReadListener(ReadListener readListener) {
      if (this.hasSetReadListener) {
         throw new IllegalStateException("The ReadListener has already been set");
      } else if (!this.servletRequest.isAsyncStarted() && !this.servletRequest.isUpgrade()) {
         throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_INPUTSTREAM_SETREADLISTENER_ERROR());
      } else {
         this.readHandler = new ReadHandlerImpl(readListener);
         this.hasSetReadListener = true;
      }
   }

   class ReadHandlerImpl implements ReadHandler {
      private ReadListener readListener;

      private ReadHandlerImpl(ReadListener listener) {
         this.readListener = null;
         this.readListener = listener;
      }

      public void onDataAvailable() throws Exception {
         if (!Boolean.TRUE.equals(ServletInputStreamImpl.IS_READY_SCOPE.get())) {
            this.invokeReadPossibleCallback();
         } else {
            AsyncContextImpl.pool.execute(new Runnable() {
               public void run() {
                  ReadHandlerImpl.this.invokeReadPossibleCallback();
               }
            });
         }

      }

      public void onAllDataRead() throws Exception {
         if (!Boolean.TRUE.equals(ServletInputStreamImpl.IS_READY_SCOPE.get())) {
            this.invokeAllDataReadCallback();
         } else {
            AsyncContextImpl.pool.execute(new Runnable() {
               public void run() {
                  ReadHandlerImpl.this.invokeAllDataReadCallback();
               }
            });
         }

      }

      public void onError(final Throwable t) {
         if (!Boolean.TRUE.equals(ServletInputStreamImpl.IS_READY_SCOPE.get())) {
            this.readListener.onError(t);
         } else {
            AsyncContextImpl.pool.execute(new Runnable() {
               public void run() {
                  ReadHandlerImpl.this.readListener.onError(t);
               }
            });
         }

      }

      private void invokeReadPossibleCallback() {
         ServletInputStreamImpl.this.prevIsReady = true;

         try {
            this.readListener.onDataAvailable();
         } catch (Throwable var2) {
            this.readListener.onError(var2);
         }

      }

      private void invokeAllDataReadCallback() {
         ServletInputStreamImpl.this.prevIsReady = true;

         try {
            this.readListener.onAllDataRead();
         } catch (Throwable var2) {
            this.readListener.onError(var2);
         }

      }

      // $FF: synthetic method
      ReadHandlerImpl(ReadListener x1, Object x2) {
         this(x1);
      }
   }
}
