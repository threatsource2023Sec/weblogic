package weblogic.ejb20.portable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.spi.HandleDelegate;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public final class PortableHandleImpl implements Handle, Serializable {
   static final long serialVersionUID = -7104018438195312776L;
   private EJBObject ejbObject;
   private static final boolean DEBUG = false;

   public PortableHandleImpl(EJBObject ejbObject) {
      this.ejbObject = ejbObject;
   }

   public EJBObject getEJBObject() throws RemoteException {
      return this.ejbObject;
   }

   private void writeObject(ObjectOutputStream oos) throws IOException {
      delegate().writeEJBObject(this.ejbObject, oos);
   }

   static HandleDelegate delegate() throws IOException {
      HandleDelegate delegate = null;

      try {
         delegate = (HandleDelegate)(new InitialContext()).lookup("java:comp/HandleDelegate");
      } catch (NamingException var2) {
         delegate = new HandleDelegateImpl();
      }

      return (HandleDelegate)delegate;
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      this.ejbObject = delegate().readEJBObject(ois);
   }

   private static final void p(String msg) {
      System.err.println("<PortableHandleImpl>: " + msg);
   }
}
