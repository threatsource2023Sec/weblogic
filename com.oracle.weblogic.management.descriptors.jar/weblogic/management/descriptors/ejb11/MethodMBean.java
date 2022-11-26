package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface MethodMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getEJBName();

   void setEJBName(String var1);

   void setMethodIntf(String var1);

   String getMethodIntf();

   String getMethodName();

   void setMethodName(String var1);

   MethodParamsMBean getMethodParams();

   void setMethodParams(MethodParamsMBean var1);
}
