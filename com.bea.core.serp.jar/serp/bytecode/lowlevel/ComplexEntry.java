package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class ComplexEntry extends Entry {
   private int _classIndex = 0;
   private int _nameAndTypeIndex = 0;

   public ComplexEntry() {
   }

   public ComplexEntry(int classIndex, int nameAndTypeIndex) {
      this._classIndex = classIndex;
      this._nameAndTypeIndex = nameAndTypeIndex;
   }

   public int getClassIndex() {
      return this._classIndex;
   }

   public void setClassIndex(int classIndex) {
      Object key = this.beforeModify();
      this._classIndex = classIndex;
      this.afterModify(key);
   }

   public ClassEntry getClassEntry() {
      return (ClassEntry)this.getPool().getEntry(this._classIndex);
   }

   public int getNameAndTypeIndex() {
      return this._nameAndTypeIndex;
   }

   public void setNameAndTypeIndex(int nameAndTypeIndex) {
      Object key = this.beforeModify();
      this._nameAndTypeIndex = nameAndTypeIndex;
      this.afterModify(key);
   }

   public NameAndTypeEntry getNameAndTypeEntry() {
      return (NameAndTypeEntry)this.getPool().getEntry(this._nameAndTypeIndex);
   }

   void readData(DataInput in) throws IOException {
      this._classIndex = in.readUnsignedShort();
      this._nameAndTypeIndex = in.readUnsignedShort();
   }

   void writeData(DataOutput out) throws IOException {
      out.writeShort(this._classIndex);
      out.writeShort(this._nameAndTypeIndex);
   }
}
