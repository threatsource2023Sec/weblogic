package weblogic.management.mbeans.custom;

import weblogic.management.configuration.PropertyValueVBean;

public class PropertyValueVBeanImpl extends SimplePropertyValueVBeanImpl implements PropertyValueVBean {
   private boolean isOriginalValueSet = false;
   private boolean isOverridingConfigBeanValueSet = false;
   private boolean isResourceDeploymentPlanValueSet = false;
   private Object defaultValue;
   private Object originalValue = null;
   private Object overridingConfigBeanValue = null;
   private Object resourceDeploymentPlanValue = null;

   public PropertyValueVBeanImpl() {
   }

   public PropertyValueVBeanImpl(String propertyName) {
      super(propertyName);
   }

   public Object getDefaultValue() {
      return this.defaultValue;
   }

   public void setDefaultValue(Object defaultValue) {
      this.defaultValue = defaultValue;
   }

   public Object getOriginalValue() {
      return this.originalValue;
   }

   public boolean isOriginalValueAssigned() {
      return this.isOriginalValueSet;
   }

   public Object getOverridingConfigBeanValue() {
      return this.overridingConfigBeanValue;
   }

   public boolean isOverridingConfigBeanValueAssigned() {
      return this.isOverridingConfigBeanValueSet;
   }

   public Object getResourceDeploymentPlanValue() {
      return this.resourceDeploymentPlanValue;
   }

   public boolean isResourceDeploymentPlanValueAssigned() {
      return this.isResourceDeploymentPlanValueSet;
   }

   public String getEffectiveValueName() {
      String result = "DEFAULT";
      if (this.isOriginalValueSet) {
         result = "ORIGINAL";
      }

      if (this.isResourceDeploymentPlanValueSet) {
         result = "RESOURCE_DEPLOYMENT_PLAN";
      }

      if (this.isOverridingConfigBeanValueSet) {
         result = "OVERRIDING_CONFIG_BEAN";
      }

      return result;
   }

   public Object getEffectiveValue() {
      Object result = this.defaultValue;
      if (this.isOriginalValueSet) {
         result = this.originalValue;
      }

      if (this.isResourceDeploymentPlanValueSet) {
         result = this.resourceDeploymentPlanValue;
      }

      if (this.isOverridingConfigBeanValueSet) {
         result = this.overridingConfigBeanValue;
      }

      return result;
   }

   public void setOriginalValue(Object v) {
      this.originalValue = v;
      this.isOriginalValueSet = true;
   }

   public void setOverridingConfigBeanValue(Object v) {
      this.overridingConfigBeanValue = v;
      this.isOverridingConfigBeanValueSet = true;
   }

   public void setResourceDeploymentPlanValue(Object v) {
      this.resourceDeploymentPlanValue = v;
      this.isResourceDeploymentPlanValueSet = true;
   }
}
