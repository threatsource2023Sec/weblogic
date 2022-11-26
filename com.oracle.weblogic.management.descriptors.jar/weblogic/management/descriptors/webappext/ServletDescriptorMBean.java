package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;
import weblogic.management.descriptors.webapp.ServletMBean;
import weblogic.xml.dom.DOMProcessingException;

public interface ServletDescriptorMBean extends WebElementMBean {
   String getServletName();

   void setServletName(String var1) throws DOMProcessingException;

   ServletMBean getServlet();

   void setServlet(ServletMBean var1);

   String getDispatchPolicy();

   void setDispatchPolicy(String var1);

   String getRunAsPrincipalName();

   void setRunAsPrincipalName(String var1);

   String getInitAsPrincipalName();

   void setInitAsPrincipalName(String var1);

   String getDestroyAsPrincipalName();

   void setDestroyAsPrincipalName(String var1);
}
