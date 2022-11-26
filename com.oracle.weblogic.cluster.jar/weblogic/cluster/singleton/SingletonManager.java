package weblogic.cluster.singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import weblogic.work.StackableThreadContext;

public class SingletonManager {
   private static final boolean DEBUG = SingletonServicesDebugLogger.isDebugEnabled();
   private final HashMap nameToServiceInfoMap = new HashMap();

   public synchronized List getServices() {
      return new ArrayList(this.nameToServiceInfoMap.values());
   }

   public synchronized void iterate(SingletonIterator iter) {
      Iterator itr = this.nameToServiceInfoMap.values().iterator();

      while(itr.hasNext()) {
         SingletonServiceInfo info = (SingletonServiceInfo)itr.next();
         iter.traverse(info);
      }

   }

   Object[] getServiceNames() {
      return this.nameToServiceInfoMap.keySet().toArray();
   }

   public synchronized SingletonServiceInfo getServiceInfo(String name) {
      return (SingletonServiceInfo)this.nameToServiceInfoMap.get(name);
   }

   private SingletonServiceInfo doAddService(String name, SingletonService service, StackableThreadContext context, boolean internalService) {
      if (this.nameToServiceInfoMap.get(name) != null) {
         throw new IllegalArgumentException(name + " has been registered as a singleton already.");
      } else {
         if (DEBUG) {
            SingletonOperation.p("Registering singleton " + name + " on this server.");
         }

         SingletonServiceInfo info = new SingletonServiceInfo(name, service, context, internalService);
         this.nameToServiceInfoMap.put(name, info);
         return info;
      }
   }

   public synchronized SingletonServiceInfo addConfiguredService(String name, SingletonService service, StackableThreadContext context) {
      return this.doAddService(name, service, context, false);
   }

   public synchronized void addInternalService(String name, SingletonService service, StackableThreadContext context) {
      this.doAddService(name, service, context, true);
      if (SingletonServicesBatchManager.theOne() != null && SingletonServicesBatchManager.theOne().hasStarted()) {
         SingletonServicesBatchManager.theOne().startService(name);
      }

   }

   public synchronized void clear() {
      this.nameToServiceInfoMap.clear();
   }

   public synchronized SingletonServiceInfo remove(String name) {
      return (SingletonServiceInfo)this.nameToServiceInfoMap.remove(name);
   }

   public interface SingletonIterator {
      void traverse(SingletonServiceInfo var1);
   }
}
