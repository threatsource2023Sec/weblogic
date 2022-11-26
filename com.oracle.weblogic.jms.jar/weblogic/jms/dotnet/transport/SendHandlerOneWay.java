package weblogic.jms.dotnet.transport;

public interface SendHandlerOneWay {
   void send(MarshalWritable var1);

   void cancel(TransportError var1);
}
