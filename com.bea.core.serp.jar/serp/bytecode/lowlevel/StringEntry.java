package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class StringEntry extends Entry implements ConstantEntry {
   private int _stringIndex = -1;

   public StringEntry() {
   }

   public StringEntry(int stringIndex) {
      this._stringIndex = stringIndex;
   }

   public int getType() {
      return 8;
   }

   public int getStringIndex() {
      return this._stringIndex;
   }

   public void setStringIndex(int stringIndex) {
      Object key = this.beforeModify();
      this._stringIndex = stringIndex;
      this.afterModify(key);
   }

   public UTF8Entry getStringEntry() {
      return (UTF8Entry)this.getPool().getEntry(this._stringIndex);
   }

   public Object getConstant() {
      return this.getStringEntry().getValue();
   }

   public void setConstant(Object value) {
      this.getStringEntry().setConstant(value);
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterStringEntry(this);
      visit.exitStringEntry(this);
   }

   void readData(DataInput in) throws IOException {
      this._stringIndex = in.readUnsignedShort();
   }

   void writeData(DataOutput out) throws IOException {
      out.writeShort(this._stringIndex);
   }
}
