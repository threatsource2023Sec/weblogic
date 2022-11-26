package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLDeclarationMBean;
import weblogic.management.descriptors.XMLElementMBean;

public interface EJBJarMBean extends XMLElementMBean, XMLDeclarationMBean {
   String getDescription();

   void setDescription(String var1);

   String getDisplayName();

   void setDisplayName(String var1);

   String getSmallIcon();

   void setSmallIcon(String var1);

   String getLargeIcon();

   void setLargeIcon(String var1);

   EnterpriseBeansMBean getEnterpriseBeans();

   void setEnterpriseBeans(EnterpriseBeansMBean var1);

   AssemblyDescriptorMBean getAssemblyDescriptor();

   void setAssemblyDescriptor(AssemblyDescriptorMBean var1);

   String getEJBClientJar();

   void setEJBClientJar(String var1);
}
