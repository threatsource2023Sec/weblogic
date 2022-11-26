package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BaseTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;

public class PrimitiveTypeImpl extends TypeMirrorImpl implements PrimitiveType {
   public static final PrimitiveTypeImpl BOOLEAN;
   public static final PrimitiveTypeImpl BYTE;
   public static final PrimitiveTypeImpl CHAR;
   public static final PrimitiveTypeImpl DOUBLE;
   public static final PrimitiveTypeImpl FLOAT;
   public static final PrimitiveTypeImpl INT;
   public static final PrimitiveTypeImpl LONG;
   public static final PrimitiveTypeImpl SHORT;

   static {
      BOOLEAN = new PrimitiveTypeImpl(TypeBinding.BOOLEAN);
      BYTE = new PrimitiveTypeImpl(TypeBinding.BYTE);
      CHAR = new PrimitiveTypeImpl(TypeBinding.CHAR);
      DOUBLE = new PrimitiveTypeImpl(TypeBinding.DOUBLE);
      FLOAT = new PrimitiveTypeImpl(TypeBinding.FLOAT);
      INT = new PrimitiveTypeImpl(TypeBinding.INT);
      LONG = new PrimitiveTypeImpl(TypeBinding.LONG);
      SHORT = new PrimitiveTypeImpl(TypeBinding.SHORT);
   }

   private PrimitiveTypeImpl(BaseTypeBinding binding) {
      super((BaseProcessingEnvImpl)null, binding);
   }

   PrimitiveTypeImpl(BaseProcessingEnvImpl env, BaseTypeBinding binding) {
      super(env, binding);
   }

   public Object accept(TypeVisitor v, Object p) {
      return v.visitPrimitive(this, p);
   }

   public TypeKind getKind() {
      return getKind((BaseTypeBinding)this._binding);
   }

   public static TypeKind getKind(BaseTypeBinding binding) {
      switch (binding.id) {
         case 2:
            return TypeKind.CHAR;
         case 3:
            return TypeKind.BYTE;
         case 4:
            return TypeKind.SHORT;
         case 5:
            return TypeKind.BOOLEAN;
         case 6:
         default:
            throw new IllegalArgumentException("BaseTypeBinding of unexpected id " + binding.id);
         case 7:
            return TypeKind.LONG;
         case 8:
            return TypeKind.DOUBLE;
         case 9:
            return TypeKind.FLOAT;
         case 10:
            return TypeKind.INT;
      }
   }
}
