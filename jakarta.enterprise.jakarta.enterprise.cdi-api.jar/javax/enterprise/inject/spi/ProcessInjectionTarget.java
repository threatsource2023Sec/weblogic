package javax.enterprise.inject.spi;

public interface ProcessInjectionTarget {
   AnnotatedType getAnnotatedType();

   InjectionTarget getInjectionTarget();

   void setInjectionTarget(InjectionTarget var1);

   void addDefinitionError(Throwable var1);
}
