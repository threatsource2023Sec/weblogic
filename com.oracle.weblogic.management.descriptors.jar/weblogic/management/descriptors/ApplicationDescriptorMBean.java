package weblogic.management.descriptors;

import weblogic.management.descriptors.application.J2EEApplicationDescriptorMBean;
import weblogic.management.descriptors.application.weblogic.WeblogicApplicationMBean;

public interface ApplicationDescriptorMBean extends TopLevelDescriptorMBean {
   void setJ2EEApplicationDescriptor(J2EEApplicationDescriptorMBean var1);

   J2EEApplicationDescriptorMBean getJ2EEApplicationDescriptor();

   void setWeblogicApplicationDescriptor(WeblogicApplicationMBean var1);

   WeblogicApplicationMBean getWeblogicApplicationDescriptor();
}
