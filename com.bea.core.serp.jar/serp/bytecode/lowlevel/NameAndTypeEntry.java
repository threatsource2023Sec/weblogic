package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class NameAndTypeEntry extends Entry {
   private int _nameIndex = 0;
   private int _descriptorIndex = 0;

   public NameAndTypeEntry() {
   }

   public NameAndTypeEntry(int nameIndex, int descriptorIndex) {
      this._nameIndex = nameIndex;
      this._descriptorIndex = descriptorIndex;
   }

   public int getType() {
      return 12;
   }

   public int getNameIndex() {
      return this._nameIndex;
   }

   public void setNameIndex(int nameIndex) {
      Object key = this.beforeModify();
      this._nameIndex = nameIndex;
      this.afterModify(key);
   }

   public UTF8Entry getNameEntry() {
      return (UTF8Entry)this.getPool().getEntry(this._nameIndex);
   }

   public int getDescriptorIndex() {
      return this._descriptorIndex;
   }

   public void setDescriptorIndex(int descriptorIndex) {
      Object key = this.beforeModify();
      this._descriptorIndex = descriptorIndex;
      this.afterModify(key);
   }

   public UTF8Entry getDescriptorEntry() {
      return (UTF8Entry)this.getPool().getEntry(this._descriptorIndex);
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterNameAndTypeEntry(this);
      visit.exitNameAndTypeEntry(this);
   }

   void readData(DataInput in) throws IOException {
      this._nameIndex = in.readUnsignedShort();
      this._descriptorIndex = in.readUnsignedShort();
   }

   void writeData(DataOutput out) throws IOException {
      out.writeShort(this._nameIndex);
      out.writeShort(this._descriptorIndex);
   }
}
