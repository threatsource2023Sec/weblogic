package weblogic.management.configuration;

public interface OptionalFeatureDeploymentMBean extends ConfigurationMBean {
   OptionalFeatureMBean[] getOptionalFeatures();

   OptionalFeatureMBean lookupOptionalFeature(String var1);

   OptionalFeatureMBean createOptionalFeature(String var1);

   void destroyOptionalFeature(OptionalFeatureMBean var1);
}
