package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface QueryMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   QueryMethodMBean getQueryMethod();

   void setQueryMethod(QueryMethodMBean var1);

   String getResultTypeMapping();

   void setResultTypeMapping(String var1);

   String getEJBQl();

   void setEJBQl(String var1);
}
