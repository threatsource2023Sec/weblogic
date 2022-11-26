package com.oracle.tyrus.fallback;

import com.oracle.tyrus.fallback.spi.LongPollingAdapter;
import com.oracle.tyrus.fallback.spi.ReadHandler;
import com.oracle.tyrus.fallback.spi.WriteFrame;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.tyrus.spi.CompletionHandler;

class LongPollingConnection implements Connection {
   private static final Logger LOGGER = Logger.getLogger(LongPollingConnection.class.getName());
   private static final String TYRUS_OPERATION = "tyrus-client-operation";
   private static final String TYRUS_READ_OP = "tyrus-send-message";
   private static final String TYRUS_WRITE_OP = "tyrus-wait-for-input";
   private static final long WRITE_REQ_SETUP_TIME = 60000L;
   private final Queue writeQueue = new ConcurrentLinkedDeque();
   private final String conId;
   private final String uri;
   private final LongPollingConnectionMgr mgr;
   private final LongPollingAdapter adapter;
   private final boolean encoding;
   private volatile LongPollingReadRequest readRequest;
   private volatile LongPollingWriteRequest writeRequest;
   private ReadHandler readHandler;
   private org.glassfish.tyrus.spi.Connection tyrusCon;
   private AtomicLong writeReqEndTime = new AtomicLong();
   private String negotiatedSubProtocol = null;

   LongPollingConnection(LongPollingConnectionMgr mgr, LongPollingAdapter adapter, String uri, String conId, boolean encoding) {
      this.mgr = mgr;
      this.adapter = adapter;
      this.uri = uri;
      this.conId = conId;
      this.encoding = encoding;
   }

   public String getRequestURI() {
      return this.uri;
   }

   public void setReadHandler(ReadHandler rh) {
      this.readHandler = (ReadHandler)(this.encoding ? new Base16FilterReadHandler(rh) : rh);
   }

   public void setNegotiatedSubProtocol(String negotiatedSubProtocol) {
      this.negotiatedSubProtocol = negotiatedSubProtocol;
   }

   public void write(byte[] data, int offset, int length, CompletionHandler ch) {
      if (this.encoding) {
         data = Base16WriteUtil.convert(data, offset, length);
         offset = 0;
         length = data.length;
      }

      WriteFrame wf = new WriteFrame(data, offset, length);
      this.writeQueue.add(wf);
      if (this.writeRequest != null) {
         this.writeRequest.onDataAvailable();
         ch.completed((Object)null);
      } else {
         long setupTime = System.currentTimeMillis() - this.writeReqEndTime.get();
         if (setupTime > 60000L) {
            LOGGER.warning("There is no HTTP fallback write request in last " + setupTime + " ms. Assuming client=" + this.conId + " disconnected abnormally.");
            ch.failed((Throwable)null);
            this.tyrusCon.close(LongPollingWriteRequest.IO_ERROR);
         } else {
            ch.completed((Object)null);
         }
      }

   }

   public void close() throws IOException {
      this.mgr.remove(this);
   }

   void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String clientOperation = FallbackUtil.getHeaderValue(req, "tyrus-client-operation");
      if (clientOperation == null) {
         throw new ServletException("tyrus-client-operation header is not present,  even though, it is a fallback connection.");
      } else {
         switch (clientOperation) {
            case "tyrus-wait-for-input":
               if (this.writeRequest != null) {
                  LOGGER.warning("There is already a writechannel.But, client opened a new writechannel");
               }

               this.writeRequest = new LongPollingWriteRequest(this, req, res, this.writeQueue, this.adapter);
               if (!this.writeQueue.isEmpty()) {
                  this.writeRequest.onDataAvailable();
               }
               break;
            case "tyrus-send-message":
               if (this.readRequest != null) {
                  LOGGER.warning("There is already a read channel.But, client opened a new readchannel");
               }

               this.readRequest = new LongPollingReadRequest(this, req, res, this.readHandler, this.adapter);
               this.readRequest.read();
               break;
            default:
               throw new ServletException("Unknown value " + clientOperation + " for " + "tyrus-client-operation" + "header in fallback connection.");
         }

         if (this.negotiatedSubProtocol != null) {
            res.setHeader("tyrus-sub-protocol", this.negotiatedSubProtocol);
         }

      }
   }

   String getConnectionId() {
      return this.conId;
   }

   void doneWriting() {
      this.writeRequest = null;
      this.writeReqEndTime.set(System.currentTimeMillis());
   }

   void doneReading() {
      this.readRequest = null;
   }

   boolean isEncoding() {
      return this.encoding;
   }

   void setTyrusConnection(org.glassfish.tyrus.spi.Connection tyrusCon) {
      this.tyrusCon = tyrusCon;
   }

   org.glassfish.tyrus.spi.Connection getTyrusConnection() {
      return this.tyrusCon;
   }
}
