package org.mozilla.javascript.regexp;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.IdFunction;
import org.mozilla.javascript.IdScriptable;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeGlobal;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.TokenStream;
import org.mozilla.javascript.Undefined;

public class NativeRegExp extends IdScriptable implements Function {
   public static final int GLOB = 1;
   public static final int FOLD = 2;
   public static final int MULTILINE = 4;
   public static final int TEST = 0;
   public static final int MATCH = 1;
   public static final int PREFIX = 2;
   private static final boolean debug = false;
   static final int JS_BITS_PER_BYTE = 8;
   private static final byte REOP_EMPTY = 0;
   private static final byte REOP_ALT = 1;
   private static final byte REOP_BOL = 2;
   private static final byte REOP_EOL = 3;
   private static final byte REOP_WBDRY = 4;
   private static final byte REOP_WNONBDRY = 5;
   private static final byte REOP_QUANT = 6;
   private static final byte REOP_STAR = 7;
   private static final byte REOP_PLUS = 8;
   private static final byte REOP_OPT = 9;
   private static final byte REOP_LPAREN = 10;
   private static final byte REOP_RPAREN = 11;
   private static final byte REOP_DOT = 12;
   private static final byte REOP_CCLASS = 13;
   private static final byte REOP_DIGIT = 14;
   private static final byte REOP_NONDIGIT = 15;
   private static final byte REOP_ALNUM = 16;
   private static final byte REOP_NONALNUM = 17;
   private static final byte REOP_SPACE = 18;
   private static final byte REOP_NONSPACE = 19;
   private static final byte REOP_BACKREF = 20;
   private static final byte REOP_FLAT = 21;
   private static final byte REOP_FLAT1 = 22;
   private static final byte REOP_JUMP = 23;
   private static final byte REOP_DOTSTAR = 24;
   private static final byte REOP_ANCHOR = 25;
   private static final byte REOP_EOLONLY = 26;
   private static final byte REOP_UCFLAT = 27;
   private static final byte REOP_UCFLAT1 = 28;
   private static final byte REOP_UCCLASS = 29;
   private static final byte REOP_NUCCLASS = 30;
   private static final byte REOP_BACKREFi = 31;
   private static final byte REOP_FLATi = 32;
   private static final byte REOP_FLAT1i = 33;
   private static final byte REOP_UCFLATi = 34;
   private static final byte REOP_UCFLAT1i = 35;
   private static final byte REOP_ANCHOR1 = 36;
   private static final byte REOP_NCCLASS = 37;
   private static final byte REOP_DOTSTARMIN = 38;
   private static final byte REOP_LPARENNON = 39;
   private static final byte REOP_RPARENNON = 40;
   private static final byte REOP_ASSERT = 41;
   private static final byte REOP_ASSERT_NOT = 42;
   private static final byte REOP_END = 43;
   private static final int REOP_FLATLEN_MAX = 255;
   private static int level;
   private static String[] reopname = null;
   static final String metachars = "|^${*+?().[\\";
   static final String closurechars = "{*+?";
   private static final int Id_lastIndex = 1;
   private static final int Id_source = 2;
   private static final int Id_global = 3;
   private static final int Id_ignoreCase = 4;
   private static final int Id_multiline = 5;
   private static final int MAX_INSTANCE_ID = 5;
   private static final int Id_compile = 6;
   private static final int Id_toString = 7;
   private static final int Id_exec = 8;
   private static final int Id_test = 9;
   private static final int Id_prefix = 10;
   private static final int MAX_PROTOTYPE_ID = 10;
   private boolean prototypeFlag;
   private String source;
   private int lastIndex;
   private int parenCount;
   private byte flags;
   private byte[] program;
   RENode ren;

   public NativeRegExp() {
   }

   public NativeRegExp(Context var1, Scriptable var2, String var3, String var4, boolean var5) {
      this.init(var1, var2, var3, var4, var5);
   }

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) {
      return this.execSub(var1, var2, var4, 1);
   }

   Scriptable compile(Context var1, Scriptable var2, Object[] var3) {
      if (var3.length > 0 && var3[0] instanceof NativeRegExp) {
         if (var3.length > 1 && var3[1] != Undefined.instance) {
            throw NativeGlobal.constructError(var1, "TypeError", "only one argument may be specified if the first argument is a RegExp object", var2);
         } else {
            NativeRegExp var6 = (NativeRegExp)var3[0];
            this.source = var6.source;
            this.lastIndex = var6.lastIndex;
            this.parenCount = var6.parenCount;
            this.flags = var6.flags;
            this.program = var6.program;
            this.ren = var6.ren;
            return this;
         }
      } else {
         String var4 = var3.length == 0 ? "" : ScriptRuntime.toString(var3[0]);
         String var5 = var3.length > 1 && var3[1] != Undefined.instance ? ScriptRuntime.toString(var3[1]) : null;
         this.init(var1, var2, var4, var5, false);
         return this;
      }
   }

   public Scriptable construct(Context var1, Scriptable var2, Object[] var3) {
      return (Scriptable)this.call(var1, var2, (Scriptable)null, var3);
   }

   private int doOctal(CompilerState var1) {
      char[] var2 = var1.source;
      int var3 = var1.index;
      int var5 = 0;

      while(true) {
         ++var3;
         char var6;
         if (var3 >= var2.length || (var6 = var2[var3]) < '0' || var6 > '7') {
            break;
         }

         int var4 = 8 * var5 + (var6 - 48);
         if (var4 > 255) {
            break;
         }

         var5 = var4;
      }

      --var3;
      var1.index = var3;
      return var5;
   }

   private void dumpRegExp(CompilerState var1, RENode var2) {
   }

   private Object exec(Context var1, Scriptable var2, Object[] var3) {
      return this.execSub(var1, var2, var3, 1);
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      if (this.prototypeFlag) {
         switch (var1) {
            case 6:
               return this.realThis(var5, var2, false).compile(var3, var4, var6);
            case 7:
               return this.realThis(var5, var2, true).toString();
            case 8:
               return this.realThis(var5, var2, false).exec(var3, var4, var6);
            case 9:
               return this.realThis(var5, var2, false).test(var3, var4, var6);
            case 10:
               return this.realThis(var5, var2, false).prefix(var3, var4, var6);
         }
      }

      return super.execMethod(var1, var2, var3, var4, var5, var6);
   }

   private Object execSub(Context var1, Scriptable var2, Object[] var3, int var4) {
      RegExpImpl var5 = getImpl(var1);
      String var6;
      if (var3.length == 0) {
         var6 = var5.input;
         if (var6 == null) {
            Object[] var7 = new Object[]{this.toString()};
            throw NativeGlobal.constructError(var1, "SyntaxError", ScriptRuntime.getMessage("msg.no.re.input.for", var7), var2);
         }
      } else {
         var6 = ScriptRuntime.toString(var3[0]);
      }

      int var10 = (this.flags & 1) != 0 ? this.lastIndex : 0;
      int[] var8 = new int[]{var10};
      Object var9 = this.executeRegExp(var1, var2, var5, var6, var8, var4);
      if ((this.flags & 1) != 0) {
         this.lastIndex = var9 != null && var9 != Undefined.instance ? var8[0] : 0;
      }

      return var9;
   }

   Object executeRegExp(Context var1, Scriptable var2, RegExpImpl var3, String var4, int[] var5, int var6) {
      MatchState var8 = new MatchState();
      var8.inputExhausted = false;
      var8.anchoring = false;
      var8.flags = this.flags;
      var8.scope = var2;
      char[] var9 = var4.toCharArray();
      int var10 = var5[0];
      if (var10 > var9.length) {
         var10 = var9.length;
      }

      var8.cpbegin = 0;
      var8.cpend = var9.length;
      var8.start = var10;
      var8.skipped = 0;
      var8.input = var9;
      var8.parenCount = 0;
      var8.maybeParens = new SubString[this.parenCount];
      var8.parens = new SubString[this.parenCount];
      int var11 = this.matchRegExp(var8, this.ren, var10);
      if (var11 == -1) {
         return var6 == 2 && var8.inputExhausted ? Undefined.instance : null;
      } else {
         int var12 = var11 - var8.cpbegin;
         var5[0] = var12;
         int var13 = var12 - (var10 + var8.skipped);
         int var14 = var11;
         var11 -= var13;
         Object var15;
         Scriptable var16;
         if (var6 == 0) {
            var15 = Boolean.TRUE;
            var16 = null;
         } else {
            Scriptable var17 = ScriptableObject.getTopLevelScope(var2);
            var15 = ScriptRuntime.newObject(var1, (Scriptable)var17, (String)"Array", (Object[])null);
            var16 = (Scriptable)var15;
            String var18 = new String(var9, var11, var13);
            var16.put(0, var16, var18);
         }

         if (var8.parenCount > this.parenCount) {
            throw new RuntimeException();
         } else {
            if (var8.parenCount == 0) {
               var3.parens.setSize(0);
               var3.lastParen = SubString.emptySubString;
            } else {
               SubString var20 = null;
               var3.parens.setSize(var8.parenCount);

               for(int var21 = 0; var21 < var8.parenCount; ++var21) {
                  var20 = var8.parens[var21];
                  var3.parens.setElementAt(var20, var21);
                  if (var6 != 0) {
                     String var19 = var20 == null ? "" : var20.toString();
                     var16.put(var21 + 1, var16, var19);
                  }
               }

               var3.lastParen = var20;
            }

            if (var6 != 0) {
               var16.put("index", var16, new Integer(var10 + var8.skipped));
               var16.put("input", var16, var4);
            }

            if (var3.lastMatch == null) {
               var3.lastMatch = new SubString();
               var3.leftContext = new SubString();
               var3.rightContext = new SubString();
            }

            var3.lastMatch.charArray = var9;
            var3.lastMatch.index = var11;
            var3.lastMatch.length = var13;
            var3.leftContext.charArray = var9;
            if (var1.getLanguageVersion() == 120) {
               var3.leftContext.index = var10;
               var3.leftContext.length = var8.skipped;
            } else {
               var3.leftContext.index = 0;
               var3.leftContext.length = var10 + var8.skipped;
            }

            var3.rightContext.charArray = var9;
            var3.rightContext.index = var14;
            var3.rightContext.length = var8.cpend - var14;
            return var15;
         }
      }
   }

   private void fixNext(CompilerState var1, RENode var2, RENode var3, RENode var4) {
      boolean var5;
      RENode var6;
      for(var5 = var3 != null && (var3.flags & 8) == 0; (var6 = var2.next) != null && var6 != var4; var2 = var6) {
         if (var2.op == 1) {
            RENode var7 = (RENode)var2.kid;
            if (var7.op != 23) {
               RENode var8;
               for(var8 = var7; var8.next != null; var8 = var8.next) {
                  if (var8.op == 1) {
                     throw new RuntimeException("REOP_ALT not expected");
                  }
               }

               var8.next = new RENode(var1, (byte)23, (Object)null);
               RENode var10000 = var8.next;
               var10000.flags = (byte)(var10000.flags | 8);
               var8.flags = (byte)(var8.flags | 16);
               this.fixNext(var1, var7, var3, var4);
            }
         }
      }

      if (var3 != null) {
         if ((var3.flags & 8) == 0) {
            var3.flags = (byte)(var3.flags | 8);
         } else {
            var3.flags = (byte)(var3.flags | 32);
         }
      }

      var2.next = var3;
      if (var5) {
         var2.flags = (byte)(var2.flags | 16);
      }

      switch (var2.op) {
         case 1:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 39:
         case 41:
         case 42:
            this.fixNext(var1, (RENode)var2.kid, var3, var4);
         default:
      }
   }

   public String getClassName() {
      return "RegExp";
   }

   static char getEscape(char var0) {
      switch (var0) {
         case 'b':
            return '\b';
         case 'f':
            return '\f';
         case 'n':
            return '\n';
         case 'r':
            return '\r';
         case 't':
            return '\t';
         case 'v':
            return '\u000b';
         default:
            throw new RuntimeException();
      }
   }

   public byte getFlags() {
      return this.flags;
   }

   protected int getIdDefaultAttributes(int var1) {
      switch (var1) {
         case 1:
            return 4;
         case 2:
         case 3:
         case 4:
         case 5:
            return 5;
         default:
            return super.getIdDefaultAttributes(var1);
      }
   }

   protected String getIdName(int var1) {
      switch (var1) {
         case 1:
            return "lastIndex";
         case 2:
            return "source";
         case 3:
            return "global";
         case 4:
            return "ignoreCase";
         case 5:
            return "multiline";
         default:
            if (this.prototypeFlag) {
               switch (var1) {
                  case 6:
                     return "compile";
                  case 7:
                     return "toString";
                  case 8:
                     return "exec";
                  case 9:
                     return "test";
                  case 10:
                     return "prefix";
               }
            }

            return null;
      }
   }

   protected Object getIdValue(int var1) {
      switch (var1) {
         case 1:
            return this.wrap_long(4294967295L & (long)this.lastIndex);
         case 2:
            return this.source;
         case 3:
            return this.wrap_boolean((this.flags & 1) != 0);
         case 4:
            return this.wrap_boolean((this.flags & 2) != 0);
         case 5:
            return this.wrap_boolean((this.flags & 4) != 0);
         default:
            return super.getIdValue(var1);
      }
   }

   private static RegExpImpl getImpl(Context var0) {
      return (RegExpImpl)ScriptRuntime.getRegExpProxy(var0);
   }

   private String getPrintableString(String var1) {
      return "";
   }

   int greedyRecurse(GreedyState var1, int var2, int var3) {
      int var6 = var1.state.parenCount;
      int var4 = this.matchRENodes(var1.state, var1.kid, var1.next, var2);
      int var5;
      if (var4 == -1) {
         var5 = this.matchRENodes(var1.state, var1.next, var1.stop, var2);
         if (var5 != -1) {
            var1.state.parenCount = var6;
            if (var3 != -1) {
               this.matchRENodes(var1.state, var1.kid, var1.next, var3);
            }

            return var2;
         } else {
            return -1;
         }
      } else if (var4 == var2) {
         if (var3 != -1) {
            this.matchRENodes(var1.state, var1.kid, var1.next, var3);
         }

         return var4;
      } else {
         if (var1.maxKid == 0 || ++var1.kidCount < var1.maxKid) {
            var5 = this.greedyRecurse(var1, var4, var2);
            if (var5 != -1) {
               return var5;
            }

            if (var1.maxKid != 0) {
               --var1.kidCount;
            }
         }

         var1.state.parenCount = var6;
         var5 = this.matchRENodes(var1.state, var1.next, var1.stop, var4);
         if (var5 != -1) {
            this.matchRENodes(var1.state, var1.kid, var1.next, var2);
            return var4;
         } else {
            return -1;
         }
      }
   }

   public void init(Context var1, Scriptable var2, String var3, String var4, boolean var5) {
      this.source = var3;
      this.flags = 0;
      int var7;
      if (var4 != null) {
         for(int var6 = 0; var6 < var4.length(); ++var6) {
            var7 = var4.charAt(var6);
            if (var7 == 103) {
               this.flags = (byte)(this.flags | 1);
            } else if (var7 == 105) {
               this.flags = (byte)(this.flags | 2);
            } else {
               if (var7 != 109) {
                  Object[] var8 = new Object[]{new Character((char)var7)};
                  throw NativeGlobal.constructError(var1, "SyntaxError", ScriptRuntime.getMessage("msg.invalid.re.flag", var8), var2);
               }

               this.flags = (byte)(this.flags | 4);
            }
         }
      }

      CompilerState var11 = new CompilerState(var3, this.flags, var1, var2);
      if (var5) {
         this.ren = null;
         var7 = var3.length();
         int var13 = 0;

         while(var7 > 0) {
            int var9 = var7;
            if (var7 > 255) {
               var9 = 255;
            }

            RENode var10 = new RENode(var11, (byte)(var9 == 1 ? 22 : 21), new Integer(var13));
            var10.flags = 4;
            if (var9 > 1) {
               var10.kid2 = var13 + var9;
            } else {
               var10.flags = (byte)(var10.flags | 2);
               var10.chr = var11.source[var13];
            }

            var13 += var9;
            var7 -= var9;
            if (this.ren == null) {
               this.ren = var10;
            } else {
               this.setNext(var11, this.ren, var10);
            }
         }
      } else {
         this.ren = this.parseRegExp(var11);
      }

      if (this.ren != null) {
         RENode var12 = new RENode(var11, (byte)43, (Object)null);
         this.setNext(var11, this.ren, var12);
         this.lastIndex = 0;
         this.parenCount = var11.parenCount;
         this.flags = this.flags;
         var2 = ScriptableObject.getTopLevelScope(var2);
         this.setPrototype(ScriptableObject.getClassPrototype(var2, "RegExp"));
         this.setParentScope(var2);
      }
   }

   public static void init(Context var0, Scriptable var1, boolean var2) {
      NativeRegExp var3 = new NativeRegExp();
      var3.prototypeFlag = true;
      var3.activateIdMap(10);
      var3.setSealFunctionsFlag(var2);
      var3.setFunctionParametrs(var0);
      var3.setParentScope(var1);
      var3.setPrototype(ScriptableObject.getObjectPrototype(var1));
      NativeRegExpCtor var4 = new NativeRegExpCtor();
      var4.setPrototype(ScriptableObject.getClassPrototype(var1, "Function"));
      var4.setParentScope(var1);
      var4.setImmunePrototypeProperty(var3);
      if (var2) {
         var3.sealObject();
         var4.sealObject();
      }

      ScriptableObject.defineProperty(var1, "RegExp", var4, 2);
   }

   public static boolean isDigit(char var0) {
      return var0 >= '0' && var0 <= '9';
   }

   static boolean isHex(char var0) {
      return var0 >= '0' && var0 <= '9' || var0 >= 'a' && var0 <= 'f' || var0 >= 'A' && var0 <= 'F';
   }

   static boolean isWord(char var0) {
      return Character.isLetter(var0) || isDigit(var0) || var0 == '_';
   }

   protected int mapNameToId(String var1) {
      byte var2 = 0;
      String var3 = null;
      int var5 = var1.length();
      char var4;
      if (var5 == 6) {
         var4 = var1.charAt(0);
         if (var4 == 'g') {
            var3 = "global";
            var2 = 3;
         } else if (var4 == 's') {
            var3 = "source";
            var2 = 2;
         }
      } else if (var5 == 9) {
         var4 = var1.charAt(0);
         if (var4 == 'l') {
            var3 = "lastIndex";
            var2 = 1;
         } else if (var4 == 'm') {
            var3 = "multiline";
            var2 = 5;
         }
      } else if (var5 == 10) {
         var3 = "ignoreCase";
         var2 = 4;
      }

      if (var3 != null && var3 != var1 && !var3.equals(var1)) {
         var2 = 0;
      }

      if (var2 == 0 && this.prototypeFlag) {
         var2 = 0;
         var3 = null;
         switch (var1.length()) {
            case 4:
               var4 = var1.charAt(0);
               if (var4 == 'e') {
                  var3 = "exec";
                  var2 = 8;
               } else if (var4 == 't') {
                  var3 = "test";
                  var2 = 9;
               }
            case 5:
            default:
               break;
            case 6:
               var3 = "prefix";
               var2 = 10;
               break;
            case 7:
               var3 = "compile";
               var2 = 6;
               break;
            case 8:
               var3 = "toString";
               var2 = 7;
         }

         if (var3 != null && var3 != var1 && !var3.equals(var1)) {
            var2 = 0;
         }

         return var2;
      } else {
         return var2;
      }
   }

   private static boolean matchChar(int var0, char var1, char var2) {
      if (var1 == var2) {
         return true;
      } else if ((var0 & 2) == 0) {
         return false;
      } else {
         var1 = Character.toUpperCase(var1);
         var2 = Character.toUpperCase(var2);
         return var1 == var2 || Character.toLowerCase(var1) == Character.toLowerCase(var2);
      }
   }

   int matchGreedyKid(MatchState var1, RENode var2, RENode var3, int var4, int var5, int var6) {
      GreedyState var7 = new GreedyState();
      var7.state = var1;
      var7.kid = (RENode)var2.kid;
      var7.next = var2.next;
      var7.maxKid = var2.op == 6 ? var2.max : 0;
      var7.stop = null;
      var7.kidCount = var4;
      int var8 = this.greedyRecurse(var7, var5, var6);
      if (var8 == -1 && var3 != null) {
         var7.kidCount = var4;
         var7.stop = var3;
         return this.greedyRecurse(var7, var5, var6);
      } else {
         return var8;
      }
   }

   int matchNonGreedyKid(MatchState var1, RENode var2, int var3, int var4, int var5) {
      int var7 = this.matchRENodes(var1, var2.next, (RENode)null, var5);
      if (var7 != -1) {
         return var5;
      } else {
         int var6 = this.matchRENodes(var1, (RENode)var2.kid, var2.next, var5);
         if (var6 == -1) {
            return -1;
         } else {
            return var6 == var5 ? var6 : this.matchNonGreedyKid(var1, var2, var3, var4, var6);
         }
      }
   }

   int matchRENodes(MatchState var1, RENode var2, RENode var3, int var4) {
      char[] var6 = var1.input;

      while(var2 != var3 && var2 != null) {
         int var5;
         int var7;
         int var8;
         int var9;
         int var10;
         SubString var13;
         RegExpImpl var17;
         Context var18;
         label373:
         switch (var2.op) {
            case 0:
            case 23:
            case 40:
            case 43:
               break;
            case 1:
               if (var2.next.op != 1) {
                  var2 = (RENode)var2.kid;
                  continue;
               }

               var5 = var1.parenCount;
               var7 = this.matchRENodes(var1, (RENode)var2.kid, var3, var4);
               if (var7 != -1) {
                  return var7;
               }

               for(var8 = var5; var8 < var1.parenCount; ++var8) {
                  var1.parens[var8] = null;
               }

               var1.parenCount = var5;
               break;
            case 2:
               var18 = Context.getCurrentContext();
               var17 = getImpl(var18);
               if (var4 == 0) {
                  break;
               }

               if (var17.multiline || (var1.flags & 4) != 0) {
                  if (var4 >= var6.length) {
                     return var1.noMoreInput();
                  }

                  if (var6[var4 - 1] == '\n') {
                     break;
                  }
               }

               return -1;
            case 3:
            case 26:
               if (var4 != var6.length) {
                  var18 = Context.getCurrentContext();
                  var17 = getImpl(var18);
                  if (!var17.multiline && (var1.flags & 4) == 0) {
                     return -1;
                  }

                  if (var6[var4] != '\n') {
                     return -1;
                  }
               }
               break;
            case 4:
               if (var4 != 0 && isWord(var6[var4 - 1])) {
                  if (var4 < var6.length && isWord(var6[var4])) {
                     return -1;
                  }
               } else {
                  if (var4 >= var6.length) {
                     return var1.noMoreInput();
                  }

                  if (!isWord(var6[var4])) {
                     return -1;
                  }
               }
               break;
            case 5:
               if (var4 != 0 && isWord(var6[var4 - 1])) {
                  if (var4 >= var6.length) {
                     return var1.noMoreInput();
                  }

                  if (!isWord(var6[var4])) {
                     return -1;
                  }
               } else if (var4 < var6.length && isWord(var6[var4])) {
                  return -1;
               }
               break;
            case 6:
               var7 = -1;

               for(var5 = 0; var5 < var2.min; ++var5) {
                  var8 = this.matchRENodes(var1, (RENode)var2.kid, var2.next, var4);
                  if (var8 == -1) {
                     return -1;
                  }

                  var7 = var4;
                  var4 = var8;
               }

               if (var5 != var2.max) {
                  if ((var2.flags & 128) == 0) {
                     var8 = this.matchGreedyKid(var1, var2, var3, var5, var4, var7);
                     if (var8 == -1) {
                        var4 = this.matchRENodes(var1, (RENode)var2.kid, var2.next, var4);
                     } else {
                        var4 = var8;
                     }
                  } else {
                     var4 = this.matchNonGreedyKid(var1, var2, var5, var2.max, var4);
                  }

                  if (var4 == -1) {
                     return -1;
                  }
               }
               break;
            case 7:
               if ((var2.flags & 128) == 0) {
                  var7 = this.matchGreedyKid(var1, var2, var3, 0, var4, -1);
                  if (var7 != -1) {
                     var4 = var7;
                  }
               } else {
                  var4 = this.matchNonGreedyKid(var1, var2, 0, 0, var4);
                  if (var4 == -1) {
                     return -1;
                  }
               }
               break;
            case 8:
               var7 = this.matchRENodes(var1, (RENode)var2.kid, var2.next, var4);
               if (var7 == -1) {
                  return -1;
               }

               if ((var2.flags & 128) == 0) {
                  var7 = this.matchGreedyKid(var1, var2, var3, 1, var7, var4);
                  if (var7 == -1) {
                     var4 = this.matchRENodes(var1, (RENode)var2.kid, var2.next, var4);
                  } else {
                     var4 = var7;
                  }
               } else {
                  var4 = this.matchNonGreedyKid(var1, var2, 1, 0, var7);
               }

               if (var4 == -1) {
                  return -1;
               }
               break;
            case 9:
               var7 = var1.parenCount;
               if ((var2.flags & 128) != 0) {
                  var8 = this.matchRENodes(var1, var2.next, var3, var4);
                  if (var8 != -1) {
                     return var8;
                  }
               }

               var8 = this.matchRENodes(var1, (RENode)var2.kid, var2.next, var4);
               if (var8 == -1) {
                  var1.parenCount = var7;
               } else {
                  var9 = this.matchRENodes(var1, var2.next, var3, var8);
                  if (var9 != -1) {
                     return var9;
                  }

                  var1.parenCount = var7;
               }
               break;
            case 10:
               var5 = var2.num;
               var2 = (RENode)var2.kid;
               var13 = var1.parens[var5];
               if (var13 == null) {
                  var13 = var1.parens[var5] = new SubString();
                  var13.charArray = var6;
               }

               var13.index = var4;
               var13.length = 0;
               if (var5 >= var1.parenCount) {
                  var1.parenCount = var5 + 1;
               }
               continue;
            case 11:
               var5 = var2.num;
               var13 = var1.parens[var5];
               if (var13 == null) {
                  throw new RuntimeException("Paren problem");
               }

               var13.length = var4 - var13.index;
               break;
            case 12:
               if (var4 >= var6.length) {
                  return var1.noMoreInput();
               }

               if (var6[var4] == '\n') {
                  return -1;
               }

               ++var4;
               break;
            case 13:
               if (var4 >= var6.length) {
                  return var1.noMoreInput();
               }

               if (var2.bitmap == null) {
                  char[] var14 = var2.s != null ? var2.s : this.source.toCharArray();
                  var2.buildBitmap(var1, var14, (var1.flags & 2) != 0);
               }

               char var15 = var6[var4];
               var8 = var15 >>> 3;
               if (var8 >= var2.bmsize) {
                  if (var2.kid2 != -1) {
                     return -1;
                  }

                  ++var4;
               } else {
                  var9 = var15 & 7;
                  var9 = 1 << var9;
                  if ((var2.bitmap[var8] & var9) == 0) {
                     return -1;
                  }

                  ++var4;
               }
               break;
            case 14:
               if (var4 >= var6.length) {
                  return var1.noMoreInput();
               }

               if (!isDigit(var6[var4])) {
                  return -1;
               }

               ++var4;
               break;
            case 15:
               if (var4 >= var6.length) {
                  return var1.noMoreInput();
               }

               if (isDigit(var6[var4])) {
                  return -1;
               }

               ++var4;
               break;
            case 16:
               if (var4 >= var6.length) {
                  return var1.noMoreInput();
               }

               if (!isWord(var6[var4])) {
                  return -1;
               }

               ++var4;
               break;
            case 17:
               if (var4 >= var6.length) {
                  return var1.noMoreInput();
               }

               if (isWord(var6[var4])) {
                  return -1;
               }

               ++var4;
               break;
            case 18:
               if (var4 >= var6.length) {
                  return var1.noMoreInput();
               }

               if (!TokenStream.isJSSpace(var6[var4]) && !TokenStream.isJSLineTerminator(var6[var4])) {
                  return -1;
               }

               ++var4;
               break;
            case 19:
               if (var4 >= var6.length) {
                  return var1.noMoreInput();
               }

               if (!TokenStream.isJSSpace(var6[var4]) && !TokenStream.isJSLineTerminator(var6[var4])) {
                  ++var4;
                  break;
               }

               return -1;
            case 20:
               var5 = var2.num;
               if (var5 >= var1.parens.length) {
                  Context.reportError(ScriptRuntime.getMessage("msg.bad.backref", (Object[])null));
                  return -1;
               }

               var13 = var1.parens[var5];
               if (var13 == null) {
                  var13 = var1.parens[var5] = new SubString();
               }

               var8 = var13.length;
               var9 = 0;

               while(true) {
                  if (var9 >= var8) {
                     break label373;
                  }

                  if (var4 >= var6.length) {
                     return var1.noMoreInput();
                  }

                  if (!matchChar(var1.flags, var6[var4], var13.charArray[var13.index + var9])) {
                     return -1;
                  }

                  ++var9;
                  ++var4;
               }
            case 21:
               char[] var16 = var2.s != null ? var2.s : this.source.toCharArray();
               var10 = (Integer)var2.kid;
               int var11 = var2.kid2 - var10;
               int var12 = 0;

               while(true) {
                  if (var12 >= var11) {
                     break label373;
                  }

                  if (var4 >= var6.length) {
                     return var1.noMoreInput();
                  }

                  if (!matchChar(var1.flags, var6[var4], var16[var10 + var12])) {
                     return -1;
                  }

                  ++var12;
                  ++var4;
               }
            case 22:
               if (var4 >= var6.length) {
                  return var1.noMoreInput();
               }

               if (!matchChar(var1.flags, var2.chr, var6[var4])) {
                  return -1;
               }

               ++var4;
               break;
            case 24:
               for(var9 = var4; var9 < var6.length && var6[var9] != '\n'; ++var9) {
               }

               while(true) {
                  if (var9 < var4) {
                     break label373;
                  }

                  var10 = this.matchRENodes(var1, var2.next, var3, var9);
                  if (var10 != -1) {
                     var4 = var9;
                     break label373;
                  }

                  --var9;
               }
            case 25:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            default:
               throw new RuntimeException("Unsupported by node matcher");
            case 38:
               for(var9 = var4; var9 < var6.length; ++var9) {
                  var10 = this.matchRENodes(var1, var2.next, var3, var9);
                  if (var10 != -1) {
                     return var10;
                  }

                  if (var6[var9] == '\n') {
                     return -1;
                  }
               }

               return var1.noMoreInput();
            case 39:
               var2 = (RENode)var2.kid;
               continue;
            case 41:
               var7 = this.matchRENodes(var1, (RENode)var2.kid, var2.next, var4);
               if (var7 == -1) {
                  return -1;
               }
               break;
            case 42:
               var7 = this.matchRENodes(var1, (RENode)var2.kid, var2.next, var4);
               if (var7 != -1) {
                  return -1;
               }
         }

         var2 = var2.next;
      }

      return var4;
   }

   int matchRegExp(MatchState var1, RENode var2, int var3) {
      for(int var4 = var3; var4 <= var1.input.length; ++var4) {
         var1.skipped = var4 - var3;
         var1.parenCount = 0;
         int var5 = this.matchRENodes(var1, var2, (RENode)null, var4);
         if (var5 != -1) {
            return var5;
         }
      }

      return -1;
   }

   protected int maxInstanceId() {
      return 5;
   }

   public int methodArity(int var1) {
      if (this.prototypeFlag) {
         switch (var1) {
            case 6:
               return 1;
            case 7:
               return 0;
            case 8:
               return 1;
            case 9:
               return 1;
            case 10:
               return 1;
         }
      }

      return super.methodArity(var1);
   }

   private RENode parseAltern(CompilerState var1) {
      RENode var2 = this.parseItem(var1);
      if (var2 == null) {
         return null;
      } else {
         RENode var3 = var2;
         int var4 = 0;
         char[] var5 = var1.source;

         char var7;
         for(int var6 = var1.index; var6 != var5.length && (var7 = var5[var6]) != '|' && var7 != ')'; var6 = var1.index) {
            RENode var8 = this.parseItem(var1);
            if (var8 == null) {
               return null;
            }

            this.setNext(var1, var3, var8);
            var4 |= var8.flags;
            var3 = var8;
         }

         var2.flags = (byte)(var2.flags | var4 & 4);
         return var2;
      }
   }

   RENode parseAtom(CompilerState var1) {
      int var2 = 0;
      RENode var4 = null;
      boolean var8 = false;
      boolean var9 = false;
      char[] var10 = var1.source;
      int var11 = var1.index;
      int var12 = var11;
      if (var11 == var10.length) {
         var1.index = var11;
         return new RENode(var1, (byte)0, (Object)null);
      } else {
         int var3;
         char var6;
         byte var7;
         label257:
         switch (var10[var11]) {
            case '(':
               var7 = 43;
               if (var10[var11 + 1] == '?') {
                  switch (var10[var11 + 2]) {
                     case '!':
                        var7 = 42;
                        break;
                     case ':':
                        var7 = 39;
                        break;
                     case '=':
                        var7 = 41;
                  }
               }

               if (var7 == 43) {
                  var7 = 10;
                  var2 = var1.parenCount++;
                  ++var11;
               } else {
                  var11 += 3;
               }

               var1.index = var11;
               RENode var5;
               if (var10[var11] == ')') {
                  var5 = new RENode(var1, (byte)0, (Object)null);
               } else {
                  var5 = this.parseRegExp(var1);
                  if (var5 == null) {
                     return null;
                  }

                  var11 = var1.index;
                  if (var11 >= var10.length || var10[var11] != ')') {
                     this.reportError("msg.unterm.paren", this.tail(var10, var12), var1);
                     return null;
                  }
               }

               ++var11;
               var4 = new RENode(var1, var7, var5);
               var4.flags = (byte)(var5.flags & 5);
               var4.num = var2;
               if (var7 == 10 || var7 == 39) {
                  var5 = new RENode(var1, (byte)(var7 + 1), (Object)null);
                  this.setNext(var1, var4, var5);
                  var5.num = var2;
               }
               break;
            case '.':
               ++var11;
               var7 = 12;
               if (var11 < var10.length && var10[var11] == '*') {
                  ++var11;
                  var7 = 24;
                  if (var11 < var10.length && var10[var11] == '?') {
                     ++var11;
                     var7 = 38;
                  }
               }

               var4 = new RENode(var1, var7, (Object)null);
               if (var4.op == 12) {
                  var4.flags = 6;
               }
               break;
            case '[':
               ++var11;
               if (var11 == var10.length) {
                  this.reportError("msg.unterm.class", this.tail(var10, var12), var1);
                  return null;
               }

               var6 = var10[var11];
               var4 = new RENode(var1, (byte)13, new Integer(var11));
               if (var6 == '^') {
                  ++var11;
                  if (var11 == var10.length) {
                     this.reportError("msg.unterm.class", this.tail(var10, var12), var1);
                     return null;
                  }
               }

               while(true) {
                  ++var11;
                  if (var11 == var10.length) {
                     this.reportError("msg.unterm.paren", this.tail(var10, var12), var1);
                     return null;
                  }

                  var6 = var10[var11];
                  if (var6 == ']') {
                     var4.kid2 = var11++;
                     var4.flags = 6;
                     break label257;
                  }

                  if (var6 == '\\' && var11 + 1 != var10.length) {
                     ++var11;
                  }
               }
            case '\\':
               ++var11;
               if (var11 == var10.length) {
                  Context.reportError(ScriptRuntime.getMessage("msg.trail.backslash", (Object[])null));
                  return null;
               }

               var6 = var10[var11];
               label227:
               switch (var6) {
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
                     if (var1.cx.getLanguageVersion() != 0 && var1.cx.getLanguageVersion() <= 140) {
                        switch (var6) {
                           case '0':
                              var1.index = var11;
                              var2 = this.doOctal(var1);
                              var11 = var1.index;
                              var4 = new RENode(var1, (byte)22, (Object)null);
                              var6 = (char)var2;
                              break label227;
                           case '1':
                           case '2':
                           case '3':
                           case '4':
                           case '5':
                           case '6':
                           case '7':
                           case '8':
                           case '9':
                              var2 = unDigit(var6);
                              var3 = 1;

                              while(true) {
                                 ++var11;
                                 if (var11 >= var10.length || !isDigit(var6 = var10[var11])) {
                                    if ((var2 == 8 || var2 == 9) && var2 > var1.parenCount) {
                                       --var11;
                                       var12 = var11;
                                       var9 = true;
                                       var8 = true;
                                    } else if (var3 <= 1 && var2 <= var1.parenCount) {
                                       --var11;
                                       var4 = new RENode(var1, (byte)20, (Object)null);
                                       var4.num = var2 - 1;
                                       var4.flags = 4;
                                       var8 = true;
                                    } else {
                                       var1.index = var12;
                                       var2 = this.doOctal(var1);
                                       var11 = var1.index;
                                       var4 = new RENode(var1, (byte)22, (Object)null);
                                       var6 = (char)var2;
                                    }
                                    break label227;
                                 }

                                 var2 = 10 * var2 + unDigit(var6);
                                 ++var3;
                              }
                        }
                     } else if (var6 == '0') {
                        var4 = new RENode(var1, (byte)22, (Object)null);
                        var6 = 0;
                        break;
                     } else {
                        var2 = unDigit(var6);
                        var3 = 1;

                        while(true) {
                           ++var11;
                           if (var11 >= var10.length || !isDigit(var6 = var10[var11])) {
                              --var11;
                              var4 = new RENode(var1, (byte)20, (Object)null);
                              var4.num = var2 - 1;
                              var4.flags = 4;
                              var8 = true;
                              break label227;
                           }

                           var2 = 10 * var2 + unDigit(var6);
                           ++var3;
                        }
                     }
                  case 'D':
                     var4 = new RENode(var1, (byte)15, (Object)null);
                     break;
                  case 'S':
                     var4 = new RENode(var1, (byte)19, (Object)null);
                     break;
                  case 'W':
                     var4 = new RENode(var1, (byte)17, (Object)null);
                     break;
                  case 'c':
                     ++var11;
                     var6 = var10[var11];
                     if (var6 >= 'A' && var6 <= 'Z' || var6 >= 'a' && var6 <= 'z') {
                        var6 = Character.toUpperCase(var6);
                        var6 = (char)(var6 ^ 64);
                        var4 = new RENode(var1, (byte)22, (Object)null);
                        break;
                     }

                     var11 -= 2;
                     var12 = var11;
                     var9 = true;
                     var8 = true;
                     break;
                  case 'd':
                     var4 = new RENode(var1, (byte)14, (Object)null);
                     break;
                  case 'f':
                  case 'n':
                  case 'r':
                  case 't':
                  case 'v':
                     var6 = getEscape(var6);
                     var4 = new RENode(var1, (byte)22, (Object)null);
                     break;
                  case 's':
                     var4 = new RENode(var1, (byte)18, (Object)null);
                     break;
                  case 'u':
                     if (var11 + 4 < var10.length && isHex(var10[var11 + 1]) && isHex(var10[var11 + 2]) && isHex(var10[var11 + 3]) && isHex(var10[var11 + 4])) {
                        var2 = (((unHex(var10[var11 + 1]) << 4) + unHex(var10[var11 + 2]) << 4) + unHex(var10[var11 + 3]) << 4) + unHex(var10[var11 + 4]);
                        var6 = (char)var2;
                        var11 += 4;
                        var4 = new RENode(var1, (byte)22, (Object)null);
                        break;
                     }

                     var12 = var11;
                     var9 = true;
                     var8 = true;
                     break;
                  case 'w':
                     var4 = new RENode(var1, (byte)16, (Object)null);
                     break;
                  case 'x':
                     var12 = var11++;
                     if (var11 < var10.length && isHex(var6 = var10[var11])) {
                        var2 = unHex(var6);
                        ++var11;
                        if (var11 < var10.length && isHex(var6 = var10[var11])) {
                           var2 <<= 4;
                           var2 += unHex(var6);
                        } else if (var1.cx.getLanguageVersion() != 0 && var1.cx.getLanguageVersion() <= 140) {
                           --var11;
                        } else {
                           var11 = var12;
                           var2 = 120;
                        }
                     } else {
                        var11 = var12;
                        var2 = 120;
                     }

                     var4 = new RENode(var1, (byte)22, (Object)null);
                     var6 = (char)var2;
                     break;
                  default:
                     var12 = var11;
                     var9 = true;
                     var8 = true;
               }

               if (var4 != null && !var8) {
                  var4.chr = var6;
                  var4.flags = 6;
               }

               var8 = false;
               if (!var9) {
                  ++var11;
                  break;
               } else {
                  var9 = false;
               }
            default:
               do {
                  ++var11;
               } while(var11 != var10.length && "|^${*+?().[\\".indexOf(var10[var11]) == -1);

               var3 = var11 - var12;
               if (var11 != var10.length && var3 > 1 && "{*+?".indexOf(var10[var11]) != -1) {
                  --var11;
                  --var3;
               }

               if (var3 > 255) {
                  var3 = 255;
                  var11 = var12 + var3;
               }

               var4 = new RENode(var1, (byte)(var3 == 1 ? 22 : 21), new Integer(var12));
               var4.flags = 4;
               if (var3 > 1) {
                  var4.kid2 = var11;
               } else {
                  var4.flags = (byte)(var4.flags | 2);
                  var4.chr = var10[var12];
               }
               break;
            case '|':
               return new RENode(var1, (byte)0, (Object)null);
         }

         var1.index = var11;
         return var4;
      }
   }

   RENode parseItem(CompilerState var1) {
      char[] var4 = var1.source;
      int var5 = var1.index;
      RENode var2;
      switch (var5 < var4.length ? var4[var5] : '\u0000') {
         case '$':
            var1.index = var5 + 1;
            return new RENode(var1, (byte)(var5 != var1.indexBegin && (var4[var5 - 1] != '(' && var4[var5 - 1] != '|' || var5 - 1 != var1.indexBegin && var4[var5 - 2] == '\\') ? 3 : 26), (Object)null);
         case '\\':
            ++var5;
            byte var3;
            switch (var5 < var4.length ? var4[var5] : '\u0000') {
               case 'B':
                  var3 = 5;
                  break;
               case 'b':
                  var3 = 4;
                  break;
               default:
                  return this.parseQuantAtom(var1);
            }

            var1.index = var5 + 1;
            var2 = new RENode(var1, var3, (Object)null);
            var2.flags = (byte)(var2.flags | 4);
            return var2;
         case '^':
            var1.index = var5 + 1;
            var2 = new RENode(var1, (byte)2, (Object)null);
            var2.flags = (byte)(var2.flags | 1);
            return var2;
         default:
            return this.parseQuantAtom(var1);
      }
   }

   RENode parseQuantAtom(CompilerState var1) {
      RENode var2 = this.parseAtom(var1);
      if (var2 == null) {
         return null;
      } else {
         char[] var8 = var1.source;
         int var9 = var1.index;

         label97:
         while(var9 < var8.length) {
            RENode var5;
            label91:
            switch (var8[var9]) {
               case '*':
                  ++var9;
                  var2 = new RENode(var1, (byte)7, var2);
                  break;
               case '+':
                  ++var9;
                  var5 = new RENode(var1, (byte)8, var2);
                  if ((var2.flags & 4) != 0) {
                     var5.flags = (byte)(var5.flags | 4);
                  }

                  var2 = var5;
                  break;
               case '?':
                  ++var9;
                  var2 = new RENode(var1, (byte)9, var2);
                  break;
               case '{':
                  ++var9;
                  char var4;
                  if (var9 == var8.length || !isDigit(var4 = var8[var9])) {
                     this.reportError("msg.bad.quant", String.valueOf(var8[var1.index]), var1);
                     return null;
                  }

                  int var6 = unDigit(var4);

                  do {
                     ++var9;
                     if (var9 >= var8.length || !isDigit(var4 = var8[var9])) {
                        int var7;
                        if (var8[var9] != ',') {
                           if (var6 == 0) {
                              this.reportError("msg.zero.quant", this.tail(var8, var1.index), var1);
                              return null;
                           }

                           var7 = var6;
                        } else {
                           ++var9;
                           int var3 = var9;
                           if (!isDigit(var8[var9])) {
                              var7 = 0;
                           } else {
                              var7 = unDigit(var8[var9]);

                              while(true) {
                                 ++var9;
                                 if (!isDigit(var4 = var8[var9])) {
                                    if (var7 == 0) {
                                       this.reportError("msg.zero.quant", this.tail(var8, var1.index), var1);
                                       return null;
                                    }

                                    if (var6 > var7) {
                                       this.reportError("msg.max.lt.min", this.tail(var8, var3), var1);
                                       return null;
                                    }
                                    break;
                                 }

                                 var7 = 10 * var7 + unDigit(var4);
                                 if (var7 >> 16 != 0) {
                                    this.reportError("msg.overlarge.max", String.valueOf(var8[var3]), var1);
                                    return null;
                                 }
                              }
                           }
                        }

                        if (var8[var9] != '}') {
                           this.reportError("msg.unterm.quant", String.valueOf(var8[var1.index]), var1);
                           return null;
                        }

                        ++var9;
                        var5 = new RENode(var1, (byte)6, var2);
                        if (var6 > 0 && (var2.flags & 4) != 0) {
                           var5.flags = (byte)(var5.flags | 4);
                        }

                        var5.min = (short)var6;
                        var5.max = (short)var7;
                        var2 = var5;
                        break label91;
                     }

                     var6 = 10 * var6 + unDigit(var4);
                  } while(var6 >> 16 == 0);

                  this.reportError("msg.overlarge.max", this.tail(var8, var9), var1);
                  return null;
               default:
                  break label97;
            }

            if (var9 < var8.length && var8[var9] == '?') {
               var2.flags = (byte)(var2.flags | 128);
               ++var9;
            }
         }

         var1.index = var9;
         return var2;
      }
   }

   private RENode parseRegExp(CompilerState var1) {
      RENode var2 = this.parseAltern(var1);
      if (var2 == null) {
         return null;
      } else {
         char[] var3 = var1.source;
         int var4 = var1.index;
         if (var4 < var3.length && var3[var4] == '|') {
            RENode var5 = var2;
            var2 = new RENode(var1, (byte)1, var2);
            if (var2 == null) {
               return null;
            }

            var2.flags = (byte)(var5.flags & 5);
            RENode var6 = var2;

            do {
               ++var4;
               var1.index = var4;
               if (var4 >= var3.length || var3[var4] != '|' && var3[var4] != ')') {
                  var5 = this.parseAltern(var1);
                  var4 = var1.index;
               } else {
                  var5 = new RENode(var1, (byte)0, (Object)null);
               }

               if (var5 == null) {
                  return null;
               }

               RENode var7 = new RENode(var1, (byte)1, var5);
               if (var7 == null) {
                  return null;
               }

               var6.next = var7;
               var6.flags = (byte)(var6.flags | 16);
               var7.flags = (byte)(var5.flags & 5 | 8);
               var6 = var7;
            } while(var4 < var3.length && var3[var4] == '|');
         }

         return var2;
      }
   }

   private Object prefix(Context var1, Scriptable var2, Object[] var3) {
      return this.execSub(var1, var2, var3, 2);
   }

   private NativeRegExp realThis(Scriptable var1, IdFunction var2, boolean var3) {
      while(!(var1 instanceof NativeRegExp)) {
         var1 = this.nextInstanceCheck(var1, var2, var3);
      }

      return (NativeRegExp)var1;
   }

   private void reportError(String var1, String var2, CompilerState var3) {
      Object[] var4 = new Object[]{var2};
      throw NativeGlobal.constructError(var3.cx, "SyntaxError", ScriptRuntime.getMessage(var1, var4), var3.scope);
   }

   protected void setIdValue(int var1, Object var2) {
      if (var1 == 1) {
         this.setLastIndex(ScriptRuntime.toInt32(var2));
      } else {
         super.setIdValue(var1, var2);
      }
   }

   void setLastIndex(int var1) {
      this.lastIndex = var1;
   }

   private void setNext(CompilerState var1, RENode var2, RENode var3) {
      this.fixNext(var1, var2, var3, (RENode)null);
   }

   private String tail(char[] var1, int var2) {
      return new String(var1, var2, var1.length - var2);
   }

   private Object test(Context var1, Scriptable var2, Object[] var3) {
      Object var4 = this.execSub(var1, var2, var3, 0);
      if (var4 == null || !var4.equals(Boolean.TRUE)) {
         var4 = Boolean.FALSE;
      }

      return var4;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append('/');
      var1.append(this.source);
      var1.append('/');
      if ((this.flags & 1) != 0) {
         var1.append('g');
      }

      if ((this.flags & 2) != 0) {
         var1.append('i');
      }

      if ((this.flags & 4) != 0) {
         var1.append('m');
      }

      return var1.toString();
   }

   static int unDigit(char var0) {
      return var0 - 48;
   }

   static int unHex(char var0) {
      return var0 >= '0' && var0 <= '9' ? var0 - 48 : 10 + Character.toLowerCase(var0) - 97;
   }
}
