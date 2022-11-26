package weblogic.corba.j2ee.naming;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.net.ssl.SSLContext;
import org.glassfish.hk2.api.AnnotationLiteral;
import org.jvnet.hk2.annotations.Contract;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.ORB;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.ORBPackage.InvalidName;
import weblogic.corba.client.transaction.TransactionHelperImpl;
import weblogic.jndi.WLInitialContextFactory;
import weblogic.server.GlobalServiceLocator;
import weblogic.transaction.TransactionHelper;

@Contract
public abstract class ORBHelper {
   public static final int THIN_CLIENT_RANK = 0;
   public static final int WLS_RANK = 10;
   public static final String ORB_INITIAL_HOST = "org.omg.CORBA.ORBInitialHost";
   public static final String ORB_INITIAL_PORT = "org.omg.CORBA.ORBInitialPort";
   public static final String ORB_INITIAL_REF = "org.omg.CORBA.ORBInitRef";
   public static final String ORB_DEFAULT_INITIAL_REF = "org.omg.CORBA.ORBDefaultInitRef";
   public static final String ORB_CLASS_PROP = "org.omg.CORBA.ORBClass";
   public static final String ORB_NAMING_PROP = "java.naming.corba.orb";
   public static final String ORB_INITIALIZER = "org.omg.PortableInterceptor.ORBInitializerClass.";
   public static final String BI_DIR_ORBINIT = "weblogic.corba.client.iiop.BiDirORBInitializer";
   public static final String CLIENT_ORBINIT = "weblogic.corba.client.ClientORBInitializer";
   public static final String CLIENT_PACKAGE = "weblogic.corba.client.";
   public static final String CLUSTER_SOCKET_FACTORY = "cluster.ORBSocketFactory";
   public static final String NATIVE_TX_HELPER = "weblogic.corba.server.transaction.TransactionHelperImpl";
   public static final String ENABLE_SERVER_AFFINITY = "weblogic.jndi.enableServerAffinity";
   public static final String ORB_PROTOCOL = "weblogic.corba.orb.ORBProtocol";
   public static final String ORB_NAME = "weblogic.corba.orb.ORBName";
   public static final String INITIAL_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
   /** @deprecated */
   @Deprecated
   public static final String REQUEST_TIMEOUT = "weblogic.jndi.requestTimeout";
   /** @deprecated */
   @Deprecated
   public static final String RMI_TIMEOUT = "weblogic.rmi.clientTimeout";
   public static final String CONNECT_TIMEOUT = "weblogic.jndi.connectTimeout";
   public static final String RESPONSE_READ_TIMEOUT = "weblogic.jndi.responseReadTimeout";
   private static final boolean DEBUG = Boolean.getBoolean("weblogic.debug.ORBHelper");
   private static final String FALSE_PROP = "false";
   private static final int NOT_FOUND = -1;
   private static final String THIN_CLIENT_JAR_NAME = "wlclient.jar";
   private static ORBHelper singleton = (ORBHelper)GlobalServiceLocator.getServiceLocator().getService(ORBHelper.class, new Annotation[]{getQualifier()});
   private static OrbCreator orbCreator = new OrbCreator() {
      public ORB createOrb(Properties props) {
         return ORB.init(new String[0], props);
      }
   };
   private HashMap orbInfoMap = new HashMap();
   private HashMap sslCtxCache = new HashMap();
   private ConcurrentHashMap timeoutCache = new ConcurrentHashMap();
   private HashMap endPointListMap = new HashMap();
   private ORBInfo currentinfo = null;

   private static Annotation getQualifier() {
      return (Annotation)(ThinClientClass.class.isAssignableFrom(WLInitialContextFactory.class) ? new ThinClientLiteral() : new WlsClientLiteral());
   }

   public static boolean isThinClient() {
      return getORBHelper().isThinClientHelper();
   }

   protected boolean isThinClientHelper() {
      return false;
   }

   public static ORBHelper getORBHelper() {
      if (singleton == null) {
         throw createClasspathException();
      } else {
         return singleton;
      }
   }

   public static void removeCurrentClientSecurityContext() {
      if (singleton != null) {
         ORBInfo info = singleton.getCurrent();
         if (info != null) {
            info.removeClientSecurityContext();
         }
      }

   }

   private static RuntimeException createClasspathException() {
      StringBuilder errorMessage = new StringBuilder("Initialization failure: ");
      String initialContextFactoryJarName = getInitialContextFactoryJarName();
      int wlclientIndex = getClassPathIndex("wlclient.jar");
      if (wlclientIndex == -1) {
         errorMessage.append("wlclient.jar").append(" not found in the classpath.");
      } else if (wlclientIndex > getClassPathIndex(initialContextFactoryJarName)) {
         errorMessage.append("wlclient.jar").append(" must be listed before ").append(initialContextFactoryJarName).append(" in the classpath");
      } else {
         errorMessage.append("reason unknown. Please contact support");
      }

      return new RuntimeException(errorMessage.toString());
   }

   private static String getInitialContextFactoryJarName() {
      URL factoryResource = Thread.currentThread().getContextClassLoader().getResource(WLInitialContextFactory.class.getName());
      return factoryResource == null ? "NO.SUCH.JAR" : factoryResource.getFile();
   }

   private static int getClassPathIndex(String fileName) {
      String classpath = System.getProperty("java.class.path");
      String pathSeparator = System.getProperty("path.separator");
      StringTokenizer st = new StringTokenizer(classpath, pathSeparator);

      for(int index = 0; st.hasMoreTokens(); ++index) {
         if (st.nextToken().contains(fileName)) {
            return index;
         }
      }

      return -1;
   }

   private static boolean hasClass(String className) {
      try {
         Class.forName(className);
         return true;
      } catch (ClassNotFoundException var2) {
         return false;
      }
   }

   public static synchronized void setORBHelper(ORBHelper helper) {
      singleton = helper;
   }

   static boolean getServerAffinity(Hashtable env) {
      return env == null ? false : Boolean.valueOf((String)env.get("weblogic.jndi.enableServerAffinity"));
   }

   public synchronized ORB getORB(String url, Hashtable env) throws NamingException {
      EndPointList info = this.getEndPointList(url);
      ORBInfo orbinfo = this.getCachedORB(info.getStartingEndPoint().getKey(), env);
      return orbinfo != null ? orbinfo.getORB() : this.createOrb(info.getStartingEndPoint(), env);
   }

   private synchronized ORBInfo getCachedORB(String key, Hashtable env) {
      ORBInfo orbinfo = (ORBInfo)this.orbInfoMap.get(key);
      if (orbinfo != null) {
         this.setCurrent(orbinfo);
         return orbinfo;
      } else {
         if (env != null && env.get("java.naming.corba.orb") != null) {
            orbinfo = this.cacheORB(key, (ORB)env.get("java.naming.corba.orb"));
         }

         return orbinfo;
      }
   }

   private ORBInfo cacheORB(String key, ORB orb) {
      ORBInfo orbinfo = this.createORBInfo(orb, key);
      this.orbInfoMap.put(key, orbinfo);
      this.setCurrent(orbinfo);
      return orbinfo;
   }

   public ORB createORB(Hashtable env, EndPointSelector epi, String protocol, String initialRef) {
      Properties props = this.createProperties(env, epi, protocol, initialRef);
      ORB orb = this.createOrbInSystemClassLoader(props);
      if (env != null) {
         this.sslCtxCache.put(orb, this.getConfiguredSslContext(env));
         this.timeoutCache.put(orb, this.getConfiguredTimeout(env));
      }

      return orb;
   }

   private ORB createOrbInSystemClassLoader(Properties props) {
      ClassLoader savedCL = Thread.currentThread().getContextClassLoader();

      ORB var3;
      try {
         Thread.currentThread().setContextClassLoader(ORBHelper.class.getClassLoader());
         var3 = orbCreator.createOrb(props);
      } finally {
         Thread.currentThread().setContextClassLoader(savedCL);
      }

      return var3;
   }

   private SSLContext getConfiguredSslContext(Hashtable env) {
      Object credentials = env.get("java.naming.security.credentials");
      return credentials instanceof SSLContext ? (SSLContext)credentials : null;
   }

   private Long getConfiguredTimeout(Hashtable env) {
      Object timeOut = env.get("weblogic.jndi.responseReadTimeout");
      if (timeOut == null) {
         timeOut = env.get("weblogic.jndi.requestTimeout");
      }

      return timeOut instanceof Long ? (Long)timeOut : 0L;
   }

   protected Properties createProperties(Hashtable env, EndPointSelector epi, String protocol, String initialRef) {
      Properties props = new Properties();
      if (env != null) {
         props.putAll(env);
      }

      if (this.mustConfigureOrbClasses(props)) {
         props.setProperty("org.omg.CORBA.ORBClass", this.getORBClass());
      }

      if (epi != null) {
         if (epi.getPort() > 0) {
            props.put("org.omg.CORBA.ORBInitialPort", Integer.toString(epi.getPort()));
         }

         props.put("weblogic.corba.orb.ORBProtocol", protocol);
         props.put("org.omg.CORBA.ORBInitialHost", epi.getHost());
      }

      return props;
   }

   private boolean mustConfigureOrbClasses(Properties props) {
      return props.getProperty("org.omg.CORBA.ORBClass") == null && this.getORBClass() != null;
   }

   public final SSLContext getSSLContext(ORB orb) {
      return (SSLContext)this.sslCtxCache.get(orb);
   }

   public int getORBTimeout(ORB orb) {
      return this.timeoutCache.containsKey(orb) ? ((Long)this.timeoutCache.get(orb)).intValue() : 0;
   }

   public String getORBClass() {
      return null;
   }

   public ORB getLocalORB() throws NamingException {
      Properties props = new Properties();
      if (this.getORBClass() != null) {
         props.setProperty("org.omg.CORBA.ORBClass", this.getORBClass());
      }

      return orbCreator.createOrb(props);
   }

   public ORBInfo getCurrent() {
      return this.currentinfo;
   }

   public void setCurrent(ORBInfo info) {
      this.currentinfo = info;
   }

   public ORBInfo createORBInfo(ORB orb, String key) {
      return new ORBInfo(orb, key);
   }

   public org.omg.CORBA.Object getORBInitialReference(String url, Hashtable env, String rir) throws NamingException {
      EndPointList info = this.getEndPointList(url);
      if (info == null) {
         throw new InvalidNameException("url `" + url + "' is invalid");
      } else {
         return (new InitialReferenceResolution(env, rir, info)).invoke();
      }
   }

   private void selectNextStartingEndPoint(Hashtable env, EndPointList.EndPointIterator iterator) {
      if (getServerAffinity(env)) {
         iterator.selectCurrentAsStart();
      } else {
         iterator.selectNextAsStart();
      }

   }

   private ORB createOrb(EndPointSelector epi, Hashtable env) {
      this.configureSystemProperties();
      ORB orb = this.createORB(env, epi, epi.getProtocol(), epi.getServiceName() + "=" + epi.getCorbalocURL());
      this.cacheORB(epi.getKey(), orb);
      return orb;
   }

   protected void configureSystemProperties() {
   }

   protected void discardOrb(ORB orb, EndPointSelector selector) {
      this.clearORBFromCache(selector.getKey());
      this.timeoutCache.remove(orb);
   }

   public synchronized void clearORBFromCache(String key) {
      this.orbInfoMap.remove(key);
      this.setCurrent((ORBInfo)null);
   }

   private synchronized EndPointList getEndPointList(String url) throws InvalidNameException {
      EndPointList endPointList = (EndPointList)this.endPointListMap.get(url);
      if (endPointList == null) {
         endPointList = EndPointList.createEndPointList(url);
         this.endPointListMap.put(url, endPointList);
      }

      return endPointList;
   }

   public void pushTransactionHelper() {
      if (this.useWlsIiopClient() && hasClass("weblogic.corba.server.transaction.TransactionHelperImpl")) {
         TransactionHelper.pushTransactionHelper(createTxHelper("weblogic.corba.server.transaction.TransactionHelperImpl"));
      } else {
         TransactionHelper.pushTransactionHelper(new TransactionHelperImpl());
      }

   }

   protected boolean useWlsIiopClient() {
      return false;
   }

   void popTransactionHelper() {
      TransactionHelper.popTransactionHelper();
   }

   private static boolean isRecoverableORBFailure(SystemException se) {
      return isRecoverableORBFailure(se, true);
   }

   public static boolean isRecoverableORBFailure(SystemException se, boolean idempotent) {
      return (se instanceof COMM_FAILURE || se instanceof MARSHAL || se instanceof OBJECT_NOT_EXIST || se instanceof BAD_PARAM && se.minor == 1330446344) && (se.completed.value() == 1 || se.completed.value() == 2 && idempotent);
   }

   private static TransactionHelper createTxHelper(String name) {
      try {
         return (TransactionHelper)Class.forName(name).newInstance();
      } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var2) {
         throw new Error(var2.toString());
      }
   }

   protected static void p(String s) {
      System.err.println("<ORBHelper> " + s);
   }

   protected void destroyORB(ORB orb) {
      try {
         Object tm = orb.getClass().getMethod("getTransportManager").invoke(orb);

         try {
            Object selector = tm.getClass().getMethod("getSelector", Integer.TYPE).invoke(tm, 0);
            selector.getClass().getMethod("close").invoke(selector);
         } catch (Exception var8) {
            if (DEBUG) {
               var8.printStackTrace();
            }
         }

         Collection acceptors = (Collection)tm.getClass().getMethod("getAcceptors").invoke(tm);
         Iterator var4 = acceptors.iterator();

         while(var4.hasNext()) {
            Object acceptor = var4.next();

            try {
               acceptor.getClass().getMethod("close").invoke(acceptor);
            } catch (Exception var7) {
               if (DEBUG) {
                  var7.printStackTrace();
               }
            }
         }
      } catch (Exception var9) {
         if (DEBUG) {
            var9.printStackTrace();
         }
      }

      orb.destroy();
   }

   private class InitialReferenceResolution {
      private Hashtable env;
      private String rir;
      private EndPointList info;
      private List causes = new ArrayList();

      InitialReferenceResolution(Hashtable env, String rir, EndPointList info) {
         this.env = env;
         this.rir = rir;
         this.info = info;
      }

      public org.omg.CORBA.Object invoke() throws NamingException {
         org.omg.CORBA.Object initialReference = null;

         EndPointList.EndPointIterator iterator;
         for(iterator = this.info.iterator(); initialReference == null && iterator.hasNext(); initialReference = this.getInitialReferenceForEndPoint(this.env, this.rir, iterator.next())) {
         }

         if (initialReference == null) {
            throw this.createNamingException();
         } else {
            ORBHelper.this.selectNextStartingEndPoint(this.env, iterator);
            return initialReference;
         }
      }

      private NamingException createNamingException() {
         if (this.causes.isEmpty()) {
            return new NamingException("Couldn't resolve initial reference: " + this.rir + "; no valid urls found in " + this.info);
         } else {
            NamingException namingException = new NamingException("Couldn't resolve initial reference: " + this.rir + " after " + this.causes.size() + " failures");
            namingException.initCause((Throwable)this.causes.get(this.causes.size() - 1));
            return namingException;
         }
      }

      private org.omg.CORBA.Object getInitialReferenceForEndPoint(Hashtable env, String rir, EndPointSelector selector) throws NamingException {
         org.omg.CORBA.Object initialReference = this.getInitialReferenceForCachedOrb(env, rir, selector);
         if (initialReference == null) {
            initialReference = this.getInitialReferenceForNewOrb(env, rir, selector);
         }

         return selector.redirectToSelectedPartition(initialReference);
      }

      private org.omg.CORBA.Object getInitialReferenceForCachedOrb(Hashtable env, String rir, EndPointSelector selector) throws NamingException {
         ORBInfo orbInfo = ORBHelper.this.getCachedORB(selector.getKey(), env);
         return orbInfo == null ? null : this.getInitialReference(orbInfo.getORB(), rir, selector);
      }

      private org.omg.CORBA.Object getInitialReferenceForNewOrb(Hashtable env, String rir, EndPointSelector selector) throws NamingException {
         return this.getInitialReference(ORBHelper.this.createOrb(selector, env), rir, selector);
      }

      private org.omg.CORBA.Object getInitialReference(ORB orb, String rir, EndPointSelector selector) throws NamingException {
         try {
            return this.clone(orb, orb.resolve_initial_references(rir));
         } catch (InvalidName var5) {
            throw Utils.wrapNamingException(var5, "Couldn't resolve initial reference: " + rir);
         } catch (SystemException var6) {
            ORBHelper.this.discardOrb(orb, selector);
            if (!ORBHelper.isRecoverableORBFailure(var6)) {
               throw Utils.wrapNamingException(var6, "Couldn't resolve initial reference: " + rir);
            } else {
               if (ORBHelper.DEBUG) {
                  this.logInitialReferenceFailure(var6);
               }

               this.causes.add(var6);
               return null;
            }
         }
      }

      private org.omg.CORBA.Object clone(ORB orb, org.omg.CORBA.Object initialReference) {
         return orb.string_to_object(orb.object_to_string(initialReference));
      }

      private void logInitialReferenceFailure(SystemException e) {
         e.printStackTrace();
      }
   }

   public interface OrbCreator {
      ORB createOrb(Properties var1);
   }

   private static class ThinClientLiteral extends AnnotationLiteral implements ThinClient {
      private ThinClientLiteral() {
      }

      // $FF: synthetic method
      ThinClientLiteral(Object x0) {
         this();
      }
   }

   private static class WlsClientLiteral extends AnnotationLiteral implements WlsClient {
      private WlsClientLiteral() {
      }

      // $FF: synthetic method
      WlsClientLiteral(Object x0) {
         this();
      }
   }
}
