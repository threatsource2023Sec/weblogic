package weblogic.management.mbeanservers.edit;

import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;

public interface WebAppDescriptorMBean extends DescriptorMBean {
   WebAppBean getWebAppDescriptor();

   WeblogicWebAppBean getWeblogicWebAppDescriptor();
}
