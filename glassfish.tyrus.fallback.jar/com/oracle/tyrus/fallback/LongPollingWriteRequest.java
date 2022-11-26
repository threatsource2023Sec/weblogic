package com.oracle.tyrus.fallback;

import com.oracle.tyrus.fallback.spi.LongPollingAdapter;
import com.oracle.tyrus.fallback.spi.WriteFrame;
import java.io.IOException;
import java.nio.channels.CompletionHandler;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;

class LongPollingWriteRequest implements AsyncListener, CompletionHandler {
   static final CloseReason IO_ERROR;
   private static final int MAX_FRAMES_PER_CONNECTION = 10;
   private static final Logger LOGGER;
   private static final String TYRUS_CORS_HDR = "tyrus-cors-headers";
   private static final String TYRUS_CORS_VALUE = "yes";
   private final LongPollingAdapter adapter;
   private final HttpServletResponse res;
   private final Queue writeQueue;
   private final AsyncContext asyncContext;
   private final LongPollingConnection parent;
   private final AtomicBoolean inWriting = new AtomicBoolean();
   private final boolean cors;
   private boolean doneWriting;

   LongPollingWriteRequest(LongPollingConnection parent, HttpServletRequest req, HttpServletResponse res, Queue writeQueue, LongPollingAdapter adapter) {
      this.parent = parent;
      this.res = res;
      this.adapter = adapter;
      this.writeQueue = writeQueue;
      String corsHeader = FallbackUtil.getHeaderValue(req, "tyrus-cors-headers");
      this.cors = corsHeader != null && corsHeader.equals("yes");
      this.asyncContext = req.startAsync();
      this.asyncContext.addListener(this);
   }

   void onDataAvailable() {
      if (this.inWriting.compareAndSet(false, true)) {
         this.res.setStatus(200);
         String ct = this.parent.isEncoding() ? "text/plain" : "application/octet-stream";
         this.res.setContentType(ct);
         if (this.cors) {
            this.res.addHeader("Access-Control-Allow-Origin", "*");
            this.res.addHeader("Access-Control-Allow-Headers", "base16");
            this.res.addHeader("Access-Control-Expose-Headers", "base16");
         }

         LinkedList slice = new LinkedList();

         for(int i = 0; i < 10; ++i) {
            WriteFrame frame = (WriteFrame)this.writeQueue.poll();
            if (frame == null) {
               break;
            }

            slice.add(frame);
         }

         this.adapter.write(this.res, slice, this);
      }

   }

   public void onComplete(AsyncEvent asyncEvent) throws IOException {
      this.parent.doneWriting();
   }

   public void onTimeout(AsyncEvent asyncEvent) throws IOException {
      if (this.inWriting.compareAndSet(false, true)) {
         this.res.setStatus(200);
         this.asyncContext.complete();
      } else {
         LOGGER.log(Level.WARNING, "Timeout while writing fallback frames", asyncEvent.getThrowable());
         synchronized(this) {
            while(!this.doneWriting) {
               try {
                  this.wait(this.asyncContext.getTimeout());
               } catch (InterruptedException var5) {
               }
            }
         }
      }

   }

   public void onError(AsyncEvent asyncEvent) throws IOException {
      LOGGER.log(Level.WARNING, "Exception while writing fallback frames", asyncEvent.getThrowable());
   }

   public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
   }

   public void completed(Void result, Void attachment) {
      synchronized(this) {
         this.doneWriting = true;
         this.notifyAll();
      }

      this.asyncContext.complete();
   }

   public void failed(Throwable exc, Void attachment) {
      synchronized(this) {
         this.doneWriting = true;
         this.notifyAll();
      }

      LOGGER.log(Level.WARNING, "Exception while writing fallback frames", exc);
      this.asyncContext.complete();
      this.parent.getTyrusConnection().close(IO_ERROR);
   }

   static {
      IO_ERROR = new CloseReason(CloseCodes.CLOSED_ABNORMALLY, "I/O error while writing via fallback transport");
      LOGGER = Logger.getLogger(LongPollingWriteRequest.class.getName());
   }
}
