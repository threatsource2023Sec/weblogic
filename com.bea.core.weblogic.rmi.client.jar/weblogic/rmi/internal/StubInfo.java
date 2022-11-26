package weblogic.rmi.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.common.internal.CodeBaseInfo;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.ServerURL;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.extensions.server.ActivatableRemoteReference;
import weblogic.rmi.extensions.server.ActivatableServerReference;
import weblogic.rmi.extensions.server.CollocatedRemoteReference;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.reflect.MethodSignatureBuilder;

public class StubInfo implements Serializable, StubReference {
   private static final boolean debug = false;
   static final long serialVersionUID = 1386046997462080210L;
   private static final Class DEFAULT_STUB_BASE = Stub.class;
   private ClientRuntimeDescriptor desc;
   private RemoteReference ref;
   private String stubName;
   private transient String repositoryId;
   private String stubBaseName;
   private static final transient boolean refreshClientRuntimeDescriptor;
   private transient int jndiSpecifiedTimeout;
   private transient String providerURL;
   private transient ServerURL sURL;
   private final String partitionName;
   private static final String JNDI_RESPONSE_READ_TIMEOUT = "weblogic.jndi.responseReadTimeout";
   private static final String JNDI_RESPONSE_READ_TIMEOUT_DEPRECATED = "weblogic.rmi.clientTimeout";
   private static final String JNDI_PROVIDER_URL = "java.naming.provider.url";
   private static final AuthenticatedSubject KERNEL_ID;
   private static ComponentInvocationContextManager CICM;

   public StubInfo(RemoteReference ref, ClientRuntimeDescriptor crd, String stubName) {
      this(ref, crd, stubName, (String)null);
   }

   public StubInfo(RemoteReference ref, ClientRuntimeDescriptor crd, String stubName, String stubBaseName) {
      this(ref, crd, stubName, stubBaseName, (String)null, KernelStatus.isServer() ? RmiInvocationFacade.getCurrentPartitionName(KERNEL_ID) : "DOMAIN");
   }

   public StubInfo(RemoteReference ref, ClientRuntimeDescriptor crd, String stubName, String stubBaseName, String providerURL, String partitionName) {
      this.jndiSpecifiedTimeout = -1;
      if (partitionName == null && providerURL == null) {
         throw new IllegalStateException("Can't have both pName && pURL as NULL");
      } else if (partitionName != null && providerURL != null) {
         throw new IllegalStateException("Can't have both pName && pURL as NON-NULL; Remove pURL");
      } else {
         this.ref = ref;
         this.checkRef();
         this.desc = crd == null ? null : crd.intern();
         this.stubName = stubName;
         this.stubBaseName = stubBaseName;
         this.partitionName = partitionName;
         this.providerURL = providerURL;
      }
   }

   StubInfo(StubInfo stubInfo) {
      this.jndiSpecifiedTimeout = -1;
      this.ref = stubInfo.ref;
      this.checkRef();
      this.desc = stubInfo.desc;
      this.stubName = stubInfo.stubName;
      this.stubBaseName = stubInfo.stubBaseName;
      this.repositoryId = stubInfo.repositoryId;
      this.partitionName = stubInfo.getPartitionName();
      this.providerURL = stubInfo.providerURL;
   }

   public Class[] getInterfaces() {
      return this.desc.getInterfaces();
   }

   public ClassLoader getClassLoader() {
      return this.desc.getClassLoader();
   }

   public Map getDescriptorBySignature() {
      return this.desc.getDescriptorBySignature();
   }

   public ClientMethodDescriptor getDefaultClientMethodDescriptor() {
      return this.desc.getDefaultClientMethodDescriptor();
   }

   public String getStubName() {
      if (this.stubName == null) {
         this.stubName = this.desc.getInterfaceNames()[0] + "_WLStub";
      }

      return this.stubName;
   }

   public String getStubBaseClassName() {
      return this.stubBaseName == null ? DEFAULT_STUB_BASE.getName() : this.stubBaseName;
   }

   public RemoteReference getRemoteRef() {
      return this.ref;
   }

   public int getTimeOut(Method method) {
      String signature = MethodSignatureBuilder.compute(method);
      return this.getTimeOut(signature);
   }

   public int getTimeOut(String signature) {
      int timeout = 0;
      Map mdMap = this.getDescriptorBySignature();
      ClientMethodDescriptor defaultCMD;
      if (mdMap != null) {
         defaultCMD = (ClientMethodDescriptor)mdMap.get(signature);
         if (defaultCMD != null) {
            timeout = defaultCMD.getTimeOut();
         }
      }

      if (timeout > 0) {
         return timeout;
      } else {
         if (timeout == 0) {
            timeout = this.getJndiSpecifiedTimeout();
            if (timeout > 0) {
               return timeout;
            }

            defaultCMD = this.getDefaultClientMethodDescriptor();
            if (defaultCMD != null) {
               timeout = defaultCMD.getTimeOut();
            }
         }

         return timeout;
      }
   }

   public String getRemoteExceptionWrapperClassName(Method method) {
      String exceptionWrapperClassName = null;
      Map mdMap = this.getDescriptorBySignature();
      if (mdMap != null) {
         String signature = MethodSignatureBuilder.compute(method);
         ClientMethodDescriptor cmd = (ClientMethodDescriptor)mdMap.get(signature);
         if (cmd != null) {
            exceptionWrapperClassName = cmd.getRemoteExceptionWrapperClassName();
         }
      }

      return exceptionWrapperClassName;
   }

   public String getRepositoryId() {
      return this.repositoryId;
   }

   public void setRepositoryId(String repositoryId) {
      this.repositoryId = repositoryId;
   }

   public String getApplicationName() {
      return this.desc.getApplicationName();
   }

   public String[] getInterfaceNames() {
      return this.desc.getInterfaceNames();
   }

   public MethodDescriptor describe(Method m) {
      return this.desc.describe(m);
   }

   public void setRemoteRef(RemoteReference ref) {
      this.ref = ref;
      this.checkRef();
   }

   private void checkRef() {
      if (this.ref == null) {
         throw new AssertionError("StubInfo with null reference");
      } else if (this.ref.getHostID() == null) {
         throw new AssertionError("StubInfo reference has no HostID: " + this.ref.getClass().getName() + ": " + this.ref);
      }
   }

   public ClientRuntimeDescriptor getDescriptor() {
      return this.desc;
   }

   public String toString() {
      return this.ref.toString();
   }

   public StubInfo getReplaceableInfo() {
      return new ReplaceableStubInfo(this);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      String url = null;
      if (in instanceof CodeBaseInfo) {
         url = ((CodeBaseInfo)in).getCodebase();
      }

      if (this.stubName == null) {
         this.stubName = this.desc.getInterfaceNames()[0] + "_WLStub";
      }

      if (this.desc != null) {
         this.desc.setStubName(this.stubName);
         this.desc.setCodebase(url);
         this.desc = this.desc.intern();
      }

      this.checkRef();
      this.setEnvQueriedJNDITimeout();
      if (!(this.ref instanceof CollocatedRemoteReference)) {
         Hashtable props = RMIEnvironment.getEnvironment().getFromThreadLocalMap();
         if (props != null) {
            this.providerURL = (String)props.get("java.naming.provider.url");
         }
      }

   }

   public void setEnvQueriedJNDITimeout() {
      this.setJndiSpecifiedTimeout(this.getEnvQueriedJNDITimeout());
   }

   private int getEnvQueriedJNDITimeout() {
      int timeout = 0;
      Hashtable props = RMIEnvironment.getEnvironment().getFromThreadLocalMap();
      if (props == null) {
         return timeout;
      } else {
         Object o = props.get("weblogic.jndi.responseReadTimeout");
         if (o == null) {
            o = props.get("weblogic.rmi.clientTimeout");
         }

         if (o != null) {
            if (o instanceof String) {
               timeout = Integer.parseInt((String)o);
            } else {
               timeout = ((Long)o).intValue();
            }
         }

         return timeout;
      }
   }

   private Object readResolve() throws IOException {
      return KernelStatus.isApplet() ? this.loadStub() : this.resolveObject();
   }

   private Object loadStub() {
      Class stubClass;
      try {
         stubClass = Class.forName(this.stubName);
      } catch (LinkageError var4) {
         stubClass = this.loadStubClassWithLocalVer();
         if (stubClass == null) {
            throw var4;
         }
      } catch (ClassNotFoundException var5) {
         stubClass = this.loadStubClassWithLocalVer();
         if (stubClass == null) {
            throw new AssertionError(var5);
         }
      }

      try {
         Constructor con = stubClass.getConstructor(StubInfo.class);
         return con.newInstance(this);
      } catch (Exception var3) {
         throw new AssertionError(var3);
      }
   }

   private Class loadStubClassWithLocalVer() {
      if (!this.stubName.endsWith(ServerHelper.getWlsStubVersion())) {
         String newName = this.getStubNameWithLocalVer(this.stubName);
         if (newName != null) {
            this.stubName = newName;
            if (this.desc != null) {
               this.desc.setStubName(newName);
               this.desc = this.desc.intern();
            }

            try {
               return Class.forName(newName);
            } catch (ClassNotFoundException var3) {
               throw new AssertionError(var3);
            }
         }
      }

      return null;
   }

   private String getStubNameWithLocalVer(String name) {
      try {
         Matcher matcher = Pattern.compile("_[0-9]{3,}_WLStub$").matcher(name);
         if (matcher.find()) {
            return matcher.replaceFirst(ServerHelper.getWlsStubVersion());
         }
      } catch (Exception var3) {
      }

      return null;
   }

   private Object resolveObject() throws NoSuchObjectException {
      if (!this.ref.getHostID().isLocal()) {
         return StubFactory.getStub((StubReference)this);
      } else {
         try {
            ServerReference serverRef;
            try {
               serverRef = OIDManager.getInstance().getServerReference(this.ref.getObjectID());
            } catch (NoSuchObjectException var4) {
               if (couldBeNonLocalOnNoSuchObjectException(this.partitionName)) {
                  return StubFactory.getStub((StubReference)this);
               }

               throw var4;
            }

            RuntimeDescriptor rtd = serverRef.getDescriptor();
            if (!isFromDifferentApp(serverRef) && !rtd.getEnableServerSideStubs()) {
               Object resolved;
               if (serverRef instanceof ActivatableServerReference) {
                  resolved = ((ActivatableServerReference)serverRef).getImplementation(((ActivatableRemoteReference)this.ref).getActivationID());
               } else {
                  resolved = serverRef.getImplementation();
               }

               return rtd.getEnforceCallByValue() ? CBVWrapper.getCBVWrapper(rtd, resolved) : resolved;
            } else {
               return StubFactory.getStub((StubReference)this);
            }
         } catch (RemoteException var5) {
            throw new AssertionError(var5);
         }
      }
   }

   private static boolean isFromDifferentApp(ServerReference ref) {
      if (ref.getObjectID() < 256) {
         return true;
      } else {
         ClassLoader implCL = ref.getApplicationClassLoader();
         if (implCL == null) {
            return false;
         } else {
            ClassLoader callerCL = Thread.currentThread().getContextClassLoader();
            if (callerCL == ClassLoader.getSystemClassLoader() && implCL == ClassLoader.getSystemClassLoader()) {
               return false;
            } else {
               while(callerCL != null) {
                  if (callerCL == implCL) {
                     return false;
                  }

                  callerCL = callerCL.getParent();
               }

               return true;
            }
         }
      }
   }

   void refreshClientRuntimeDescriptor(ClassLoader classLoader) {
      if (isRefreshEnabled()) {
         if (this.stubName == null) {
            return;
         }

         String remoteClassName = BasicRuntimeDescriptor.getRemoteClassNameFromStubName(this.stubName);
         if (remoteClassName == null) {
            return;
         }

         String descFileName = remoteClassName.replace('.', '/') + "RTD.xml";

         try {
            RuntimeDescriptor rd = DescriptorManager.createRuntimeDescriptor(classLoader.getResourceAsStream(descFileName), remoteClassName, this.getInterfaces());
            this.overrideDescriptor(rd);
         } catch (RemoteException var5) {
            var5.printStackTrace();
         }
      }

   }

   void overrideDescriptor(RuntimeDescriptor rd) {
      if (rd != null) {
         this.desc.setDefaultClientMethodDescriptor(rd.getDefaultClientMethodDescriptor());
         this.desc.setDescriptorBySignature(rd.getClientMethodDescriptors());
         this.desc.intern();
      }

   }

   private static boolean isRefreshEnabled() {
      return refreshClientRuntimeDescriptor;
   }

   public void setDefaultClientMethodDescriptor(ClientMethodDescriptor defaultMethodDescriptor) {
      this.desc.setDefaultClientMethodDescriptor(defaultMethodDescriptor);
   }

   public synchronized void setJndiSpecifiedTimeout(int timeout) {
      this.jndiSpecifiedTimeout = timeout;
   }

   public synchronized int getJndiSpecifiedTimeout() {
      if (this.jndiSpecifiedTimeout > -1) {
         return this.jndiSpecifiedTimeout;
      } else {
         this.jndiSpecifiedTimeout = this.getEnvQueriedJNDITimeout();
         return this.jndiSpecifiedTimeout;
      }
   }

   public String getPartitionURL() {
      if (this.providerURL != null && this.providerURL.indexOf(44) == -1) {
         return this.providerURL;
      } else {
         String hostURI = this.ref.getHostID().getHostURI().toLowerCase();
         if (hostURI.indexOf("-1", hostURI.lastIndexOf(58) + 1) != -1) {
            return this.partitionName != null ? "/?partitionName=" + this.partitionName : null;
         } else {
            URI host;
            try {
               host = new URI(hostURI);
            } catch (URISyntaxException var8) {
               return this.partitionName != null ? "/?partitionName=" + this.partitionName : null;
            }

            if (this.sURL == null) {
               if (this.providerURL != null) {
                  try {
                     this.sURL = new ServerURL(this.providerURL);
                  } catch (MalformedURLException var7) {
                     this.sURL = ServerURL.DEFAULT_CONTEXT;
                  }
               } else {
                  this.sURL = ServerURL.DEFAULT_CONTEXT;
               }
            }

            InetAddress hostInetAdd;
            try {
               hostInetAdd = InetAddress.getByName(host.getHost());
            } catch (UnknownHostException var6) {
               throw new RuntimeException(var6);
            }

            if (this.sURL != ServerURL.DEFAULT_CONTEXT) {
               for(int i = 0; i < this.sURL.getAddressCount(); ++i) {
                  if (host.getPort() == this.sURL.getPort(i)) {
                     if (host.getHost().equals(this.sURL.getLowerCaseHost(i))) {
                        return this.sURL.getUrlString(i);
                     }

                     InetAddress sHost = this.sURL.getInetAddress(i);
                     if (sHost != null && (hostInetAdd.equals(sHost) || hostInetAdd.isLoopbackAddress() && sHost.isLoopbackAddress())) {
                        return this.sURL.getUrlString(i);
                     }
                  }
               }
            }

            return "/?partitionName=" + (this.partitionName == null ? "DOMAIN" : this.partitionName);
         }
      }
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   static boolean couldBeNonLocalOnNoSuchObjectException(String objPartitionName) {
      if (CICM == null) {
         return false;
      } else if (!isValidNonGlobalPartitionName(objPartitionName)) {
         return false;
      } else {
         ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
         String thrPartitionName = cic == null ? null : cic.getPartitionName();
         return !isValidNonGlobalPartitionName(thrPartitionName) ? false : thrPartitionName.equals(objPartitionName);
      }
   }

   static boolean isValidNonGlobalPartitionName(String pname) {
      return pname != null && !pname.equals("") && !pname.equals("~") && !pname.equals("DOMAIN");
   }

   static {
      if (KernelStatus.isApplet()) {
         refreshClientRuntimeDescriptor = false;
      } else {
         refreshClientRuntimeDescriptor = RMIEnvironment.getEnvironment().isRefreshClientRuntimeDescriptor();
      }

      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      CICM = null;
      if (KernelStatus.isServer()) {
         try {
            CICM = ComponentInvocationContextManager.getInstance(KERNEL_ID);
         } catch (Throwable var1) {
            CICM = null;
         }
      } else {
         CICM = null;
      }

   }

   private class ReplaceableStubInfo extends StubInfo implements InteropWriteReplaceable {
      static final long serialVersionUID = -9072149440278394014L;
      private StubInfo originalInfo;

      private ReplaceableStubInfo(StubInfo info) {
         super(info);
         this.originalInfo = info;
      }

      public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
         if (this.getRemoteRef().getHostID().isLocal()) {
            try {
               ServerReference serverRef = OIDManager.getInstance().getServerReference(this.getRemoteRef().getObjectID());
               Object obj = serverRef.getImplementation();
               if (obj instanceof InteropWriteReplaceable) {
                  Object newobj = ((InteropWriteReplaceable)obj).interopWriteReplace(peerInfo);
                  if (newobj != obj) {
                     obj = RemoteObjectReplacer.getReplacer().replaceObject(newobj);
                     if (!(obj instanceof StubInfo)) {
                        throw new IllegalArgumentException("StubInfo was no longer Remote after interopWriteReplace()");
                     }

                     StubInfo info = (StubInfo)obj;
                     info.getDescriptor().intern();
                     return info;
                  }
               }
            } catch (NoSuchObjectException var6) {
               throw new AssertionError(var6);
            }
         }

         throw new AssertionError("ReplaceableStubInfo must always be local");
      }

      public Object writeReplace() throws ObjectStreamException {
         return this.originalInfo;
      }

      // $FF: synthetic method
      ReplaceableStubInfo(StubInfo x1, Object x2) {
         this(x1);
      }
   }
}
