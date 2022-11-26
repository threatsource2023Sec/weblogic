package weblogic.rmi.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.StubNotFoundException;
import java.rmi.UnexpectedException;
import java.rmi.server.ExportException;
import java.rmi.server.SkeletonNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.RemoteObject;
import weblogic.rmi.extensions.NotImplementedException;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.ActivatableServerReference;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.rmi.extensions.server.InvokableServerReference;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.internal.dgc.DGCPolicyConstants;
import weblogic.rmi.rmic.XMLDescriptorCreator;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.utils.Utilities;
import weblogic.utils.Debug;
import weblogic.utils.classloaders.ByteArraySource;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.collections.ArrayMap;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

final class BasicRuntimeDescriptor implements RuntimeDescriptor, DescriptorConstants, DGCPolicyConstants {
   private static final boolean debug = false;
   private static final boolean GENERATE_SKELETON = true;
   private final MethodDescriptor defaultMethodDescriptor;
   private final Class remoteClass;
   private final RemoteType remoteType;
   private final String[] interfaceNames;
   private final Class[] remoteInterfaces;
   private final String applicationName;
   private final String moduleName;
   private ArrayMap rtd;
   private ArrayMap rmiDescriptor;
   private ArrayMap clusterDescriptor;
   private ArrayMap lifecyleDescriptor;
   private ArrayMap methodDescriptors;
   private ArrayMap securityDescriptors;
   private Class cbvWrapperClass;
   private Constructor cbvWrapperConstructor;
   private final Skeleton skelInstance;
   private Method[] remoteMethods;
   private Set remoteMethodSet;
   private String remoteClassName;
   private String skeletonClassName;
   private String stubClassName;
   private String remoteRefClassName;
   private String serverRefClassName;
   private String initialReference;
   private String dispatchPolicy;
   private boolean useServerSideStubs;
   private boolean enableCallByReference;
   private boolean clusterable;
   private boolean defaultidempotent;
   private boolean defaultTransactional;
   private boolean propagateEnvironment;
   private boolean stickToFirstServer;
   private String loadAlgorithm;
   private String callRouterClassName;
   private String replicaHandlerClassName;
   private int dgcPolicy;
   private boolean activatableObject;
   private boolean corbaObject;
   private final HashMap clientMethodDescMap;
   private MethodDescriptor[] mds;
   private HashMap methodSignatureAndIndexMap;
   private ClientMethodDescriptor defaultClientMD;
   private Constructor remoteRefCon;
   private Constructor serverRefCon;
   private ArrayMap methodMap;
   private ArrayMap methodDescMap;
   private boolean isIIOPInitialized;
   private String clientCertAuthentication;
   private String clientAuthentication;
   private String identityAssertion;
   private String confidentiality;
   private String integrity;
   private static String iiopSecurity = RMIEnvironment.getEnvironment().getIIOPSystemSecurity();
   private boolean statefulAuthentication;
   private String networkAccessPoint;
   private ByteArraySource arraySource;
   private boolean customMethodDescriptors;
   private final Class[] SREF_ARGS_ACT_IR;
   private final Class[] SREF_ARGS_ACT;
   private final Class[] SREF_ARGS;
   private final Class[] SREF_ARGS_IR;
   private final ClientRuntimeDescriptor crd;

   public BasicRuntimeDescriptor(ArrayMap rtd, Class c) throws RemoteException {
      this(rtd, c, Utilities.getRemoteInterfaces(c), true);
   }

   public BasicRuntimeDescriptor(Class c) throws RemoteException {
      this((ArrayMap)null, c, Utilities.getRemoteInterfaces(c), true);
   }

   private BasicRuntimeDescriptor(ArrayMap rtd, Class c, Class[] remoteInterfaces, boolean generateSkeleton) throws RemoteException {
      this.defaultMethodDescriptor = new MethodDescriptor();
      this.dispatchPolicy = "weblogic.kernel.Default";
      this.useServerSideStubs = false;
      this.enableCallByReference = true;
      this.clusterable = false;
      this.defaultidempotent = true;
      this.defaultTransactional = true;
      this.propagateEnvironment = false;
      this.stickToFirstServer = false;
      this.dgcPolicy = -1;
      this.clientMethodDescMap = new HashMap();
      this.methodSignatureAndIndexMap = new HashMap();
      this.isIIOPInitialized = false;
      this.clientCertAuthentication = "supported";
      this.clientAuthentication = "supported";
      this.identityAssertion = "supported";
      this.confidentiality = "supported";
      this.integrity = "supported";
      this.statefulAuthentication = true;
      this.arraySource = null;
      this.customMethodDescriptors = false;
      this.SREF_ARGS_ACT_IR = new Class[]{Class.class, Integer.TYPE, Activator.class};
      this.SREF_ARGS_ACT = new Class[]{Class.class, Activator.class};
      this.SREF_ARGS = new Class[]{Object.class};
      this.SREF_ARGS_IR = new Class[]{Integer.TYPE, Object.class};
      this.rtd = rtd;
      this.remoteClass = c;
      this.remoteInterfaces = remoteInterfaces;
      this.remoteType = new RemoteType(remoteInterfaces);
      this.interfaceNames = getInterfaceNames(remoteInterfaces);
      this.applicationName = getApplicationName(this.remoteClass);
      this.moduleName = this.getCurrentModuleName();
      this.initializeRuntimeDescriptor();
      if (generateSkeleton) {
         this.skelInstance = createSkeleton(this.remoteClass, this.createSkeletonClass());
      } else {
         this.skelInstance = null;
      }

      ClientRuntimeDescriptor temp = new ClientRuntimeDescriptor(this.interfaceNames, this.applicationName, this.clientMethodDescMap, this.defaultClientMD, this.getStubClassName());
      this.crd = temp.intern();
   }

   BasicRuntimeDescriptor(ArrayMap rtd, String remoteClassName, Class[] remoteInterfaces) throws RemoteException {
      this.defaultMethodDescriptor = new MethodDescriptor();
      this.dispatchPolicy = "weblogic.kernel.Default";
      this.useServerSideStubs = false;
      this.enableCallByReference = true;
      this.clusterable = false;
      this.defaultidempotent = true;
      this.defaultTransactional = true;
      this.propagateEnvironment = false;
      this.stickToFirstServer = false;
      this.dgcPolicy = -1;
      this.clientMethodDescMap = new HashMap();
      this.methodSignatureAndIndexMap = new HashMap();
      this.isIIOPInitialized = false;
      this.clientCertAuthentication = "supported";
      this.clientAuthentication = "supported";
      this.identityAssertion = "supported";
      this.confidentiality = "supported";
      this.integrity = "supported";
      this.statefulAuthentication = true;
      this.arraySource = null;
      this.customMethodDescriptors = false;
      this.SREF_ARGS_ACT_IR = new Class[]{Class.class, Integer.TYPE, Activator.class};
      this.SREF_ARGS_ACT = new Class[]{Class.class, Activator.class};
      this.SREF_ARGS = new Class[]{Object.class};
      this.SREF_ARGS_IR = new Class[]{Integer.TYPE, Object.class};
      this.remoteClass = null;
      this.moduleName = null;
      this.applicationName = null;
      this.skelInstance = null;
      this.remoteClassName = remoteClassName;
      this.rtd = rtd;
      this.remoteInterfaces = remoteInterfaces;
      this.remoteType = new RemoteType(remoteInterfaces);
      this.interfaceNames = Utilities.getRemoteInterfaceNames(remoteInterfaces);
      this.initializeRuntimeDescriptor();
      ClientRuntimeDescriptor temp = new ClientRuntimeDescriptor(this.interfaceNames, (String)null, this.clientMethodDescMap, this.defaultClientMD, this.getStubClassName());
      this.crd = temp.intern();
   }

   static BasicRuntimeDescriptor getRuntimeDescriptorForRMIC(ArrayMap rtd, Class c) throws RemoteException {
      return new BasicRuntimeDescriptor(rtd, c, Utilities.getRemoteInterfaces(c), false);
   }

   private static String[] getInterfaceNames(Class[] remoteInterfaces) {
      String[] interfaceNames = new String[remoteInterfaces.length + 1];

      for(int i = 0; i < remoteInterfaces.length; ++i) {
         interfaceNames[i] = remoteInterfaces[i].getName();
      }

      interfaceNames[remoteInterfaces.length] = StubInfoIntf.class.getName();
      return interfaceNames;
   }

   private static String getApplicationName(Class remoteClass) {
      if (remoteClass == null) {
         return null;
      } else {
         ClassLoader cl = remoteClass.getClassLoader();
         if (cl instanceof GenericClassLoader) {
            GenericClassLoader gcl = (GenericClassLoader)cl;
            return gcl.getAnnotation().getAnnotationString();
         } else {
            return null;
         }
      }
   }

   private String getCurrentModuleName() {
      if (this.applicationName != null) {
         try {
            Context ic = new InitialContext();
            return (String)ic.lookup("java:/bea/ModuleName");
         } catch (NamingException var2) {
            return null;
         }
      } else {
         return null;
      }
   }

   public Class getRemoteClass() {
      return this.remoteClass;
   }

   public String getRemoteClassName() {
      return this.remoteClassName;
   }

   public String getSkeletonClassName() {
      return this.skeletonClassName;
   }

   public String getStubClassName() {
      return this.stubClassName;
   }

   public Method[] getRemoteMethods() {
      return this.remoteMethods;
   }

   public final Class[] getRemoteInterfaces() {
      return this.remoteInterfaces;
   }

   private void initializeRuntimeDescriptor() throws RemoteException {
      this.initClassNames();
      this.initRemoteMethods();
      if (this.rtd != null) {
         this.rmiDescriptor = (ArrayMap)this.rtd.get("rmidescriptor");
         this.clusterDescriptor = (ArrayMap)this.rtd.get("clusterdescriptor");
         this.lifecyleDescriptor = (ArrayMap)this.rtd.get("lifecycledescriptor");
         this.methodDescriptors = (ArrayMap)this.rtd.get("methoddescriptor");
         this.securityDescriptors = (ArrayMap)this.rtd.get("securitydescriptor");
      }

      if (this.rmiDescriptor != null) {
         this.initializeRMIDescriptorInfo();
      }

      if (this.lifecyleDescriptor != null) {
         this.initializeLifecycleDescriptor();
      }

      if (this.clusterDescriptor != null) {
         this.initializeClusterDescriptor();
      }

      if (this.securityDescriptors != null) {
         this.initializeSecurityDescriptor();
      }

      this.createMethodDescriptors();
   }

   private Class createSkeletonClass() throws SkeletonNotFoundException {
      Class clazz = null;
      if (this.getRemoteClass() == null) {
         return null;
      } else {
         ClassLoader cl = this.getRemoteClass().getClassLoader();
         if (cl == null) {
            cl = StubInfo.class.getClassLoader();
         }

         try {
            clazz = cl.loadClass(this.getSkeletonClassName());
         } catch (ClassNotFoundException var4) {
         }

         if (clazz == null) {
            clazz = this.generateSkeletonClass(cl);
         }

         return clazz;
      }
   }

   private Class generateSkeletonClass(ClassLoader cl) throws SkeletonNotFoundException {
      SkelGenerator skelGenerator = new SkelGenerator(this);

      try {
         return skelGenerator.generateClass(cl);
      } catch (SecurityException var6) {
         try {
            return Class.forName(this.skeletonClassName);
         } catch (ClassNotFoundException var5) {
            throw new SkeletonNotFoundException("Failed to download the Skeleton into applet from server through ClasspathServlet.", var5);
         }
      }
   }

   public Constructor getCBVWrapper() {
      if (this.cbvWrapperConstructor == null) {
         ClassLoader cl = this.getRemoteClass().getClassLoader();
         CBVWrapperGenerator cbvGenerator = new CBVWrapperGenerator(this);
         this.cbvWrapperClass = cbvGenerator.generateClass(cl);

         try {
            this.cbvWrapperConstructor = this.cbvWrapperClass.getConstructor(this.getRemoteClass());
         } catch (NoSuchMethodException var4) {
            throw new AssertionError(var4);
         }
      }

      return this.cbvWrapperConstructor;
   }

   private static Skeleton createSkeleton(Class remoteClass, Class clazz) throws SkeletonNotFoundException {
      if (clazz == null && remoteClass == null) {
         return null;
      } else {
         try {
            return (Skeleton)clazz.newInstance();
         } catch (InstantiationException var3) {
            throw new SkeletonNotFoundException("Could not create skeleton", var3);
         } catch (IllegalAccessException var4) {
            throw new SkeletonNotFoundException("Could not access skeleton", var4);
         }
      }
   }

   public Skeleton getSkeleton() {
      return this.skelInstance;
   }

   public int getDGCPolicy() {
      return this.dgcPolicy;
   }

   public String getRemoteReferenceClassName() {
      return this.remoteRefClassName == null ? this.getDefaultRemoteRefClassName() : this.remoteRefClassName;
   }

   public Class getRemoteReferenceClass() throws ClassNotFoundException {
      return Class.forName(this.getRemoteReferenceClassName());
   }

   public int getInitialReference() {
      if (this.initialReference != null && !this.initialReference.equals("")) {
         try {
            return Integer.parseInt(this.initialReference);
         } catch (NumberFormatException var2) {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public String getNetworkAccessPoint() {
      return this.networkAccessPoint;
   }

   public boolean isClusterable() {
      return this.clusterable;
   }

   public String[] getRemoteInterfacesClassNames() {
      return this.interfaceNames;
   }

   public boolean getPropagateEnvironment() {
      return this.propagateEnvironment;
   }

   public String getCallRouterClassName() {
      return this.callRouterClassName;
   }

   public String getReplicaHandlerClassName() {
      return this.replicaHandlerClassName;
   }

   public String getLoadAlgorithm() {
      return this.loadAlgorithm;
   }

   public boolean getMethodsAreIdempotent() {
      return this.defaultidempotent;
   }

   public boolean getMethodsAreTransactional() {
      return this.defaultTransactional;
   }

   public boolean getStickToFirstServer() {
      return this.stickToFirstServer;
   }

   public boolean getEnableServerSideStubs() {
      Debug.assertion(!this.corbaObject || !this.useServerSideStubs);
      return this.useServerSideStubs;
   }

   public boolean getEnforceCallByValue() {
      return !this.enableCallByReference;
   }

   public String getDispatchPolicyName() {
      return this.dispatchPolicy;
   }

   public ServerReference createServerReference(Object impl) throws RemoteException {
      int ir = this.getInitialReference();

      try {
         Object[] args;
         if (ir < 0) {
            args = new Object[]{impl};
         } else {
            args = new Object[]{new Integer(ir), impl};
         }

         if (this.serverRefCon == null) {
            if (ir < 0) {
               this.serverRefCon = this.getServerReferenceClass().getConstructor(this.SREF_ARGS);
            } else {
               this.serverRefCon = this.getServerReferenceClass().getConstructor(this.SREF_ARGS_IR);
            }
         }

         ServerReference sref = (ServerReference)this.serverRefCon.newInstance(args);
         return (ServerReference)(this.isClusterable() && !(sref instanceof ClusterAwareServerReference) && sref instanceof InvokableServerReference ? RMIEnvironment.getEnvironment().createClusteredServerRef((InvokableServerReference)sref) : sref);
      } catch (NoSuchMethodException var5) {
         throw new ExportException("Server reference class missing constructor: '" + this.getServerReferenceClassName() + "'", var5);
      } catch (IllegalAccessException var6) {
         throw new ExportException("Server reference class constructor not public: '" + this.getServerReferenceClassName() + "'", var6);
      } catch (InstantiationException var7) {
         throw new ExportException("Failed to instantiate server reference: '" + this.getServerReferenceClassName() + "'", var7);
      } catch (InvocationTargetException var8) {
         Throwable nested = var8.getTargetException();
         if (!(nested instanceof Exception)) {
            nested = var8;
         }

         throw new ExportException("Failed to invoke contructor for server reference: '" + this.getServerReferenceClassName() + "'", (Exception)nested);
      } catch (ClassCastException var9) {
         throw new StubNotFoundException("Server reference not an instance of ServerReference: '" + this.getServerReferenceClassName() + "'", var9);
      }
   }

   private String getServerReferenceClassName() {
      return this.serverRefClassName == null ? this.getDefaultServerRefClassName() : this.serverRefClassName;
   }

   private Class getServerReferenceClass() throws UnexpectedException {
      try {
         return Class.forName(this.getServerReferenceClassName());
      } catch (ClassNotFoundException var2) {
         throw new UnexpectedException("Failed to load: '" + this.getServerReferenceClassName() + "'", var2);
      }
   }

   ActivatableServerReference createActivatableServerReference(Object o, Activator activator) throws RemoteException {
      if (!this.isClusterable()) {
         return this.createActivatableServerReference(o.getClass(), activator);
      } else {
         try {
            if (this.serverRefCon == null) {
               this.serverRefCon = this.getServerReferenceClass().getConstructor(Object.class, Activator.class);
            }

            return (ActivatableServerReference)this.serverRefCon.newInstance(o, activator);
         } catch (NoSuchMethodException var5) {
            throw new ExportException("Server reference class missing constructor: '" + this.getServerReferenceClassName() + "'", var5);
         } catch (IllegalAccessException var6) {
            throw new ExportException("Server reference class constructor not public: '" + this.getServerReferenceClassName() + "'", var6);
         } catch (InstantiationException var7) {
            throw new ExportException("Failed to instantiate server reference: '" + this.getServerReferenceClassName() + "'", var7);
         } catch (InvocationTargetException var8) {
            Throwable nested = var8.getTargetException();
            if (!(nested instanceof Exception)) {
               nested = var8;
            }

            throw new ExportException("Failed to invoke contructor for server reference: '" + this.getServerReferenceClassName() + "'", (Exception)nested);
         } catch (ClassCastException var9) {
            throw new StubNotFoundException("Server reference not an instance of ServerReference: '" + this.getServerReferenceClassName() + "'", var9);
         }
      }
   }

   ActivatableServerReference createActivatableServerReference(Class c, Activator activator) throws RemoteException {
      Debug.assertion(this.isActivatable() && !this.isClusterable(), "Can only export activatable objects");
      int initialReference = this.getInitialReference();

      try {
         if (this.serverRefCon == null) {
            if (this.getInitialReference() > 0) {
               this.serverRefCon = this.getServerReferenceClass().getConstructor(this.SREF_ARGS_ACT_IR);
            } else {
               this.serverRefCon = this.getServerReferenceClass().getConstructor(this.SREF_ARGS_ACT);
            }
         }

         return initialReference < 0 ? (ActivatableServerReference)this.serverRefCon.newInstance(c, activator) : (ActivatableServerReference)this.serverRefCon.newInstance(c, new Integer(initialReference), activator);
      } catch (NoSuchMethodException var6) {
         throw new ExportException("Server reference class missing constructor: '" + this.getServerReferenceClassName() + "'", var6);
      } catch (IllegalAccessException var7) {
         throw new ExportException("Server reference class constructor not public: '" + this.getServerReferenceClassName() + "'", var7);
      } catch (InstantiationException var8) {
         throw new ExportException("Failed to instantiate server reference: '" + this.getServerReferenceClassName() + "'", var8);
      } catch (InvocationTargetException var9) {
         Throwable nested = var9.getTargetException();
         if (!(nested instanceof Exception)) {
            nested = var9;
         }

         throw new ExportException("Failed to invoke contructor for server reference: '" + this.getServerReferenceClassName() + "'", (Exception)nested);
      } catch (ClassCastException var10) {
         throw new StubNotFoundException("Server reference not an instance of ServerReference: '" + this.getServerReferenceClassName() + "'", var10);
      }
   }

   public RemoteReference getRemoteReference(int oid, Object o) throws RemoteException {
      if (this.activatableObject) {
         return this.getActivatableRemoteReference(oid, o);
      } else {
         try {
            Object hostID;
            if (ThreadPreferredHost.get() != null) {
               hostID = ThreadPreferredHost.get();
            } else {
               hostID = LocalServerIdentity.getIdentity();
            }

            return (RemoteReference)this.getRemoteRefConstructor().newInstance(new Integer(oid), hostID);
         } catch (ClassNotFoundException var5) {
            throw new ExportException("RemoteReference class missing: " + this.getRemoteReferenceClassName(), var5);
         } catch (NoSuchMethodException var6) {
            throw new ExportException("RemoteReference class missing constructor: " + this.getRemoteReferenceClassName(), var6);
         } catch (IllegalAccessException var7) {
            throw new ExportException("RemoteReference class constructor not public: " + this.getRemoteReferenceClassName(), var7);
         } catch (InstantiationException var8) {
            throw new ExportException("Failed to instantiate RemoteReference: " + this.getRemoteReferenceClassName(), var8);
         } catch (InvocationTargetException var9) {
            Throwable nested = var9.getTargetException();
            if (!(nested instanceof Exception)) {
               nested = var9;
            }

            throw new ExportException("Failed to invoke contructor for RemoteReference: " + this.getRemoteReferenceClassName(), (Exception)nested);
         }
      }
   }

   public RuntimeMethodDescriptor getControlDescriptor(RuntimeMethodDescriptor md) {
      if (this.mds == null) {
         return null;
      } else {
         int pos = this.getIndex(md);
         return pos != -1 && pos < this.mds.length ? this.mds[pos] : null;
      }
   }

   private int getIndex(RuntimeMethodDescriptor md) {
      Integer pos = (Integer)this.methodSignatureAndIndexMap.get(md.getSignature());
      return pos == null ? -1 : pos;
   }

   public HashMap getClientMethodDescriptors() {
      return this.clientMethodDescMap;
   }

   public ClientMethodDescriptor getClientMethodDescriptor(String sig) {
      return (ClientMethodDescriptor)this.clientMethodDescMap.get(sig);
   }

   public ClientMethodDescriptor getDefaultClientMethodDescriptor() {
      return this.defaultClientMD;
   }

   public RemoteType getRemoteType() {
      return this.remoteType;
   }

   public MethodDescriptor[] getMethodDescriptors() {
      return this.mds;
   }

   public ClientRuntimeDescriptor getClientRuntimeDescriptor(String appName) {
      return this.crd;
   }

   public MethodDescriptor getMethodDescriptor(String name) {
      if (!this.isIIOPInitialized) {
         this.initIIOP();
      }

      MethodDescriptor returnMD = (MethodDescriptor)this.methodDescMap.get(name);
      if (returnMD == null) {
      }

      return returnMD;
   }

   public Method getMethod(String name) {
      if (!this.isIIOPInitialized) {
         this.initIIOP();
      }

      return (Method)this.methodMap.get(name);
   }

   private void initClassNames() {
      if (this.getRemoteClass() != null) {
         this.remoteClassName = this.getRemoteClass().getName();
         if (!Remote.class.isAssignableFrom(this.getRemoteClass()) && org.omg.CORBA.Object.class.isAssignableFrom(this.getRemoteClass())) {
            this.corbaObject = true;
         }
      }

      if (this.remoteClassName != null) {
         this.skeletonClassName = this.remoteClassName + "_WLSkel";
         this.stubClassName = ServerHelper.getStubClassName(this.remoteClassName);
      }

   }

   static String getRemoteClassNameFromStubName(String stubName) {
      if (stubName == null) {
         return null;
      } else {
         int versionIndex = stubName.indexOf(ServerHelper.getWlsStubVersion());
         if (versionIndex < 0) {
            return null;
         } else {
            String remoteName = stubName.substring(0, versionIndex);
            return remoteName;
         }
      }
   }

   private void initRemoteMethods() {
      Debug.assertion(this.remoteInterfaces != null);
      Map sigMap = Utilities.getRemoteMethodsAndSignatures(this.remoteInterfaces);
      this.remoteMethods = new Method[sigMap.size()];
      this.remoteMethods = (Method[])sigMap.values().toArray(this.remoteMethods);
      this.remoteMethodSet = new HashSet();
      this.remoteMethodSet.addAll(sigMap.keySet());
      if (MethodDescriptor.isGenericMethodSignatureModeEnabled()) {
         GenericInfo gInfo = null;
         if (this.remoteClass != null) {
            gInfo = new GenericInfo(this.remoteClass);
         } else {
            gInfo = new GenericInfo(this.remoteInterfaces);
         }

         Method[] var3 = this.remoteMethods;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var3[var5];
            if (GenericMethodDescriptor.isGenericMethod(m)) {
               try {
                  this.remoteMethodSet.add(GenericMethodDescriptor.computeGenericMethodSignature(m, gInfo));
               } catch (UnresolvedTypeException var8) {
               }
            }
         }
      }

   }

   public boolean isActivatable() {
      return this.activatableObject;
   }

   private void initializeRMIDescriptorInfo() {
      String temp = (String)this.rmiDescriptor.get("use-server-side-stubs");
      if (temp != null) {
         this.useServerSideStubs = this.toBoolean(temp);
      }

      temp = (String)this.rmiDescriptor.get("enable-call-by-reference");
      if (temp != null) {
         this.enableCallByReference = this.toBoolean(temp);
      }

      temp = (String)this.rmiDescriptor.get("remote-ref-classname");
      if (temp != null) {
         this.remoteRefClassName = temp;
      }

      temp = (String)this.rmiDescriptor.get("server-ref-classname");
      if (temp != null) {
         this.serverRefClassName = temp;
      }

      temp = (String)this.rmiDescriptor.get("initial-reference");
      if (temp != null) {
         this.initialReference = temp;
      }

      this.dispatchPolicy = (String)this.rmiDescriptor.get("dispatch-policy");
      temp = (String)this.rmiDescriptor.get("network-access-point");
      if (temp != null) {
         this.networkAccessPoint = temp;
      }

      if (this.remoteRefClassName != null) {
         this.activatableObject = this.remoteRefClassName.equals("weblogic.rmi.internal.activation.ActivatableRemoteRef") || this.remoteRefClassName.equals("weblogic.rmi.cluster.ClusterActivatableRemoteRef");
      }

   }

   private void initializeClusterDescriptor() {
      String temp = (String)this.clusterDescriptor.get("clusterable");
      if (temp != null) {
         this.clusterable = this.toBoolean(temp) && KernelStatus.isServer();
      }

      temp = (String)this.clusterDescriptor.get("propagate-environment");
      if (temp != null) {
         this.propagateEnvironment = this.toBoolean(temp);
      }

      temp = (String)this.clusterDescriptor.get("load-algorithm");
      if (temp != null) {
         this.loadAlgorithm = temp;
      } else {
         this.loadAlgorithm = "default";
      }

      this.callRouterClassName = (String)this.clusterDescriptor.get("call-router-classname");
      this.replicaHandlerClassName = (String)this.clusterDescriptor.get("replica-handler-classname");
      temp = (String)this.clusterDescriptor.get("stick-to-first-server");
      if (temp != null) {
         this.stickToFirstServer = this.toBoolean(temp);
      }

      if (this.remoteRefClassName == null) {
         this.remoteRefClassName = this.getDefaultRemoteRefClassName();
      }

      if (this.serverRefClassName == null) {
         this.serverRefClassName = this.getDefaultServerRefClassName();
      }

   }

   private void initializeSecurityDescriptor() {
      this.confidentiality = this.getSecurityElementValue("confidentiality");
      this.clientCertAuthentication = this.getSecurityElementValue("client-cert-authentication");
      this.clientAuthentication = this.getSecurityElementValue("client-authentication");
      this.identityAssertion = this.getSecurityElementValue("identity-assertion");
      this.integrity = this.getSecurityElementValue("integrity");
      this.statefulAuthentication = this.toBoolean(this.getSecurityElementValue("stateful-authentication"));
   }

   private String getSecurityElementValue(String key) {
      String value = (String)this.securityDescriptors.get(key);
      return value != null && value.equalsIgnoreCase("config") ? iiopSecurity : value;
   }

   private void createMethodDescriptors() throws RemoteException {
      ArrayList methodList = new ArrayList();
      Debug.assertion(this.remoteMethods != null);
      ArrayMap defaultMethodDescriptorMap = null;
      this.validateXMLDescriptor();
      if (this.methodDescriptors != null) {
         defaultMethodDescriptorMap = (ArrayMap)this.methodDescriptors.get("*");
         if (defaultMethodDescriptorMap != null) {
            this.populate(defaultMethodDescriptorMap, this.defaultMethodDescriptor, (Method)null, new HashMap());
            this.defaultClientMD = this.defaultMethodDescriptor.getClientDescriptor();
         }
      } else {
         this.defaultidempotent = this.defaultMethodDescriptor.isIdempotent();
         this.defaultTransactional = this.defaultMethodDescriptor.isTransactional();
      }

      GenericInfo gInfo = null;
      if (MethodDescriptor.isGenericMethodSignatureModeEnabled()) {
         gInfo = new GenericInfo(this.remoteClass);
      }

      Map wms = new HashMap();

      for(int i = 0; i < this.remoteMethods.length; ++i) {
         MethodDescriptor md = new MethodDescriptor(this.remoteMethods[i], this.remoteClass, this.applicationName, this.moduleName, i);
         String signature = md.getSignature();
         if (defaultMethodDescriptorMap != null) {
            this.populate(defaultMethodDescriptorMap, md, (Method)null, wms);
         }

         if (this.methodDescriptors != null) {
            ArrayMap h = (ArrayMap)this.methodDescriptors.get(signature);
            if (h != null) {
               this.defaultClientMD = null;
            }

            this.populate(h, md, this.remoteMethods[i], wms);
            h = null;
         }

         ClientMethodDescriptor cd = md.getClientDescriptor();
         this.clientMethodDescMap.put(cd.getSignature(), cd);
         methodList.add(md);
         this.methodSignatureAndIndexMap.put(signature, new Integer(i));
         if (MethodDescriptor.isGenericMethodSignatureModeEnabled() && GenericMethodDescriptor.isGenericMethod(this.remoteMethods[i])) {
            String genericSignature;
            try {
               genericSignature = GenericMethodDescriptor.computeGenericMethodSignature(this.remoteMethods[i], gInfo);
            } catch (UnresolvedTypeException var11) {
               continue;
            }

            assert !genericSignature.equals(signature) : "For generic method = " + this.remoteMethods[i] + " got erased signature = " + genericSignature;

            this.methodSignatureAndIndexMap.put(genericSignature, new Integer(i));
         }
      }

      this.mds = (MethodDescriptor[])((MethodDescriptor[])methodList.toArray(new MethodDescriptor[methodList.size()]));
   }

   private synchronized void initIIOP() {
      if (!this.isIIOPInitialized) {
         this.methodMap = new ArrayMap();
         this.methodDescMap = new ArrayMap();
         Debug.assertion(this.mds.length == this.remoteMethods.length);

         for(int i = 0; i < this.mds.length; ++i) {
            String mangledName = RMIEnvironment.getEnvironment().getIIOPMangledName(this.remoteMethods[i], this.remoteClass);
            this.methodMap.put(mangledName, this.remoteMethods[i]);
            this.methodDescMap.put(mangledName, this.mds[i]);
         }

         this.isIIOPInitialized = true;
      }
   }

   private void populate(ArrayMap h, MethodDescriptor md, Method m, Map workManagers) throws RemoteException {
      if (h != null) {
         boolean transactional = false;
         String temp = (String)h.get("oneway");
         boolean b = false;
         if (temp != null) {
            b = this.toBoolean(temp);
            if (b && m != null) {
               Class returnType = m.getReturnType();
               if (returnType != Void.TYPE) {
                  throw new ExportException(md.getSignature() + " is declared as a oneway method, hence cannot return a value");
               }

               Class[] exceptionTypes = m.getExceptionTypes();
               if (exceptionTypes != null && exceptionTypes.length > 0) {
                  Class[] var10 = exceptionTypes;
                  int var11 = exceptionTypes.length;

                  for(int var12 = 0; var12 < var11; ++var12) {
                     Class exceptionType = var10[var12];
                     if (!exceptionType.isAssignableFrom(RemoteException.class) && !exceptionType.isAssignableFrom(RuntimeException.class)) {
                        throw new ExportException(md.getSignature() + " is declared as a oneway method, hence cannot throw a checked exception: " + exceptionType.getName());
                     }
                  }
               }
            }

            md.setOneway(b);
         }

         temp = (String)h.get("transactional");
         if (temp != null) {
            b = this.toBoolean(temp);
            md.setTransactional(b);
            this.defaultTransactional &= b;
            transactional = b;
         }

         temp = (String)h.get("requires-transaction");
         if (temp != null) {
            b = this.toBoolean(temp);
            md.setRequiresTransaction(b);
         }

         temp = (String)h.get("oneway-transactional-request");
         if (temp != null) {
            b = this.toBoolean(temp);
            md.setOnewayTransactionalRequest(b);
         }

         temp = (String)h.get("oneway-transactional-response");
         if (temp != null) {
            b = this.toBoolean(temp);
            md.setOnewayTransactionalResponse(b);
         }

         temp = (String)h.get("dispatch-policy");
         if (temp != null) {
            if (KernelStatus.isServer()) {
               WorkManager wm = (WorkManager)workManagers.get(temp);
               if (wm == null) {
                  wm = WorkManagerFactory.getInstance().find(temp);
                  workManagers.put(temp, wm);
               }

               md.setDispatchPolicy(temp, wm);
            } else {
               md.setDispatchPolicy(temp, (WorkManager)null);
            }
         }

         temp = (String)h.get("timeout");
         if (temp != null) {
            int timeout = Integer.parseInt(temp);
            if ((transactional || md.requiresTransaction()) && (!md.requiresTransaction() || this.remoteClass == null || !RemoteObject.class.isAssignableFrom(this.remoteClass))) {
               timeout = Math.max(timeout, ServerHelper.getTransactionTimeoutMillis());
            }

            md.setTimeOut(timeout);
            if (timeout > 0) {
               this.customMethodDescriptors = true;
            }
         }

         temp = (String)h.get("idempotent");
         if (temp != null) {
            b = this.toBoolean(temp);
            md.setIdempotent(b);
            this.defaultidempotent &= b;
         }

         temp = (String)h.get("asynchronous");
         if (temp != null) {
            b = this.toBoolean(temp);
            md.setAsynchronous(b);
         }

         temp = (String)h.get("future");
         if (temp != null) {
            b = this.toBoolean(temp);
            if (b) {
               md.setDispatchMethod(m, this.remoteClass, FutureResponse.class);
            }
         }

         temp = (String)h.get("dispatch-context");
         if (temp != null) {
            if (temp.equals("future")) {
               md.setDispatchMethod(m, this.remoteClass, FutureResponse.class);
            } else if (temp.equals("request")) {
               md.setDispatchMethod(m, this.remoteClass, InboundRequest.class);
            }
         }

         temp = (String)h.get("remote-exception-wrapper-classname");
         if (temp != null) {
            md.setRemoteExceptionWrapperClassName(temp);
            this.customMethodDescriptors = true;
         }

      }
   }

   private void validateXMLDescriptor() throws RemoteException {
      if (this.methodDescriptors != null) {
         Iterator i = this.methodDescriptors.keySet().iterator();

         while(i.hasNext()) {
            String signature = (String)i.next();
            if (!signature.equals("*") && !this.remoteMethodSet.contains(signature)) {
               throw new ExportException("Failed to export " + this.remoteClass + "; problem with rmi descriptor signature " + signature + "; RemoteMethodSet=" + this.remoteMethodSet);
            }
         }
      }

   }

   private void initializeLifecycleDescriptor() {
      String temp = (String)this.lifecyleDescriptor.get("dgc-policy");
      this.dgcPolicy = this.getDGCPolicy(temp);
   }

   private int getDGCPolicy(String dgcPolicyStr) {
      if (dgcPolicyStr != null && !dgcPolicyStr.equals("")) {
         if (dgcPolicyStr.equalsIgnoreCase("leased")) {
            return 0;
         } else if (dgcPolicyStr.equalsIgnoreCase("referenceCounted")) {
            return 1;
         } else if (dgcPolicyStr.equalsIgnoreCase("managed")) {
            return 2;
         } else if (dgcPolicyStr.equalsIgnoreCase("useItOrLoseIt")) {
            return 3;
         } else {
            return dgcPolicyStr.equalsIgnoreCase("deactivateOnMethodBoundries") ? 4 : -1;
         }
      } else {
         return -1;
      }
   }

   private Constructor getRemoteRefConstructor() throws ClassNotFoundException, NoSuchMethodException {
      if (this.remoteRefCon != null) {
         return this.remoteRefCon;
      } else {
         if (this.activatableObject) {
            if (this.isClusterable()) {
               this.remoteRefCon = this.getRemoteReferenceClass().getConstructor(Integer.TYPE, HostID.class, Object.class, Object.class);
            } else {
               this.remoteRefCon = this.getRemoteReferenceClass().getConstructor(Integer.TYPE, HostID.class, Object.class);
            }
         } else {
            this.remoteRefCon = this.getRemoteReferenceClass().getConstructor(Integer.TYPE, HostID.class);
         }

         return this.remoteRefCon;
      }
   }

   private RemoteReference getActivatableRemoteReference(int oid, Object impl) throws RemoteException {
      Object activationID = ((Activatable)impl).getActivationID();

      try {
         return (RemoteReference)this.getRemoteRefConstructor().newInstance(new Integer(oid), LocalServerIdentity.getIdentity(), activationID);
      } catch (NoSuchMethodException var6) {
         throw new ExportException("RemoteReference class missing constructor: " + this.getRemoteReferenceClassName(), var6);
      } catch (IllegalAccessException var7) {
         throw new ExportException("RemoteReference class constructor not public: " + this.getRemoteReferenceClassName(), var7);
      } catch (ClassNotFoundException var8) {
         throw new ExportException("Failed to load class: " + this.getRemoteReferenceClassName(), var8);
      } catch (InstantiationException var9) {
         throw new ExportException("Failed to instantiate RemoteReference: " + this.getRemoteReferenceClassName(), var9);
      } catch (InvocationTargetException var10) {
         Throwable nested = var10.getTargetException();
         if (!(nested instanceof Exception)) {
            nested = var10;
         }

         throw new ExportException("Failed to invoke constructor for RemoteReference: " + this.getRemoteReferenceClassName(), (Exception)nested);
      }
   }

   private String getDefaultServerRefClassName() {
      if (this.activatableObject) {
         return "weblogic.rmi.internal.activation.ActivatableServerRef";
      } else {
         return this.corbaObject ? "weblogic.corba.idl.CorbaServerRef" : "weblogic.rmi.internal.BasicServerRef";
      }
   }

   private String getDefaultRemoteRefClassName() {
      switch (this.dgcPolicy) {
         case -1:
         case 0:
            return "weblogic.rmi.internal.LeasedRemoteRef";
         case 1:
            throw new NotImplementedException();
         case 2:
         case 3:
         case 4:
            return "weblogic.rmi.internal.BasicRemoteRef";
         default:
            throw new AssertionError("Unknown DGC Policy specified: " + this.dgcPolicy);
      }
   }

   public String getConfidentiality() {
      return this.confidentiality;
   }

   public String getClientCertAuthentication() {
      return this.clientCertAuthentication;
   }

   public String getClientAuthentication() {
      return this.clientAuthentication;
   }

   public String getIdentityAssertion() {
      return this.identityAssertion;
   }

   public String getIntegrity() {
      return this.integrity;
   }

   public boolean getStatefulAuthentication() {
      return this.statefulAuthentication;
   }

   private boolean toBoolean(String b) {
      return Boolean.valueOf(b);
   }

   void nullifyActivationRuntimeProperties() {
      this.dgcPolicy = 0;
      this.remoteRefClassName = "weblogic.rmi.internal.BasicRemoteRef";
      this.serverRefClassName = "weblogic.rmi.internal.BasicServerRef";
      this.remoteRefCon = null;
      this.serverRefCon = null;
      this.activatableObject = false;

      try {
         this.getRemoteRefConstructor();
      } catch (Exception var2) {
         throw (Error)(new AssertionError("Interop properties are corrupted")).initCause(var2);
      }
   }

   public Source getRuntimeDescriptorSource() throws IOException {
      if (this.arraySource == null) {
         this.generateXMLDescriptorSource();
      }

      return this.arraySource;
   }

   public boolean hasCustomMethodDescriptors() {
      return this.customMethodDescriptors;
   }

   private Source generateXMLDescriptorSource() throws IOException {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      String fileName = "";
      if (this.remoteClass != null) {
         fileName = this.remoteClass.getName();
      }

      XMLDescriptorCreator xmlDesc = new XMLDescriptorCreator(out, fileName);
      this.writeXMLDescriptorStream(xmlDesc);
      this.arraySource = new ByteArraySource(out.toByteArray(), (URL)null);
      return this.arraySource;
   }

   public void generateXMLDescriptor(String className, String rootDirName) throws IOException {
      XMLDescriptorCreator xmldesc = new XMLDescriptorCreator(className, rootDirName);
      this.writeXMLDescriptorStream(xmldesc);
   }

   private void writeXMLDescriptorStream(XMLDescriptorCreator xmlDesc) throws IOException {
      if (this.useServerSideStubs) {
         xmlDesc.useServerSideStubs();
      }

      if (!this.enableCallByReference) {
         xmlDesc.disableLocalCallsByReference();
      }

      if (this.remoteRefClassName != null) {
         xmlDesc.setRemoteRefClassName(this.remoteRefClassName);
      }

      if (this.serverRefClassName != null) {
         xmlDesc.setServerRefClassName(this.serverRefClassName);
      }

      int initialRef = this.getInitialReference();
      if (initialRef != -1) {
         xmlDesc.setInitialReference(String.valueOf(initialRef));
      }

      if (this.dispatchPolicy != null) {
         xmlDesc.setDispatchPolicy(this.dispatchPolicy);
      }

      if (this.networkAccessPoint != null) {
         xmlDesc.setNetworkAccessPoint(this.networkAccessPoint);
      }

      String dgcPolicyStr = this.getDGCPolicyString(this.dgcPolicy);
      if (dgcPolicyStr != null && dgcPolicyStr.length() > 0) {
         xmlDesc.setDGCPolicy(dgcPolicyStr);
      }

      if (this.clusterable) {
         xmlDesc.setClusterable();
      }

      if (this.propagateEnvironment) {
         xmlDesc.setPropagateEnvironment();
      }

      if (this.loadAlgorithm != null && !this.loadAlgorithm.equals("default")) {
         xmlDesc.setLoadAlgorithm(this.loadAlgorithm);
      }

      if (this.callRouterClassName != null) {
         xmlDesc.setCallRouter(this.callRouterClassName);
      }

      if (this.replicaHandlerClassName != null) {
         xmlDesc.setReplicaHandler(this.replicaHandlerClassName);
      }

      if (this.stickToFirstServer) {
         xmlDesc.setStickToFirstServer();
      }

      if (this.confidentiality != null) {
         xmlDesc.setConfidentiality(this.confidentiality);
      }

      if (this.clientCertAuthentication != null) {
         xmlDesc.setClientCertAuthentication(this.clientCertAuthentication);
      }

      if (this.clientAuthentication != null) {
         xmlDesc.setClientAuthentication(this.clientAuthentication);
      }

      if (this.identityAssertion != null) {
         xmlDesc.setIdentityAssertion(this.identityAssertion);
      }

      if (this.integrity != null) {
         xmlDesc.setIntegrity(this.integrity);
      }

      ArrayList list = new ArrayList();
      if (this.methodDescriptors != null) {
         Iterator itr = this.methodDescriptors.keySet().iterator();

         while(itr.hasNext()) {
            String signature = (String)itr.next();
            ArrayMap methodDesc = (ArrayMap)this.methodDescriptors.get(signature);
            methodDesc.put("name", signature);
            list.add(methodDesc);
         }
      } else {
         list.add(this.getMethodDescriptorAttributesMap(this.defaultMethodDescriptor, true));
      }

      xmlDesc.setMethodDescriptors(list);
      xmlDesc.createDescriptor();
   }

   private String getDGCPolicyString(int dgcPolicy) {
      switch (dgcPolicy) {
         case -1:
            return "";
         case 0:
            return "leased";
         case 1:
            return "referenceCounted";
         case 2:
            return "managed";
         case 3:
            return "useItOrLoseIt";
         case 4:
            return "deactivateOnMethodBoundries";
         default:
            return "";
      }
   }

   private ArrayMap getMethodDescriptorAttributesMap(MethodDescriptor md, boolean defaultDescriptor) {
      if (md == null) {
         return null;
      } else {
         ArrayMap methodAttributes = new ArrayMap();
         if (defaultDescriptor) {
            methodAttributes.put("name", "*");
         } else {
            methodAttributes.put("name", md.getSignature());
         }

         Class dispatchType = md.getDispatchType();
         if (dispatchType == FutureResponse.class) {
            methodAttributes.put("dispatch-context", "future");
         } else if (dispatchType == InboundRequest.class) {
            methodAttributes.put("dispatch-context", "request");
         }

         if (!md.isTransactional()) {
            methodAttributes.put("transactional", "false");
         }

         if (md.isOneway()) {
            methodAttributes.put("oneway", "true");
         }

         if (md.isOnewayTransactionalRequest()) {
            methodAttributes.put("oneway-transactional-request", "true");
         }

         if (md.isTransactionalOnewayResponse()) {
            methodAttributes.put("oneway-transactional-response", "true");
         }

         if (md.isIdempotent()) {
            methodAttributes.put("idempotent", "true");
         }

         if (md.requiresTransaction()) {
            methodAttributes.put("requires-transaction", "true");
         }

         if (md.getTimeOut() > 0) {
            methodAttributes.put("timeout", String.valueOf(md.getTimeOut()));
         }

         if (md.getRemoteExceptionWrapperClassName() != null) {
            methodAttributes.put("remote-exception-wrapper-classname", md.getRemoteExceptionWrapperClassName());
         }

         if (md.hasAsyncResponse()) {
            methodAttributes.put("asynchronous", "true");
         }

         return methodAttributes;
      }
   }
}
