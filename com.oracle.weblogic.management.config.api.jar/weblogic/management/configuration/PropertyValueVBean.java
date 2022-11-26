package weblogic.management.configuration;

public interface PropertyValueVBean extends SimplePropertyValueVBean {
   String ORIGIN_DEFAULT = "DEFAULT";
   String ORIGIN_ORIGINAL = "ORIGINAL";
   String ORIGIN_OVERRIDING_CONFIG_BEAN = "OVERRIDING_CONFIG_BEAN";
   String ORIGIN_RESOURCE_DEPLOYMENT_PLAN = "RESOURCE_DEPLOYMENT_PLAN";

   Object getDefaultValue();

   void setDefaultValue(Object var1);

   Object getOriginalValue();

   void setOriginalValue(Object var1);

   boolean isOriginalValueAssigned();

   Object getOverridingConfigBeanValue();

   void setOverridingConfigBeanValue(Object var1);

   boolean isOverridingConfigBeanValueAssigned();

   Object getResourceDeploymentPlanValue();

   void setResourceDeploymentPlanValue(Object var1);

   boolean isResourceDeploymentPlanValueAssigned();

   String getEffectiveValueName();
}
