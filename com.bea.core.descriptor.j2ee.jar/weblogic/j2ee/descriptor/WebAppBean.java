package weblogic.j2ee.descriptor;

public interface WebAppBean extends WebAppBaseBean, JavaEEModuleNameBean {
   String DESCRIPTOR_VERSION = "4.0";

   String getJavaEEModuleName();

   String[] getModuleNames();

   String getVersion();

   void setVersion(String var1);

   EmptyBean[] getDenyUncoveredHttpMethods();

   EmptyBean createDenyUncoveredHttpMethods();

   void destroyDenyUncoveredHttpMethods(EmptyBean var1);

   boolean isDenyUncoveredHttpMethods();

   AbsoluteOrderingBean[] getAbsoluteOrderings();

   AbsoluteOrderingBean createAbsoluteOrdering();

   void destroyAbsoluteOrdering(AbsoluteOrderingBean var1);

   String[] getDefaultContextPaths();

   void addDefaultContextPath(String var1);

   void removeDefaultContextPath(String var1);

   void setDefaultContextPaths(String[] var1);

   String[] getRequestCharacterEncodings();

   void addRequestCharacterEncoding(String var1);

   void removeRequestCharacterEncoding(String var1);

   void setRequestCharacterEncodings(String[] var1);

   String[] getResponseCharacterEncodings();

   void addResponseCharacterEncoding(String var1);

   void removeResponseCharacterEncoding(String var1);

   void setResponseCharacterEncodings(String[] var1);
}
