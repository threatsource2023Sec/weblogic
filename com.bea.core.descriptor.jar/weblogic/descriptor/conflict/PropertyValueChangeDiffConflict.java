package weblogic.descriptor.conflict;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;

public class PropertyValueChangeDiffConflict extends DiffConflict {
   private final String propertyName;
   private final Object originalValue;
   private final Object currentValue;
   private final Object editValue;

   public PropertyValueChangeDiffConflict(BeanUpdateEvent orig2CurrBeanUpdate, BeanUpdateEvent.PropertyUpdate orig2CurrPropertyUpdate, DescriptorBean editBean, String propertyName, Object originalValue, Object currentValue, Object editValue) {
      super(orig2CurrBeanUpdate, orig2CurrPropertyUpdate, editBean);
      this.propertyName = propertyName;
      this.originalValue = originalValue;
      this.currentValue = currentValue;
      this.editValue = editValue;
   }

   public ConflictDescriptorDiff resolve(ConflictDescriptorDiff orig2CurrDiff) {
      return this.removePropertyUpdateFromOrig2CurrDiff(orig2CurrDiff);
   }

   public String getResolveDescription() {
      return textFormatter.getPropertyValueChangeDiffConflictResolve(this.getBeanFullName(), this.propertyName);
   }

   public String toString() {
      return textFormatter.getPropertyValueChangeDiffConflictString(this.getBeanFullName(), this.propertyName, String.valueOf(this.originalValue), String.valueOf(this.editValue), String.valueOf(this.currentValue));
   }
}
