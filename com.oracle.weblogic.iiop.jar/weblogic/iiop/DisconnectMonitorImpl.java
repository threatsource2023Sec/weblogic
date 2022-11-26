package weblogic.iiop;

import java.io.IOException;
import java.rmi.Remote;
import org.omg.CORBA.portable.ObjectImpl;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.Kernel;
import weblogic.rmi.cluster.ClusterableRemoteRef;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.server.DisconnectMonitorProvider;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.internal.StubInfoIntf;

public class DisconnectMonitorImpl implements DisconnectMonitorProvider {
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");

   public boolean addDisconnectListener(Remote stub, DisconnectListener listener) {
      IIOPRemoteRef ref = this.getRefFromStub(stub);
      if (ref != null) {
         if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
            p("registering: " + stub + ", hashcode: " + stub.hashCode());
         }

         try {
            try {
               if (((ObjectImpl)stub)._non_existent()) {
                  throw new IllegalArgumentException("The remote peer is alive but does not have the object: " + stub);
               }
            } catch (Throwable var6) {
               if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
                  var6.printStackTrace();
                  EndPoint e = EndPointManager.findEndPoint(ref.getIOR());
                  if (e != null) {
                     p("There is still an EndPoint: " + e + ", it should be null!");
                  }
               }
            }

            EndPoint e = EndPointManager.findOrCreateEndPoint(ref.getIOR());
            if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
               p("Registering on EndPoint: " + e);
            }

            e.addDisconnectListener(stub, listener);
            return true;
         } catch (IOException var7) {
         }
      }

      return false;
   }

   public boolean removeDisconnectListener(Remote stub, DisconnectListener listener) {
      IIOPRemoteRef ref = this.getRefFromStub(stub);
      if (ref != null) {
         EndPoint e = EndPointManager.findEndPoint(ref.getIOR());
         if (e != null) {
            e.removeDisconnectListener(stub, listener);
         }

         return true;
      } else {
         return false;
      }
   }

   private IIOPRemoteRef getRefFromStub(Object o) {
      RemoteReference ref = null;
      if (o instanceof StubInfoIntf) {
         ref = ((StubInfoIntf)o).getStubInfo().getRemoteRef();
      } else if (o instanceof StubReference) {
         ref = ((StubReference)o).getRemoteRef();
      }

      if (ref instanceof ClusterableRemoteRef) {
         ClusterableRemoteRef rar = (ClusterableRemoteRef)ref;
         ref = rar.getCurrentReplica();
      }

      return ref instanceof IIOPRemoteRef ? (IIOPRemoteRef)ref : null;
   }

   private static void p(String s) {
      System.out.println("<HeartbeatMonitorDelegate>: " + s);
   }
}
