package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public abstract class ReductionResult {
   protected static final ConstraintTypeFormula TRUE = new ConstraintTypeFormula() {
      public Object reduce(InferenceContext18 context) {
         return this;
      }

      public String toString() {
         return "TRUE";
      }
   };
   protected static final ConstraintTypeFormula FALSE = new ConstraintTypeFormula() {
      public Object reduce(InferenceContext18 context) {
         return this;
      }

      public String toString() {
         return "FALSE";
      }
   };
   protected static final int COMPATIBLE = 1;
   protected static final int SUBTYPE = 2;
   protected static final int SUPERTYPE = 3;
   protected static final int SAME = 4;
   protected static final int TYPE_ARGUMENT_CONTAINED = 5;
   protected static final int CAPTURE = 6;
   static final int EXCEPTIONS_CONTAINED = 7;
   protected static final int POTENTIALLY_COMPATIBLE = 8;
   protected TypeBinding right;
   protected int relation;

   protected static String relationToString(int relation) {
      switch (relation) {
         case 1:
            return " → ";
         case 2:
            return " <: ";
         case 3:
            return " :> ";
         case 4:
            return " = ";
         case 5:
            return " <= ";
         case 6:
            return " captureOf ";
         case 7:
         default:
            throw new IllegalArgumentException("Unknown type relation " + relation);
         case 8:
            return " →? ";
      }
   }
}
