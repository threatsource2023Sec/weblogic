package weblogic.osgi.internal;

import com.oracle.core.registryhelper.RegistryListener;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.launch.Framework;
import weblogic.osgi.OSGiLogger;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class OSGiWorkManagerRegistryListener implements RegistryListener {
   private final String NAME = "Name";
   private final Object lock = new Object();
   private final Framework framework;
   private final Map workManagers = new HashMap();
   private final String frameworkName;

   OSGiWorkManagerRegistryListener(Framework myFramework, String frameworkName) {
      this.framework = myFramework;
      this.frameworkName = frameworkName;
   }

   void initialize() {
      WorkManagerFactory.getInstance().addWorkManagerListener(this);
   }

   void destroy() {
      synchronized(this.lock) {
         Iterator var2 = this.workManagers.values().iterator();

         while(true) {
            if (!var2.hasNext()) {
               this.workManagers.clear();
               break;
            }

            ServiceRegistration handle = (ServiceRegistration)var2.next();
            handle.unregister();
         }
      }

      WorkManagerFactory.getInstance().removeWorkManagerListener(this);
   }

   private void addWorkManager(String name, WorkManager object) {
      if (object != null) {
         BundleContext bc = this.framework.getBundleContext();
         Dictionary props = new Hashtable();
         props.put("Name", name);
         String[] iFaces = Utilities.getAllInterfaces(object);
         if (iFaces.length <= 0) {
            iFaces = new String[]{object.getClass().getName()};
         }

         ServiceRegistration handle;
         try {
            handle = bc.registerService(iFaces, object, props);
         } catch (Throwable var10) {
            OSGiLogger.logCouldNotAdvertiseWorkManager(name, var10.getMessage(), this.frameworkName);
            return;
         }

         synchronized(this.lock) {
            this.workManagers.put(name, handle);
         }
      }
   }

   public void init(Collection initialElements) {
      Iterator var2 = initialElements.iterator();

      while(var2.hasNext()) {
         Map.Entry element = (Map.Entry)var2.next();
         String name = (String)element.getKey();
         WorkManager manager = (WorkManager)element.getValue();
         this.addWorkManager(name, manager);
      }

   }

   public void added(Map.Entry added) {
      this.addWorkManager((String)added.getKey(), (WorkManager)added.getValue());
   }

   public void removed(Map.Entry removed) {
      ServiceRegistration handle = null;
      synchronized(this.lock) {
         handle = (ServiceRegistration)this.workManagers.remove(removed.getKey());
      }

      if (handle != null) {
         handle.unregister();
      }

   }

   public void modified(Map.Entry oldValue, Map.Entry newValue) {
      this.removed(oldValue);
      this.added(newValue);
   }

   public String toString() {
      return "OSGiWorkManagerRegistryListener(" + System.identityHashCode(this) + "," + this.framework.getSymbolicName() + ")";
   }
}
