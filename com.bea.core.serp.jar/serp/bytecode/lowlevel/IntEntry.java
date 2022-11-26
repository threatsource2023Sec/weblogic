package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;
import serp.util.Numbers;

public class IntEntry extends Entry implements ConstantEntry {
   private int _value = -1;

   public IntEntry() {
   }

   public IntEntry(int value) {
      this._value = value;
   }

   public int getType() {
      return 3;
   }

   public int getValue() {
      return this._value;
   }

   public void setValue(int value) {
      Object key = this.beforeModify();
      this._value = value;
      this.afterModify(key);
   }

   public Object getConstant() {
      return Numbers.valueOf(this.getValue());
   }

   public void setConstant(Object value) {
      this.setValue(((Number)value).intValue());
   }

   protected void readData(DataInput in) throws IOException {
      this._value = in.readInt();
   }

   protected void writeData(DataOutput out) throws IOException {
      out.writeInt(this._value);
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterIntEntry(this);
      visit.exitIntEntry(this);
   }
}
