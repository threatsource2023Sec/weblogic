package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface ContainerTransactionMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   MethodMBean[] getMethods();

   void setMethods(MethodMBean[] var1);

   void addMethod(MethodMBean var1);

   void removeMethod(MethodMBean var1);

   void setTransAttribute(String var1);

   String getTransAttribute();
}
