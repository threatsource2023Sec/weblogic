package weblogic.persistence;

import weblogic.management.ManagementException;
import weblogic.management.runtime.PersistenceUnitRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class PersistenceUnitRuntimeMBeanImpl extends RuntimeMBeanDelegate implements PersistenceUnitRuntimeMBean {
   private final String name;

   public PersistenceUnitRuntimeMBeanImpl(String name, RuntimeMBean parent) throws ManagementException {
      super(name, parent, true, "PersistenceUnitRuntime");
      this.name = name;
   }

   public String getPersistenceUnitName() {
      return this.name;
   }
}
