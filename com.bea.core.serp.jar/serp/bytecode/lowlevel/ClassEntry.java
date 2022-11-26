package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class ClassEntry extends Entry implements ConstantEntry {
   private int _nameIndex = 0;

   public ClassEntry() {
   }

   public ClassEntry(int nameIndex) {
      this._nameIndex = nameIndex;
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

   public int getType() {
      return 7;
   }

   public Object getConstant() {
      return this.getNameEntry().getValue();
   }

   public void setConstant(Object value) {
      this.getNameEntry().setConstant(value);
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterClassEntry(this);
      visit.exitClassEntry(this);
   }

   void readData(DataInput in) throws IOException {
      this._nameIndex = in.readUnsignedShort();
   }

   void writeData(DataOutput out) throws IOException {
      out.writeShort(this._nameIndex);
   }
}
