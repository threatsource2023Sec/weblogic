package org.jboss.weld.events;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.enterprise.event.Event;
import javax.enterprise.util.TypeLiteral;

public interface WeldEvent extends Event {
   WeldEvent select(Type var1, Annotation... var2);

   WeldEvent select(Annotation... var1);

   WeldEvent select(Class var1, Annotation... var2);

   WeldEvent select(TypeLiteral var1, Annotation... var2);
}
