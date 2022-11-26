package weblogic.rmi.server;

import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.ServerReference;

public class RemoteObject implements Remote {
   protected RemoteObject() {
   }

   public static Remote toStub(Remote obj) throws NoSuchObjectException {
      try {
         ServerReference s = OIDManager.getInstance().getServerReference(obj);
         if (s == null) {
            throw new NoSuchObjectException("Not exported " + obj);
         } else {
            return (RemoteObject)StubFactory.getStub(s.getStubReference());
         }
      } catch (NoSuchObjectException var2) {
         throw var2;
      } catch (RemoteException var3) {
         throw new NoSuchObjectException("Could not replaceObject " + obj + " because: " + var3);
      }
   }
}
