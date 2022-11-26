package weblogic.management.mbeanservers.edit;

import weblogic.j2ee.descriptor.WebservicesBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebservicesBean;

public interface WebservicesDescriptorMBean extends DescriptorMBean {
   WebservicesBean getWebservicesDescriptor();

   WeblogicWebservicesBean getWeblogicWebservicesDescriptor();
}
