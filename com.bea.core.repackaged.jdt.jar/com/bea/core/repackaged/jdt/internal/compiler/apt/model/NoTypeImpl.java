package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.List;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;

public class NoTypeImpl extends TypeMirrorImpl implements NoType, NullType {
   private final TypeKind _kind;
   public static final NoType NO_TYPE_NONE;
   public static final NoType NO_TYPE_VOID;
   public static final NoType NO_TYPE_PACKAGE;
   public static final NullType NULL_TYPE;
   public static final Binding NO_TYPE_BINDING;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$javax$lang$model$type$TypeKind;

   static {
      NO_TYPE_NONE = new NoTypeImpl(TypeKind.NONE);
      NO_TYPE_VOID = new NoTypeImpl(TypeKind.VOID, TypeBinding.VOID);
      NO_TYPE_PACKAGE = new NoTypeImpl(TypeKind.PACKAGE);
      NULL_TYPE = new NoTypeImpl(TypeKind.NULL, TypeBinding.NULL);
      NO_TYPE_BINDING = new Binding() {
         public int kind() {
            throw new IllegalStateException();
         }

         public char[] readableName() {
            throw new IllegalStateException();
         }
      };
   }

   public NoTypeImpl(TypeKind kind) {
      super((BaseProcessingEnvImpl)null, NO_TYPE_BINDING);
      this._kind = kind;
   }

   public NoTypeImpl(TypeKind kind, Binding binding) {
      super((BaseProcessingEnvImpl)null, binding);
      this._kind = kind;
   }

   public Object accept(TypeVisitor v, Object p) {
      switch (this.getKind()) {
         case NULL:
            return v.visitNull(this, p);
         default:
            return v.visitNoType(this, p);
      }
   }

   public TypeKind getKind() {
      return this._kind;
   }

   public String toString() {
      switch (this._kind) {
         case VOID:
            return "void";
         case NONE:
         default:
            return "none";
         case NULL:
            return "null";
         case PACKAGE:
            return "package";
         case MODULE:
            return "module";
      }
   }

   public List getAnnotationMirrors() {
      return Factory.EMPTY_ANNOTATION_MIRRORS;
   }

   public Annotation getAnnotation(Class annotationType) {
      return null;
   }

   public Annotation[] getAnnotationsByType(Class annotationType) {
      return (Annotation[])Array.newInstance(annotationType, 0);
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$javax$lang$model$type$TypeKind() {
      int[] var10000 = $SWITCH_TABLE$javax$lang$model$type$TypeKind;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[TypeKind.values().length];

         try {
            var0[TypeKind.ARRAY.ordinal()] = 12;
         } catch (NoSuchFieldError var22) {
         }

         try {
            var0[TypeKind.BOOLEAN.ordinal()] = 1;
         } catch (NoSuchFieldError var21) {
         }

         try {
            var0[TypeKind.BYTE.ordinal()] = 2;
         } catch (NoSuchFieldError var20) {
         }

         try {
            var0[TypeKind.CHAR.ordinal()] = 6;
         } catch (NoSuchFieldError var19) {
         }

         try {
            var0[TypeKind.DECLARED.ordinal()] = 13;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[TypeKind.DOUBLE.ordinal()] = 8;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[TypeKind.ERROR.ordinal()] = 14;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[TypeKind.EXECUTABLE.ordinal()] = 18;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[TypeKind.FLOAT.ordinal()] = 7;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[TypeKind.INT.ordinal()] = 4;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[TypeKind.INTERSECTION.ordinal()] = 21;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[TypeKind.LONG.ordinal()] = 5;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[TypeKind.MODULE.ordinal()] = 22;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[TypeKind.NONE.ordinal()] = 10;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[TypeKind.NULL.ordinal()] = 11;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[TypeKind.OTHER.ordinal()] = 19;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[TypeKind.PACKAGE.ordinal()] = 17;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[TypeKind.SHORT.ordinal()] = 3;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[TypeKind.TYPEVAR.ordinal()] = 15;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[TypeKind.UNION.ordinal()] = 20;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[TypeKind.VOID.ordinal()] = 9;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[TypeKind.WILDCARD.ordinal()] = 16;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$javax$lang$model$type$TypeKind = var0;
         return var0;
      }
   }
}
