package weblogic.ejb20.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.Remote;
import javax.rmi.CORBA.Stub;
import weblogic.iiop.IIOPReplacer;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.internal.StubInfoIntf;

public final class HandleDelegateImpl extends weblogic.ejb20.portable.HandleDelegateImpl {
   protected void writeStub(Remote stub, ObjectOutputStream os) throws IOException {
      if (stub instanceof StubInfoIntf && !(stub instanceof Stub)) {
         Object obj = ((StubInfoIntf)stub).getStubInfo();
         os.writeObject(obj);
      } else if (!(stub instanceof weblogic.corba.rmi.Stub) && stub instanceof Stub) {
         super.writeStub(stub, os);
      } else {
         Object obj = IIOPReplacer.getIIOPReplacer().replaceObject(stub);
         os.writeObject(obj);
      }

   }

   protected Object readStub(ObjectInputStream is, Class c) throws IOException, ClassNotFoundException {
      Object readStub = is.readObject();
      Object resolved = IIOPReplacer.getIIOPReplacer().resolveObject(readStub);
      return PortableRemoteObject.narrow(resolved, c);
   }
}
