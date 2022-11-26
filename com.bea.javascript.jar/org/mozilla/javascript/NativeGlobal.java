package org.mozilla.javascript;

import java.io.IOException;
import java.io.StringReader;

public class NativeGlobal implements IdFunctionMaster {
   private static String uriReservedPlusPound = ";/?:@&=+$,#";
   private static String uriUnescaped = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_.!~*'()";
   private static final int Id_decodeURI = 1;
   private static final int Id_decodeURIComponent = 2;
   private static final int Id_encodeURI = 3;
   private static final int Id_encodeURIComponent = 4;
   private static final int Id_escape = 5;
   private static final int Id_eval = 6;
   private static final int Id_isFinite = 7;
   private static final int Id_isNaN = 8;
   private static final int Id_parseFloat = 9;
   private static final int Id_parseInt = 10;
   private static final int Id_unescape = 11;
   private static final int LAST_SCOPE_FUNCTION_ID = 11;
   private static final int Id_new_CommonError = 12;
   private boolean scopeSlaveFlag;

   public static EcmaError constructError(Context var0, String var1, String var2, Object var3) {
      int[] var4 = new int[1];
      String var5 = Context.getSourcePositionFromStack(var4);
      return constructError(var0, var1, var2, var3, var5, var4[0], 0, (String)null);
   }

   public static EcmaError constructError(Context var0, String var1, String var2, Object var3, String var4, int var5, int var6, String var7) {
      Scriptable var8;
      try {
         var8 = (Scriptable)var3;
      } catch (ClassCastException var14) {
         throw new RuntimeException(var14.toString());
      }

      Object[] var9 = new Object[]{var2};

      try {
         Scriptable var10 = var0.newObject(var8, var1, var9);
         return new EcmaError((NativeError)var10, var4, var5, var6, var7);
      } catch (PropertyException var11) {
         throw new RuntimeException(var11.toString());
      } catch (JavaScriptException var12) {
         throw new RuntimeException(var12.toString());
      } catch (NotAFunctionException var13) {
         throw new RuntimeException(var13.toString());
      }
   }

   private static String decode(Context var0, String var1, String var2) {
      int var4 = 0;
      char[] var9 = new char[6];

      StringBuffer var10;
      for(var10 = new StringBuffer(); var4 < var1.length(); ++var4) {
         char var5 = var1.charAt(var4);
         if (var5 == '%') {
            int var3 = var4;
            if (var4 + 2 >= var1.length()) {
               throw Context.reportRuntimeError0("msg.bad.uri");
            }

            if (!isHex(var1.charAt(var4 + 1)) || !isHex(var1.charAt(var4 + 2))) {
               throw Context.reportRuntimeError0("msg.bad.uri");
            }

            int var8 = unHex(var1.charAt(var4 + 1)) * 16 + unHex(var1.charAt(var4 + 2));
            var4 += 2;
            if ((var8 & 128) == 0) {
               var5 = (char)var8;
            } else {
               int var12;
               for(var12 = 1; (var8 & 128 >>> var12) != 0; ++var12) {
               }

               if (var12 == 1 || var12 > 6) {
                  throw Context.reportRuntimeError0("msg.bad.uri");
               }

               var9[0] = (char)var8;
               if (var4 + 3 * (var12 - 1) >= var1.length()) {
                  throw Context.reportRuntimeError0("msg.bad.uri");
               }

               for(int var11 = 1; var11 < var12; ++var11) {
                  ++var4;
                  if (var1.charAt(var4) != '%') {
                     throw Context.reportRuntimeError0("msg.bad.uri");
                  }

                  if (!isHex(var1.charAt(var4 + 1)) || !isHex(var1.charAt(var4 + 2))) {
                     throw Context.reportRuntimeError0("msg.bad.uri");
                  }

                  var8 = unHex(var1.charAt(var4 + 1)) * 16 + unHex(var1.charAt(var4 + 2));
                  if ((var8 & 192) != 128) {
                     throw Context.reportRuntimeError0("msg.bad.uri");
                  }

                  var4 += 2;
                  var9[var11] = (char)var8;
               }

               int var7 = utf8ToOneUcs4Char(var9, var12);
               if (var7 >= 65536) {
                  var7 -= 65536;
                  if (var7 > 1048575) {
                     throw Context.reportRuntimeError0("msg.bad.uri");
                  }

                  var5 = (char)((var7 & 1023) + '\udc00');
                  char var6 = (char)((var7 >>> 10) + '\ud800');
                  var10.append(var6);
               } else {
                  var5 = (char)var7;
               }
            }

            if (var2.indexOf(var5) != -1) {
               for(int var13 = 0; var13 < var4 - var3 + 1; ++var13) {
                  var10.append(var1.charAt(var3 + var13));
               }
            } else {
               var10.append(var5);
            }
         } else {
            var10.append(var5);
         }
      }

      return var10.toString();
   }

   private static String encode(Context var0, String var1, String var2) {
      int var4 = 0;
      char[] var9 = new char[6];

      StringBuffer var10;
      for(var10 = new StringBuffer(); var4 < var1.length(); ++var4) {
         char var6 = var1.charAt(var4);
         if (var2.indexOf(var6) != -1) {
            var10.append(var6);
         } else {
            if (var6 >= '\udc00' && var6 <= '\udfff') {
               throw Context.reportRuntimeError0("msg.bad.uri");
            }

            int var8;
            if (var6 >= '\ud800' && var6 <= '\udbff') {
               ++var4;
               if (var4 == var1.length()) {
                  throw Context.reportRuntimeError0("msg.bad.uri");
               }

               char var7 = var1.charAt(var4);
               if (var7 < '\udc00' || var7 > '\udfff') {
                  throw Context.reportRuntimeError0("msg.bad.uri");
               }

               var8 = (var6 - '\ud800' << 10) + (var7 - '\udc00') + 65536;
            } else {
               var8 = var6;
            }

            int var5 = oneUcs4ToUtf8Char(var9, var8);

            for(int var3 = 0; var3 < var5; ++var3) {
               var10.append('%');
               if (var9[var3] < 16) {
                  var10.append('0');
               }

               var10.append(Integer.toHexString(var9[var3]));
            }
         }
      }

      return var10.toString();
   }

   public static Object evalSpecial(Context var0, Scriptable var1, Object var2, Object[] var3, String var4, int var5) throws JavaScriptException {
      if (var3.length < 1) {
         return Undefined.instance;
      } else {
         Object var6 = var3[0];
         if (!(var6 instanceof String)) {
            String var15 = Context.getMessage0("msg.eval.nonstring");
            Context.reportWarning(var15);
            return var6;
         } else {
            int[] var7 = new int[]{var5};
            if (var4 == null) {
               var4 = Context.getSourcePositionFromStack(var7);
               if (var4 == null) {
                  var4 = "";
                  var7[0] = 1;
               }
            }

            var4 = var4 + "(eval)";

            try {
               StringReader var8 = new StringReader((String)var6);
               Object var9 = var0.getSecurityDomainForStackDepth(3);
               int var10 = var0.getOptimizationLevel();
               var0.setOptimizationLevel(-1);
               Script var11 = var0.compileReader(var1, var8, var4, var7[0], var9);
               var0.setOptimizationLevel(var10);
               if (var11 == null) {
                  String var16 = Context.getMessage0("msg.syntax");
                  throw new EvaluatorException(var16);
               } else {
                  InterpretedScript var12 = (InterpretedScript)var11;
                  var12.itsData.itsFromEvalCode = true;
                  Object var13 = var12.call(var0, var1, (Scriptable)var2, (Object[])null);
                  return var13;
               }
            } catch (IOException var14) {
               throw new RuntimeException("unexpected io exception");
            }
         }
      }
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      if (this.scopeSlaveFlag) {
         switch (var1) {
            case 1:
               return this.js_decodeURI(var3, var6);
            case 2:
               return this.js_decodeURIComponent(var3, var6);
            case 3:
               return this.js_encodeURI(var3, var6);
            case 4:
               return this.js_encodeURIComponent(var3, var6);
            case 5:
               return this.js_escape(var3, var6);
            case 6:
               return this.js_eval(var3, var4, var6);
            case 7:
               return this.js_isFinite(var3, var6);
            case 8:
               return this.js_isNaN(var3, var6);
            case 9:
               return this.js_parseFloat(var3, var6);
            case 10:
               return this.js_parseInt(var3, var6);
            case 11:
               return this.js_unescape(var3, var6);
            case 12:
               return this.new_CommonError(var2, var3, var4, var6);
         }
      }

      throw IdFunction.onBadMethodId(this, var1);
   }

   private static String getMethodName(int var0) {
      switch (var0) {
         case 1:
            return "decodeURI";
         case 2:
            return "decodeURIComponent";
         case 3:
            return "encodeURI";
         case 4:
            return "encodeURIComponent";
         case 5:
            return "escape";
         case 6:
            return "eval";
         case 7:
            return "isFinite";
         case 8:
            return "isNaN";
         case 9:
            return "parseFloat";
         case 10:
            return "parseInt";
         case 11:
            return "unescape";
         default:
            return null;
      }
   }

   private static char hex_digit_to_char(int var0) {
      return (char)(var0 <= 9 ? var0 + 48 : var0 + 55);
   }

   public static void init(Context var0, Scriptable var1, boolean var2) {
      NativeGlobal var3 = new NativeGlobal();
      var3.scopeSlaveFlag = true;

      for(int var4 = 1; var4 <= 11; ++var4) {
         String var5 = getMethodName(var4);
         IdFunction var6 = new IdFunction(var3, var5, var4);
         var6.setParentScope(var1);
         if (var2) {
            var6.sealObject();
         }

         ScriptableObject.defineProperty(var1, var5, var6, 2);
      }

      ScriptableObject.defineProperty(var1, "NaN", ScriptRuntime.NaNobj, 2);
      ScriptableObject.defineProperty(var1, "Infinity", new Double(Double.POSITIVE_INFINITY), 2);
      ScriptableObject.defineProperty(var1, "undefined", Undefined.instance, 2);
      String[] var10 = new String[]{"ConversionError", "EvalError", "RangeError", "ReferenceError", "SyntaxError", "TypeError", "URIError"};

      for(int var11 = 0; var11 < var10.length; ++var11) {
         String var7 = var10[var11];
         IdFunction var8 = new IdFunction(var3, var7, 12);
         var8.setFunctionType(2);
         var8.setParentScope(var1);
         ScriptableObject.defineProperty(var1, var7, var8, 2);
         Scriptable var9 = ScriptRuntime.newObject(var0, var1, "Error", ScriptRuntime.emptyArgs);
         var9.put("name", var9, var7);
         var8.put("prototype", var8, var9);
         if (var2) {
            var8.sealObject();
            if (var9 instanceof ScriptableObject) {
               ((ScriptableObject)var9).sealObject();
            }
         }
      }

   }

   private static boolean isHex(char var0) {
      return var0 >= '0' && var0 <= '9' || var0 >= 'a' && var0 <= 'f' || var0 >= 'A' && var0 <= 'F';
   }

   private String js_decodeURI(Context var1, Object[] var2) {
      String var3 = ScriptRuntime.toString(var2, 0);
      return decode(var1, var3, uriReservedPlusPound);
   }

   private String js_decodeURIComponent(Context var1, Object[] var2) {
      String var3 = ScriptRuntime.toString(var2, 0);
      return decode(var1, var3, "");
   }

   private Object js_encodeURI(Context var1, Object[] var2) {
      String var3 = ScriptRuntime.toString(var2, 0);
      return encode(var1, var3, uriReservedPlusPound + uriUnescaped);
   }

   private String js_encodeURIComponent(Context var1, Object[] var2) {
      String var3 = ScriptRuntime.toString(var2, 0);
      return encode(var1, var3, uriUnescaped);
   }

   private Object js_escape(Context var1, Object[] var2) {
      boolean var3 = true;
      boolean var4 = true;
      boolean var5 = true;
      String var6 = ScriptRuntime.toString(var2, 0);
      int var7 = 7;
      if (var2.length > 1) {
         double var8 = ScriptRuntime.toNumber(var2[1]);
         if (var8 != var8 || (double)(var7 = (int)var8) != var8 || (var7 & -8) != 0) {
            String var10 = Context.getMessage0("msg.bad.esc.mask");
            Context.reportError(var10);
            var7 = 7;
         }
      }

      StringBuffer var11 = new StringBuffer();

      for(int var9 = 0; var9 < var6.length(); ++var9) {
         char var12 = var6.charAt(var9);
         if (var7 == 0 || (var12 < '0' || var12 > '9') && (var12 < 'A' || var12 > 'Z') && (var12 < 'a' || var12 > 'z') && var12 != '@' && var12 != '*' && var12 != '_' && var12 != '-' && var12 != '.' && (var12 != '/' && var12 != '+' || var7 <= 3)) {
            if (var12 < 256) {
               if (var12 == ' ' && var7 == 2) {
                  var11.append('+');
               } else {
                  var11.append('%');
                  var11.append(hex_digit_to_char(var12 >>> 4));
                  var11.append(hex_digit_to_char(var12 & 15));
               }
            } else {
               var11.append('%');
               var11.append('u');
               var11.append(hex_digit_to_char(var12 >>> 12));
               var11.append(hex_digit_to_char((var12 & 3840) >>> 8));
               var11.append(hex_digit_to_char((var12 & 240) >>> 4));
               var11.append(hex_digit_to_char(var12 & 15));
            }
         } else {
            var11.append((char)var12);
         }
      }

      return var11.toString();
   }

   private Object js_eval(Context var1, Scriptable var2, Object[] var3) throws JavaScriptException {
      String var4 = ScriptRuntime.getMessage1("msg.cant.call.indirect", "eval");
      throw constructError(var1, "EvalError", var4, var2);
   }

   private Object js_isFinite(Context var1, Object[] var2) {
      if (var2.length < 1) {
         return Boolean.FALSE;
      } else {
         double var3 = ScriptRuntime.toNumber(var2[0]);
         return var3 == var3 && var3 != Double.POSITIVE_INFINITY && var3 != Double.NEGATIVE_INFINITY ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   private Object js_isNaN(Context var1, Object[] var2) {
      if (var2.length < 1) {
         return Boolean.TRUE;
      } else {
         double var3 = ScriptRuntime.toNumber(var2[0]);
         return var3 != var3 ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   private Object js_parseFloat(Context var1, Object[] var2) {
      if (var2.length < 1) {
         return ScriptRuntime.NaNobj;
      } else {
         String var3 = ScriptRuntime.toString(var2[0]);
         int var4 = var3.length();
         if (var4 == 0) {
            return ScriptRuntime.NaNobj;
         } else {
            int var5;
            char var6;
            for(var5 = 0; TokenStream.isJSSpace(var6 = var3.charAt(var5)) && var5 + 1 < var4; ++var5) {
            }

            int var7 = var5;
            if (var6 == '+' || var6 == '-') {
               ++var5;
               var6 = var3.charAt(var5);
            }

            if (var6 == 'I') {
               if (var5 + 8 <= var4 && var3.substring(var5, var5 + 8).equals("Infinity")) {
                  double var11 = var3.charAt(var7) == '-' ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
                  return new Double(var11);
               } else {
                  return ScriptRuntime.NaNobj;
               }
            } else {
               int var8 = -1;

               label67:
               for(int var9 = -1; var5 < var4; ++var5) {
                  switch (var3.charAt(var5)) {
                     case '+':
                     case '-':
                        if (var9 != var5 - 1) {
                           break label67;
                        }
                        break;
                     case '.':
                        if (var8 != -1) {
                           break label67;
                        }

                        var8 = var5;
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
                        break;
                     case 'E':
                     case 'e':
                        if (var9 == -1) {
                           var9 = var5;
                           break;
                        }
                     default:
                        break label67;
                  }
               }

               var3 = var3.substring(var7, var5);

               try {
                  return Double.valueOf(var3);
               } catch (NumberFormatException var10) {
                  return ScriptRuntime.NaNobj;
               }
            }
         }
      }
   }

   private Object js_parseInt(Context var1, Object[] var2) {
      String var3 = ScriptRuntime.toString(var2, 0);
      int var4 = ScriptRuntime.toInt32(var2, 1);
      int var5 = var3.length();
      if (var5 == 0) {
         return ScriptRuntime.NaNobj;
      } else {
         boolean var6 = false;
         int var7 = 0;

         char var8;
         do {
            var8 = var3.charAt(var7);
            if (!Character.isWhitespace(var8)) {
               break;
            }

            ++var7;
         } while(var7 < var5);

         if (var8 == '+' || (var6 = var8 == '-')) {
            ++var7;
         }

         boolean var9 = true;
         if (var4 == 0) {
            var4 = -1;
         } else {
            if (var4 < 2 || var4 > 36) {
               return ScriptRuntime.NaNobj;
            }

            if (var4 == 16 && var5 - var7 > 1 && var3.charAt(var7) == '0') {
               var8 = var3.charAt(var7 + 1);
               if (var8 == 'x' || var8 == 'X') {
                  var7 += 2;
               }
            }
         }

         if (var4 == -1) {
            var4 = 10;
            if (var5 - var7 > 1 && var3.charAt(var7) == '0') {
               var8 = var3.charAt(var7 + 1);
               if (var8 != 'x' && var8 != 'X') {
                  if (var8 != '.') {
                     var4 = 8;
                     ++var7;
                  }
               } else {
                  var4 = 16;
                  var7 += 2;
               }
            }
         }

         double var10 = ScriptRuntime.stringToNumber(var3, var7, var4);
         return new Double(var6 ? -var10 : var10);
      }
   }

   private Object js_unescape(Context var1, Object[] var2) {
      String var3 = ScriptRuntime.toString(var2, 0);
      int var4 = var3.indexOf(37);
      if (var4 >= 0) {
         int var5 = var3.length();
         char[] var6 = var3.toCharArray();
         int var7 = var4;

         for(int var8 = var4; var8 != var5; ++var7) {
            char var9 = var6[var8];
            ++var8;
            if (var9 == '%' && var8 != var5) {
               int var10;
               int var11;
               if (var6[var8] == 'u') {
                  var11 = var8 + 1;
                  var10 = var8 + 5;
               } else {
                  var11 = var8;
                  var10 = var8 + 2;
               }

               if (var10 <= var5) {
                  int var12 = 0;

                  for(int var13 = var11; var13 != var10; ++var13) {
                     var12 = var12 << 4 | TokenStream.xDigitToInt(var6[var13]);
                  }

                  if (var12 >= 0) {
                     var9 = (char)var12;
                     var8 = var10;
                  }
               }
            }

            var6[var7] = var9;
         }

         var3 = new String(var6, 0, var7);
      }

      return var3;
   }

   public int methodArity(int var1) {
      if (this.scopeSlaveFlag) {
         switch (var1) {
            case 1:
               return 1;
            case 2:
               return 1;
            case 3:
               return 1;
            case 4:
               return 1;
            case 5:
               return 1;
            case 6:
               return 1;
            case 7:
               return 1;
            case 8:
               return 1;
            case 9:
               return 1;
            case 10:
               return 2;
            case 11:
               return 1;
            case 12:
               return 1;
         }
      }

      return -1;
   }

   private Object new_CommonError(IdFunction var1, Context var2, Scriptable var3, Object[] var4) {
      NativeError var5 = new NativeError();
      var5.setPrototype((Scriptable)var1.get("prototype", var1));
      var5.setParentScope(var3);
      if (var4.length > 0) {
         var5.put("message", var5, var4[0]);
      }

      return var5;
   }

   private static int oneUcs4ToUtf8Char(char[] var0, int var1) {
      int var2 = 1;
      if ((var1 & -128) == 0) {
         var0[0] = (char)var1;
      } else {
         int var4 = var1 >>> 11;

         for(var2 = 2; var4 != 0; ++var2) {
            var4 >>>= 5;
         }

         int var3 = var2;

         while(true) {
            --var3;
            if (var3 <= 0) {
               var0[0] = (char)(256 - (1 << 8 - var2) + var1);
               break;
            }

            var0[var3] = (char)(var1 & 63 | 128);
            var1 >>>= 6;
         }
      }

      return var2;
   }

   static EcmaError typeError0(String var0, Object var1) {
      return constructError(Context.getContext(), "TypeError", ScriptRuntime.getMessage0(var0), var1);
   }

   static EcmaError typeError1(String var0, Object var1, Object var2) {
      return constructError(Context.getContext(), "TypeError", ScriptRuntime.getMessage1(var0, var1), var2);
   }

   private static int unHex(char var0) {
      if (var0 >= '0' && var0 <= '9') {
         return var0 - 48;
      } else {
         return var0 >= 'a' && var0 <= 'f' ? var0 - 97 + 10 : var0 - 65 + 10;
      }
   }

   private static int utf8ToOneUcs4Char(char[] var0, int var1) {
      int var3 = 0;
      int var2;
      if (var1 == 1) {
         var2 = var0[0];
      } else {
         var2 = var0[var3++] & (1 << 7 - var1) - 1;

         while(true) {
            --var1;
            if (var1 <= 0) {
               break;
            }

            var2 = var2 << 6 | var0[var3++] & 63;
         }
      }

      return var2;
   }
}
