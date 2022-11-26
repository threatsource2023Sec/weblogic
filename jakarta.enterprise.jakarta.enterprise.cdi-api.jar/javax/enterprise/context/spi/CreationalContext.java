package javax.enterprise.context.spi;

public interface CreationalContext {
   void push(Object var1);

   void release();
}
