package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class FloatEntry extends Entry implements ConstantEntry {
   private float _value = 0.0F;

   public FloatEntry() {
   }

   public FloatEntry(float value) {
      this._value = value;
   }

   public int getType() {
      return 4;
   }

   public float getValue() {
      return this._value;
   }

   public void setValue(float value) {
      Object key = this.beforeModify();
      this._value = value;
      this.afterModify(key);
   }

   public Object getConstant() {
      return new Float(this.getValue());
   }

   public void setConstant(Object value) {
      this.setValue(((Number)value).floatValue());
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterFloatEntry(this);
      visit.exitFloatEntry(this);
   }

   void readData(DataInput in) throws IOException {
      this._value = in.readFloat();
   }

   void writeData(DataOutput out) throws IOException {
      out.writeFloat(this._value);
   }
}
