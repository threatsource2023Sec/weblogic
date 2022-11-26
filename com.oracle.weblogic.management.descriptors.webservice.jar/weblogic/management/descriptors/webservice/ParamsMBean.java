package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface ParamsMBean extends XMLElementMBean {
   ParamMBean[] getParams();

   void setParams(ParamMBean[] var1);

   void addParam(ParamMBean var1);

   void removeParam(ParamMBean var1);

   ReturnParamMBean getReturnParam();

   void setReturnParam(ReturnParamMBean var1);

   FaultMBean[] getFaults();

   void setFaults(FaultMBean[] var1);

   void addFault(FaultMBean var1);

   void removeFault(FaultMBean var1);
}
