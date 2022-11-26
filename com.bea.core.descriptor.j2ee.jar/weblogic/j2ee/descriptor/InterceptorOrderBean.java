package weblogic.j2ee.descriptor;

public interface InterceptorOrderBean {
   String[] getInterceptorClasses();

   void addInterceptorClass(String var1);

   void removeInterceptorClass(String var1);

   void setInterceptorClasses(String[] var1);

   String getId();

   void setId(String var1);
}
