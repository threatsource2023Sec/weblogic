package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.lowlevel.ConstantEntry;
import serp.bytecode.lowlevel.Entry;
import serp.bytecode.visitor.BCVisitor;
import serp.util.Numbers;

public class ConstantInstruction extends TypedInstruction {
   private int _arg = -1;

   ConstantInstruction(Code owner) {
      super(owner);
   }

   ConstantInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   int getLength() {
      switch (this.getOpcode()) {
         case 16:
         case 18:
            return super.getLength() + 1;
         case 17:
         case 19:
         case 20:
            return super.getLength() + 2;
         default:
            return super.getLength();
      }
   }

   public int getStackChange() {
      String type = this.getTypeName();
      return !Double.TYPE.getName().equals(type) && !Long.TYPE.getName().equals(type) ? 1 : 2;
   }

   public int getLogicalStackChange() {
      return 1;
   }

   public String getTypeName() {
      int opcode = this.getOpcode();
      switch (opcode) {
         case 0:
            return null;
         case 1:
            return Object.class.getName();
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 16:
         case 17:
            return Integer.TYPE.getName();
         case 9:
         case 10:
            return Long.TYPE.getName();
         case 11:
         case 12:
         case 13:
            return Float.TYPE.getName();
         case 14:
         case 15:
            return Double.TYPE.getName();
         default:
            Entry entry = this.getPool().getEntry(this._arg);
            switch (entry.getType()) {
               case 1:
               case 8:
                  return String.class.getName();
               case 2:
               default:
                  return null;
               case 3:
                  return Integer.TYPE.getName();
               case 4:
                  return Float.TYPE.getName();
               case 5:
                  return Long.TYPE.getName();
               case 6:
                  return Double.TYPE.getName();
               case 7:
                  return Class.class.getName();
            }
      }
   }

   public TypedInstruction setType(String type) {
      throw new UnsupportedOperationException("Use setValue");
   }

   public Object getValue() {
      int opcode = this.getOpcode();
      switch (opcode) {
         case 0:
         case 1:
            return null;
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
            return Numbers.valueOf(opcode - 3);
         case 9:
         case 10:
            return Numbers.valueOf((long)(opcode - 9));
         case 11:
         case 12:
         case 13:
            return new Float((float)(opcode - 11));
         case 14:
         case 15:
            return new Double((double)(opcode - 14));
         case 16:
         case 17:
            return Numbers.valueOf(this._arg);
         default:
            Entry entry = this.getPool().getEntry(this._arg);
            Object val = ((ConstantEntry)entry).getConstant();
            return entry.getType() == 7 ? this.getProject().getNameCache().getExternalForm((String)val, false) : val;
      }
   }

   public ConstantInstruction setValue(Object value) {
      boolean clsName = false;
      if (value instanceof Boolean) {
         value = Numbers.valueOf((Boolean)value ? 1 : 0);
      } else if (value instanceof Character) {
         value = Numbers.valueOf((Character)value);
      } else if (value instanceof Byte) {
         value = Numbers.valueOf(((Byte)value).intValue());
      } else if (value instanceof Short) {
         value = Numbers.valueOf(((Short)value).intValue());
      } else if (value instanceof Class) {
         value = ((Class)value).getName();
         clsName = true;
      } else if (value instanceof BCClass) {
         value = ((BCClass)value).getName();
         clsName = true;
      } else if (value != null && !(value instanceof Number) && !(value instanceof String)) {
         throw new IllegalArgumentException("value = " + value);
      }

      this.calculateOpcode(value, clsName, false);
      return this;
   }

   public String getStringValue() {
      return (String)this.getValue();
   }

   public int getIntValue() {
      Object value = this.getValue();
      return value == null ? 0 : ((Number)value).intValue();
   }

   public long getLongValue() {
      Object value = this.getValue();
      return value == null ? 0L : ((Number)value).longValue();
   }

   public float getFloatValue() {
      Object value = this.getValue();
      return value == null ? 0.0F : ((Number)value).floatValue();
   }

   public double getDoubleValue() {
      Object value = this.getValue();
      return value == null ? 0.0 : ((Number)value).doubleValue();
   }

   public String getClassNameValue() {
      return (String)this.getValue();
   }

   public ConstantInstruction setNull() {
      this.calculateOpcode((Object)null, false, false);
      return this;
   }

   public ConstantInstruction setValue(String value) {
      return this.setValue(value, false);
   }

   public ConstantInstruction setValue(String value, boolean clsName) {
      this.calculateOpcode(value, clsName, false);
      return this;
   }

   public ConstantInstruction setValue(Class value) {
      if (value == null) {
         return this.setNull();
      } else {
         this.calculateOpcode(value.getName(), true, false);
         return this;
      }
   }

   public ConstantInstruction setValue(BCClass value) {
      if (value == null) {
         return this.setNull();
      } else {
         this.calculateOpcode(value.getName(), true, false);
         return this;
      }
   }

   public ConstantInstruction setValue(int value) {
      this.calculateOpcode(Numbers.valueOf(value), false, false);
      return this;
   }

   public ConstantInstruction setValue(long value) {
      this.calculateOpcode(Numbers.valueOf(value), false, false);
      return this;
   }

   public ConstantInstruction setValue(float value) {
      this.calculateOpcode(new Float(value), false, false);
      return this;
   }

   public ConstantInstruction setValue(double value) {
      this.calculateOpcode(new Double(value), false, false);
      return this;
   }

   public ConstantInstruction setValue(boolean value) {
      return this.setValue(value ? 1 : 0);
   }

   public ConstantInstruction setValue(short value) {
      return this.setValue((int)value);
   }

   public ConstantInstruction setValue(char value) {
      return this.setValue((int)value);
   }

   public boolean equalsInstruction(Instruction other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof ConstantInstruction)) {
         return false;
      } else {
         ConstantInstruction ci = (ConstantInstruction)other;
         Object value = this.getValue();
         Object otherValue = ci.getValue();
         if (value != null && otherValue != null) {
            if (this.getTypeName() != null && ci.getTypeName() != null) {
               return value.equals(otherValue) && this.getTypeName().equals(ci.getTypeName());
            } else {
               return true;
            }
         } else {
            return true;
         }
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterConstantInstruction(this);
      visit.exitConstantInstruction(this);
   }

   void read(Instruction orig) {
      super.read(orig);
      ConstantInstruction ci = (ConstantInstruction)orig;
      this.calculateOpcode(ci.getValue(), Class.class.getName().equals(ci.getTypeName()), ci.getOpcode() == 19);
   }

   void read(DataInput in) throws IOException {
      super.read(in);
      switch (this.getOpcode()) {
         case 16:
            this._arg = in.readByte();
            break;
         case 17:
            this._arg = in.readShort();
            break;
         case 18:
            this._arg = in.readUnsignedByte();
            break;
         case 19:
         case 20:
            this._arg = in.readUnsignedShort();
      }

   }

   void write(DataOutput out) throws IOException {
      super.write(out);
      switch (this.getOpcode()) {
         case 16:
         case 18:
            out.writeByte(this._arg);
            break;
         case 17:
         case 19:
         case 20:
            out.writeShort(this._arg);
      }

   }

   private void calculateOpcode(Object value, boolean clsName, boolean wide) {
      int len = this.getLength();
      this._arg = -1;
      if (value == null) {
         this.setOpcode(1);
      } else if (clsName) {
         String name = this.getProject().getNameCache().getInternalForm((String)value, false);
         this._arg = this.getPool().findClassEntry(name, true);
         this.setOpcode(19);
         this.ensureBytecodeVersion();
      } else if (value instanceof Float) {
         float floatVal = (Float)value;
         if (floatVal != 0.0F && floatVal != 1.0F && floatVal != 2.0F) {
            this._arg = this.getPool().findFloatEntry(floatVal, true);
            this.setOpcode(this._arg <= 255 && !wide ? 18 : 19);
         } else {
            this.setOpcode(11 + (int)floatVal);
         }
      } else if (value instanceof Long) {
         long longVal = (Long)value;
         if (longVal != 0L && longVal != 1L) {
            this._arg = this.getPool().findLongEntry(longVal, true);
            this.setOpcode(20);
         } else {
            this.setOpcode(9 + (int)longVal);
         }
      } else if (value instanceof Double) {
         double doubleVal = (Double)value;
         if (doubleVal != 0.0 && doubleVal != 1.0) {
            this._arg = this.getPool().findDoubleEntry(doubleVal, true);
            this.setOpcode(20);
         } else {
            this.setOpcode(14 + (int)doubleVal);
         }
      } else if (value instanceof Integer) {
         int intVal = (Integer)value;
         if (intVal >= -1 && intVal <= 5) {
            this.setOpcode(3 + intVal);
         } else if (intVal >= -128 && intVal < 128) {
            this.setOpcode(16);
            this._arg = intVal;
         } else if (intVal >= -32768 && intVal < 32768) {
            this.setOpcode(17);
            this._arg = intVal;
         } else {
            this._arg = this.getPool().findIntEntry(intVal, true);
            this.setOpcode(this._arg <= 255 && !wide ? 18 : 19);
         }
      } else {
         if (!(value instanceof String)) {
            throw new IllegalArgumentException(String.valueOf(value));
         }

         this._arg = this.getPool().findStringEntry((String)value, true);
         this.setOpcode(this._arg <= 255 && !wide ? 18 : 19);
      }

      if (len != this.getLength()) {
         this.invalidateByteIndexes();
      }

   }

   private void ensureBytecodeVersion() {
      BCClass bc = this.getCode().getMethod().getDeclarer();
      if (bc.getMajorVersion() < 49) {
         bc.setMajorVersion(49);
         bc.setMinorVersion(0);
      }

   }
}
