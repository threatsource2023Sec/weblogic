package org.mozilla.javascript;

public class NativeMath extends IdScriptable {
   private static final int Id_abs = 1;
   private static final int Id_acos = 2;
   private static final int Id_asin = 3;
   private static final int Id_atan = 4;
   private static final int Id_atan2 = 5;
   private static final int Id_ceil = 6;
   private static final int Id_cos = 7;
   private static final int Id_exp = 8;
   private static final int Id_floor = 9;
   private static final int Id_log = 10;
   private static final int Id_max = 11;
   private static final int Id_min = 12;
   private static final int Id_pow = 13;
   private static final int Id_random = 14;
   private static final int Id_round = 15;
   private static final int Id_sin = 16;
   private static final int Id_sqrt = 17;
   private static final int Id_tan = 18;
   private static final int LAST_METHOD_ID = 18;
   private static final int Id_E = 19;
   private static final int Id_PI = 20;
   private static final int Id_LN10 = 21;
   private static final int Id_LN2 = 22;
   private static final int Id_LOG2E = 23;
   private static final int Id_LOG10E = 24;
   private static final int Id_SQRT1_2 = 25;
   private static final int Id_SQRT2 = 26;
   private static final int MAX_INSTANCE_ID = 26;
   private static final double E = Math.E;
   private static final double PI = Math.PI;
   private static final double LN10 = 2.302585092994046;
   private static final double LN2 = 0.6931471805599453;
   private static final double LOG2E = 1.4426950408889634;
   private static final double LOG10E = 0.4342944819032518;
   private static final double SQRT1_2 = 0.7071067811865476;
   private static final double SQRT2 = 1.4142135623730951;

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      switch (var1) {
         case 1:
            return this.wrap_double(this.js_abs(ScriptRuntime.toNumber(var6, 0)));
         case 2:
            return this.wrap_double(this.js_acos(ScriptRuntime.toNumber(var6, 0)));
         case 3:
            return this.wrap_double(this.js_asin(ScriptRuntime.toNumber(var6, 0)));
         case 4:
            return this.wrap_double(this.js_atan(ScriptRuntime.toNumber(var6, 0)));
         case 5:
            return this.wrap_double(this.js_atan2(ScriptRuntime.toNumber(var6, 0), ScriptRuntime.toNumber(var6, 1)));
         case 6:
            return this.wrap_double(this.js_ceil(ScriptRuntime.toNumber(var6, 0)));
         case 7:
            return this.wrap_double(this.js_cos(ScriptRuntime.toNumber(var6, 0)));
         case 8:
            return this.wrap_double(this.js_exp(ScriptRuntime.toNumber(var6, 0)));
         case 9:
            return this.wrap_double(this.js_floor(ScriptRuntime.toNumber(var6, 0)));
         case 10:
            return this.wrap_double(this.js_log(ScriptRuntime.toNumber(var6, 0)));
         case 11:
            return this.wrap_double(this.js_max(var6));
         case 12:
            return this.wrap_double(this.js_min(var6));
         case 13:
            return this.wrap_double(this.js_pow(ScriptRuntime.toNumber(var6, 0), ScriptRuntime.toNumber(var6, 1)));
         case 14:
            return this.wrap_double(this.js_random());
         case 15:
            return this.wrap_double(this.js_round(ScriptRuntime.toNumber(var6, 0)));
         case 16:
            return this.wrap_double(this.js_sin(ScriptRuntime.toNumber(var6, 0)));
         case 17:
            return this.wrap_double(this.js_sqrt(ScriptRuntime.toNumber(var6, 0)));
         case 18:
            return this.wrap_double(this.js_tan(ScriptRuntime.toNumber(var6, 0)));
         default:
            return super.execMethod(var1, var2, var3, var4, var5, var6);
      }
   }

   public String getClassName() {
      return "Math";
   }

   private double getField(int var1) {
      switch (var1) {
         case 19:
            return Math.E;
         case 20:
            return Math.PI;
         case 21:
            return 2.302585092994046;
         case 22:
            return 0.6931471805599453;
         case 23:
            return 1.4426950408889634;
         case 24:
            return 0.4342944819032518;
         case 25:
            return 0.7071067811865476;
         case 26:
            return 1.4142135623730951;
         default:
            return 0.0;
      }
   }

   protected int getIdDefaultAttributes(int var1) {
      return var1 > 18 ? 7 : super.getIdDefaultAttributes(var1);
   }

   protected String getIdName(int var1) {
      switch (var1) {
         case 1:
            return "abs";
         case 2:
            return "acos";
         case 3:
            return "asin";
         case 4:
            return "atan";
         case 5:
            return "atan2";
         case 6:
            return "ceil";
         case 7:
            return "cos";
         case 8:
            return "exp";
         case 9:
            return "floor";
         case 10:
            return "log";
         case 11:
            return "max";
         case 12:
            return "min";
         case 13:
            return "pow";
         case 14:
            return "random";
         case 15:
            return "round";
         case 16:
            return "sin";
         case 17:
            return "sqrt";
         case 18:
            return "tan";
         case 19:
            return "E";
         case 20:
            return "PI";
         case 21:
            return "LN10";
         case 22:
            return "LN2";
         case 23:
            return "LOG2E";
         case 24:
            return "LOG10E";
         case 25:
            return "SQRT1_2";
         case 26:
            return "SQRT2";
         default:
            return null;
      }
   }

   protected Object getIdValue(int var1) {
      return var1 > 18 ? this.cacheIdValue(var1, this.wrap_double(this.getField(var1))) : super.getIdValue(var1);
   }

   public static void init(Context var0, Scriptable var1, boolean var2) {
      NativeMath var3 = new NativeMath();
      var3.setSealFunctionsFlag(var2);
      var3.setFunctionParametrs(var0);
      var3.setPrototype(ScriptableObject.getObjectPrototype(var1));
      var3.setParentScope(var1);
      if (var2) {
         var3.sealObject();
      }

      ScriptableObject.defineProperty(var1, "Math", var3, 2);
   }

   private double js_abs(double var1) {
      return var1 == 0.0 ? 0.0 : (var1 < 0.0 ? -var1 : var1);
   }

   private double js_acos(double var1) {
      return var1 == var1 && var1 >= -1.0 && var1 <= 1.0 ? Math.acos(var1) : Double.NaN;
   }

   private double js_asin(double var1) {
      return var1 == var1 && var1 >= -1.0 && var1 <= 1.0 ? Math.asin(var1) : Double.NaN;
   }

   private double js_atan(double var1) {
      return Math.atan(var1);
   }

   private double js_atan2(double var1, double var3) {
      return Math.atan2(var1, var3);
   }

   private double js_ceil(double var1) {
      return Math.ceil(var1);
   }

   private double js_cos(double var1) {
      return Math.cos(var1);
   }

   private double js_exp(double var1) {
      return var1 == Double.POSITIVE_INFINITY ? var1 : (var1 == Double.NEGATIVE_INFINITY ? 0.0 : Math.exp(var1));
   }

   private double js_floor(double var1) {
      return Math.floor(var1);
   }

   private double js_log(double var1) {
      return var1 < 0.0 ? Double.NaN : Math.log(var1);
   }

   private double js_max(Object[] var1) {
      double var2 = Double.NEGATIVE_INFINITY;
      if (var1.length == 0) {
         return var2;
      } else {
         for(int var4 = 0; var4 < var1.length; ++var4) {
            double var5 = ScriptRuntime.toNumber(var1[var4]);
            if (var5 != var5) {
               return var5;
            }

            var2 = Math.max(var2, var5);
         }

         return var2;
      }
   }

   private double js_min(Object[] var1) {
      double var2 = Double.POSITIVE_INFINITY;
      if (var1.length == 0) {
         return var2;
      } else {
         for(int var4 = 0; var4 < var1.length; ++var4) {
            double var5 = ScriptRuntime.toNumber(var1[var4]);
            if (var5 != var5) {
               return var5;
            }

            var2 = Math.min(var2, var5);
         }

         return var2;
      }
   }

   private double js_pow(double var1, double var3) {
      if (var3 == 0.0) {
         return 1.0;
      } else if (var1 == 0.0 && var3 < 0.0) {
         if (1.0 / var1 > 0.0) {
            return Double.POSITIVE_INFINITY;
         } else {
            int var5 = (int)var3;
            return (double)var5 == var3 && (var5 & 1) != 0 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
         }
      } else {
         return Math.pow(var1, var3);
      }
   }

   private double js_random() {
      return Math.random();
   }

   private double js_round(double var1) {
      if (var1 != var1) {
         return var1;
      } else if (var1 != Double.POSITIVE_INFINITY && var1 != Double.NEGATIVE_INFINITY) {
         long var3 = Math.round(var1);
         if (var3 == 0L) {
            if (var1 < 0.0) {
               return ScriptRuntime.negativeZero;
            } else {
               return var1 == 0.0 ? var1 : 0.0;
            }
         } else {
            return (double)var3;
         }
      } else {
         return var1;
      }
   }

   private double js_sin(double var1) {
      return Math.sin(var1);
   }

   private double js_sqrt(double var1) {
      return Math.sqrt(var1);
   }

   private double js_tan(double var1) {
      return Math.tan(var1);
   }

   protected int mapNameToId(String var1) {
      byte var2;
      String var3;
      var2 = 0;
      var3 = null;
      char var4;
      label102:
      switch (var1.length()) {
         case 1:
            if (var1.charAt(0) == 'E') {
               var2 = 19;
               return var2;
            }
            break;
         case 2:
            if (var1.charAt(0) == 'P' && var1.charAt(1) == 'I') {
               var2 = 20;
               return var2;
            }
            break;
         case 3:
            switch (var1.charAt(0)) {
               case 'L':
                  if (var1.charAt(2) == '2' && var1.charAt(1) == 'N') {
                     var2 = 22;
                     return var2;
                  }
                  break label102;
               case 'a':
                  if (var1.charAt(2) == 's' && var1.charAt(1) == 'b') {
                     var2 = 1;
                     return var2;
                  }
                  break label102;
               case 'c':
                  if (var1.charAt(2) == 's' && var1.charAt(1) == 'o') {
                     var2 = 7;
                     return var2;
                  }
                  break label102;
               case 'e':
                  if (var1.charAt(2) == 'p' && var1.charAt(1) == 'x') {
                     var2 = 8;
                     return var2;
                  }
                  break label102;
               case 'l':
                  if (var1.charAt(2) == 'g' && var1.charAt(1) == 'o') {
                     var2 = 10;
                     return var2;
                  }
                  break label102;
               case 'm':
                  var4 = var1.charAt(2);
                  if (var4 == 'n') {
                     if (var1.charAt(1) == 'i') {
                        var2 = 12;
                        return var2;
                     }
                  } else if (var4 == 'x' && var1.charAt(1) == 'a') {
                     var2 = 11;
                     return var2;
                  }
                  break label102;
               case 'p':
                  if (var1.charAt(2) == 'w' && var1.charAt(1) == 'o') {
                     var2 = 13;
                     return var2;
                  }
                  break label102;
               case 's':
                  if (var1.charAt(2) == 'n' && var1.charAt(1) == 'i') {
                     var2 = 16;
                     return var2;
                  }
                  break label102;
               case 't':
                  if (var1.charAt(2) == 'n' && var1.charAt(1) == 'a') {
                     var2 = 18;
                     return var2;
                  }
               default:
                  break label102;
            }
         case 4:
            switch (var1.charAt(1)) {
               case 'N':
                  var3 = "LN10";
                  var2 = 21;
                  break label102;
               case 'c':
                  var3 = "acos";
                  var2 = 2;
                  break label102;
               case 'e':
                  var3 = "ceil";
                  var2 = 6;
                  break label102;
               case 'q':
                  var3 = "sqrt";
                  var2 = 17;
                  break label102;
               case 's':
                  var3 = "asin";
                  var2 = 3;
                  break label102;
               case 't':
                  var3 = "atan";
                  var2 = 4;
               default:
                  break label102;
            }
         case 5:
            switch (var1.charAt(0)) {
               case 'L':
                  var3 = "LOG2E";
                  var2 = 23;
                  break label102;
               case 'S':
                  var3 = "SQRT2";
                  var2 = 26;
                  break label102;
               case 'a':
                  var3 = "atan2";
                  var2 = 5;
                  break label102;
               case 'f':
                  var3 = "floor";
                  var2 = 9;
                  break label102;
               case 'r':
                  var3 = "round";
                  var2 = 15;
               default:
                  break label102;
            }
         case 6:
            var4 = var1.charAt(0);
            if (var4 == 'L') {
               var3 = "LOG10E";
               var2 = 24;
            } else if (var4 == 'r') {
               var3 = "random";
               var2 = 14;
            }
            break;
         case 7:
            var3 = "SQRT1_2";
            var2 = 25;
      }

      if (var3 != null && var3 != var1 && !var3.equals(var1)) {
         var2 = 0;
      }

      return var2;
   }

   protected int maxInstanceId() {
      return 26;
   }

   public int methodArity(int var1) {
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
            return 2;
         case 6:
            return 1;
         case 7:
            return 1;
         case 8:
            return 1;
         case 9:
            return 1;
         case 10:
            return 1;
         case 11:
            return 2;
         case 12:
            return 2;
         case 13:
            return 2;
         case 14:
            return 0;
         case 15:
            return 1;
         case 16:
            return 1;
         case 17:
            return 1;
         case 18:
            return 1;
         default:
            return super.methodArity(var1);
      }
   }
}
