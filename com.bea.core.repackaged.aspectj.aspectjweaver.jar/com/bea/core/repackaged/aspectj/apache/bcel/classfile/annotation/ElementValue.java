package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ElementValue {
   public static final int STRING = 115;
   public static final int ENUM_CONSTANT = 101;
   public static final int CLASS = 99;
   public static final int ANNOTATION = 64;
   public static final int ARRAY = 91;
   public static final int PRIMITIVE_INT = 73;
   public static final int PRIMITIVE_BYTE = 66;
   public static final int PRIMITIVE_CHAR = 67;
   public static final int PRIMITIVE_DOUBLE = 68;
   public static final int PRIMITIVE_FLOAT = 70;
   public static final int PRIMITIVE_LONG = 74;
   public static final int PRIMITIVE_SHORT = 83;
   public static final int PRIMITIVE_BOOLEAN = 90;
   protected int type;
   protected ConstantPool cpool;

   protected ElementValue(int type, ConstantPool cpool) {
      this.type = type;
      this.cpool = cpool;
   }

   public int getElementValueType() {
      return this.type;
   }

   public abstract String stringifyValue();

   public abstract void dump(DataOutputStream var1) throws IOException;

   public static ElementValue readElementValue(DataInputStream dis, ConstantPool cpGen) throws IOException {
      int type = dis.readUnsignedByte();
      switch (type) {
         case 64:
            return new AnnotationElementValue(64, AnnotationGen.read(dis, cpGen, true), cpGen);
         case 66:
            return new SimpleElementValue(66, dis.readUnsignedShort(), cpGen);
         case 67:
            return new SimpleElementValue(67, dis.readUnsignedShort(), cpGen);
         case 68:
            return new SimpleElementValue(68, dis.readUnsignedShort(), cpGen);
         case 70:
            return new SimpleElementValue(70, dis.readUnsignedShort(), cpGen);
         case 73:
            return new SimpleElementValue(73, dis.readUnsignedShort(), cpGen);
         case 74:
            return new SimpleElementValue(74, dis.readUnsignedShort(), cpGen);
         case 83:
            return new SimpleElementValue(83, dis.readUnsignedShort(), cpGen);
         case 90:
            return new SimpleElementValue(90, dis.readUnsignedShort(), cpGen);
         case 91:
            int numArrayVals = dis.readUnsignedShort();
            ElementValue[] evalues = new ElementValue[numArrayVals];

            for(int j = 0; j < numArrayVals; ++j) {
               evalues[j] = readElementValue(dis, cpGen);
            }

            return new ArrayElementValue(91, evalues, cpGen);
         case 99:
            return new ClassElementValue(dis.readUnsignedShort(), cpGen);
         case 101:
            return new EnumElementValue(dis.readUnsignedShort(), dis.readUnsignedShort(), cpGen);
         case 115:
            return new SimpleElementValue(115, dis.readUnsignedShort(), cpGen);
         default:
            throw new RuntimeException("Unexpected element value kind in annotation: " + type);
      }
   }

   protected ConstantPool getConstantPool() {
      return this.cpool;
   }

   public static ElementValue copy(ElementValue value, ConstantPool cpool, boolean copyPoolEntries) {
      switch (value.getElementValueType()) {
         case 64:
            return new AnnotationElementValue((AnnotationElementValue)value, cpool, copyPoolEntries);
         case 66:
         case 67:
         case 68:
         case 70:
         case 73:
         case 74:
         case 83:
         case 90:
         case 115:
            return new SimpleElementValue((SimpleElementValue)value, cpool, copyPoolEntries);
         case 91:
            return new ArrayElementValue((ArrayElementValue)value, cpool, copyPoolEntries);
         case 99:
            return new ClassElementValue((ClassElementValue)value, cpool, copyPoolEntries);
         case 101:
            return new EnumElementValue((EnumElementValue)value, cpool, copyPoolEntries);
         default:
            throw new RuntimeException("Not implemented yet! (" + value.getElementValueType() + ")");
      }
   }
}
