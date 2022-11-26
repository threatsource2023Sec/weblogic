package org.mozilla.javascript;

public class NativeNumber extends IdScriptable {
   private static final int MAX_PRECISION = 100;
   private static final int Id_constructor = 1;
   private static final int Id_toString = 2;
   private static final int Id_valueOf = 3;
   private static final int Id_toLocaleString = 4;
   private static final int Id_toFixed = 5;
   private static final int Id_toExponential = 6;
   private static final int Id_toPrecision = 7;
   private static final int MAX_PROTOTYPE_ID = 7;
   private static final double defaultValue = 0.0;
   private double doubleValue;
   private boolean prototypeFlag;

   public NativeNumber() {
      this.doubleValue = 0.0;
   }

   public NativeNumber(double var1) {
      this.doubleValue = var1;
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      if (this.prototypeFlag) {
         switch (var1) {
            case 1:
               return this.jsConstructor(var6, var5 == null);
            case 2:
               return this.realThis(var5, var2).jsFunction_toString(toBase(var6, 0));
            case 3:
               return this.wrap_double(this.realThis(var5, var2).jsFunction_valueOf());
            case 4:
               return this.realThis(var5, var2).jsFunction_toLocaleString(toBase(var6, 0));
            case 5:
               return this.realThis(var5, var2).jsFunction_toFixed(var3, var6);
            case 6:
               return this.realThis(var5, var2).jsFunction_toExponential(var3, var6);
            case 7:
               return this.realThis(var5, var2).jsFunction_toPrecision(var3, var6);
         }
      }

      return super.execMethod(var1, var2, var3, var4, var5, var6);
   }

   protected void fillConstructorProperties(Context var1, IdFunction var2, boolean var3) {
      boolean var4 = true;
      var2.defineProperty("NaN", this.wrap_double(ScriptRuntime.NaN), 7);
      var2.defineProperty("POSITIVE_INFINITY", this.wrap_double(Double.POSITIVE_INFINITY), 7);
      var2.defineProperty("NEGATIVE_INFINITY", this.wrap_double(Double.NEGATIVE_INFINITY), 7);
      var2.defineProperty("MAX_VALUE", this.wrap_double(Double.MAX_VALUE), 7);
      var2.defineProperty("MIN_VALUE", this.wrap_double(Double.MIN_VALUE), 7);
      super.fillConstructorProperties(var1, var2, var3);
   }

   public String getClassName() {
      return "Number";
   }

   protected String getIdName(int var1) {
      if (this.prototypeFlag) {
         switch (var1) {
            case 1:
               return "constructor";
            case 2:
               return "toString";
            case 3:
               return "valueOf";
            case 4:
               return "toLocaleString";
            case 5:
               return "toFixed";
            case 6:
               return "toExponential";
            case 7:
               return "toPrecision";
         }
      }

      return null;
   }

   public static void init(Context var0, Scriptable var1, boolean var2) {
      NativeNumber var3 = new NativeNumber();
      var3.prototypeFlag = true;
      var3.addAsPrototype(7, var0, var1, var2);
   }

   private Object jsConstructor(Object[] var1, boolean var2) {
      double var3 = var1.length >= 1 ? ScriptRuntime.toNumber(var1[0]) : 0.0;
      return var2 ? new NativeNumber(var3) : this.wrap_double(var3);
   }

   private String jsFunction_toExponential(Context var1, Object[] var2) {
      return this.num_to(var1, var2, 1, 3, 0, 100, 1);
   }

   private String jsFunction_toFixed(Context var1, Object[] var2) {
      return this.num_to(var1, var2, 2, 2, -20, 100, 0);
   }

   private String jsFunction_toLocaleString(int var1) {
      return this.jsFunction_toString(var1);
   }

   private String jsFunction_toPrecision(Context var1, Object[] var2) {
      return this.num_to(var1, var2, 0, 4, 1, 100, 0);
   }

   private String jsFunction_toString(int var1) {
      return ScriptRuntime.numberToString(this.doubleValue, var1);
   }

   private double jsFunction_valueOf() {
      return this.doubleValue;
   }

   protected int mapNameToId(String var1) {
      if (!this.prototypeFlag) {
         return 0;
      } else {
         byte var2 = 0;
         String var3 = null;
         char var4;
         switch (var1.length()) {
            case 7:
               var4 = var1.charAt(0);
               if (var4 == 't') {
                  var3 = "toFixed";
                  var2 = 5;
               } else if (var4 == 'v') {
                  var3 = "valueOf";
                  var2 = 3;
               }
               break;
            case 8:
               var3 = "toString";
               var2 = 2;
            case 9:
            case 10:
            case 12:
            default:
               break;
            case 11:
               var4 = var1.charAt(0);
               if (var4 == 'c') {
                  var3 = "constructor";
                  var2 = 1;
               } else if (var4 == 't') {
                  var3 = "toPrecision";
                  var2 = 7;
               }
               break;
            case 13:
               var3 = "toExponential";
               var2 = 6;
               break;
            case 14:
               var3 = "toLocaleString";
               var2 = 4;
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
               return 1;
            case 3:
               return 0;
            case 4:
               return 1;
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

   private String num_to(Context var1, Object[] var2, int var3, int var4, int var5, int var6, int var7) {
      int var8;
      if (var2.length == 0) {
         var8 = 0;
         var4 = var3;
      } else {
         var8 = ScriptRuntime.toInt32(var2[0]);
         if (var8 < var5 || var8 > var6) {
            String var10 = ScriptRuntime.getMessage1("msg.bad.precision", ScriptRuntime.toString(var2[0]));
            throw NativeGlobal.constructError(var1, "RangeError", var10, this);
         }
      }

      StringBuffer var9 = new StringBuffer();
      DToA.JS_dtostr(var9, var4, var8 + var7, this.doubleValue);
      return var9.toString();
   }

   private NativeNumber realThis(Scriptable var1, IdFunction var2) {
      while(!(var1 instanceof NativeNumber)) {
         var1 = this.nextInstanceCheck(var1, var2, true);
      }

      return (NativeNumber)var1;
   }

   private static int toBase(Object[] var0, int var1) {
      return var1 < var0.length ? ScriptRuntime.toInt32(var0[var1]) : 10;
   }

   public String toString() {
      return this.jsFunction_toString(10);
   }
}
