package org.mozilla.javascript;

public class NativeBoolean extends IdScriptable {
   private static final int Id_constructor = 1;
   private static final int Id_toString = 2;
   private static final int Id_valueOf = 3;
   private static final int MAX_PROTOTYPE_ID = 3;
   private boolean booleanValue;
   private boolean prototypeFlag;

   public NativeBoolean() {
   }

   public NativeBoolean(boolean var1) {
      this.booleanValue = var1;
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      if (this.prototypeFlag) {
         if (var1 == 1) {
            return this.jsConstructor(var6, var5 == null);
         }

         if (var1 == 2) {
            return this.realThis(var5, var2).jsFunction_toString();
         }

         if (var1 == 3) {
            return this.wrap_boolean(this.realThis(var5, var2).jsFunction_valueOf());
         }
      }

      return super.execMethod(var1, var2, var3, var4, var5, var6);
   }

   public String getClassName() {
      return "Boolean";
   }

   public Object getDefaultValue(Class var1) {
      return var1 == ScriptRuntime.BooleanClass ? this.wrap_boolean(this.booleanValue) : super.getDefaultValue(var1);
   }

   protected String getIdName(int var1) {
      if (this.prototypeFlag) {
         if (var1 == 1) {
            return "constructor";
         }

         if (var1 == 2) {
            return "toString";
         }

         if (var1 == 3) {
            return "valueOf";
         }
      }

      return null;
   }

   public static void init(Context var0, Scriptable var1, boolean var2) {
      NativeBoolean var3 = new NativeBoolean();
      var3.prototypeFlag = true;
      var3.addAsPrototype(3, var0, var1, var2);
   }

   private Object jsConstructor(Object[] var1, boolean var2) {
      boolean var3 = ScriptRuntime.toBoolean(var1, 0);
      return var2 ? new NativeBoolean(var3) : this.wrap_boolean(var3);
   }

   private String jsFunction_toString() {
      return this.booleanValue ? "true" : "false";
   }

   private boolean jsFunction_valueOf() {
      return this.booleanValue;
   }

   protected int mapNameToId(String var1) {
      if (!this.prototypeFlag) {
         return 0;
      } else {
         byte var2 = 0;
         String var3 = null;
         int var4 = var1.length();
         if (var4 == 7) {
            var3 = "valueOf";
            var2 = 3;
         } else if (var4 == 8) {
            var3 = "toString";
            var2 = 2;
         } else if (var4 == 11) {
            var3 = "constructor";
            var2 = 1;
         }

         if (var3 != null && var3 != var1 && !var3.equals(var1)) {
            var2 = 0;
         }

         return var2;
      }
   }

   public int methodArity(int var1) {
      if (this.prototypeFlag) {
         if (var1 == 1) {
            return 1;
         }

         if (var1 == 2) {
            return 0;
         }

         if (var1 == 3) {
            return 0;
         }
      }

      return super.methodArity(var1);
   }

   private NativeBoolean realThis(Scriptable var1, IdFunction var2) {
      while(!(var1 instanceof NativeBoolean)) {
         var1 = this.nextInstanceCheck(var1, var2, true);
      }

      return (NativeBoolean)var1;
   }
}
