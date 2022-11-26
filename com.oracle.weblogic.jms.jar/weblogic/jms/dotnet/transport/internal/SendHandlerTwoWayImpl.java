package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.ReceivedOneWay;
import weblogic.jms.dotnet.transport.SendHandlerTwoWay;
import weblogic.jms.dotnet.transport.ServiceOneWay;
import weblogic.jms.dotnet.transport.TransportError;
import weblogic.jms.dotnet.transport.TransportExecutable;
import weblogic.jms.dotnet.transport.TwoWayResponseListener;

public class SendHandlerTwoWayImpl extends SendHandlerOneWayImpl implements SendHandlerTwoWay, ServiceOneWay, TransportExecutable {
   private ResponseLock lock = new ResponseLock();
   private long responseId;
   private long responseOrderingId;
   private int blockingCount;
   private TwoWayResponseListener responseListener;
   private ReceivedOneWayImpl response;
   private static final TransportError CANCEL_ERROR = new TransportError("Canceled", false);
   private static final TransportError SHUTDOWN_ERROR = new TransportError("Shutdown", false);

   SendHandlerTwoWayImpl(TransportImpl t, long rqid, long rqod, long rspod) {
      super(t, rqid, rqod);
      this.responseId = t.allocateServiceID();
      this.responseOrderingId = rspod;
   }

   private void scheduleMe() {
      this.getTransport().getDefaultThreadPool().schedule(this, this.responseOrderingId);
   }

   long getResponseID() {
      return this.responseId;
   }

   long getResponseOrderingID() {
      return this.responseOrderingId;
   }

   public void send(MarshalWritable mw) {
      this.send(mw, (TwoWayResponseListener)null);
   }

   public void send(MarshalWritable mw, TwoWayResponseListener rl) {
      this.checkAlreadySent();
      synchronized(this.lock) {
         this.responseListener = rl;
         if (this.response != null) {
            if (this.responseListener != null) {
               this.scheduleMe();
            }

            return;
         }

         this.getTransport().registerService(this.responseId, this, ThreadPoolWrapper.DIRECT);
         if (this.response != null) {
            this.getTransport().unregisterService(this.responseId, (TransportError)((TransportError)this.response.getRequest()));
            return;
         }
      }

      this.getTransport().sendInternalTwoWay(this, mw);
   }

   public MarshalReadable getResponse(boolean blocking) {
      synchronized(this.lock) {
         if (this.response == null && blocking) {
            ++this.blockingCount;

            try {
               this.lock.wait();
            } catch (InterruptedException var5) {
               this.setResponse((MarshalReadable)(new TransportError(var5)));
            }

            --this.blockingCount;
         }

         if (this.blockingCount > 0) {
            this.lock.notify();
         }

         return this.response.getRequest();
      }
   }

   public void cancel(TransportError te) {
      if (te == null) {
         te = CANCEL_ERROR;
      }

      synchronized(this.lock) {
         if (this.response == null) {
            this.setResponse((MarshalReadable)te);
            this.getTransport().unregisterService(this.responseId, te);
         }

      }
   }

   public void execute() {
      TwoWayResponseListener localListener;
      ReceivedOneWayImpl localResponse;
      synchronized(this.lock) {
         localListener = this.responseListener;
         localResponse = this.response;
      }

      localListener.onResponse(localResponse);
   }

   private void setResponse(MarshalReadable mreadable) {
      this.setResponse(new ReceivedOneWayImpl(this.getTransport(), this.getRemoteServiceID(), mreadable));
   }

   private void setResponse(ReceivedOneWayImpl row) {
      TwoWayResponseListener localResponseListener;
      synchronized(this.lock) {
         if (this.response != null) {
            return;
         }

         this.response = row;
         if (this.blockingCount > 0) {
            this.lock.notify();
         }

         localResponseListener = this.responseListener;
      }

      if (localResponseListener != null) {
         this.scheduleMe();
      }

      this.getTransport().unregisterServiceSilent(this.responseId);
   }

   public void invoke(ReceivedOneWay row) {
      this.setResponse((ReceivedOneWayImpl)row);
   }

   public void onPeerGone(TransportError te) {
      this.setResponse((MarshalReadable)te);
   }

   public void onShutdown() {
      this.setResponse((MarshalReadable)SHUTDOWN_ERROR);
   }

   public void onUnregister() {
      this.setResponse((MarshalReadable)CANCEL_ERROR);
   }
}
