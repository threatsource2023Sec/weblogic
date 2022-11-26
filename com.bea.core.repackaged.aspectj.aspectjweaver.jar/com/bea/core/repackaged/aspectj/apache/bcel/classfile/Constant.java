package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Constant implements Cloneable, Node {
   protected byte tag;

   Constant(byte tag) {
      this.tag = tag;
   }

   public final byte getTag() {
      return this.tag;
   }

   public String toString() {
      return Constants.CONSTANT_NAMES[this.tag] + "[" + this.tag + "]";
   }

   public abstract void accept(ClassVisitor var1);

   public abstract void dump(DataOutputStream var1) throws IOException;

   public abstract Object getValue();

   public Constant copy() {
      try {
         return (Constant)super.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   static final Constant readConstant(DataInputStream dis) throws IOException, ClassFormatException {
      byte b = dis.readByte();
      switch (b) {
         case 1:
            return new ConstantUtf8(dis);
         case 2:
         case 13:
         case 14:
         case 17:
         default:
            throw new ClassFormatException("Invalid byte tag in constant pool: " + b);
         case 3:
            return new ConstantInteger(dis);
         case 4:
            return new ConstantFloat(dis);
         case 5:
            return new ConstantLong(dis);
         case 6:
            return new ConstantDouble(dis);
         case 7:
            return new ConstantClass(dis);
         case 8:
            return new ConstantString(dis);
         case 9:
            return new ConstantFieldref(dis);
         case 10:
            return new ConstantMethodref(dis);
         case 11:
            return new ConstantInterfaceMethodref(dis);
         case 12:
            return new ConstantNameAndType(dis);
         case 15:
            return new ConstantMethodHandle(dis);
         case 16:
            return new ConstantMethodType(dis);
         case 18:
            return new ConstantInvokeDynamic(dis);
      }
   }
}
