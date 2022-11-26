package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class UnknownAttribute extends Attribute {
   private byte[] _value = new byte[0];

   UnknownAttribute(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   int getLength() {
      return this._value.length;
   }

   public byte[] getValue() {
      return this._value;
   }

   public void setValue(byte[] value) {
      if (value == null) {
         value = new byte[0];
      }

      this._value = value;
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterUnknownAttribute(this);
      visit.exitUnknownAttribute(this);
   }

   void read(Attribute other) {
      this.setValue(((UnknownAttribute)other).getValue());
   }

   void read(DataInput in, int length) throws IOException {
      this._value = new byte[length];
      in.readFully(this._value);
   }

   void write(DataOutput out, int length) throws IOException {
      out.write(this._value);
   }
}
