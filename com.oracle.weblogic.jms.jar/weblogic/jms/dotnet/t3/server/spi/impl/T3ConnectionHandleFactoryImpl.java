package weblogic.jms.dotnet.t3.server.spi.impl;

import weblogic.jms.dotnet.t3.server.spi.T3Connection;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionHandle;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionHandleFactory;

public class T3ConnectionHandleFactoryImpl implements T3ConnectionHandleFactory {
   public T3ConnectionHandle createHandle(T3Connection client) {
      return new T3ConnectionHandleImpl(client);
   }
}
