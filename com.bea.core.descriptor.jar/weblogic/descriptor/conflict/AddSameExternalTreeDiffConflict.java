package weblogic.descriptor.conflict;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;

public class AddSameExternalTreeDiffConflict extends DiffConflict {
   private final String externalTreeName;

   public AddSameExternalTreeDiffConflict(String externalTreeName) {
      super((BeanUpdateEvent)null, (BeanUpdateEvent.PropertyUpdate)null, (DescriptorBean)null);
      this.externalTreeName = externalTreeName;
   }

   public ConflictDescriptorDiff resolve(ConflictDescriptorDiff orig2CurrDiff) {
      return orig2CurrDiff;
   }

   public String getResolveDescription() {
      return textFormatter.getAddSameExternalTreeDiffConflictResolve();
   }

   public String toString() {
      return textFormatter.getAddSameExternalTreeDiffConflictString(this.externalTreeName);
   }
}
