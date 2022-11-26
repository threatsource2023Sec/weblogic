package weblogic.descriptor.conflict;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;

public class ReferenceToRemoveInAddedDiffConflict extends DiffConflict {
   private final boolean addedInEditTree;
   private final AbstractDescriptorBean addedBean;
   private final AbstractDescriptorBean rootOfRemovedSubTree;
   private final AbstractDescriptorBean beanWithReference;
   private final DescriptorBean referencedRemovedBean;
   private final String propertyName;

   public ReferenceToRemoveInAddedDiffConflict(boolean addedInEditTree, AbstractDescriptorBean addedBean, AbstractDescriptorBean rootOfRemovedSubTree, AbstractDescriptorBean beanWithReference, DescriptorBean referencedRemovedBean, String propertyName) {
      super((BeanUpdateEvent)null, (BeanUpdateEvent.PropertyUpdate)null, addedBean);
      this.addedInEditTree = addedInEditTree;
      this.addedBean = addedBean;
      this.rootOfRemovedSubTree = rootOfRemovedSubTree;
      this.beanWithReference = beanWithReference;
      this.referencedRemovedBean = referencedRemovedBean;
      this.propertyName = propertyName;
   }

   public String toString() {
      String referencedRemovedBeanName;
      if (this.referencedRemovedBean instanceof AbstractDescriptorBean) {
         referencedRemovedBeanName = ((AbstractDescriptorBean)this.referencedRemovedBean)._getQualifiedName();
      } else {
         referencedRemovedBeanName = String.valueOf(this.referencedRemovedBean);
      }

      return this.addedInEditTree ? textFormatter.getReferenceToRemoveInAddedDiffConflictStringA(this.beanWithReference._getQualifiedName(), this.propertyName, referencedRemovedBeanName) : textFormatter.getReferenceToRemoveInAddedDiffConflictStringB(this.beanWithReference._getQualifiedName(), this.propertyName, referencedRemovedBeanName);
   }

   public ConflictDescriptorDiff resolve(ConflictDescriptorDiff orig2CurrDiff) {
      orig2CurrDiff.addResolveUpdateEvent(new ConflictDescriptorDiff.RemoveReferenceUpdateEvent() {
         public void apply() {
            ReferenceToRemoveInAddedDiffConflict.this.beanWithReference.unSet(ReferenceToRemoveInAddedDiffConflict.this.propertyName);
         }

         public String getModifiedBeanName() {
            return ReferenceToRemoveInAddedDiffConflict.this.beanWithReference._getQualifiedName();
         }

         public String getUnsetPropertyName() {
            return ReferenceToRemoveInAddedDiffConflict.this.propertyName;
         }

         public String getRemovedBeanName() {
            return ReferenceToRemoveInAddedDiffConflict.this.referencedRemovedBean instanceof AbstractDescriptorBean ? ((AbstractDescriptorBean)ReferenceToRemoveInAddedDiffConflict.this.referencedRemovedBean)._getQualifiedName() : String.valueOf(ReferenceToRemoveInAddedDiffConflict.this.referencedRemovedBean);
         }
      });
      return orig2CurrDiff;
   }

   public String getResolveDescription() {
      return textFormatter.getReferenceToRemoveInAddedDiffConflictResolve(this.beanWithReference._getQualifiedName(), this.propertyName, ((AbstractDescriptorBean)this.referencedRemovedBean)._getQualifiedName());
   }
}
