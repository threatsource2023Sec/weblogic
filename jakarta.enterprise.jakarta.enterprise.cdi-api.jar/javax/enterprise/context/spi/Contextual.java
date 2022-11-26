package javax.enterprise.context.spi;

public interface Contextual {
   Object create(CreationalContext var1);

   void destroy(Object var1, CreationalContext var2);
}
