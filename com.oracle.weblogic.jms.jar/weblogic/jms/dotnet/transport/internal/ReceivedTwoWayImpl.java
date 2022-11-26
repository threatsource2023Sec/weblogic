package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.ReceivedTwoWay;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.jms.dotnet.transport.TransportError;

public class ReceivedTwoWayImpl extends ReceivedOneWayImpl implements ReceivedTwoWay {
   private SendHandlerOneWayImpl sendHandler;

   ReceivedTwoWayImpl(Transport t, long sid, MarshalReader mr, SendHandlerOneWayImpl sh) {
      super(t, sid, mr);
      this.sendHandler = sh;
   }

   public void cancel(TransportError te) {
      this.sendHandler.send(te);
   }

   public void send(MarshalWritable mw) {
      this.sendHandler.send(mw);
   }

   boolean isAlreadySent() {
      return this.sendHandler.isAlreadySent();
   }
}
