package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.Harvester;

abstract class BaseHarvesterControlImpl implements DelegateHarvesterControl {
   private boolean active = false;
   private Harvester delegate;
   private DelegateHarvesterControl.ActivationPolicy activationPolicy;
   private String namespace;
   private String name;
   private boolean defaultDelegate;
   private boolean attributeValidationEnabled;

   public BaseHarvesterControlImpl(String harvesterName, String namespace, DelegateHarvesterControl.ActivationPolicy policy, boolean isDefault) {
      this.activationPolicy = DelegateHarvesterControl.ActivationPolicy.ON_METADATA_REQUEST;
      this.attributeValidationEnabled = false;
      this.namespace = namespace;
      this.name = harvesterName;
      this.activationPolicy = policy;
      this.defaultDelegate = isDefault;
   }

   public boolean isDefaultDelegate() {
      return this.defaultDelegate;
   }

   public Harvester getDelegate() {
      return this.delegate;
   }

   public String getName() {
      return this.name;
   }

   public String getNamespace() {
      return this.namespace;
   }

   protected void setDelegate(Harvester h) {
      this.delegate = h;
   }

   protected void setActive(boolean active) {
      this.active = active;
   }

   public void activate() {
      this.active = true;
   }

   public void deactivate() {
      this.active = false;
   }

   public boolean isActive() {
      return this.active;
   }

   public DelegateHarvesterControl.ActivationPolicy getActivationPolicy() {
      return this.activationPolicy;
   }

   public void setActivationPolicy(DelegateHarvesterControl.ActivationPolicy activationPolicy) {
      this.activationPolicy = activationPolicy;
   }

   public void setAttributeValidationEnabled(boolean validate) {
      this.attributeValidationEnabled = validate;
   }

   public boolean isAttributeValidationEnabled() {
      return this.attributeValidationEnabled;
   }
}
