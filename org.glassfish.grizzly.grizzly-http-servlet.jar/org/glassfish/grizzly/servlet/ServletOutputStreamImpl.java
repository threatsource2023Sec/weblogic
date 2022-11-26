package org.glassfish.grizzly.servlet;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.http.io.NIOOutputStream;
import org.glassfish.grizzly.localization.LogMessages;

public class ServletOutputStreamImpl extends ServletOutputStream {
   private final HttpServletResponseImpl servletResponse;
   private NIOOutputStream outputStream;
   private WriteHandler writeHandler = null;
   private boolean hasSetWriteListener = false;
   private boolean prevIsReady = true;
   private static final ThreadLocal CAN_WRITE_SCOPE = new ThreadLocal();

   protected ServletOutputStreamImpl(HttpServletResponseImpl servletResponse) {
      this.servletResponse = servletResponse;
   }

   protected void initialize() throws IOException {
      this.outputStream = this.servletResponse.getResponse().createOutputStream();
   }

   public void write(int i) throws IOException {
      if (!this.prevIsReady) {
         throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_NON_BLOCKING_ERROR());
      } else {
         this.outputStream.write(i);
      }
   }

   public void write(byte[] b) throws IOException {
      this.write(b, 0, b.length);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      if (!this.prevIsReady) {
         throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_NON_BLOCKING_ERROR());
      } else {
         this.outputStream.write(b, off, len);
      }
   }

   public void flush() throws IOException {
      if (!this.prevIsReady) {
         throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_NON_BLOCKING_ERROR());
      } else {
         this.outputStream.flush();
      }
   }

   public void close() throws IOException {
      if (!this.prevIsReady) {
         throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_NON_BLOCKING_ERROR());
      } else {
         this.outputStream.close();
      }
   }

   public boolean isReady() {
      if (!this.hasSetWriteListener) {
         throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_OUTPUTSTREAM_ISREADY_ERROR());
      } else if (!this.prevIsReady) {
         return false;
      } else {
         boolean result = this.outputStream.canWrite();
         if (!result) {
            if (this.hasSetWriteListener) {
               this.prevIsReady = false;
               CAN_WRITE_SCOPE.set(Boolean.TRUE);

               try {
                  this.outputStream.notifyCanWrite(this.writeHandler);
               } finally {
                  CAN_WRITE_SCOPE.remove();
               }
            } else {
               this.prevIsReady = true;
            }
         }

         return result;
      }
   }

   public void setWriteListener(WriteListener writeListener) {
      if (this.hasSetWriteListener) {
         throw new IllegalStateException("The WriteListener has already been set.");
      } else {
         HttpServletRequestImpl req = this.servletResponse.servletRequest;
         if (!req.isAsyncStarted() && !req.isUpgrade()) {
            throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_OUTPUTSTREAM_SETWRITELISTENER_ERROR());
         } else {
            this.writeHandler = new WriteHandlerImpl(writeListener);
            this.hasSetWriteListener = true;
         }
      }
   }

   void recycle() {
      this.outputStream = null;
      this.writeHandler = null;
      this.prevIsReady = true;
      this.hasSetWriteListener = false;
   }

   class WriteHandlerImpl implements WriteHandler {
      private WriteListener writeListener;

      private WriteHandlerImpl(WriteListener listener) {
         this.writeListener = null;
         this.writeListener = listener;
      }

      public void onWritePossible() throws Exception {
         if (!Boolean.TRUE.equals(ServletOutputStreamImpl.CAN_WRITE_SCOPE.get())) {
            this.invokeWriteCallback();
         } else {
            AsyncContextImpl.pool.execute(new Runnable() {
               public void run() {
                  WriteHandlerImpl.this.invokeWriteCallback();
               }
            });
         }

      }

      public void onError(final Throwable t) {
         if (!Boolean.TRUE.equals(ServletOutputStreamImpl.CAN_WRITE_SCOPE.get())) {
            this.writeListener.onError(t);
         } else {
            AsyncContextImpl.pool.execute(new Runnable() {
               public void run() {
                  WriteHandlerImpl.this.writeListener.onError(t);
               }
            });
         }

      }

      private void invokeWriteCallback() {
         ServletOutputStreamImpl.this.prevIsReady = true;

         try {
            this.writeListener.onWritePossible();
         } catch (Throwable var2) {
            this.writeListener.onError(var2);
         }

      }

      // $FF: synthetic method
      WriteHandlerImpl(WriteListener x1, Object x2) {
         this(x1);
      }
   }
}
