package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import java.lang.reflect.Field;

public class ProblemReferenceBinding extends ReferenceBinding {
   ReferenceBinding closestMatch;
   private int problemReason;

   public ProblemReferenceBinding(char[][] compoundName, ReferenceBinding closestMatch, int problemReason) {
      this.compoundName = compoundName;
      this.closestMatch = closestMatch;
      this.problemReason = problemReason;
   }

   public TypeBinding clone(TypeBinding enclosingType) {
      throw new IllegalStateException();
   }

   public TypeBinding closestMatch() {
      return this.closestMatch;
   }

   public ReferenceBinding closestReferenceMatch() {
      return this.closestMatch;
   }

   public boolean hasTypeBit(int bit) {
      return this.closestMatch != null ? this.closestMatch.hasTypeBit(bit) : false;
   }

   public int problemId() {
      return this.problemReason;
   }

   public static String problemReasonString(int problemReason) {
      try {
         Class reasons = ProblemReasons.class;
         String simpleName = reasons.getName();
         int lastDot = simpleName.lastIndexOf(46);
         if (lastDot >= 0) {
            simpleName = simpleName.substring(lastDot + 1);
         }

         Field[] fields = reasons.getFields();
         int i = 0;

         for(int length = fields.length; i < length; ++i) {
            Field field = fields[i];
            if (field.getType().equals(Integer.TYPE) && field.getInt(reasons) == problemReason) {
               return simpleName + '.' + field.getName();
            }
         }
      } catch (IllegalAccessException var8) {
      }

      return "unknown";
   }

   public void setTypeAnnotations(AnnotationBinding[] annotations, boolean evalNullAnnotations) {
   }

   public char[] shortReadableName() {
      return this.readableName();
   }

   public char[] sourceName() {
      return this.compoundName.length == 0 ? null : this.compoundName[this.compoundName.length - 1];
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer(10);
      buffer.append("ProblemType:[compoundName=");
      buffer.append(this.compoundName == null ? "<null>" : new String(CharOperation.concatWith(this.compoundName, '.')));
      buffer.append("][problemID=").append(problemReasonString(this.problemReason));
      buffer.append("][closestMatch=");
      buffer.append(this.closestMatch == null ? "<null>" : this.closestMatch.toString());
      buffer.append("]");
      return buffer.toString();
   }
}
