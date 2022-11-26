package weblogic.rmi.internal;

import java.io.IOException;
import java.rmi.UnexpectedException;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public final class ReplyOnError extends WorkAdapter {
   private static final boolean DEBUG = false;
   private InboundRequest request = null;
   private OutboundResponse response = null;
   private Throwable th;

   public ReplyOnError(OutboundResponse response, Throwable t) {
      this.response = response;
      this.th = t;
      WorkManagerFactory.getInstance().getSystem().schedule(this);
   }

   public ReplyOnError(InboundRequest request, OutboundResponse response, Throwable t) {
      this.request = request;
      this.response = response;
      this.th = t;
      if (request.isCollocated()) {
         this.run();
      } else {
         WorkManagerFactory.getInstance().getSystem().schedule(this);
      }

   }

   public void run() {
      try {
         if (this.request != null) {
            if (this.response == null) {
               this.response = this.request.getOutboundResponse();
            }

            this.response.transferThreadLocalContext(this.request);
         }
      } catch (IOException var2) {
      }

      if (this.th == null) {
         this.response.sendThrowable(new UnexpectedException("An error condition was raised with no accompanying exception"));
      } else {
         this.response.sendThrowable(this.th);
      }

   }
}
