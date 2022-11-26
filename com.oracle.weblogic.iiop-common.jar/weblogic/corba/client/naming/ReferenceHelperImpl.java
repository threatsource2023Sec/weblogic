package weblogic.corba.client.naming;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import weblogic.corba.j2ee.naming.NamingContextHolder;
import weblogic.rmi.extensions.server.ReferenceHelper;

public class ReferenceHelperImpl extends ReferenceHelper {
   private static final boolean DEBUG = false;

   public Object narrow(Object o, Class c) throws NamingException {
      return c.isInstance(o) ? o : PortableRemoteObject.narrow(o, c);
   }

   public Object replaceObject(Object o) throws IOException {
      Object obj = o;
      if (o instanceof NamingContextHolder && ((NamingContextHolder)o).getContext() != null) {
         obj = ((NamingContextHolder)o).getContext();
      } else if (!(o instanceof org.omg.CORBA.Object) && o instanceof Remote) {
         obj = PortableRemoteObject.toStub((Remote)o);
      }

      return obj;
   }

   public void exportObject(Remote r) throws RemoteException {
      PortableRemoteObject.exportObject(r);
   }

   public void unexportObject(Remote r) throws NoSuchObjectException {
      PortableRemoteObject.unexportObject(r);
   }

   public Remote toStub(Remote obj) throws NoSuchObjectException {
      return PortableRemoteObject.toStub(obj);
   }

   static void p(String s) {
      System.err.println("<ReferenceHelperImpl> " + s);
   }
}
