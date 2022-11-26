package org.mozilla.javascript;

import java.io.IOException;
import java.io.Reader;

public class TokenStream {
   public static final int TSF_NEWLINES = 1;
   public static final int TSF_FUNCTION = 2;
   public static final int TSF_RETURN_EXPR = 4;
   public static final int TSF_RETURN_VOID = 8;
   public static final int TSF_REGEXP = 16;
   private static final int EOF_CHAR = -1;
   public static final int ERROR = -1;
   public static final int EOF = 0;
   public static final int EOL = 1;
   public static final int POPV = 2;
   public static final int ENTERWITH = 3;
   public static final int LEAVEWITH = 4;
   public static final int RETURN = 5;
   public static final int GOTO = 6;
   public static final int IFEQ = 7;
   public static final int IFNE = 8;
   public static final int DUP = 9;
   public static final int SETNAME = 10;
   public static final int BITOR = 11;
   public static final int BITXOR = 12;
   public static final int BITAND = 13;
   public static final int EQ = 14;
   public static final int NE = 15;
   public static final int LT = 16;
   public static final int LE = 17;
   public static final int GT = 18;
   public static final int GE = 19;
   public static final int LSH = 20;
   public static final int RSH = 21;
   public static final int URSH = 22;
   public static final int ADD = 23;
   public static final int SUB = 24;
   public static final int MUL = 25;
   public static final int DIV = 26;
   public static final int MOD = 27;
   public static final int BITNOT = 28;
   public static final int NEG = 29;
   public static final int NEW = 30;
   public static final int DELPROP = 31;
   public static final int TYPEOF = 32;
   public static final int NAMEINC = 33;
   public static final int PROPINC = 34;
   public static final int ELEMINC = 35;
   public static final int NAMEDEC = 36;
   public static final int PROPDEC = 37;
   public static final int ELEMDEC = 38;
   public static final int GETPROP = 39;
   public static final int SETPROP = 40;
   public static final int GETELEM = 41;
   public static final int SETELEM = 42;
   public static final int CALL = 43;
   public static final int NAME = 44;
   public static final int NUMBER = 45;
   public static final int STRING = 46;
   public static final int ZERO = 47;
   public static final int ONE = 48;
   public static final int NULL = 49;
   public static final int THIS = 50;
   public static final int FALSE = 51;
   public static final int TRUE = 52;
   public static final int SHEQ = 53;
   public static final int SHNE = 54;
   public static final int CLOSURE = 55;
   public static final int OBJECT = 56;
   public static final int POP = 57;
   public static final int POS = 58;
   public static final int VARINC = 59;
   public static final int VARDEC = 60;
   public static final int BINDNAME = 61;
   public static final int THROW = 62;
   public static final int IN = 63;
   public static final int INSTANCEOF = 64;
   public static final int GOSUB = 65;
   public static final int RETSUB = 66;
   public static final int CALLSPECIAL = 67;
   public static final int GETTHIS = 68;
   public static final int NEWTEMP = 69;
   public static final int USETEMP = 70;
   public static final int GETBASE = 71;
   public static final int GETVAR = 72;
   public static final int SETVAR = 73;
   public static final int UNDEFINED = 74;
   public static final int TRY = 75;
   public static final int ENDTRY = 76;
   public static final int NEWSCOPE = 77;
   public static final int TYPEOFNAME = 78;
   public static final int ENUMINIT = 79;
   public static final int ENUMNEXT = 80;
   public static final int GETPROTO = 81;
   public static final int GETPARENT = 82;
   public static final int SETPROTO = 83;
   public static final int SETPARENT = 84;
   public static final int SCOPE = 85;
   public static final int GETSCOPEPARENT = 86;
   public static final int THISFN = 87;
   public static final int JTHROW = 88;
   public static final int SEMI = 89;
   public static final int LB = 90;
   public static final int RB = 91;
   public static final int LC = 92;
   public static final int RC = 93;
   public static final int LP = 94;
   public static final int RP = 95;
   public static final int COMMA = 96;
   public static final int ASSIGN = 97;
   public static final int HOOK = 98;
   public static final int COLON = 99;
   public static final int OR = 100;
   public static final int AND = 101;
   public static final int EQOP = 102;
   public static final int RELOP = 103;
   public static final int SHOP = 104;
   public static final int UNARYOP = 105;
   public static final int INC = 106;
   public static final int DEC = 107;
   public static final int DOT = 108;
   public static final int PRIMARY = 109;
   public static final int FUNCTION = 110;
   public static final int EXPORT = 111;
   public static final int IMPORT = 112;
   public static final int IF = 113;
   public static final int ELSE = 114;
   public static final int SWITCH = 115;
   public static final int CASE = 116;
   public static final int DEFAULT = 117;
   public static final int WHILE = 118;
   public static final int DO = 119;
   public static final int FOR = 120;
   public static final int BREAK = 121;
   public static final int CONTINUE = 122;
   public static final int VAR = 123;
   public static final int WITH = 124;
   public static final int CATCH = 125;
   public static final int FINALLY = 126;
   public static final int RESERVED = 127;
   public static final int NOP = 128;
   public static final int NOT = 129;
   public static final int PRE = 130;
   public static final int POST = 131;
   public static final int VOID = 132;
   public static final int BLOCK = 133;
   public static final int ARRAYLIT = 134;
   public static final int OBJLIT = 135;
   public static final int LABEL = 136;
   public static final int TARGET = 137;
   public static final int LOOP = 138;
   public static final int ENUMDONE = 139;
   public static final int EXPRSTMT = 140;
   public static final int PARENT = 141;
   public static final int CONVERT = 142;
   public static final int JSR = 143;
   public static final int NEWLOCAL = 144;
   public static final int USELOCAL = 145;
   public static final int SCRIPT = 146;
   public static final int LINE = 147;
   public static final int SOURCEFILE = 148;
   public static final int BREAKPOINT = 149;
   private static String[] names;
   private LineBuffer in;
   public int flags;
   public String regExpFlags;
   private String sourceName;
   private String line;
   private Scriptable scope;
   private int pushbackToken;
   private int tokenno;
   private int op;
   private String string = "";
   private Number number;

   public TokenStream(Reader var1, Scriptable var2, String var3, int var4) {
      this.in = new LineBuffer(var1, var4);
      this.scope = var2;
      this.pushbackToken = 0;
      this.sourceName = var3;
      this.flags = 0;
   }

   private static void checkNames() {
   }

   public void clearPushback() {
      this.pushbackToken = 0;
   }

   public boolean eof() {
      return this.in.eof();
   }

   public String getLine() {
      return this.in.getLine();
   }

   public int getLineno() {
      return this.in.getLineno();
   }

   public Number getNumber() {
      return this.number;
   }

   public int getOffset() {
      return this.in.getOffset();
   }

   public int getOp() {
      return this.op;
   }

   public Scriptable getScope() {
      return this.scope;
   }

   public String getSourceName() {
      return this.sourceName;
   }

   public String getString() {
      return this.string;
   }

   public int getToken() throws IOException {
      ++this.tokenno;
      if (this.pushbackToken != 0) {
         int var13 = this.pushbackToken;
         this.pushbackToken = 0;
         return var13;
      } else {
         int var1;
         do {
            var1 = this.in.read();
         } while((var1 != 10 || (this.flags & 1) == 0) && (isJSSpace(var1) || var1 == 10));

         if (var1 == -1) {
            return 0;
         } else {
            boolean var2 = false;
            if (var1 == 92) {
               var1 = this.in.read();
               if (var1 == 117) {
                  var2 = true;
               } else {
                  var1 = 92;
               }

               this.in.unread();
            }

            int var4;
            int var7;
            int var8;
            int var9;
            if (!var2 && !Character.isJavaIdentifierStart((char)var1)) {
               if (!isDigit(var1) && (var1 != 46 || !isDigit(this.in.peek()))) {
                  StringBuffer var17;
                  if (var1 != 34 && var1 != 39) {
                     switch (var1) {
                        case 10:
                           return 1;
                        case 33:
                           if (this.in.match('=')) {
                              if (this.in.match('=')) {
                                 this.op = 54;
                              } else {
                                 this.op = 15;
                              }

                              return 102;
                           }

                           this.op = 129;
                           return 105;
                        case 37:
                           this.op = 27;
                           if (this.in.match('=')) {
                              return 97;
                           }

                           return 27;
                        case 38:
                           if (this.in.match('&')) {
                              return 101;
                           } else {
                              if (this.in.match('=')) {
                                 this.op = 13;
                                 return 97;
                              }

                              return 13;
                           }
                        case 40:
                           return 94;
                        case 41:
                           return 95;
                        case 42:
                           if (this.in.match('=')) {
                              this.op = 25;
                              return 97;
                           }

                           return 25;
                        case 43:
                        case 45:
                           if (this.in.match('=')) {
                              if (var1 == 43) {
                                 this.op = 23;
                                 return 97;
                              }

                              this.op = 24;
                              return 97;
                           } else if (this.in.match((char)var1)) {
                              if (var1 == 43) {
                                 return 106;
                              }

                              return 107;
                           } else {
                              if (var1 == 45) {
                                 return 24;
                              }

                              return 23;
                           }
                        case 44:
                           return 96;
                        case 46:
                           return 108;
                        case 47:
                           if (this.in.match('/')) {
                              while((var1 = this.in.read()) != -1 && var1 != 10) {
                              }

                              this.in.unread();
                              return this.getToken();
                           } else if (this.in.match('*')) {
                              while((var1 = this.in.read()) != -1 && (var1 != 42 || !this.in.match('/'))) {
                                 if (var1 != 10 && var1 == 47 && this.in.match('*')) {
                                    if (this.in.match('/')) {
                                       return this.getToken();
                                    }

                                    this.reportSyntaxError("msg.nested.comment", (Object[])null);
                                    return -1;
                                 }
                              }

                              if (var1 == -1) {
                                 this.reportSyntaxError("msg.unterminated.comment", (Object[])null);
                                 return -1;
                              } else {
                                 return this.getToken();
                              }
                           } else if ((this.flags & 16) == 0) {
                              if (this.in.match('=')) {
                                 this.op = 26;
                                 return 97;
                              } else {
                                 return 26;
                              }
                           } else {
                              for(var17 = new StringBuffer(); (var1 = this.in.read()) != 47; var17.append((char)var1)) {
                                 if (var1 == 10 || var1 == -1) {
                                    this.in.unread();
                                    this.reportSyntaxError("msg.unterminated.re.lit", (Object[])null);
                                    return -1;
                                 }

                                 if (var1 == 92) {
                                    var17.append((char)var1);
                                    var1 = this.in.read();
                                 }
                              }

                              StringBuffer var19 = new StringBuffer();

                              while(true) {
                                 while(!this.in.match('g')) {
                                    if (this.in.match('i')) {
                                       var19.append('i');
                                    } else {
                                       if (!this.in.match('m')) {
                                          if (isAlpha(this.in.peek())) {
                                             this.reportSyntaxError("msg.invalid.re.flag", (Object[])null);
                                             return -1;
                                          }

                                          this.string = var17.toString();
                                          this.regExpFlags = var19.toString();
                                          return 56;
                                       }

                                       var19.append('m');
                                    }
                                 }

                                 var19.append('g');
                              }
                           }
                        case 58:
                           return 99;
                        case 59:
                           return 89;
                        case 60:
                           if (this.in.match('!')) {
                              if (this.in.match('-')) {
                                 if (this.in.match('-')) {
                                    while((var1 = this.in.read()) != -1 && var1 != 10) {
                                    }

                                    this.in.unread();
                                    return this.getToken();
                                 }

                                 this.in.unread();
                              }

                              this.in.unread();
                           }

                           if (this.in.match('<')) {
                              if (this.in.match('=')) {
                                 this.op = 20;
                                 return 97;
                              }

                              this.op = 20;
                              return 104;
                           } else {
                              if (this.in.match('=')) {
                                 this.op = 17;
                                 return 103;
                              }

                              this.op = 16;
                              return 103;
                           }
                        case 61:
                           if (this.in.match('=')) {
                              if (this.in.match('=')) {
                                 this.op = 53;
                              } else {
                                 this.op = 14;
                              }

                              return 102;
                           }

                           this.op = 128;
                           return 97;
                        case 62:
                           if (this.in.match('>')) {
                              if (this.in.match('>')) {
                                 if (this.in.match('=')) {
                                    this.op = 22;
                                    return 97;
                                 }

                                 this.op = 22;
                                 return 104;
                              } else {
                                 if (this.in.match('=')) {
                                    this.op = 21;
                                    return 97;
                                 }

                                 this.op = 21;
                                 return 104;
                              }
                           } else {
                              if (this.in.match('=')) {
                                 this.op = 19;
                                 return 103;
                              }

                              this.op = 18;
                              return 103;
                           }
                        case 63:
                           return 98;
                        case 91:
                           return 90;
                        case 93:
                           return 91;
                        case 94:
                           if (this.in.match('=')) {
                              this.op = 12;
                              return 97;
                           }

                           return 12;
                        case 123:
                           return 92;
                        case 124:
                           if (this.in.match('|')) {
                              return 100;
                           } else {
                              if (this.in.match('=')) {
                                 this.op = 11;
                                 return 97;
                              }

                              return 11;
                           }
                        case 125:
                           return 93;
                        case 126:
                           this.op = 28;
                           return 105;
                        default:
                           this.reportSyntaxError("msg.illegal.character", (Object[])null);
                           return -1;
                     }
                  } else {
                     var17 = null;
                     var4 = var1;
                     boolean var18 = false;
                     var1 = this.in.read();
                     this.in.startString();

                     for(; var1 != var4; var1 = this.in.read()) {
                        if (var1 == 10 || var1 == -1) {
                           this.in.unread();
                           this.in.getString();
                           this.reportSyntaxError("msg.unterminated.string.lit", (Object[])null);
                           return -1;
                        }

                        if (var1 == 92) {
                           if (var17 == null) {
                              this.in.unread();
                              var17 = new StringBuffer(this.in.getString());
                              this.in.read();
                           }

                           switch (var1 = this.in.read()) {
                              case 98:
                                 var1 = 8;
                                 break;
                              case 102:
                                 var1 = 12;
                                 break;
                              case 110:
                                 var1 = 10;
                                 break;
                              case 114:
                                 var1 = 13;
                                 break;
                              case 116:
                                 var1 = 9;
                                 break;
                              case 118:
                                 var1 = 11;
                                 break;
                              default:
                                 if (isDigit(var1) && var1 < 56) {
                                    int var21 = var1 - 48;
                                    var1 = this.in.read();
                                    if (isDigit(var1) && var1 < 56) {
                                       var21 = 8 * var21 + var1 - 48;
                                       var1 = this.in.read();
                                       if (isDigit(var1) && var1 < 56) {
                                          var21 = 8 * var21 + var1 - 48;
                                          var1 = this.in.read();
                                       }
                                    }

                                    this.in.unread();
                                    if (var21 > 255) {
                                       this.reportSyntaxError("msg.oct.esc.too.large", (Object[])null);
                                       return -1;
                                    }

                                    var1 = var21;
                                 } else {
                                    int var22;
                                    if (var1 == 117) {
                                       var22 = this.in.read();
                                       var1 = xDigitToInt(var22);
                                       if (var1 < 0) {
                                          this.in.unread();
                                          var1 = 117;
                                       } else {
                                          var7 = this.in.read();
                                          var1 = var1 << 4 | xDigitToInt(var7);
                                          if (var1 < 0) {
                                             this.in.unread();
                                             var17.append('u');
                                             var1 = var22;
                                          } else {
                                             var8 = this.in.read();
                                             var1 = var1 << 4 | xDigitToInt(var8);
                                             if (var1 < 0) {
                                                this.in.unread();
                                                var17.append('u');
                                                var17.append((char)var22);
                                                var1 = var7;
                                             } else {
                                                var9 = this.in.read();
                                                var1 = var1 << 4 | xDigitToInt(var9);
                                                if (var1 < 0) {
                                                   this.in.unread();
                                                   var17.append('u');
                                                   var17.append((char)var22);
                                                   var17.append((char)var7);
                                                   var1 = var8;
                                                }
                                             }
                                          }
                                       }
                                    } else if (var1 == 120) {
                                       var22 = this.in.read();
                                       var1 = xDigitToInt(var22);
                                       if (var1 < 0) {
                                          this.in.unread();
                                          var1 = 120;
                                       } else {
                                          var7 = this.in.read();
                                          var1 = var1 << 4 | xDigitToInt(var7);
                                          if (var1 < 0) {
                                             this.in.unread();
                                             var17.append('x');
                                             var1 = var22;
                                          }
                                       }
                                    }
                                 }
                           }
                        }

                        if (var17 != null) {
                           var17.append((char)var1);
                        }
                     }

                     if (var17 != null) {
                        this.string = var17.toString();
                     } else {
                        this.in.unread();
                        this.string = this.in.getString();
                        this.in.read();
                     }

                     return 46;
                  }
               } else {
                  byte var15 = 10;
                  this.in.startString();
                  double var16 = ScriptRuntime.NaN;
                  long var20 = 0L;
                  boolean var23 = true;
                  if (var1 == 48) {
                     var1 = this.in.read();
                     if (var1 != 120 && var1 != 88) {
                        if (isDigit(var1)) {
                           var15 = 8;
                        }
                     } else {
                        var1 = this.in.read();
                        var15 = 16;
                        this.in.startString();
                     }
                  }

                  for(; xDigitToInt(var1) >= 0; var1 = this.in.read()) {
                     if (var15 < 16) {
                        if (isAlpha(var1)) {
                           break;
                        }

                        if (var15 == 8 && var1 >= 56) {
                           Object[] var24 = new Object[]{var1 == 56 ? "8" : "9"};
                           Context.reportWarning(Context.getMessage("msg.bad.octal.literal", var24), this.getSourceName(), this.in.getLineno(), this.getLine(), this.getOffset());
                           var15 = 10;
                        }
                     }
                  }

                  if (var15 == 10 && (var1 == 46 || var1 == 101 || var1 == 69)) {
                     var23 = false;
                     if (var1 == 46) {
                        do {
                           var1 = this.in.read();
                        } while(isDigit(var1));
                     }

                     if (var1 == 101 || var1 == 69) {
                        var1 = this.in.read();
                        if (var1 == 43 || var1 == 45) {
                           var1 = this.in.read();
                        }

                        if (!isDigit(var1)) {
                           this.in.getString();
                           this.reportSyntaxError("msg.missing.exponent", (Object[])null);
                           return -1;
                        }

                        do {
                           var1 = this.in.read();
                        } while(isDigit(var1));
                     }
                  }

                  this.in.unread();
                  String var25 = this.in.getString();
                  if (var15 == 10 && !var23) {
                     try {
                        var16 = Double.valueOf(var25);
                     } catch (NumberFormatException var12) {
                        Object[] var26 = new Object[]{var12.getMessage()};
                        this.reportSyntaxError("msg.caught.nfe", var26);
                        return -1;
                     }
                  } else {
                     var16 = ScriptRuntime.stringToNumber(var25, 0, var15);
                     var20 = (long)var16;
                     if ((double)var20 != var16) {
                        var23 = false;
                     }
                  }

                  if (!var23) {
                     this.number = new Double(var16);
                  } else if (var20 >= -128L && var20 <= 127L) {
                     this.number = new Byte((byte)((int)var20));
                  } else if (var20 >= -32768L && var20 <= 32767L) {
                     this.number = new Short((short)((int)var20));
                  } else if (var20 >= -2147483648L && var20 <= 2147483647L) {
                     this.number = new Integer((int)var20);
                  } else {
                     this.number = new Double((double)var20);
                  }

                  return 45;
               }
            } else {
               this.in.startString();
               boolean var3 = var2;

               do {
                  var1 = this.in.read();
                  if (var1 == 92) {
                     var1 = this.in.read();
                     var3 = var1 == 117;
                  }
               } while(Character.isJavaIdentifierPart((char)var1));

               this.in.unread();
               String var5 = this.in.getString();
               if (var3) {
                  char[] var6 = var5.toCharArray();
                  var7 = var5.length();
                  var8 = 0;

                  for(var9 = 0; var9 != var7; ++var8) {
                     char var14 = var6[var9];
                     ++var9;
                     if (var14 == '\\' && var9 != var7 && var6[var9] == 'u') {
                        boolean var10 = false;
                        if (var9 + 4 < var7) {
                           int var11 = xDigitToInt(var6[var9 + 1]);
                           if (var11 >= 0) {
                              var11 = var11 << 4 | xDigitToInt(var6[var9 + 2]);
                              if (var11 >= 0) {
                                 var11 = var11 << 4 | xDigitToInt(var6[var9 + 3]);
                                 if (var11 >= 0) {
                                    var11 = var11 << 4 | xDigitToInt(var6[var9 + 4]);
                                    if (var11 >= 0) {
                                       var14 = (char)var11;
                                       var9 += 5;
                                       var10 = true;
                                    }
                                 }
                              }
                           }
                        }

                        if (!var10) {
                           this.reportSyntaxError("msg.invalid.escape", (Object[])null);
                           return -1;
                        }
                     }

                     var6[var8] = (char)var14;
                  }

                  var5 = new String(var6, 0, var8);
               } else if ((var4 = this.stringToKeyword(var5)) != 0) {
                  return var4;
               }

               this.string = var5;
               return 44;
            }
         }
      }
   }

   public int getTokenno() {
      return this.tokenno;
   }

   private static boolean isAlpha(int var0) {
      return var0 >= 97 && var0 <= 122 || var0 >= 65 && var0 <= 90;
   }

   static boolean isDigit(int var0) {
      return var0 >= 48 && var0 <= 57;
   }

   protected static boolean isJSIdentifier(String var0) {
      int var1 = var0.length();
      if (var1 != 0 && Character.isJavaIdentifierStart(var0.charAt(0))) {
         for(int var2 = 1; var2 < var1; ++var2) {
            char var3 = var0.charAt(var2);
            if (!Character.isJavaIdentifierPart(var3) && var3 == '\\' && var2 + 5 >= var1 && var0.charAt(var2 + 1) == 'u' && xDigitToInt(var0.charAt(var2 + 2)) >= 0 && xDigitToInt(var0.charAt(var2 + 3)) >= 0 && xDigitToInt(var0.charAt(var2 + 4)) >= 0 && xDigitToInt(var0.charAt(var2 + 5)) >= 0) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean isJSLineTerminator(int var0) {
      return var0 == 10 || var0 == 13 || var0 == 8232 || var0 == 8233;
   }

   public static boolean isJSSpace(int var0) {
      return var0 == 32 || var0 == 9 || var0 == 12 || var0 == 11 || var0 == 160 || Character.getType((char)var0) == 12;
   }

   public boolean matchToken(int var1) throws IOException {
      int var2 = this.getToken();
      if (var2 == var1) {
         return true;
      } else {
         --this.tokenno;
         this.pushbackToken = var2;
         return false;
      }
   }

   public int peekToken() throws IOException {
      int var1 = this.getToken();
      this.pushbackToken = var1;
      --this.tokenno;
      return var1;
   }

   public int peekTokenSameLine() throws IOException {
      this.flags |= 1;
      int var1 = this.peekToken();
      this.flags &= -2;
      if (this.pushbackToken == 1) {
         this.pushbackToken = 0;
      }

      return var1;
   }

   public void reportSyntaxError(String var1, Object[] var2) {
      String var3 = Context.getMessage(var1, var2);
      if (this.scope != null) {
         throw NativeGlobal.constructError(Context.getContext(), "SyntaxError", var3, this.scope, this.getSourceName(), this.getLineno(), this.getOffset(), this.getLine());
      } else {
         Context.reportError(var3, this.getSourceName(), this.getLineno(), this.getLine(), this.getOffset());
      }
   }

   private int stringToKeyword(String var1) {
      char var61;
      label194: {
         String var63;
         boolean var2 = true;
         boolean var3 = true;
         boolean var4 = true;
         boolean var5 = true;
         boolean var6 = true;
         boolean var7 = true;
         boolean var8 = true;
         boolean var9 = true;
         boolean var10 = true;
         boolean var11 = true;
         boolean var12 = true;
         boolean var13 = true;
         boolean var14 = true;
         boolean var15 = true;
         boolean var16 = true;
         boolean var17 = true;
         boolean var18 = true;
         boolean var19 = true;
         boolean var20 = true;
         boolean var21 = true;
         boolean var22 = true;
         char var23 = '葩';
         boolean var24 = true;
         boolean var25 = true;
         boolean var26 = true;
         boolean var27 = true;
         boolean var28 = true;
         boolean var29 = true;
         boolean var30 = true;
         boolean var31 = true;
         boolean var32 = true;
         boolean var33 = true;
         boolean var34 = true;
         boolean var35 = true;
         boolean var36 = true;
         boolean var37 = true;
         boolean var38 = true;
         boolean var39 = true;
         boolean var40 = true;
         boolean var41 = true;
         boolean var42 = true;
         boolean var43 = true;
         boolean var44 = true;
         boolean var45 = true;
         boolean var46 = true;
         boolean var47 = true;
         boolean var48 = true;
         boolean var49 = true;
         boolean var50 = true;
         boolean var51 = true;
         boolean var52 = true;
         boolean var53 = true;
         boolean var54 = true;
         boolean var55 = true;
         boolean var56 = true;
         boolean var57 = true;
         boolean var58 = true;
         boolean var59 = true;
         boolean var60 = true;
         var61 = 0;
         var63 = null;
         char var64;
         label191:
         switch (var1.length()) {
            case 2:
               var64 = var1.charAt(1);
               if (var64 == 'f') {
                  if (var1.charAt(0) == 'i') {
                     var61 = 'q';
                     break label194;
                  }
               } else if (var64 == 'n') {
                  if (var1.charAt(0) == 'i') {
                     var61 = 16231;
                     break label194;
                  }
               } else if (var64 == 'o' && var1.charAt(0) == 'd') {
                  var61 = 'w';
                  break label194;
               }
               break;
            case 3:
               switch (var1.charAt(0)) {
                  case 'f':
                     if (var1.charAt(2) == 'r' && var1.charAt(1) == 'o') {
                        var61 = 'x';
                        break label194;
                     }
                     break label191;
                  case 'i':
                     if (var1.charAt(2) == 't' && var1.charAt(1) == 'n') {
                        var61 = 127;
                        break label194;
                     }
                     break label191;
                  case 'n':
                     if (var1.charAt(2) == 'w' && var1.charAt(1) == 'e') {
                        var61 = 30;
                        break label194;
                     }
                     break label191;
                  case 't':
                     if (var1.charAt(2) == 'y' && var1.charAt(1) == 'r') {
                        var61 = 'K';
                        break label194;
                     }
                     break label191;
                  case 'v':
                     if (var1.charAt(2) == 'r' && var1.charAt(1) == 'a') {
                        var61 = '{';
                        break label194;
                     }
                  default:
                     break label191;
               }
            case 4:
               switch (var1.charAt(0)) {
                  case 'b':
                     var63 = "byte";
                     var61 = 127;
                     break label191;
                  case 'c':
                     var64 = var1.charAt(3);
                     if (var64 == 'e') {
                        if (var1.charAt(2) == 's' && var1.charAt(1) == 'a') {
                           var61 = 't';
                           break label194;
                        }
                     } else if (var64 == 'r' && var1.charAt(2) == 'a' && var1.charAt(1) == 'h') {
                        var61 = 127;
                        break label194;
                     }
                  case 'd':
                  case 'f':
                  case 'h':
                  case 'i':
                  case 'j':
                  case 'k':
                  case 'm':
                  case 'o':
                  case 'p':
                  case 'q':
                  case 'r':
                  case 's':
                  case 'u':
                  default:
                     break label191;
                  case 'e':
                     var64 = var1.charAt(3);
                     if (var64 == 'e') {
                        if (var1.charAt(2) == 's' && var1.charAt(1) == 'l') {
                           var61 = 'r';
                           break label194;
                        }
                     } else if (var64 == 'm' && var1.charAt(2) == 'u' && var1.charAt(1) == 'n') {
                        var61 = 127;
                        break label194;
                     }
                     break label191;
                  case 'g':
                     var63 = "goto";
                     var61 = 127;
                     break label191;
                  case 'l':
                     var63 = "long";
                     var61 = 127;
                     break label191;
                  case 'n':
                     var63 = "null";
                     var61 = 12653;
                     break label191;
                  case 't':
                     var64 = var1.charAt(3);
                     if (var64 == 'e') {
                        if (var1.charAt(2) == 'u' && var1.charAt(1) == 'r') {
                           var61 = 13421;
                           break label194;
                        }
                     } else if (var64 == 's' && var1.charAt(2) == 'i' && var1.charAt(1) == 'h') {
                        var61 = 12909;
                        break label194;
                     }
                     break label191;
                  case 'v':
                     var63 = "void";
                     var61 = '葩';
                     break label191;
                  case 'w':
                     var63 = "with";
                     var61 = '|';
                     break label191;
               }
            case 5:
               switch (var1.charAt(2)) {
                  case 'a':
                     var63 = "class";
                     var61 = 127;
                  case 'b':
                  case 'c':
                  case 'd':
                  case 'f':
                  case 'g':
                  case 'h':
                  case 'j':
                  case 'k':
                  case 'm':
                  case 'q':
                  case 's':
                  default:
                     break label191;
                  case 'e':
                     var63 = "break";
                     var61 = 'y';
                     break label191;
                  case 'i':
                     var63 = "while";
                     var61 = 'v';
                     break label191;
                  case 'l':
                     var63 = "false";
                     var61 = 13165;
                     break label191;
                  case 'n':
                     var64 = var1.charAt(0);
                     if (var64 == 'c') {
                        var63 = "const";
                        var61 = 127;
                     } else if (var64 == 'f') {
                        var63 = "final";
                        var61 = 127;
                     }
                     break label191;
                  case 'o':
                     var64 = var1.charAt(0);
                     if (var64 == 'f') {
                        var63 = "float";
                        var61 = 127;
                     } else if (var64 == 's') {
                        var63 = "short";
                        var61 = 127;
                     }
                     break label191;
                  case 'p':
                     var63 = "super";
                     var61 = 127;
                     break label191;
                  case 'r':
                     var63 = "throw";
                     var61 = '>';
                     break label191;
                  case 't':
                     var63 = "catch";
                     var61 = '}';
                     break label191;
               }
            case 6:
               switch (var1.charAt(1)) {
                  case 'a':
                     var63 = "native";
                     var61 = 127;
                  case 'b':
                  case 'c':
                  case 'd':
                  case 'f':
                  case 'g':
                  case 'i':
                  case 'j':
                  case 'k':
                  case 'l':
                  case 'n':
                  case 'p':
                  case 'q':
                  case 'r':
                  case 's':
                  case 'v':
                  default:
                     break label191;
                  case 'e':
                     var64 = var1.charAt(0);
                     if (var64 == 'd') {
                        var63 = "delete";
                        var61 = 31;
                     } else if (var64 == 'r') {
                        var63 = "return";
                        var61 = 5;
                     }
                     break label191;
                  case 'h':
                     var63 = "throws";
                     var61 = 127;
                     break label191;
                  case 'm':
                     var63 = "import";
                     var61 = 'p';
                     break label191;
                  case 'o':
                     var63 = "double";
                     var61 = 127;
                     break label191;
                  case 't':
                     var63 = "static";
                     var61 = 127;
                     break label191;
                  case 'u':
                     var63 = "public";
                     var61 = 127;
                     break label191;
                  case 'w':
                     var63 = "switch";
                     var61 = 's';
                     break label191;
                  case 'x':
                     var63 = "export";
                     var61 = 'o';
                     break label191;
                  case 'y':
                     var63 = "typeof";
                     var61 = 8297;
                     break label191;
               }
            case 7:
               switch (var1.charAt(1)) {
                  case 'a':
                     var63 = "package";
                     var61 = 127;
                     break label191;
                  case 'e':
                     var63 = "default";
                     var61 = 'u';
                     break label191;
                  case 'i':
                     var63 = "finally";
                     var61 = '~';
                     break label191;
                  case 'o':
                     var63 = "boolean";
                     var61 = 127;
                     break label191;
                  case 'r':
                     var63 = "private";
                     var61 = 127;
                     break label191;
                  case 'x':
                     var63 = "extends";
                     var61 = 127;
                  default:
                     break label191;
               }
            case 8:
               switch (var1.charAt(0)) {
                  case 'a':
                     var63 = "abstract";
                     var61 = 127;
                     break label191;
                  case 'c':
                     var63 = "continue";
                     var61 = 'z';
                     break label191;
                  case 'd':
                     var63 = "debugger";
                     var61 = 127;
                     break label191;
                  case 'f':
                     var63 = "function";
                     var61 = 'n';
                     break label191;
                  case 'v':
                     var63 = "volatile";
                     var61 = 127;
                  default:
                     break label191;
               }
            case 9:
               var64 = var1.charAt(0);
               if (var64 == 'i') {
                  var63 = "interface";
                  var61 = 127;
               } else if (var64 == 'p') {
                  var63 = "protected";
                  var61 = 127;
               } else if (var64 == 't') {
                  var63 = "transient";
                  var61 = 127;
               }
               break;
            case 10:
               var64 = var1.charAt(1);
               if (var64 == 'm') {
                  var63 = "implements";
                  var61 = 127;
               } else if (var64 == 'n') {
                  var63 = "instanceof";
                  var61 = 16487;
               }
            case 11:
            default:
               break;
            case 12:
               var63 = "synchronized";
               var61 = 127;
         }

         if (var63 != null && var63 != var1 && !var63.equals(var1)) {
            var61 = 0;
         }
      }

      if (var61 == 0) {
         return 0;
      } else {
         this.op = var61 >> 8;
         return var61 & 255;
      }
   }

   public static String tokenToName(int var0) {
      checkNames();
      return names == null ? "" : names[var0 + 1];
   }

   public String tokenToString(int var1) {
      return "";
   }

   public void ungetToken(int var1) {
      if (this.pushbackToken != 0 && var1 != -1) {
         String var2 = Context.getMessage2("msg.token.replaces.pushback", this.tokenToString(var1), this.tokenToString(this.pushbackToken));
         throw new RuntimeException(var2);
      } else {
         this.pushbackToken = var1;
         --this.tokenno;
      }
   }

   static int xDigitToInt(int var0) {
      if (var0 >= 48 && var0 <= 57) {
         return var0 - 48;
      } else if (var0 >= 97 && var0 <= 102) {
         return var0 - 87;
      } else {
         return var0 >= 65 && var0 <= 70 ? var0 - 55 : -1;
      }
   }
}
