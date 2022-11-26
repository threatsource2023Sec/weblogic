package weblogic.rmi.internal;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.MarshalException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.ServerError;
import java.rmi.UnmarshalException;
import java.security.AccessController;
import javax.management.remote.rmi.RMIConnection;
import javax.management.remote.rmi.RMIServerImpl;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.RMILogger;
import weblogic.rmi.cluster.PiggybackResponse;
import weblogic.rmi.cluster.Version;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.CBVInputStream;
import weblogic.rmi.extensions.server.CBVOutputStream;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.internal.activation.ActivatableServerRef;
import weblogic.rmi.spi.AsyncCallback;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.InboundResponse;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.MsgOutput;
import weblogic.rmi.spi.OutboundRequest;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.AssertionError;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class ServerRequest implements OutboundRequest, InboundResponse, OperationConstants {
   private static final Object CBV_PLACEHOLDER = new Object();
   private static final ClassLoader SYSTEM_CLASSLOADER = ClassLoader.getSystemClassLoader();
   private RuntimeMethodDescriptor md;
   private ServerReference dispatcher;
   private Object txContext;
   private Object[] args;
   private Object result;
   private Object activationID;
   private Throwable throwable;
   private Object replicaInfo;
   private String partitionURL;

   public ServerRequest(ServerReference dispatcher, RuntimeMethodDescriptor md) throws IOException {
      this.dispatcher = dispatcher;
      this.md = md;
   }

   public ServerRequest(ServerReference dispatcher, RuntimeMethodDescriptor md, String partitionURL) throws IOException {
      this.dispatcher = dispatcher;
      this.md = md;
      this.partitionURL = partitionURL;
   }

   void init(ServerReference dispatcher, RuntimeMethodDescriptor md) {
      this.dispatcher = dispatcher;
      this.md = md;
   }

   public synchronized Throwable getThrowable() {
      if (this.throwable != null) {
         this.unmarshalThrowable();
      }

      return this.throwable;
   }

   public MsgInput getMsgInput() {
      throw new AssertionError();
   }

   public MsgOutput getMsgOutput() {
      throw new AssertionError();
   }

   public EndPoint getEndPoint() {
      throw new AssertionError();
   }

   public void marshalArgs(Object[] args) throws MarshalException {
      this.args = args;
   }

   public Object unmarshalReturn() throws UnmarshalException {
      if (this.result == null) {
         return this.result;
      } else {
         Object replacement = getReplacement(this.result);
         if (replacement != null) {
            return replacement;
         } else {
            ComponentInvocationContextManager mgr = ComponentInvocationContextManager.getInstance();
            ComponentInvocationContext cic = mgr.getCurrentComponentInvocationContext();
            if (cic.getApplicationName() == null) {
               cic = mgr.createComponentInvocationContext(cic.getPartitionName(), "_SERVER_REQUEST_", cic.getApplicationVersion(), cic.getModuleName(), cic.getComponentName());
            }

            try {
               ManagedInvocationContext mic = this.setCIC(cic);
               Throwable var5 = null;

               Object var9;
               try {
                  CBVOutputStream out = new CBVOutputStream();
                  out.writeObject(this.result);
                  out.flush();
                  CBVInputStream in = out.makeCBVInputStream();
                  Object returnValue = in.readObject();
                  out.close();
                  in.close();
                  var9 = returnValue;
               } catch (Throwable var20) {
                  var5 = var20;
                  throw var20;
               } finally {
                  if (mic != null) {
                     if (var5 != null) {
                        try {
                           mic.close();
                        } catch (Throwable var19) {
                           var5.addSuppressed(var19);
                        }
                     } else {
                        mic.close();
                     }
                  }

               }

               return var9;
            } catch (IOException var22) {
               throw new UnmarshalException("failed to unmarshal return type: " + this.md.getReturnType(), var22);
            } catch (ClassNotFoundException var23) {
               throw new UnmarshalException("failed to load return type: " + this.md.getReturnType(), var23);
            }
         }
      }
   }

   public PiggybackResponse getReplicaInfo() {
      if (this.replicaInfo != null) {
         Object impl = this.dispatcher.getImplementation();
         if (impl instanceof Remote) {
            try {
               ServerReference serverReference = ServerHelper.getServerReference((Remote)impl);
               if (serverReference instanceof ClusterAwareServerReference) {
                  ClusterAwareServerReference cAwareServerRef = (ClusterAwareServerReference)serverReference;
                  return cAwareServerRef.handlePiggybackRequest(this.replicaInfo);
               }
            } catch (NoSuchObjectException var4) {
            }
         }
      }

      return null;
   }

   public Object getTxContext() {
      return this.txContext;
   }

   public void setTxContext(Object txContext) {
      this.txContext = txContext;
   }

   public void close() {
   }

   public void sendOneWay() throws RemoteException {
      throw new AssertionError("collocated oneway calls not supported");
   }

   public void sendAsync(AsyncCallback callback) throws RemoteException {
      if (callback == null) {
         throw new RemoteException("collocated oneway calls not supported");
      } else if (!this.md.hasAsyncResponse()) {
         throw new RemoteException("md = " + this.md + " is not marked with an asynchronous annotation!");
      } else {
         WorkManager wm;
         if (this.md.workManagerAvailable()) {
            wm = this.md.getWorkManager();
         } else {
            wm = WorkManagerFactory.getInstance().getDefault();
         }

         wm.schedule(new WLSAsyncExecuteRequest(this, callback));
      }
   }

   public synchronized void setThrowable(Throwable t) {
      this.throwable = t;
      this.notify();
   }

   private ManagedInvocationContext setCIC(ComponentInvocationContext cic) {
      return cic != null ? RmiInvocationFacade.setInvocationContext(ServerRequest.KernelIdProvider.INSTANCE.getKernelId(), cic) : null;
   }

   public InboundResponse sendReceive() throws Throwable {
      try {
         this.md = this.md.getCanonical(this.dispatcher.getDescriptor());
         Thread currentThread = null;
         ClassLoader clSave = null;
         ClassLoader newCCL = this.dispatcher.getApplicationClassLoader();
         ComponentInvocationContext newCIC = this.isMBeanServerObject() ? null : this.dispatcher.getInvocationContext();
         EndPoint epSave = ServerHelper.getClientEndPointInternal();
         ServerChannel scSave = ServerHelper.getServerChannel();
         ServerHelper.setClientInfo((EndPoint)null, (ServerChannel)null);
         Object impl = null;
         Activator activator = null;
         if (this.activationID != null) {
            activator = ((ActivatableServerRef)this.dispatcher).getActivator();
         }

         boolean var31 = false;

         Throwable t2;
         try {
            var31 = true;
            if (newCCL != null) {
               currentThread = Thread.currentThread();
               clSave = currentThread.getContextClassLoader();
               currentThread.setContextClassLoader(newCCL);
            }

            ManagedInvocationContext mic = this.setCIC(newCIC);
            t2 = null;

            try {
               Skeleton skeleton = null;

               try {
                  skeleton = this.dispatcher.getDescriptor().getSkeleton();
               } catch (RemoteException var57) {
                  throw new UnmarshalException("Failed to load skeleton", var57);
               }

               if (this.args != null || this.activationID != null) {
                  this.copy(this.args);
               }

               try {
                  if (this.activationID != null) {
                     impl = activator.activate(this.activationID);
                     this.result = skeleton.invoke(this.md.getIndex(), this.args, impl);
                  } else {
                     this.result = skeleton.invoke(this.md.getIndex(), this.args, this.dispatcher.getImplementation());
                  }
               } catch (Throwable var56) {
                  this.throwable = var56;
               } finally {
                  if (this.activationID != null) {
                     activator.deactivate((Activatable)impl);
                  }

               }
            } catch (Throwable var59) {
               t2 = var59;
               throw var59;
            } finally {
               if (mic != null) {
                  if (t2 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var55) {
                        t2.addSuppressed(var55);
                     }
                  } else {
                     mic.close();
                  }
               }

            }

            var31 = false;
         } finally {
            if (var31) {
               if (clSave != null) {
                  currentThread.setContextClassLoader(clSave);
               }

               ServerHelper.setClientInfo(epSave, scSave);
               if (this.result != null) {
                  Object replacement = getReplacement(this.result);
                  if (replacement != null && !(replacement instanceof Serializable)) {
                     ClassLoader loader = replacement.getClass().getClassLoader();
                     if (loader != null && loader != SYSTEM_CLASSLOADER) {
                        this.throwable = new MarshalException("Failed to serialize " + replacement.getClass().getName());
                     }
                  }
               }

            }
         }

         if (clSave != null) {
            currentThread.setContextClassLoader(clSave);
         }

         ServerHelper.setClientInfo(epSave, scSave);
         if (this.result != null) {
            Object replacement = getReplacement(this.result);
            if (replacement != null && !(replacement instanceof Serializable)) {
               ClassLoader loader = replacement.getClass().getClassLoader();
               if (loader != null && loader != SYSTEM_CLASSLOADER) {
                  this.throwable = new MarshalException("Failed to serialize " + replacement.getClass().getName());
               }
            }
         }

         Throwable t = this.getThrowable();
         if (t == null) {
            return this;
         } else {
            t2 = t.fillInStackTrace();
            if (t2 != null) {
               throw t2;
            } else {
               t.initCause(new Exception("an exception is thrown from ServerRequest."));
               throw t;
            }
         }
      } catch (RuntimeException var62) {
         throw var62;
      } catch (RemoteException var63) {
         throw var63;
      } catch (OutOfMemoryError var64) {
         throw var64;
      } catch (Error var65) {
         RMILogger.logErrorServer(this.dispatcher.getImplementation().toString(), this.md.toString());
         throw new ServerError("A error occurred the server", var65);
      }
   }

   private boolean isMBeanServerObject() {
      return this.dispatcher.getImplementation() instanceof RMIServerImpl || this.dispatcher.getImplementation() instanceof RMIConnection;
   }

   private void setFutureObjectID(Object impl, Object obj) throws UnmarshalException {
      if (obj != null) {
         ((FutureResultHandle)impl).__WL_setFutureResultID((FutureResultID)obj, ServerRequest.KernelIdProvider.INSTANCE.getKernelId());
      }

   }

   private void copy(Object[] args) throws ClassNotFoundException, IOException {
      CBVOutputStream cbvout = new CBVOutputStream();
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            Object toCopy = args[i];
            if (toCopy != null) {
               Object replacement = getReplacement(toCopy);
               if (replacement != null) {
                  this.checkObjectSerialization(replacement);
                  args[i] = replacement;
               } else {
                  cbvout.writeObject(toCopy);
                  args[i] = CBV_PLACEHOLDER;
               }
            }
         }
      }

      if (this.activationID != null) {
         Object replacement = getReplacement(this.activationID);
         if (replacement != null) {
            this.activationID = replacement;
         } else {
            cbvout.writeObject(this.activationID);
            this.activationID = CBV_PLACEHOLDER;
         }
      }

      cbvout.flush();
      CBVInputStream cbvin = cbvout.makeCBVInputStream();
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            if (args[i] == CBV_PLACEHOLDER) {
               args[i] = cbvin.readObject();
            }
         }
      }

      if (this.activationID == CBV_PLACEHOLDER) {
         this.activationID = cbvin.readObject();
      }

      cbvout.close();
      cbvin.close();
   }

   public void setTimeOut(int msecs) {
   }

   public void setReplicaInfo(Version replicaInfo) throws IOException {
      this.replicaInfo = replicaInfo;
   }

   public void setActivationID(Object activationID) {
      this.activationID = activationID;
   }

   public void retrieveThreadLocalContext() throws IOException {
   }

   public void retrieveThreadLocalContext(boolean forceReset) throws IOException {
   }

   public void transferThreadLocalContext() throws IOException {
   }

   public void setContext(int id, Object data) throws IOException {
      if (this.md.hasAsyncResponse()) {
         Object impl;
         if (this.activationID != null) {
            Activator activator = ((ActivatableServerRef)this.dispatcher).getActivator();
            impl = activator.activate(this.activationID);
         } else {
            impl = this.dispatcher.getImplementation();
         }

         this.setFutureObjectID(impl, data);
      }

   }

   public Object getContext(int id) throws IOException {
      return null;
   }

   public void setRuntimeMethodDescriptor(RuntimeMethodDescriptor descriptor) throws IOException {
      this.md = descriptor;
   }

   public Object getActivatedPinnedRef() {
      return null;
   }

   private static Object getReplacement(Object o) {
      if (o instanceof String) {
         return o;
      } else {
         Class c = o.getClass();
         if (o instanceof Number && c.getClassLoader() == ClassLoader.getSystemClassLoader()) {
            return o;
         } else if (o instanceof Boolean) {
            return o;
         } else if (o instanceof Character) {
            return o;
         } else {
            if (c.isArray()) {
               if (o instanceof String[]) {
                  return ((String[])((String[])o)).clone();
               }

               if (o instanceof Number[] && c.getClassLoader() == ClassLoader.getSystemClassLoader()) {
                  return ((Number[])((Number[])o)).clone();
               }

               if (o instanceof Boolean[]) {
                  return ((Boolean[])((Boolean[])o)).clone();
               }

               if (o instanceof Character[]) {
                  return ((Character[])((Character[])o)).clone();
               }

               if (c.getComponentType().isPrimitive()) {
                  if (o instanceof byte[]) {
                     return ((byte[])((byte[])o)).clone();
                  }

                  if (o instanceof int[]) {
                     return ((int[])((int[])o)).clone();
                  }

                  if (o instanceof char[]) {
                     return ((char[])((char[])o)).clone();
                  }

                  if (o instanceof boolean[]) {
                     return ((boolean[])((boolean[])o)).clone();
                  }

                  if (o instanceof short[]) {
                     return ((short[])((short[])o)).clone();
                  }

                  if (o instanceof long[]) {
                     return ((long[])((long[])o)).clone();
                  }

                  if (o instanceof double[]) {
                     return ((double[])((double[])o)).clone();
                  }

                  if (o instanceof float[]) {
                     return ((float[])((float[])o)).clone();
                  }
               }
            }

            if (o instanceof Serializable) {
               return null;
            } else {
               return o instanceof Remote ? null : o;
            }
         }
      }
   }

   private void unmarshalThrowable() {
      try {
         CBVOutputStream out = new CBVOutputStream();
         out.writeObject(this.throwable);
         out.flush();
         CBVInputStream in = out.makeCBVInputStream();
         Throwable thr = (Throwable)in.readObject();
         out.close();
         in.close();
         this.throwable = thr;
      } catch (IOException var4) {
         throw new AssertionError("cannot unmarshaling throwable", var4);
      } catch (ClassNotFoundException var5) {
         throw new AssertionError("error unmarshaling throwable", var5);
      }
   }

   private void checkObjectSerialization(Object replacement) throws MarshalException {
      if (replacement != null && !(replacement instanceof Serializable)) {
         ClassLoader loader = replacement.getClass().getClassLoader();
         if (loader != null && loader != SYSTEM_CLASSLOADER) {
            throw new MarshalException("Failed to serialize " + replacement.getClass().getName());
         }
      }

   }

   private static enum KernelIdProvider {
      INSTANCE;

      private final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      private AuthenticatedSubject getKernelId() {
         return this.kernelId;
      }
   }
}
