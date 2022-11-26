package org.jboss.weld.util;

import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.Iterator;
import javax.enterprise.util.TypeLiteral;
import org.jboss.weld.inject.WeldInstance;

public abstract class ForwardingWeldInstance implements WeldInstance {
   public abstract WeldInstance delegate();

   public Iterator iterator() {
      return this.delegate().iterator();
   }

   public Object get() {
      return this.delegate().get();
   }

   public WeldInstance select(Annotation... qualifiers) {
      return this.delegate().select(qualifiers);
   }

   public WeldInstance select(Class subtype, Annotation... qualifiers) {
      return this.delegate().select(subtype, qualifiers);
   }

   public WeldInstance select(TypeLiteral subtype, Annotation... qualifiers) {
      return this.delegate().select(subtype, qualifiers);
   }

   public boolean isUnsatisfied() {
      return this.delegate().isUnsatisfied();
   }

   public boolean isAmbiguous() {
      return this.delegate().isAmbiguous();
   }

   public void destroy(Object instance) {
      this.delegate().destroy(instance);
   }

   public WeldInstance.Handler getHandler() {
      return this.delegate().getHandler();
   }

   public Iterable handlers() {
      return this.delegate().handlers();
   }

   public Comparator getPriorityComparator() {
      return this.delegate().getPriorityComparator();
   }
}
