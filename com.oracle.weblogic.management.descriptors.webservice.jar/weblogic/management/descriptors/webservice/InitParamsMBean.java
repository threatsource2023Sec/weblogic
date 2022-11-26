package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface InitParamsMBean extends XMLElementMBean {
   InitParamMBean[] getInitParams();

   void setInitParams(InitParamMBean[] var1);

   void addInitParam(InitParamMBean var1);

   void removeInitParam(InitParamMBean var1);
}
