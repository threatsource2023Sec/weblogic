package org.apache.regexp;

import java.io.Serializable;

public class REProgram implements Serializable {
   static final int OPT_HASBACKREFS = 1;
   static final int OPT_HASBOL = 2;
   char[] instruction;
   int lenInstruction;
   char[] prefix;
   int flags;
   int maxParens;

   public REProgram(char[] var1) {
      this(var1, var1.length);
   }

   public REProgram(int var1, char[] var2) {
      this(var2, var2.length);
      this.maxParens = var1;
   }

   public REProgram(char[] var1, int var2) {
      this.maxParens = -1;
      this.setInstructions(var1, var2);
   }

   public char[] getInstructions() {
      if (this.lenInstruction != 0) {
         char[] var1 = new char[this.lenInstruction];
         System.arraycopy(this.instruction, 0, var1, 0, this.lenInstruction);
         return var1;
      } else {
         return null;
      }
   }

   public void setInstructions(char[] var1, int var2) {
      this.instruction = var1;
      this.lenInstruction = var2;
      this.flags = 0;
      this.prefix = null;
      if (var1 != null && var2 != 0) {
         int var3;
         if (var2 >= 3 && var1[0] == '|') {
            var3 = var1[2];
            if (var1[var3 + 0] == 'E' && var2 >= 6) {
               char var4 = var1[3];
               if (var4 == 'A') {
                  char var5 = var1[4];
                  this.prefix = new char[var5];
                  System.arraycopy(var1, 6, this.prefix, 0, var5);
               } else if (var4 == '^') {
                  this.flags |= 2;
               }
            }
         }

         for(var3 = 0; var3 < var2; var3 += 3) {
            switch (var1[var3 + 0]) {
               case '#':
                  this.flags |= 1;
                  return;
               case 'A':
                  var3 += var1[var3 + 1];
                  break;
               case '[':
                  var3 += var1[var3 + 1] * 2;
            }
         }
      }

   }
}
