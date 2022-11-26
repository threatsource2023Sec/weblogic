package org.mozilla.javascript.regexp;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;

public class NativeRegExpCtor extends NativeFunction {
   private static final int Id_multiline = 1;
   private static final int Id_STAR = 2;
   private static final int Id_input = 3;
   private static final int Id_UNDERSCORE = 4;
   private static final int Id_lastMatch = 5;
   private static final int Id_AMPERSAND = 6;
   private static final int Id_lastParen = 7;
   private static final int Id_PLUS = 8;
   private static final int Id_leftContext = 9;
   private static final int Id_BACK_QUOTE = 10;
   private static final int Id_rightContext = 11;
   private static final int Id_QUOTE = 12;
   private static final int DOLLAR_ID_BASE = 12;
   private static final int Id_DOLLAR_1 = 13;
   private static final int Id_DOLLAR_2 = 14;
   private static final int Id_DOLLAR_3 = 15;
   private static final int Id_DOLLAR_4 = 16;
   private static final int Id_DOLLAR_5 = 17;
   private static final int Id_DOLLAR_6 = 18;
   private static final int Id_DOLLAR_7 = 19;
   private static final int Id_DOLLAR_8 = 20;
   private static final int Id_DOLLAR_9 = 21;
   private static final int MAX_INSTANCE_ID = 21;
   private static int idBase;

   public NativeRegExpCtor() {
      super.functionName = "RegExp";
   }

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) {
      return var4.length <= 0 || !(var4[0] instanceof NativeRegExp) || var4.length != 1 && var4[1] != Undefined.instance ? this.construct(var1, super.parent, var4) : var4[0];
   }

   public Scriptable construct(Context var1, Scriptable var2, Object[] var3) {
      NativeRegExp var4 = new NativeRegExp();
      var4.compile(var1, var2, var3);
      var4.setPrototype(ScriptableObject.getClassPrototype(var2, "RegExp"));
      var4.setParentScope(this.getParentScope());
      return var4;
   }

   public String getClassName() {
      return "Function";
   }

   protected int getIdDefaultAttributes(int var1) {
      int var2 = var1 - idBase;
      if (var2 >= 1 && var2 <= 21) {
         switch (var2) {
            case 1:
            case 2:
            case 3:
            case 4:
               return 4;
            default:
               return 5;
         }
      } else {
         return super.getIdDefaultAttributes(var1);
      }
   }

   protected String getIdName(int var1) {
      int var2 = var1 - idBase;
      if (var2 >= 1 && var2 <= 21) {
         switch (var2) {
            case 1:
               return "multiline";
            case 2:
               return "$*";
            case 3:
               return "input";
            case 4:
               return "$_";
            case 5:
               return "lastMatch";
            case 6:
               return "$&";
            case 7:
               return "lastParen";
            case 8:
               return "$+";
            case 9:
               return "leftContext";
            case 10:
               return "$`";
            case 11:
               return "rightContext";
            case 12:
               return "$'";
            default:
               int var3 = var2 - 12 - 1;
               char[] var4 = new char[]{'$', (char)(49 + var3)};
               return new String(var4);
         }
      } else {
         return super.getIdName(var1);
      }
   }

   protected Object getIdValue(int var1) {
      int var2 = var1 - idBase;
      if (var2 >= 1 && var2 <= 21) {
         RegExpImpl var3 = getImpl();
         switch (var2) {
            case 1:
            case 2:
               return this.wrap_boolean(var3.multiline);
            case 3:
            case 4:
               return stringResult(var3.input);
            case 5:
            case 6:
               return stringResult(var3.lastMatch);
            case 7:
            case 8:
               return stringResult(var3.lastParen);
            case 9:
            case 10:
               return stringResult(var3.leftContext);
            case 11:
            case 12:
               return stringResult(var3.rightContext);
            default:
               int var4 = var2 - 12 - 1;
               return var3.getParenSubString(var4).toString();
         }
      } else {
         return super.getIdValue(var1);
      }
   }

   static RegExpImpl getImpl() {
      Context var0 = Context.getCurrentContext();
      return (RegExpImpl)ScriptRuntime.getRegExpProxy(var0);
   }

   protected int mapNameToId(String var1) {
      byte var2 = 0;
      String var3 = null;
      switch (var1.length()) {
         case 2:
            switch (var1.charAt(1)) {
               case '&':
                  if (var1.charAt(0) == '$') {
                     var2 = 6;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '\'':
                  if (var1.charAt(0) == '$') {
                     var2 = 12;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '*':
                  if (var1.charAt(0) == '$') {
                     var2 = 2;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '+':
                  if (var1.charAt(0) == '$') {
                     var2 = 8;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '1':
                  if (var1.charAt(0) == '$') {
                     var2 = 13;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '2':
                  if (var1.charAt(0) == '$') {
                     var2 = 14;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '3':
                  if (var1.charAt(0) == '$') {
                     var2 = 15;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '4':
                  if (var1.charAt(0) == '$') {
                     var2 = 16;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '5':
                  if (var1.charAt(0) == '$') {
                     var2 = 17;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '6':
                  if (var1.charAt(0) == '$') {
                     var2 = 18;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '7':
                  if (var1.charAt(0) == '$') {
                     var2 = 19;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '8':
                  if (var1.charAt(0) == '$') {
                     var2 = 20;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '9':
                  if (var1.charAt(0) == '$') {
                     var2 = 21;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '_':
                  if (var1.charAt(0) == '$') {
                     var2 = 4;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
                  break;
               case '`':
                  if (var1.charAt(0) == '$') {
                     var2 = 10;
                     return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
                  }
            }
         case 3:
         case 4:
         case 6:
         case 7:
         case 8:
         case 10:
         default:
            break;
         case 5:
            var3 = "input";
            var2 = 3;
            break;
         case 9:
            char var4 = var1.charAt(4);
            if (var4 == 'M') {
               var3 = "lastMatch";
               var2 = 5;
            } else if (var4 == 'P') {
               var3 = "lastParen";
               var2 = 7;
            } else if (var4 == 'i') {
               var3 = "multiline";
               var2 = 1;
            }
            break;
         case 11:
            var3 = "leftContext";
            var2 = 9;
            break;
         case 12:
            var3 = "rightContext";
            var2 = 11;
      }

      if (var3 != null && var3 != var1 && !var3.equals(var1)) {
         var2 = 0;
      }

      return var2 != 0 ? idBase + var2 : super.mapNameToId(var1);
   }

   protected int maxInstanceId() {
      if (idBase == 0) {
         idBase = super.maxInstanceId();
      }

      return idBase + 21;
   }

   protected void setIdValue(int var1, Object var2) {
      switch (var1 - idBase) {
         case 1:
         case 2:
            getImpl().multiline = ScriptRuntime.toBoolean(var2);
            return;
         case 3:
         case 4:
            getImpl().input = ScriptRuntime.toString(var2);
            return;
         default:
            super.setIdValue(var1, var2);
      }
   }

   private static String stringResult(Object var0) {
      return var0 == null ? "" : var0.toString();
   }
}
