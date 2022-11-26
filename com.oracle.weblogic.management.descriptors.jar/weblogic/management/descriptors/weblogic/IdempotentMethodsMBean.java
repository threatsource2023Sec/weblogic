package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;
import weblogic.management.descriptors.ejb11.MethodMBean;

public interface IdempotentMethodsMBean extends XMLElementMBean {
   MethodMBean[] getMethods();

   void setMethods(MethodMBean[] var1);

   void addMethod(MethodMBean var1);

   void removeMethod(MethodMBean var1);
}
