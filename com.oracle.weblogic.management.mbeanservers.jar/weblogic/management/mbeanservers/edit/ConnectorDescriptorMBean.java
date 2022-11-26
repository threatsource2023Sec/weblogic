package weblogic.management.mbeanservers.edit;

import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;

public interface ConnectorDescriptorMBean extends DescriptorMBean {
   ConnectorBean getConnectorDescriptor();

   WeblogicConnectorBean getWeblogicConnectorDescriptor();
}
