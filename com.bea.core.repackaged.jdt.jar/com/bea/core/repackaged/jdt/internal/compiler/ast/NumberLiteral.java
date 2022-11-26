package com.bea.core.repackaged.jdt.internal.compiler.ast;

public abstract class NumberLiteral extends Literal {
   char[] source;

   public NumberLiteral(char[] token, int s, int e) {
      this(s, e);
      this.source = token;
   }

   public NumberLiteral(int s, int e) {
      super(s, e);
   }

   public boolean isValidJavaStatement() {
      return false;
   }

   public char[] source() {
      return this.source;
   }

   protected static char[] removePrefixZerosAndUnderscores(char[] token, boolean isLong) {
      int max = token.length;
      int start = 0;
      int end = max - 1;
      if (isLong) {
         --end;
      }

      if (max > 1 && token[0] == '0') {
         if (max <= 2 || token[1] != 'x' && token[1] != 'X') {
            if (max <= 2 || token[1] != 'b' && token[1] != 'B') {
               start = 1;
            } else {
               start = 2;
            }
         } else {
            start = 2;
         }
      }

      boolean modified = false;
      boolean ignore = true;

      int i;
      label63:
      for(int i = start; i < max; ++i) {
         i = token[i];
         switch (i) {
            case 48:
               if (ignore && !modified && i < end) {
                  modified = true;
               }
               break;
            case 95:
               modified = true;
               break label63;
            default:
               ignore = false;
         }
      }

      if (!modified) {
         return token;
      } else {
         ignore = true;
         StringBuffer buffer = new StringBuffer();
         buffer.append(token, 0, start);

         for(i = start; i < max; ++i) {
            char currentChar = token[i];
            switch (currentChar) {
               case '0':
                  if (ignore && i < end) {
                     continue;
                  }
                  break;
               case '_':
                  continue;
               default:
                  ignore = false;
            }

            buffer.append(currentChar);
         }

         return buffer.toString().toCharArray();
      }
   }
}
