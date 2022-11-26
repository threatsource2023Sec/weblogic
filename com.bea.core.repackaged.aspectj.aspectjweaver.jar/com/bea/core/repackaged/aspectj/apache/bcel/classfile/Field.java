package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import java.io.DataInputStream;
import java.io.IOException;

public final class Field extends FieldOrMethod {
   public static final Field[] NoFields = new Field[0];
   private Type fieldType = null;

   private Field() {
   }

   public Field(Field c) {
      super(c);
   }

   Field(DataInputStream dis, ConstantPool cpool) throws IOException {
      super(dis, cpool);
   }

   public Field(int modifiers, int nameIndex, int signatureIndex, Attribute[] attributes, ConstantPool cpool) {
      super(modifiers, nameIndex, signatureIndex, attributes, cpool);
   }

   public void accept(ClassVisitor v) {
      v.visitField(this);
   }

   public final ConstantValue getConstantValue() {
      return AttributeUtils.getConstantValueAttribute(this.attributes);
   }

   public final String toString() {
      StringBuffer buf = new StringBuffer(Utility.accessToString(this.modifiers));
      if (buf.length() > 0) {
         buf.append(" ");
      }

      String signature = Utility.signatureToString(this.getSignature());
      buf.append(signature).append(" ").append(this.getName());
      ConstantValue cv = this.getConstantValue();
      if (cv != null) {
         buf.append(" = ").append(cv);
      }

      Attribute[] var4;
      int var5 = (var4 = this.attributes).length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Attribute a = var4[var6];
         if (!(a instanceof ConstantValue)) {
            buf.append(" [").append(a.toString()).append("]");
         }
      }

      return buf.toString();
   }

   public Type getType() {
      if (this.fieldType == null) {
         this.fieldType = Type.getReturnType(this.getSignature());
      }

      return this.fieldType;
   }
}
