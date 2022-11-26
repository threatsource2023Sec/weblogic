package weblogic.management.mbeanservers.edit;

import weblogic.j2ee.descriptor.PersistenceBean;

public interface PersistenceDescriptorMBean extends DescriptorMBean {
   PersistenceBean getPersistenceDescriptor();
}
