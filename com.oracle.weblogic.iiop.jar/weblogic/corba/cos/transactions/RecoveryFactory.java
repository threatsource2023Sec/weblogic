package weblogic.corba.cos.transactions;

import java.util.HashMap;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;

public class RecoveryFactory implements Activator {
   private static final HashMap map = new HashMap();
   private static final Activator activator = new RecoveryFactory();

   public static final Activator getActivator() {
      return activator;
   }

   private RecoveryFactory() {
   }

   public synchronized Activatable activate(Object id) {
      if (map.get(id) == null) {
         map.put(id, new RecoveryCoordinatorImpl(id));
      }

      return (Activatable)map.get(id);
   }

   public synchronized void deactivate(Activatable activatable) {
      map.remove(activatable.getActivationID());
   }
}
