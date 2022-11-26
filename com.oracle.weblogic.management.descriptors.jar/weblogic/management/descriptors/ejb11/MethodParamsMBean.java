package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface MethodParamsMBean extends XMLElementMBean {
   String[] getMethodParams();

   void setMethodParams(String[] var1);

   void addMethodParam(String var1);
}
