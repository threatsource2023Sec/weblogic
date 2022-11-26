package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeInvisAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeInvisParamAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeInvisTypeAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeVisAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeVisParamAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeVisTypeAnnos;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public abstract class Attribute implements Cloneable, Node, Serializable {
   public static final Attribute[] NoAttributes = new Attribute[0];
   protected byte tag;
   protected int nameIndex;
   protected int length;
   protected ConstantPool cpool;

   protected Attribute(byte tag, int nameIndex, int length, ConstantPool cpool) {
      this.tag = tag;
      this.nameIndex = nameIndex;
      this.length = length;
      this.cpool = cpool;
   }

   public void dump(DataOutputStream file) throws IOException {
      file.writeShort(this.nameIndex);
      file.writeInt(this.length);
   }

   public static final Attribute readAttribute(DataInputStream file, ConstantPool cpool) throws IOException {
      byte tag = -1;
      int idx = file.readUnsignedShort();
      String name = cpool.getConstantUtf8(idx).getValue();
      int len = file.readInt();

      for(byte i = 0; i < 23; ++i) {
         if (name.equals(Constants.ATTRIBUTE_NAMES[i])) {
            tag = i;
            break;
         }
      }

      switch (tag) {
         case -1:
            return new Unknown(idx, len, file, cpool);
         case 0:
            return new SourceFile(idx, len, file, cpool);
         case 1:
            return new ConstantValue(idx, len, file, cpool);
         case 2:
            return new Code(idx, len, file, cpool);
         case 3:
            return new ExceptionTable(idx, len, file, cpool);
         case 4:
            return new LineNumberTable(idx, len, file, cpool);
         case 5:
            return new LocalVariableTable(idx, len, file, cpool);
         case 6:
            return new InnerClasses(idx, len, file, cpool);
         case 7:
            return new Synthetic(idx, len, file, cpool);
         case 8:
            return new Deprecated(idx, len, file, cpool);
         case 9:
         default:
            throw new IllegalStateException();
         case 10:
            return new Signature(idx, len, file, cpool);
         case 11:
            return new StackMap(idx, len, file, cpool);
         case 12:
            return new RuntimeVisAnnos(idx, len, file, cpool);
         case 13:
            return new RuntimeInvisAnnos(idx, len, file, cpool);
         case 14:
            return new RuntimeVisParamAnnos(idx, len, file, cpool);
         case 15:
            return new RuntimeInvisParamAnnos(idx, len, file, cpool);
         case 16:
            return new LocalVariableTypeTable(idx, len, file, cpool);
         case 17:
            return new EnclosingMethod(idx, len, file, cpool);
         case 18:
            return new AnnotationDefault(idx, len, file, cpool);
         case 19:
            return new BootstrapMethods(idx, len, file, cpool);
         case 20:
            return new RuntimeVisTypeAnnos(idx, len, file, cpool);
         case 21:
            return new RuntimeInvisTypeAnnos(idx, len, file, cpool);
         case 22:
            return new MethodParameters(idx, len, file, cpool);
      }
   }

   public String getName() {
      return this.cpool.getConstantUtf8(this.nameIndex).getValue();
   }

   public final int getLength() {
      return this.length;
   }

   public final int getNameIndex() {
      return this.nameIndex;
   }

   public final byte getTag() {
      return this.tag;
   }

   public final ConstantPool getConstantPool() {
      return this.cpool;
   }

   public String toString() {
      return Constants.ATTRIBUTE_NAMES[this.tag];
   }

   public abstract void accept(ClassVisitor var1);
}
