package weblogic.corba.cos.transactions;

import java.util.HashMap;
import javax.transaction.xa.Xid;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;

public class CoordinatorImplFactory implements Activator {
   private static final HashMap map = new HashMap();
   private static final Activator activator = new CoordinatorImplFactory();

   public static final Activator getActivator() {
      return activator;
   }

   private CoordinatorImplFactory() {
   }

   public synchronized Activatable activate(Object id) {
      if (map.get(id) == null) {
         map.put(id, new CoordinatorImpl((Xid)id));
      }

      return (Activatable)map.get(id);
   }

   public synchronized void deactivate(Activatable activatable) {
      map.remove(activatable.getActivationID());
   }
}
