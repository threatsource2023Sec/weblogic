package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.lowlevel.ConstantEntry;
import serp.bytecode.lowlevel.DoubleEntry;
import serp.bytecode.lowlevel.FloatEntry;
import serp.bytecode.lowlevel.IntEntry;
import serp.bytecode.lowlevel.LongEntry;
import serp.bytecode.lowlevel.StringEntry;
import serp.bytecode.visitor.BCVisitor;

public class ConstantValue extends Attribute {
   int _valueIndex = 0;

   ConstantValue(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   int getLength() {
      return 2;
   }

   public BCField getField() {
      return (BCField)this.getOwner();
   }

   public int getValueIndex() {
      return this._valueIndex;
   }

   public void setValueIndex(int valueIndex) {
      this._valueIndex = valueIndex;
   }

   public String getTypeName() {
      Class type = this.getType();
      return type == null ? null : type.getName();
   }

   public Class getType() {
      Object value = this.getValue();
      if (value == null) {
         return null;
      } else {
         Class type = value.getClass();
         if (type == Integer.class) {
            return Integer.TYPE;
         } else if (type == Float.class) {
            return Float.TYPE;
         } else if (type == Double.class) {
            return Double.TYPE;
         } else {
            return type == Long.class ? Long.TYPE : String.class;
         }
      }
   }

   public BCClass getTypeBC() {
      return this.getProject().loadClass(this.getType());
   }

   public Object getValue() {
      return this._valueIndex <= 0 ? null : ((ConstantEntry)this.getPool().getEntry(this._valueIndex)).getConstant();
   }

   public void setValue(Object value) {
      Class type = value.getClass();
      if (type == Boolean.class) {
         this.setIntValue((Boolean)value ? 1 : 0);
      } else if (type == Character.class) {
         this.setIntValue((Character)value);
      } else if (type != Byte.class && type != Integer.class && type != Short.class) {
         if (type == Float.class) {
            this.setFloatValue(((Number)value).floatValue());
         } else if (type == Double.class) {
            this.setDoubleValue(((Number)value).doubleValue());
         } else if (type == Long.class) {
            this.setLongValue(((Number)value).longValue());
         } else {
            this.setStringValue(value.toString());
         }
      } else {
         this.setIntValue(((Number)value).intValue());
      }

   }

   public int getIntValue() {
      return this.getValueIndex() <= 0 ? 0 : ((IntEntry)this.getPool().getEntry(this.getValueIndex())).getValue();
   }

   public void setIntValue(int value) {
      this.setValueIndex(this.getPool().findIntEntry(value, true));
   }

   public float getFloatValue() {
      return this.getValueIndex() <= 0 ? 0.0F : ((FloatEntry)this.getPool().getEntry(this.getValueIndex())).getValue();
   }

   public void setFloatValue(float value) {
      this.setValueIndex(this.getPool().findFloatEntry(value, true));
   }

   public double getDoubleValue() {
      return this.getValueIndex() <= 0 ? 0.0 : ((DoubleEntry)this.getPool().getEntry(this.getValueIndex())).getValue();
   }

   public void setDoubleValue(double value) {
      this.setValueIndex(this.getPool().findDoubleEntry(value, true));
   }

   public long getLongValue() {
      return this.getValueIndex() <= 0 ? 0L : ((LongEntry)this.getPool().getEntry(this.getValueIndex())).getValue();
   }

   public void setLongValue(long value) {
      this.setValueIndex(this.getPool().findLongEntry(value, true));
   }

   public String getStringValue() {
      return this.getValueIndex() <= 0 ? null : ((StringEntry)this.getPool().getEntry(this.getValueIndex())).getStringEntry().getValue();
   }

   public void setStringValue(String value) {
      this.setValueIndex(this.getPool().findStringEntry(value, true));
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterConstantValue(this);
      visit.exitConstantValue(this);
   }

   void read(Attribute other) {
      this.setValue(((ConstantValue)other).getValue());
   }

   void read(DataInput in, int length) throws IOException {
      this.setValueIndex(in.readUnsignedShort());
   }

   void write(DataOutput out, int length) throws IOException {
      out.writeShort(this.getValueIndex());
   }
}
