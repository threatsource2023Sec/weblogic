package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.Array;

public class TypeReference extends SpelNodeImpl {
   private final int dimensions;
   @Nullable
   private transient Class type;

   public TypeReference(int pos, SpelNodeImpl qualifiedId) {
      this(pos, qualifiedId, 0);
   }

   public TypeReference(int pos, SpelNodeImpl qualifiedId, int dims) {
      super(pos, qualifiedId);
      this.dimensions = dims;
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      String typeName = (String)this.children[0].getValueInternal(state).getValue();
      Assert.state(typeName != null, "No type name");
      if (!typeName.contains(".") && Character.isLowerCase(typeName.charAt(0))) {
         TypeCode tc = TypeCode.valueOf(typeName.toUpperCase());
         if (tc != TypeCode.OBJECT) {
            Class clazz = this.makeArrayIfNecessary(tc.getType());
            this.exitTypeDescriptor = "Ljava/lang/Class";
            this.type = clazz;
            return new TypedValue(clazz);
         }
      }

      Class clazz = state.findType(typeName);
      clazz = this.makeArrayIfNecessary(clazz);
      this.exitTypeDescriptor = "Ljava/lang/Class";
      this.type = clazz;
      return new TypedValue(clazz);
   }

   private Class makeArrayIfNecessary(Class clazz) {
      if (this.dimensions != 0) {
         for(int i = 0; i < this.dimensions; ++i) {
            Object array = Array.newInstance(clazz, 0);
            clazz = array.getClass();
         }
      }

      return clazz;
   }

   public String toStringAST() {
      StringBuilder sb = new StringBuilder("T(");
      sb.append(this.getChild(0).toStringAST());

      for(int d = 0; d < this.dimensions; ++d) {
         sb.append("[]");
      }

      sb.append(")");
      return sb.toString();
   }

   public boolean isCompilable() {
      return this.exitTypeDescriptor != null;
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      Assert.state(this.type != null, "No type available");
      if (this.type.isPrimitive()) {
         if (this.type == Boolean.TYPE) {
            mv.visitFieldInsn(178, "java/lang/Boolean", "TYPE", "Ljava/lang/Class;");
         } else if (this.type == Byte.TYPE) {
            mv.visitFieldInsn(178, "java/lang/Byte", "TYPE", "Ljava/lang/Class;");
         } else if (this.type == Character.TYPE) {
            mv.visitFieldInsn(178, "java/lang/Character", "TYPE", "Ljava/lang/Class;");
         } else if (this.type == Double.TYPE) {
            mv.visitFieldInsn(178, "java/lang/Double", "TYPE", "Ljava/lang/Class;");
         } else if (this.type == Float.TYPE) {
            mv.visitFieldInsn(178, "java/lang/Float", "TYPE", "Ljava/lang/Class;");
         } else if (this.type == Integer.TYPE) {
            mv.visitFieldInsn(178, "java/lang/Integer", "TYPE", "Ljava/lang/Class;");
         } else if (this.type == Long.TYPE) {
            mv.visitFieldInsn(178, "java/lang/Long", "TYPE", "Ljava/lang/Class;");
         } else if (this.type == Short.TYPE) {
            mv.visitFieldInsn(178, "java/lang/Short", "TYPE", "Ljava/lang/Class;");
         }
      } else {
         mv.visitLdcInsn(Type.getType(this.type));
      }

      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
