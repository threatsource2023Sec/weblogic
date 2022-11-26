package weblogic.corba.cos.transactions;

import java.util.HashMap;
import weblogic.iiop.IIOPLogger;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;

public class ResourceFactory implements Activator {
   private static final HashMap map = new HashMap();
   private static final Activator activator = new ResourceFactory();

   public static final Activator getActivator() {
      return activator;
   }

   private ResourceFactory() {
   }

   public synchronized Activatable activate(Object id) {
      if (map.get(id) == null) {
         ResourceImpl res = ResourceImpl.getResource(((ResourceImpl.ResourceActivationID)id).getXid());
         if (OTSHelper.isDebugEnabled()) {
            IIOPLogger.logDebugOTS("activating new resource " + res);
         }

         return res;
      } else {
         return (Activatable)map.get(id);
      }
   }

   synchronized void activateResource(ResourceImpl res) {
      if (OTSHelper.isDebugEnabled()) {
         IIOPLogger.logDebugOTS("activating resource " + res);
      }

      map.put(res.getActivationID(), res);
   }

   public synchronized void deactivate(Activatable activatable) {
   }

   public synchronized void release(Activatable activatable) {
      if (OTSHelper.isDebugEnabled()) {
         IIOPLogger.logDebugOTS("released resource " + activatable);
      }

      map.remove(activatable.getActivationID());
   }
}
