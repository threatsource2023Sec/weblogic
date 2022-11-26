package weblogic.application.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationLifecycleListener;

public final class ApplicationEventManager {
   private List factories = new ArrayList();
   private static ApplicationEventManager instance = new ApplicationEventManager();

   private ApplicationEventManager() {
   }

   public static ApplicationEventManager getInstance() {
      return instance;
   }

   public void registerListenerFactory(ApplicationLifecycleListenerFactory factory) {
      this.factories.add(factory);
   }

   public List createListeners(ApplicationContext appCtx) {
      List listeners = new ArrayList();
      Iterator var3 = this.factories.iterator();

      while(var3.hasNext()) {
         ApplicationLifecycleListenerFactory factory = (ApplicationLifecycleListenerFactory)var3.next();
         ApplicationLifecycleListener l = factory.createListener(appCtx);
         if (l != null) {
            listeners.add(l);
         }
      }

      return listeners;
   }
}
