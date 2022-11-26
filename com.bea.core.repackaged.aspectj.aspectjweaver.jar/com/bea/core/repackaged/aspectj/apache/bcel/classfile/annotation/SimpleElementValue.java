package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantDouble;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantFloat;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantInteger;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantLong;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantUtf8;
import java.io.DataOutputStream;
import java.io.IOException;

public class SimpleElementValue extends ElementValue {
   private int idx;

   protected SimpleElementValue(int type, int idx, ConstantPool cpGen) {
      super(type, cpGen);
      this.idx = idx;
   }

   public SimpleElementValue(int type, ConstantPool cpGen, int value) {
      super(type, cpGen);
      this.idx = cpGen.addInteger(value);
   }

   public SimpleElementValue(int type, ConstantPool cpGen, long value) {
      super(type, cpGen);
      this.idx = cpGen.addLong(value);
   }

   public SimpleElementValue(int type, ConstantPool cpGen, double value) {
      super(type, cpGen);
      this.idx = cpGen.addDouble(value);
   }

   public SimpleElementValue(int type, ConstantPool cpGen, float value) {
      super(type, cpGen);
      this.idx = cpGen.addFloat(value);
   }

   public SimpleElementValue(int type, ConstantPool cpGen, short value) {
      super(type, cpGen);
      this.idx = cpGen.addInteger(value);
   }

   public SimpleElementValue(int type, ConstantPool cpGen, byte value) {
      super(type, cpGen);
      this.idx = cpGen.addInteger(value);
   }

   public SimpleElementValue(int type, ConstantPool cpGen, char value) {
      super(type, cpGen);
      this.idx = cpGen.addInteger(value);
   }

   public SimpleElementValue(int type, ConstantPool cpGen, boolean value) {
      super(type, cpGen);
      if (value) {
         this.idx = cpGen.addInteger(1);
      } else {
         this.idx = cpGen.addInteger(0);
      }

   }

   public SimpleElementValue(int type, ConstantPool cpGen, String value) {
      super(type, cpGen);
      this.idx = cpGen.addUtf8(value);
   }

   public byte getValueByte() {
      if (this.type != 66) {
         throw new RuntimeException("Dont call getValueByte() on a non BYTE ElementValue");
      } else {
         ConstantInteger c = (ConstantInteger)this.cpool.getConstant(this.idx, (byte)3);
         return (byte)c.getIntValue();
      }
   }

   public char getValueChar() {
      if (this.type != 67) {
         throw new RuntimeException("Dont call getValueChar() on a non CHAR ElementValue");
      } else {
         ConstantInteger c = (ConstantInteger)this.cpool.getConstant(this.idx, (byte)3);
         return (char)c.getIntValue();
      }
   }

   public long getValueLong() {
      if (this.type != 74) {
         throw new RuntimeException("Dont call getValueLong() on a non LONG ElementValue");
      } else {
         ConstantLong j = (ConstantLong)this.cpool.getConstant(this.idx);
         return j.getValue();
      }
   }

   public float getValueFloat() {
      if (this.type != 70) {
         throw new RuntimeException("Dont call getValueFloat() on a non FLOAT ElementValue");
      } else {
         ConstantFloat f = (ConstantFloat)this.cpool.getConstant(this.idx);
         return f.getValue();
      }
   }

   public double getValueDouble() {
      if (this.type != 68) {
         throw new RuntimeException("Dont call getValueDouble() on a non DOUBLE ElementValue");
      } else {
         ConstantDouble d = (ConstantDouble)this.cpool.getConstant(this.idx);
         return d.getValue();
      }
   }

   public boolean getValueBoolean() {
      if (this.type != 90) {
         throw new RuntimeException("Dont call getValueBoolean() on a non BOOLEAN ElementValue");
      } else {
         ConstantInteger bo = (ConstantInteger)this.cpool.getConstant(this.idx);
         return bo.getValue() != 0;
      }
   }

   public short getValueShort() {
      if (this.type != 83) {
         throw new RuntimeException("Dont call getValueShort() on a non SHORT ElementValue");
      } else {
         ConstantInteger s = (ConstantInteger)this.cpool.getConstant(this.idx);
         return (short)s.getIntValue();
      }
   }

   public SimpleElementValue(SimpleElementValue value, ConstantPool cpool, boolean copyPoolEntries) {
      super(value.getElementValueType(), cpool);
      if (!copyPoolEntries) {
         this.idx = value.getIndex();
      } else {
         switch (value.getElementValueType()) {
            case 66:
               this.idx = cpool.addInteger(value.getValueByte());
               break;
            case 67:
               this.idx = cpool.addInteger(value.getValueChar());
               break;
            case 68:
               this.idx = cpool.addDouble(value.getValueDouble());
               break;
            case 70:
               this.idx = cpool.addFloat(value.getValueFloat());
               break;
            case 73:
               this.idx = cpool.addInteger(value.getValueInt());
               break;
            case 74:
               this.idx = cpool.addLong(value.getValueLong());
               break;
            case 83:
               this.idx = cpool.addInteger(value.getValueShort());
               break;
            case 90:
               if (value.getValueBoolean()) {
                  this.idx = cpool.addInteger(1);
               } else {
                  this.idx = cpool.addInteger(0);
               }
               break;
            case 115:
               this.idx = cpool.addUtf8(value.getValueString());
               break;
            default:
               throw new RuntimeException("SimpleElementValueGen class does not know how to copy this type " + this.type);
         }
      }

   }

   public int getIndex() {
      return this.idx;
   }

   public String getValueString() {
      if (this.type != 115) {
         throw new RuntimeException("Dont call getValueString() on a non STRING ElementValue");
      } else {
         ConstantUtf8 c = (ConstantUtf8)this.cpool.getConstant(this.idx);
         return c.getValue();
      }
   }

   public int getValueInt() {
      if (this.type != 73) {
         throw new RuntimeException("Dont call getValueString() on a non STRING ElementValue");
      } else {
         ConstantInteger c = (ConstantInteger)this.cpool.getConstant(this.idx);
         return c.getValue();
      }
   }

   public String stringifyValue() {
      switch (this.type) {
         case 66:
            ConstantInteger b = (ConstantInteger)this.cpool.getConstant(this.idx);
            return Integer.toString(b.getValue());
         case 67:
            ConstantInteger ch = (ConstantInteger)this.cpool.getConstant(this.idx);
            return (new Character((char)ch.getIntValue())).toString();
         case 68:
            ConstantDouble d = (ConstantDouble)this.cpool.getConstant(this.idx);
            return d.getValue().toString();
         case 70:
            ConstantFloat f = (ConstantFloat)this.cpool.getConstant(this.idx);
            return Float.toString(f.getValue());
         case 73:
            ConstantInteger c = (ConstantInteger)this.cpool.getConstant(this.idx);
            return Integer.toString(c.getValue());
         case 74:
            ConstantLong j = (ConstantLong)this.cpool.getConstant(this.idx);
            return Long.toString(j.getValue());
         case 83:
            ConstantInteger s = (ConstantInteger)this.cpool.getConstant(this.idx);
            return Integer.toString(s.getValue());
         case 90:
            ConstantInteger bo = (ConstantInteger)this.cpool.getConstant(this.idx);
            if (bo.getValue() == 0) {
               return "false";
            }

            return "true";
         case 115:
            ConstantUtf8 cu8 = (ConstantUtf8)this.cpool.getConstant(this.idx);
            return cu8.getValue();
         default:
            throw new RuntimeException("SimpleElementValueGen class does not know how to stringify type " + this.type);
      }
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      switch (this.type) {
         case 66:
            ConstantInteger b = (ConstantInteger)this.cpool.getConstant(this.idx);
            s.append("(byte)").append(Integer.toString(b.getValue()));
            break;
         case 67:
            ConstantInteger ch = (ConstantInteger)this.cpool.getConstant(this.idx);
            s.append("(char)").append((new Character((char)ch.getIntValue())).toString());
            break;
         case 68:
            ConstantDouble d = (ConstantDouble)this.cpool.getConstant(this.idx);
            s.append("(double)").append(d.getValue().toString());
            break;
         case 70:
            ConstantFloat f = (ConstantFloat)this.cpool.getConstant(this.idx);
            s.append("(float)").append(Float.toString(f.getValue()));
            break;
         case 73:
            ConstantInteger c = (ConstantInteger)this.cpool.getConstant(this.idx);
            s.append("(int)").append(Integer.toString(c.getValue()));
            break;
         case 74:
            ConstantLong j = (ConstantLong)this.cpool.getConstant(this.idx);
            s.append("(long)").append(Long.toString(j.getValue()));
            break;
         case 83:
            ConstantInteger ci = (ConstantInteger)this.cpool.getConstant(this.idx);
            s.append("(short)").append(Integer.toString(ci.getValue()));
            break;
         case 90:
            ConstantInteger bo = (ConstantInteger)this.cpool.getConstant(this.idx);
            s.append("(boolean)");
            if (bo.getValue() == 0) {
               s.append("false");
            } else {
               s.append("true");
            }
            break;
         case 115:
            ConstantUtf8 cu8 = (ConstantUtf8)this.cpool.getConstant(this.idx);
            s.append("(string)").append(cu8.getValue());
            break;
         default:
            throw new RuntimeException("SimpleElementValueGen class does not know how to stringify type " + this.type);
      }

      return s.toString();
   }

   public void dump(DataOutputStream dos) throws IOException {
      dos.writeByte(this.type);
      switch (this.type) {
         case 66:
         case 67:
         case 68:
         case 70:
         case 73:
         case 74:
         case 83:
         case 90:
         case 115:
            dos.writeShort(this.idx);
            return;
         default:
            throw new RuntimeException("SimpleElementValueGen doesnt know how to write out type " + this.type);
      }
   }
}
