package weblogic.ejb20.portable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.HomeHandle;

public final class PortableHomeHandleImpl implements HomeHandle, Serializable {
   static final long serialVersionUID = -7735381439647377868L;
   private EJBHome ejbHome;
   private static final boolean DEBUG = false;

   public PortableHomeHandleImpl(EJBHome ejbHome) {
      this.ejbHome = ejbHome;
   }

   public EJBHome getEJBHome() throws RemoteException {
      return this.ejbHome;
   }

   private void writeObject(ObjectOutputStream oos) throws IOException {
      PortableHandleImpl.delegate().writeEJBHome(this.ejbHome, oos);
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      this.ejbHome = PortableHandleImpl.delegate().readEJBHome(ois);
   }

   private static final void p(String msg) {
      System.err.println("<PortableHomeHandleImpl>: " + msg);
   }
}
