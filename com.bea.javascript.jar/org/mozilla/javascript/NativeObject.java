package org.mozilla.javascript;

import java.util.Hashtable;

public class NativeObject extends IdScriptable {
   private static final int Id_constructor = 1;
   private static final int Id_toString = 2;
   private static final int Id_toLocaleString = 3;
   private static final int Id_valueOf = 4;
   private static final int Id_hasOwnProperty = 5;
   private static final int Id_propertyIsEnumerable = 6;
   private static final int Id_isPrototypeOf = 7;
   private static final int MAX_PROTOTYPE_ID = 7;
   private boolean prototypeFlag;

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      if (this.prototypeFlag) {
         switch (var1) {
            case 1:
               return jsConstructor(var3, var6, var2, var5 == null);
            case 2:
               return jsFunction_toString(var3, var5);
            case 3:
               return jsFunction_toLocaleString(var3, var5);
            case 4:
               return jsFunction_valueOf(var5);
            case 5:
               return jsFunction_hasOwnProperty(var5, var6);
            case 6:
               return jsFunction_propertyIsEnumerable(var3, var5, var6);
            case 7:
               return jsFunction_isPrototypeOf(var3, var5, var6);
         }
      }

      return super.execMethod(var1, var2, var3, var4, var5, var6);
   }

   public String getClassName() {
      return "Object";
   }

   protected String getIdName(int var1) {
      if (this.prototypeFlag) {
         switch (var1) {
            case 1:
               return "constructor";
            case 2:
               return "toString";
            case 3:
               return "toLocaleString";
            case 4:
               return "valueOf";
            case 5:
               return "hasOwnProperty";
            case 6:
               return "propertyIsEnumerable";
            case 7:
               return "isPrototypeOf";
         }
      }

      return null;
   }

   public static void init(Context var0, Scriptable var1, boolean var2) {
      NativeObject var3 = new NativeObject();
      var3.prototypeFlag = true;
      var3.addAsPrototype(7, var0, var1, var2);
   }

   private static Object jsConstructor(Context var0, Object[] var1, Function var2, boolean var3) throws JavaScriptException {
      if (!var3) {
         return var2.construct(var0, var2.getParentScope(), var1);
      } else {
         return var1.length != 0 && var1[0] != null && var1[0] != Undefined.instance ? ScriptRuntime.toObject(var2.getParentScope(), var1[0]) : new NativeObject();
      }
   }

   private static Object jsFunction_hasOwnProperty(Scriptable var0, Object[] var1) {
      return var1.length != 0 && var0.has(ScriptRuntime.toString(var1[0]), var0) ? Boolean.TRUE : Boolean.FALSE;
   }

   private static Object jsFunction_isPrototypeOf(Context var0, Scriptable var1, Object[] var2) {
      if (var2.length != 0 && var2[0] instanceof Scriptable) {
         Scriptable var3 = (Scriptable)var2[0];

         do {
            var3 = var3.getPrototype();
            if (var3 == var1) {
               return Boolean.TRUE;
            }
         } while(var3 != null);
      }

      return Boolean.FALSE;
   }

   private static Object jsFunction_propertyIsEnumerable(Context var0, Scriptable var1, Object[] var2) {
      try {
         if (var2.length != 0) {
            String var3 = ScriptRuntime.toString(var2[0]);
            if (var1.has(var3, var1)) {
               int var4 = ((ScriptableObject)var1).getAttributes(var3, var1);
               if ((var4 & 2) == 0) {
                  return Boolean.TRUE;
               }
            }
         }
      } catch (PropertyException var5) {
      } catch (ClassCastException var6) {
      }

      return Boolean.FALSE;
   }

   private static String jsFunction_toLocaleString(Context var0, Scriptable var1) {
      return jsFunction_toString(var0, var1);
   }

   private static String jsFunction_toString(Context var0, Scriptable var1) {
      return var0.getLanguageVersion() != 120 ? "[object " + var1.getClassName() + "]" : toSource(var0, var1);
   }

   private static Object jsFunction_valueOf(Scriptable var0) {
      return var0;
   }

   protected int mapNameToId(String var1) {
      if (!this.prototypeFlag) {
         return 0;
      } else {
         byte var2 = 0;
         String var3 = null;
         switch (var1.length()) {
            case 7:
               var3 = "valueOf";
               var2 = 4;
               break;
            case 8:
               var3 = "toString";
               var2 = 2;
            case 9:
            case 10:
            case 12:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            default:
               break;
            case 11:
               var3 = "constructor";
               var2 = 1;
               break;
            case 13:
               var3 = "isPrototypeOf";
               var2 = 7;
               break;
            case 14:
               char var4 = var1.charAt(0);
               if (var4 == 'h') {
                  var3 = "hasOwnProperty";
                  var2 = 5;
               } else if (var4 == 't') {
                  var3 = "toLocaleString";
                  var2 = 3;
               }
               break;
            case 20:
               var3 = "propertyIsEnumerable";
               var2 = 6;
         }

         if (var3 != null && var3 != var1 && !var3.equals(var1)) {
            var2 = 0;
         }

         return var2;
      }
   }

   public int methodArity(int var1) {
      if (this.prototypeFlag) {
         switch (var1) {
            case 1:
               return 1;
            case 2:
               return 0;
            case 3:
               return 0;
            case 4:
               return 0;
            case 5:
               return 1;
            case 6:
               return 1;
            case 7:
               return 1;
         }
      }

      return super.methodArity(var1);
   }

   private static String toSource(Context var0, Scriptable var1) {
      Scriptable var2 = var1;
      if (var0.iterating == null) {
         var0.iterating = new Hashtable(31);
      }

      if (var0.iterating.get(var1) == Boolean.TRUE) {
         return "{}";
      } else {
         StringBuffer var3 = new StringBuffer("{");
         Object[] var4 = var1.getIds();

         for(int var5 = 0; var5 < var4.length; ++var5) {
            if (var5 > 0) {
               var3.append(", ");
            }

            Object var6 = var4[var5];
            String var7 = ScriptRuntime.toString(var6);
            Object var8 = var6 instanceof String ? var2.get((String)var6, var2) : var2.get(((Number)var6).intValue(), var2);
            if (var8 instanceof String) {
               var3.append(var7 + ":\"" + ScriptRuntime.escapeString(ScriptRuntime.toString(var8)) + "\"");
            } else {
               try {
                  var0.iterating.put(var2, Boolean.TRUE);
                  var3.append(var7 + ":" + ScriptRuntime.toString(var8));
               } finally {
                  var0.iterating.remove(var2);
               }
            }
         }

         var3.append('}');
         return var3.toString();
      }
   }

   public String toString() {
      Context var1 = Context.getCurrentContext();
      return var1 != null ? jsFunction_toString(var1, this) : "[object " + this.getClassName() + "]";
   }
}
