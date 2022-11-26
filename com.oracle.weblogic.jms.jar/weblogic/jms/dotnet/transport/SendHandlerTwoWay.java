package weblogic.jms.dotnet.transport;

public interface SendHandlerTwoWay extends SendHandlerOneWay {
   MarshalReadable getResponse(boolean var1);

   void send(MarshalWritable var1, TwoWayResponseListener var2);
}
