package weblogic.management.j2ee.internal;

import weblogic.management.j2ee.JCAConnectionFactoryMBean;

public final class JCAConnectionFactoryMBeanImpl extends J2EEManagedObjectMBeanImpl implements JCAConnectionFactoryMBean {
   private final String mmanagedConnectionFactory;

   public JCAConnectionFactoryMBeanImpl(String name, String managedConnectionFactory) {
      super(name);
      this.mmanagedConnectionFactory = managedConnectionFactory;
   }

   public String getmanagedConnectionFactory() {
      return this.mmanagedConnectionFactory;
   }
}
