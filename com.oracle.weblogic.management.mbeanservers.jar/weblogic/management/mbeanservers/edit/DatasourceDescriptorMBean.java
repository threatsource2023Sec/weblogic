package weblogic.management.mbeanservers.edit;

import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;

public interface DatasourceDescriptorMBean extends DescriptorMBean {
   JDBCDataSourceBean getDatasourceDescriptor();
}
