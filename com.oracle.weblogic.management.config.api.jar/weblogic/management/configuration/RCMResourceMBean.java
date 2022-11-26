package weblogic.management.configuration;

public interface RCMResourceMBean extends ConfigurationMBean {
   TriggerMBean[] getTriggers();

   TriggerMBean lookupTrigger(String var1);

   TriggerMBean createTrigger(String var1);

   TriggerMBean createTrigger(String var1, long var2, String var4);

   void destroyTrigger(TriggerMBean var1);
}
