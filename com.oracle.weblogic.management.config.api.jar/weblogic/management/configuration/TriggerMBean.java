package weblogic.management.configuration;

public interface TriggerMBean extends ConfigurationMBean {
   long getValue();

   void setValue(long var1);

   String getAction();

   void setAction(String var1);
}
