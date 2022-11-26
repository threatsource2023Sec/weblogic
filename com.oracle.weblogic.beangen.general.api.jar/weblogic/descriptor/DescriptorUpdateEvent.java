package weblogic.descriptor;

import java.util.EventObject;

public class DescriptorUpdateEvent extends EventObject {
   private final Descriptor proposed;
   private final DescriptorDiff diff;
   private final int updateID;

   public DescriptorUpdateEvent(Descriptor source, Descriptor proposed, int updateID, DescriptorDiff diff) {
      super(source);
      this.proposed = proposed;
      this.diff = diff;
      this.updateID = updateID;
   }

   public int getUpdateID() {
      return this.updateID;
   }

   public Object getSource() {
      return super.getSource();
   }

   public Descriptor getSourceDescriptor() {
      return (Descriptor)this.getSource();
   }

   public Descriptor getProposedDescriptor() {
      return this.proposed;
   }

   public DescriptorDiff getDiff() {
      return this.diff;
   }
}
