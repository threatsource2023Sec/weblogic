package weblogic.kernel;

public interface AuditableThreadLocal {
   Object get();

   Object get(AuditableThread var1);

   void set(Object var1);
}
