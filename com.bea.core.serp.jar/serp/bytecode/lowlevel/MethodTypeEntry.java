package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class MethodTypeEntry extends Entry {
   private int _descriptor_index;

   public MethodTypeEntry() {
   }

   public MethodTypeEntry(int _descriptor_index) {
      this._descriptor_index = _descriptor_index;
   }

   public void acceptVisit(BCVisitor visitor) {
      visitor.enterMethodTypeEntry(this);
      visitor.exitMethodTypeEntry(this);
   }

   public int getType() {
      return 16;
   }

   void readData(DataInput in) throws IOException {
      this._descriptor_index = in.readUnsignedShort();
   }

   void writeData(DataOutput out) throws IOException {
      out.writeShort(this._descriptor_index);
   }

   public UTF8Entry getMethodDescriptorEntry() {
      return (UTF8Entry)this.getPool().getEntry(this._descriptor_index);
   }
}
