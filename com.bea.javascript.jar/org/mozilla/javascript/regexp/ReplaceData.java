package org.mozilla.javascript.regexp;

import java.util.Vector;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;

class ReplaceData extends GlobData {
   static SubString dollarStr = new SubString("$");
   Function lambda;
   char[] repstr;
   int dollar = -1;
   char[] charArray;
   int length;
   int index;
   int leftIndex;

   void doGlobal(Context var1, Scriptable var2, int var3, RegExpImpl var4) throws JavaScriptException {
      SubString var6 = var4.leftContext;
      char[] var7 = var6.charArray;
      int var8 = this.leftIndex;
      int var9 = var4.lastMatch.index - var8;
      this.leftIndex = var4.lastMatch.index + var4.lastMatch.length;
      int var10 = this.findReplen(var1, var4);
      int var11 = var9 + var10;
      char[] var12;
      if (this.charArray != null) {
         var12 = new char[this.length + var11];
         System.arraycopy(this.charArray, 0, var12, 0, this.length);
      } else {
         var12 = new char[var11];
      }

      this.charArray = var12;
      this.length += var11;
      int var13 = this.index;
      this.index += var11;
      System.arraycopy(var7, var8, var12, var13, var9);
      var13 += var9;
      this.doReplace(var1, var4, var12, var13);
   }

   void doReplace(Context var1, RegExpImpl var2, char[] var3, int var4) {
      int var5 = 0;
      char[] var6 = this.repstr;
      int var7 = this.dollar;
      int var8 = var5;
      if (var7 != -1) {
         label34:
         while(true) {
            int var9 = var7 - var5;
            System.arraycopy(this.repstr, var5, var3, var4, var9);
            var4 += var9;
            var5 = var7;
            int[] var10 = new int[1];
            SubString var11 = interpretDollar(var1, var2, var6, var7, var8, var10);
            if (var11 != null) {
               var9 = var11.length;
               if (var9 > 0) {
                  System.arraycopy(var11.charArray, var11.index, var3, var4, var9);
               }

               var4 += var9;
               var5 = var7 + var10[0];
               var7 += var10[0];
            } else {
               ++var7;
            }

            if (var7 >= this.repstr.length) {
               break;
            }

            while(this.repstr[var7] != '$') {
               ++var7;
               if (var7 >= this.repstr.length) {
                  break label34;
               }
            }
         }
      }

      if (this.repstr.length > var5) {
         System.arraycopy(this.repstr, var5, var3, var4, this.repstr.length - var5);
      }

   }

   int findReplen(Context var1, RegExpImpl var2) throws JavaScriptException {
      if (this.lambda == null) {
         int var9 = this.repstr.length;
         if (this.dollar == -1) {
            return var9;
         } else {
            byte var10 = 0;
            int var11 = this.dollar;

            while(var11 < this.repstr.length) {
               char var12 = this.repstr[var11];
               if (var12 != '$') {
                  ++var11;
               } else {
                  int[] var14 = new int[1];
                  SubString var15 = interpretDollar(var1, var2, this.repstr, var11, var10, var14);
                  if (var15 != null) {
                     var9 += var15.length - var14[0];
                     var11 += var14[0];
                  } else {
                     ++var11;
                  }
               }
            }

            return var9;
         }
      } else {
         Vector var3 = var2.parens;
         int var4 = var3.size();
         Object[] var5 = new Object[var4 + 3];
         var5[0] = var2.lastMatch.toString();

         for(int var6 = 0; var6 < var4; ++var6) {
            SubString var7 = (SubString)var3.elementAt(var6);
            var5[var6 + 1] = var7.toString();
         }

         var5[var4 + 1] = new Integer(var2.leftContext.length);
         var5[var4 + 2] = super.str;
         Scriptable var13 = this.lambda.getParentScope();
         Object var8 = this.lambda.call(var1, var13, var13, var5);
         this.repstr = ScriptRuntime.toString(var8).toCharArray();
         return this.repstr.length;
      }
   }

   static SubString interpretDollar(Context var0, RegExpImpl var1, char[] var2, int var3, int var4, int[] var5) {
      if (var2[var3] != '$') {
         throw new RuntimeException();
      } else if (var0.getLanguageVersion() != 0 && var0.getLanguageVersion() <= 140 && var3 > var4 && var2[var3 - 1] == '\\') {
         return null;
      } else {
         char var8 = var2[var3 + 1];
         if (!NativeRegExp.isDigit(var8)) {
            var5[0] = 2;
            switch (var8) {
               case '$':
                  return dollarStr;
               case '&':
                  return var1.lastMatch;
               case '\'':
                  return var1.rightContext;
               case '+':
                  return var1.lastParen;
               case '`':
                  if (var0.getLanguageVersion() == 120) {
                     var1.leftContext.index = 0;
                     var1.leftContext.length = var1.lastMatch.index;
                  }

                  return var1.leftContext;
               default:
                  return null;
            }
         } else {
            int var7;
            int var9;
            if (var0.getLanguageVersion() != 0 && var0.getLanguageVersion() <= 140) {
               if (var8 == '0') {
                  return null;
               }

               var9 = 0;
               char[] var6 = var2;
               var7 = var3;

               while(true) {
                  ++var7;
                  if (var7 >= var6.length || !NativeRegExp.isDigit(var8 = var6[var7])) {
                     break;
                  }

                  int var10 = 10 * var9 + NativeRegExp.unDigit(var8);
                  if (var10 < var9) {
                     break;
                  }

                  var9 = var10;
               }
            } else {
               var9 = NativeRegExp.unDigit(var8);
               var7 = var3 + 2;
               if (var3 + 2 < var2.length) {
                  var8 = var2[var3 + 2];
                  if (NativeRegExp.isDigit(var8)) {
                     var9 = 10 * var9 + NativeRegExp.unDigit(var8);
                     ++var7;
                  }
               }

               if (var9 == 0) {
                  return null;
               }
            }

            --var9;
            var5[0] = var7 - var3;
            return var1.getParenSubString(var9);
         }
      }
   }
}
