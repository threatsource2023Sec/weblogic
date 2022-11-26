package weblogic.management.configuration;

public interface OptionalFeatureMBean extends ConfigurationMBean {
   boolean isEnabled();

   void setEnabled(boolean var1);
}
