package weblogic.j2ee.descriptor.wl;

public interface TransactionIsolationBean {
   String getIsolationLevel();

   void setIsolationLevel(String var1);

   MethodBean[] getMethods();

   MethodBean createMethod();

   void destroyMethod(MethodBean var1);

   String getId();

   void setId(String var1);
}
