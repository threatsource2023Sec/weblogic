package weblogic.j2ee.descriptor.wl;

public interface RetryMethodsOnRollbackBean {
   String getDescription();

   void setDescription(String var1);

   int getRetryCount();

   void setRetryCount(int var1);

   MethodBean[] getMethods();

   MethodBean createMethod();

   void destroyMethod(MethodBean var1);

   String getId();

   void setId(String var1);
}
