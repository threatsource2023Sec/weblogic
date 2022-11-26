package weblogic.j2ee.descriptor;

public interface MethodPermissionBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String[] getRoleNames();

   void addRoleName(String var1);

   void removeRoleName(String var1);

   void setRoleNames(String[] var1);

   EmptyBean getUnchecked();

   EmptyBean createUnchecked();

   void destroyUnchecked(EmptyBean var1);

   MethodBean[] getMethods();

   MethodBean createMethod();

   void destroyMethod(MethodBean var1);

   String getId();

   void setId(String var1);
}
