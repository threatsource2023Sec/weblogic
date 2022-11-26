package weblogic.jms.dotnet.transport.t3plugin;

import weblogic.jms.dotnet.t3.server.JMSCSharp;
import weblogic.jms.dotnet.t3.server.spi.T3Connection;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionHandle;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionHandleFactory;

public class T3ServerPlugin implements T3ConnectionHandleFactory {
   public static final T3ServerPlugin singleton = new T3ServerPlugin();

   public static void register() {
      JMSCSharp.getInstance();
      JMSCSharp.setT3ConnectionHandleFactory(1, singleton);
   }

   public static void unregister() {
      JMSCSharp.getInstance();
      JMSCSharp.unsetT3ConnectionHandleFactory(1);
   }

   public T3ConnectionHandle createHandle(T3Connection client) {
      return new T3ConnectionHandleImpl(client);
   }

   public static void main(String[] argv) {
      register();
   }
}
