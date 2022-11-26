package weblogic.j2ee.descriptor;

public interface InterceptorsBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   InterceptorBean[] getInterceptors();

   InterceptorBean createInterceptor();

   InterceptorBean lookupInterceptor(String var1);

   void destroyInterceptor(InterceptorBean var1);

   String getId();

   void setId(String var1);
}
