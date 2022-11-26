package weblogic.ejb20.portable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.Remote;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.spi.HandleDelegate;
import weblogic.rmi.extensions.PortableRemoteObject;

public class HandleDelegateImpl implements HandleDelegate {
   public void writeEJBObject(EJBObject ejbObject, ObjectOutputStream os) throws IOException {
      this.writeStub(ejbObject, os);
   }

   public EJBObject readEJBObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
      return (EJBObject)this.readStub(is, EJBObject.class);
   }

   public void writeEJBHome(EJBHome ejbHome, ObjectOutputStream os) throws IOException {
      this.writeStub(ejbHome, os);
   }

   public EJBHome readEJBHome(ObjectInputStream is) throws IOException, ClassNotFoundException {
      return (EJBHome)this.readStub(is, EJBHome.class);
   }

   protected void writeStub(Remote stub, ObjectOutputStream os) throws IOException {
      os.writeObject(stub);
   }

   protected Object readStub(ObjectInputStream is, Class c) throws IOException, ClassNotFoundException {
      return PortableRemoteObject.narrow(is.readObject(), c);
   }
}
