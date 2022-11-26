package org.jboss.weld.resolution;

import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InterceptionFactory;
import javax.inject.Provider;
import org.jboss.weld.bean.AbstractProducerBean;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.events.WeldEvent;
import org.jboss.weld.inject.WeldInstance;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.LazyValueHolder;
import org.jboss.weld.util.Primitives;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Reflections;

public abstract class AbstractTypeSafeBeanResolver extends TypeSafeResolver {
   private final BeanManagerImpl beanManager;
   private final ComputingCache disambiguatedBeans;
   private final MetaAnnotationStore store;
   private final LazyValueHolder beansByType;

   public AbstractTypeSafeBeanResolver(BeanManagerImpl beanManager, final Iterable beans) {
      super(beans, (WeldConfiguration)beanManager.getServices().get(WeldConfiguration.class));
      this.beanManager = beanManager;
      this.disambiguatedBeans = ComputingCacheBuilder.newBuilder().build(new BeanDisambiguation());
      this.store = (MetaAnnotationStore)beanManager.getServices().get(MetaAnnotationStore.class);
      this.beansByType = new LazyValueHolder() {
         protected Map computeValue() {
            Map map = new HashMap();
            Iterator var2 = beans.iterator();

            while(var2.hasNext()) {
               Bean bean = (Bean)var2.next();
               this.mapBean(map, bean);
            }

            this.trimArrayListsToSize(map);
            return WeldCollections.immutableMapView(map);
         }

         private void mapBean(Map map, Bean bean) {
            Iterator var3 = bean.getTypes().iterator();

            while(var3.hasNext()) {
               Type type = (Type)var3.next();
               this.mapTypeToBean(map, type, bean);
               if (type instanceof ParameterizedType) {
                  Type rawType = ((ParameterizedType)type).getRawType();
                  this.mapTypeToBean(map, rawType, bean);
               } else if (type instanceof Class) {
                  Class clazz = (Class)type;
                  if (clazz.isPrimitive()) {
                     Class wrapped = Primitives.wrap(clazz);
                     this.mapTypeToBean(map, wrapped, bean);
                  }
               }
            }

         }

         private void mapTypeToBean(Map map, Type type, Bean bean) {
            if (!map.containsKey(type)) {
               map.put(type, new ArrayList());
            }

            ((ArrayList)map.get(type)).add(bean);
         }

         private void trimArrayListsToSize(Map map) {
            Iterator var2 = map.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               ((ArrayList)entry.getValue()).trimToSize();
            }

         }
      };
   }

   protected boolean matches(Resolvable resolvable, Bean bean) {
      AssignabilityRules rules = null;
      if (resolvable.isDelegate()) {
         rules = DelegateInjectionPointAssignabilityRules.instance();
      } else {
         rules = BeanTypeAssignabilityRules.instance();
      }

      return rules.matches(resolvable.getTypes(), bean.getTypes()) && Beans.containsAllQualifiers(resolvable.getQualifiers(), QualifierInstance.of(bean, this.store));
   }

   protected Iterable getAllBeans(Resolvable resolvable) {
      if (!resolvable.getTypes().contains(Object.class) && !Instance.class.equals(resolvable.getJavaClass()) && !Event.class.equals(resolvable.getJavaClass()) && !Provider.class.equals(resolvable.getJavaClass()) && !InterceptionFactory.class.equals(resolvable.getJavaClass()) && !WeldInstance.class.equals(resolvable.getJavaClass()) && !WeldEvent.class.equals(resolvable.getJavaClass()) && !resolvable.getTypes().contains(Serializable.class)) {
         Set beans = new HashSet();
         Iterator var3 = resolvable.getTypes().iterator();

         while(var3.hasNext()) {
            Type type = (Type)var3.next();
            beans.addAll(this.getBeans(type));
            if (type instanceof ParameterizedType) {
               Type rawType = ((ParameterizedType)type).getRawType();
               beans.addAll(this.getBeans(rawType));
            } else {
               Class clazz;
               if (type instanceof Class) {
                  clazz = (Class)type;
                  if (clazz.isPrimitive()) {
                     clazz = Primitives.wrap(clazz);
                     beans.addAll(this.getBeans(clazz));
                  }
               } else if (type instanceof GenericArrayType) {
                  clazz = Reflections.getRawType(type);
                  beans.addAll(this.getBeans(clazz));
               }
            }
         }

         return beans;
      } else {
         return super.getAllBeans(resolvable);
      }
   }

   private List getBeans(Type type) {
      List beansForType = (List)((Map)this.beansByType.get()).get(type);
      return beansForType == null ? Collections.emptyList() : beansForType;
   }

   protected BeanManagerImpl getBeanManager() {
      return this.beanManager;
   }

   protected Set filterResult(Set matched) {
      return Beans.removeDisabledBeans(matched, this.beanManager);
   }

   public Set resolve(Set beans) {
      if (beans.isEmpty()) {
         return beans;
      } else {
         beans = ImmutableSet.copyOf(beans);
         return (Set)this.disambiguatedBeans.getCastValue(beans);
      }
   }

   public void clear() {
      super.clear();
      this.disambiguatedBeans.clear();
      this.beansByType.clear();
   }

   MetaAnnotationStore getStore() {
      return this.store;
   }

   public class BeanDisambiguation implements Function {
      private BeanDisambiguation() {
      }

      public Set apply(Set from) {
         if (from.size() > 1) {
            ImmutableSet.Builder allBeans = ImmutableSet.builder();
            Set priorityBeans = new HashSet();

            Bean bean;
            for(Iterator var4 = from.iterator(); var4.hasNext(); allBeans.add(bean)) {
               bean = (Bean)var4.next();
               if (bean.isAlternative()) {
                  priorityBeans.add(bean);
               } else if (bean instanceof AbstractProducerBean) {
                  AbstractProducerBean producer = (AbstractProducerBean)bean;
                  if (producer.getDeclaringBean().isAlternative()) {
                     priorityBeans.add(bean);
                  }
               }
            }

            if (priorityBeans.isEmpty()) {
               return allBeans.build();
            } else if (priorityBeans.size() == 1) {
               return Collections.singleton(priorityBeans.iterator().next());
            } else {
               return this.resolveAlternatives(priorityBeans);
            }
         } else {
            return ImmutableSet.copyOf(from);
         }
      }

      public Set resolveAlternatives(Set alternatives) {
         int highestPriority = Integer.MIN_VALUE;
         Set selectedAlternativesWithHighestPriority = new HashSet();
         Iterator var4 = alternatives.iterator();

         while(var4.hasNext()) {
            Bean bean = (Bean)var4.next();
            Integer priority = AbstractTypeSafeBeanResolver.this.beanManager.getEnabled().getAlternativePriority(bean.getBeanClass());
            if (priority == null) {
               return ImmutableSet.copyOf(alternatives);
            }

            if (priority > highestPriority) {
               highestPriority = priority;
               selectedAlternativesWithHighestPriority.clear();
            }

            if (priority == highestPriority) {
               selectedAlternativesWithHighestPriority.add(bean);
            }
         }

         return ImmutableSet.copyOf(selectedAlternativesWithHighestPriority);
      }

      // $FF: synthetic method
      BeanDisambiguation(Object x1) {
         this();
      }
   }
}
