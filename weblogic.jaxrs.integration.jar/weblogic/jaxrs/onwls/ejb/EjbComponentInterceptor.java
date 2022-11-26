package weblogic.jaxrs.onwls.ejb;

import javax.annotation.PostConstruct;
import javax.interceptor.InvocationContext;
import org.glassfish.jersey.internal.inject.InjectionManager;

public final class EjbComponentInterceptor {
   private final InjectionManager injectionManager;

   public EjbComponentInterceptor(InjectionManager injectionManager) {
      this.injectionManager = injectionManager;
   }

   @PostConstruct
   private void inject(InvocationContext context) throws Exception {
      Object beanInstance = context.getTarget();
      this.injectionManager.inject(beanInstance, "CdiInjecteeSkippingClassAnalyzer");
      context.proceed();
   }
}
