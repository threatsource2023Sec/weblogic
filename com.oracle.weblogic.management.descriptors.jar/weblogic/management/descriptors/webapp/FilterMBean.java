package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface FilterMBean extends WebElementMBean {
   String getFilterName();

   void setFilterName(String var1);

   UIMBean getUIData();

   void setUIData(UIMBean var1);

   String getFilterClass();

   void setFilterClass(String var1);

   ParameterMBean[] getInitParams();

   void setInitParams(ParameterMBean[] var1);

   void addInitParam(ParameterMBean var1);

   void removeInitParam(ParameterMBean var1);
}
