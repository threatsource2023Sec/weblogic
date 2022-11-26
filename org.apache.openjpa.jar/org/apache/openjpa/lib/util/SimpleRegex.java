package org.apache.openjpa.lib.util;

public class SimpleRegex {
   private final String expr;
   private final boolean caseInsensitive;

   public SimpleRegex(String expr, boolean caseInsensitive) {
      this.caseInsensitive = caseInsensitive;
      if (caseInsensitive) {
         this.expr = expr.toLowerCase();
      } else {
         this.expr = expr;
      }

   }

   public boolean matches(String target) {
      if (this.caseInsensitive) {
         target = target.toLowerCase();
      }

      boolean mobile = false;
      int exprPos = 0;
      int targetPos = 0;

      while(true) {
         while(true) {
            int star = this.expr.indexOf(".*", exprPos);
            if (star == exprPos) {
               mobile = true;
               exprPos += 2;
            } else {
               int len;
               if (star == -1) {
                  len = this.expr.length() - exprPos;
                  if (!mobile && targetPos != target.length() - len) {
                     return false;
                  }

                  if (target.length() < len) {
                     return false;
                  }

                  int match = this.indexOf(target, target.length() - len, exprPos, len, true);
                  if (match != -1) {
                     return true;
                  }

                  return false;
               }

               len = this.indexOf(target, targetPos, exprPos, star - exprPos, !mobile);
               if (len == -1) {
                  return false;
               }

               targetPos = len + star - exprPos;
               exprPos = star + 2;
               mobile = true;
            }
         }
      }
   }

   private int indexOf(String target, int targetStart, int exprStart, int exprLength, boolean beginOnly) {
      while(target.length() - targetStart >= exprLength) {
         boolean found = true;

         for(int i = 0; i < exprLength; ++i) {
            if (this.expr.charAt(exprStart + i) != '.' && this.expr.charAt(exprStart + i) != target.charAt(targetStart + i)) {
               found = false;
               break;
            }
         }

         if (found) {
            return targetStart;
         }

         if (beginOnly) {
            return -1;
         }

         ++targetStart;
      }

      return -1;
   }
}
