package weblogic.management.mbeanservers.edit;

import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;

public interface ApplicationDescriptorMBean extends DescriptorMBean {
   WeblogicApplicationBean getWeblogicApplicationDescriptor();

   ApplicationBean getApplicationDescriptor();
}
