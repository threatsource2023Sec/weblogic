package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;
import weblogic.management.descriptors.webapp.EjbRefMBean;

public interface EjbReferenceDescriptionMBean extends WebElementMBean {
   void setEjbReference(EjbRefMBean var1);

   EjbRefMBean getEjbReference();

   void setJndiName(String var1);

   String getJndiName();
}
