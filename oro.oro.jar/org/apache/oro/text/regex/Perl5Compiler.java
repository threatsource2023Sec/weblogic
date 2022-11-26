package org.apache.oro.text.regex;

import java.util.HashMap;

public final class Perl5Compiler implements PatternCompiler {
   private static final int __WORSTCASE = 0;
   private static final int __NONNULL = 1;
   private static final int __SIMPLE = 2;
   private static final int __SPSTART = 4;
   private static final int __TRYAGAIN = 8;
   private static final char __CASE_INSENSITIVE = '\u0001';
   private static final char __GLOBAL = '\u0002';
   private static final char __KEEP = '\u0004';
   private static final char __MULTILINE = '\b';
   private static final char __SINGLELINE = '\u0010';
   private static final char __EXTENDED = ' ';
   private static final char __READ_ONLY = '耀';
   private static final String __HEX_DIGIT = "0123456789abcdef0123456789ABCDEFx";
   private CharStringPointer __input;
   private boolean __sawBackreference;
   private char[] __modifierFlags = new char[]{'\u0000'};
   private int __numParentheses;
   private int __programSize;
   private int __cost;
   private char[] __program;
   private static final HashMap __hashPOSIX = new HashMap();
   public static final int DEFAULT_MASK = 0;
   public static final int CASE_INSENSITIVE_MASK = 1;
   public static final int MULTILINE_MASK = 8;
   public static final int SINGLELINE_MASK = 16;
   public static final int EXTENDED_MASK = 32;
   public static final int READ_ONLY_MASK = 32768;

   public static final String quotemeta(char[] var0) {
      StringBuffer var2 = new StringBuffer(2 * var0.length);

      for(int var1 = 0; var1 < var0.length; ++var1) {
         if (!OpCode._isWordCharacter(var0[var1])) {
            var2.append('\\');
         }

         var2.append(var0[var1]);
      }

      return var2.toString();
   }

   public static final String quotemeta(String var0) {
      return quotemeta(var0.toCharArray());
   }

   private static boolean __isSimpleRepetitionOp(char var0) {
      return var0 == '*' || var0 == '+' || var0 == '?';
   }

   private static boolean __isComplexRepetitionOp(char[] var0, int var1) {
      if (var1 < var0.length && var1 >= 0) {
         return var0[var1] == '*' || var0[var1] == '+' || var0[var1] == '?' || var0[var1] == '{' && __parseRepetition(var0, var1);
      } else {
         return false;
      }
   }

   private static boolean __parseRepetition(char[] var0, int var1) {
      if (var0[var1] != '{') {
         return false;
      } else {
         ++var1;
         if (var1 < var0.length && Character.isDigit(var0[var1])) {
            while(var1 < var0.length && Character.isDigit(var0[var1])) {
               ++var1;
            }

            if (var1 < var0.length && var0[var1] == ',') {
               ++var1;
            }

            while(var1 < var0.length && Character.isDigit(var0[var1])) {
               ++var1;
            }

            return var1 < var0.length && var0[var1] == '}';
         } else {
            return false;
         }
      }
   }

   private static int __parseHex(char[] var0, int var1, int var2, int[] var3) {
      int var4 = 0;

      int var10002;
      int var5;
      for(var3[0] = 0; var1 < var0.length && var2-- > 0 && (var5 = "0123456789abcdef0123456789ABCDEFx".indexOf(var0[var1])) != -1; var10002 = var3[0]++) {
         var4 <<= 4;
         var4 |= var5 & 15;
         ++var1;
      }

      return var4;
   }

   private static int __parseOctal(char[] var0, int var1, int var2, int[] var3) {
      int var4 = 0;

      int var10002;
      for(var3[0] = 0; var1 < var0.length && var2 > 0 && var0[var1] >= '0' && var0[var1] <= '7'; var10002 = var3[0]++) {
         var4 <<= 3;
         var4 |= var0[var1] - 48;
         --var2;
         ++var1;
      }

      return var4;
   }

   private static void __setModifierFlag(char[] var0, char var1) {
      switch (var1) {
         case 'g':
            var0[0] = (char)(var0[0] | 2);
            return;
         case 'h':
         case 'j':
         case 'k':
         case 'l':
         case 'n':
         case 'p':
         case 'q':
         case 'r':
         case 't':
         case 'u':
         case 'v':
         case 'w':
         default:
            return;
         case 'i':
            var0[0] = (char)(var0[0] | 1);
            return;
         case 'm':
            var0[0] = (char)(var0[0] | 8);
            return;
         case 'o':
            var0[0] = (char)(var0[0] | 4);
            return;
         case 's':
            var0[0] = (char)(var0[0] | 16);
            return;
         case 'x':
            var0[0] = (char)(var0[0] | 32);
      }
   }

   private void __emitCode(char var1) {
      if (this.__program != null) {
         this.__program[this.__programSize] = var1;
      }

      ++this.__programSize;
   }

   private int __emitNode(char var1) {
      int var2 = this.__programSize;
      if (this.__program == null) {
         this.__programSize += 2;
      } else {
         this.__program[this.__programSize++] = var1;
         this.__program[this.__programSize++] = 0;
      }

      return var2;
   }

   private int __emitArgNode(char var1, char var2) {
      int var3 = this.__programSize;
      if (this.__program == null) {
         this.__programSize += 3;
      } else {
         this.__program[this.__programSize++] = var1;
         this.__program[this.__programSize++] = 0;
         this.__program[this.__programSize++] = var2;
      }

      return var3;
   }

   private void __programInsertOperator(char var1, int var2) {
      int var5 = OpCode._opType[var1] == '\n' ? 2 : 0;
      if (this.__program == null) {
         this.__programSize += 2 + var5;
      } else {
         int var3 = this.__programSize;
         this.__programSize += 2 + var5;

         for(int var4 = this.__programSize; var3 > var2; this.__program[var4] = this.__program[var3]) {
            --var3;
            --var4;
         }

         this.__program[var2++] = var1;

         for(this.__program[var2++] = 0; var5-- > 0; this.__program[var2++] = 0) {
         }

      }
   }

   private void __programAddTail(int var1, int var2) {
      if (this.__program != null && var1 != -1) {
         int var3 = var1;

         while(true) {
            int var4 = OpCode._getNext(this.__program, var3);
            if (var4 == -1) {
               int var5;
               if (this.__program[var3] == '\r') {
                  var5 = var3 - var2;
               } else {
                  var5 = var2 - var3;
               }

               this.__program[var3 + 1] = (char)var5;
               return;
            }

            var3 = var4;
         }
      }
   }

   private void __programAddOperatorTail(int var1, int var2) {
      if (this.__program != null && var1 != -1 && OpCode._opType[this.__program[var1]] == '\f') {
         this.__programAddTail(OpCode._getNextOperator(var1), var2);
      }
   }

   private char __getNextChar() {
      char var1 = this.__input._postIncrement();

      while(true) {
         while(true) {
            char var2 = this.__input._getValue();
            if (var2 != '(' || this.__input._getValueRelative(1) != '?' || this.__input._getValueRelative(2) != '#') {
               if ((this.__modifierFlags[0] & 32) == 0) {
                  return var1;
               }

               if (Character.isWhitespace(var2)) {
                  this.__input._increment();
               } else {
                  if (var2 != '#') {
                     return var1;
                  }

                  while(var2 != '\uffff' && var2 != '\n') {
                     var2 = this.__input._increment();
                  }

                  this.__input._increment();
               }
            } else {
               while(var2 != '\uffff' && var2 != ')') {
                  var2 = this.__input._increment();
               }

               this.__input._increment();
            }
         }
      }
   }

   private int __parseAlternation(int[] var1) throws MalformedPatternException {
      int var5 = 0;
      var1[0] = 0;
      int var3 = this.__emitNode('\f');
      int var2 = -1;
      if (this.__input._getOffset() == 0) {
         this.__input._setOffset(-1);
         this.__getNextChar();
      } else {
         this.__input._decrement();
         this.__getNextChar();
      }

      char var6 = this.__input._getValue();

      while(var6 != '\uffff' && var6 != '|' && var6 != ')') {
         var5 &= -9;
         int var4 = this.__parseBranch(var1);
         if (var4 == -1) {
            if ((var5 & 8) == 0) {
               return -1;
            }

            var6 = this.__input._getValue();
         } else {
            var1[0] |= var5 & 1;
            if (var2 == -1) {
               var1[0] |= var5 & 4;
            } else {
               ++this.__cost;
               this.__programAddTail(var2, var4);
            }

            var2 = var4;
            var6 = this.__input._getValue();
         }
      }

      if (var2 == -1) {
         this.__emitNode('\u000f');
      }

      return var3;
   }

   private int __parseAtom(int[] var1) throws MalformedPatternException {
      int[] var5 = new int[]{0};
      var1[0] = 0;
      boolean var2 = false;
      int var4 = -1;

      char var3;
      label265:
      while(true) {
         var3 = this.__input._getValue();
         switch (var3) {
            case '#':
               if ((this.__modifierFlags[0] & 32) != 0) {
                  while(!this.__input._isAtEnd() && this.__input._getValue() != '\n') {
                     this.__input._increment();
                  }

                  if (!this.__input._isAtEnd()) {
                     break;
                  }
               }
            default:
               this.__input._increment();
               var2 = true;
               break label265;
            case '$':
               this.__getNextChar();
               if ((this.__modifierFlags[0] & 8) != 0) {
                  var4 = this.__emitNode('\u0005');
               } else if ((this.__modifierFlags[0] & 16) != 0) {
                  var4 = this.__emitNode('\u0006');
               } else {
                  var4 = this.__emitNode('\u0004');
               }
               break label265;
            case '(':
               this.__getNextChar();
               var4 = this.__parseExpression(true, var5);
               if (var4 == -1) {
                  if ((var5[0] & 8) == 0) {
                     return -1;
                  }
                  break;
               } else {
                  var1[0] |= var5[0] & 5;
                  break label265;
               }
            case ')':
            case '|':
               if ((var5[0] & 8) != 0) {
                  var1[0] |= 8;
                  return -1;
               }

               throw new MalformedPatternException("Error in expression at " + this.__input._toString(this.__input._getOffset()));
            case '*':
            case '+':
            case '?':
               throw new MalformedPatternException("?+* follows nothing in expression");
            case '.':
               this.__getNextChar();
               if ((this.__modifierFlags[0] & 16) != 0) {
                  var4 = this.__emitNode('\b');
               } else {
                  var4 = this.__emitNode('\u0007');
               }

               ++this.__cost;
               var1[0] |= 3;
               break label265;
            case '[':
               this.__input._increment();
               var4 = this.__parseUnicodeClass();
               var1[0] |= 3;
               break label265;
            case '\\':
               var3 = this.__input._increment();
               switch (var3) {
                  case '\u0000':
                  case '\uffff':
                     if (this.__input._isAtEnd()) {
                        throw new MalformedPatternException("Trailing \\ in expression.");
                     }
                  default:
                     var2 = true;
                     break label265;
                  case '0':
                  case 'a':
                  case 'c':
                  case 'e':
                  case 'f':
                  case 'n':
                  case 'r':
                  case 't':
                  case 'x':
                     var2 = true;
                     break label265;
                  case '1':
                  case '2':
                  case '3':
                  case '4':
                  case '5':
                  case '6':
                  case '7':
                  case '8':
                  case '9':
                     StringBuffer var7 = new StringBuffer(10);
                     int var6 = 0;

                     for(var3 = this.__input._getValueRelative(var6); Character.isDigit(var3); var3 = this.__input._getValueRelative(var6)) {
                        var7.append(var3);
                        ++var6;
                     }

                     try {
                        var6 = Integer.parseInt(var7.toString());
                     } catch (NumberFormatException var17) {
                        throw new MalformedPatternException("Unexpected number format exception.  Please report this bug.NumberFormatException message: " + var17.getMessage());
                     }

                     if (var6 > 9 && var6 >= this.__numParentheses) {
                        var2 = true;
                     } else {
                        if (var6 >= this.__numParentheses) {
                           throw new MalformedPatternException("Invalid backreference: \\" + var6);
                        }

                        this.__sawBackreference = true;
                        var4 = this.__emitArgNode('\u001a', (char)var6);
                        var1[0] |= 1;

                        for(var3 = this.__input._getValue(); Character.isDigit(var3); var3 = this.__input._increment()) {
                        }

                        this.__input._decrement();
                        this.__getNextChar();
                     }
                     break label265;
                  case 'A':
                     var4 = this.__emitNode('\u0003');
                     var1[0] |= 2;
                     this.__getNextChar();
                     break label265;
                  case 'B':
                     var4 = this.__emitNode('\u0015');
                     var1[0] |= 2;
                     this.__getNextChar();
                     break label265;
                  case 'D':
                     var4 = this.__emitNode('\u0019');
                     var1[0] |= 3;
                     this.__getNextChar();
                     break label265;
                  case 'G':
                     var4 = this.__emitNode('\u001e');
                     var1[0] |= 2;
                     this.__getNextChar();
                     break label265;
                  case 'S':
                     var4 = this.__emitNode('\u0017');
                     var1[0] |= 3;
                     this.__getNextChar();
                     break label265;
                  case 'W':
                     var4 = this.__emitNode('\u0013');
                     var1[0] |= 3;
                     this.__getNextChar();
                     break label265;
                  case 'Z':
                     var4 = this.__emitNode('\u0006');
                     var1[0] |= 2;
                     this.__getNextChar();
                     break label265;
                  case 'b':
                     var4 = this.__emitNode('\u0014');
                     var1[0] |= 2;
                     this.__getNextChar();
                     break label265;
                  case 'd':
                     var4 = this.__emitNode('\u0018');
                     var1[0] |= 3;
                     this.__getNextChar();
                     break label265;
                  case 's':
                     var4 = this.__emitNode('\u0016');
                     var1[0] |= 3;
                     this.__getNextChar();
                     break label265;
                  case 'w':
                     var4 = this.__emitNode('\u0012');
                     var1[0] |= 3;
                     this.__getNextChar();
                     break label265;
               }
            case '^':
               this.__getNextChar();
               if ((this.__modifierFlags[0] & 8) != 0) {
                  var4 = this.__emitNode('\u0002');
               } else if ((this.__modifierFlags[0] & 16) != 0) {
                  var4 = this.__emitNode('\u0003');
               } else {
                  var4 = this.__emitNode('\u0001');
               }
               break label265;
         }
      }

      if (var2) {
         var4 = this.__emitNode('\u000e');
         this.__emitCode('\uffff');
         int var19 = 0;
         int var8 = this.__input._getOffset() - 1;

         label219:
         for(int var9 = this.__input._getLength(); var19 < 127 && var8 < var9; ++var19) {
            int var10;
            char var18;
            label214: {
               var10 = var8;
               var3 = this.__input._getValue(var8);
               switch (var3) {
                  case '#':
                     if ((this.__modifierFlags[0] & 32) != 0) {
                        while(var8 < var9 && this.__input._getValue(var8) != '\n') {
                           ++var8;
                        }
                     }
                  case '\t':
                  case '\n':
                  case '\u000b':
                  case '\f':
                  case '\r':
                  case ' ':
                     if ((this.__modifierFlags[0] & 32) != 0) {
                        ++var8;
                        --var19;
                        continue;
                     }
                     break;
                  case '$':
                  case '(':
                  case ')':
                  case '.':
                  case '[':
                  case '^':
                  case '|':
                     break label219;
                  case '\\':
                     ++var8;
                     var3 = this.__input._getValue(var8);
                     int[] var11;
                     switch (var3) {
                        case '\u0000':
                        case '\uffff':
                           if (var8 >= var9) {
                              throw new MalformedPatternException("Trailing \\ in expression.");
                           }
                        default:
                           var18 = this.__input._getValue(var8++);
                           break label214;
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                           boolean var12 = false;
                           var3 = this.__input._getValue(var8);
                           if (var3 == '0') {
                              var12 = true;
                           }

                           var3 = this.__input._getValue(var8 + 1);
                           if (Character.isDigit(var3)) {
                              StringBuffer var14 = new StringBuffer(10);
                              int var13 = var8;

                              for(var3 = this.__input._getValue(var8); Character.isDigit(var3); var3 = this.__input._getValue(var13)) {
                                 var14.append(var3);
                                 ++var13;
                              }

                              try {
                                 var13 = Integer.parseInt(var14.toString());
                              } catch (NumberFormatException var16) {
                                 throw new MalformedPatternException("Unexpected number format exception.  Please report this bug.NumberFormatException message: " + var16.getMessage());
                              }

                              if (!var12) {
                                 var12 = var13 >= this.__numParentheses;
                              }
                           }

                           if (!var12) {
                              --var8;
                              break label219;
                           }

                           var11 = new int[1];
                           var18 = (char)__parseOctal(this.__input._array, var8, 3, var11);
                           var8 += var11[0];
                           break label214;
                        case 'A':
                        case 'B':
                        case 'D':
                        case 'G':
                        case 'S':
                        case 'W':
                        case 'Z':
                        case 'b':
                        case 'd':
                        case 's':
                        case 'w':
                           --var8;
                           break label219;
                        case 'a':
                           var18 = 7;
                           ++var8;
                           break label214;
                        case 'c':
                           ++var8;
                           var18 = this.__input._getValue(var8++);
                           if (Character.isLowerCase(var18)) {
                              var18 = Character.toUpperCase(var18);
                           }

                           var18 = (char)(var18 ^ 64);
                           break label214;
                        case 'e':
                           var18 = 27;
                           ++var8;
                           break label214;
                        case 'f':
                           var18 = '\f';
                           ++var8;
                           break label214;
                        case 'n':
                           var18 = '\n';
                           ++var8;
                           break label214;
                        case 'r':
                           var18 = '\r';
                           ++var8;
                           break label214;
                        case 't':
                           var18 = '\t';
                           ++var8;
                           break label214;
                        case 'x':
                           var11 = new int[1];
                           ++var8;
                           var18 = (char)__parseHex(this.__input._array, var8, 2, var11);
                           var8 += var11[0];
                           break label214;
                     }
               }

               var18 = this.__input._getValue(var8++);
            }

            if ((this.__modifierFlags[0] & 1) != 0 && Character.isUpperCase(var18)) {
               var18 = Character.toLowerCase(var18);
            }

            if (var8 < var9 && __isComplexRepetitionOp(this.__input._array, var8)) {
               if (var19 > 0) {
                  var8 = var10;
               } else {
                  ++var19;
                  this.__emitCode(var18);
               }
               break;
            }

            this.__emitCode(var18);
         }

         this.__input._setOffset(var8 - 1);
         this.__getNextChar();
         if (var19 < 0) {
            throw new MalformedPatternException("Unexpected compilation failure.  Please report this bug!");
         }

         if (var19 > 0) {
            var1[0] |= 1;
         }

         if (var19 == 1) {
            var1[0] |= 2;
         }

         if (this.__program != null) {
            this.__program[OpCode._getOperand(var4)] = (char)var19;
         }

         this.__emitCode('\uffff');
      }

      return var4;
   }

   private int __parseUnicodeClass() throws MalformedPatternException {
      boolean var1 = false;
      char var4 = '\uffff';
      int[] var6 = new int[]{0};
      boolean[] var7 = new boolean[]{false};
      int var5;
      if (this.__input._getValue() == '^') {
         var5 = this.__emitNode('$');
         this.__input._increment();
      } else {
         var5 = this.__emitNode('#');
      }

      char var3 = this.__input._getValue();
      boolean var2;
      if (var3 != ']' && var3 != '-') {
         var2 = false;
      } else {
         var2 = true;
      }

      while(true) {
         while(!this.__input._isAtEnd() && (var3 = this.__input._getValue()) != ']' || var2) {
            var2 = false;
            boolean var8 = false;
            this.__input._increment();
            if (var3 == '\\' || var3 == '[') {
               if (var3 == '\\') {
                  var3 = this.__input._postIncrement();
               } else {
                  char var9 = this.__parsePOSIX(var7);
                  if (var9 != 0) {
                     var8 = true;
                     var3 = var9;
                  }
               }

               if (!var8) {
                  switch (var3) {
                     case '0':
                     case '1':
                     case '2':
                     case '3':
                     case '4':
                     case '5':
                     case '6':
                     case '7':
                     case '8':
                     case '9':
                        var3 = (char)__parseOctal(this.__input._array, this.__input._getOffset() - 1, 3, var6);
                        this.__input._increment(var6[0] - 1);
                     case ':':
                     case ';':
                     case '<':
                     case '=':
                     case '>':
                     case '?':
                     case '@':
                     case 'A':
                     case 'B':
                     case 'C':
                     case 'E':
                     case 'F':
                     case 'G':
                     case 'H':
                     case 'I':
                     case 'J':
                     case 'K':
                     case 'L':
                     case 'M':
                     case 'N':
                     case 'O':
                     case 'P':
                     case 'Q':
                     case 'R':
                     case 'T':
                     case 'U':
                     case 'V':
                     case 'X':
                     case 'Y':
                     case 'Z':
                     case '[':
                     case '\\':
                     case ']':
                     case '^':
                     case '_':
                     case '`':
                     case 'g':
                     case 'h':
                     case 'i':
                     case 'j':
                     case 'k':
                     case 'l':
                     case 'm':
                     case 'o':
                     case 'p':
                     case 'q':
                     case 'u':
                     case 'v':
                     default:
                        break;
                     case 'D':
                        var8 = true;
                        var3 = 25;
                        var4 = '\uffff';
                        break;
                     case 'S':
                        var8 = true;
                        var3 = 23;
                        var4 = '\uffff';
                        break;
                     case 'W':
                        var8 = true;
                        var3 = 19;
                        var4 = '\uffff';
                        break;
                     case 'a':
                        var3 = 7;
                        break;
                     case 'b':
                        var3 = '\b';
                        break;
                     case 'c':
                        var3 = this.__input._postIncrement();
                        if (Character.isLowerCase(var3)) {
                           var3 = Character.toUpperCase(var3);
                        }

                        var3 = (char)(var3 ^ 64);
                        break;
                     case 'd':
                        var8 = true;
                        var3 = 24;
                        var4 = '\uffff';
                        break;
                     case 'e':
                        var3 = 27;
                        break;
                     case 'f':
                        var3 = '\f';
                        break;
                     case 'n':
                        var3 = '\n';
                        break;
                     case 'r':
                        var3 = '\r';
                        break;
                     case 's':
                        var8 = true;
                        var3 = 22;
                        var4 = '\uffff';
                        break;
                     case 't':
                        var3 = '\t';
                        break;
                     case 'w':
                        var8 = true;
                        var3 = 18;
                        var4 = '\uffff';
                        break;
                     case 'x':
                        var3 = (char)__parseHex(this.__input._array, this.__input._getOffset(), 2, var6);
                        this.__input._increment(var6[0]);
                  }
               }
            }

            if (var1) {
               if (var4 > var3) {
                  throw new MalformedPatternException("Invalid [] range in expression.");
               }

               var1 = false;
            } else {
               var4 = var3;
               if (!var8 && this.__input._getValue() == '-' && this.__input._getOffset() + 1 < this.__input._getLength() && this.__input._getValueRelative(1) != ']') {
                  this.__input._increment();
                  var1 = true;
                  continue;
               }
            }

            if (var4 == var3) {
               if (var8) {
                  if (!var7[0]) {
                     this.__emitCode('/');
                  } else {
                     this.__emitCode('0');
                  }
               } else {
                  this.__emitCode('1');
               }

               this.__emitCode(var3);
               if ((this.__modifierFlags[0] & 1) != 0 && Character.isUpperCase(var3) && Character.isUpperCase(var4)) {
                  --this.__programSize;
                  this.__emitCode(Character.toLowerCase(var3));
               }
            }

            if (var4 < var3) {
               this.__emitCode('%');
               this.__emitCode(var4);
               this.__emitCode(var3);
               if ((this.__modifierFlags[0] & 1) != 0 && Character.isUpperCase(var3) && Character.isUpperCase(var4)) {
                  this.__programSize -= 2;
                  this.__emitCode(Character.toLowerCase(var4));
                  this.__emitCode(Character.toLowerCase(var3));
               }

               var4 = '\uffff';
               var1 = false;
            }

            var4 = var3;
         }

         if (this.__input._getValue() != ']') {
            throw new MalformedPatternException("Unmatched [] in expression.");
         }

         this.__getNextChar();
         this.__emitCode('\u0000');
         return var5;
      }
   }

   private char __parsePOSIX(boolean[] var1) throws MalformedPatternException {
      int var2 = this.__input._getOffset();
      int var3 = this.__input._getLength();
      int var4 = var2 + 1;
      char var5 = this.__input._getValue(var2);
      if (var5 != ':') {
         return '\u0000';
      } else {
         if (this.__input._getValue(var4) == '^') {
            var1[0] = true;
            ++var4;
         } else {
            var1[0] = false;
         }

         StringBuffer var6 = new StringBuffer();

         try {
            while((var5 = this.__input._getValue(var4++)) != ':' && var4 < var3) {
               var6.append(var5);
            }
         } catch (Exception var9) {
            return '\u0000';
         }

         if (this.__input._getValue(var4++) != ']') {
            return '\u0000';
         } else {
            Object var7 = __hashPOSIX.get(var6.toString());
            if (var7 == null) {
               return '\u0000';
            } else {
               this.__input._setOffset(var4);
               return (Character)var7;
            }
         }
      }
   }

   private int __parseBranch(int[] var1) throws MalformedPatternException {
      boolean var2 = false;
      boolean var3 = false;
      int[] var8 = new int[]{0};
      int var6 = 0;
      int var7 = 65535;
      int var4 = this.__parseAtom(var8);
      if (var4 == -1) {
         if ((var8[0] & 8) != 0) {
            var1[0] |= 8;
         }

         return -1;
      } else {
         char var9 = this.__input._getValue();
         if (var9 == '(' && this.__input._getValueRelative(1) == '?' && this.__input._getValueRelative(2) == '#') {
            while(var9 != '\uffff' && var9 != ')') {
               var9 = this.__input._increment();
            }

            if (var9 != '\uffff') {
               this.__getNextChar();
               var9 = this.__input._getValue();
            }
         }

         if (var9 == '{' && __parseRepetition(this.__input._array, this.__input._getOffset())) {
            int var5 = this.__input._getOffset() + 1;
            int var11;
            int var12 = var11 = this.__input._getLength();

            char var10;
            for(var10 = this.__input._getValue(var5); Character.isDigit(var10) || var10 == ','; var10 = this.__input._getValue(var5)) {
               if (var10 == ',') {
                  if (var12 != var11) {
                     break;
                  }

                  var12 = var5;
               }

               ++var5;
            }

            if (var10 == '}') {
               StringBuffer var14 = new StringBuffer(10);
               if (var12 == var11) {
                  var12 = var5;
               }

               this.__input._increment();
               int var13 = this.__input._getOffset();

               for(var10 = this.__input._getValue(var13); Character.isDigit(var10); var10 = this.__input._getValue(var13)) {
                  var14.append(var10);
                  ++var13;
               }

               try {
                  var6 = Integer.parseInt(var14.toString());
               } catch (NumberFormatException var16) {
                  throw new MalformedPatternException("Unexpected number format exception.  Please report this bug.NumberFormatException message: " + var16.getMessage());
               }

               var10 = this.__input._getValue(var12);
               if (var10 == ',') {
                  ++var12;
               } else {
                  var12 = this.__input._getOffset();
               }

               var13 = var12;
               var14 = new StringBuffer(10);

               for(var10 = this.__input._getValue(var12); Character.isDigit(var10); var10 = this.__input._getValue(var13)) {
                  var14.append(var10);
                  ++var13;
               }

               try {
                  if (var13 != var12) {
                     var7 = Integer.parseInt(var14.toString());
                  }
               } catch (NumberFormatException var17) {
                  throw new MalformedPatternException("Unexpected number format exception.  Please report this bug.NumberFormatException message: " + var17.getMessage());
               }

               if (var7 == 0 && this.__input._getValue(var12) != '0') {
                  var7 = 65535;
               }

               this.__input._setOffset(var5);
               this.__getNextChar();
               var2 = true;
               var3 = true;
            }
         }

         if (!var2) {
            var3 = false;
            if (!__isSimpleRepetitionOp(var9)) {
               var1[0] = var8[0];
               return var4;
            }

            this.__getNextChar();
            var1[0] = var9 != '+' ? 4 : 1;
            if (var9 == '*' && (var8[0] & 2) != 0) {
               this.__programInsertOperator('\u0010', var4);
               this.__cost += 4;
            } else if (var9 == '*') {
               var6 = 0;
               var3 = true;
            } else if (var9 == '+' && (var8[0] & 2) != 0) {
               this.__programInsertOperator('\u0011', var4);
               this.__cost += 3;
            } else if (var9 == '+') {
               var6 = 1;
               var3 = true;
            } else if (var9 == '?') {
               var6 = 0;
               var7 = 1;
               var3 = true;
            }
         }

         if (var3) {
            if ((var8[0] & 2) != 0) {
               this.__cost += (2 + this.__cost) / 2;
               this.__programInsertOperator('\n', var4);
            } else {
               this.__cost += 4 + this.__cost;
               this.__programAddTail(var4, this.__emitNode('"'));
               this.__programInsertOperator('\u000b', var4);
               this.__programAddTail(var4, this.__emitNode('\u000f'));
            }

            if (var6 > 0) {
               var1[0] = 1;
            }

            if (var7 != 0 && var7 < var6) {
               throw new MalformedPatternException("Invalid interval {" + var6 + "," + var7 + "}");
            }

            if (this.__program != null) {
               this.__program[var4 + 2] = (char)var6;
               this.__program[var4 + 3] = (char)var7;
            }
         }

         if (this.__input._getValue() == '?') {
            this.__getNextChar();
            this.__programInsertOperator('\u001d', var4);
            this.__programAddTail(var4, var4 + 2);
         }

         if (__isComplexRepetitionOp(this.__input._array, this.__input._getOffset())) {
            throw new MalformedPatternException("Nested repetitions *?+ in expression");
         } else {
            return var4;
         }
      }
   }

   private int __parseExpression(boolean var1, int[] var2) throws MalformedPatternException {
      char[] var6 = new char[]{'\u0000'};
      char[] var7 = new char[]{'\u0000'};
      int var8 = -1;
      int var9 = 0;
      int[] var12 = new int[]{0};
      String var13 = "iogmsx-";
      char[] var5 = var6;
      var2[0] = 1;
      char var4;
      if (var1) {
         var4 = 1;
         if (this.__input._getValue() == '?') {
            this.__input._increment();
            char var3;
            var4 = var3 = this.__input._postIncrement();
            switch (var3) {
               case '!':
               case ':':
               case '=':
                  break;
               case '#':
                  for(var3 = this.__input._getValue(); var3 != '\uffff' && var3 != ')'; var3 = this.__input._increment()) {
                  }

                  if (var3 != ')') {
                     throw new MalformedPatternException("Sequence (?#... not terminated");
                  }

                  this.__getNextChar();
                  var2[0] = 8;
                  return -1;
               default:
                  this.__input._decrement();

                  for(var3 = this.__input._getValue(); var3 != '\uffff' && var13.indexOf(var3) != -1; var3 = this.__input._increment()) {
                     if (var3 == '-') {
                        var5 = var7;
                     } else {
                        __setModifierFlag(var5, var3);
                     }
                  }

                  char[] var10000 = this.__modifierFlags;
                  var10000[0] |= var6[0];
                  var10000 = this.__modifierFlags;
                  var10000[0] = (char)(var10000[0] & ~var7[0]);
                  if (var3 != ')') {
                     throw new MalformedPatternException("Sequence (?" + var3 + "...) not recognized");
                  }

                  this.__getNextChar();
                  var2[0] = 8;
                  return -1;
            }
         } else {
            var9 = this.__numParentheses++;
            var8 = this.__emitArgNode('\u001b', (char)var9);
         }
      } else {
         var4 = 0;
      }

      int var10 = this.__parseAlternation(var12);
      if (var10 == -1) {
         return -1;
      } else {
         if (var8 != -1) {
            this.__programAddTail(var8, var10);
         } else {
            var8 = var10;
         }

         if ((var12[0] & 1) == 0) {
            var2[0] &= -2;
         }

         for(var2[0] |= var12[0] & 4; this.__input._getValue() == '|'; var2[0] |= var12[0] & 4) {
            this.__getNextChar();
            var10 = this.__parseAlternation(var12);
            if (var10 == -1) {
               return -1;
            }

            this.__programAddTail(var8, var10);
            if ((var12[0] & 1) == 0) {
               var2[0] &= -2;
            }
         }

         int var11;
         switch (var4) {
            case '\u0000':
            default:
               var11 = this.__emitNode('\u0000');
               break;
            case '\u0001':
               var11 = this.__emitArgNode('\u001c', (char)var9);
               break;
            case '!':
            case '=':
               var11 = this.__emitNode('!');
               var2[0] &= -2;
               break;
            case ':':
               var11 = this.__emitNode('\u000f');
         }

         this.__programAddTail(var8, var11);

         for(var10 = var8; var10 != -1; var10 = OpCode._getNext(this.__program, var10)) {
            this.__programAddOperatorTail(var10, var11);
         }

         if (var4 == '=') {
            this.__programInsertOperator('\u001f', var8);
            this.__programAddTail(var8, this.__emitNode('\u000f'));
         } else if (var4 == '!') {
            this.__programInsertOperator(' ', var8);
            this.__programAddTail(var8, this.__emitNode('\u000f'));
         }

         if (var4 == 0 || !this.__input._isAtEnd() && this.__getNextChar() == ')') {
            if (var4 == 0 && !this.__input._isAtEnd()) {
               if (this.__input._getValue() == ')') {
                  throw new MalformedPatternException("Unmatched parentheses.");
               } else {
                  throw new MalformedPatternException("Unreached characters at end of expression.  Please report this bug!");
               }
            } else {
               return var8;
            }
         } else {
            throw new MalformedPatternException("Unmatched parentheses.");
         }
      }
   }

   public Pattern compile(char[] var1, int var2) throws MalformedPatternException {
      int[] var3 = new int[]{0};
      boolean var10 = false;
      boolean var11 = false;
      int var15 = 0;
      this.__input = new CharStringPointer(var1);
      int var4 = var2 & 1;
      this.__modifierFlags[0] = (char)var2;
      this.__sawBackreference = false;
      this.__numParentheses = 1;
      this.__programSize = 0;
      this.__cost = 0;
      this.__program = null;
      this.__emitCode('\u0000');
      if (this.__parseExpression(false, var3) == -1) {
         throw new MalformedPatternException("Unknown compilation error.");
      } else if (this.__programSize >= 65534) {
         throw new MalformedPatternException("Expression is too large.");
      } else {
         this.__program = new char[this.__programSize];
         Perl5Pattern var6 = new Perl5Pattern();
         var6._program = this.__program;
         var6._expression = new String(var1);
         this.__input._setOffset(0);
         this.__numParentheses = 1;
         this.__programSize = 0;
         this.__cost = 0;
         this.__emitCode('\u0000');
         if (this.__parseExpression(false, var3) == -1) {
            throw new MalformedPatternException("Unknown compilation error.");
         } else {
            var4 = this.__modifierFlags[0] & 1;
            var6._isExpensive = this.__cost >= 10;
            var6._startClassOffset = -1;
            var6._anchor = 0;
            var6._back = -1;
            var6._options = var2;
            var6._startString = null;
            var6._mustString = null;
            String var7 = null;
            String var8 = null;
            int var5 = 1;
            if (this.__program[OpCode._getNext(this.__program, var5)] == 0) {
               int var9 = var5 = OpCode._getNextOperator(var5);
               char var20 = this.__program[var9];

               label221:
               while(true) {
                  label235: {
                     if (var20 == 27) {
                        var10 = true;
                        if (true) {
                           break label235;
                        }
                     }

                     if ((var20 != '\f' || this.__program[OpCode._getNext(this.__program, var9)] == '\f') && var20 != 17 && var20 != 29 && (OpCode._opType[var20] != '\n' || OpCode._getArg1(this.__program, var9) <= 0)) {
                        boolean var19 = true;

                        while(true) {
                           while(var19) {
                              var19 = false;
                              var20 = this.__program[var9];
                              if (var20 == 14) {
                                 var8 = new String(this.__program, OpCode._getOperand(var9 + 1), this.__program[OpCode._getOperand(var9)]);
                              } else if (OpCode._isInArray(var20, OpCode._opLengthOne, 2)) {
                                 var6._startClassOffset = var9;
                              } else if (var20 != 20 && var20 != 21) {
                                 if (OpCode._opType[var20] == 1) {
                                    if (var20 == 1) {
                                       var6._anchor = 1;
                                    } else if (var20 == 2) {
                                       var6._anchor = 2;
                                    } else {
                                       var6._anchor = 3;
                                    }

                                    var9 = OpCode._getNextOperator(var9);
                                    var19 = true;
                                 } else if (var20 == 16 && OpCode._opType[this.__program[OpCode._getNextOperator(var9)]] == 7 && (var6._anchor & 3) != 0) {
                                    var6._anchor = 11;
                                    var9 = OpCode._getNextOperator(var9);
                                    var19 = true;
                                 }
                              } else {
                                 var6._startClassOffset = var9;
                              }
                           }

                           if (var11 && (!var10 || !this.__sawBackreference)) {
                              var6._anchor |= 4;
                           }

                           StringBuffer var12 = new StringBuffer();
                           StringBuffer var13 = new StringBuffer();
                           int var14 = 0;
                           var15 = 0;
                           int var16 = 0;
                           int var17 = 0;
                           int var18 = 0;

                           while(true) {
                              while(true) {
                                 while(var5 > 0 && (var20 = this.__program[var5]) != 0) {
                                    if (var20 == '\f') {
                                       if (this.__program[OpCode._getNext(this.__program, var5)] == '\f') {
                                          for(var16 = -30000; this.__program[var5] == '\f'; var5 = OpCode._getNext(this.__program, var5)) {
                                          }
                                       } else {
                                          var5 = OpCode._getNextOperator(var5);
                                       }
                                    } else if (var20 == ' ') {
                                       var16 = -30000;
                                       var5 = OpCode._getNext(this.__program, var5);
                                    } else {
                                       if (var20 == 14) {
                                          int var21;
                                          for(var9 = var5; this.__program[var21 = OpCode._getNext(this.__program, var5)] == 28; var5 = var21) {
                                          }

                                          var15 += this.__program[OpCode._getOperand(var9)];
                                          char var22 = this.__program[OpCode._getOperand(var9)];
                                          if (var16 - var17 == var14) {
                                             var12.append(new String(this.__program, OpCode._getOperand(var9) + 1, var22));
                                             var14 += var22;
                                             var16 += var22;
                                             var9 = OpCode._getNext(this.__program, var5);
                                          } else if (var22 >= var14 + (var16 >= 0 ? 1 : 0)) {
                                             var14 = var22;
                                             var12 = new StringBuffer(new String(this.__program, OpCode._getOperand(var9) + 1, var22));
                                             var17 = var16;
                                             var16 += var22;
                                             var9 = OpCode._getNext(this.__program, var5);
                                          } else {
                                             var16 += var22;
                                          }
                                       } else if (OpCode._isInArray(var20, OpCode._opLengthVaries, 0)) {
                                          var16 = -30000;
                                          var14 = 0;
                                          if (var12.length() > var13.length()) {
                                             var13 = var12;
                                             var18 = var17;
                                          }

                                          var12 = new StringBuffer();
                                          if (var20 == 17 && OpCode._isInArray(this.__program[OpCode._getNextOperator(var5)], OpCode._opLengthOne, 0)) {
                                             ++var15;
                                          } else if (OpCode._opType[var20] == '\n' && OpCode._isInArray(this.__program[OpCode._getNextOperator(var5) + 2], OpCode._opLengthOne, 0)) {
                                             var15 += OpCode._getArg1(this.__program, var5);
                                          }
                                       } else if (OpCode._isInArray(var20, OpCode._opLengthOne, 0)) {
                                          ++var16;
                                          ++var15;
                                          var14 = 0;
                                          if (var12.length() > var13.length()) {
                                             var13 = var12;
                                             var18 = var17;
                                          }

                                          var12 = new StringBuffer();
                                       }

                                       var5 = OpCode._getNext(this.__program, var5);
                                    }
                                 }

                                 if (var12.length() + (OpCode._opType[this.__program[var9]] == 4 ? 1 : 0) > var13.length()) {
                                    var13 = var12;
                                    var18 = var17;
                                 } else {
                                    new StringBuffer();
                                 }

                                 if (var13.length() > 0 && var8 == null) {
                                    var7 = var13.toString();
                                    if (var18 < 0) {
                                       var18 = -1;
                                    }

                                    var6._back = var18;
                                 } else {
                                    var13 = null;
                                 }
                                 break label221;
                              }
                           }
                        }
                     }
                  }

                  if (var20 == 17) {
                     var11 = true;
                  } else {
                     var9 += OpCode._operandLength[var20];
                  }

                  var9 = OpCode._getNextOperator(var9);
                  var20 = this.__program[var9];
               }
            }

            var6._isCaseInsensitive = (var4 & 1) != 0;
            var6._numParentheses = this.__numParentheses - 1;
            var6._minLength = var15;
            if (var7 != null) {
               var6._mustString = var7.toCharArray();
               var6._mustUtility = 100;
            }

            if (var8 != null) {
               var6._startString = var8.toCharArray();
            }

            return var6;
         }
      }
   }

   public Pattern compile(char[] var1) throws MalformedPatternException {
      return this.compile((char[])var1, 0);
   }

   public Pattern compile(String var1) throws MalformedPatternException {
      return this.compile((char[])var1.toCharArray(), 0);
   }

   public Pattern compile(String var1, int var2) throws MalformedPatternException {
      return this.compile(var1.toCharArray(), var2);
   }

   static {
      __hashPOSIX.put("alnum", new Character('2'));
      __hashPOSIX.put("word", new Character('\u0012'));
      __hashPOSIX.put("alpha", new Character('&'));
      __hashPOSIX.put("blank", new Character('\''));
      __hashPOSIX.put("cntrl", new Character('('));
      __hashPOSIX.put("digit", new Character('\u0018'));
      __hashPOSIX.put("graph", new Character(')'));
      __hashPOSIX.put("lower", new Character('*'));
      __hashPOSIX.put("print", new Character('+'));
      __hashPOSIX.put("punct", new Character(','));
      __hashPOSIX.put("space", new Character('\u0016'));
      __hashPOSIX.put("upper", new Character('-'));
      __hashPOSIX.put("xdigit", new Character('.'));
      __hashPOSIX.put("ascii", new Character('3'));
   }
}
