package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface FilterMappingMBean extends WebElementMBean {
   FilterMBean getFilter();

   void setFilter(FilterMBean var1);

   String getUrlPattern();

   void setUrlPattern(String var1);

   ServletMBean getServlet();

   void setServlet(ServletMBean var1);
}
