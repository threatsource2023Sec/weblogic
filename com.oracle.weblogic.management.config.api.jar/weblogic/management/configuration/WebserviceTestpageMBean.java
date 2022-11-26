package weblogic.management.configuration;

public interface WebserviceTestpageMBean extends ConfigurationMBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   boolean isBasicAuthEnabled();

   void setBasicAuthEnabled(boolean var1);
}
