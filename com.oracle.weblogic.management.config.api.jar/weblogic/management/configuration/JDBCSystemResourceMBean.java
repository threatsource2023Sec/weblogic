package weblogic.management.configuration;

import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;

public interface JDBCSystemResourceMBean extends SystemResourceMBean {
   String getName();

   String getDescriptorFileName();

   JDBCDataSourceBean getJDBCResource();
}
