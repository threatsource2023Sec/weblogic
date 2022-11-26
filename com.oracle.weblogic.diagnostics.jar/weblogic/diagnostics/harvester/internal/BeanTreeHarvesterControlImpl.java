package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.Harvester;

class BeanTreeHarvesterControlImpl extends BaseHarvesterControlImpl {
   private BeanTreeHarvesterImpl beanTreeHarvester;

   public BeanTreeHarvesterControlImpl(String name, String namespace) {
      this(name, namespace, DelegateHarvesterControl.ActivationPolicy.ON_METADATA_REQUEST);
   }

   public BeanTreeHarvesterControlImpl(String name, String namespace, DelegateHarvesterControl.ActivationPolicy policy, boolean isDefault) {
      super(name, namespace, policy, isDefault);
   }

   public BeanTreeHarvesterControlImpl(String name, String namespace, DelegateHarvesterControl.ActivationPolicy policy) {
      super(name, namespace, policy, false);
   }

   public void deactivate() {
      synchronized(this) {
         if (this.beanTreeHarvester != null && this.isActive()) {
            this.beanTreeHarvester.deallocate();
            this.beanTreeHarvester = null;
            this.setDelegate((Harvester)null);
         }

         this.setActive(false);
      }
   }

   public void activate() {
      synchronized(this) {
         if (!this.isActive()) {
            this.beanTreeHarvester = BeanTreeHarvesterImpl.getInstance();
            this.beanTreeHarvester.setAttributeValidationEnabled(this.isAttributeValidationEnabled());
            this.beanTreeHarvester.setRemoveAttributesWithProblems(false);
            this.setDelegate(this.beanTreeHarvester);
            this.setActive(true);
         }

      }
   }
}
