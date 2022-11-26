package weblogic.management.configuration;

public interface DeploymentValidationPluginMBean extends ConfigurationMBean {
   String getFactoryClassname();

   void setFactoryClassname(String var1);

   ParameterMBean[] getParameters();

   void destroyParameter(ParameterMBean var1);

   ParameterMBean createParameter(String var1);
}
