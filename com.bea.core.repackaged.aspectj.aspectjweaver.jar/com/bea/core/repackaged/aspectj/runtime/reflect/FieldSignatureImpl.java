package com.bea.core.repackaged.aspectj.runtime.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.FieldSignature;
import java.lang.reflect.Field;

public class FieldSignatureImpl extends MemberSignatureImpl implements FieldSignature {
   Class fieldType;
   private Field field;

   FieldSignatureImpl(int modifiers, String name, Class declaringType, Class fieldType) {
      super(modifiers, name, declaringType);
      this.fieldType = fieldType;
   }

   FieldSignatureImpl(String stringRep) {
      super(stringRep);
   }

   public Class getFieldType() {
      if (this.fieldType == null) {
         this.fieldType = this.extractType(3);
      }

      return this.fieldType;
   }

   protected String createToString(StringMaker sm) {
      StringBuffer buf = new StringBuffer();
      buf.append(sm.makeModifiersString(this.getModifiers()));
      if (sm.includeArgs) {
         buf.append(sm.makeTypeName(this.getFieldType()));
      }

      if (sm.includeArgs) {
         buf.append(" ");
      }

      buf.append(sm.makePrimaryTypeName(this.getDeclaringType(), this.getDeclaringTypeName()));
      buf.append(".");
      buf.append(this.getName());
      return buf.toString();
   }

   public Field getField() {
      if (this.field == null) {
         try {
            this.field = this.getDeclaringType().getDeclaredField(this.getName());
         } catch (Exception var2) {
         }
      }

      return this.field;
   }
}
