package org.jboss.weld.contexts.beanstore;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.reflection.Reflections;

public abstract class AbstractMapBackedBeanStore implements BeanStore {
   protected abstract Map delegate();

   public ContextualInstance get(BeanIdentifier id) {
      return (ContextualInstance)Reflections.cast(this.delegate().get(id));
   }

   public void clear() {
      this.delegate().clear();
   }

   public boolean contains(BeanIdentifier id) {
      return this.delegate().containsKey(id);
   }

   public ContextualInstance remove(BeanIdentifier id) {
      return (ContextualInstance)Reflections.cast(this.delegate().remove(id));
   }

   public boolean equals(Object obj) {
      if (obj instanceof AbstractMapBackedBeanStore) {
         AbstractMapBackedBeanStore that = (AbstractMapBackedBeanStore)obj;
         return this.delegate().equals(that.delegate());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public Set getContextualIds() {
      return this.delegate().keySet();
   }

   public void put(BeanIdentifier id, ContextualInstance beanInstance) {
      this.delegate().put(id, beanInstance);
   }

   public String toString() {
      return "holding " + this.delegate().size() + " instances";
   }

   public Iterator iterator() {
      return this.delegate().keySet().iterator();
   }
}
