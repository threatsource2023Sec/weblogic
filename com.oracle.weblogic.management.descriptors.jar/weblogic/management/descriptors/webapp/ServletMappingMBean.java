package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface ServletMappingMBean extends WebElementMBean {
   ServletMBean getServlet();

   void setServlet(ServletMBean var1);

   String getURLPattern();

   void setURLPattern(String var1);
}
