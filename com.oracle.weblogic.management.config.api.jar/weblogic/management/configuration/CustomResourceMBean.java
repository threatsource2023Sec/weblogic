package weblogic.management.configuration;

import weblogic.descriptor.DescriptorBean;

public interface CustomResourceMBean extends SystemResourceMBean {
   String getResourceClass();

   void setResourceClass(String var1);

   String getDescriptorBeanClass();

   void setDescriptorBeanClass(String var1);

   DescriptorBean getCustomResource();
}
