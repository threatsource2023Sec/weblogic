package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;
import weblogic.management.descriptors.webapp.ServletMBean;
import weblogic.xml.dom.DOMProcessingException;

public interface InitAsMBean extends WebElementMBean {
   String getServletName();

   void setServletName(String var1) throws DOMProcessingException;

   ServletMBean getServlet();

   void setServlet(ServletMBean var1);

   String getPrincipalName();

   void setPrincipalName(String var1);
}
