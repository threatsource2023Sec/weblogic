package weblogic.rmi.cluster;

import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import weblogic.ejb.spi.BaseEJBHomeIntf;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.internal.activation.ActivatableServerRef;

public final class ClusterActivatableServerRef extends ActivatableServerRef {
   private final String jndiName;

   public ClusterActivatableServerRef(Object obj, Activator activator) throws RemoteException {
      super(obj.getClass(), activator);
      EJBHome home = ((EJBObject)obj).getEJBHome();
      this.jndiName = ((BaseEJBHomeIntf)home).getJNDINameAsString();
   }

   public RemoteReference getActivatableRef(Object aid) {
      return new ClusterActivatableRemoteRef(this.getObjectID(), LocalServerIdentity.getIdentity(), aid, this.jndiName);
   }
}
