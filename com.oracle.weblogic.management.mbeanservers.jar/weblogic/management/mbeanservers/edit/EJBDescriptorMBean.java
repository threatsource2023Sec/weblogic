package weblogic.management.mbeanservers.edit;

import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;

public interface EJBDescriptorMBean extends DescriptorMBean {
   EjbJarBean getEJBDescriptor();

   WeblogicEjbJarBean getWeblogicEJBDescriptor();
}
