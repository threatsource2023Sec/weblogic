package weblogic.rmi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import weblogic.common.internal.PeerInfo;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.extensions.server.CBVInputStream;
import weblogic.rmi.extensions.server.CBVOutputStream;
import weblogic.rmi.extensions.server.InvokableServerReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.InvokeHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.work.Work;

public abstract class RMIEnvironment {
   private static RMIEnvironment singleton;

   public static RMIEnvironment getEnvironment() {
      return singleton;
   }

   public abstract boolean isTracingEnabled();

   public abstract boolean isLogRemoteExceptions();

   public abstract boolean isInstrumentStackTrace();

   public abstract boolean isRefreshClientRuntimeDescriptor();

   public abstract long getTimedOutRefIsolationTime();

   public abstract String getIIOPSystemSecurity();

   public abstract int getHeartbeatPeriodLength();

   public abstract int getDGCIdlePeriodsUntilTimeout();

   public abstract int getTransactionTimeoutMillis();

   public abstract boolean isNetworkClassLoadingEnabled();

   public abstract boolean printExceptionStackTrace();

   public abstract String getStubVersion();

   public abstract CBVOutput getCBVOutput(CBVOutputStream var1, OutputStream var2) throws IOException;

   public abstract CBVInput getCBVInput(CBVInputStream var1, InputStream var2) throws IOException;

   /** @deprecated */
   @Deprecated
   public abstract Parser getSAXParser() throws SAXException, ParserConfigurationException;

   public abstract Work createExecuteRequest(BasicServerRef var1, InboundRequest var2, RuntimeMethodDescriptor var3, InvokeHandler var4, AuthenticatedSubject var5);

   public abstract ClusterAwareServerReference createClusteredServerRef(InvokableServerReference var1);

   public abstract String getIIOPMangledName(Method var1, Class var2);

   public abstract boolean isIIOPResponse(Object var1);

   public abstract boolean isIIOPHostID(HostID var1);

   public abstract boolean isIIOPVendorInfoCluster(ReplicaList var1);

   public abstract boolean isIIOPInboundRequest(InboundRequest var1);

   public abstract Object replaceSpecialCBVObject(Object var1);

   public abstract ClassLoader getDescriptorClassLoader();

   public abstract Object newEnvironment();

   public abstract Object threadEnvironmentGet();

   public abstract Object threadEnvironmentPop();

   public abstract void threadEnvironmentPush(Object var1);

   public abstract Context getContext(Object var1) throws NamingException;

   public abstract Hashtable getProperties(Object var1);

   public abstract boolean isAdminModeAccessException(NamingException var1);

   public abstract String getClusterDefaultLoadAlgorithm();

   public abstract boolean isServerInCluster();

   public abstract AuthenticatedSubject getCurrentSubjectForWire(AuthenticatedSubject var1);

   public abstract boolean isMigratableInactiveException(RemoteException var1);

   public abstract boolean isMigratableActivatingException(RemoteException var1);

   public abstract boolean rmiShutdownAcceptRequest(int var1, AuthenticatedSubject var2);

   public abstract boolean nonTxRmiShutdownAcceptRequest(int var1, AuthenticatedSubject var2, Object var3);

   public abstract Hashtable getFromThreadLocalMap();

   public abstract Hashtable popFromThreadLocalMap();

   public abstract void pushIntoThreadLocalMap(Hashtable var1);

   public boolean txnAffinityEnabled() {
      return false;
   }

   public abstract Object doInteropWriteReplace(Object var1, PeerInfo var2, int var3, Object var4) throws RemoteException;

   public abstract boolean isRemoteDomain(String var1) throws IOException, RemoteException;

   public abstract void certificateValidate(InboundRequest var1, int var2) throws RemoteException;

   public abstract boolean isRemoteAnonymousRMIT3Enabled();

   public abstract void validateAuthenticatedUser(Object var1);

   static {
      try {
         Class wlsClass = Class.forName("weblogic.rmi.internal.wls.WLSRMIEnvironment");
         singleton = (RMIEnvironment)wlsClass.newInstance();
      } catch (Throwable var5) {
         try {
            Class wlsClientClass = Class.forName("weblogic.rmi.internal.wls.WLSClientRMIEnvironment");
            singleton = (RMIEnvironment)wlsClientClass.newInstance();
         } catch (Throwable var4) {
            try {
               Class defaultClass = Class.forName("weblogic.rmi.internal.DefaultRMIEnvironment");
               singleton = (RMIEnvironment)defaultClass.newInstance();
            } catch (Throwable var3) {
               throw new IllegalArgumentException(var3.toString());
            }
         }
      }

   }
}
