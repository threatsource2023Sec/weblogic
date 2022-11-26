package weblogic.iiop;

import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.rmi.CORBA.PortableRemoteObjectDelegate;
import weblogic.rmi.extensions.PortableRemoteObject;

public final class PortableRemoteObjectDelegateImpl implements PortableRemoteObjectDelegate {
   public void exportObject(Remote o) throws RemoteException {
      PortableRemoteObject.exportObject(o);
   }

   public Remote toStub(Remote o) throws NoSuchObjectException {
      return PortableRemoteObject.toStub(o);
   }

   public void unexportObject(Remote o) throws NoSuchObjectException {
      PortableRemoteObject.unexportObject(o);
   }

   public Object narrow(Object narrowFrom, Class narrowTo) {
      return PortableRemoteObject.narrow(narrowFrom, narrowTo);
   }

   public void connect(Remote target, Remote source) throws RemoteException {
   }
}
