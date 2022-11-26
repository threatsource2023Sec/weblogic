package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface QueryMethodMBean extends XMLElementMBean {
   String getMethodName();

   void setMethodName(String var1);

   String getMethodIntf();

   void setMethodIntf(String var1);

   MethodParamsMBean getMethodParams();

   void setMethodParams(MethodParamsMBean var1);
}
