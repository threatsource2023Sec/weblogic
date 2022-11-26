package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import weblogic.buzzmessagebus.BuzzHTTP;
import weblogic.buzzmessagebus.BuzzHTTPSupport;
import weblogic.protocol.ServerChannel;
import weblogic.socket.MuxableSocket;
import weblogic.utils.io.Chunk;

public final class BuzzMuxableSocketHTTP extends MuxableSocketHTTP implements BuzzHTTP {
   private BuzzHTTPSupport buzzHTTPSupport;
   private BuzzMuxableSocketNonBlocking buzzMuxableSocketNonBlocking;

   public BuzzMuxableSocketHTTP(Chunk head, Socket s, BuzzHTTPSupport buzzHTTPSupport, boolean secure, ServerChannel channel) throws IOException {
      super(head, s, secure, channel);
      this.buzzHTTPSupport = buzzHTTPSupport;
   }

   public boolean isServletResponseCommitCalled() {
      return this.connHandler.getServletResponse().getServletOutputStream().isCommitCalled();
   }

   public boolean isDispatchOnRequestData() {
      return false;
   }

   public void registerForReadEvent() {
      if (this.buzzMuxableSocketNonBlocking != null) {
         this.buzzMuxableSocketNonBlocking.registerForReadEvent();
      }

   }

   public void closeConnection(Throwable throwable) {
      if (throwable == null) {
         this.endOfStream();
      } else {
         this.hasException(throwable);
      }

   }

   public void requeue() {
      this.buzzHTTPSupport.requeue();
   }

   MuxableSocketNonBlocking createMuxableSocketNonBlocking(HttpConnectionHandler connHandler, ReadListenerStateContext readStateHandler, InputStream inputStream, boolean isAsync, boolean isChunk, long clen) {
      return new BuzzMuxableSocketNonBlocking(connHandler, readStateHandler, inputStream, isAsync, isChunk, clen);
   }

   void reRegister(MuxableSocket oldSock, MuxableSocket newSock) {
      if (newSock instanceof BuzzMuxableSocketNonBlocking) {
         this.buzzMuxableSocketNonBlocking = (BuzzMuxableSocketNonBlocking)newSock;
      } else {
         this.buzzMuxableSocketNonBlocking = null;
      }

      this.buzzHTTPSupport.register(this.buzzMuxableSocketNonBlocking);
   }

   void deliverEndOfStream() {
      this.endOfStream();
   }
}
