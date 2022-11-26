package weblogic.descriptor.conflict;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;

public class AddSameBeanDiffConflict extends DiffConflict {
   private final AbstractDescriptorBean addToEditBean;

   public AddSameBeanDiffConflict(BeanUpdateEvent orig2CurrBeanUpdate, BeanUpdateEvent.PropertyUpdate orig2CurrPropertyUpdate, DescriptorBean editBean, AbstractDescriptorBean addToEditBean, AbstractDescriptorBean addToCurrentBean) {
      super(orig2CurrBeanUpdate, orig2CurrPropertyUpdate, editBean);
      this.addToEditBean = addToEditBean;
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append(this.addToEditBean._getQualifiedName()).append(" - Bean with same qualified name was already add to ").append(this.getBeanFullName());
      return textFormatter.getAddSameBeanDiffConflictString(this.addToEditBean._getQualifiedName(), this.getBeanFullName());
   }

   public ConflictDescriptorDiff resolve(ConflictDescriptorDiff orig2CurrDiff) {
      return this.removePropertyUpdateFromOrig2CurrDiff(orig2CurrDiff);
   }

   public String getResolveDescription() {
      return textFormatter.getAddSameBeanDiffConflictResolve(this.addToEditBean._getQualifiedName(), this.getBeanFullName());
   }
}
