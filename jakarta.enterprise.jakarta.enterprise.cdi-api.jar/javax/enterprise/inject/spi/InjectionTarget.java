package javax.enterprise.inject.spi;

import javax.enterprise.context.spi.CreationalContext;

public interface InjectionTarget extends Producer {
   void inject(Object var1, CreationalContext var2);

   void postConstruct(Object var1);

   void preDestroy(Object var1);
}
