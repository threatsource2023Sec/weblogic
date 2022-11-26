package org.jboss.weld.bootstrap.api.helpers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.jboss.weld.bootstrap.api.BootstrapService;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;

public class SimpleServiceRegistry implements ServiceRegistry {
   private final Map services = new HashMap();
   private final Set bootstrapServices = new HashSet();

   public void add(Class type, Service service) {
      this.services.put(type, service);
      if (service instanceof BootstrapService) {
         this.bootstrapServices.add((BootstrapService)service);
      }

   }

   public void addAll(Collection services) {
      Iterator var2 = services.iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.add((Class)entry.getKey(), (Service)entry.getValue());
      }

   }

   public Set entrySet() {
      return this.services.entrySet();
   }

   protected Map get() {
      return this.services;
   }

   public Service get(Class type) {
      return (Service)this.services.get(type);
   }

   public Optional getOptional(Class type) {
      return Optional.ofNullable(this.get(type));
   }

   public boolean contains(Class type) {
      return this.services.containsKey(type);
   }

   public void cleanup() {
      Iterator var1 = this.services.values().iterator();

      while(var1.hasNext()) {
         Service service = (Service)var1.next();
         service.cleanup();
      }

   }

   public void cleanupAfterBoot() {
      Iterator var1 = this.bootstrapServices.iterator();

      while(var1.hasNext()) {
         BootstrapService service = (BootstrapService)var1.next();
         service.cleanupAfterBoot();
      }

   }

   public String toString() {
      return this.services.toString();
   }

   public int hashCode() {
      return this.services.hashCode();
   }

   public boolean equals(Object obj) {
      return obj instanceof Map ? this.services.equals(obj) : false;
   }

   public Iterator iterator() {
      return new ValueIterator() {
         protected Iterator delegate() {
            return SimpleServiceRegistry.this.services.entrySet().iterator();
         }
      };
   }

   public Service getRequired(Class type) {
      Service result = this.get(type);
      if (result == null) {
         throw new IllegalStateException("Required service " + type.getName() + " not available.");
      } else {
         return result;
      }
   }

   public Service addIfAbsent(Class type, Service service) {
      Service previous = (Service)this.services.putIfAbsent(type, service);
      if (previous == null && service instanceof BootstrapService) {
         this.bootstrapServices.add((BootstrapService)service);
      }

      return previous;
   }

   private abstract static class ValueIterator implements Iterator {
      private ValueIterator() {
      }

      protected abstract Iterator delegate();

      public boolean hasNext() {
         return this.delegate().hasNext();
      }

      public Object next() {
         return ((Map.Entry)this.delegate().next()).getValue();
      }

      public void remove() {
         this.delegate().remove();
      }

      // $FF: synthetic method
      ValueIterator(Object x0) {
         this();
      }
   }
}
