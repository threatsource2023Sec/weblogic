package org.apache.oro.text.regex;

public class Perl5Substitution extends StringSubstitution {
   public static final int INTERPOLATE_ALL = 0;
   public static final int INTERPOLATE_NONE = -1;
   private static final int __OPCODE_STORAGE_SIZE = 32;
   private static final int __MAX_GROUPS = 65535;
   static final int _OPCODE_COPY = -1;
   static final int _OPCODE_LOWERCASE_CHAR = -2;
   static final int _OPCODE_UPPERCASE_CHAR = -3;
   static final int _OPCODE_LOWERCASE_MODE = -4;
   static final int _OPCODE_UPPERCASE_MODE = -5;
   static final int _OPCODE_ENDCASE_MODE = -6;
   int _numInterpolations;
   int[] _subOpcodes;
   int _subOpcodesCount;
   char[] _substitutionChars;
   transient String _lastInterpolation;

   private static final boolean __isInterpolationCharacter(char var0) {
      return Character.isDigit(var0) || var0 == '&';
   }

   private void __addElement(int var1) {
      int var2 = this._subOpcodes.length;
      if (this._subOpcodesCount == var2) {
         int[] var3 = new int[var2 + 32];
         System.arraycopy(this._subOpcodes, 0, var3, 0, var2);
         this._subOpcodes = var3;
      }

      this._subOpcodes[this._subOpcodesCount++] = var1;
   }

   private void __parseSubs(String var1) {
      char[] var7 = this._substitutionChars = var1.toCharArray();
      int var8 = var7.length;
      this._subOpcodes = new int[32];
      this._subOpcodesCount = 0;
      int var5 = 0;
      int var6 = -1;
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;

      for(int var9 = 0; var9 < var8; ++var9) {
         char var10 = var7[var9];
         int var12 = var9 + 1;
         if (var2) {
            int var13 = Character.digit(var10, 10);
            if (var13 > -1) {
               if (var5 <= 65535) {
                  var5 *= 10;
                  var5 += var13;
               }

               if (var12 == var8) {
                  this.__addElement(var5);
               }
               continue;
            }

            if (var10 == '&' && var7[var9 - 1] == '$') {
               this.__addElement(0);
               var5 = 0;
               var2 = false;
               continue;
            }

            this.__addElement(var5);
            var5 = 0;
            var2 = false;
         }

         if ((var10 == '$' || var10 == '\\') && !var3) {
            if (var6 >= 0) {
               this.__addElement(var9 - var6);
               var6 = -1;
            }

            if (var12 != var8) {
               char var11 = var7[var12];
               if (var10 == '$') {
                  var2 = __isInterpolationCharacter(var11);
               } else if (var10 == '\\') {
                  if (var11 == 'l') {
                     if (!var4) {
                        this.__addElement(-2);
                        ++var9;
                     }
                  } else if (var11 == 'u') {
                     if (!var4) {
                        this.__addElement(-3);
                        ++var9;
                     }
                  } else if (var11 == 'L') {
                     this.__addElement(-4);
                     ++var9;
                     var4 = true;
                  } else if (var11 == 'U') {
                     this.__addElement(-5);
                     ++var9;
                     var4 = true;
                  } else if (var11 == 'E') {
                     this.__addElement(-6);
                     ++var9;
                     var4 = false;
                  } else {
                     var3 = true;
                  }
               }
            }
         } else {
            var3 = false;
            if (var6 < 0) {
               var6 = var9;
               this.__addElement(-1);
               this.__addElement(var9);
            }

            if (var12 == var8) {
               this.__addElement(var12 - var6);
            }
         }
      }

   }

   String _finalInterpolatedSub(MatchResult var1) {
      StringBuffer var2 = new StringBuffer(10);
      this._calcSub(var2, var1);
      return var2.toString();
   }

   void _calcSub(StringBuffer var1, MatchResult var2) {
      int[] var10 = this._subOpcodes;
      int var6 = 0;
      char[] var8 = this._substitutionChars;
      char[] var9 = var2.group(0).toCharArray();
      int var3 = this._subOpcodesCount;

      for(int var11 = 0; var11 < var3; ++var11) {
         int var12 = var10[var11];
         int var4;
         int var5;
         char[] var7;
         int var13;
         if (var12 >= 0 && var12 < var2.groups()) {
            var4 = var2.begin(var12);
            if (var4 < 0) {
               continue;
            }

            var13 = var2.end(var12);
            if (var13 < 0) {
               continue;
            }

            int var14 = var2.length();
            if (var4 >= var14 || var13 > var14 || var4 >= var13) {
               continue;
            }

            var5 = var13 - var4;
            var7 = var9;
         } else {
            if (var12 != -1) {
               if (var12 != -2 && var12 != -3) {
                  if (var12 != -4 && var12 != -5) {
                     if (var12 == -6) {
                        var6 = 0;
                     }
                     continue;
                  }

                  var6 = var12;
                  continue;
               }

               if (var6 != -4 && var6 != -5) {
                  var6 = var12;
               }
               continue;
            }

            ++var11;
            if (var11 >= var3) {
               continue;
            }

            var4 = var10[var11];
            ++var11;
            if (var11 >= var3) {
               continue;
            }

            var5 = var10[var11];
            var7 = var8;
         }

         if (var6 == -2) {
            var1.append(Character.toLowerCase(var7[var4++]));
            --var5;
            var1.append(var7, var4, var5);
            var6 = 0;
         } else if (var6 == -3) {
            var1.append(Character.toUpperCase(var7[var4++]));
            --var5;
            var1.append(var7, var4, var5);
            var6 = 0;
         } else if (var6 == -4) {
            var13 = var4 + var5;

            while(var4 < var13) {
               var1.append(Character.toLowerCase(var7[var4++]));
            }
         } else if (var6 == -5) {
            var13 = var4 + var5;

            while(var4 < var13) {
               var1.append(Character.toUpperCase(var7[var4++]));
            }
         } else {
            var1.append(var7, var4, var5);
         }
      }

   }

   public Perl5Substitution() {
      this("", 0);
   }

   public Perl5Substitution(String var1) {
      this(var1, 0);
   }

   public Perl5Substitution(String var1, int var2) {
      this.setSubstitution(var1, var2);
   }

   public void setSubstitution(String var1) {
      this.setSubstitution(var1, 0);
   }

   public void setSubstitution(String var1, int var2) {
      super.setSubstitution(var1);
      this._numInterpolations = var2;
      if (var2 == -1 || var1.indexOf(36) == -1 && var1.indexOf(92) == -1) {
         this._subOpcodes = null;
      } else {
         this.__parseSubs(var1);
      }

      this._lastInterpolation = null;
   }

   public void appendSubstitution(StringBuffer var1, MatchResult var2, int var3, PatternMatcherInput var4, PatternMatcher var5, Pattern var6) {
      if (this._subOpcodes == null) {
         super.appendSubstitution(var1, var2, var3, var4, var5, var6);
      } else {
         if (this._numInterpolations >= 1 && var3 >= this._numInterpolations) {
            if (var3 == this._numInterpolations) {
               this._lastInterpolation = this._finalInterpolatedSub(var2);
            }

            var1.append(this._lastInterpolation);
         } else {
            this._calcSub(var1, var2);
         }

      }
   }
}
