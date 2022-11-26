package weblogic.management.provider.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InstanceLifecycleEvent;
import org.glassfish.hk2.api.InstanceLifecycleEventType;
import org.glassfish.hk2.api.InstanceLifecycleListener;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.ServerService;

@Singleton
@Service
public class ServerServiceInstanceRoster implements InstanceLifecycleListener {
   private final AtomicInteger index = new AtomicInteger(0);
   private final Map creationOrder = new ConcurrentHashMap();

   public Filter getFilter() {
      return null;
   }

   public void lifecycleEvent(InstanceLifecycleEvent lifecycleEvent) {
      Object obj = lifecycleEvent.getLifecycleObject();
      if (lifecycleEvent.getEventType() == InstanceLifecycleEventType.POST_PRODUCTION && this.isObjectServerService(obj)) {
         int serviceIndex = this.index.getAndIncrement();
         this.creationOrder.put(obj.getClass().getName(), new Integer(serviceIndex));
      }
   }

   public Integer lookup(String serverServiceClassName) {
      return (Integer)this.creationOrder.get(serverServiceClassName);
   }

   public Map getCreationOrder() {
      return this.creationOrder;
   }

   private boolean isObjectServerService(Object obj) {
      return obj instanceof ServerService;
   }
}
