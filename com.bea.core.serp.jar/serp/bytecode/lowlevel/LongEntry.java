package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;
import serp.util.Numbers;

public class LongEntry extends Entry implements ConstantEntry {
   private long _value = 0L;

   public LongEntry() {
   }

   public LongEntry(long value) {
      this._value = value;
   }

   public boolean isWide() {
      return true;
   }

   public int getType() {
      return 5;
   }

   public long getValue() {
      return this._value;
   }

   public void setValue(long value) {
      Object key = this.beforeModify();
      this._value = value;
      this.afterModify(key);
   }

   public Object getConstant() {
      return Numbers.valueOf(this.getValue());
   }

   public void setConstant(Object value) {
      this.setValue(((Number)value).longValue());
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterLongEntry(this);
      visit.exitLongEntry(this);
   }

   void readData(DataInput in) throws IOException {
      this._value = in.readLong();
   }

   void writeData(DataOutput out) throws IOException {
      out.writeLong(this._value);
   }
}
