package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;

public interface AuthFilterMBean extends WebElementMBean {
   String getFilter();

   void setFilter(String var1);
}
