package weblogic.factories.tgiop;

import javax.naming.NamingException;
import weblogic.iiop.IORFactory;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.server.ior.ServerIORFactory;
import weblogic.tgiop.TGIOPObjectKey;

public final class IORFactoryImpl implements IORFactory {
   public IOR createIOR(String domain, int port, String serviceName, int majorVersion, int minorVersion) {
      try {
         return ServerIORFactory.createIOR(TGIOPObjectKey.getInitialRefInterfaceName(serviceName), "", 0, new TGIOPObjectKey(serviceName, domain), (byte)majorVersion, (byte)minorVersion);
      } catch (NamingException var7) {
         return null;
      }
   }
}
