package weblogic.rmi.internal.wls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import weblogic.cluster.migration.MigratableActivatingException;
import weblogic.cluster.migration.MigratableInactiveException;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.VersionInfoFactory;
import weblogic.corba.rmic.IDLMangler;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.EJBObjectEnum;
import weblogic.iiop.HostIDImpl;
import weblogic.iiop.contexts.VendorInfoCluster;
import weblogic.iiop.server.OutboundResponse;
import weblogic.jndi.Environment;
import weblogic.jndi.ThreadLocalMap;
import weblogic.jndi.internal.AdminModeAccessException;
import weblogic.jndi.internal.ThreadEnvironment;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.cluster.ClusterActivatableServerRef;
import weblogic.rmi.cluster.ClusterableServerRef;
import weblogic.rmi.cluster.EntityServerRef;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.CBVInputStream;
import weblogic.rmi.extensions.server.CBVOutputStream;
import weblogic.rmi.extensions.server.InvokableServerReference;
import weblogic.rmi.extensions.server.RemoteDomainSecurityHelper;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.internal.BasicServerRef;
import weblogic.rmi.internal.CBVInput;
import weblogic.rmi.internal.CBVOutput;
import weblogic.rmi.internal.ClusterAwareServerReference;
import weblogic.rmi.internal.NonTxRMIShutdownService;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RMIEntityResolver;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.internal.RMIShutdownService;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.InvokeHandler;
import weblogic.security.SSL.CertificateCallback;
import weblogic.security.SSL.CertificateCallbackInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.work.Work;
import weblogic.xml.jaxp.WebLogicSAXParserFactory;

public final class WLSRMIEnvironment extends RMIEnvironment {
   private static final DebugLogger debugRMIDetailed = DebugLogger.getDebugLogger("DebugRMIDetailed");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static CertificateCallback certificateCallback;
   private static final String CERTIFICATE_CALLBACK_PROPERTY = "weblogic.security.SSL.CertificateCallback";
   private static final String WLS_STUB_VERSION = "_" + VersionInfoFactory.getPeerInfo().getMajor() + VersionInfoFactory.getPeerInfo().getMinor() + VersionInfoFactory.getPeerInfo().getServicePack() + VersionInfoFactory.getPeerInfo().getRollingPatch() + "_WLStub";
   private ClusterMBean clusterMBean;

   public boolean isTracingEnabled() {
      return Kernel.isTracingEnabled();
   }

   public long getTimedOutRefIsolationTime() {
      return Kernel.getConfig().getTimedOutRefIsolationTime();
   }

   public String getIIOPSystemSecurity() {
      return Kernel.getConfig().getIIOP().getSystemSecurity();
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

   public int getTransactionTimeoutMillis() {
      DomainMBean domain = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
      JTAMBean jta = domain.getJTA();
      return jta.getTimeoutSeconds() * 1000;
   }

   public boolean isNetworkClassLoadingEnabled() {
      return !KernelStatus.isServer() || ManagementService.getRuntimeAccess(KERNEL_ID).getServer().isNetworkClassLoadingEnabled();
   }

   public boolean printExceptionStackTrace() {
      boolean productionMode = false;
      if (KernelStatus.isServer()) {
         RuntimeAccess access = ManagementService.getRuntimeAccess(KERNEL_ID);
         if (access != null) {
            productionMode = access.getDomain().isProductionModeEnabled();
         }

         return productionMode ? Kernel.getConfig().getPrintStackTraceInProduction() : true;
      } else {
         return Kernel.getConfig().getPrintStackTraceInProduction();
      }
   }

   public String getStubVersion() {
      return WLS_STUB_VERSION;
   }

   public CBVInput getCBVInput(CBVInputStream cbv, InputStream in) throws IOException {
      return new WLSCBVInput(cbv, in);
   }

   public CBVOutput getCBVOutput(CBVOutputStream cbv, OutputStream out) throws IOException {
      return new WLSCBVOutput(cbv, out);
   }

   /** @deprecated */
   @Deprecated
   public Parser getSAXParser() throws SAXException, ParserConfigurationException {
      WebLogicSAXParserFactory factory = new WebLogicSAXParserFactory();
      factory.setValidating(false);
      factory.setDisabledEntityResolutionRegistry(true);
      SAXParser saxParser = factory.newSAXParser();
      Parser parser = saxParser.getParser();
      parser.setEntityResolver(new RMIEntityResolver());
      return parser;
   }

   static boolean tryLoadingComponentInvocationContextManager() {
      try {
         Class.forName("weblogic.invocation.ComponentInvocationContextManager");
         return true;
      } catch (Exception var1) {
         return false;
      }
   }

   public Work createExecuteRequest(BasicServerRef ref, InboundRequest ir, RuntimeMethodDescriptor currentMD, InvokeHandler invoker, AuthenticatedSubject as) {
      return new WLSExecuteRequest(ref, ir, currentMD, invoker, as, RmiInvocationFacade.getCurrentComponentInvocationContext(KERNEL_ID));
   }

   public ClusterAwareServerReference createClusteredServerRef(InvokableServerReference ref) {
      return new ClusterableServerRef(ref);
   }

   public String getIIOPMangledName(Method method, Class klass) {
      return IDLMangler.getMangledMethodName(method, klass);
   }

   public boolean isIIOPResponse(Object obj) {
      return obj instanceof OutboundResponse;
   }

   public Object replaceSpecialCBVObject(Object obj) {
      return obj instanceof EJBObjectEnum ? ((EJBObjectEnum)obj).clone() : null;
   }

   public boolean isIIOPHostID(HostID hostID) {
      return hostID instanceof HostIDImpl;
   }

   public Context getContext(Object env) throws NamingException {
      return ((Environment)env).getContext((String)null);
   }

   public boolean isIIOPVendorInfoCluster(ReplicaList replicaList) {
      return replicaList instanceof VendorInfoCluster;
   }

   public boolean isIIOPInboundRequest(InboundRequest request) {
      return request instanceof weblogic.iiop.server.InboundRequest;
   }

   public Object newEnvironment() {
      return new Environment();
   }

   public Object threadEnvironmentGet() {
      return ThreadEnvironment.get();
   }

   public Hashtable threadEnvironmentGetProperties() {
      return ThreadEnvironment.getEnvironmentProperties();
   }

   public Object threadEnvironmentPop() {
      return ThreadEnvironment.pop();
   }

   public void threadEnvironmentPush(Object env) {
      ThreadEnvironment.push((Environment)env);
   }

   public Hashtable getProperties(Object env) {
      return ((Environment)env).getProperties();
   }

   public boolean isAdminModeAccessException(NamingException ne) {
      return ne instanceof AdminModeAccessException;
   }

   public ClassLoader getDescriptorClassLoader() {
      return Thread.currentThread().getContextClassLoader();
   }

   public String getClusterDefaultLoadAlgorithm() {
      if (this.clusterMBean == null) {
         this.clusterMBean = ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getCluster();
      }

      return this.clusterMBean != null ? this.clusterMBean.getDefaultLoadAlgorithm() : null;
   }

   public boolean txnAffinityEnabled() {
      if (KernelStatus.isServer() && this.clusterMBean == null) {
         this.clusterMBean = ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getCluster();
      }

      return this.clusterMBean != null && this.clusterMBean.getTxnAffinityEnabled();
   }

   public boolean isServerInCluster() {
      return this.clusterMBean != null;
   }

   public AuthenticatedSubject getCurrentSubjectForWire(AuthenticatedSubject kernelID) {
      return SecurityServiceManager.getCurrentSubjectForWire(kernelID);
   }

   public boolean rmiShutdownAcceptRequest(int oid, AuthenticatedSubject subject) {
      return RMIShutdownService.acceptRequest(oid, subject);
   }

   public boolean nonTxRmiShutdownAcceptRequest(int oid, AuthenticatedSubject subject, Object obj) {
      return NonTxRMIShutdownService.acceptRequest(oid, subject, obj);
   }

   public boolean isMigratableActivatingException(RemoteException re) {
      return re instanceof MigratableActivatingException;
   }

   public boolean isMigratableInactiveException(RemoteException re) {
      return re instanceof MigratableInactiveException;
   }

   public Hashtable getFromThreadLocalMap() {
      return ThreadLocalMap.get();
   }

   public Hashtable popFromThreadLocalMap() {
      return ThreadLocalMap.pop();
   }

   public void pushIntoThreadLocalMap(Hashtable env) {
      ThreadLocalMap.push(env);
   }

   public Object doInteropWriteReplace(Object me, PeerInfo info, int oid, Object activationID) throws RemoteException {
      if (info.getMajor() == 6 && info.getMinor() == 1) {
         ClusterActivatableServerRef serverRef = (ClusterActivatableServerRef)OIDManager.getInstance().getServerReference(oid);
         Activator activator = serverRef.getActivator();
         Object impl = activator.activate(activationID);
         EntityServerRef ref = null;

         try {
            ref = (EntityServerRef)ServerHelper.getServerReference((Remote)impl);
         } catch (NoSuchObjectException var10) {
         }

         if (ref == null) {
            ref = new EntityServerRef(impl);
         }

         ref.exportObject();
         return ref.getRemoteRef();
      } else {
         return me;
      }
   }

   public boolean isRemoteDomain(String url) throws IOException {
      return RemoteDomainSecurityHelper.isRemoteDomain(url);
   }

   public void certificateValidate(InboundRequest request, int oid) throws RemoteException {
      if (certificateCallback != null && this.canInvokeCertificateValidation(request, oid)) {
         Subject as = request.getSubject().getSubject();
         if (as != null) {
            EndPoint ep = request.getEndPoint();
            ServerIdentity si = (ServerIdentity)ep.getHostID();
            CertificateCallbackInfo callbackInfo = new CertificateCallbackInfo(si.getServerName(), si.getDomainName(), ep.getRemoteChannel().getPublicAddress(), ep.getRemoteChannel().getPublicPort(), request.getServerChannel().getPublicAddress(), request.getServerChannel().getPublicPort(), as, request.getCertificateChain());
            if (!certificateCallback.validate(callbackInfo)) {
               throw new RemoteException("Authentication Denied - certificate validation failed.");
            }
         }
      }

   }

   public boolean isRemoteAnonymousRMIT3Enabled() {
      if (!KernelStatus.isServer()) {
         return true;
      } else {
         String enabled = System.getProperty("weblogic.security.remoteAnonymousRMIT3Enabled");
         return enabled != null ? Boolean.parseBoolean(enabled) : ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().getSecurityConfiguration().isRemoteAnonymousRMIT3Enabled();
      }
   }

   public void validateAuthenticatedUser(Object user) {
      SecurityServiceManager.getSealedSubjectFromWire(KERNEL_ID, (AuthenticatedUser)user);
   }

   private boolean canInvokeCertificateValidation(InboundRequest request, int oid) {
      return KernelStatus.isServer() && !this.isBootstrapCall(oid) && oid != 2 && (request.getServerChannel().getProtocol().getQOS() == 103 || request.getServerChannel().getProtocol().getQOS() == 102);
   }

   private boolean isBootstrapCall(int oid) {
      return oid == 27;
   }

   static {
      if (KernelStatus.isServer()) {
         String certCallbackClassName = System.getProperty("weblogic.security.SSL.CertificateCallback");
         if (certCallbackClassName != null && !certCallbackClassName.equals("")) {
            try {
               Class certificateCallbackClass = Class.forName(certCallbackClassName);
               certificateCallback = (CertificateCallback)certificateCallbackClass.newInstance();
            } catch (Exception var2) {
               debugRMIDetailed.debug("Unable to instantiate certificate call back class. " + var2);
            }
         }
      }

   }

   private static final class CheckComponentInvocationContext {
      private static boolean OKAY = KernelStatus.isServer() && WLSRMIEnvironment.tryLoadingComponentInvocationContextManager();
   }
}
