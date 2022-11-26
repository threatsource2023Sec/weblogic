package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;
import weblogic.management.descriptors.ejb11.MethodMBean;

public interface ExcludeListMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   MethodMBean[] getMethods();

   void setMethods(MethodMBean[] var1);

   void addMethod(MethodMBean var1);

   void removeMethod(MethodMBean var1);
}
