package weblogic.management.mbeanservers.edit;

import weblogic.j2ee.descriptor.wl.JMSBean;

public interface JMSDescriptorMBean extends DescriptorMBean {
   JMSBean getJMSDescriptor();
}
