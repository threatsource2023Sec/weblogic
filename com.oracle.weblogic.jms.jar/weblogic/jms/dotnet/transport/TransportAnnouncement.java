package weblogic.jms.dotnet.transport;

class TransportAnnouncement implements ReceivedOneWay {
   private Transport transport;

   TransportAnnouncement(Transport t) {
      this.transport = t;
   }

   public Transport getTransport() {
      return this.transport;
   }

   public long getServiceId() {
      return 10000L;
   }

   public MarshalReadable getRequest() {
      return null;
   }
}
