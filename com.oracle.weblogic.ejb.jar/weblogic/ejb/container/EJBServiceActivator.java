package weblogic.ejb.container;

import weblogic.server.ServiceActivator;

public abstract class EJBServiceActivator extends ServiceActivator {
   protected EJBServiceActivator(String serviceClass) {
      super(serviceClass);
   }

   public String getVersion() {
      return "EJB 3.2";
   }
}
