package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class InvokeDynamicEntry extends Entry {
   private int _bootstrap_method_attr_index;
   private int _name_and_type_index;

   public InvokeDynamicEntry() {
   }

   public InvokeDynamicEntry(int bootstrap_method_attr_index, int nameAndTypeIndex) {
      this._bootstrap_method_attr_index = bootstrap_method_attr_index;
      this._name_and_type_index = nameAndTypeIndex;
   }

   public void acceptVisit(BCVisitor visitor) {
      visitor.enterInvokeDynamicEntry(this);
      visitor.exitInvokeDynamicEntry(this);
   }

   public int getType() {
      return 18;
   }

   void readData(DataInput in) throws IOException {
      this._bootstrap_method_attr_index = in.readUnsignedShort();
      this._name_and_type_index = in.readUnsignedShort();
   }

   void writeData(DataOutput out) throws IOException {
      out.writeShort(this._bootstrap_method_attr_index);
      out.writeShort(this._name_and_type_index);
   }

   public int getBootstrapMethodAttrIndex() {
      return this._bootstrap_method_attr_index;
   }

   public int getNameAndTypeIndex() {
      return this._name_and_type_index;
   }

   public NameAndTypeEntry getNameAndTypeEntry() {
      return (NameAndTypeEntry)this.getPool().getEntry(this._name_and_type_index);
   }
}
