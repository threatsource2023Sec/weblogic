package weblogic.management.configuration;

public interface PartitionPropertyMBean extends ConfigurationMBean {
   String getValue();

   void setValue(String var1);
}
