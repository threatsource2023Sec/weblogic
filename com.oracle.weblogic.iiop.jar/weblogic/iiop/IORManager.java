package weblogic.iiop;

import java.io.IOException;
import javax.naming.NamingException;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import weblogic.corba.j2ee.naming.EndPointList;
import weblogic.corba.j2ee.naming.EndPointSelector;
import weblogic.corba.orb.ORB;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.ior.IOR;
import weblogic.kernel.Kernel;
import weblogic.utils.concurrent.locks.Mutex;

public final class IORManager {
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");
   private static final String NAME_SERVICE = "NameService";
   private static boolean isClient = true;
   private static final String TRUE_PROP = "true";
   private static final String FALSE_PROP = "false";
   private static final Mutex bootstrapLock = new Mutex();

   public static IOR createIOR(String protocol, String host, int port, String serviceName, int majorVersion, int minorVersion) {
      try {
         IORFactory factory = (IORFactory)Class.forName("weblogic.factories." + protocol + ".IORFactoryImpl").newInstance();
         return factory.createIOR(host, port, serviceName, majorVersion, minorVersion);
      } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var7) {
         throw new Error(var7.toString());
      }
   }

   public static void initialize() {
      isClient = false;
   }

   public static boolean isClient() {
      return isClient;
   }

   public static Object createInitialReference(String url, long timeout) throws NamingException {
      try {
         EndPointSelector selector = EndPointList.createEndPointList(url).getStartingEndPoint();
         if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
            p("createInitialReference: " + selector.getHost() + ":" + Integer.toString(selector.getPort()));
         }

         String enableClientProp;
         try {
            enableClientProp = System.getProperty("weblogic.system.iiop.enableClient");
         } catch (SecurityException var6) {
            enableClientProp = "false";
         }

         if (!IIOPClient.isEnabled() && "true".equals(enableClientProp)) {
            IIOPClient.initialize();
         }

         IOR ior = createIOR(selector.getProtocol(), selector.getHost(), selector.getPort(), selector.getServiceName(), selector.getMajorVersion(), selector.getMinorVersion());
         if (selector.getServiceName().equals("NameService")) {
            ior = locateNameService(ior, timeout);
         }

         return (Object)IIOPReplacer.resolveObject(ior);
      } catch (IOException | SystemException var7) {
         throw weblogic.corba.j2ee.naming.Utils.wrapNamingException(var7, var7.getMessage());
      }
   }

   public static IOR locateNameService(IOR ior, long timeout) {
      boolean loadBalance = ORB.reconnectOnBootstrap && ObjectKey.getObjectKey(ior).isNamingKey();

      IOR var5;
      try {
         EndPoint endPoint;
         if (loadBalance) {
            bootstrapLock.lock();
            endPoint = EndPointManager.findOrCreateEndPoint(ior, (String)null, true);
         } else {
            endPoint = EndPointManager.findOrCreateEndPoint(ior);
         }

         var5 = endPoint.getCurrentIor(ior, timeout);
      } catch (IOException var9) {
         throw Utils.mapToCORBAException(var9);
      } finally {
         if (loadBalance) {
            bootstrapLock.unlock();
         }

      }

      return var5;
   }

   public static IOR locateInitialReference(IOR ior, String channelName, long timeout) {
      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         p("locateInitialReference(" + ior + ", " + channelName + ")");
      }

      try {
         EndPoint endPoint = EndPointManager.findOrCreateEndPoint(ior, channelName);
         return endPoint.getCurrentIor(ior, timeout);
      } catch (IOException var5) {
         throw Utils.mapToCORBAException(var5);
      }
   }

   static void p(String s) {
      System.err.println("<IORManager> " + s);
   }
}
