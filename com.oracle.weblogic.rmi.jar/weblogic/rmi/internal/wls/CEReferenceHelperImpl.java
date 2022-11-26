package weblogic.rmi.internal.wls;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import weblogic.rmi.extensions.server.ReferenceHelper;
import weblogic.rmi.extensions.server.ServerHelper;

public class CEReferenceHelperImpl extends ReferenceHelper {
   public void exportObject(Remote r) throws RemoteException {
      ServerHelper.exportObject(r);
   }

   public Object narrow(Object o, Class c) throws NamingException {
      return o;
   }

   public Object replaceObject(Object o) throws IOException {
      return o;
   }

   public Remote toStub(Remote obj) throws NoSuchObjectException {
      return obj;
   }

   public void unexportObject(Remote r) throws NoSuchObjectException {
      ServerHelper.unexportObject(r, true, false);
   }
}
