package org.jboss.weld.resolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.Interceptor;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Beans;

public class TypeSafeInterceptorResolver extends TypeSafeResolver {
   private final BeanManagerImpl manager;

   public TypeSafeInterceptorResolver(BeanManagerImpl manager, Iterable interceptors) {
      super(interceptors, (WeldConfiguration)manager.getServices().get(WeldConfiguration.class));
      this.manager = manager;
   }

   protected boolean matches(InterceptorResolvable resolvable, Interceptor bean) {
      return bean.intercepts(resolvable.getInterceptionType()) && Beans.containsAllInterceptionBindings(bean.getInterceptorBindings(), resolvable.getQualifiers(), this.getManager()) && this.manager.getEnabled().isInterceptorEnabled(bean.getBeanClass());
   }

   protected List sortResult(Set matchedInterceptors) {
      List sortedInterceptors = new ArrayList(matchedInterceptors);
      Collections.sort(sortedInterceptors, this.manager.getEnabled().getInterceptorComparator());
      return sortedInterceptors;
   }

   protected Set filterResult(Set matched) {
      return matched;
   }

   public BeanManagerImpl getManager() {
      return this.manager;
   }
}
