package weblogic.rmi.cluster;

import java.rmi.RemoteException;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.ActivatableServerReference;
import weblogic.rmi.extensions.server.InvokableServerReference;
import weblogic.rmi.extensions.server.StubReference;

public class ClusterableActivatableServerRef extends ClusterableServerRef implements ActivatableServerReference {
   public ClusterableActivatableServerRef(ActivatableServerReference delegate) {
      super((InvokableServerReference)delegate);
   }

   public Object getImplementation(Object aid) throws RemoteException {
      return ((ActivatableServerReference)this.getDelegate()).getImplementation(aid);
   }

   public StubReference getStubReference(Object id) throws RemoteException {
      throw new UnsupportedOperationException("getStubReference()");
   }

   public Activator getActivator() {
      return ((ActivatableServerReference)this.getDelegate()).getActivator();
   }
}
