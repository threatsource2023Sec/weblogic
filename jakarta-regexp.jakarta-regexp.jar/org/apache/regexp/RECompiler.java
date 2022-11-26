package org.apache.regexp;

import java.util.Hashtable;

public class RECompiler {
   char[] instruction = new char[128];
   int lenInstruction = 0;
   String pattern;
   int len;
   int idx;
   int parens;
   static final int NODE_NORMAL = 0;
   static final int NODE_NULLABLE = 1;
   static final int NODE_TOPLEVEL = 2;
   static final int ESC_MASK = 1048560;
   static final int ESC_BACKREF = 1048575;
   static final int ESC_COMPLEX = 1048574;
   static final int ESC_CLASS = 1048573;
   int maxBrackets = 10;
   static final int bracketUnbounded = -1;
   int brackets = 0;
   int[] bracketStart = null;
   int[] bracketEnd = null;
   int[] bracketMin = null;
   int[] bracketOpt = null;
   static Hashtable hashPOSIX = new Hashtable();

   void ensure(int var1) {
      int var2 = this.instruction.length;
      if (this.lenInstruction + var1 >= var2) {
         while(true) {
            if (this.lenInstruction + var1 < var2) {
               char[] var3 = new char[var2];
               System.arraycopy(this.instruction, 0, var3, 0, this.lenInstruction);
               this.instruction = var3;
               break;
            }

            var2 *= 2;
         }
      }

   }

   void emit(char var1) {
      this.ensure(1);
      this.instruction[this.lenInstruction++] = var1;
   }

   void nodeInsert(char var1, int var2, int var3) {
      this.ensure(3);
      System.arraycopy(this.instruction, var3, this.instruction, var3 + 3, this.lenInstruction - var3);
      this.instruction[var3 + 0] = var1;
      this.instruction[var3 + 1] = (char)var2;
      this.instruction[var3 + 2] = 0;
      this.lenInstruction += 3;
   }

   void setNextOfEnd(int var1, int var2) {
      for(char var3 = this.instruction[var1 + 2]; var3 != 0 && var1 < this.lenInstruction; var3 = this.instruction[var1 + 2]) {
         if (var1 == var2) {
            var2 = this.lenInstruction;
         }

         var1 += var3;
      }

      if (var1 < this.lenInstruction) {
         this.instruction[var1 + 2] = (char)((short)(var2 - var1));
      }

   }

   int node(char var1, int var2) {
      this.ensure(3);
      this.instruction[this.lenInstruction + 0] = var1;
      this.instruction[this.lenInstruction + 1] = (char)var2;
      this.instruction[this.lenInstruction + 2] = 0;
      this.lenInstruction += 3;
      return this.lenInstruction - 3;
   }

   void internalError() throws Error {
      throw new Error("Internal error!");
   }

   void syntaxError(String var1) throws RESyntaxException {
      throw new RESyntaxException(var1);
   }

   void allocBrackets() {
      if (this.bracketStart == null) {
         this.bracketStart = new int[this.maxBrackets];
         this.bracketEnd = new int[this.maxBrackets];
         this.bracketMin = new int[this.maxBrackets];
         this.bracketOpt = new int[this.maxBrackets];

         for(int var1 = 0; var1 < this.maxBrackets; ++var1) {
            this.bracketStart[var1] = this.bracketEnd[var1] = this.bracketMin[var1] = this.bracketOpt[var1] = -1;
         }
      }

   }

   synchronized void reallocBrackets() {
      if (this.bracketStart == null) {
         this.allocBrackets();
      }

      int var1 = this.maxBrackets * 2;
      int[] var2 = new int[var1];
      int[] var3 = new int[var1];
      int[] var4 = new int[var1];
      int[] var5 = new int[var1];

      for(int var6 = this.brackets; var6 < var1; ++var6) {
         var2[var6] = var3[var6] = var4[var6] = var5[var6] = -1;
      }

      System.arraycopy(this.bracketStart, 0, var2, 0, this.brackets);
      System.arraycopy(this.bracketEnd, 0, var3, 0, this.brackets);
      System.arraycopy(this.bracketMin, 0, var4, 0, this.brackets);
      System.arraycopy(this.bracketOpt, 0, var5, 0, this.brackets);
      this.bracketStart = var2;
      this.bracketEnd = var3;
      this.bracketMin = var4;
      this.bracketOpt = var5;
      this.maxBrackets = var1;
   }

   void bracket() throws RESyntaxException {
      if (this.idx >= this.len || this.pattern.charAt(this.idx++) != '{') {
         this.internalError();
      }

      if (this.idx >= this.len || !Character.isDigit(this.pattern.charAt(this.idx))) {
         this.syntaxError("Expected digit");
      }

      StringBuffer var1 = new StringBuffer();

      while(this.idx < this.len && Character.isDigit(this.pattern.charAt(this.idx))) {
         var1.append(this.pattern.charAt(this.idx++));
      }

      try {
         this.bracketMin[this.brackets] = Integer.parseInt(var1.toString());
      } catch (NumberFormatException var4) {
         this.syntaxError("Expected valid number");
      }

      if (this.idx >= this.len) {
         this.syntaxError("Expected comma or right bracket");
      }

      if (this.pattern.charAt(this.idx) == '}') {
         ++this.idx;
         this.bracketOpt[this.brackets] = 0;
      } else {
         if (this.idx >= this.len || this.pattern.charAt(this.idx++) != ',') {
            this.syntaxError("Expected comma");
         }

         if (this.idx >= this.len) {
            this.syntaxError("Expected comma or right bracket");
         }

         if (this.pattern.charAt(this.idx) == '}') {
            ++this.idx;
            this.bracketOpt[this.brackets] = -1;
         } else {
            if (this.idx >= this.len || !Character.isDigit(this.pattern.charAt(this.idx))) {
               this.syntaxError("Expected digit");
            }

            var1.setLength(0);

            while(this.idx < this.len && Character.isDigit(this.pattern.charAt(this.idx))) {
               var1.append(this.pattern.charAt(this.idx++));
            }

            try {
               this.bracketOpt[this.brackets] = Integer.parseInt(var1.toString()) - this.bracketMin[this.brackets];
            } catch (NumberFormatException var3) {
               this.syntaxError("Expected valid number");
            }

            if (this.bracketOpt[this.brackets] < 0) {
               this.syntaxError("Bad range");
            }

            if (this.idx >= this.len || this.pattern.charAt(this.idx++) != '}') {
               this.syntaxError("Missing close brace");
            }

         }
      }
   }

   int escape() throws RESyntaxException {
      if (this.pattern.charAt(this.idx) != '\\') {
         this.internalError();
      }

      if (this.idx + 1 == this.len) {
         this.syntaxError("Escape terminates string");
      }

      this.idx += 2;
      char var1 = this.pattern.charAt(this.idx - 1);
      int var2;
      switch (var1) {
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
            if ((this.idx >= this.len || !Character.isDigit(this.pattern.charAt(this.idx))) && var1 != '0') {
               return 1048575;
            } else {
               var2 = var1 - 48;
               if (this.idx < this.len && Character.isDigit(this.pattern.charAt(this.idx))) {
                  var2 = (var2 << 3) + (this.pattern.charAt(this.idx++) - 48);
                  if (this.idx < this.len && Character.isDigit(this.pattern.charAt(this.idx))) {
                     var2 = (var2 << 3) + (this.pattern.charAt(this.idx++) - 48);
                  }
               }

               return var2;
            }
         case ':':
         case ';':
         case '<':
         case '=':
         case '>':
         case '?':
         case '@':
         case 'A':
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
         case 'a':
         case 'c':
         case 'e':
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
         case 'v':
         default:
            return var1;
         case 'B':
         case 'b':
            return 1048574;
         case 'D':
         case 'S':
         case 'W':
         case 'd':
         case 's':
         case 'w':
            return 1048573;
         case 'f':
            return 12;
         case 'n':
            return 10;
         case 'r':
            return 13;
         case 't':
            return 9;
         case 'u':
         case 'x':
            var2 = var1 == 'u' ? 4 : 2;

            int var3;
            for(var3 = 0; this.idx < this.len && var2-- > 0; ++this.idx) {
               char var4 = this.pattern.charAt(this.idx);
               if (var4 >= '0' && var4 <= '9') {
                  var3 = (var3 << 4) + var4 - 48;
               } else {
                  var4 = Character.toLowerCase(var4);
                  if (var4 >= 'a' && var4 <= 'f') {
                     var3 = (var3 << 4) + (var4 - 97) + 10;
                  } else {
                     this.syntaxError("Expected " + var2 + " hexadecimal digits after \\" + var1);
                  }
               }
            }

            return var3;
      }
   }

   int characterClass() throws RESyntaxException {
      if (this.pattern.charAt(this.idx) != '[') {
         this.internalError();
      }

      if (this.idx + 1 >= this.len || this.pattern.charAt(++this.idx) == ']') {
         this.syntaxError("Empty or unterminated class");
      }

      int var1;
      if (this.idx < this.len && this.pattern.charAt(this.idx) == ':') {
         ++this.idx;

         for(var1 = this.idx; this.idx < this.len && this.pattern.charAt(this.idx) >= 'a' && this.pattern.charAt(this.idx) <= 'z'; ++this.idx) {
         }

         if (this.idx + 1 < this.len && this.pattern.charAt(this.idx) == ':' && this.pattern.charAt(this.idx + 1) == ']') {
            String var2 = this.pattern.substring(var1, this.idx);
            Character var3 = (Character)hashPOSIX.get(var2);
            if (var3 != null) {
               this.idx += 2;
               return this.node('P', var3);
            }

            this.syntaxError("Invalid POSIX character class '" + var2 + "'");
         }

         this.syntaxError("Invalid POSIX character class syntax");
      }

      var1 = this.node('[', 0);
      char var12 = '\uffff';
      char var13 = var12;
      boolean var4 = false;
      boolean var5 = true;
      boolean var6 = false;
      int var7 = this.idx;
      char var8 = 0;
      RERange var10 = new RERange();

      int var11;
      while(this.idx < this.len && this.pattern.charAt(this.idx) != ']') {
         char var14;
         label107:
         switch (this.pattern.charAt(this.idx)) {
            case '-':
               if (var6) {
                  this.syntaxError("Bad class range");
               }

               var6 = true;
               var8 = var13 == var12 ? 0 : var13;
               if (this.idx + 1 >= this.len || this.pattern.charAt(++this.idx) != ']') {
                  continue;
               }

               var14 = '\uffff';
               break;
            case '\\':
               switch (var11 = this.escape()) {
                  case 1048574:
                  case 1048575:
                     this.syntaxError("Bad character class");
                  case 1048573:
                     if (var6) {
                        this.syntaxError("Bad character class");
                     }

                     switch (this.pattern.charAt(this.idx - 1)) {
                        case 'D':
                           var10.include(0, 47, var5);
                           var10.include(58, 65535, var5);
                           break;
                        case 'S':
                           var10.include(0, 7, var5);
                           var10.include('\u000b', var5);
                           var10.include(14, 31, var5);
                           var10.include(33, 65535, var5);
                           break;
                        case 'W':
                           var10.include(0, 47, var5);
                           var10.include(58, 64, var5);
                           var10.include(91, 94, var5);
                           var10.include('`', var5);
                           var10.include(123, 65535, var5);
                           break;
                        case 's':
                           var10.include('\t', var5);
                           var10.include('\r', var5);
                           var10.include('\f', var5);
                           var10.include('\n', var5);
                           var10.include('\b', var5);
                           var10.include(' ', var5);
                           break;
                        case 'w':
                           var10.include(97, 122, var5);
                           var10.include(65, 90, var5);
                           var10.include('_', var5);
                        case 'd':
                           var10.include(48, 57, var5);
                     }

                     var13 = var12;
                     continue;
                  default:
                     var14 = (char)var11;
                     break label107;
               }
            case '^':
               var5 = !var5;
               if (this.idx == var7) {
                  var10.include(0, 65535, true);
               }

               ++this.idx;
               continue;
            default:
               var14 = this.pattern.charAt(this.idx++);
         }

         if (var6) {
            if (var8 >= var14) {
               this.syntaxError("Bad character class");
            }

            var10.include(var8, var14, var5);
            var13 = var12;
            var6 = false;
         } else {
            if (this.idx >= this.len || this.pattern.charAt(this.idx) != '-') {
               var10.include(var14, var5);
            }

            var13 = var14;
         }
      }

      if (this.idx == this.len) {
         this.syntaxError("Unterminated character class");
      }

      ++this.idx;
      this.instruction[var1 + 1] = (char)var10.num;

      for(var11 = 0; var11 < var10.num; ++var11) {
         this.emit((char)var10.minRange[var11]);
         this.emit((char)var10.maxRange[var11]);
      }

      return var1;
   }

   int atom() throws RESyntaxException {
      int var1 = this.node('A', 0);
      int var2 = 0;

      label46:
      while(this.idx < this.len) {
         int var3;
         int var4;
         if (this.idx + 1 < this.len) {
            var3 = this.pattern.charAt(this.idx + 1);
            if (this.pattern.charAt(this.idx) == '\\') {
               var4 = this.idx;
               this.escape();
               if (this.idx < this.len) {
                  var3 = this.pattern.charAt(this.idx);
               }

               this.idx = var4;
            }

            switch (var3) {
               case 42:
               case 43:
               case 63:
               case 123:
                  if (var2 != 0) {
                     break label46;
                  }
            }
         }

         switch (this.pattern.charAt(this.idx)) {
            case '$':
            case '(':
            case ')':
            case '.':
            case '[':
            case ']':
            case '^':
            case '|':
               break label46;
            case '*':
            case '+':
            case '?':
            case '{':
               if (var2 == 0) {
                  this.syntaxError("Missing operand to closure");
               }
               break label46;
            case '\\':
               var3 = this.idx;
               var4 = this.escape();
               if ((var4 & 1048560) == 1048560) {
                  this.idx = var3;
                  break label46;
               }

               this.emit((char)var4);
               ++var2;
               break;
            default:
               this.emit(this.pattern.charAt(this.idx++));
               ++var2;
         }
      }

      if (var2 == 0) {
         this.internalError();
      }

      this.instruction[var1 + 1] = (char)var2;
      return var1;
   }

   int terminal(int[] var1) throws RESyntaxException {
      switch (this.pattern.charAt(this.idx)) {
         case '$':
         case '.':
         case '^':
            return this.node(this.pattern.charAt(this.idx++), 0);
         case '(':
            return this.expr(var1);
         case ')':
            this.syntaxError("Unexpected close paren");
         case '|':
            this.internalError();
         case ']':
            this.syntaxError("Mismatched class");
         case '\u0000':
            this.syntaxError("Unexpected end of input");
         case '*':
         case '+':
         case '?':
         case '{':
            this.syntaxError("Missing operand to closure");
         case '\\':
            int var2 = this.idx;
            switch (this.escape()) {
               case 1048573:
               case 1048574:
                  var1[0] &= -2;
                  return this.node('\\', this.pattern.charAt(this.idx - 1));
               case 1048575:
                  char var3 = (char)(this.pattern.charAt(this.idx - 1) - 48);
                  if (this.parens <= var3) {
                     this.syntaxError("Bad backreference");
                  }

                  var1[0] |= 1;
                  return this.node('#', var3);
               default:
                  this.idx = var2;
                  var1[0] &= -2;
            }
         default:
            var1[0] &= -2;
            return this.atom();
         case '[':
            return this.characterClass();
      }
   }

   int closure(int[] var1) throws RESyntaxException {
      int var2 = this.idx;
      int[] var3 = new int[]{0};
      int var4 = this.terminal(var3);
      var1[0] |= var3[0];
      if (this.idx >= this.len) {
         return var4;
      } else {
         boolean var5 = true;
         char var6 = this.pattern.charAt(this.idx);
         switch (var6) {
            case '*':
            case '?':
               var1[0] |= 1;
            case '+':
               ++this.idx;
            case '{':
               char var7 = this.instruction[var4 + 0];
               if (var7 == '^' || var7 == '$') {
                  this.syntaxError("Bad closure operand");
               }

               if ((var3[0] & 1) != 0) {
                  this.syntaxError("Closure operand can't be nullable");
               }
            default:
               if (this.idx < this.len && this.pattern.charAt(this.idx) == '?') {
                  ++this.idx;
                  var5 = false;
               }

               if (var5) {
                  int var11;
                  switch (var6) {
                     case '+':
                        var11 = this.node('|', 0);
                        this.setNextOfEnd(var4, var11);
                        this.setNextOfEnd(this.node('G', 0), var4);
                        this.setNextOfEnd(var11, this.node('|', 0));
                        this.setNextOfEnd(var4, this.node('N', 0));
                        break;
                     case '{':
                        boolean var8 = false;
                        this.allocBrackets();

                        int var9;
                        for(var9 = 0; var9 < this.brackets; ++var9) {
                           if (this.bracketStart[var9] == this.idx) {
                              var8 = true;
                              break;
                           }
                        }

                        if (!var8) {
                           if (this.brackets >= this.maxBrackets) {
                              this.reallocBrackets();
                           }

                           this.bracketStart[this.brackets] = this.idx;
                           this.bracket();
                           this.bracketEnd[this.brackets] = this.idx;
                           var9 = this.brackets++;
                        }

                        if (this.bracketMin[var9]-- > 0) {
                           if (this.bracketMin[var9] <= 0 && this.bracketOpt[var9] == 0) {
                              this.idx = this.bracketEnd[var9];
                           } else {
                              for(int var10 = 0; var10 < this.brackets; ++var10) {
                                 if (var10 != var9 && this.bracketStart[var10] < this.idx && this.bracketStart[var10] >= var2) {
                                    --this.brackets;
                                    this.bracketStart[var10] = this.bracketStart[this.brackets];
                                    this.bracketEnd[var10] = this.bracketEnd[this.brackets];
                                    this.bracketMin[var10] = this.bracketMin[this.brackets];
                                    this.bracketOpt[var10] = this.bracketOpt[this.brackets];
                                 }
                              }

                              this.idx = var2;
                           }
                           break;
                        } else if (this.bracketOpt[var9] == -1) {
                           var6 = '*';
                           this.bracketOpt[var9] = 0;
                           this.idx = this.bracketEnd[var9];
                        } else if (this.bracketOpt[var9]-- <= 0) {
                           this.lenInstruction = var4;
                           this.node('N', 0);
                           this.idx = this.bracketEnd[var9];
                           break;
                        } else {
                           if (this.bracketOpt[var9] > 0) {
                              this.idx = var2;
                           } else {
                              this.idx = this.bracketEnd[var9];
                           }

                           var6 = '?';
                        }
                     case '*':
                     case '?':
                        if (var5) {
                           if (var6 == '?') {
                              this.nodeInsert('|', 0, var4);
                              this.setNextOfEnd(var4, this.node('|', 0));
                              var11 = this.node('N', 0);
                              this.setNextOfEnd(var4, var11);
                              this.setNextOfEnd(var4 + 3, var11);
                           }

                           if (var6 == '*') {
                              this.nodeInsert('|', 0, var4);
                              this.setNextOfEnd(var4 + 3, this.node('|', 0));
                              this.setNextOfEnd(var4 + 3, this.node('G', 0));
                              this.setNextOfEnd(var4 + 3, var4);
                              this.setNextOfEnd(var4, this.node('|', 0));
                              this.setNextOfEnd(var4, this.node('N', 0));
                           }
                        }
                  }
               } else {
                  this.setNextOfEnd(var4, this.node('E', 0));
                  switch (var6) {
                     case '*':
                        this.nodeInsert('8', 0, var4);
                        break;
                     case '+':
                        this.nodeInsert('=', 0, var4);
                        break;
                     case '?':
                        this.nodeInsert('/', 0, var4);
                  }

                  this.setNextOfEnd(var4, this.lenInstruction);
               }

               return var4;
         }
      }
   }

   int branch(int[] var1) throws RESyntaxException {
      int var3 = this.node('|', 0);
      int var4 = -1;
      int[] var5 = new int[1];

      int var2;
      boolean var6;
      for(var6 = true; this.idx < this.len && this.pattern.charAt(this.idx) != '|' && this.pattern.charAt(this.idx) != ')'; var4 = var2) {
         var5[0] = 0;
         var2 = this.closure(var5);
         if (var5[0] == 0) {
            var6 = false;
         }

         if (var4 != -1) {
            this.setNextOfEnd(var4, var2);
         }
      }

      if (var4 == -1) {
         this.node('N', 0);
      }

      if (var6) {
         var1[0] |= 1;
      }

      return var3;
   }

   int expr(int[] var1) throws RESyntaxException {
      byte var2 = -1;
      int var3 = -1;
      int var4 = this.parens;
      if ((var1[0] & 2) == 0 && this.pattern.charAt(this.idx) == '(') {
         if (this.idx + 2 < this.len && this.pattern.charAt(this.idx + 1) == '?' && this.pattern.charAt(this.idx + 2) == ':') {
            var2 = 2;
            this.idx += 3;
            var3 = this.node('<', 0);
         } else {
            var2 = 1;
            ++this.idx;
            var3 = this.node('(', this.parens++);
         }
      }

      var1[0] &= -3;
      int var5 = this.branch(var1);
      if (var3 == -1) {
         var3 = var5;
      } else {
         this.setNextOfEnd(var3, var5);
      }

      while(this.idx < this.len && this.pattern.charAt(this.idx) == '|') {
         ++this.idx;
         var5 = this.branch(var1);
         this.setNextOfEnd(var3, var5);
      }

      int var6;
      if (var2 > 0) {
         if (this.idx < this.len && this.pattern.charAt(this.idx) == ')') {
            ++this.idx;
         } else {
            this.syntaxError("Missing close paren");
         }

         if (var2 == 1) {
            var6 = this.node(')', var4);
         } else {
            var6 = this.node('>', 0);
         }
      } else {
         var6 = this.node('E', 0);
      }

      this.setNextOfEnd(var3, var6);
      int var7 = var3;

      for(char var8 = this.instruction[var3 + 2]; var8 != 0 && var7 < this.lenInstruction; var7 += var8) {
         if (this.instruction[var7 + 0] == '|') {
            this.setNextOfEnd(var7 + 3, var6);
         }

         var8 = this.instruction[var7 + 2];
      }

      return var3;
   }

   public REProgram compile(String var1) throws RESyntaxException {
      this.pattern = var1;
      this.len = var1.length();
      this.idx = 0;
      this.lenInstruction = 0;
      this.parens = 1;
      this.brackets = 0;
      int[] var2 = new int[]{2};
      this.expr(var2);
      if (this.idx != this.len) {
         if (var1.charAt(this.idx) == ')') {
            this.syntaxError("Unmatched close paren");
         }

         this.syntaxError("Unexpected input remains");
      }

      char[] var3 = new char[this.lenInstruction];
      System.arraycopy(this.instruction, 0, var3, 0, this.lenInstruction);
      return new REProgram(this.parens, var3);
   }

   static {
      hashPOSIX.put("alnum", new Character('w'));
      hashPOSIX.put("alpha", new Character('a'));
      hashPOSIX.put("blank", new Character('b'));
      hashPOSIX.put("cntrl", new Character('c'));
      hashPOSIX.put("digit", new Character('d'));
      hashPOSIX.put("graph", new Character('g'));
      hashPOSIX.put("lower", new Character('l'));
      hashPOSIX.put("print", new Character('p'));
      hashPOSIX.put("punct", new Character('!'));
      hashPOSIX.put("space", new Character('s'));
      hashPOSIX.put("upper", new Character('u'));
      hashPOSIX.put("xdigit", new Character('x'));
      hashPOSIX.put("javastart", new Character('j'));
      hashPOSIX.put("javapart", new Character('k'));
   }

   class RERange {
      int size = 16;
      int[] minRange;
      int[] maxRange;
      int num;

      RERange() {
         this.minRange = new int[this.size];
         this.maxRange = new int[this.size];
         this.num = 0;
      }

      void delete(int var1) {
         if (this.num != 0 && var1 < this.num) {
            while(true) {
               ++var1;
               if (var1 >= this.num) {
                  --this.num;
                  return;
               }

               if (var1 - 1 >= 0) {
                  this.minRange[var1 - 1] = this.minRange[var1];
                  this.maxRange[var1 - 1] = this.maxRange[var1];
               }
            }
         }
      }

      void merge(int var1, int var2) {
         for(int var3 = 0; var3 < this.num; ++var3) {
            if (var1 >= this.minRange[var3] && var2 <= this.maxRange[var3]) {
               return;
            }

            if (var1 <= this.minRange[var3] && var2 >= this.maxRange[var3]) {
               this.delete(var3);
               this.merge(var1, var2);
               return;
            }

            if (var1 >= this.minRange[var3] && var1 <= this.maxRange[var3]) {
               var1 = this.minRange[var3];
               this.delete(var3);
               this.merge(var1, var2);
               return;
            }

            if (var2 >= this.minRange[var3] && var2 <= this.maxRange[var3]) {
               var2 = this.maxRange[var3];
               this.delete(var3);
               this.merge(var1, var2);
               return;
            }
         }

         if (this.num >= this.size) {
            this.size *= 2;
            int[] var4 = new int[this.size];
            int[] var5 = new int[this.size];
            System.arraycopy(this.minRange, 0, var4, 0, this.num);
            System.arraycopy(this.maxRange, 0, var5, 0, this.num);
            this.minRange = var4;
            this.maxRange = var5;
         }

         this.minRange[this.num] = var1;
         this.maxRange[this.num] = var2;
         ++this.num;
      }

      void remove(int var1, int var2) {
         for(int var3 = 0; var3 < this.num; ++var3) {
            if (this.minRange[var3] >= var1 && this.maxRange[var3] <= var2) {
               this.delete(var3);
               --var3;
               return;
            }

            if (var1 >= this.minRange[var3] && var2 <= this.maxRange[var3]) {
               int var4 = this.minRange[var3];
               int var5 = this.maxRange[var3];
               this.delete(var3);
               if (var4 < var1) {
                  this.merge(var4, var1 - 1);
               }

               if (var2 < var5) {
                  this.merge(var2 + 1, var5);
               }

               return;
            }

            if (this.minRange[var3] >= var1 && this.minRange[var3] <= var2) {
               this.minRange[var3] = var2 + 1;
               return;
            }

            if (this.maxRange[var3] >= var1 && this.maxRange[var3] <= var2) {
               this.maxRange[var3] = var1 - 1;
               return;
            }
         }

      }

      void include(int var1, int var2, boolean var3) {
         if (var3) {
            this.merge(var1, var2);
         } else {
            this.remove(var1, var2);
         }

      }

      void include(char var1, boolean var2) {
         this.include(var1, var1, var2);
      }
   }
}
