package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface EJBEntityRefDescriptionMBean extends XMLElementMBean {
   String getRemoteEJBName();

   void setRemoteEJBName(String var1);

   String getJNDIName();

   void setJNDIName(String var1);
}
