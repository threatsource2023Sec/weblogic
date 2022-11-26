package org.jboss.weld.bootstrap.api.helpers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;

public abstract class ForwardingServiceRegistry implements ServiceRegistry {
   protected abstract ServiceRegistry delegate();

   public void add(Class type, Service service) {
      this.delegate().add(type, service);
   }

   public boolean contains(Class type) {
      return this.delegate().contains(type);
   }

   public Service get(Class type) {
      return this.delegate().get(type);
   }

   public Optional getOptional(Class type) {
      return this.delegate().getOptional(type);
   }

   public Iterator iterator() {
      return this.delegate().iterator();
   }

   public void addAll(Collection services) {
      this.delegate().addAll(services);
   }

   public Set entrySet() {
      return this.delegate().entrySet();
   }

   public void cleanup() {
      this.delegate().cleanup();
   }

   public void cleanupAfterBoot() {
      this.delegate().cleanupAfterBoot();
   }

   public Service getRequired(Class type) {
      return this.delegate().getRequired(type);
   }

   public Service addIfAbsent(Class type, Service service) {
      return this.delegate().addIfAbsent(type, service);
   }
}
