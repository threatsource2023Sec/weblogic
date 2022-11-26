package weblogic.jms.dotnet.transport;

public interface Transport extends MarshalReadableFactory {
   void registerService(long var1, Service var3);

   long allocateServiceID();

   void unregisterService(long var1);

   MarshalReadable bootstrap(String var1);

   SendHandlerOneWay createOneWay(long var1, long var3);

   SendHandlerOneWay createOneWay(long var1);

   SendHandlerTwoWay createTwoWay(long var1, long var3, long var5);

   SendHandlerTwoWay createTwoWay(long var1, long var3);

   SendHandlerTwoWay createTwoWay(long var1);

   void shutdown(TransportError var1);

   void addMarshalReadableFactory(MarshalReadableFactory var1);

   void dispatch(MarshalReader var1);

   long getScratchId();
}
