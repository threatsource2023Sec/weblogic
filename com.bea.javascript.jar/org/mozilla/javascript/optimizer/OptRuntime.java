package org.mozilla.javascript.optimizer;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeCall;
import org.mozilla.javascript.NativeGlobal;
import org.mozilla.javascript.NativeWith;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;

public final class OptRuntime extends ScriptRuntime {
   private OptRuntime() {
   }

   public static Object add(double var0, Object var2) {
      if (var2 instanceof Scriptable) {
         var2 = ((Scriptable)var2).getDefaultValue((Class)null);
      }

      return !(var2 instanceof String) ? new Double(ScriptRuntime.toNumber(var2) + var0) : ScriptRuntime.numberToString(var0, 10) + ScriptRuntime.toString(var2);
   }

   public static Object add(Object var0, double var1) {
      if (var0 instanceof Scriptable) {
         var0 = ((Scriptable)var0).getDefaultValue((Class)null);
      }

      return !(var0 instanceof String) ? new Double(ScriptRuntime.toNumber(var0) + var1) : ScriptRuntime.toString(var0) + ScriptRuntime.numberToString(var1, 10);
   }

   public static Object callSimple(Context var0, String var1, Scriptable var2, Object[] var3) throws JavaScriptException {
      Scriptable var4 = var2;
      Object var5 = null;

      Scriptable var6;
      label46:
      for(var6 = null; var4 != null; var4 = var4.getParentScope()) {
         Scriptable var7 = var4;

         do {
            var5 = var7.get(var1, var4);
            if (var5 != Scriptable.NOT_FOUND) {
               var6 = var4;
               break label46;
            }

            var7 = var7.getPrototype();
         } while(var7 != null);
      }

      if (var5 != null && var5 != Scriptable.NOT_FOUND) {
         while(var6 instanceof NativeWith) {
            var6 = var6.getPrototype();
         }

         if (var6 instanceof NativeCall) {
            var6 = ScriptableObject.getTopLevelScope(var6);
         }

         Function var11;
         try {
            var11 = (Function)var5;
         } catch (ClassCastException var9) {
            Object[] var8 = new Object[]{ScriptRuntime.toString(var5)};
            throw Context.reportRuntimeError(ScriptRuntime.getMessage("msg.isnt.function", var8));
         }

         return var11.call(var0, var2, var6, var3);
      } else {
         Object[] var10 = new Object[]{var1};
         throw NativeGlobal.constructError(var0, "ReferenceError", ScriptRuntime.getMessage("msg.is.not.defined", var10), var2);
      }
   }

   public static int cmp(Object var0, Object var1) {
      if (var0 instanceof Scriptable) {
         var0 = ((Scriptable)var0).getDefaultValue(ScriptRuntime.NumberClass);
      }

      if (var1 instanceof Scriptable) {
         var1 = ((Scriptable)var1).getDefaultValue(ScriptRuntime.NumberClass);
      }

      if (var0 instanceof String && var1 instanceof String) {
         return ScriptRuntime.toString(var0).compareTo(ScriptRuntime.toString(var1)) < 0 ? 1 : 0;
      } else {
         double var2 = ScriptRuntime.toNumber(var0);
         if (var2 != var2) {
            return -1;
         } else {
            double var4 = ScriptRuntime.toNumber(var1);
            if (var4 != var4) {
               return -1;
            } else {
               return var2 < var4 ? 1 : 0;
            }
         }
      }
   }

   public static int cmp_LE(double var0, Object var2) {
      if (var2 instanceof Scriptable) {
         var2 = ((Scriptable)var2).getDefaultValue(ScriptRuntime.NumberClass);
      }

      if (!(var2 instanceof String)) {
         if (var0 != var0) {
            return 0;
         } else {
            double var3 = ScriptRuntime.toNumber(var2);
            if (var3 != var3) {
               return 0;
            } else {
               return var0 <= var3 ? 1 : 0;
            }
         }
      } else {
         return ScriptRuntime.toString(new Double(var0)).compareTo(ScriptRuntime.toString(var2)) <= 0 ? 1 : 0;
      }
   }

   public static int cmp_LE(Object var0, double var1) {
      if (var0 instanceof Scriptable) {
         var0 = ((Scriptable)var0).getDefaultValue(ScriptRuntime.NumberClass);
      }

      if (!(var0 instanceof String)) {
         double var3 = ScriptRuntime.toNumber(var0);
         if (var3 != var3) {
            return 0;
         } else if (var1 != var1) {
            return 0;
         } else {
            return var3 <= var1 ? 1 : 0;
         }
      } else {
         return ScriptRuntime.toString(var0).compareTo(ScriptRuntime.toString(new Double(var1))) <= 0 ? 1 : 0;
      }
   }

   public static Boolean cmp_LEB(double var0, Object var2) {
      return cmp_LE(var0, var2) == 1 ? Boolean.TRUE : Boolean.FALSE;
   }

   public static Boolean cmp_LEB(Object var0, double var1) {
      return cmp_LE(var0, var1) == 1 ? Boolean.TRUE : Boolean.FALSE;
   }

   public static int cmp_LT(double var0, Object var2) {
      if (var2 instanceof Scriptable) {
         var2 = ((Scriptable)var2).getDefaultValue(ScriptRuntime.NumberClass);
      }

      if (!(var2 instanceof String)) {
         if (var0 != var0) {
            return 0;
         } else {
            double var3 = ScriptRuntime.toNumber(var2);
            if (var3 != var3) {
               return 0;
            } else {
               return var0 < var3 ? 1 : 0;
            }
         }
      } else {
         return ScriptRuntime.toString(new Double(var0)).compareTo(ScriptRuntime.toString(var2)) < 0 ? 1 : 0;
      }
   }

   public static int cmp_LT(Object var0, double var1) {
      if (var0 instanceof Scriptable) {
         var0 = ((Scriptable)var0).getDefaultValue(ScriptRuntime.NumberClass);
      }

      if (!(var0 instanceof String)) {
         double var3 = ScriptRuntime.toNumber(var0);
         if (var3 != var3) {
            return 0;
         } else if (var1 != var1) {
            return 0;
         } else {
            return var3 < var1 ? 1 : 0;
         }
      } else {
         return ScriptRuntime.toString(var0).compareTo(ScriptRuntime.toString(new Double(var1))) < 0 ? 1 : 0;
      }
   }

   public static Boolean cmp_LTB(double var0, Object var2) {
      return cmp_LT(var0, var2) == 1 ? Boolean.TRUE : Boolean.FALSE;
   }

   public static Boolean cmp_LTB(Object var0, double var1) {
      return cmp_LT(var0, var1) == 1 ? Boolean.TRUE : Boolean.FALSE;
   }

   public static Object getElem(Object var0, double var1, Scriptable var3) {
      int var4 = (int)var1;
      String var5 = (double)var4 == var1 ? null : ScriptRuntime.toString(new Double(var1));
      Scriptable var6 = var0 instanceof Scriptable ? (Scriptable)var0 : ScriptRuntime.toObject(var3, var0);
      Scriptable var7 = var6;
      Object var8;
      if (var5 != null) {
         while(var7 != null) {
            var8 = var7.get(var5, var6);
            if (var8 != Scriptable.NOT_FOUND) {
               return var8;
            }

            var7 = var7.getPrototype();
         }

         return Undefined.instance;
      } else {
         while(var7 != null) {
            var8 = var7.get(var4, var6);
            if (var8 != Scriptable.NOT_FOUND) {
               return var8;
            }

            var7 = var7.getPrototype();
         }

         return Undefined.instance;
      }
   }

   public static boolean neq(Object var0, Object var1) {
      return ScriptRuntime.eq(var0, var1) ^ true;
   }

   public static Object[] padStart(Object[] var0, int var1) {
      Object[] var2 = new Object[var0.length + var1];
      System.arraycopy(var0, 0, var2, var1, var0.length);
      return var2;
   }

   public static Object setElem(Object var0, double var1, Object var3, Scriptable var4) {
      int var5 = (int)var1;
      String var6 = (double)var5 == var1 ? null : ScriptRuntime.toString(new Double(var1));
      Scriptable var7 = var0 instanceof Scriptable ? (Scriptable)var0 : ScriptRuntime.toObject(var4, var0);
      Scriptable var8 = var7;
      if (var6 != null) {
         while(!var8.has(var6, var7)) {
            var8 = var8.getPrototype();
            if (var8 == null) {
               var7.put(var6, var7, var3);
               return var3;
            }
         }

         var8.put(var6, var7, var3);
         return var3;
      } else {
         while(!var8.has(var5, var7)) {
            var8 = var8.getPrototype();
            if (var8 == null) {
               var7.put(var5, var7, var3);
               return var3;
            }
         }

         var8.put(var5, var7, var3);
         return var3;
      }
   }

   public static boolean shallowNeq(Object var0, Object var1) {
      return ScriptRuntime.shallowEq(var0, var1) ^ true;
   }

   public static Object thisGet(Scriptable var0, String var1, Scriptable var2) {
      if (var0 == null) {
         throw Context.reportRuntimeError(ScriptRuntime.getMessage("msg.null.to.object", (Object[])null));
      } else {
         Object var3 = var0.get(var1, var0);
         if (var3 != Scriptable.NOT_FOUND) {
            return var3;
         } else {
            for(Scriptable var4 = var0.getPrototype(); var4 != null; var4 = var4.getPrototype()) {
               var3 = var4.get(var1, var0);
               if (var3 != Scriptable.NOT_FOUND) {
                  return var3;
               }
            }

            return Undefined.instance;
         }
      }
   }
}
