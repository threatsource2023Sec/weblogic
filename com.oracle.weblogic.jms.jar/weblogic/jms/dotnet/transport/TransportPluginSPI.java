package weblogic.jms.dotnet.transport;

public interface TransportPluginSPI {
   MarshalWriter createMarshalWriter();

   void send(MarshalWriter var1);

   long getScratchID();

   void terminateConnection();
}
