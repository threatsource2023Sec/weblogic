package org.apache.oro.text.regex;

import java.util.Stack;

public final class Perl5Matcher implements PatternMatcher {
   private static final char __EOS = '\uffff';
   private static final int __INITIAL_NUM_OFFSETS = 20;
   private boolean __multiline = false;
   private boolean __lastSuccess = false;
   private char __previousChar;
   private char[] __input;
   private char[] __originalInput;
   private Perl5Repetition __currentRep;
   private int __numParentheses;
   private int __bol;
   private int __eol;
   private int __currentOffset;
   private int __endOffset;
   private char[] __program;
   private int __expSize;
   private int __inputOffset;
   private int __lastParen;
   private int[] __beginMatchOffsets;
   private int[] __endMatchOffsets;
   private Stack __stack = new Stack();
   private Perl5MatchResult __lastMatchResult = null;
   private static final int __DEFAULT_LAST_MATCH_END_OFFSET = -100;
   private int __lastMatchInputEndOffset = -100;

   private static boolean __compare(char[] var0, int var1, char[] var2, int var3, int var4) {
      for(int var5 = 0; var5 < var4; ++var3) {
         if (var1 >= var0.length) {
            return false;
         }

         if (var3 >= var2.length) {
            return false;
         }

         if (var0[var1] != var2[var3]) {
            return false;
         }

         ++var5;
         ++var1;
      }

      return true;
   }

   private static int __findFirst(char[] var0, int var1, int var2, char[] var3) {
      if (var0.length == 0) {
         return var2;
      } else {
         for(char var6 = var3[0]; var1 < var2; ++var1) {
            if (var6 == var0[var1]) {
               int var5 = var1;

               int var4;
               for(var4 = 0; var1 < var2 && var4 < var3.length && var3[var4] == var0[var1]; ++var1) {
                  ++var4;
               }

               var1 = var5;
               if (var4 >= var3.length) {
                  break;
               }
            }
         }

         return var1;
      }
   }

   private void __initInterpreterGlobals(Perl5Pattern var1, char[] var2, int var3, int var4, int var5) {
      this.__input = var2;
      this.__endOffset = var4;
      this.__currentRep = new Perl5Repetition();
      this.__currentRep._numInstances = 0;
      this.__currentRep._lastRepetition = null;
      this.__program = var1._program;
      this.__stack.setSize(0);
      if (var5 != var3 && var5 > 0) {
         this.__previousChar = var2[var5 - 1];
         if (!this.__multiline && this.__previousChar == '\n') {
            this.__previousChar = 0;
         }
      } else {
         this.__previousChar = '\n';
      }

      this.__numParentheses = var1._numParentheses;
      this.__currentOffset = var5;
      this.__bol = var3;
      this.__eol = var4;
      var4 = this.__numParentheses + 1;
      if (this.__beginMatchOffsets == null || var4 > this.__beginMatchOffsets.length) {
         if (var4 < 20) {
            var4 = 20;
         }

         this.__beginMatchOffsets = new int[var4];
         this.__endMatchOffsets = new int[var4];
      }

   }

   private boolean __interpret(Perl5Pattern var1, char[] var2, int var3, int var4, int var5) {
      boolean var6;
      label364: {
         int var7 = 0;
         int var8 = 0;
         this.__initInterpreterGlobals(var1, var2, var3, var4, var5);
         var6 = false;
         char[] var11 = var1._mustString;
         if (var11 != null && ((var1._anchor & 3) == 0 || (this.__multiline || (var1._anchor & 2) != 0) && var1._back >= 0)) {
            this.__currentOffset = __findFirst(this.__input, this.__currentOffset, var4, var11);
            if (this.__currentOffset >= var4) {
               if ((var1._options & '耀') == 0) {
                  ++var1._mustUtility;
               }

               var6 = false;
               break label364;
            }

            if (var1._back >= 0) {
               this.__currentOffset -= var1._back;
               if (this.__currentOffset < var5) {
                  this.__currentOffset = var5;
               }

               var7 = var1._back + var11.length;
            } else if (!var1._isExpensive && (var1._options & '耀') == 0 && --var1._mustUtility < 0) {
               var11 = var1._mustString = null;
               this.__currentOffset = var5;
            } else {
               this.__currentOffset = var5;
               var7 = var11.length;
            }
         }

         if ((var1._anchor & 3) != 0) {
            if (this.__currentOffset == var3 && this.__tryExpression(var1, var3)) {
               var6 = true;
            } else if (this.__multiline || (var1._anchor & 2) != 0 || (var1._anchor & 8) != 0) {
               if (var7 > 0) {
                  var8 = var7 - 1;
               }

               var4 -= var8;
               if (this.__currentOffset > var5) {
                  --this.__currentOffset;
               }

               while(this.__currentOffset < var4) {
                  if (this.__input[this.__currentOffset++] == '\n' && this.__currentOffset < var4 && this.__tryExpression(var1, this.__currentOffset)) {
                     var6 = true;
                     break;
                  }
               }
            }
         } else {
            char var10;
            if (var1._startString != null) {
               var11 = var1._startString;
               if ((var1._anchor & 4) != 0) {
                  for(var10 = var11[0]; this.__currentOffset < var4; ++this.__currentOffset) {
                     if (var10 == this.__input[this.__currentOffset]) {
                        if (this.__tryExpression(var1, this.__currentOffset)) {
                           var6 = true;
                           break;
                        }

                        ++this.__currentOffset;

                        while(this.__currentOffset < var4 && this.__input[this.__currentOffset] == var10) {
                           ++this.__currentOffset;
                        }
                     }
                  }
               } else {
                  while((this.__currentOffset = __findFirst(this.__input, this.__currentOffset, var4, var11)) < var4) {
                     if (this.__tryExpression(var1, this.__currentOffset)) {
                        var6 = true;
                        break;
                     }

                     ++this.__currentOffset;
                  }
               }
            } else {
               int var9;
               if ((var9 = var1._startClassOffset) != -1) {
                  boolean var12 = (var1._anchor & 4) == 0;
                  if (var7 > 0) {
                     var8 = var7 - 1;
                  }

                  var4 -= var8;
                  boolean var13 = true;
                  char var14;
                  label298:
                  switch (var14 = this.__program[var9]) {
                     case '\t':
                        for(var9 = OpCode._getOperand(var9); this.__currentOffset < var4; ++this.__currentOffset) {
                           var10 = this.__input[this.__currentOffset];
                           if (var10 < 256 && (this.__program[var9 + (var10 >> 4)] & 1 << (var10 & 15)) == 0) {
                              if (var13 && this.__tryExpression(var1, this.__currentOffset)) {
                                 var6 = true;
                                 break;
                              }

                              var13 = var12;
                           } else {
                              var13 = true;
                           }
                        }
                     case '\n':
                     case '\u000b':
                     case '\f':
                     case '\r':
                     case '\u000e':
                     case '\u000f':
                     case '\u0010':
                     case '\u0011':
                     case '\u001a':
                     case '\u001b':
                     case '\u001c':
                     case '\u001d':
                     case '\u001e':
                     case '\u001f':
                     case ' ':
                     case '!':
                     case '"':
                     default:
                        break;
                     case '\u0012':
                        while(true) {
                           if (this.__currentOffset >= var4) {
                              break label298;
                           }

                           var10 = this.__input[this.__currentOffset];
                           if (OpCode._isWordCharacter(var10)) {
                              if (var13 && this.__tryExpression(var1, this.__currentOffset)) {
                                 var6 = true;
                                 break label298;
                              }

                              var13 = var12;
                           } else {
                              var13 = true;
                           }

                           ++this.__currentOffset;
                        }
                     case '\u0013':
                        while(true) {
                           if (this.__currentOffset >= var4) {
                              break label298;
                           }

                           var10 = this.__input[this.__currentOffset];
                           if (!OpCode._isWordCharacter(var10)) {
                              if (var13 && this.__tryExpression(var1, this.__currentOffset)) {
                                 var6 = true;
                                 break label298;
                              }

                              var13 = var12;
                           } else {
                              var13 = true;
                           }

                           ++this.__currentOffset;
                        }
                     case '\u0014':
                        if (var7 > 0) {
                           ++var8;
                           --var4;
                        }

                        if (this.__currentOffset != var3) {
                           var10 = this.__input[this.__currentOffset - 1];
                           var13 = OpCode._isWordCharacter(var10);
                        } else {
                           var13 = OpCode._isWordCharacter(this.__previousChar);
                        }

                        for(; this.__currentOffset < var4; ++this.__currentOffset) {
                           var10 = this.__input[this.__currentOffset];
                           if (var13 != OpCode._isWordCharacter(var10)) {
                              var13 ^= true;
                              if (this.__tryExpression(var1, this.__currentOffset)) {
                                 var6 = true;
                                 break label298;
                              }
                           }
                        }

                        if ((var7 > 0 || var13) && this.__tryExpression(var1, this.__currentOffset)) {
                           var6 = true;
                        }
                        break;
                     case '\u0015':
                        if (var7 > 0) {
                           ++var8;
                           --var4;
                        }

                        if (this.__currentOffset != var3) {
                           var10 = this.__input[this.__currentOffset - 1];
                           var13 = OpCode._isWordCharacter(var10);
                        } else {
                           var13 = OpCode._isWordCharacter(this.__previousChar);
                        }

                        for(; this.__currentOffset < var4; ++this.__currentOffset) {
                           var10 = this.__input[this.__currentOffset];
                           if (var13 != OpCode._isWordCharacter(var10)) {
                              var13 ^= true;
                           } else if (this.__tryExpression(var1, this.__currentOffset)) {
                              var6 = true;
                              break label298;
                           }
                        }

                        if ((var7 > 0 || !var13) && this.__tryExpression(var1, this.__currentOffset)) {
                           var6 = true;
                        }
                        break;
                     case '\u0016':
                        while(true) {
                           if (this.__currentOffset >= var4) {
                              break label298;
                           }

                           if (Character.isWhitespace(this.__input[this.__currentOffset])) {
                              if (var13 && this.__tryExpression(var1, this.__currentOffset)) {
                                 var6 = true;
                                 break label298;
                              }

                              var13 = var12;
                           } else {
                              var13 = true;
                           }

                           ++this.__currentOffset;
                        }
                     case '\u0017':
                        while(true) {
                           if (this.__currentOffset >= var4) {
                              break label298;
                           }

                           if (!Character.isWhitespace(this.__input[this.__currentOffset])) {
                              if (var13 && this.__tryExpression(var1, this.__currentOffset)) {
                                 var6 = true;
                                 break label298;
                              }

                              var13 = var12;
                           } else {
                              var13 = true;
                           }

                           ++this.__currentOffset;
                        }
                     case '\u0018':
                        while(true) {
                           if (this.__currentOffset >= var4) {
                              break label298;
                           }

                           if (Character.isDigit(this.__input[this.__currentOffset])) {
                              if (var13 && this.__tryExpression(var1, this.__currentOffset)) {
                                 var6 = true;
                                 break label298;
                              }

                              var13 = var12;
                           } else {
                              var13 = true;
                           }

                           ++this.__currentOffset;
                        }
                     case '\u0019':
                        while(true) {
                           if (this.__currentOffset >= var4) {
                              break label298;
                           }

                           if (!Character.isDigit(this.__input[this.__currentOffset])) {
                              if (var13 && this.__tryExpression(var1, this.__currentOffset)) {
                                 var6 = true;
                                 break label298;
                              }

                              var13 = var12;
                           } else {
                              var13 = true;
                           }

                           ++this.__currentOffset;
                        }
                     case '#':
                     case '$':
                        for(var9 = OpCode._getOperand(var9); this.__currentOffset < var4; ++this.__currentOffset) {
                           var10 = this.__input[this.__currentOffset];
                           if (this.__matchUnicodeClass(var10, this.__program, var9, var14)) {
                              if (var13 && this.__tryExpression(var1, this.__currentOffset)) {
                                 var6 = true;
                                 break;
                              }

                              var13 = var12;
                           } else {
                              var13 = true;
                           }
                        }
                  }
               } else {
                  if (var7 > 0) {
                     var8 = var7 - 1;
                  }

                  var4 -= var8;

                  do {
                     if (this.__tryExpression(var1, this.__currentOffset)) {
                        var6 = true;
                        break;
                     }
                  } while(this.__currentOffset++ < var4);
               }
            }
         }
      }

      this.__lastSuccess = var6;
      this.__lastMatchResult = null;
      return var6;
   }

   private boolean __match(int var1) {
      boolean var11 = true;
      boolean var12 = false;
      int var6 = this.__inputOffset;
      var11 = var6 < this.__endOffset;
      char var2 = var11 ? this.__input[var6] : '\uffff';
      int var4 = var1;

      int var5;
      for(int var7 = this.__program.length; var4 < var7; var4 = var5) {
         var5 = OpCode._getNext(this.__program, var4);
         char var3;
         int var8;
         int var9;
         int var10;
         Perl5Repetition var13;
         boolean var10000;
         char var19;
         switch (var3 = this.__program[var4]) {
            case '\u0000':
            case '!':
               this.__inputOffset = var6;
               if (this.__inputOffset == this.__lastMatchInputEndOffset) {
                  return false;
               }

               return true;
            case '\u0001':
               if (var6 == this.__bol) {
                  if (this.__previousChar == '\n') {
                     break;
                  }

                  var10000 = false;
               } else {
                  if (this.__multiline && (var11 || var6 < this.__eol) && this.__input[var6 - 1] == '\n') {
                     break;
                  }

                  var10000 = false;
               }

               if (!var10000) {
                  return false;
               }
               break;
            case '\u0002':
               if (var6 == this.__bol) {
                  if (this.__previousChar == '\n') {
                     break;
                  }

                  var10000 = false;
               } else {
                  if ((var11 || var6 < this.__eol) && this.__input[var6 - 1] == '\n') {
                     break;
                  }

                  var10000 = false;
               }

               if (!var10000) {
                  return false;
               }
               break;
            case '\u0003':
               if (var6 == this.__bol && this.__previousChar == '\n') {
                  break;
               }

               return false;
            case '\u0004':
               if ((var11 || var6 < this.__eol) && var2 != '\n') {
                  return false;
               }

               if (!this.__multiline && this.__eol - var6 > 1) {
                  return false;
               }
               break;
            case '\u0005':
               if ((var11 || var6 < this.__eol) && var2 != '\n') {
                  return false;
               }
               break;
            case '\u0006':
               if ((var11 || var6 < this.__eol) && var2 != '\n') {
                  return false;
               }

               if (this.__eol - var6 > 1) {
                  return false;
               }
               break;
            case '\u0007':
               if (!var11 && var6 >= this.__eol || var2 == '\n') {
                  return false;
               }

               ++var6;
               var11 = var6 < this.__endOffset;
               var2 = var11 ? this.__input[var6] : '\uffff';
               break;
            case '\b':
               if (!var11 && var6 >= this.__eol) {
                  return false;
               }

               ++var6;
               var11 = var6 < this.__endOffset;
               var2 = var11 ? this.__input[var6] : '\uffff';
               break;
            case '\t':
               var8 = OpCode._getOperand(var4);
               if (var2 == '\uffff' && var11) {
                  var2 = this.__input[var6];
               }

               if (var2 < 256 && (this.__program[var8 + (var2 >> 4)] & 1 << (var2 & 15)) == 0) {
                  if (!var11 && var6 >= this.__eol) {
                     return false;
                  }

                  ++var6;
                  var11 = var6 < this.__endOffset;
                  var2 = var11 ? this.__input[var6] : '\uffff';
                  break;
               }

               return false;
            case '\n':
            case '\u0010':
            case '\u0011':
               if (var3 == '\n') {
                  var9 = OpCode._getArg1(this.__program, var4);
                  var19 = OpCode._getArg2(this.__program, var4);
                  var4 = OpCode._getNextOperator(var4) + 2;
               } else if (var3 == 16) {
                  var9 = 0;
                  var19 = '\uffff';
                  var4 = OpCode._getNextOperator(var4);
               } else {
                  var9 = 1;
                  var19 = '\uffff';
                  var4 = OpCode._getNextOperator(var4);
               }

               short var17;
               if (this.__program[var5] == 14) {
                  var2 = this.__program[OpCode._getOperand(var5) + 1];
                  var17 = 0;
               } else {
                  var2 = '\uffff';
                  var17 = -1000;
               }

               this.__inputOffset = var6;
               if (var12) {
                  var12 = false;
                  if (var9 > 0 && this.__repeat(var4, var9) < var9) {
                     return false;
                  }

                  while(var19 >= var9 || var19 == '\uffff' && var9 > 0) {
                     if ((var17 == -1000 || this.__inputOffset >= this.__endOffset || this.__input[this.__inputOffset] == var2) && this.__match(var5)) {
                        return true;
                     }

                     this.__inputOffset = var6 + var9;
                     if (this.__repeat(var4, 1) == 0) {
                        return false;
                     }

                     ++var9;
                     this.__inputOffset = var6 + var9;
                  }
               } else {
                  var10 = this.__repeat(var4, var19);
                  if (var9 < var10 && OpCode._opType[this.__program[var5]] == 4 && (!this.__multiline || this.__program[var5] == 6)) {
                     var9 = var10;
                  }

                  while(var10 >= var9) {
                     if ((var17 == -1000 || this.__inputOffset >= this.__endOffset || this.__input[this.__inputOffset] == var2) && this.__match(var5)) {
                        return true;
                     }

                     --var10;
                     this.__inputOffset = var6 + var10;
                  }
               }

               return false;
            case '\u000b':
               var13 = new Perl5Repetition();
               var13._lastRepetition = this.__currentRep;
               this.__currentRep = var13;
               var13._parenFloor = this.__lastParen;
               var13._numInstances = -1;
               var13._min = OpCode._getArg1(this.__program, var4);
               var13._max = OpCode._getArg2(this.__program, var4);
               var13._scan = OpCode._getNextOperator(var4) + 2;
               var13._next = var5;
               var13._minMod = var12;
               var13._lastLocation = -1;
               this.__inputOffset = var6;
               var12 = this.__match(OpCode._getPrevOperator(var5));
               this.__currentRep = var13._lastRepetition;
               return var12;
            case '\f':
               if (this.__program[var5] == '\f') {
                  int var16 = this.__lastParen;

                  do {
                     this.__inputOffset = var6;
                     if (this.__match(OpCode._getNextOperator(var4))) {
                        return true;
                     }

                     for(var10 = this.__lastParen; var10 > var16; --var10) {
                        this.__endMatchOffsets[var10] = -1;
                     }

                     this.__lastParen = var10;
                     var4 = OpCode._getNext(this.__program, var4);
                  } while(var4 != -1 && this.__program[var4] == '\f');

                  return false;
               }

               var5 = OpCode._getNextOperator(var4);
            case '\r':
            case '\u000f':
            default:
               break;
            case '\u000e':
               var8 = OpCode._getOperand(var4);
               char var18 = this.__program[var8++];
               if (this.__program[var8] != var2) {
                  return false;
               }

               if (this.__eol - var6 < var18) {
                  return false;
               }

               if (var18 > 1 && !__compare(this.__program, var8, this.__input, var6, var18)) {
                  return false;
               }

               var6 += var18;
               var11 = var6 < this.__endOffset;
               var2 = var11 ? this.__input[var6] : '\uffff';
               break;
            case '\u0012':
               if (!var11) {
                  return false;
               }

               if (!OpCode._isWordCharacter(var2)) {
                  return false;
               }

               ++var6;
               var11 = var6 < this.__endOffset;
               var2 = var11 ? this.__input[var6] : '\uffff';
               break;
            case '\u0013':
               if (!var11 && var6 >= this.__eol) {
                  return false;
               }

               if (OpCode._isWordCharacter(var2)) {
                  return false;
               }

               ++var6;
               var11 = var6 < this.__endOffset;
               var2 = var11 ? this.__input[var6] : '\uffff';
               break;
            case '\u0014':
            case '\u0015':
               boolean var14;
               if (var6 == this.__bol) {
                  var14 = OpCode._isWordCharacter(this.__previousChar);
               } else {
                  var14 = OpCode._isWordCharacter(this.__input[var6 - 1]);
               }

               boolean var15 = OpCode._isWordCharacter(var2);
               if (var14 == var15 == (this.__program[var4] == 20)) {
                  return false;
               }
               break;
            case '\u0016':
               if (!var11 && var6 >= this.__eol) {
                  return false;
               }

               if (!Character.isWhitespace(var2)) {
                  return false;
               }

               ++var6;
               var11 = var6 < this.__endOffset;
               var2 = var11 ? this.__input[var6] : '\uffff';
               break;
            case '\u0017':
               if (!var11) {
                  return false;
               }

               if (Character.isWhitespace(var2)) {
                  return false;
               }

               ++var6;
               var11 = var6 < this.__endOffset;
               var2 = var11 ? this.__input[var6] : '\uffff';
               break;
            case '\u0018':
               if (!Character.isDigit(var2)) {
                  return false;
               }

               ++var6;
               var11 = var6 < this.__endOffset;
               var2 = var11 ? this.__input[var6] : '\uffff';
               break;
            case '\u0019':
               if (!var11 && var6 >= this.__eol) {
                  return false;
               }

               if (Character.isDigit(var2)) {
                  return false;
               }

               ++var6;
               var11 = var6 < this.__endOffset;
               var2 = var11 ? this.__input[var6] : '\uffff';
               break;
            case '\u001a':
               var19 = OpCode._getArg1(this.__program, var4);
               var8 = this.__beginMatchOffsets[var19];
               if (var8 == -1) {
                  return false;
               }

               if (this.__endMatchOffsets[var19] == -1) {
                  return false;
               }

               if (var8 != this.__endMatchOffsets[var19]) {
                  if (this.__input[var8] != var2) {
                     return false;
                  }

                  var9 = this.__endMatchOffsets[var19] - var8;
                  if (var6 + var9 > this.__eol) {
                     return false;
                  }

                  if (var9 > 1 && !__compare(this.__input, var8, this.__input, var6, var9)) {
                     return false;
                  }

                  var6 += var9;
                  var11 = var6 < this.__endOffset;
                  var2 = var11 ? this.__input[var6] : '\uffff';
               }
               break;
            case '\u001b':
               var19 = OpCode._getArg1(this.__program, var4);
               this.__beginMatchOffsets[var19] = var6;
               if (var19 > this.__expSize) {
                  this.__expSize = var19;
               }
               break;
            case '\u001c':
               var19 = OpCode._getArg1(this.__program, var4);
               this.__endMatchOffsets[var19] = var6;
               if (var19 > this.__lastParen) {
                  this.__lastParen = var19;
               }
               break;
            case '\u001d':
               var12 = true;
               break;
            case '\u001e':
               if (var6 != this.__bol) {
                  return true;
               }
               break;
            case '\u001f':
               this.__inputOffset = var6;
               var4 = OpCode._getNextOperator(var4);
               if (!this.__match(var4)) {
                  return false;
               }
               break;
            case ' ':
               this.__inputOffset = var6;
               var4 = OpCode._getNextOperator(var4);
               if (this.__match(var4)) {
                  return false;
               }
               break;
            case '"':
               var13 = this.__currentRep;
               var10 = var13._numInstances + 1;
               this.__inputOffset = var6;
               if (var6 == var13._lastLocation) {
                  this.__currentRep = var13._lastRepetition;
                  var9 = this.__currentRep._numInstances;
                  if (this.__match(var13._next)) {
                     return true;
                  }

                  this.__currentRep._numInstances = var9;
                  this.__currentRep = var13;
                  return false;
               }

               if (var10 < var13._min) {
                  var13._numInstances = var10;
                  var13._lastLocation = var6;
                  if (this.__match(var13._scan)) {
                     return true;
                  }

                  var13._numInstances = var10 - 1;
                  return false;
               }

               if (var13._minMod) {
                  this.__currentRep = var13._lastRepetition;
                  var9 = this.__currentRep._numInstances;
                  if (this.__match(var13._next)) {
                     return true;
                  }

                  this.__currentRep._numInstances = var9;
                  this.__currentRep = var13;
                  if (var10 >= var13._max) {
                     return false;
                  }

                  this.__inputOffset = var6;
                  var13._numInstances = var10;
                  var13._lastLocation = var6;
                  if (this.__match(var13._scan)) {
                     return true;
                  }

                  var13._numInstances = var10 - 1;
                  return false;
               }

               if (var10 < var13._max) {
                  this.__pushState(var13._parenFloor);
                  var13._numInstances = var10;
                  var13._lastLocation = var6;
                  if (this.__match(var13._scan)) {
                     return true;
                  }

                  this.__popState();
                  this.__inputOffset = var6;
               }

               this.__currentRep = var13._lastRepetition;
               var9 = this.__currentRep._numInstances;
               if (this.__match(var13._next)) {
                  return true;
               }

               var13._numInstances = var9;
               this.__currentRep = var13;
               var13._numInstances = var10 - 1;
               return false;
            case '#':
            case '$':
               var8 = OpCode._getOperand(var4);
               if (var2 == '\uffff' && var11) {
                  var2 = this.__input[var6];
               }

               if (!this.__matchUnicodeClass(var2, this.__program, var8, var3)) {
                  return false;
               }

               if (!var11 && var6 >= this.__eol) {
                  return false;
               }

               ++var6;
               var11 = var6 < this.__endOffset;
               var2 = var11 ? this.__input[var6] : '\uffff';
         }
      }

      return false;
   }

   private boolean __matchUnicodeClass(char var1, char[] var2, int var3, char var4) {
      // $FF: Couldn't be decompiled
   }

   private void __popState() {
      int[] var1 = (int[])this.__stack.pop();
      this.__expSize = var1[0];
      this.__lastParen = var1[1];
      this.__inputOffset = var1[2];

      int var3;
      for(int var2 = 3; var2 < var1.length; var2 += 3) {
         var3 = var1[var2 + 2];
         this.__beginMatchOffsets[var3] = var1[var2 + 1];
         if (var3 <= this.__lastParen) {
            this.__endMatchOffsets[var3] = var1[var2];
         }
      }

      for(var3 = this.__lastParen + 1; var3 <= this.__numParentheses; ++var3) {
         if (var3 > this.__expSize) {
            this.__beginMatchOffsets[var3] = -1;
         }

         this.__endMatchOffsets[var3] = -1;
      }

   }

   private void __pushState(int var1) {
      int var3 = 3 * (this.__expSize - var1);
      int[] var2;
      if (var3 <= 0) {
         var2 = new int[3];
      } else {
         var2 = new int[var3 + 3];
      }

      var2[0] = this.__expSize;
      var2[1] = this.__lastParen;
      var2[2] = this.__inputOffset;

      for(int var4 = this.__expSize; var4 > var1; var3 -= 3) {
         var2[var3] = this.__endMatchOffsets[var4];
         var2[var3 + 1] = this.__beginMatchOffsets[var4];
         var2[var3 + 2] = var4;
         var4 -= 3;
      }

      this.__stack.push(var2);
   }

   private int __repeat(int var1, int var2) {
      int var3 = this.__inputOffset;
      int var4 = this.__eol;
      if (var2 != 65535 && var2 < var4 - var3) {
         var4 = var3 + var2;
      }

      int var5 = OpCode._getOperand(var1);
      char var7;
      char var8;
      label121:
      switch (var8 = this.__program[var1]) {
         case '\u0007':
            while(true) {
               if (var3 >= var4 || this.__input[var3] == '\n') {
                  break label121;
               }

               ++var3;
            }
         case '\b':
            var3 = var4;
            break;
         case '\t':
            if (var3 < var4 && (var7 = this.__input[var3]) < 256) {
               while(var7 < 256 && (this.__program[var5 + (var7 >> 4)] & 1 << (var7 & 15)) == 0) {
                  ++var3;
                  if (var3 >= var4) {
                     break;
                  }

                  var7 = this.__input[var3];
               }
            }
         case '\n':
         case '\u000b':
         case '\f':
         case '\r':
         case '\u000f':
         case '\u0010':
         case '\u0011':
         case '\u0014':
         case '\u0015':
         case '\u001a':
         case '\u001b':
         case '\u001c':
         case '\u001d':
         case '\u001e':
         case '\u001f':
         case ' ':
         case '!':
         case '"':
         default:
            break;
         case '\u000e':
            ++var5;

            while(true) {
               if (var3 >= var4 || this.__program[var5] != this.__input[var3]) {
                  break label121;
               }

               ++var3;
            }
         case '\u0012':
            while(true) {
               if (var3 >= var4 || !OpCode._isWordCharacter(this.__input[var3])) {
                  break label121;
               }

               ++var3;
            }
         case '\u0013':
            while(true) {
               if (var3 >= var4 || OpCode._isWordCharacter(this.__input[var3])) {
                  break label121;
               }

               ++var3;
            }
         case '\u0016':
            while(true) {
               if (var3 >= var4 || !Character.isWhitespace(this.__input[var3])) {
                  break label121;
               }

               ++var3;
            }
         case '\u0017':
            while(true) {
               if (var3 >= var4 || Character.isWhitespace(this.__input[var3])) {
                  break label121;
               }

               ++var3;
            }
         case '\u0018':
            while(true) {
               if (var3 >= var4 || !Character.isDigit(this.__input[var3])) {
                  break label121;
               }

               ++var3;
            }
         case '\u0019':
            while(true) {
               if (var3 >= var4 || Character.isDigit(this.__input[var3])) {
                  break label121;
               }

               ++var3;
            }
         case '#':
         case '$':
            if (var3 < var4) {
               for(var7 = this.__input[var3]; this.__matchUnicodeClass(var7, this.__program, var5, var8); var7 = this.__input[var3]) {
                  ++var3;
                  if (var3 >= var4) {
                     break;
                  }
               }
            }
      }

      int var6 = var3 - this.__inputOffset;
      this.__inputOffset = var3;
      return var6;
   }

   private void __setLastMatchResult() {
      this.__lastMatchResult = new Perl5MatchResult(this.__numParentheses + 1);
      if (this.__endMatchOffsets[0] > this.__originalInput.length) {
         throw new ArrayIndexOutOfBoundsException();
      } else {
         this.__lastMatchResult._match = new String(this.__originalInput, this.__beginMatchOffsets[0], this.__endMatchOffsets[0] - this.__beginMatchOffsets[0]);

         for(this.__lastMatchResult._matchBeginOffset = this.__beginMatchOffsets[0]; this.__numParentheses >= 0; --this.__numParentheses) {
            int var1 = this.__beginMatchOffsets[this.__numParentheses];
            if (var1 >= 0) {
               this.__lastMatchResult._beginGroupOffset[this.__numParentheses] = var1 - this.__lastMatchResult._matchBeginOffset;
            } else {
               this.__lastMatchResult._beginGroupOffset[this.__numParentheses] = -1;
            }

            var1 = this.__endMatchOffsets[this.__numParentheses];
            if (var1 >= 0) {
               this.__lastMatchResult._endGroupOffset[this.__numParentheses] = var1 - this.__lastMatchResult._matchBeginOffset;
            } else {
               this.__lastMatchResult._endGroupOffset[this.__numParentheses] = -1;
            }
         }

         this.__originalInput = null;
      }
   }

   private boolean __tryExpression(Perl5Pattern var1, int var2) {
      this.__inputOffset = var2;
      this.__lastParen = 0;
      this.__expSize = 0;
      if (this.__numParentheses > 0) {
         for(int var3 = 0; var3 <= this.__numParentheses; ++var3) {
            this.__beginMatchOffsets[var3] = -1;
            this.__endMatchOffsets[var3] = -1;
         }
      }

      if (this.__match(1)) {
         this.__beginMatchOffsets[0] = var2;
         this.__endMatchOffsets[0] = this.__inputOffset;
         return true;
      } else {
         return false;
      }
   }

   char[] _toLower(char[] var1) {
      char[] var3 = new char[var1.length];
      System.arraycopy(var1, 0, var3, 0, var1.length);
      var1 = var3;

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (Character.isUpperCase(var1[var2])) {
            var1[var2] = Character.toLowerCase(var1[var2]);
         }
      }

      return var1;
   }

   public boolean contains(String var1, Pattern var2) {
      return this.contains(var1.toCharArray(), var2);
   }

   public boolean contains(PatternMatcherInput var1, Pattern var2) {
      if (var1._currentOffset > var1._endOffset) {
         return false;
      } else {
         Perl5Pattern var4 = (Perl5Pattern)var2;
         this.__originalInput = var1._originalBuffer;
         this.__originalInput = var1._originalBuffer;
         char[] var3;
         if (var4._isCaseInsensitive) {
            if (var1._toLowerBuffer == null) {
               var1._toLowerBuffer = this._toLower(this.__originalInput);
            }

            var3 = var1._toLowerBuffer;
         } else {
            var3 = this.__originalInput;
         }

         this.__lastMatchInputEndOffset = var1.getMatchEndOffset();
         boolean var5 = this.__interpret(var4, var3, var1._beginOffset, var1._endOffset, var1._currentOffset);
         if (var5) {
            var1.setCurrentOffset(this.__endMatchOffsets[0]);
            var1.setMatchOffsets(this.__beginMatchOffsets[0], this.__endMatchOffsets[0]);
         } else {
            var1.setCurrentOffset(var1._endOffset + 1);
         }

         this.__lastMatchInputEndOffset = -100;
         return var5;
      }
   }

   public boolean contains(char[] var1, Pattern var2) {
      Perl5Pattern var3 = (Perl5Pattern)var2;
      this.__originalInput = var1;
      if (var3._isCaseInsensitive) {
         var1 = this._toLower(var1);
      }

      return this.__interpret(var3, var1, 0, var1.length, 0);
   }

   public MatchResult getMatch() {
      if (!this.__lastSuccess) {
         return null;
      } else {
         if (this.__lastMatchResult == null) {
            this.__setLastMatchResult();
         }

         return this.__lastMatchResult;
      }
   }

   public boolean isMultiline() {
      return this.__multiline;
   }

   public boolean matches(String var1, Pattern var2) {
      return this.matches(var1.toCharArray(), var2);
   }

   public boolean matches(PatternMatcherInput var1, Pattern var2) {
      Perl5Pattern var4 = (Perl5Pattern)var2;
      this.__originalInput = var1._originalBuffer;
      char[] var3;
      if (var4._isCaseInsensitive) {
         if (var1._toLowerBuffer == null) {
            var1._toLowerBuffer = this._toLower(this.__originalInput);
         }

         var3 = var1._toLowerBuffer;
      } else {
         var3 = this.__originalInput;
      }

      this.__initInterpreterGlobals(var4, var3, var1._beginOffset, var1._endOffset, var1._beginOffset);
      this.__lastMatchResult = null;
      if (!this.__tryExpression(var4, var1._beginOffset) || this.__endMatchOffsets[0] != var1._endOffset && var1.length() != 0 && var1._beginOffset != var1._endOffset) {
         this.__lastSuccess = false;
         return false;
      } else {
         this.__lastSuccess = true;
         return true;
      }
   }

   public boolean matches(char[] var1, Pattern var2) {
      Perl5Pattern var3 = (Perl5Pattern)var2;
      this.__originalInput = var1;
      if (var3._isCaseInsensitive) {
         var1 = this._toLower(var1);
      }

      this.__initInterpreterGlobals(var3, var1, 0, var1.length, 0);
      this.__lastSuccess = this.__tryExpression(var3, 0) && this.__endMatchOffsets[0] == var1.length;
      this.__lastMatchResult = null;
      return this.__lastSuccess;
   }

   public boolean matchesPrefix(String var1, Pattern var2) {
      return this.matchesPrefix(var1.toCharArray(), var2, 0);
   }

   public boolean matchesPrefix(PatternMatcherInput var1, Pattern var2) {
      Perl5Pattern var4 = (Perl5Pattern)var2;
      this.__originalInput = var1._originalBuffer;
      char[] var3;
      if (var4._isCaseInsensitive) {
         if (var1._toLowerBuffer == null) {
            var1._toLowerBuffer = this._toLower(this.__originalInput);
         }

         var3 = var1._toLowerBuffer;
      } else {
         var3 = this.__originalInput;
      }

      this.__initInterpreterGlobals(var4, var3, var1._beginOffset, var1._endOffset, var1._currentOffset);
      this.__lastSuccess = this.__tryExpression(var4, var1._currentOffset);
      this.__lastMatchResult = null;
      return this.__lastSuccess;
   }

   public boolean matchesPrefix(char[] var1, Pattern var2) {
      return this.matchesPrefix(var1, var2, 0);
   }

   public boolean matchesPrefix(char[] var1, Pattern var2, int var3) {
      Perl5Pattern var4 = (Perl5Pattern)var2;
      this.__originalInput = var1;
      if (var4._isCaseInsensitive) {
         var1 = this._toLower(var1);
      }

      this.__initInterpreterGlobals(var4, var1, 0, var1.length, var3);
      this.__lastSuccess = this.__tryExpression(var4, var3);
      this.__lastMatchResult = null;
      return this.__lastSuccess;
   }

   public void setMultiline(boolean var1) {
      this.__multiline = var1;
   }
}
