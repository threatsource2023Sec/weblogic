package weblogic.management.mbeanservers.edit;

import weblogic.diagnostics.descriptor.WLDFResourceBean;

public interface WLDFDescriptorMBean extends DescriptorMBean {
   WLDFResourceBean getWLDFDescriptor();
}
