package weblogic.ejb.container.internal;

import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;

public class EJBBusinessActivator implements Activator {
   private StatefulEJBHomeImpl home;
   private Class businessImplClass;
   private Class iFace;

   public EJBBusinessActivator(StatefulEJBHomeImpl home, Class businessImplClass, Class iface) {
      this.home = home;
      this.businessImplClass = businessImplClass;
      this.iFace = iface;
   }

   public Activatable activate(Object pk) {
      return (Activatable)this.home.allocateBI(pk, this.businessImplClass, this.iFace, this);
   }

   public void deactivate(Activatable eo) {
   }
}
