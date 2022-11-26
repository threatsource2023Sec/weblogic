package org.jboss.weld.bean.builtin;

import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.TypeLiteral;
import org.jboss.weld.event.EventImpl;
import org.jboss.weld.events.WeldEvent;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class EventBean extends AbstractFacadeBean {
   private static final Type DEFAULT_TYPE = (new TypeLiteral() {
   }).getType();
   private static final Set TYPES = ImmutableSet.of(Event.class, Object.class, WeldEvent.class);

   public EventBean(BeanManagerImpl manager) {
      super(manager, (Class)Reflections.cast(Event.class));
   }

   public Class getBeanClass() {
      return EventImpl.class;
   }

   protected Event newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      return EventImpl.of(ip, this.getBeanManager());
   }

   public String toString() {
      return "Implicit Bean [javax.enterprise.event.Event] with qualifiers [@Default]";
   }

   protected Type getDefaultType() {
      return DEFAULT_TYPE;
   }

   public Set getTypes() {
      return TYPES;
   }
}
