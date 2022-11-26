package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalReadableFactory;
import weblogic.jms.dotnet.transport.TransportError;

class MarshalFactoryImpl implements MarshalReadableFactory {
   public MarshalReadable createMarshalReadable(int marshalTypeCode) {
      switch (marshalTypeCode) {
         case 20000:
            return new BootstrapRequest();
         case 20001:
            return new TransportError();
         case 20002:
         default:
            return null;
         case 20003:
            return new HeartbeatRequest();
      }
   }
}
