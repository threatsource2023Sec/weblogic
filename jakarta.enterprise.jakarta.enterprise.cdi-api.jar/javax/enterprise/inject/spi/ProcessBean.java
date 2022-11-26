package javax.enterprise.inject.spi;

public interface ProcessBean {
   Annotated getAnnotated();

   Bean getBean();

   void addDefinitionError(Throwable var1);
}
