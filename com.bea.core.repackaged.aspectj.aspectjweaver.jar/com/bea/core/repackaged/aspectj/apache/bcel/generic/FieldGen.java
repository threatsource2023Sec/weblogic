package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Constant;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantDouble;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantFloat;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantInteger;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantLong;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantObject;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantString;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Field;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeAnnos;
import java.util.Iterator;
import java.util.List;

public class FieldGen extends FieldGenOrMethodGen {
   private Object value;

   public FieldGen(int modifiers, Type type, String name, ConstantPool cpool) {
      this.value = null;
      this.setModifiers(modifiers);
      this.setType(type);
      this.setName(name);
      this.setConstantPool(cpool);
   }

   public FieldGen(Field field, ConstantPool cp) {
      this(field.getModifiers(), Type.getType(field.getSignature()), field.getName(), cp);
      Attribute[] attrs = field.getAttributes();

      for(int i = 0; i < attrs.length; ++i) {
         if (attrs[i] instanceof ConstantValue) {
            this.setValue(((ConstantValue)attrs[i]).getConstantValueIndex());
         } else if (attrs[i] instanceof RuntimeAnnos) {
            RuntimeAnnos runtimeAnnotations = (RuntimeAnnos)attrs[i];
            List l = runtimeAnnotations.getAnnotations();
            Iterator it = l.iterator();

            while(it.hasNext()) {
               AnnotationGen element = (AnnotationGen)it.next();
               this.addAnnotation(new AnnotationGen(element, cp, false));
            }
         } else {
            this.addAttribute(attrs[i]);
         }
      }

   }

   public void setValue(int index) {
      ConstantPool cp = this.cp;
      Constant c = cp.getConstant(index);
      if (c instanceof ConstantInteger) {
         this.value = ((ConstantInteger)c).getIntValue();
      } else if (c instanceof ConstantFloat) {
         this.value = ((ConstantFloat)c).getValue();
      } else if (c instanceof ConstantDouble) {
         this.value = ((ConstantDouble)c).getValue();
      } else if (c instanceof ConstantLong) {
         this.value = ((ConstantLong)c).getValue();
      } else if (c instanceof ConstantString) {
         this.value = ((ConstantString)c).getString(cp);
      } else {
         this.value = ((ConstantObject)c).getConstantValue(cp);
      }

   }

   public void setValue(String constantString) {
      this.value = constantString;
   }

   public void wipeValue() {
      this.value = null;
   }

   private void checkType(Type atype) {
      if (this.type == null) {
         throw new ClassGenException("You haven't defined the type of the field yet");
      } else if (!this.isFinal()) {
         throw new ClassGenException("Only final fields may have an initial value!");
      } else if (!this.type.equals(atype)) {
         throw new ClassGenException("Types are not compatible: " + this.type + " vs. " + atype);
      }
   }

   public Field getField() {
      String signature = this.getSignature();
      int nameIndex = this.cp.addUtf8(this.name);
      int signatureIndex = this.cp.addUtf8(signature);
      if (this.value != null) {
         this.checkType(this.type);
         int index = this.addConstant();
         this.addAttribute(new ConstantValue(this.cp.addUtf8("ConstantValue"), 2, index, this.cp));
      }

      this.addAnnotationsAsAttribute(this.cp);
      return new Field(this.modifiers, nameIndex, signatureIndex, this.getAttributesImmutable(), this.cp);
   }

   private int addConstant() {
      switch (this.type.getType()) {
         case 4:
         case 5:
         case 8:
         case 9:
         case 10:
            return this.cp.addInteger((Integer)this.value);
         case 6:
            return this.cp.addFloat((Float)this.value);
         case 7:
            return this.cp.addDouble((Double)this.value);
         case 11:
            return this.cp.addLong((Long)this.value);
         case 12:
         case 13:
         default:
            throw new RuntimeException("Oops: Unhandled : " + this.type.getType());
         case 14:
            return this.cp.addString((String)this.value);
      }
   }

   public String getSignature() {
      return this.type.getSignature();
   }

   public String getInitialValue() {
      return this.value == null ? null : this.value.toString();
   }

   public void setInitialStringValue(String value) {
      this.value = value;
   }

   public final String toString() {
      String access = Utility.accessToString(this.modifiers);
      access = access.equals("") ? "" : access + " ";
      String signature = this.type.toString();
      String name = this.getName();
      StringBuffer buf = (new StringBuffer(access)).append(signature).append(" ").append(name);
      String value = this.getInitialValue();
      if (value != null) {
         buf.append(" = ").append(value);
      }

      return buf.toString();
   }
}
