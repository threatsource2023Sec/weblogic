package weblogic.management.descriptors.toplevel;

import java.util.Set;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.management.descriptors.TopLevelDescriptorMBean;
import weblogic.management.descriptors.cmp11.WeblogicRDBMSJarMBean;
import weblogic.management.descriptors.ejb11.EJBJarMBean;
import weblogic.management.descriptors.weblogic.WeblogicEJBJarMBean;

public interface EJBDescriptorMBean extends TopLevelDescriptorMBean {
   String getJarFileName();

   void setJarFileName(String var1);

   String getParsingErrorMessage();

   void setParsingErrorMessage(String var1);

   void setEjbJarBean(EjbJarBean var1);

   EjbJarBean getEjbJarBean();

   void setEJBJarMBean(EJBJarMBean var1);

   EJBJarMBean getEJBJarMBean();

   void setWeblogicEJBJarMBean(WeblogicEJBJarMBean var1);

   WeblogicEJBJarMBean getWeblogicEJBJarMBean();

   WeblogicRDBMSJarMBean[] getWeblogicRDBMSJarMBeans();

   void setWeblogicRDBMSJarMBeans(WeblogicRDBMSJarMBean[] var1);

   void addWeblogicRDBMSJarMBean(WeblogicRDBMSJarMBean var1);

   void removeWeblogicRDBMSJarMBean(WeblogicRDBMSJarMBean var1);

   weblogic.management.descriptors.cmp20.WeblogicRDBMSJarMBean[] getWeblogicRDBMSJar20MBeans();

   void setWeblogicRDBMSJar20MBeans(weblogic.management.descriptors.cmp20.WeblogicRDBMSJarMBean[] var1);

   void addWeblogicRDBMSJar20MBean(weblogic.management.descriptors.cmp20.WeblogicRDBMSJarMBean var1);

   void removeWeblogicRDBMSJar20MBean(weblogic.management.descriptors.cmp20.WeblogicRDBMSJarMBean var1);

   Set getRDBMSDescriptorFileNames();
}
