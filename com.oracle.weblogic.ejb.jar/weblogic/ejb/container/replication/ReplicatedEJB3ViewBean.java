package weblogic.ejb.container.replication;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import weblogic.cluster.replication.ApplicationUnavailableException;
import weblogic.cluster.replication.ROID;
import weblogic.rmi.extensions.server.ServerHelper;

public class ReplicatedEJB3ViewBean extends ReplicatedBean {
   private String ifaceClassName;

   public ReplicatedEJB3ViewBean() {
   }

   public ReplicatedEJB3ViewBean(String jndiName, Class iface) {
      super(jndiName);
      this.ifaceClassName = iface.getName();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.jndiName);
      out.writeObject(this.ifaceClassName);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.jndiName = (String)in.readObject();
      this.ifaceClassName = (String)in.readObject();
   }

   public Object becomeSecondary(ROID key) throws ApplicationUnavailableException {
      try {
         ReplicatedHome replicatedHome = this.getHome();
         if (!ServerHelper.isLocal(replicatedHome)) {
            this.logClustersNotHomogeneous(this.jndiName);
            throw new ApplicationUnavailableException(this.jndiName + " cannot be found at local instance.");
         } else {
            return replicatedHome.createSecondaryForBI(key, this.ifaceClassName);
         }
      } catch (RemoteException var3) {
         this.logClustersNotHomogeneous(this.jndiName);
         throw new ApplicationUnavailableException(var3.toString());
      } catch (EJBException var4) {
         throw new ApplicationUnavailableException(var4.toString());
      }
   }
}
