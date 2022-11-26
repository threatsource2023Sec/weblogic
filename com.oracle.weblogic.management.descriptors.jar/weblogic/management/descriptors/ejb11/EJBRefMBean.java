package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface EJBRefMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getEJBRefName();

   void setEJBRefName(String var1);

   void setEJBRefType(String var1);

   String getEJBRefType();

   String getHome();

   void setHome(String var1);

   String getRemote();

   void setRemote(String var1);

   String getEJBLink();

   void setEJBLink(String var1);
}
