package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBean;

public interface ConnectionParamsMBean extends XMLElementMBean {
   ParameterMBean[] getParameters();

   void setParameters(ParameterMBean[] var1);

   void addParameter(ParameterMBean var1);

   void removeParameter(ParameterMBean var1);
}
