package weblogic.jms.dotnet.transport;

public interface ReceivedOneWay {
   Transport getTransport();

   long getServiceId();

   MarshalReadable getRequest();
}
