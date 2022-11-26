package weblogic.ejb.container.internal;

import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;

public class EJBActivator implements Activator {
   private BaseEJBHome homeClass;

   public EJBActivator(BaseEJBHome homeClass) {
      this.homeClass = homeClass;
   }

   public Activatable activate(Object pk) {
      return (Activatable)this.homeClass.allocateEO(pk);
   }

   public void deactivate(Activatable eo) {
   }
}
