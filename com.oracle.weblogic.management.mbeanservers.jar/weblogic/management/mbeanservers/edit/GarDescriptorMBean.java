package weblogic.management.mbeanservers.edit;

import weblogic.coherence.app.descriptor.wl.CoherenceApplicationBean;

public interface GarDescriptorMBean extends DescriptorMBean {
   CoherenceApplicationBean getCoherenceApplicationDescriptor();
}
