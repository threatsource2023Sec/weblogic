package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface EJBReferenceDescriptionMBean extends XMLElementMBean {
   String getEJBRefName();

   void setEJBRefName(String var1);

   String getJNDIName();

   void setJNDIName(String var1);
}
