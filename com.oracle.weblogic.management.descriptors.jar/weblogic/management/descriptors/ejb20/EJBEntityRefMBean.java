package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface EJBEntityRefMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getRemoteEJBName();

   void setRemoteEJBName(String var1);

   String getEJBRefName();

   void setEJBRefName(String var1);

   String getHome();

   void setHome(String var1);

   String getRemote();

   void setRemote(String var1);

   String getEJBLink();

   void setEJBLink(String var1);
}
