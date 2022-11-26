package org.jboss.weld.resolution;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;

public class NameBasedResolver {
   private ComputingCache resolvedNames;

   public NameBasedResolver(BeanManagerImpl manager, Iterable allBeans) {
      this.resolvedNames = ComputingCacheBuilder.newBuilder().build(new NameToBeanSet(manager, allBeans));
   }

   public void clear() {
      this.resolvedNames.clear();
   }

   public Set resolve(String name) {
      return (Set)this.resolvedNames.getValue(name);
   }

   public String toString() {
      StringBuilder buffer = new StringBuilder();
      buffer.append("Resolver\n");
      buffer.append("Resolved names points: ").append(this.resolvedNames.size()).append('\n');
      return buffer.toString();
   }

   private static class NameToBeanSet implements Function {
      private final BeanManagerImpl beanManager;
      private final Iterable allBeans;

      private NameToBeanSet(BeanManagerImpl beanManager, Iterable allBeans) {
         this.beanManager = beanManager;
         this.allBeans = allBeans;
      }

      public Set apply(String from) {
         Set matchedBeans = new HashSet();
         Iterator var3 = this.allBeans.iterator();

         while(true) {
            Bean bean;
            do {
               if (!var3.hasNext()) {
                  return Beans.removeDisabledBeans(matchedBeans, this.beanManager);
               }

               bean = (Bean)var3.next();
            } while((bean.getName() != null || from != null) && (bean.getName() == null || !bean.getName().equals(from)));

            matchedBeans.add(bean);
         }
      }

      // $FF: synthetic method
      NameToBeanSet(BeanManagerImpl x0, Iterable x1, Object x2) {
         this(x0, x1);
      }
   }
}
