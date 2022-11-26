package org.jboss.weld.bean;

import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.inject.Singleton;
import org.jboss.weld.contexts.cache.RequestScopedCache;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public abstract class ContextualInstanceStrategy {
   public static ContextualInstanceStrategy defaultStrategy() {
      return ContextualInstanceStrategy.DefaultContextualInstanceStrategy.INSTANCE;
   }

   public static ContextualInstanceStrategy create(BeanAttributes bean, BeanManagerImpl manager) {
      if (ApplicationScoped.class != bean.getScope() && Singleton.class != bean.getScope()) {
         return (ContextualInstanceStrategy)(ContextualInstanceStrategy.CachingContextualInstanceStrategy.CACHEABLE_SCOPES.contains(bean.getScope()) ? new CachingContextualInstanceStrategy() : defaultStrategy());
      } else {
         return new ApplicationScopedContextualInstanceStrategy();
      }
   }

   ContextualInstanceStrategy() {
   }

   abstract Object get(Bean var1, BeanManagerImpl var2, CreationalContext var3);

   abstract Object getIfExists(Bean var1, BeanManagerImpl var2);

   abstract void destroy(Bean var1);

   private static class CachingContextualInstanceStrategy extends DefaultContextualInstanceStrategy {
      private static final Set CACHEABLE_SCOPES = ImmutableSet.of(RequestScoped.class, ConversationScoped.class, SessionScoped.class);
      private final ThreadLocal cache;

      private CachingContextualInstanceStrategy() {
         super(null);
         this.cache = new ThreadLocal();
      }

      Object getIfExists(Bean bean, BeanManagerImpl manager) {
         Object cached = this.cache.get();
         if (cached != null) {
            return cached;
         } else {
            cached = super.getIfExists(bean, manager);
            if (cached != null && RequestScopedCache.addItemIfActive(this.cache)) {
               this.cache.set(cached);
            }

            return cached;
         }
      }

      Object get(Bean bean, BeanManagerImpl manager, CreationalContext ctx) {
         Object cached = this.cache.get();
         if (cached != null) {
            return cached;
         } else {
            cached = super.get(bean, manager, ctx);
            if (RequestScopedCache.addItemIfActive(this.cache)) {
               this.cache.set(cached);
            }

            return cached;
         }
      }

      // $FF: synthetic method
      CachingContextualInstanceStrategy(Object x0) {
         this();
      }
   }

   private static class ApplicationScopedContextualInstanceStrategy extends DefaultContextualInstanceStrategy {
      private volatile Object value;

      private ApplicationScopedContextualInstanceStrategy() {
         super(null);
      }

      Object getIfExists(Bean bean, BeanManagerImpl manager) {
         Object instance = this.value;
         if (instance != null) {
            return instance;
         } else {
            synchronized(this) {
               if (this.value == null) {
                  instance = super.getIfExists(bean, manager);
                  if (instance != null) {
                     this.value = instance;
                  }
               }

               return instance;
            }
         }
      }

      Object get(Bean bean, BeanManagerImpl manager, CreationalContext ctx) {
         Object instance = this.value;
         if (instance != null) {
            return instance;
         } else {
            synchronized(this) {
               if ((instance = this.value) == null) {
                  this.value = instance = super.get(bean, manager, ctx);
               }

               return instance;
            }
         }
      }

      void destroy(Bean bean) {
         this.value = null;
      }

      // $FF: synthetic method
      ApplicationScopedContextualInstanceStrategy(Object x0) {
         this();
      }
   }

   private static class DefaultContextualInstanceStrategy extends ContextualInstanceStrategy {
      static final ContextualInstanceStrategy INSTANCE = new DefaultContextualInstanceStrategy();

      private DefaultContextualInstanceStrategy() {
      }

      Object getIfExists(Bean bean, BeanManagerImpl manager) {
         return manager.getContext(bean.getScope()).get(bean);
      }

      Object get(Bean bean, BeanManagerImpl manager, CreationalContext ctx) {
         Context context = manager.getContext(bean.getScope());
         Object instance = context.get(bean);
         if (instance == null) {
            if (ctx == null) {
               ctx = manager.createCreationalContext(bean);
            }

            instance = context.get(bean, (CreationalContext)Reflections.cast(ctx));
         }

         return instance;
      }

      void destroy(Bean bean) {
      }

      // $FF: synthetic method
      DefaultContextualInstanceStrategy(Object x0) {
         this();
      }
   }
}
