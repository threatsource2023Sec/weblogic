package weblogic.server;

import org.jvnet.hk2.annotations.Contract;

@Contract
public abstract class ActivatedService {
   private ServiceActivator activator;

   protected abstract boolean startService() throws ServiceFailureException;

   protected abstract void stopService() throws ServiceFailureException;

   protected abstract void haltService() throws ServiceFailureException;

   protected void setActivator(ServiceActivator activator) {
      this.activator = activator;
   }

   public ServiceActivator getActivator() {
      return this.activator;
   }
}
