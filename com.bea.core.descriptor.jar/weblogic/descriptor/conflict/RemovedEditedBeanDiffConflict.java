package weblogic.descriptor.conflict;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;

public class RemovedEditedBeanDiffConflict extends DiffConflict {
   private final DescriptorBean editedCurrentBean;

   public RemovedEditedBeanDiffConflict(BeanUpdateEvent orig2CurrBeanUpdate, DescriptorBean removedEditBean, DescriptorBean editedCurrentBean) {
      super(orig2CurrBeanUpdate, (BeanUpdateEvent.PropertyUpdate)null, removedEditBean);
      this.editedCurrentBean = editedCurrentBean;
   }

   public ConflictDescriptorDiff resolve(ConflictDescriptorDiff orig2CurrDiff) {
      return this.removePropertyUpdateFromOrig2CurrDiff(orig2CurrDiff);
   }

   public String getResolveDescription() {
      String bn;
      if (this.editedCurrentBean instanceof AbstractDescriptorBean) {
         bn = ((AbstractDescriptorBean)this.editedCurrentBean)._getQualifiedName();
      } else {
         bn = String.valueOf(this.editedCurrentBean);
      }

      return textFormatter.getRemovedEditedBeanDiffConflictResolve(bn);
   }

   public String toString() {
      String bn;
      if (this.editedCurrentBean instanceof AbstractDescriptorBean) {
         bn = ((AbstractDescriptorBean)this.editedCurrentBean)._getQualifiedName();
      } else {
         bn = String.valueOf(this.editedCurrentBean);
      }

      return textFormatter.getRemovedEditedBeanDiffConflictString(bn);
   }
}
