package weblogic.health;

import weblogic.management.runtime.RuntimeMBean;

final class MonitoredSystemTableEntry {
   private final MonitoredSystemTableKey key;
   private final boolean isCritical;
   private final HealthFeedback hf;
   private final String MBeanName;
   private final String MBeanType;

   MonitoredSystemTableEntry(MonitoredSystemTableKey keyArg, RuntimeMBean MBeanRefArg, boolean isCriticalArg) {
      this.key = keyArg;
      this.MBeanName = MBeanRefArg.getName();
      this.MBeanType = MBeanRefArg.getType();
      this.isCritical = isCriticalArg;
      this.hf = (HealthFeedback)MBeanRefArg;
   }

   MonitoredSystemTableEntry(MonitoredSystemTableKey keyArg, HealthFeedback hf, boolean isCriticalArg) {
      this.key = keyArg;
      this.hf = hf;
      this.isCritical = isCriticalArg;
      this.MBeanName = "";
      this.MBeanType = "";
   }

   MonitoredSystemTableKey getKey() {
      return this.key;
   }

   HealthFeedback getHealthFeedback() {
      return this.hf;
   }

   boolean getIsCritical() {
      return this.isCritical;
   }

   String getMBeanName() {
      return this.MBeanName;
   }

   String getMBeanType() {
      return this.MBeanType;
   }

   HealthState getHealthState() {
      String subsystem = this.key.getId();
      String partition = this.key.getPartition();
      HealthState health = this.hf.getHealthState();
      health = new HealthState(health.getState(), health.getSymptoms(), partition);
      health.setSubsystemName(subsystem);
      health.setCritical(this.isCritical);
      health.setMBeanName(this.MBeanName);
      health.setMBeanType(this.MBeanType);
      return health;
   }
}
