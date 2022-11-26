package com.bea.core.repackaged.jdt.internal.compiler.env;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;

public class AccessRule {
   public static final int IgnoreIfBetter = 33554432;
   public char[] pattern;
   public int problemId;

   public AccessRule(char[] pattern, int problemId) {
      this(pattern, problemId, false);
   }

   public AccessRule(char[] pattern, int problemId, boolean keepLooking) {
      this.pattern = pattern;
      this.problemId = keepLooking ? problemId | 33554432 : problemId;
   }

   public int hashCode() {
      return this.problemId * 17 + CharOperation.hashCode(this.pattern);
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof AccessRule)) {
         return false;
      } else {
         AccessRule other = (AccessRule)obj;
         return this.problemId != other.problemId ? false : CharOperation.equals(this.pattern, other.pattern);
      }
   }

   public int getProblemId() {
      return this.problemId & -33554433;
   }

   public boolean ignoreIfBetter() {
      return (this.problemId & 33554432) != 0;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("pattern=");
      buffer.append(this.pattern);
      switch (this.getProblemId()) {
         case 16777496:
            buffer.append(" (DISCOURAGED");
            break;
         case 16777523:
            buffer.append(" (NON ACCESSIBLE");
            break;
         default:
            buffer.append(" (ACCESSIBLE");
      }

      if (this.ignoreIfBetter()) {
         buffer.append(" | IGNORE IF BETTER");
      }

      buffer.append(')');
      return buffer.toString();
   }
}
