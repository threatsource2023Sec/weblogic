package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface MethodPermissionMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String[] getRoleNames();

   void setRoleNames(String[] var1);

   void addRoleName(String var1);

   void removeRoleName(String var1);

   MethodMBean[] getMethods();

   void setMethods(MethodMBean[] var1);

   void addMethod(MethodMBean var1);

   void removeMethod(MethodMBean var1);
}
