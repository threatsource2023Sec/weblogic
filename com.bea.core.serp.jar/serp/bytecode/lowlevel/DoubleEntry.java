package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class DoubleEntry extends Entry implements ConstantEntry {
   private double _value = 0.0;

   public DoubleEntry() {
   }

   public DoubleEntry(double value) {
      this._value = value;
   }

   public boolean isWide() {
      return true;
   }

   public int getType() {
      return 6;
   }

   public double getValue() {
      return this._value;
   }

   public void setValue(double value) {
      Object key = this.beforeModify();
      this._value = value;
      this.afterModify(key);
   }

   public Object getConstant() {
      return new Double(this.getValue());
   }

   public void setConstant(Object value) {
      this.setValue(((Number)value).doubleValue());
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterDoubleEntry(this);
      visit.exitDoubleEntry(this);
   }

   void readData(DataInput in) throws IOException {
      this._value = in.readDouble();
   }

   void writeData(DataOutput out) throws IOException {
      out.writeDouble(this._value);
   }
}
