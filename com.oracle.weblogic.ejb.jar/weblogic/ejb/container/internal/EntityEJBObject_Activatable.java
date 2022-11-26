package weblogic.ejb.container.internal;

import java.rmi.Remote;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;

public abstract class EntityEJBObject_Activatable extends EntityEJBObject implements Activatable, Remote {
   private Activator ejbActivator;

   public Activator getActivator() {
      return this.ejbActivator;
   }

   public void setActivator(Activator ejbActivator) {
      this.ejbActivator = ejbActivator;
   }
}
