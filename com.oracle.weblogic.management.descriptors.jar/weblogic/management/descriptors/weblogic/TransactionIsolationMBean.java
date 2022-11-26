package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;
import weblogic.management.descriptors.ejb11.MethodMBean;

public interface TransactionIsolationMBean extends XMLElementMBean {
   void setIsolationLevel(String var1);

   String getIsolationLevel();

   MethodMBean[] getMethods();

   void setMethods(MethodMBean[] var1);

   void addMethod(MethodMBean var1);

   void removeMethod(MethodMBean var1);
}
