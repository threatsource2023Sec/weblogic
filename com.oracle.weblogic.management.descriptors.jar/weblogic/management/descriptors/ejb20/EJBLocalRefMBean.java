package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface EJBLocalRefMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getEJBRefName();

   void setEJBRefName(String var1);

   void setEJBRefType(String var1);

   String getEJBRefType();

   String getLocalHome();

   void setLocalHome(String var1);

   String getLocal();

   void setLocal(String var1);

   String getEJBLink();

   void setEJBLink(String var1);
}
