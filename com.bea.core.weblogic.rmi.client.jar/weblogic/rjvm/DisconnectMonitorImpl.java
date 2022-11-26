package weblogic.rjvm;

import java.rmi.Remote;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.server.DisconnectMonitorProvider;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RemoteWrapper;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.RMIRuntime;
import weblogic.utils.Debug;

public final class DisconnectMonitorImpl implements DisconnectMonitorProvider {
   private static final DebugLogger debugConnection = DebugLogger.getDebugLogger("DebugConnection");

   public boolean addDisconnectListener(Remote stub, DisconnectListener listener) {
      debug("addDisconnectListener(" + stub + ')');
      HostID hostID = getHostIDFromStub(stub);
      if (hostID == null) {
         return false;
      } else {
         RMIRuntime.getRMIRuntime();
         EndPoint e = RMIRuntime.findEndPoint(hostID);
         if (e == null || e.isDead()) {
            debug("Creating EndPoint for: " + stub + ", " + hostID);
            Debug.assertion(((JVMID)hostID).isServer());
            RMIRuntime.getRMIRuntime();
            e = RMIRuntime.findOrCreateEndPoint(hostID);
            if (e == null) {
               debug("Failed Creating EndPoint for: " + stub + ", " + hostID);
            }
         }

         if (e != null) {
            e.addDisconnectListener(stub, listener);
         }

         return true;
      }
   }

   public boolean removeDisconnectListener(Remote stub, DisconnectListener listener) {
      HostID hostID = getHostIDFromStub(stub);
      if (hostID != null) {
         RMIRuntime.getRMIRuntime();
         EndPoint e = RMIRuntime.findEndPoint(hostID);
         if (e != null) {
            e.removeDisconnectListener(stub, listener);
         }

         return true;
      } else {
         return false;
      }
   }

   private static HostID getHostIDFromStub(Remote o) {
      if (o == null) {
         return null;
      } else {
         if (o instanceof RemoteWrapper) {
            o = ((RemoteWrapper)o).getRemoteDelegate();
         }

         if (o instanceof StubInfoIntf) {
            RemoteReference ref = ((StubInfoIntf)o).getStubInfo().getRemoteRef();
            HostID hostID = ref.getHostID();
            if (hostID instanceof JVMID) {
               return hostID;
            }
         }

         return null;
      }
   }

   private static void debug(String msg) {
      if (debugConnection.isDebugEnabled()) {
         debugConnection.debug("<DisconnectMonitorImpl>: " + msg);
      }

   }
}
