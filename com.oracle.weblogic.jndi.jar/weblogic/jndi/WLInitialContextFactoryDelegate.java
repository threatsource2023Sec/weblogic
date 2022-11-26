package weblogic.jndi;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.NamingManager;
import javax.security.auth.login.LoginException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.invocation.PartitionTable;
import weblogic.jndi.internal.ExceptionTranslator;
import weblogic.jndi.internal.JNDIEnvironment;
import weblogic.jndi.internal.NamingDebugLogger;
import weblogic.jndi.internal.NamingNode;
import weblogic.jndi.internal.NamingNodeReplicaHandler;
import weblogic.jndi.internal.PartitionHandler;
import weblogic.jndi.internal.WLContextImpl;
import weblogic.jndi.internal.WLEventContextImpl;
import weblogic.jndi.internal.WLInternalContext;
import weblogic.jndi.spi.EnvironmentFactory;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ProtocolStack;
import weblogic.protocol.ServerIdentity;
import weblogic.rjvm.ClientServerURL;
import weblogic.rjvm.JVMID;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMManager;
import weblogic.rmi.cluster.ClusterableRemoteRef;
import weblogic.rmi.cluster.ReplicaAwareInfo;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.internal.BasicRemoteRef;
import weblogic.rmi.internal.ClientMethodDescriptor;
import weblogic.rmi.internal.ClientRuntimeDescriptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.spi.HostID;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.DefaultUserInfoImpl;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.acl.internal.RemoteAuthenticate;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.http.HttpParsing;

public class WLInitialContextFactoryDelegate implements InitialContextFactory, EnvironmentFactory {
   private static final DebugLogger debugConnection;
   private static final AuthenticatedSubject kernelId;
   private static Class preload;
   private static final String ROOT_NAMING_NODE_CLASS_NAME = "weblogic.jndi.internal.ServerNamingNode";
   private static final boolean enableVisibilityControl;
   private static NamingNode rootNode;
   private static final String[] ROOT_NODE_INTERFACES;
   private static final ClientMethodDescriptor DESC;
   private static final Class[] STUB_INFO_CLASS;
   private static final ClientRuntimeDescriptor ROOT_NODE_DESCRIPTOR;
   private static final ReplicaAwareInfo NAMING_NODE_RA_INFO;
   private static boolean keepEnvironmentUntilContextClose;

   public static WLInitialContextFactoryDelegate theOne() {
      return WLInitialContextFactoryDelegate.SingletonMaker.singleton;
   }

   public WLInitialContextFactoryDelegate() {
      JNDIEnvironment.getJNDIEnvironment().prepareKernel();
   }

   public final Context getInitialContext(Hashtable env) throws NamingException {
      Environment environment = new Environment(env);
      return this.getInitialContext(environment, (String)null);
   }

   public final Context getInitialContext(final Environment env, String subCtxName, final HostID hostID) throws NamingException {
      this.processLocalSchemaProviderURL(env);
      CommunicationException pendingException = null;
      IOException ioException = null;
      String url = this.getProviderURL(env);
      ServerIdentity explicitHost = env.getProviderIdentity();
      int retryTime = env.getRetryTimes();
      long retryInterval = env.getRetryInterval();
      ClientServerURL serverURL = null;
      int time = 0;

      while(true) {
         Object hostVM;
         while(true) {
            if (time > retryTime) {
               throw this.throwRetryException(pendingException, time, retryInterval);
            }

            ++time;
            hostVM = null;
            if (explicitHost != null) {
               hostVM = explicitHost;
               break;
            }

            if (url == "local://") {
               hostVM = LocalServerIdentity.getIdentity();
               break;
            }

            JNDIEnvironment.getJNDIEnvironment().pushThreadEnvironment(env);

            try {
               final ClientServerURL surl = new ClientServerURL(url);
               if (env.getSecurityPrincipal() == null && env.getSecurityCredentials() == null) {
                  hostVM = surl.findOrCreateRJVM(env.getEnableServerAffinity(), env.getProviderChannel(), hostID, (int)env.getConnectionTimeout(), env.getForceResolveDNSName()).getID();
               } else {
                  hostVM = (ServerIdentity)SecurityManager.runAs(kernelId, SubjectUtils.getAnonymousSubject(), new PrivilegedExceptionAction() {
                     public Object run() throws Exception {
                        return surl.findOrCreateRJVM(env.getEnableServerAffinity(), env.getProviderChannel(), hostID, (int)env.getConnectionTimeout(), env.getForceResolveDNSName()).getID();
                     }
                  });
               }

               if (NamingDebugLogger.isDebugEnabled()) {
                  boolean debugConn = debugConnection.isDebugEnabled();
                  if (debugConn) {
                     NamingDebugLogger.debug("Bootstrapping context from DNS " + url);
                  }
               }

               serverURL = surl;
            } catch (PrivilegedActionException var33) {
               if (var33.getException() instanceof IOException) {
                  ioException = (IOException)var33.getException();
                  break;
               }

               throw this.toNamingException(var33.getException());
            } catch (IOException var34) {
               ioException = var34;
            } catch (Exception var35) {
               throw this.toNamingException(var35);
            } finally {
               JNDIEnvironment.getJNDIEnvironment().popThreadEnvironment();
               if (ioException != null) {
                  if (NamingDebugLogger.isDebugEnabled()) {
                     NamingDebugLogger.debug("Try to create JVM connection " + time + " time, encounter : " + ioException);
                  }

                  if (time > retryTime) {
                     throw this.throwRetryException(this.toNamingException(ioException), time, retryInterval);
                  }

                  ioException = null;
                  this.retrySleep(retryInterval);
                  continue;
               }
            }
            break;
         }

         try {
            String providerUrl = url;
            if (serverURL != null) {
               providerUrl = serverURL.getCurrentURL();
            }

            Context var40 = this.newContext((ServerIdentity)hostVM, env, subCtxName, providerUrl);
            return var40;
         } catch (CommunicationException var37) {
            pendingException = var37;
            if (NamingDebugLogger.isDebugEnabled()) {
               NamingDebugLogger.debug("Try to create context " + time + " time, encounter : " + var37);
            }
         } catch (NamingException var38) {
            if (!(var38.getRootCause() instanceof IOException)) {
               throw var38;
            }

            ioException = (IOException)var38.getRootCause();
         } finally {
            if (ioException != null) {
               if (NamingDebugLogger.isDebugEnabled()) {
                  NamingDebugLogger.debug("Try to create context " + time + " time, encounter : " + ioException);
               }

               if (time > retryTime) {
                  throw this.throwRetryException(this.toNamingException(ioException), time, retryInterval);
               }

               ioException = null;
            }

         }

         this.retrySleep(retryInterval);
      }
   }

   private void retrySleep(long millis) {
      if (millis > 0L) {
         try {
            Thread.currentThread();
            Thread.sleep(millis);
         } catch (InterruptedException var4) {
         }

      }
   }

   private final NamingException throwRetryException(NamingException e, int times, long interval) {
      if (!(e instanceof CommunicationException) && !(e.getRootCause() instanceof IOException)) {
         return e;
      } else {
         NamingException thr = new CommunicationException("Failed to initialize JNDI context, tried " + times + " time or times totally, the interval of each time is " + interval + "ms. \n" + e.getMessage());
         thr.setRootCause(e.getRootCause());
         return thr;
      }
   }

   private String getProviderURL(Environment env) {
      String url = env.getClusterProviderUrl();
      if (url == null) {
         url = env.getProviderUrl();
      }

      return url;
   }

   private void processLocalSchemaProviderURL(Environment env) throws NamingException {
      String url = env.getProviderUrl();
      if (!KernelStatus.isServer() && url.toLowerCase().startsWith("local://")) {
         throw new NamingException("URL schema local:// is only supported on server side.");
      } else {
         if (url.length() > "local://".length() && url.toLowerCase().startsWith("local://")) {
            URI uri = null;

            try {
               uri = new URI(url);
            } catch (URISyntaxException var7) {
               throw new NamingException(var7.getMessage());
            }

            String queryString = uri.getQuery();
            if (null == queryString) {
               return;
            }

            Hashtable queryStringMap = new Hashtable();
            HttpParsing.parseQueryString(queryString, queryStringMap);
            String partitionInfo = (String)queryStringMap.get("partitionName");
            if (null == partitionInfo) {
               return;
            }

            if (!"DOMAIN".equals(partitionInfo)) {
               PartitionHandler.checkPartition(partitionInfo);
            }

            env.setProviderUrl("local://");
            if (env.getProperty("weblogic.jndi.partitionInformation") == null) {
               env.setProperty("weblogic.jndi.partitionInformation", partitionInfo);
            }
         }

      }
   }

   public Remote getInitialReference(Environment env, Class implClass) throws NamingException {
      this.processLocalSchemaProviderURL(env);
      String url = this.getProviderURL(env);
      ServerIdentity hostVM = env.getProviderIdentity();
      if (hostVM == null) {
         if (url == "local://") {
            hostVM = LocalServerIdentity.getIdentity();
         } else {
            JNDIEnvironment.getJNDIEnvironment().pushThreadEnvironment(env);

            try {
               hostVM = this.getHostVM(env, url);
            } catch (IOException var17) {
               throw this.toNamingException(var17);
            } finally {
               JNDIEnvironment.getJNDIEnvironment().popThreadEnvironment();
            }
         }
      }

      JNDIEnvironment.getJNDIEnvironment().pushThreadEnvironment(env);

      Remote var7;
      try {
         long responseReadTimeout = env.getResponseReadTimeout();
         if (responseReadTimeout > 0L) {
            var7 = (Remote)StubFactory.getStubWithTimeout(implClass, hostVM, env.getProviderChannel(), responseReadTimeout);
            return var7;
         }

         var7 = (Remote)StubFactory.getStub(implClass, hostVM, env.getProviderChannel());
      } catch (RemoteException var19) {
         throw this.toNamingException(var19);
      } finally {
         JNDIEnvironment.getJNDIEnvironment().popThreadEnvironment();
      }

      return var7;
   }

   ServerIdentity getHostVM(Environment env, String url) throws IOException {
      ServerIdentity hostVM = (new ClientServerURL(url)).findOrCreateRJVM(env.getEnableServerAffinity(), env.getProviderChannel(), (HostID)null, (int)env.getConnectionTimeout(), env.getForceResolveDNSName()).getID();
      if (NamingDebugLogger.isDebugEnabled()) {
         boolean debugConn = debugConnection.isDebugEnabled();
         if (debugConn) {
            NamingDebugLogger.debug("Bootstrapping reference from DNS " + url);
         }
      }

      return hostVM;
   }

   public Context getInitialContext(Environment environment, String contextName) throws NamingException {
      return this.getInitialContext(environment, contextName, (HostID)null);
   }

   private Context newContext(ServerIdentity hostVM, Environment env, String subCtxName, String providerUrl) throws NamingException {
      Context initialContext = null;
      JNDIEnvironment.getJNDIEnvironment().pushThreadEnvironment(env);

      try {
         this.pushSubject(env, hostVM, providerUrl);
         JNDIEnvironment.getJNDIEnvironment().activateTransactionHelper();

         try {
            if (hostVM.isLocal()) {
               if (enableVisibilityControl && this.isRemoteAccessWithLocalRJVM(env, providerUrl)) {
                  initialContext = this.newRemoteContext(JVMID.localRemoteID(), env, subCtxName, providerUrl);
               } else {
                  initialContext = this.newLocalContext(env, subCtxName, providerUrl);
               }
            } else {
               initialContext = this.newRemoteContext(hostVM, env, subCtxName, providerUrl);
            }

            if (env.getSecurityUser() != null || env.isClientCertAvailable() || env.isLocalIdentitySet()) {
               ((WLInternalContext)initialContext).enableLogoutOnClose();
            }

            this.cacheConnectionTimeout(hostVM, env, initialContext);
         } catch (RemoteException var15) {
            throw this.toNamingException(var15);
         } finally {
            if (initialContext == null) {
               this.popSubject(env);
               JNDIEnvironment.getJNDIEnvironment().deactivateTransactionHelper();
            }

         }
      } finally {
         if (!keepEnvironmentUntilContextClose) {
            JNDIEnvironment.getJNDIEnvironment().popThreadEnvironment();
         }

      }

      return initialContext;
   }

   private boolean isRemoteAccessWithLocalRJVM(Environment env, String providerUrl) throws NamingException {
      if (providerUrl.startsWith("local://")) {
         return false;
      } else {
         String partitionName = this.getPartitionNameFrom(env, providerUrl);
         if (partitionName == null) {
            return false;
         } else {
            return !partitionName.equals(this.getPartitionName());
         }
      }
   }

   private void cacheConnectionTimeout(ServerIdentity hostVM, Environment env, Context initialContext) {
      long connectionTimeout = env.getConnectionTimeout();
      if (connectionTimeout > 0L) {
         JNDIEnvironment.getJNDIEnvironment().storeConnectionTimeout(initialContext, hostVM, connectionTimeout);
      }

   }

   private Context newLocalContext(Environment env, String subCtxName, String providerUrl) throws NamingException {
      Debug.assertion(Thread.currentThread().getContextClassLoader() != null, "ContextClassLoader == null");
      Hashtable delegateEnv = env.getDelegateEnvironment();
      if (delegateEnv != null) {
         this.popSubject(env);
         return NamingManager.getInitialContext(delegateEnv);
      } else {
         try {
            NamingNode stub = this.getRootNode();
            Hashtable properties = env.getProperties();
            if (properties != null && !properties.containsKey("weblogic.jndi.partitionInformation")) {
               String partitionName = null;
               if (providerUrl != "local://") {
                  partitionName = this.getPartitionName(providerUrl);
               } else {
                  partitionName = this.getPartitionName();
               }

               if (partitionName != null) {
                  properties.put("weblogic.jndi.partitionInformation", partitionName);
               }
            }

            Context ctx = new WLEventContextImpl(properties, stub);
            return (Context)(subCtxName != null && subCtxName.length() != 0 ? (Context)ctx.lookup(subCtxName) : ctx);
         } catch (NoSuchObjectException var8) {
            throw new NoInitialContextException("JNDI subsystem is not ready for use ");
         }
      }
   }

   private NamingNode getRootNode() throws NoSuchObjectException {
      if (rootNode == null) {
         rootNode = (NamingNode)ServerHelper.getRemoteObject(9);
      }

      return rootNode;
   }

   private Context newRemoteContext(ServerIdentity hostVM, Environment env, String subCtxName, String providerUrl) throws NamingException, RemoteException {
      Hashtable delegateEnv = env.getDelegateEnvironment();
      if (delegateEnv != null) {
         return JNDIEnvironment.getJNDIEnvironment().getDelegateContext(hostVM, env, subCtxName);
      } else {
         BasicRemoteRef basicRef = new BasicRemoteRef(9, hostVM, env.getProviderChannel());
         NamingNode stub = this.newRootNamingNodeStub(basicRef, providerUrl);
         Context ctx = new WLContextImpl(env.getRemoteProperties(), stub);
         return (Context)(subCtxName != null && subCtxName.length() != 0 ? (Context)ctx.lookup(subCtxName) : ctx);
      }
   }

   private NamingNode newRootNamingNodeStub(RemoteReference ref, String providerUrl) {
      ClientRuntimeDescriptor desc = ROOT_NODE_DESCRIPTOR;
      ClusterableRemoteRef raRef = new ClusterableRemoteRef(ref);
      raRef.initialize(NAMING_NODE_RA_INFO);
      StubInfo info = new StubInfo(raRef, desc, ServerHelper.getStubClassName("weblogic.jndi.internal.ServerNamingNode"), (String)null, providerUrl, (String)null);

      try {
         Class stubCls = Class.forName(ServerHelper.getStubClassName("weblogic.jndi.internal.ServerNamingNode"));
         Constructor con = stubCls.getConstructor(STUB_INFO_CLASS);
         return (NamingNode)con.newInstance(info);
      } catch (ClassNotFoundException var8) {
         throw new AssertionError(var8);
      } catch (Exception var9) {
         throw new AssertionError(var9);
      }
   }

   private final void pushSubject(Environment env, ServerIdentity rjvm, String providerUrl) throws NamingException {
      Protocol protocol = null;

      try {
         protocol = this.getProtocol(env);
         pushProtocol(env, protocol);
      } catch (MalformedURLException var26) {
         throw new AssertionError(var26);
      }

      UserInfo user = env.getSecurityUser();
      if (user == null && protocol.isSecure() && (env.isClientCertAvailable() || env.isLocalIdentitySet())) {
         user = new DefaultUserInfoImpl((String)null, (Object)null);
      }

      AuthenticatedUser au = null;
      boolean isServer = rjvm.isLocal();
      AuthenticatedSubject subject = null;
      if (user != null) {
         if (env.getSecuritySubject() != null) {
            subject = env.getSecuritySubject();
         } else if (isServer) {
            try {
               String newPartitionName = this.getPartitionNameFrom(env, providerUrl);
               if (null != newPartitionName && !newPartitionName.equals(this.getPartitionName())) {
                  ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(kernelId);
                  ManagedInvocationContext mic = cicm.setCurrentComponentInvocationContext(cicm.createComponentInvocationContext(newPartitionName));
                  Throwable var12 = null;

                  try {
                     subject = this.authenticateLocally((UserInfo)user);
                  } catch (Throwable var25) {
                     var12 = var25;
                     throw var25;
                  } finally {
                     if (mic != null) {
                        if (var12 != null) {
                           try {
                              mic.close();
                           } catch (Throwable var23) {
                              var12.addSuppressed(var23);
                           }
                        } else {
                           mic.close();
                        }
                     }

                  }
               } else {
                  subject = this.authenticateLocally((UserInfo)user);
               }
            } catch (LoginException var28) {
               popProtocol(env);
               throw this.toNamingException(var28);
            }
         } else {
            try {
               subject = this.authenticateRemotely(protocol, env, (UserInfo)user, rjvm, providerUrl);
            } catch (RemoteException | SecurityException var24) {
               popProtocol(env);
               throw this.toNamingException(var24);
            }
         }
      }

      if (subject != null) {
         env.setSecuritySubject(subject);
         JNDIEnvironment.getJNDIEnvironment().pushSubject(kernelId, subject);
      }

   }

   private AuthenticatedSubject authenticateLocally(UserInfo user) throws LoginException {
      String realmName = "weblogicDEFAULT";
      PrincipalAuthenticator pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(kernelId, realmName, ServiceType.AUTHENTICATION);
      AuthenticatedSubject subject = null;
      if (user instanceof DefaultUserInfoImpl) {
         DefaultUserInfoImpl u = (DefaultUserInfoImpl)user;
         SimpleCallbackHandler handler = new SimpleCallbackHandler(u.getName(), u.getPassword());
         subject = pa.authenticate(handler);
      }

      return subject;
   }

   private Protocol getProtocol(Environment env) throws MalformedURLException {
      String url = env.getProviderUrl();
      Protocol protocol = null;
      if (url == "local://") {
         protocol = ProtocolManager.getProtocolByName("t3");
      } else {
         protocol = ProtocolManager.getProtocolByName((new ClientServerURL(url)).getProtocol());
      }

      if (protocol.isUnknown()) {
         throw new MalformedURLException("No support for protocol: " + url);
      } else {
         return protocol;
      }
   }

   private AuthenticatedSubject authenticateRemotely(Protocol protocol, Environment env, UserInfo user, ServerIdentity id, String providerUrl) throws RemoteException {
      RJVM rjvm = RJVMManager.getRJVMManager().findOrCreate((JVMID)id);
      int timeout = (int)env.getResponseReadTimeout();
      AuthenticatedUser au = RemoteAuthenticate.authenticate(user, rjvm, protocol, env.getProviderChannel(), env.getEnableDefaultUser(), timeout, providerUrl);
      rjvm.setUser(providerUrl, au);
      return JNDIEnvironment.getJNDIEnvironment().getASFromAU(au);
   }

   private void popSubject(Environment env) {
      popProtocol(env);
      if (env.getSecuritySubject() != null) {
         SecurityServiceManager.popSubject(kernelId);
         JNDIEnvironment.getJNDIEnvironment().popSubject(kernelId);
      }

   }

   private static void pushProtocol(Environment env, Protocol protocol) {
      if (env.getProviderUrl() != "local://") {
         ProtocolStack.push(protocol);
      }

   }

   private static void popProtocol(Environment env) {
      if (env.getProviderUrl() != "local://") {
         ProtocolStack.pop();
      }

   }

   private final NamingException toNamingException(Throwable e) {
      NamingException ne = ExceptionTranslator.toNamingException(e);
      if (NamingDebugLogger.isDebugEnabled()) {
         boolean debugConn = debugConnection.isDebugEnabled();
         if (debugConn && ne instanceof CommunicationException) {
            String exceptionDesc = StackTraceUtils.throwable2StackTrace(ne.getRootCause());
            NamingDebugLogger.debug("Failed to create initial context due to: " + exceptionDesc);
         }
      }

      return ne;
   }

   private String getPartitionName() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      return cic.getPartitionName();
   }

   private String getPartitionName(String url) throws NamingException {
      String partitionName = null;

      try {
         partitionName = PartitionTable.getInstance().lookup(url).getPartitionName();
         return partitionName;
      } catch (URISyntaxException var4) {
         NamingDebugLogger.debug("Failed to get partition name from url : " + url + "  because " + var4.getMessage());
         throw this.toNamingException(var4);
      }
   }

   private String getPartitionNameFrom(Environment env, String providerURL) throws NamingException {
      String partitionName = null;
      if (null != providerURL && !providerURL.startsWith("local://")) {
         partitionName = this.getPartitionName(providerURL);
      }

      if (null == partitionName) {
         partitionName = (String)env.getProperty("weblogic.jndi.partitionInformation");
      }

      return partitionName;
   }

   static {
      JNDIEnvironment.getJNDIEnvironment().prepareSubjectManager();
      debugConnection = DebugLogger.getDebugLogger("DebugConnection");
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      preload = ExceptionTranslator.class;
      enableVisibilityControl = System.getProperty("weblogic.jndi.mt.enableVisibilityControl") == null ? true : Boolean.parseBoolean(System.getProperty("weblogic.jndi.mt.enableVisibilityControl"));
      ROOT_NODE_INTERFACES = new String[]{NamingNode.class.getName(), StubInfoIntf.class.getName()};
      DESC = new ClientMethodDescriptor("*", false, false, false, false, 0);
      STUB_INFO_CLASS = new Class[]{StubInfo.class};
      ROOT_NODE_DESCRIPTOR = (new ClientRuntimeDescriptor(ROOT_NODE_INTERFACES, (String)null, (Map)null, DESC, ServerHelper.getStubClassName("weblogic.jndi.internal.ServerNamingNode"))).intern();
      NAMING_NODE_RA_INFO = new ReplicaAwareInfo("", NamingNodeReplicaHandler.class);
      keepEnvironmentUntilContextClose = false;
      if (KernelStatus.isServer()) {
         String s = System.getProperty("weblogic.jndi.retainenvironment");
         if (s != null) {
            keepEnvironmentUntilContextClose = true;
         }
      }

   }

   static class SingletonMaker {
      static final WLInitialContextFactoryDelegate singleton = new WLInitialContextFactoryDelegate();
   }
}
