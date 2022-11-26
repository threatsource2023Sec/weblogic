package weblogic.servlet.internal;

import java.io.InputStream;
import java.net.Socket;
import weblogic.buzzmessagebus.BuzzHTTP;
import weblogic.buzzmessagebus.BuzzHTTPSupport;

public final class BuzzMuxableSocketNonBlocking extends MuxableSocketNonBlocking implements BuzzHTTP {
   private BuzzHTTPSupport buzzHTTPSupport = null;

   public BuzzMuxableSocketNonBlocking(HttpConnectionHandler connHandler, ReadListenerStateContext readStateHandler, InputStream inputStream, boolean isAsync, boolean isChunk, long clen) {
      super(connHandler, readStateHandler, inputStream, isAsync, isChunk, clen);
      Socket socket = this.getSocket();
      if (socket instanceof BuzzHTTPSupport) {
         this.buzzHTTPSupport = (BuzzHTTPSupport)socket;
      }

   }

   public boolean isServletResponseCommitCalled() {
      return this.connHandler.getServletResponse().getServletOutputStream().isCommitCalled();
   }

   public boolean isDispatchOnRequestData() {
      return true;
   }

   public synchronized void registerForReadEvent() {
      try {
         InputStream inputStream = this.getSocket().getInputStream();
         if (inputStream.available() == 0) {
            if (this.buzzHTTPSupport != null) {
               this.buzzHTTPSupport.registerForRead();
            }

         } else {
            byte[] buf = this.getBuffer();
            int offset = this.getBufferOffset();
            int availableBuffer = buf.length - offset;
            int len = this.getSocket().getInputStream().read(buf, offset, availableBuffer);
            if (len > 0) {
               this.incrementBufferOffset(len);
            }

            if (this.isMessageComplete()) {
               this.buzzHTTPSupport.messageCompleted();
               this.dispatch();
            } else {
               this.buzzHTTPSupport.messageInitiated();
            }

         }
      } catch (Throwable var6) {
         throw new RuntimeException(var6);
      }
   }

   public void closeConnection(Throwable throwable) {
      if (throwable == null) {
         this.endOfStream();
      } else {
         this.hasException(throwable);
      }

      this.safeCloseSocket();
   }

   private void safeCloseSocket() {
      try {
         this.getSocket().close();
      } catch (Exception var2) {
      }

   }
}
