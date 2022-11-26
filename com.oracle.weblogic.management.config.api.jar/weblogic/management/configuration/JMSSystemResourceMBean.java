package weblogic.management.configuration;

import weblogic.j2ee.descriptor.wl.JMSBean;

public interface JMSSystemResourceMBean extends SystemResourceMBean {
   String getName();

   String getDescriptorFileName();

   JMSBean getJMSResource();
}
