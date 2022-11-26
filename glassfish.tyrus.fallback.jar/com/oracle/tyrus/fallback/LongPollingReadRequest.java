package com.oracle.tyrus.fallback;

import com.oracle.tyrus.fallback.spi.LongPollingAdapter;
import com.oracle.tyrus.fallback.spi.ReadHandler;
import java.io.IOException;
import java.nio.channels.CompletionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;

class LongPollingReadRequest implements AsyncListener, CompletionHandler {
   private static final Logger LOGGER = Logger.getLogger(LongPollingWriteRequest.class.getName());
   private static final CloseReason IO_ERROR;
   private final HttpServletResponse res;
   private final AsyncContext asyncContext;
   private final LongPollingAdapter adapter;
   private final ReadHandler readHandler;
   private final HttpServletRequest req;
   private final LongPollingConnection parent;

   LongPollingReadRequest(LongPollingConnection parent, HttpServletRequest req, HttpServletResponse res, ReadHandler readHandler, LongPollingAdapter adapter) {
      this.parent = parent;
      this.req = req;
      this.res = res;
      this.readHandler = readHandler;
      this.adapter = adapter;
      this.asyncContext = req.startAsync();
      this.asyncContext.addListener(this);
   }

   void read() {
      this.adapter.read(this.req, this.readHandler, this);
   }

   public void completed(Void result, Void attachment) {
      this.res.setStatus(200);
      this.asyncContext.complete();
   }

   public void failed(Throwable exc, Void attachment) {
      LOGGER.log(Level.WARNING, "Exception while reading fallback frames", exc);
      this.res.setStatus(500);
      this.asyncContext.complete();
      this.parent.getTyrusConnection().close(IO_ERROR);
   }

   public void onComplete(AsyncEvent asyncEvent) throws IOException {
      this.parent.doneReading();
   }

   public void onTimeout(AsyncEvent asyncEvent) throws IOException {
      LOGGER.log(Level.WARNING, "Timeout while reading fallback frames");
   }

   public void onError(AsyncEvent asyncEvent) throws IOException {
      LOGGER.log(Level.WARNING, "Exception while reading fallback frames", asyncEvent.getThrowable());
   }

   public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
   }

   static {
      IO_ERROR = new CloseReason(CloseCodes.CLOSED_ABNORMALLY, "I/O error while reading via fallback transport");
   }
}
