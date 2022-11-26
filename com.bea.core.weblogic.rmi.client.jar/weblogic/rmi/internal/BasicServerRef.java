package weblogic.rmi.internal;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.ServerError;
import java.rmi.UnmarshalException;
import java.rmi.server.Unreferenced;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import weblogic.common.internal.VersionInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.health.OOMENotifier;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.KernelStatus;
import weblogic.kernel.QueueFullException;
import weblogic.kernel.QueueThrottleException;
import weblogic.kernel.ThreadLocalInitialValue;
import weblogic.protocol.ChannelHelperBase;
import weblogic.protocol.OutgoingMessage;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.LocalRemoteJVM;
import weblogic.rmi.RMILogger;
import weblogic.rmi.ServerShuttingDownException;
import weblogic.rmi.extensions.NotificationListener;
import weblogic.rmi.extensions.server.Collectable;
import weblogic.rmi.extensions.server.InvokableServerReference;
import weblogic.rmi.extensions.server.RemoteExceptionWrapper;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.internal.dgc.DGCPolicyConstants;
import weblogic.rmi.internal.dgc.DGCServerImpl;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.Interceptor;
import weblogic.rmi.spi.InterceptorManager;
import weblogic.rmi.spi.InvokeHandler;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.trace.Trace;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class BasicServerRef implements InvokableServerReference, InvokeHandler, Collectable, OperationConstants, DGCPolicyConstants {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean ASSERT = false;
   private static final boolean DEBUG = false;
   private static final boolean KERNEL_ID_MUST_USE_ADMIN_CHANNEL = initProperty("weblogic.checkKernelIdUsage", false);
   private static final boolean KERNEL_ID_MUST_USE_ADMIN_CHANNEL_LOG_ONLY = initProperty("weblogic.checkKernelIdUsageLogOnly", false);
   private static final boolean tracingEnabled = RMIEnvironment.getEnvironment().isTracingEnabled();
   private static final List commonInterceptors;
   private static AtomicReference wldfDyeInjectionInterceptor = new AtomicReference();
   private ComponentInvocationContext invocationContext;
   private List interceptors;
   private Object implementation;
   private ClassLoader classloader;
   private RuntimeDescriptor descriptor;
   private final AtomicLong refCount;
   private long lease;
   private String applicationName;
   private int oid;
   private final String networkAccessPoint;
   private final String implementationClassName;
   private NotificationListener notificationListener;
   private static final DebugLogger debugDgcEnrollment = DebugLogger.getDebugLogger("DebugDGCEnrollment");
   private static final DebugLogger debugMessaging = DebugLogger.getDebugLogger("DebugMessaging");
   protected static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final AuditableThreadLocal remoteIndicator = AuditableThreadLocalFactory.createThreadLocal(new ThreadLocalInitialValue() {
      protected Object initialValue() {
         return false;
      }
   });
   private static OOMENotifier oomeNotifier;
   private static final ServerError PRE_ALLOC_SERVER_ERROR;

   public static boolean isRemote() {
      return (Boolean)remoteIndicator.get();
   }

   static List getCommonInterceptors() {
      return commonInterceptors;
   }

   private static boolean initProperty(String name, boolean defaultValue) {
      try {
         boolean flag = Boolean.getBoolean(name);
         if (flag) {
            System.out.println("********************************************");
            System.out.println("-D" + name + "=" + flag);
            System.out.println("********************************************");
         }

         return flag;
      } catch (SecurityException var3) {
         return defaultValue;
      } catch (NumberFormatException var4) {
         return defaultValue;
      }
   }

   static void initOOMENotifier(OOMENotifier oomeNotifier) {
      BasicServerRef.oomeNotifier = oomeNotifier;
   }

   BasicServerRef(int oid) {
      this.interceptors = new ArrayList();
      this.refCount = new AtomicLong(0L);
      this.lease = 0L;
      this.oid = oid;
      this.networkAccessPoint = null;
      this.implementationClassName = "";
      this.invocationContext = null;
   }

   public BasicServerRef(Object o) throws RemoteException {
      this(OIDManager.getInstance().getNextObjectID(), o);
   }

   public BasicServerRef(int oid, Object o) throws RemoteException {
      this(o.getClass(), oid);
      this.implementation = o;
      if (this.implementation instanceof NotificationListener) {
         this.notificationListener = (NotificationListener)this.implementation;
      }

   }

   public BasicServerRef(Object o, int dgcPolicy) throws RemoteException {
      this(o);
      if (dgcPolicy == 3) {
         this.lease = BasicServerRef.OIDManagerMaker.SINGLETON.getNewLease();
      }

      ((BasicRuntimeDescriptor)this.descriptor).nullifyActivationRuntimeProperties();
   }

   protected BasicServerRef(Class c, int oid) throws RemoteException {
      this.interceptors = new ArrayList();
      this.refCount = new AtomicLong(0L);
      this.lease = 0L;
      this.invocationContext = RmiInvocationFacade.getCurrentComponentInvocationContext(kernelId);
      this.oid = oid;
      this.implementationClassName = c.getName();
      this.initializeDGCPolicy(c);
      this.networkAccessPoint = this.descriptor.getNetworkAccessPoint();
      this.classloader = c.getClassLoader();
      if (this.classloader instanceof GenericClassLoader) {
         this.applicationName = ((GenericClassLoader)this.classloader).getAnnotation().getAnnotationString();
      } else {
         this.classloader = null;
      }

      if (KernelStatus.DEBUG && debugDgcEnrollment.isDebugEnabled()) {
         logExportingRemoteObject(c.getName(), oid);
      }

   }

   public boolean equals(Object obj) {
      if (!(obj instanceof BasicServerRef)) {
         return false;
      } else {
         BasicServerRef other = (BasicServerRef)obj;
         return other.oid == this.oid;
      }
   }

   public int hashCode() {
      return this.oid;
   }

   public final int getObjectID() {
      return this.oid;
   }

   public void addInterceptor(ServerReferenceInterceptor interceptor) {
      this.interceptors.add(interceptor);
   }

   List getInterceptors() {
      return Collections.unmodifiableList(this.interceptors);
   }

   private static void logExportingRemoteObject(String className, int oid) {
      RMILogger.logExportingRemoteObject(className, oid);
   }

   public String toString() {
      return String.valueOf(System.identityHashCode(this));
   }

   private void initializeDGCPolicy(Class c) throws RemoteException {
      this.descriptor = DescriptorManager.getDescriptor(c);
      switch (this.descriptor.getDGCPolicy()) {
         case -1:
         case 0:
         case 1:
         case 3:
            this.lease = BasicServerRef.OIDManagerMaker.SINGLETON.getNewLease();
            break;
         case 2:
            this.refCount.set(2147483647L);
         case 4:
            break;
         default:
            throw new AssertionError("Unknown DGC Policy specified: " + this.descriptor.getDGCPolicy());
      }

   }

   public ServerReference exportObject() {
      BasicServerRef.OIDManagerMaker.SINGLETON.ensureExported(this);
      return this;
   }

   protected static void trySendThrowableBeforeInterceptor(InboundRequest request, Throwable t) {
      try {
         new ReplyOnError(request.getOutboundResponse(), t);
      } catch (IOException var3) {
         RMILogger.logException("Unable to send error response to client", var3);
      }

   }

   protected static void trySendThrowable(InboundRequest request, Throwable t) {
      try {
         Throwable throwable = t;
         if (t instanceof QueueFullException) {
            throwable = new QueueThrottleException(t.getMessage(), t);
         }

         new ReplyOnError(request, request.getOutboundResponse(), (Throwable)throwable);
      } catch (IOException var3) {
         RMILogger.logException("Unable to send error response to client", var3);
      }

   }

   public void dispatch(InboundRequest request, InvokeHandler invoker) {
      RuntimeMethodDescriptor md = null;

      try {
         try {
            md = request.getRuntimeMethodDescriptor(this.descriptor);
         } catch (IOException var16) {
            trySendThrowableBeforeInterceptor(request, new UnmarshalException("Could not unmarshal method ID", var16));
            return;
         }

         Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
         if (ti != null) {
            if (md.isTransactionalOnewayResponse()) {
               ti.receiveResponse(request.getTxContext());
            } else {
               ti.receiveRequest(request.getTxContext());
            }
         }

         if (request.isCollocated()) {
            this.handleRequest(request, invoker, md);
         } else if (this.isBootstrapCall()) {
            byte qos = request.getServerChannel().getProtocol().getQOS();
            WorkManager manager = this.getWorkManager(md, qos);
            manager.schedule(RMIEnvironment.getEnvironment().createExecuteRequest(this, request, md, invoker, (AuthenticatedSubject)null));
         } else {
            if (!this.acceptRequest(request)) {
               throw new ServerShuttingDownException("Server is being shut down");
            }

            AuthenticatedSubject subject = (AuthenticatedSubject)request.getSubject();
            ServerChannel sc = request.getServerChannel();
            this.checkPriviledges(subject, sc, request.getEndPoint());
            WorkManager wm = this.getWorkManager(sc, md, subject);
            wm.schedule(RMIEnvironment.getEnvironment().createExecuteRequest(this, request, md, invoker, subject));
         }
      } catch (RuntimeException var18) {
         RMILogger.logRuntimeException(this.toString(), var18);
         if (!md.isOneway()) {
            trySendThrowable(request, var18);
         }
      } catch (RemoteException var19) {
         if (!md.isOneway()) {
            trySendThrowable(request, var19);
         } else if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
            RMILogger.logException(this.toString(), var19);
         }
      } catch (Error var20) {
         Error er = var20;

         try {
            RMILogger.logErrorDisp(er);
            if (!md.isOneway()) {
               trySendThrowable(request, new ServerError("A error occurred while attampting to dispatch the server", er));
            }
         } catch (Throwable var15) {
            RMILogger.logError("Throwable was thrown and ignored while executing: " + this.getMethodStr(md), var15);
         } finally {
            if (var20 instanceof OutOfMemoryError) {
               if (oomeNotifier == null) {
                  throw var20;
               }

               oomeNotifier.notifyOOME((OutOfMemoryError)var20);
            } else if (var20 instanceof ThreadDeath) {
               throw var20;
            }

         }
      }

   }

   private void ensureAdministratorUsesAdminChannel(AuthenticatedSubject subject, ServerChannel channel, EndPoint endPoint) throws RemoteException {
      if (KernelStatus.isServer()) {
         SecurityException se;
         if ((KERNEL_ID_MUST_USE_ADMIN_CHANNEL || endPoint.getCreationTime() <= ChannelHelperBase.getAdminChannelCreationTime()) && ChannelHelperBase.isLocalAdminChannelEnabled() && !ChannelHelperBase.isAdminChannel(channel) && SubjectUtils.isUserAnAdministrator(subject)) {
            se = new SecurityException("User '" + subject + "' has administration role. All tasks by adminstrators must go through an Administration Port. Channel:" + channel + ", endpoint:" + endPoint + ", serverRef:" + this.toString());
            RMILogger.logException("Remote call rejected due to wrong channel usage", se);
            throw new RemoteException(se.getMessage(), se);
         } else {
            if (KERNEL_ID_MUST_USE_ADMIN_CHANNEL_LOG_ONLY && ChannelHelperBase.isLocalAdminChannelEnabled() && !ChannelHelperBase.isAdminChannel(channel) && SubjectUtils.isUserAnAdministrator(subject)) {
               se = new SecurityException("User '" + subject + "' has administration role. All tasks by adminstrators must go through an Administration Port. Channel:" + channel + ", endpoint:" + endPoint + ", serverRef:" + this.toString());
               RMILogger.logException("Remote call rejected due to wrong channel usage", se);
            }

         }
      }
   }

   protected WorkManager getWorkManager(ServerChannel sc, RuntimeMethodDescriptor md, AuthenticatedSubject subject) {
      return this.getWorkManager(md, subject);
   }

   protected WorkManager getWorkManager(RuntimeMethodDescriptor md, AuthenticatedSubject subject) {
      return md.getWorkManager();
   }

   private WorkManager getWorkManager(RuntimeMethodDescriptor md, byte qos) {
      return qos == 103 ? WorkManagerFactory.getInstance().getSystem() : md.getWorkManager();
   }

   public void handleRequest(final InboundRequest request, final InvokeHandler invoker, final RuntimeMethodDescriptor md) {
      Throwable e = null;
      Thread currentThread = null;
      ClassLoader clSave = null;
      ClassLoader newCCL = this.getApplicationClassLoader();
      if (newCCL != null) {
         currentThread = Thread.currentThread();
         clSave = currentThread.getContextClassLoader();
         currentThread.setContextClassLoader(newCCL);
      }

      OutboundResponse resp = null;

      try {
         resp = md.isOneway() ? null : request.getOutboundResponse();
         this.preInvoke(request, md);
         final OutboundResponse response = resp;
         AuthenticatedSubject subject = null;

         try {
            subject = (AuthenticatedSubject)request.getSubject();
         } catch (SecurityException var92) {
         }

         if (this.isBootstrapCall() && subject == null) {
            subject = SubjectUtils.getAnonymousSubject();
         }

         TimeoutChecker tc = null;

         try {
            tc = RMIDiagnosticUtil.initTimeoutCheckerIfNeeded();
            SecurityManager.runAs(kernelId, subject, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  invoker.invoke(md, request, response);
                  return null;
               }
            });
         } finally {
            if (tc != null) {
               tc.checkTimeout();
            }

         }
      } catch (PrivilegedActionException var95) {
         e = var95.getException();
      } catch (OutOfMemoryError var96) {
         e = var96;
         if (oomeNotifier == null) {
            throw var96;
         }

         oomeNotifier.notifyOOME(var96);
      } catch (Throwable var97) {
         if (var97 instanceof UndeclaredThrowableException) {
            e = ((UndeclaredThrowableException)var97).getUndeclaredThrowable();
         } else {
            e = var97;
         }
      } finally {
         try {
            this.postInvoke(md, request, resp, (Throwable)e);
         } finally {
            if (clSave != null) {
               currentThread.setContextClassLoader(clSave);
            }

         }
      }

   }

   private void preInvoke(InboundRequest request, RuntimeMethodDescriptor md) throws RemoteException {
      Iterator var3 = commonInterceptors.iterator();

      ServerReferenceInterceptor dyeInjectionInterceptor;
      while(var3.hasNext()) {
         dyeInjectionInterceptor = (ServerReferenceInterceptor)var3.next();
         dyeInjectionInterceptor.preInvoke(this, request, md);
      }

      var3 = this.interceptors.iterator();

      while(var3.hasNext()) {
         dyeInjectionInterceptor = (ServerReferenceInterceptor)var3.next();
         dyeInjectionInterceptor.preInvoke(this, request, md);
      }

      Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
      if (ti != null) {
         ti.dispatchRequest(request.getTxContext());
      }

      try {
         if (tracingEnabled) {
            byte[] trace = (byte[])((byte[])request.getContext(4));
            if (trace != null) {
               Trace.propagateTrace(trace);
            }
         }

         request.retrieveThreadLocalContext();
      } catch (IOException var5) {
         throw new UnmarshalException("Unable to read thread-local data from request.", var5);
      }

      if (md.hasAsyncResponse()) {
         this.setFutureObjectID(request, md);
      }

      if (this.getDescriptor().getDGCPolicy() == 3) {
         this.renewLease();
      }

      dyeInjectionInterceptor = (ServerReferenceInterceptor)wldfDyeInjectionInterceptor.get();
      if (dyeInjectionInterceptor != null) {
         dyeInjectionInterceptor.preInvoke(this, request, md);
      }

   }

   public static void enableWLDFDyeInjection(Boolean enabled) throws Exception {
      if (enabled) {
         wldfDyeInjectionInterceptor.set(DyeInjectionInterceptor.getInstance());
      } else {
         wldfDyeInjectionInterceptor.set((Object)null);
      }

   }

   public void invoke(RuntimeMethodDescriptor md, InboundRequest request, OutboundResponse response) throws Exception {
      if (this.networkAccessPoint != null) {
         String inboundChannel = request.getServerChannel().getChannelName();
         if (!this.networkAccessPoint.equals(inboundChannel)) {
            throw new SecurityException("Remote method calls are allowed only over NetworkAccessPoint '" + this.networkAccessPoint + "'");
         }
      }

      remoteIndicator.set(true);
      Skeleton skeleton = this.getDescriptor().getSkeleton();
      if (md.getImplRespondsToClient()) {
         response = new BasicFutureResponse(request, (OutboundResponse)response);
      }

      if (this.notificationListener != null) {
         this.notificationListener.notifyRemoteCallBegin();
      }

      try {
         EndPoint endPoint = request.getEndPoint();
         if (endPoint != null && endPoint instanceof LocalRemoteJVM) {
            ThreadPreferredHost.set(endPoint.getHostID());
         }

         skeleton.invoke(md.getIndex(), request, (OutboundResponse)response, this.implementation);
      } finally {
         ThreadPreferredHost.set((HostID)null);
         remoteIndicator.set(false);
      }

   }

   private void postInvoke(RuntimeMethodDescriptor md, InboundRequest request, OutboundResponse response, Throwable t) {
      boolean var18 = false;

      Iterator var5;
      ServerReferenceInterceptor interceptor;
      label311: {
         try {
            try {
               try {
                  var18 = true;
                  if (this.getDescriptor().getDGCPolicy() == 4) {
                     this.unexportObject(true);
                  }
               } catch (RemoteException var24) {
                  RMILogger.logUnexport(this.toString());
               }

               if (t != null) {
                  throw t;
               }

               if (response != null) {
                  if (!md.getImplRespondsToClient()) {
                     if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
                        this.debugDisplayResponseInfo(md, request, response);
                     }

                     response.send();
                     var18 = false;
                  } else {
                     var18 = false;
                  }
               } else {
                  var18 = false;
               }
               break label311;
            } catch (Throwable var25) {
               this.logThrowable(md, var25);
               if (response != null) {
                  try {
                     response.close();
                  } catch (IOException var22) {
                  }

                  try {
                     response = request.getOutboundResponse();
                     response.transferThreadLocalContext(request);
                  } catch (IOException var21) {
                     RMILogger.logAssociateTX(var21);
                  }
               }
            }

            handleThrowable(var25, response);
            var18 = false;
         } finally {
            if (var18) {
               Iterator var8 = commonInterceptors.iterator();

               ServerReferenceInterceptor interceptor;
               while(var8.hasNext()) {
                  interceptor = (ServerReferenceInterceptor)var8.next();
                  if (interceptor.isUnconditional()) {
                     interceptor.postInvokeUnconditional(this, request, md);
                  }
               }

               var8 = this.interceptors.iterator();

               while(var8.hasNext()) {
                  interceptor = (ServerReferenceInterceptor)var8.next();
                  if (interceptor.isUnconditional()) {
                     interceptor.postInvokeUnconditional(this, request, md);
                  }
               }

               try {
                  this.resetFutureObjectID(request);
               } catch (UnmarshalException var19) {
               }

               if (this.notificationListener != null) {
                  this.notificationListener.notifyRemoteCallEnd();
               }

            }
         }

         var5 = commonInterceptors.iterator();

         while(var5.hasNext()) {
            interceptor = (ServerReferenceInterceptor)var5.next();
            if (interceptor.isUnconditional()) {
               interceptor.postInvokeUnconditional(this, request, md);
            }
         }

         var5 = this.interceptors.iterator();

         while(var5.hasNext()) {
            interceptor = (ServerReferenceInterceptor)var5.next();
            if (interceptor.isUnconditional()) {
               interceptor.postInvokeUnconditional(this, request, md);
            }
         }

         try {
            this.resetFutureObjectID(request);
         } catch (UnmarshalException var20) {
         }

         if (this.notificationListener != null) {
            this.notificationListener.notifyRemoteCallEnd();
         }

         return;
      }

      var5 = commonInterceptors.iterator();

      while(var5.hasNext()) {
         interceptor = (ServerReferenceInterceptor)var5.next();
         if (interceptor.isUnconditional()) {
            interceptor.postInvokeUnconditional(this, request, md);
         }
      }

      var5 = this.interceptors.iterator();

      while(var5.hasNext()) {
         interceptor = (ServerReferenceInterceptor)var5.next();
         if (interceptor.isUnconditional()) {
            interceptor.postInvokeUnconditional(this, request, md);
         }
      }

      try {
         this.resetFutureObjectID(request);
      } catch (UnmarshalException var23) {
      }

      if (this.notificationListener != null) {
         this.notificationListener.notifyRemoteCallEnd();
      }

   }

   private void debugDisplayResponseInfo(RuntimeMethodDescriptor md, InboundRequest request, OutboundResponse response) throws IOException {
      if (response instanceof OutgoingMessage) {
         int rspSize = ((OutgoingMessage)response).getLength();
         int maxSize = request.getServerChannel().getMaxMessageSize();
         if (rspSize > maxSize) {
            debugMessaging.debug("BasicServerRef.postInvoke response message size: " + rspSize + " for method '" + md + "' to sender: " + request.getEndPoint() + " max size: " + maxSize, new Throwable());
         }
      }

   }

   private void logThrowable(RuntimeMethodDescriptor md, Throwable th) {
      if (th instanceof RuntimeException) {
         RMILogger.logRuntimeException(this.getMethodStr(md), th);
      } else if (th instanceof Error) {
         RMILogger.logError(this.getMethodStr(md), th);
      } else if (RMIEnvironment.getEnvironment().isLogRemoteExceptions()) {
         RMILogger.logException(this.getMethodStr(md), th);
      }

   }

   protected void setFutureObjectID(InboundRequest request, RuntimeMethodDescriptor md) throws UnmarshalException {
      try {
         Object obj = request.getContext(25);
         if (obj != null) {
            ((FutureResultHandle)this.implementation).__WL_setFutureResultID((FutureResultID)obj, kernelId);
         } else {
            Class cls = md.getMethod().getReturnType();
            if (Future.class.isAssignableFrom(cls)) {
               throw new UnmarshalException("Request received from client that is incompatible with WLS server " + VersionInfo.theOne().getReleaseVersion());
            }
         }

      } catch (IOException var5) {
         throw new UnmarshalException("Unable to get FUTURE_OBJECT_ID from request. ", var5);
      }
   }

   private void resetFutureObjectID(InboundRequest request) throws UnmarshalException {
      try {
         Object obj = request.getContext(25);
         if (obj != null) {
            ((FutureResultHandle)this.implementation).__WL_setFutureResultID((FutureResultID)null, kernelId);
         }
      } catch (IOException var3) {
      }

   }

   private String getMethodStr(RuntimeMethodDescriptor md) {
      return this.implementationClassName == null ? this.oid + "." + md : this.implementationClassName + "." + md;
   }

   private static final ServerError allocServerError() {
      OutOfMemoryError oome = new OutOfMemoryError();
      oome.setStackTrace(new StackTraceElement[0]);
      ServerError se = new ServerError("A error occurred the server", oome);
      se.setStackTrace(new StackTraceElement[0]);
      return se;
   }

   protected static void handleThrowable(Throwable t, OutboundResponse response) {
      if (response != null) {
         if (t instanceof RemoteExceptionWrapper) {
            response.sendThrowable(t.getCause());
         } else if (t instanceof OutOfMemoryError) {
            response.sendThrowable(PRE_ALLOC_SERVER_ERROR);
         } else if (t instanceof Error && !RMIEnvironment.getEnvironment().isIIOPResponse(response)) {
            response.sendThrowable(new ServerError("A error occurred the server", (Error)t));
         } else {
            response.sendThrowable(t);
         }
      }

      if (t instanceof OutOfMemoryError) {
         throw (OutOfMemoryError)t;
      } else if (t instanceof ThreadDeath) {
         throw (ThreadDeath)t;
      }
   }

   public final ClassLoader getApplicationClassLoader() {
      return this.classloader;
   }

   public final Object getImplementation() {
      return this.implementation;
   }

   public ComponentInvocationContext getInvocationContext() {
      return this.invocationContext;
   }

   public void setInvocationContext(ComponentInvocationContext invocationContext) {
      this.invocationContext = invocationContext;
   }

   public final boolean unexportObject(boolean force) throws NoSuchObjectException {
      if (force) {
         return BasicServerRef.OIDManagerMaker.SINGLETON.removeServerReference((ServerReference)this) != null;
      } else {
         this.decrementRefCount();
         return true;
      }
   }

   public final boolean isExported() {
      return false;
   }

   public RuntimeDescriptor getDescriptor() {
      return this.descriptor;
   }

   public StubReference getStubReference() throws RemoteException {
      return new StubInfo(this.getLocalRef(), this.descriptor.getClientRuntimeDescriptor(this.getApplicationName()), this.descriptor.getStubClassName());
   }

   public ServerReference getDelegate() {
      return this;
   }

   public RemoteReference getRemoteRef() throws RemoteException {
      return this.descriptor.getRemoteReference(this.getObjectID(), this.getImplementation());
   }

   public final RemoteReference getLocalRef() {
      CollocatedRemoteRef crr = new CollocatedRemoteRef(this);
      if (this.getDescriptor().getDGCPolicy() != 2) {
         DGCServerImpl.addPhantomRef(new PhantomRef(crr, DGCServerImpl.getReferenceQueue()));
      }

      return crr;
   }

   public final void sweep(long expiredLease) {
      if (this.lease <= expiredLease) {
         if (this.refCount.get() <= 0L) {
            BasicServerRef.OIDManagerMaker.SINGLETON.removeServerReference((ServerReference)this);
            if (this.getImplementation() instanceof Unreferenced) {
               WorkManagerFactory.getInstance().getSystem().schedule(new UnreferencedExecuteRequest((Unreferenced)this.getImplementation()));
            }

         }
      }
   }

   public final void incrementRefCount() {
      if (this.descriptor.getDGCPolicy() != 2) {
         this.refCount.incrementAndGet();
      }
   }

   public final void decrementRefCount() {
      if (this.descriptor.getDGCPolicy() != 2) {
         this.refCount.decrementAndGet();
      }
   }

   public final void renewLease() {
      switch (this.descriptor.getDGCPolicy()) {
         case -1:
         case 0:
         case 3:
            this.lease = BasicServerRef.OIDManagerMaker.SINGLETON.getNewLease();
            return;
         case 1:
         case 2:
         case 4:
            return;
         default:
            throw new AssertionError("Unknown DGC Policy specified: " + this.descriptor.getDGCPolicy());
      }
   }

   public final String getImplementationClassName() {
      return this.implementationClassName;
   }

   public final String getApplicationName() {
      return this.applicationName;
   }

   private boolean acceptRequest(InboundRequest request) {
      return RMIEnvironment.getEnvironment().rmiShutdownAcceptRequest(this.oid, (AuthenticatedSubject)request.getSubject()) && RMIEnvironment.getEnvironment().nonTxRmiShutdownAcceptRequest(this.oid, (AuthenticatedSubject)request.getSubject(), request.getTxContext());
   }

   private final boolean isBootstrapCall() {
      return this.getObjectID() == 27;
   }

   public void dispatchError(InboundRequest request, Throwable t) {
      boolean hasNoOutboundResponse;
      try {
         hasNoOutboundResponse = request.getRuntimeMethodDescriptor(this.descriptor).isOneway();
      } catch (IOException var5) {
         RMILogger.logException("Error getting runtimeMethodDescriptor for request: " + request, var5);
         hasNoOutboundResponse = true;
      }

      if (hasNoOutboundResponse) {
         handleThrowable(t, (OutboundResponse)null);
         RMILogger.logException("Logging error for request: " + request, t);
      } else {
         WorkManager wm = WorkManagerFactory.getInstance().getSystem();
         wm.schedule(new ErrorReporter(request, t));
      }
   }

   private boolean isExportedForApplication() {
      GenericClassLoader gcl;
      for(ClassLoader cl = Thread.currentThread().getContextClassLoader(); cl instanceof GenericClassLoader; cl = gcl.getParent()) {
         gcl = (GenericClassLoader)cl;
         String appName = gcl.getAnnotation().getAnnotationString();
         if (appName != null && appName.length() != 0) {
            return true;
         }
      }

      return false;
   }

   public void dispatch(InboundRequest request) {
      this.dispatch(request, this);
   }

   protected void checkPriviledges(AuthenticatedSubject subject, ServerChannel channel, EndPoint endPoint) throws RemoteException {
      this.ensureAdministratorUsesAdminChannel(subject, channel, endPoint);
   }

   static {
      List ci = new ArrayList();
      ci.add(CertificateValidationInterceptor.INSTANCE);
      ci.add(ClientInfoInterceptor.INSTANCE);
      ci.add(OneWayMethodInterceptor.INSTANCE);
      commonInterceptors = new CopyOnWriteArrayList(ci);
      PRE_ALLOC_SERVER_ERROR = allocServerError();
   }

   private static class ErrorReporter implements Runnable {
      InboundRequest request;
      Throwable problem;

      ErrorReporter(InboundRequest request, Throwable problem) {
         this.request = request;
         this.problem = problem;
      }

      public void run() {
         try {
            Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
            if (ti != null) {
               ti.dispatchRequest(this.request.getTxContext());
            }

            OutboundResponse response = this.request.getOutboundResponse();
            response.transferThreadLocalContext(this.request);
            BasicServerRef.handleThrowable(this.problem, response);
         } catch (IOException var3) {
            RMILogger.logException("Unable to send error response to client", var3);
         }

      }

      public final String toString() {
         return super.toString() + " - problem: '" + this.problem + "'";
      }
   }

   private static final class UnreferencedExecuteRequest extends WorkAdapter {
      final Unreferenced unrefed;

      UnreferencedExecuteRequest(Unreferenced unrefed) {
         this.unrefed = unrefed;
      }

      public void run() {
         this.unrefed.unreferenced();
      }
   }

   private static final class OIDManagerMaker {
      private static final OIDManager SINGLETON = OIDManager.getInstance();
   }
}
