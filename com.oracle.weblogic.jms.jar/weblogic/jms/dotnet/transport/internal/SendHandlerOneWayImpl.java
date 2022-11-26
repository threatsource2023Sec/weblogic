package weblogic.jms.dotnet.transport.internal;

import java.util.concurrent.atomic.AtomicBoolean;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.SendHandlerOneWay;
import weblogic.jms.dotnet.transport.TransportError;

public class SendHandlerOneWayImpl implements SendHandlerOneWay {
   private AtomicBoolean sentFlag = new AtomicBoolean(false);
   private AtomicBoolean canceledFlag = new AtomicBoolean(false);
   private TransportImpl transportImpl;
   private long remoteServiceId;
   private long remoteOrderingId;

   SendHandlerOneWayImpl(TransportImpl t, long rid, long rod) {
      this.transportImpl = t;
      this.remoteServiceId = rid;
      this.remoteOrderingId = rod;
   }

   long getRemoteServiceID() {
      return this.remoteServiceId;
   }

   long getRemoteOrderingID() {
      return this.remoteOrderingId;
   }

   void checkAlreadySent() {
      if (this.sentFlag.getAndSet(true)) {
         throw new AssertionError("duplicate send");
      }
   }

   boolean isAlreadySent() {
      return this.sentFlag.get();
   }

   TransportImpl getTransport() {
      return this.transportImpl;
   }

   public void send(MarshalWritable mw) {
      this.checkAlreadySent();
      if (!this.canceledFlag.get()) {
         this.transportImpl.sendInternalOneWay(this, mw);
      }
   }

   public void cancel(TransportError te) {
      this.canceledFlag.set(true);
   }
}
