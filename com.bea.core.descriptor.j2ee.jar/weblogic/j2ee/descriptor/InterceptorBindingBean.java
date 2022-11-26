package weblogic.j2ee.descriptor;

public interface InterceptorBindingBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getEjbName();

   void setEjbName(String var1);

   String[] getInterceptorClasses();

   void addInterceptorClass(String var1);

   void removeInterceptorClass(String var1);

   void setInterceptorClasses(String[] var1);

   InterceptorOrderBean getInterceptorOrder();

   InterceptorOrderBean createInterceptorOrder();

   void destroyInterceptorOrder(InterceptorOrderBean var1);

   boolean isExcludeDefaultInterceptors();

   void setExcludeDefaultInterceptors(boolean var1);

   boolean isExcludeClassInterceptors();

   void setExcludeClassInterceptors(boolean var1);

   NamedMethodBean getMethod();

   NamedMethodBean createMethod();

   void destroyMethod(NamedMethodBean var1);

   String getId();

   void setId(String var1);
}
