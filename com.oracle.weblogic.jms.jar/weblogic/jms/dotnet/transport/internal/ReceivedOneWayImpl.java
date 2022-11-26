package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.ReceivedOneWay;
import weblogic.jms.dotnet.transport.Transport;

public class ReceivedOneWayImpl implements ReceivedOneWay {
   private Transport transport;
   private long serviceId;
   private MarshalReader reader;
   private MarshalReadable req;

   ReceivedOneWayImpl(Transport t, long sid, MarshalReader mr) {
      this.transport = t;
      this.serviceId = sid;
      this.reader = mr;
   }

   ReceivedOneWayImpl(Transport t, long sid, MarshalReadable r) {
      this.transport = t;
      this.serviceId = sid;
      this.req = r;
   }

   public synchronized MarshalReadable getRequest() {
      if (this.reader == null) {
         return this.req;
      } else {
         this.req = this.reader.readMarshalable();
         this.reader.internalClose();
         this.reader = null;
         return this.req;
      }
   }

   public long getServiceId() {
      return this.serviceId;
   }

   public Transport getTransport() {
      return this.transport;
   }
}
