package weblogic.rmi.extensions;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.MarshalException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.UnmarshalException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.SystemException;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.QueueThrottleException;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;
import weblogic.rjvm.PartitionNotFoundException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RemoteWrapper;
import weblogic.rmi.extensions.server.SmartStub;
import weblogic.rmi.internal.BasicRemoteRef;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.RMIRuntime;
import weblogic.utils.NestedThrowable;
import weblogic.work.WorkRejectedException;

public class RemoteHelper {
   private static AuditableThreadLocal timeoutInfo = null;

   protected RemoteHelper() {
   }

   public static final HostID getHostID(Object remoteObject) throws IllegalArgumentException {
      if (remoteObject == null) {
         return null;
      } else if (remoteObject instanceof RemoteReference) {
         return ((RemoteReference)remoteObject).getHostID();
      } else {
         Remote remote = getRemote(remoteObject);
         if (remote == null) {
            throw new IllegalArgumentException(remoteObject.getClass().getName() + " does not implement Remote, RemoteWrapper or SmartStub");
         } else {
            return (HostID)(remote instanceof StubInfoIntf ? ((StubInfoIntf)remote).getStubInfo().getRemoteRef().getHostID() : LocalServerIdentity.getIdentity());
         }
      }
   }

   public static final EndPoint getEndPoint(Object remoteObject) throws IllegalArgumentException {
      HostID hid = getHostID(remoteObject);
      EndPoint endPoint = RMIRuntime.findEndPoint(hid);
      if (endPoint != null) {
         return endPoint;
      } else {
         throw new IllegalArgumentException("Could not find EndPoint for " + remoteObject.getClass().getName());
      }
   }

   private static boolean isDead(HostID hostID) {
      EndPoint endPoint = RMIRuntime.findOrCreateEndPoint(hostID);
      return endPoint == null ? false : endPoint.isDead();
   }

   public static final boolean isHostDead(Object remoteObject) {
      if (remoteObject instanceof RemoteReference) {
         return isDead(((RemoteReference)remoteObject).getHostID());
      } else {
         Remote remote = getRemote(remoteObject);
         if (remote instanceof StubInfoIntf) {
            StubInfo info = ((StubInfoIntf)remote).getStubInfo();
            return isDead(info.getRemoteRef().getHostID());
         } else {
            return false;
         }
      }
   }

   public static boolean isRecoverableFailure(RemoteException e) {
      if (e instanceof UnmarshalException) {
         return isRecoverableUnmarshalException(e.getCause());
      } else {
         return e instanceof RemoteSystemException ? isRecoverableFailure(((RemoteSystemException)e).getNested()) : isRecoverablePreInvokeFailure(e);
      }
   }

   private static boolean isRecoverableUnmarshalException(Throwable nested) {
      if (nested instanceof ClassNotFoundException) {
         return false;
      } else if (nested instanceof EOFException) {
         return true;
      } else if (nested instanceof SocketException) {
         return true;
      } else {
         return !(nested instanceof IOException);
      }
   }

   public static final boolean isRecoverablePreInvokeFailure(RemoteException e) {
      if (!(e instanceof UnknownHostException) && !(e instanceof ConnectException) && !(e instanceof ConnectIOException) && !(e instanceof NoSuchObjectException) && !(e instanceof QueueThrottleException) && !(e instanceof WorkRejectedException) && !(e instanceof PartitionNotFoundException)) {
         if (e instanceof MarshalException) {
            return false;
         } else if (e instanceof RemoteSystemException) {
            return isRecoverableFailure(((RemoteSystemException)e).getNested());
         } else {
            Throwable t = null;
            if (e.getCause() != null) {
               t = e.getCause();
            } else if (e instanceof NestedThrowable) {
               t = ((NestedThrowable)e).getNested();
            }

            return t instanceof ConnectException;
         }
      } else {
         return true;
      }
   }

   public static final RemoteException returnOrUnwrap(RemoteException re) throws RemoteException {
      if (re instanceof RemoteSystemException) {
         throw ((RemoteSystemException)re).getNested();
      } else {
         return re;
      }
   }

   public static final boolean isRecoverableFailure(SystemException e) {
      return e instanceof MARSHAL && e.completed.value() == 2 ? true : isRecoverablePreInvokeFailure(e);
   }

   public static final boolean isRecoverablePreInvokeFailure(SystemException e) {
      return e instanceof COMM_FAILURE || e instanceof OBJECT_NOT_EXIST || e instanceof BAD_PARAM && e.minor == 1330446344 || e instanceof MARSHAL && e.completed.value() == 1;
   }

   public static final boolean isCollocated(Object o) throws IllegalArgumentException {
      return getHostID(o).isLocal();
   }

   public static final Remote getRemote(Object remoteObject) {
      if (remoteObject instanceof Remote) {
         return (Remote)remoteObject;
      } else if (remoteObject instanceof RemoteWrapper) {
         return ((RemoteWrapper)remoteObject).getRemoteDelegate();
      } else {
         return remoteObject instanceof SmartStub ? (Remote)((SmartStub)remoteObject).getStubDelegate() : null;
      }
   }

   public static final String getDomainName(RemoteReference rr) {
      if (rr != null) {
         HostID hid = getHostID(rr);
         if (hid instanceof ServerIdentity) {
            return ((ServerIdentity)hid).getDomainName();
         }
      }

      return null;
   }

   public static final String getServerName(Object rr) {
      if (rr != null) {
         HostID hid = getHostID(rr);
         if (hid instanceof ServerIdentity) {
            return ((ServerIdentity)hid).getServerName();
         }
      }

      return null;
   }

   public static final EndPoint getCurrentEndPoint(RemoteReference rr) {
      EndPoint endPoint = null;
      if (rr instanceof BasicRemoteRef) {
         endPoint = ((BasicRemoteRef)rr).getCurrentEndPoint();
      }

      return endPoint;
   }

   public static final PeerInfo getCurrentPeerInfo(RemoteReference rr) {
      PeerInfo peerInfo = null;
      if (rr instanceof BasicRemoteRef) {
         peerInfo = ((BasicRemoteRef)rr).getCurrentPeerInfo();
      }

      return peerInfo;
   }

   public static final PeerInfo getPeerInfo(RemoteReference rr) {
      PeerInfo peerInfo = null;
      if (rr instanceof BasicRemoteRef) {
         HostID hostID = rr.getHostID();
         EndPoint endPoint = RMIRuntime.findOrCreateEndPoint(hostID);
         if (endPoint != null && endPoint instanceof PeerInfoable) {
            peerInfo = ((PeerInfoable)endPoint).getPeerInfo();
         }
      }

      return peerInfo;
   }

   public static final boolean isColocatedInApplication(Object o) throws IllegalArgumentException {
      if (!isCollocated(o)) {
         return false;
      } else {
         Remote remote = getRemote(o);
         if (remote == null) {
            throw new IllegalArgumentException(o.getClass().getName() + " does not implement Remote, RemoteWrapper or SmartStub");
         } else {
            return !(remote instanceof StubInfoIntf);
         }
      }
   }
}
