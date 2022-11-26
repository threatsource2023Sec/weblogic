package org.jboss.weld.bean.builtin;

import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.event.CurrentEventMetadata;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.ImmutableSet;

public class EventMetadataBean extends AbstractStaticallyDecorableBuiltInBean {
   private static final Set TYPES = ImmutableSet.of(EventMetadata.class, Object.class);
   private final CurrentEventMetadata currentEventMetadata;

   public EventMetadataBean(BeanManagerImpl beanManager) {
      super(beanManager, EventMetadata.class);
      this.currentEventMetadata = (CurrentEventMetadata)beanManager.getServices().get(CurrentEventMetadata.class);
   }

   protected EventMetadata newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      return (EventMetadata)this.currentEventMetadata.peek();
   }

   public Set getTypes() {
      return TYPES;
   }

   public String toString() {
      return "Implicit Bean [" + EventMetadata.class.getName() + "] with qualifiers [@Default]";
   }
}
