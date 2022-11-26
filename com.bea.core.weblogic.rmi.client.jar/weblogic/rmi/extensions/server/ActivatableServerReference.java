package weblogic.rmi.extensions.server;

import java.rmi.RemoteException;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.internal.ServerReference;

public interface ActivatableServerReference extends ServerReference {
   Object getImplementation(Object var1) throws RemoteException;

   StubReference getStubReference(Object var1) throws RemoteException;

   Activator getActivator();
}
