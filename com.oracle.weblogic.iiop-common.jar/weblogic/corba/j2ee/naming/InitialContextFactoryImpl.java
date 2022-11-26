package weblogic.corba.j2ee.naming;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContext;
import weblogic.corba.client.spi.ServiceManager;

public final class InitialContextFactoryImpl implements InitialContextFactory {
   public Context getInitialContext(Hashtable env) throws NamingException {
      String url = null;
      if (env != null) {
         env = (Hashtable)env.clone();
         url = (String)env.remove("java.naming.provider.url");
      }

      if (url == null) {
         url = "iiop://localhost:7001";
      }

      return getInitialContext(env, url);
   }

   public static Context getInitialContext(Hashtable env, String url) throws NamingException {
      Object nameService = isIorUrl(url) ? getNameServiceFromIor(url) : getRemoteNameService(env, url);
      ContextImpl context = createContext(env, url, nameService);
      establishThreadEnvironment(env, context);
      return context;
   }

   private static boolean isIorUrl(String url) {
      return url.startsWith("IOR:");
   }

   private static ContextImpl createContext(Hashtable env, String requestUrl, Object remoteObject) throws NamingException {
      NamingContext ctx = Utils.narrowContext(remoteObject);
      if (ctx == null) {
         throw new NamingException("Could not resolve context from: " + requestUrl);
      } else {
         return new ContextImpl(env, getCachedOrbInfo(), ctx);
      }
   }

   public static void establishThreadEnvironment(Hashtable env, ContextImpl context) throws NamingException {
      ServiceManager.getSecurityManager().pushSubject(env, context);
      establishTransactionManagerForIiop();
   }

   private static void establishTransactionManagerForIiop() {
      getOrbHelper().pushTransactionHelper();
   }

   private static Object getNameServiceFromIor(String url) throws NamingException {
      return getOrb(getCachedOrbInfo()).string_to_object(url);
   }

   private static ORB getOrb(ORBInfo orbinfo) throws NamingException {
      return orbinfo != null ? orbinfo.getORB() : getOrbHelper().getLocalORB();
   }

   private static Object getRemoteNameService(Hashtable env, String url) throws NamingException {
      return getOrbHelper().getORBInitialReference(url, env, "NameService");
   }

   private static boolean usesTgiopProtocol(String url) {
      return url.contains("tgiop:");
   }

   private static ORBHelper getOrbHelper() {
      return ORBHelper.getORBHelper();
   }

   private static ORBInfo getCachedOrbInfo() {
      return getOrbHelper().getCurrent();
   }
}
