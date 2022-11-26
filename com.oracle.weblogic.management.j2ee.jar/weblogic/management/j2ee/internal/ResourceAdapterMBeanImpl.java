package weblogic.management.j2ee.internal;

import weblogic.management.j2ee.ResourceAdapterMBean;

public class ResourceAdapterMBeanImpl extends J2EEManagedObjectMBeanImpl implements ResourceAdapterMBean {
   private final String mjcaResource;

   public ResourceAdapterMBeanImpl(String objectname, String jcaResource) {
      super(objectname);
      this.mjcaResource = jcaResource;
   }

   public String getjcaResource() {
      return this.mjcaResource;
   }
}
