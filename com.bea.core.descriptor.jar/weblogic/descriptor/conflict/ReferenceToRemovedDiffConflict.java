package weblogic.descriptor.conflict;

import java.util.Iterator;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.BeanDiff;

public class ReferenceToRemovedDiffConflict extends DiffConflict {
   private final String propertyName;
   private final AbstractDescriptorBean removedBean;
   private final BeanUpdateEvent orig2EditBeanUpdate;

   public ReferenceToRemovedDiffConflict(BeanUpdateEvent orig2EditBeanUpdate, String propertyName, AbstractDescriptorBean removedBean) {
      super((BeanUpdateEvent)null, (BeanUpdateEvent.PropertyUpdate)null, orig2EditBeanUpdate.getProposedBean());
      this.propertyName = propertyName;
      this.removedBean = removedBean;
      this.orig2EditBeanUpdate = orig2EditBeanUpdate;
   }

   public ConflictDescriptorDiff resolve(ConflictDescriptorDiff orig2CurrDiff) {
      Iterator iterator = orig2CurrDiff.iterator();

      BeanDiff beanUpdateEvent;
      for(beanUpdateEvent = null; iterator.hasNext(); beanUpdateEvent = null) {
         beanUpdateEvent = (BeanDiff)iterator.next();
         if (beanUpdateEvent.getSourceBean().equals(this.editBean)) {
            break;
         }
      }

      if (beanUpdateEvent == null) {
         beanUpdateEvent = new BeanDiff(this.orig2EditBeanUpdate.getProposedBean(), this.orig2EditBeanUpdate.getSourceBean(), 0, 0);
         beanUpdateEvent.recordChange(this.propertyName, true);
         orig2CurrDiff.addBeanDiff(beanUpdateEvent);
      } else {
         beanUpdateEvent = new BeanDiff(beanUpdateEvent);
         beanUpdateEvent.recordChange(this.propertyName, true);
         orig2CurrDiff.addBeanDiff(beanUpdateEvent);
      }

      return orig2CurrDiff;
   }

   public String getResolveDescription() {
      return textFormatter.getReferenceToRemovedDiffConflictResolve(this.getBeanFullName(), this.propertyName, this.removedBean._getQualifiedName());
   }

   public String toString() {
      return textFormatter.getReferenceToRemovedDiffConflictString(this.getBeanFullName(), this.propertyName, this.removedBean._getQualifiedName());
   }
}
