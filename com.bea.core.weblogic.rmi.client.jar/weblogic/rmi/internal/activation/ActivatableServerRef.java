package weblogic.rmi.internal.activation;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.util.concurrent.Future;
import weblogic.common.internal.VersionInfo;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.extensions.NotificationListener;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.ActivatableServerReference;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.internal.BasicFutureResponse;
import weblogic.rmi.internal.BasicServerRef;
import weblogic.rmi.internal.FutureResultHandle;
import weblogic.rmi.internal.FutureResultID;
import weblogic.rmi.internal.InitialReferenceConstants;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.OutboundResponse;

public class ActivatableServerRef extends BasicServerRef implements InitialReferenceConstants, ActivatableServerReference {
   private final Activator activator;

   public ActivatableServerRef(Class c, Activator activator) throws RemoteException {
      this(c, OIDManager.getInstance().getNextObjectID(), activator);
   }

   public ActivatableServerRef(Class c, int oid, Activator activator) throws RemoteException {
      super(c, oid);
      this.activator = activator;
   }

   public StubReference getStubReference(Object aid) {
      return new StubInfo(this.getActivatableRef(aid), this.getDescriptor().getClientRuntimeDescriptor(this.getApplicationName()), this.getDescriptor().getStubClassName());
   }

   public RemoteReference getActivatableRef(Object aid) {
      return new ActivatableRemoteRef(this.getObjectID(), LocalServerIdentity.getIdentity(), aid);
   }

   public Object getImplementation(Object aid) throws RemoteException {
      return this.activator.activate(aid);
   }

   public Activator getActivator() {
      return this.activator;
   }

   public void invoke(RuntimeMethodDescriptor md, InboundRequest request, OutboundResponse response) throws Exception {
      Skeleton skeleton = this.getDescriptor().getSkeleton();
      if (md.getImplRespondsToClient()) {
         response = new BasicFutureResponse(request, (OutboundResponse)response);
      }

      Object aid = request.getActivationID();
      Activatable impl = this.activator.activate(aid);
      if (md.hasAsyncResponse()) {
         this.setFutureObjectIDOnActivated(request, md, impl);
      }

      if (impl instanceof NotificationListener) {
         ((NotificationListener)impl).notifyRemoteCallBegin();
      }

      try {
         skeleton.invoke(md.getIndex(), request, (OutboundResponse)response, impl);
      } catch (ClassCastException var11) {
         if (impl.getClass().getName().toLowerCase().contains("anonymous")) {
            System.out.format("+++ attempted to invoke impl: %s" + impl);
         }

         throw var11;
      } finally {
         if (impl instanceof NotificationListener) {
            ((NotificationListener)impl).notifyRemoteCallEnd();
         }

         if (md.hasAsyncResponse()) {
            this.resetFutureObjectIDOnActivated(request, impl);
         }

         this.activator.deactivate(impl);
      }

   }

   protected void setFutureObjectID(InboundRequest request, RuntimeMethodDescriptor md) throws UnmarshalException {
   }

   protected void setFutureObjectIDOnActivated(InboundRequest request, RuntimeMethodDescriptor md, Activatable implementation) throws UnmarshalException {
      try {
         Object obj = request.getContext(25);
         if (obj != null) {
            ((FutureResultHandle)implementation).__WL_setFutureResultID((FutureResultID)obj, kernelId);
         } else {
            Class cls = md.getMethod().getReturnType();
            if (Future.class.isAssignableFrom(cls)) {
               throw new UnmarshalException("Request received from client that is incompatible with WLS server " + VersionInfo.theOne().getReleaseVersion());
            }
         }

      } catch (IOException var6) {
         throw new UnmarshalException("Unable to get FUTURE_OBJECT_ID from request. ", var6);
      }
   }

   private void resetFutureObjectIDOnActivated(InboundRequest request, Activatable implementation) throws UnmarshalException {
      try {
         Object obj = request.getContext(25);
         if (obj != null) {
            ((FutureResultHandle)implementation).__WL_setFutureResultID((FutureResultID)null, kernelId);
         }
      } catch (IOException var4) {
      }

   }
}
