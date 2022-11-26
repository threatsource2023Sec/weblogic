package weblogic.descriptor.conflict;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.internal.AbstractDescriptorBean;

public class EditRemovedBeanDiffConflict extends DiffConflict {
   private final AbstractDescriptorBean removedBean;

   public EditRemovedBeanDiffConflict(AbstractDescriptorBean editBean, AbstractDescriptorBean removedBean) {
      super((BeanUpdateEvent)null, (BeanUpdateEvent.PropertyUpdate)null, editBean);
      this.removedBean = removedBean;
   }

   public ConflictDescriptorDiff resolve(ConflictDescriptorDiff orig2CurrDiff) {
      return orig2CurrDiff;
   }

   public String getResolveDescription() {
      return textFormatter.getEditRemovedBeanDiffConflictResolve(this.getBeanFullName());
   }

   protected AbstractDescriptorBean getEditBean() {
      return (AbstractDescriptorBean)this.editBean;
   }

   protected String getBeanFullName() {
      return this.getEditBean()._getQualifiedName();
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append(textFormatter.getEditRemovedBeanDiffConflictString(this.getBeanFullName()));
      if (!this.getEditBean()._getQualifiedKey().equals(this.removedBean._getQualifiedKey())) {
         result.append("\n     ").append(textFormatter.getRemovedParent(this.removedBean._getQualifiedName()));
      }

      return result.toString();
   }
}
