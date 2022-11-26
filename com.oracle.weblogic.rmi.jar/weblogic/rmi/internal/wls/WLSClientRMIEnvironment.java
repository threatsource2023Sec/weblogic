package weblogic.rmi.internal.wls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.VersionInfoFactory;
import weblogic.jndi.Environment;
import weblogic.kernel.Kernel;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.extensions.server.CBVInputStream;
import weblogic.rmi.extensions.server.CBVOutputStream;
import weblogic.rmi.extensions.server.InvokableServerReference;
import weblogic.rmi.extensions.server.ReferenceHelper;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.BasicServerRef;
import weblogic.rmi.internal.CBVInput;
import weblogic.rmi.internal.CBVOutput;
import weblogic.rmi.internal.ClusterAwareServerReference;
import weblogic.rmi.internal.DefaultCBVInput;
import weblogic.rmi.internal.DefaultCBVOutput;
import weblogic.rmi.internal.DefaultExecuteRequest;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.InvokeHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.XXEUtils;
import weblogic.work.Work;

public class WLSClientRMIEnvironment extends RMIEnvironment {
   private static final String WLS_STUB_VERSION = "_" + VersionInfoFactory.getPeerInfo().getMajor() + VersionInfoFactory.getPeerInfo().getMinor() + VersionInfoFactory.getPeerInfo().getServicePack() + VersionInfoFactory.getPeerInfo().getRollingPatch() + "_WLStub";
   private static final String DEFAULT_STUB_VERSION;
   private static final String DEFAULT_SYSTEM_SECURITY = "supported";
   private static final boolean DEFAULT_NETWORK_CLASS_LOADING = false;
   private static final String NETWORK_CLASS_LOADING_PROP = "weblogic.rmi.networkclassloadingenabled";
   private static final int DEFAULT_TRAN_TIMEOUT = 30000;
   private static final boolean SECURITY_MANAGER_ENABLED;

   public WLSClientRMIEnvironment() {
      ReferenceHelper.setReferenceHelper(new CEReferenceHelperImpl());
   }

   public long getTimedOutRefIsolationTime() {
      return Kernel.getConfig().getTimedOutRefIsolationTime();
   }

   public boolean isTracingEnabled() {
      return Kernel.isTracingEnabled();
   }

   public String getIIOPSystemSecurity() {
      return "supported";
   }

   public boolean isLogRemoteExceptions() {
      return Kernel.getConfig().isLogRemoteExceptionsEnabled();
   }

   public int getHeartbeatPeriodLength() {
      return Kernel.getConfig().getPeriodLength();
   }

   public boolean isRefreshClientRuntimeDescriptor() {
      return Kernel.getConfig().getRefreshClientRuntimeDescriptor();
   }

   public int getDGCIdlePeriodsUntilTimeout() {
      return Kernel.getConfig().getDGCIdlePeriodsUntilTimeout();
   }

   public boolean isInstrumentStackTrace() {
      return Kernel.getConfig().isInstrumentStackTraceEnabled();
   }

   public boolean isNetworkClassLoadingEnabled() {
      return SECURITY_MANAGER_ENABLED ? (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
         public Boolean run() {
            return WLSClientRMIEnvironment.this.getNetworkClassLoadingEnabledValue();
         }
      }) : this.getNetworkClassLoadingEnabledValue();
   }

   private boolean getNetworkClassLoadingEnabledValue() {
      return System.getProperty("weblogic.rmi.networkclassloadingenabled") != null ? Boolean.getBoolean("weblogic.rmi.networkclassloadingenabled") : false;
   }

   public boolean printExceptionStackTrace() {
      return true;
   }

   public int getTransactionTimeoutMillis() {
      return 30000;
   }

   public String getStubVersion() {
      return DEFAULT_STUB_VERSION;
   }

   public CBVInput getCBVInput(CBVInputStream cbv, InputStream in) throws IOException {
      return new DefaultCBVInput(cbv, in);
   }

   public CBVOutput getCBVOutput(CBVOutputStream cbv, OutputStream out) throws IOException {
      return new DefaultCBVOutput(cbv, out);
   }

   /** @deprecated */
   @Deprecated
   public Parser getSAXParser() throws SAXException, ParserConfigurationException {
      SAXParserFactory factory = XXEUtils.createSAXParserFactoryInstance();
      factory.setValidating(true);
      SAXParser parser = factory.newSAXParser();
      Parser ret = parser.getParser();
      ret.setEntityResolver(new DefaultRMIEntityResolver());
      ret.setErrorHandler(new DefaultErrorHandler());
      return ret;
   }

   public Work createExecuteRequest(BasicServerRef ref, InboundRequest ir, RuntimeMethodDescriptor currentMD, InvokeHandler invoker, AuthenticatedSubject as) {
      return new DefaultExecuteRequest(ref, ir, currentMD, invoker, as);
   }

   public ClusterAwareServerReference createClusteredServerRef(InvokableServerReference ref) {
      throw new AssertionError("Clustering not supported");
   }

   public String getIIOPMangledName(Method method, Class klass) {
      throw new AssertionError("RMI over IIOP not supported");
   }

   public boolean isIIOPResponse(Object obj) {
      return false;
   }

   public Object replaceSpecialCBVObject(Object obj) {
      return null;
   }

   public ClassLoader getDescriptorClassLoader() {
      return Thread.currentThread().getContextClassLoader();
   }

   public boolean isIIOPHostID(HostID hostID) {
      return false;
   }

   public Context getContext(Object env) throws NamingException {
      return ((Environment)env).getContext((String)null);
   }

   public boolean isIIOPVendorInfoCluster(ReplicaList replicaList) {
      return false;
   }

   public boolean isIIOPInboundRequest(InboundRequest request) {
      return false;
   }

   public Object newEnvironment() {
      try {
         Class c = Class.forName("weblogic.jndi.Environment");
         Constructor constructor = c.getConstructor();
         return constructor.newInstance((Object[])null);
      } catch (ClassNotFoundException var3) {
         throw new AssertionError(var3);
      } catch (SecurityException var4) {
         throw new AssertionError(var4);
      } catch (NoSuchMethodException var5) {
         throw new AssertionError(var5);
      } catch (IllegalArgumentException var6) {
         throw new AssertionError(var6);
      } catch (InstantiationException var7) {
         throw new AssertionError(var7);
      } catch (IllegalAccessException var8) {
         throw new AssertionError(var8);
      } catch (InvocationTargetException var9) {
         throw new AssertionError(var9);
      }
   }

   public Object threadEnvironmentGet() {
      try {
         Class c = Class.forName("weblogic.jndi.internal.ThreadEnvironment");
         Method method = c.getMethod("get");
         return method.invoke((Object)null);
      } catch (ClassNotFoundException var3) {
         throw new AssertionError(var3);
      } catch (SecurityException var4) {
         throw new AssertionError(var4);
      } catch (NoSuchMethodException var5) {
         throw new AssertionError(var5);
      } catch (IllegalArgumentException var6) {
         throw new AssertionError(var6);
      } catch (IllegalAccessException var7) {
         throw new AssertionError(var7);
      } catch (InvocationTargetException var8) {
         throw new AssertionError(var8);
      }
   }

   public Object threadEnvironmentGetProperties() {
      try {
         Class c = Class.forName("weblogic.jndi.internal.ThreadEnvironment");
         Method method = c.getMethod("getEnvironmentProperties");
         return method.invoke((Object)null);
      } catch (ClassNotFoundException var3) {
         throw new AssertionError(var3);
      } catch (SecurityException var4) {
         throw new AssertionError(var4);
      } catch (NoSuchMethodException var5) {
         throw new AssertionError(var5);
      } catch (IllegalArgumentException var6) {
         throw new AssertionError(var6);
      } catch (IllegalAccessException var7) {
         throw new AssertionError(var7);
      } catch (InvocationTargetException var8) {
         throw new AssertionError(var8);
      }
   }

   public Object threadEnvironmentPop() {
      try {
         Class c = Class.forName("weblogic.jndi.internal.ThreadEnvironment");
         Method method = c.getMethod("pop");
         return method.invoke((Object)null);
      } catch (ClassNotFoundException var3) {
         throw new AssertionError(var3);
      } catch (SecurityException var4) {
         throw new AssertionError(var4);
      } catch (NoSuchMethodException var5) {
         throw new AssertionError(var5);
      } catch (IllegalArgumentException var6) {
         throw new AssertionError(var6);
      } catch (IllegalAccessException var7) {
         throw new AssertionError(var7);
      } catch (InvocationTargetException var8) {
         throw new AssertionError(var8);
      }
   }

   public void threadEnvironmentPush(Object env) {
      try {
         Class c = Class.forName("weblogic.jndi.internal.ThreadEnvironment");
         Class classEnvironment = Class.forName("weblogic.jndi.Environment");
         Class[] argTypes = new Class[]{classEnvironment};
         Method method = c.getMethod("push", argTypes);
         Object[] args = new Object[]{env};
         method.invoke((Object)null, args);
      } catch (ClassNotFoundException var7) {
         throw new AssertionError(var7);
      } catch (SecurityException var8) {
         throw new AssertionError(var8);
      } catch (NoSuchMethodException var9) {
         throw new AssertionError(var9);
      } catch (IllegalArgumentException var10) {
         throw new AssertionError(var10);
      } catch (IllegalAccessException var11) {
         throw new AssertionError(var11);
      } catch (InvocationTargetException var12) {
         throw new AssertionError(var12);
      }
   }

   public Hashtable getProperties(Object env) {
      return ((Environment)env).getProperties();
   }

   public boolean isAdminModeAccessException(NamingException ne) {
      return false;
   }

   public String getClusterDefaultLoadAlgorithm() {
      return "round-robin";
   }

   public boolean isMigratableActivatingException(RemoteException re) {
      return false;
   }

   public boolean isMigratableInactiveException(RemoteException re) {
      return false;
   }

   public boolean isServerInCluster() {
      return false;
   }

   public AuthenticatedSubject getCurrentSubjectForWire(AuthenticatedSubject kernelID) {
      return SecurityServiceManager.getCurrentSubjectForWire(kernelID);
   }

   public boolean rmiShutdownAcceptRequest(int oid, AuthenticatedSubject subject) {
      return true;
   }

   public boolean nonTxRmiShutdownAcceptRequest(int oid, AuthenticatedSubject subject, Object obj) {
      return true;
   }

   public Hashtable getFromThreadLocalMap() {
      try {
         Class c = Class.forName("weblogic.jndi.ThreadLocalMap");
         Method method = c.getMethod("get", (Class[])null);
         return (Hashtable)method.invoke((Object)null);
      } catch (ClassNotFoundException var3) {
         throw new AssertionError(var3);
      } catch (SecurityException var4) {
         throw new AssertionError(var4);
      } catch (NoSuchMethodException var5) {
         throw new AssertionError(var5);
      } catch (IllegalArgumentException var6) {
         throw new AssertionError(var6);
      } catch (IllegalAccessException var7) {
         throw new AssertionError(var7);
      } catch (InvocationTargetException var8) {
         throw new AssertionError(var8);
      }
   }

   public Hashtable popFromThreadLocalMap() {
      try {
         Class c = Class.forName("weblogic.jndi.ThreadLocalMap");
         Method method = c.getMethod("pop", (Class[])null);
         return (Hashtable)method.invoke((Object)null);
      } catch (ClassNotFoundException var3) {
         throw new AssertionError(var3);
      } catch (SecurityException var4) {
         throw new AssertionError(var4);
      } catch (NoSuchMethodException var5) {
         throw new AssertionError(var5);
      } catch (IllegalArgumentException var6) {
         throw new AssertionError(var6);
      } catch (IllegalAccessException var7) {
         throw new AssertionError(var7);
      } catch (InvocationTargetException var8) {
         throw new AssertionError(var8);
      }
   }

   public void pushIntoThreadLocalMap(Hashtable env) {
      try {
         Class c = Class.forName("weblogic.jndi.ThreadLocalMap");
         Class classEnvironment = Class.forName("java.util.Hashtable");
         Class[] argTypes = new Class[]{classEnvironment};
         Method method = c.getMethod("push", argTypes);
         Object[] args = new Object[]{env};
         method.invoke((Object)null, args);
      } catch (ClassNotFoundException var7) {
         throw new AssertionError(var7);
      } catch (SecurityException var8) {
         throw new AssertionError(var8);
      } catch (NoSuchMethodException var9) {
         throw new AssertionError(var9);
      } catch (IllegalArgumentException var10) {
         throw new AssertionError(var10);
      } catch (IllegalAccessException var11) {
         throw new AssertionError(var11);
      } catch (InvocationTargetException var12) {
         throw new AssertionError(var12);
      }
   }

   public Object doInteropWriteReplace(Object me, PeerInfo info, int oid, Object activationID) throws RemoteException {
      if (info.getMajor() == 6 && info.getMinor() == 1) {
         throw new AssertionError("Release 6.1 is not supported");
      } else {
         return me;
      }
   }

   public boolean isRemoteDomain(String url) throws IOException, RemoteException {
      throw new AssertionError("It is not supported in the thin client");
   }

   public void certificateValidate(InboundRequest request, int oid) throws RemoteException {
   }

   public boolean isRemoteAnonymousRMIT3Enabled() {
      return true;
   }

   public void validateAuthenticatedUser(Object user) {
   }

   static {
      DEFAULT_STUB_VERSION = WLS_STUB_VERSION;
      SECURITY_MANAGER_ENABLED = System.getSecurityManager() != null;
   }

   private static final class DefaultErrorHandler implements ErrorHandler {
      private DefaultErrorHandler() {
      }

      public void error(SAXParseException e) throws SAXException {
         throw e;
      }

      public void fatalError(SAXParseException e) throws SAXException {
         throw e;
      }

      public void warning(SAXParseException e) {
      }

      // $FF: synthetic method
      DefaultErrorHandler(Object x0) {
         this();
      }
   }

   private static final class DefaultRMIEntityResolver implements EntityResolver {
      private DefaultRMIEntityResolver() {
      }

      public InputSource resolveEntity(String publicId, String systemId) {
         int last = systemId.lastIndexOf(47);
         String lastPart;
         if (last >= 0) {
            lastPart = systemId.substring(last + 1);
         } else {
            lastPart = systemId;
         }

         if (lastPart != null && lastPart.equals("rmi.dtd")) {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("weblogic/rmi/internal/rmi.dtd");
            if (in != null) {
               return new InputSource(in);
            }
         }

         return null;
      }

      // $FF: synthetic method
      DefaultRMIEntityResolver(Object x0) {
         this();
      }
   }
}
