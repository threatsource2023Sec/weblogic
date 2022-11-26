package weblogic.factories.iiops;

import weblogic.iiop.IORFactory;
import weblogic.iiop.ObjectKey;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.server.ior.ServerIORFactory;

public final class IORFactoryImpl implements IORFactory {
   public IOR createIOR(String host, int port, String serviceName, int majorVersion, int minorVersion) {
      return ServerIORFactory.createSecureIOR(ObjectKey.getTypeId(serviceName), host, port, ObjectKey.getBootstrapKey(serviceName), (byte)majorVersion, (byte)minorVersion);
   }
}
