package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class UTF8Entry extends Entry implements ConstantEntry {
   private String _value = "";

   public UTF8Entry() {
   }

   public UTF8Entry(String value) {
      this._value = value;
   }

   public int getType() {
      return 1;
   }

   public String getValue() {
      return this._value;
   }

   public void setValue(String value) {
      if (value == null) {
         throw new NullPointerException("value = null");
      } else {
         Object key = this.beforeModify();
         this._value = value;
         this.afterModify(key);
      }
   }

   public Object getConstant() {
      return this.getValue();
   }

   public void setConstant(Object value) {
      this.setValue((String)value);
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterUTF8Entry(this);
      visit.exitUTF8Entry(this);
   }

   void readData(DataInput in) throws IOException {
      this._value = in.readUTF();
   }

   void writeData(DataOutput out) throws IOException {
      out.writeUTF(this._value);
   }
}
