package org.mozilla.javascript;

public class NativeString extends IdScriptable {
   private static final int ConstructorId_fromCharCode = -1;
   private static final int Id_length = 1;
   private static final int MAX_INSTANCE_ID = 1;
   private static final int Id_constructor = 2;
   private static final int Id_toString = 3;
   private static final int Id_valueOf = 4;
   private static final int Id_charAt = 5;
   private static final int Id_charCodeAt = 6;
   private static final int Id_indexOf = 7;
   private static final int Id_lastIndexOf = 8;
   private static final int Id_split = 9;
   private static final int Id_substring = 10;
   private static final int Id_toLowerCase = 11;
   private static final int Id_toUpperCase = 12;
   private static final int Id_substr = 13;
   private static final int Id_concat = 14;
   private static final int Id_slice = 15;
   private static final int Id_bold = 16;
   private static final int Id_italics = 17;
   private static final int Id_fixed = 18;
   private static final int Id_strike = 19;
   private static final int Id_small = 20;
   private static final int Id_big = 21;
   private static final int Id_blink = 22;
   private static final int Id_sup = 23;
   private static final int Id_sub = 24;
   private static final int Id_fontsize = 25;
   private static final int Id_fontcolor = 26;
   private static final int Id_link = 27;
   private static final int Id_anchor = 28;
   private static final int Id_equals = 29;
   private static final int Id_equalsIgnoreCase = 30;
   private static final int Id_match = 31;
   private static final int Id_search = 32;
   private static final int Id_replace = 33;
   private static final int MAX_PROTOTYPE_ID = 33;
   private static final String defaultValue = "";
   private String string;
   private boolean prototypeFlag;

   public NativeString() {
      this.string = "";
   }

   public NativeString(String var1) {
      this.string = var1;
   }

   private static RegExpProxy checkReProxy(Context var0) {
      RegExpProxy var1 = var0.getRegExpProxy();
      if (var1 == null) {
         throw Context.reportRuntimeError0("msg.no.regexp");
      } else {
         return var1;
      }
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      if (this.prototypeFlag) {
         switch (var1) {
            case -1:
               return jsStaticFunction_fromCharCode(var6);
            case 0:
            case 1:
            default:
               break;
            case 2:
               return jsConstructor(var6, var5 == null);
            case 3:
               return this.realThis(var5, var2).jsFunction_toString();
            case 4:
               return this.realThis(var5, var2).jsFunction_valueOf();
            case 5:
               return jsFunction_charAt(ScriptRuntime.toString(var5), var6);
            case 6:
               return this.wrap_double(jsFunction_charCodeAt(ScriptRuntime.toString(var5), var6));
            case 7:
               return this.wrap_int(jsFunction_indexOf(ScriptRuntime.toString(var5), var6));
            case 8:
               return this.wrap_int(jsFunction_lastIndexOf(ScriptRuntime.toString(var5), var6));
            case 9:
               return jsFunction_split(var3, var4, ScriptRuntime.toString(var5), var6);
            case 10:
               return jsFunction_substring(var3, ScriptRuntime.toString(var5), var6);
            case 11:
               return jsFunction_toLowerCase(ScriptRuntime.toString(var5));
            case 12:
               return jsFunction_toUpperCase(ScriptRuntime.toString(var5));
            case 13:
               return jsFunction_substr(ScriptRuntime.toString(var5), var6);
            case 14:
               return jsFunction_concat(ScriptRuntime.toString(var5), var6);
            case 15:
               return jsFunction_slice(ScriptRuntime.toString(var5), var6);
            case 16:
               return this.realThis(var5, var2).tagify("b", (String)null, (String)null);
            case 17:
               return this.realThis(var5, var2).tagify("i", (String)null, (String)null);
            case 18:
               return this.realThis(var5, var2).tagify("tt", (String)null, (String)null);
            case 19:
               return this.realThis(var5, var2).tagify("strike", (String)null, (String)null);
            case 20:
               return this.realThis(var5, var2).tagify("small", (String)null, (String)null);
            case 21:
               return this.realThis(var5, var2).tagify("big", (String)null, (String)null);
            case 22:
               return this.realThis(var5, var2).tagify("blink", (String)null, (String)null);
            case 23:
               return this.realThis(var5, var2).tagify("sup", (String)null, (String)null);
            case 24:
               return this.realThis(var5, var2).tagify("sub", (String)null, (String)null);
            case 25:
               return this.realThis(var5, var2).tagify("font size", "font", ScriptRuntime.toString(var6, 0));
            case 26:
               return this.realThis(var5, var2).tagify("font color", "font", ScriptRuntime.toString(var6, 0));
            case 27:
               return this.realThis(var5, var2).tagify("a href", "a", ScriptRuntime.toString(var6, 0));
            case 28:
               return this.realThis(var5, var2).tagify("a name", "a", ScriptRuntime.toString(var6, 0));
            case 29:
               return this.wrap_boolean(jsFunction_equals(ScriptRuntime.toString(var5), ScriptRuntime.toString(var6, 0)));
            case 30:
               return this.wrap_boolean(jsFunction_equalsIgnoreCase(ScriptRuntime.toString(var5), ScriptRuntime.toString(var6, 0)));
            case 31:
               return checkReProxy(var3).match(var3, var4, var5, var6);
            case 32:
               return checkReProxy(var3).search(var3, var4, var5, var6);
            case 33:
               return checkReProxy(var3).replace(var3, var4, var5, var6);
         }
      }

      return super.execMethod(var1, var2, var3, var4, var5, var6);
   }

   protected void fillConstructorProperties(Context var1, IdFunction var2, boolean var3) {
      this.addIdFunctionProperty(var2, -1, var3);
      super.fillConstructorProperties(var1, var2, var3);
   }

   private static int find_split(Scriptable var0, String var1, String var2, Object var3, int[] var4, int[] var5, boolean[] var6, String[][] var7) {
      int var8 = var4[0];
      int var9 = var1.length();
      Context var10 = Context.getContext();
      int var11 = var10.getLanguageVersion();
      if (var11 == 120 && var3 == null && var2.length() == 1 && var2.charAt(0) == ' ') {
         if (var8 == 0) {
            while(var8 < var9 && Character.isWhitespace(var1.charAt(var8))) {
               ++var8;
            }

            var4[0] = var8;
         }

         if (var8 == var9) {
            return -1;
         } else {
            while(var8 < var9 && !Character.isWhitespace(var1.charAt(var8))) {
               ++var8;
            }

            int var12;
            for(var12 = var8; var12 < var9 && Character.isWhitespace(var1.charAt(var12)); ++var12) {
            }

            var5[0] = var12 - var8;
            return var8;
         }
      } else if (var8 > var9) {
         return -1;
      } else if (var3 != null) {
         return var10.getRegExpProxy().find_split(var0, var1, var2, var3, var4, var5, var6, var7);
      } else if (var11 != 0 && var11 < 130 && var9 == 0) {
         return -1;
      } else if (var2.length() == 0) {
         if (var11 == 120) {
            if (var8 == var9) {
               var5[0] = 1;
               return var8;
            } else {
               return var8 + 1;
            }
         } else {
            return var8 == var9 ? -1 : var8 + 1;
         }
      } else if (var4[0] >= var9) {
         return var9;
      } else {
         var8 = var1.indexOf(var2, var4[0]);
         return var8 != -1 ? var8 : var9;
      }
   }

   public Object get(int var1, Scriptable var2) {
      return var1 >= 0 && var1 < this.string.length() ? this.string.substring(var1, var1 + 1) : super.get(var1, var2);
   }

   public String getClassName() {
      return "String";
   }

   protected int getIdDefaultAttributes(int var1) {
      return var1 == 1 ? 7 : super.getIdDefaultAttributes(var1);
   }

   protected String getIdName(int var1) {
      if (var1 == 1) {
         return "length";
      } else {
         if (this.prototypeFlag) {
            switch (var1) {
               case -1:
                  return "fromCharCode";
               case 0:
               case 1:
               default:
                  break;
               case 2:
                  return "constructor";
               case 3:
                  return "toString";
               case 4:
                  return "valueOf";
               case 5:
                  return "charAt";
               case 6:
                  return "charCodeAt";
               case 7:
                  return "indexOf";
               case 8:
                  return "lastIndexOf";
               case 9:
                  return "split";
               case 10:
                  return "substring";
               case 11:
                  return "toLowerCase";
               case 12:
                  return "toUpperCase";
               case 13:
                  return "substr";
               case 14:
                  return "concat";
               case 15:
                  return "slice";
               case 16:
                  return "bold";
               case 17:
                  return "italics";
               case 18:
                  return "fixed";
               case 19:
                  return "strike";
               case 20:
                  return "small";
               case 21:
                  return "big";
               case 22:
                  return "blink";
               case 23:
                  return "sup";
               case 24:
                  return "sub";
               case 25:
                  return "fontsize";
               case 26:
                  return "fontcolor";
               case 27:
                  return "link";
               case 28:
                  return "anchor";
               case 29:
                  return "equals";
               case 30:
                  return "equalsIgnoreCase";
               case 31:
                  return "match";
               case 32:
                  return "search";
               case 33:
                  return "replace";
            }
         }

         return null;
      }
   }

   protected Object getIdValue(int var1) {
      return var1 == 1 ? this.wrap_int(this.string.length()) : super.getIdValue(var1);
   }

   public static void init(Context var0, Scriptable var1, boolean var2) {
      NativeString var3 = new NativeString();
      var3.prototypeFlag = true;
      var3.addAsPrototype(33, var0, var1, var2);
   }

   private static Object jsConstructor(Object[] var0, boolean var1) {
      String var2 = var0.length >= 1 ? ScriptRuntime.toString(var0[0]) : "";
      return var1 ? new NativeString(var2) : var2;
   }

   private static String jsFunction_charAt(String var0, Object[] var1) {
      double var2 = ScriptRuntime.toInteger(var1, 0);
      return !(var2 < 0.0) && !(var2 >= (double)var0.length()) ? var0.substring((int)var2, (int)var2 + 1) : "";
   }

   private static double jsFunction_charCodeAt(String var0, Object[] var1) {
      double var2 = ScriptRuntime.toInteger(var1, 0);
      return !(var2 < 0.0) && !(var2 >= (double)var0.length()) ? (double)var0.charAt((int)var2) : ScriptRuntime.NaN;
   }

   private static String jsFunction_concat(String var0, Object[] var1) {
      int var2 = var1.length;
      if (var2 == 0) {
         return var0;
      } else {
         StringBuffer var3 = new StringBuffer();
         var3.append(var0);

         for(int var4 = 0; var4 < var2; ++var4) {
            var3.append(ScriptRuntime.toString(var1[var4]));
         }

         return var3.toString();
      }
   }

   private static boolean jsFunction_equals(String var0, String var1) {
      return var0.equals(var1);
   }

   private static boolean jsFunction_equalsIgnoreCase(String var0, String var1) {
      return var0.equalsIgnoreCase(var1);
   }

   private static int jsFunction_indexOf(String var0, Object[] var1) {
      String var2 = ScriptRuntime.toString(var1, 0);
      double var3 = ScriptRuntime.toInteger(var1, 1);
      if (var3 > (double)var0.length()) {
         return -1;
      } else {
         if (var3 < 0.0) {
            var3 = 0.0;
         }

         return var0.indexOf(var2, (int)var3);
      }
   }

   private static int jsFunction_lastIndexOf(String var0, Object[] var1) {
      String var2 = ScriptRuntime.toString(var1, 0);
      double var3 = ScriptRuntime.toNumber(var1, 1);
      if (var3 == var3 && !(var3 > (double)var0.length())) {
         if (var3 < 0.0) {
            var3 = 0.0;
         }
      } else {
         var3 = (double)var0.length();
      }

      return var0.lastIndexOf(var2, (int)var3);
   }

   private static String jsFunction_slice(String var0, Object[] var1) {
      if (var1.length != 0) {
         double var2 = ScriptRuntime.toInteger(var1[0]);
         int var6 = var0.length();
         if (var2 < 0.0) {
            var2 += (double)var6;
            if (var2 < 0.0) {
               var2 = 0.0;
            }
         } else if (var2 > (double)var6) {
            var2 = (double)var6;
         }

         double var4;
         if (var1.length == 1) {
            var4 = (double)var6;
         } else {
            var4 = ScriptRuntime.toInteger(var1[1]);
            if (var4 < 0.0) {
               var4 += (double)var6;
               if (var4 < 0.0) {
                  var4 = 0.0;
               }
            } else if (var4 > (double)var6) {
               var4 = (double)var6;
            }

            if (var4 < var2) {
               var4 = var2;
            }
         }

         return var0.substring((int)var2, (int)var4);
      } else {
         return var0;
      }
   }

   private static Object jsFunction_split(Context var0, Scriptable var1, String var2, Object[] var3) {
      Scriptable var4 = ScriptableObject.getTopLevelScope(var1);
      Scriptable var5 = ScriptRuntime.newObject(var0, (Scriptable)var4, (String)"Array", (Object[])null);
      if (var3.length < 1) {
         var5.put(0, var5, var2);
         return var5;
      } else {
         boolean var6 = var3.length > 1 && var3[1] != Undefined.instance;
         long var7 = 0L;
         if (var6) {
            var7 = ScriptRuntime.toUint32(var3[1]);
            if (var7 > (long)var2.length()) {
               var7 = (long)(1 + var2.length());
            }
         }

         String var9 = null;
         int[] var10 = new int[1];
         Object var11 = null;
         RegExpProxy var12 = var0.getRegExpProxy();
         if (var12 != null && var12.isRegExp(var3[0])) {
            var11 = var3[0];
         } else {
            var9 = ScriptRuntime.toString(var3[0]);
            var10[0] = var9.length();
         }

         int[] var13 = new int[1];
         int var15 = 0;
         boolean[] var16 = new boolean[1];
         String[][] var17 = new String[][]{null};

         int var14;
         while((var14 = find_split(var1, var2, var9, var11, var13, var10, var16, var17)) >= 0 && (!var6 || (long)var15 < var7) && var14 <= var2.length()) {
            String var18;
            if (var2.length() == 0) {
               var18 = var2;
            } else {
               var18 = var2.substring(var13[0], var14);
            }

            var5.put(var15, var5, var18);
            ++var15;
            if (var11 != null && var16[0]) {
               int var19 = var17[0].length;

               for(int var20 = 0; var20 < var19 && (!var6 || (long)var15 < var7); ++var20) {
                  var5.put(var15, var5, var17[0][var20]);
                  ++var15;
               }

               var16[0] = false;
            }

            var13[0] = var14 + var10[0];
            if (var0.getLanguageVersion() < 130 && var0.getLanguageVersion() != 0 && !var6 && var13[0] == var2.length()) {
               break;
            }
         }

         return var5;
      }
   }

   private static String jsFunction_substr(String var0, Object[] var1) {
      if (var1.length < 1) {
         return var0;
      } else {
         double var2 = ScriptRuntime.toInteger(var1[0]);
         int var6 = var0.length();
         if (var2 < 0.0) {
            var2 += (double)var6;
            if (var2 < 0.0) {
               var2 = 0.0;
            }
         } else if (var2 > (double)var6) {
            var2 = (double)var6;
         }

         double var4;
         if (var1.length == 1) {
            var4 = (double)var6;
         } else {
            var4 = ScriptRuntime.toInteger(var1[1]);
            if (var4 < 0.0) {
               var4 = 0.0;
            }

            var4 += var2;
            if (var4 > (double)var6) {
               var4 = (double)var6;
            }
         }

         return var0.substring((int)var2, (int)var4);
      }
   }

   private static String jsFunction_substring(Context var0, String var1, Object[] var2) {
      int var3 = var1.length();
      double var4 = ScriptRuntime.toInteger(var2, 0);
      if (var4 < 0.0) {
         var4 = 0.0;
      } else if (var4 > (double)var3) {
         var4 = (double)var3;
      }

      double var6;
      if (var2.length > 1 && var2[1] != Undefined.instance) {
         var6 = ScriptRuntime.toInteger(var2[1]);
         if (var6 < 0.0) {
            var6 = 0.0;
         } else if (var6 > (double)var3) {
            var6 = (double)var3;
         }

         if (var6 < var4) {
            if (var0.getLanguageVersion() != 120) {
               double var8 = var4;
               var4 = var6;
               var6 = var8;
            } else {
               var6 = var4;
            }
         }
      } else {
         var6 = (double)var3;
      }

      return var1.substring((int)var4, (int)var6);
   }

   private static String jsFunction_toLowerCase(String var0) {
      return var0.toLowerCase();
   }

   private String jsFunction_toString() {
      return this.string;
   }

   private static String jsFunction_toUpperCase(String var0) {
      return var0.toUpperCase();
   }

   private String jsFunction_valueOf() {
      return this.string;
   }

   public double jsGet_length() {
      return (double)this.string.length();
   }

   private static String jsStaticFunction_fromCharCode(Object[] var0) {
      int var1 = var0.length;
      if (var1 < 1) {
         return "";
      } else {
         StringBuffer var2 = new StringBuffer(var1);

         for(int var3 = 0; var3 < var1; ++var3) {
            var2.append(ScriptRuntime.toUint16(var0[var3]));
         }

         return var2.toString();
      }
   }

   protected int mapNameToId(String var1) {
      if (var1.equals("length")) {
         return 1;
      } else {
         return this.prototypeFlag ? toPrototypeId(var1) : 0;
      }
   }

   protected int maxInstanceId() {
      return 1;
   }

   public int methodArity(int var1) {
      if (this.prototypeFlag) {
         switch (var1) {
            case -1:
               return 1;
            case 0:
            case 1:
            default:
               break;
            case 2:
               return 1;
            case 3:
               return 0;
            case 4:
               return 0;
            case 5:
               return 1;
            case 6:
               return 1;
            case 7:
               return 2;
            case 8:
               return 2;
            case 9:
               return 1;
            case 10:
               return 2;
            case 11:
               return 0;
            case 12:
               return 0;
            case 13:
               return 2;
            case 14:
               return 1;
            case 15:
               return 2;
            case 16:
               return 0;
            case 17:
               return 0;
            case 18:
               return 0;
            case 19:
               return 0;
            case 20:
               return 0;
            case 21:
               return 0;
            case 22:
               return 0;
            case 23:
               return 0;
            case 24:
               return 0;
            case 25:
               return 0;
            case 26:
               return 0;
            case 27:
               return 0;
            case 28:
               return 0;
            case 29:
               return 1;
            case 30:
               return 1;
            case 31:
               return 1;
            case 32:
               return 1;
            case 33:
               return 1;
         }
      }

      return super.methodArity(var1);
   }

   public void put(int var1, Scriptable var2, Object var3) {
      if (var1 < 0 || var1 >= this.string.length()) {
         super.put(var1, var2, var3);
      }
   }

   private NativeString realThis(Scriptable var1, IdFunction var2) {
      while(!(var1 instanceof NativeString)) {
         var1 = this.nextInstanceCheck(var1, var2, true);
      }

      return (NativeString)var1;
   }

   private String tagify(String var1, String var2, String var3) {
      StringBuffer var4 = new StringBuffer();
      var4.append('<');
      var4.append(var1);
      if (var3 != null) {
         var4.append("=\"");
         var4.append(var3);
         var4.append('"');
      }

      var4.append('>');
      var4.append(this.string);
      var4.append("</");
      var4.append(var2 == null ? var1 : var2);
      var4.append('>');
      return var4.toString();
   }

   private static int toPrototypeId(String var0) {
      byte var1;
      String var2;
      var1 = 0;
      var2 = null;
      char var3;
      label97:
      switch (var0.length()) {
         case 3:
            var3 = var0.charAt(2);
            if (var3 == 'b') {
               if (var0.charAt(0) == 's' && var0.charAt(1) == 'u') {
                  var1 = 24;
                  return var1;
               }
            } else if (var3 == 'g') {
               if (var0.charAt(0) == 'b' && var0.charAt(1) == 'i') {
                  var1 = 21;
                  return var1;
               }
            } else if (var3 == 'p' && var0.charAt(0) == 's' && var0.charAt(1) == 'u') {
               var1 = 23;
               return var1;
            }
            break;
         case 4:
            var3 = var0.charAt(0);
            if (var3 == 'b') {
               var2 = "bold";
               var1 = 16;
            } else if (var3 == 'l') {
               var2 = "link";
               var1 = 27;
            }
            break;
         case 5:
            switch (var0.charAt(4)) {
               case 'd':
                  var2 = "fixed";
                  var1 = 18;
                  break label97;
               case 'e':
                  var2 = "slice";
                  var1 = 15;
               case 'f':
               case 'g':
               case 'i':
               case 'j':
               case 'm':
               case 'n':
               case 'o':
               case 'p':
               case 'q':
               case 'r':
               case 's':
               default:
                  break label97;
               case 'h':
                  var2 = "match";
                  var1 = 31;
                  break label97;
               case 'k':
                  var2 = "blink";
                  var1 = 22;
                  break label97;
               case 'l':
                  var2 = "small";
                  var1 = 20;
                  break label97;
               case 't':
                  var2 = "split";
                  var1 = 9;
                  break label97;
            }
         case 6:
            switch (var0.charAt(1)) {
               case 'e':
                  var3 = var0.charAt(0);
                  if (var3 == 'l') {
                     var2 = "length";
                     var1 = 1;
                  } else if (var3 == 's') {
                     var2 = "search";
                     var1 = 32;
                  }
               case 'f':
               case 'g':
               case 'i':
               case 'j':
               case 'k':
               case 'l':
               case 'm':
               case 'p':
               case 'r':
               case 's':
               default:
                  break label97;
               case 'h':
                  var2 = "charAt";
                  var1 = 5;
                  break label97;
               case 'n':
                  var2 = "anchor";
                  var1 = 28;
                  break label97;
               case 'o':
                  var2 = "concat";
                  var1 = 14;
                  break label97;
               case 'q':
                  var2 = "equals";
                  var1 = 29;
                  break label97;
               case 't':
                  var2 = "strike";
                  var1 = 19;
                  break label97;
               case 'u':
                  var2 = "substr";
                  var1 = 13;
                  break label97;
            }
         case 7:
            switch (var0.charAt(1)) {
               case 'a':
                  var2 = "valueOf";
                  var1 = 4;
                  break label97;
               case 'e':
                  var2 = "replace";
                  var1 = 33;
                  break label97;
               case 'n':
                  var2 = "indexOf";
                  var1 = 7;
                  break label97;
               case 't':
                  var2 = "italics";
                  var1 = 17;
               default:
                  break label97;
            }
         case 8:
            var3 = var0.charAt(0);
            if (var3 == 'f') {
               var2 = "fontsize";
               var1 = 25;
            } else if (var3 == 't') {
               var2 = "toString";
               var1 = 3;
            }
            break;
         case 9:
            var3 = var0.charAt(0);
            if (var3 == 'f') {
               var2 = "fontcolor";
               var1 = 26;
            } else if (var3 == 's') {
               var2 = "substring";
               var1 = 10;
            }
            break;
         case 10:
            var2 = "charCodeAt";
            var1 = 6;
            break;
         case 11:
            switch (var0.charAt(2)) {
               case 'L':
                  var2 = "toLowerCase";
                  var1 = 11;
                  break;
               case 'U':
                  var2 = "toUpperCase";
                  var1 = 12;
                  break;
               case 'n':
                  var2 = "constructor";
                  var1 = 2;
                  break;
               case 's':
                  var2 = "lastIndexOf";
                  var1 = 8;
            }
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            break;
         case 16:
            var2 = "equalsIgnoreCase";
            var1 = 30;
      }

      if (var2 != null && var2 != var0 && !var2.equals(var0)) {
         var1 = 0;
      }

      return var1;
   }

   public String toString() {
      return this.string;
   }
}
