package weblogic.descriptor.conflict;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;

public class RemoveEditedExternalTreeDiffConflict extends DiffConflict {
   private final String externalTreeName;

   public RemoveEditedExternalTreeDiffConflict(String externalTreeName) {
      super((BeanUpdateEvent)null, (BeanUpdateEvent.PropertyUpdate)null, (DescriptorBean)null);
      this.externalTreeName = externalTreeName;
   }

   public ConflictDescriptorDiff resolve(ConflictDescriptorDiff orig2CurrDiff) {
      return orig2CurrDiff;
   }

   public String getResolveDescription() {
      return textFormatter.getRemoveEditedExternalTreeDiffConflictResolve(this.externalTreeName);
   }

   public String toString() {
      return textFormatter.getRemoveEditedExternalTreeDiffConflictString(this.externalTreeName);
   }
}
