package javax.enterprise.context.spi;

public interface Context {
   Class getScope();

   Object get(Contextual var1, CreationalContext var2);

   Object get(Contextual var1);

   boolean isActive();
}
