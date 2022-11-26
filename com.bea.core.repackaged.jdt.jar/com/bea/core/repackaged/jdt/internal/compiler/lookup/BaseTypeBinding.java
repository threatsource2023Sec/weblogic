package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class BaseTypeBinding extends TypeBinding {
   public static final int[] CONVERSIONS = initializeConversions();
   public static final int IDENTITY = 1;
   public static final int WIDENING = 2;
   public static final int NARROWING = 4;
   public static final int MAX_CONVERSIONS = 256;
   public char[] simpleName;
   private char[] constantPoolName;

   public static final int[] initializeConversions() {
      int[] table = new int[256];
      table[85] = 1;
      table[51] = 1;
      table[67] = 2;
      table[35] = 4;
      table[163] = 2;
      table[115] = 2;
      table[147] = 2;
      table[131] = 2;
      table[52] = 4;
      table[68] = 1;
      table[36] = 4;
      table[164] = 2;
      table[116] = 2;
      table[148] = 2;
      table[132] = 2;
      table[50] = 4;
      table[66] = 4;
      table[34] = 1;
      table[162] = 2;
      table[114] = 2;
      table[146] = 2;
      table[130] = 2;
      table[58] = 4;
      table[74] = 4;
      table[42] = 4;
      table[170] = 1;
      table[122] = 2;
      table[154] = 2;
      table[138] = 2;
      table[55] = 4;
      table[71] = 4;
      table[39] = 4;
      table[167] = 4;
      table[119] = 1;
      table[151] = 2;
      table[135] = 2;
      table[57] = 4;
      table[73] = 4;
      table[41] = 4;
      table[169] = 4;
      table[121] = 4;
      table[153] = 1;
      table[137] = 2;
      table[56] = 4;
      table[72] = 4;
      table[40] = 4;
      table[168] = 4;
      table[120] = 4;
      table[152] = 4;
      table[136] = 1;
      return table;
   }

   public static final boolean isNarrowing(int left, int right) {
      int right2left = right + (left << 4);
      return right2left >= 0 && right2left < 256 && (CONVERSIONS[right2left] & 5) != 0;
   }

   public static final boolean isWidening(int left, int right) {
      int right2left = right + (left << 4);
      return right2left >= 0 && right2left < 256 && (CONVERSIONS[right2left] & 3) != 0;
   }

   BaseTypeBinding(int id, char[] name, char[] constantPoolName) {
      this.tagBits |= 2L;
      this.id = id;
      this.simpleName = name;
      this.constantPoolName = constantPoolName;
   }

   public char[] computeUniqueKey(boolean isLeaf) {
      return this.constantPoolName();
   }

   public char[] constantPoolName() {
      return this.constantPoolName;
   }

   public TypeBinding clone(TypeBinding enclosingType) {
      return new BaseTypeBinding(this.id, this.simpleName, this.constantPoolName);
   }

   public PackageBinding getPackage() {
      return null;
   }

   public final boolean isCompatibleWith(TypeBinding right, Scope captureScope) {
      if (equalsEquals(this, right)) {
         return true;
      } else {
         int right2left = this.id + (right.id << 4);
         if (right2left >= 0 && right2left < 256 && (CONVERSIONS[right2left] & 3) != 0) {
            return true;
         } else {
            return this == TypeBinding.NULL && !right.isBaseType();
         }
      }
   }

   public void setTypeAnnotations(AnnotationBinding[] annotations, boolean evalNullAnnotations) {
      super.setTypeAnnotations(annotations, false);
   }

   public TypeBinding unannotated() {
      if (!this.hasTypeAnnotations()) {
         return this;
      } else {
         switch (this.id) {
            case 2:
               return TypeBinding.CHAR;
            case 3:
               return TypeBinding.BYTE;
            case 4:
               return TypeBinding.SHORT;
            case 5:
               return TypeBinding.BOOLEAN;
            case 6:
            default:
               throw new IllegalStateException();
            case 7:
               return TypeBinding.LONG;
            case 8:
               return TypeBinding.DOUBLE;
            case 9:
               return TypeBinding.FLOAT;
            case 10:
               return TypeBinding.INT;
         }
      }
   }

   public boolean isUncheckedException(boolean includeSupertype) {
      return this == TypeBinding.NULL;
   }

   public int kind() {
      return 132;
   }

   public char[] qualifiedSourceName() {
      return this.simpleName;
   }

   public char[] readableName() {
      return this.simpleName;
   }

   public char[] shortReadableName() {
      return this.simpleName;
   }

   public char[] sourceName() {
      return this.simpleName;
   }

   public String toString() {
      return this.hasTypeAnnotations() ? this.annotatedDebugName() : new String(this.readableName());
   }
}
