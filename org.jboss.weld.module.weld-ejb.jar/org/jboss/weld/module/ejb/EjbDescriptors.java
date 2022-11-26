package org.jboss.weld.module.ejb;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.util.collections.SetMultimap;
import org.jboss.weld.util.reflection.Reflections;

class EjbDescriptors {
   private final Map ejbByName = new HashMap();
   private final SetMultimap ejbByClass = SetMultimap.newSetMultimap();

   EjbDescriptors(Collection descriptors) {
      this.addAll(descriptors);
   }

   public InternalEjbDescriptor get(String beanName) {
      return (InternalEjbDescriptor)Reflections.cast(this.ejbByName.get(beanName));
   }

   private void add(EjbDescriptor ejbDescriptor) {
      InternalEjbDescriptor internalEjbDescriptor = InternalEjbDescriptor.of(ejbDescriptor);
      this.ejbByName.put(ejbDescriptor.getEjbName(), internalEjbDescriptor);
      this.ejbByClass.put(ejbDescriptor.getBeanClass(), internalEjbDescriptor.getEjbName());
   }

   public boolean contains(String beanName) {
      return this.ejbByName.containsKey(beanName);
   }

   public boolean contains(Class beanClass) {
      return this.ejbByClass.containsKey(beanClass);
   }

   public InternalEjbDescriptor getUnique(Class beanClass) {
      Set ejbs = (Set)this.ejbByClass.get(beanClass);
      if (ejbs.size() > 1) {
         throw BeanLogger.LOG.tooManyEjbsForClass(beanClass, ejbs);
      } else {
         return ejbs.size() == 0 ? null : this.get((String)ejbs.iterator().next());
      }
   }

   private void addAll(Iterable ejbDescriptors) {
      Iterator var2 = ejbDescriptors.iterator();

      while(var2.hasNext()) {
         EjbDescriptor ejbDescriptor = (EjbDescriptor)var2.next();
         this.add(ejbDescriptor);
      }

   }

   public Iterator iterator() {
      return this.ejbByName.values().iterator();
   }

   public Collection getAll() {
      return this.ejbByName.values();
   }

   public void cleanup() {
      this.ejbByClass.clear();
      this.ejbByName.clear();
   }
}
