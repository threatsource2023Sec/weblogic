package org.mozilla.javascript;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import org.mozilla.classfile.DefiningClassLoader;

public class ScriptRuntime {
   public static final Class UndefinedClass;
   public static final Class ScriptableClass;
   public static final Class StringClass;
   public static final Class NumberClass;
   public static final Class BooleanClass;
   public static final Class ByteClass;
   public static final Class ShortClass;
   public static final Class IntegerClass;
   public static final Class LongClass;
   public static final Class FloatClass;
   public static final Class DoubleClass;
   public static final Class CharacterClass;
   public static final Class ObjectClass;
   public static final Class FunctionClass;
   public static final Class ClassClass;
   public static double NaN;
   public static Double NaNobj;
   public static double negativeZero;
   private static final boolean MSJVM_BUG_WORKAROUNDS = true;
   private static final String GLOBAL_CLASS = "org.mozilla.javascript.tools.shell.Global";
   public static final Object[] emptyArgs;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$Undefined;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$Scriptable;
   // $FF: synthetic field
   static Class class$java$lang$String;
   // $FF: synthetic field
   static Class class$java$lang$Number;
   // $FF: synthetic field
   static Class class$java$lang$Boolean;
   // $FF: synthetic field
   static Class class$java$lang$Byte;
   // $FF: synthetic field
   static Class class$java$lang$Short;
   // $FF: synthetic field
   static Class class$java$lang$Integer;
   // $FF: synthetic field
   static Class class$java$lang$Long;
   // $FF: synthetic field
   static Class class$java$lang$Float;
   // $FF: synthetic field
   static Class class$java$lang$Double;
   // $FF: synthetic field
   static Class class$java$lang$Character;
   // $FF: synthetic field
   static Class class$java$lang$Object;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$Function;
   // $FF: synthetic field
   static Class class$java$lang$Class;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$NativeGlobal;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$NativeWith;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$NativeScript;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$Context;

   static {
      UndefinedClass = class$org$mozilla$javascript$Undefined != null ? class$org$mozilla$javascript$Undefined : (class$org$mozilla$javascript$Undefined = class$("org.mozilla.javascript.Undefined"));
      ScriptableClass = class$org$mozilla$javascript$Scriptable != null ? class$org$mozilla$javascript$Scriptable : (class$org$mozilla$javascript$Scriptable = class$("org.mozilla.javascript.Scriptable"));
      StringClass = class$java$lang$String != null ? class$java$lang$String : (class$java$lang$String = class$("java.lang.String"));
      NumberClass = class$java$lang$Number != null ? class$java$lang$Number : (class$java$lang$Number = class$("java.lang.Number"));
      BooleanClass = class$java$lang$Boolean != null ? class$java$lang$Boolean : (class$java$lang$Boolean = class$("java.lang.Boolean"));
      ByteClass = class$java$lang$Byte != null ? class$java$lang$Byte : (class$java$lang$Byte = class$("java.lang.Byte"));
      ShortClass = class$java$lang$Short != null ? class$java$lang$Short : (class$java$lang$Short = class$("java.lang.Short"));
      IntegerClass = class$java$lang$Integer != null ? class$java$lang$Integer : (class$java$lang$Integer = class$("java.lang.Integer"));
      LongClass = class$java$lang$Long != null ? class$java$lang$Long : (class$java$lang$Long = class$("java.lang.Long"));
      FloatClass = class$java$lang$Float != null ? class$java$lang$Float : (class$java$lang$Float = class$("java.lang.Float"));
      DoubleClass = class$java$lang$Double != null ? class$java$lang$Double : (class$java$lang$Double = class$("java.lang.Double"));
      CharacterClass = class$java$lang$Character != null ? class$java$lang$Character : (class$java$lang$Character = class$("java.lang.Character"));
      ObjectClass = class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object"));
      FunctionClass = class$org$mozilla$javascript$Function != null ? class$org$mozilla$javascript$Function : (class$org$mozilla$javascript$Function = class$("org.mozilla.javascript.Function"));
      ClassClass = class$java$lang$Class != null ? class$java$lang$Class : (class$java$lang$Class = class$("java.lang.Class"));
      NaN = Double.NaN;
      NaNobj = new Double(Double.NaN);
      negativeZero = -0.0;
      emptyArgs = new Object[0];
   }

   protected ScriptRuntime() {
   }

   public static Object add(Object var0, Object var1) {
      if (var0 instanceof Scriptable) {
         var0 = ((Scriptable)var0).getDefaultValue((Class)null);
      }

      if (var1 instanceof Scriptable) {
         var1 = ((Scriptable)var1).getDefaultValue((Class)null);
      }

      if (!(var0 instanceof String) && !(var1 instanceof String)) {
         return var0 instanceof Number && var1 instanceof Number ? new Double(((Number)var0).doubleValue() + ((Number)var1).doubleValue()) : new Double(toNumber(var0) + toNumber(var1));
      } else {
         return toString(var0) + toString(var1);
      }
   }

   public static Scriptable bind(Scriptable var0, String var1) {
      for(Scriptable var2 = var0; var2 != null; var2 = var2.getParentScope()) {
         Scriptable var4 = var2;

         do {
            if (var4.has(var1, var2)) {
               return var2;
            }

            var4 = var4.getPrototype();
         } while(var4 != null);
      }

      return null;
   }

   public static Object call(Context var0, Object var1, Object var2, Object[] var3) throws JavaScriptException {
      Scriptable var4 = null;
      if (var1 instanceof Scriptable) {
         var4 = ((Scriptable)var1).getParentScope();
      }

      return call(var0, var1, var2, var3, var4);
   }

   public static Object call(Context var0, Object var1, Object var2, Object[] var3, Scriptable var4) throws JavaScriptException {
      Function var5;
      try {
         var5 = (Function)var1;
      } catch (ClassCastException var7) {
         throw NativeGlobal.typeError1("msg.isnt.function", toString(var1), var4);
      }

      Scriptable var6;
      if (!(var2 instanceof Scriptable) && var2 != null) {
         var6 = toObject(var4, var2);
      } else {
         var6 = (Scriptable)var2;
      }

      return var5.call(var0, var4, var6, var3);
   }

   private static Object callOrNewSpecial(Context var0, Scriptable var1, Object var2, Object var3, Object var4, Object[] var5, boolean var6, String var7, int var8) throws JavaScriptException {
      if (var2 instanceof IdFunction) {
         IdFunction var9 = (IdFunction)var2;
         String var10 = var9.getFunctionName();
         if (var10.length() == 4) {
            if (var10.equals("eval")) {
               if (var9.master.getClass() == (class$org$mozilla$javascript$NativeGlobal != null ? class$org$mozilla$javascript$NativeGlobal : (class$org$mozilla$javascript$NativeGlobal = class$("org.mozilla.javascript.NativeGlobal")))) {
                  return NativeGlobal.evalSpecial(var0, var1, var4, var5, var7, var8);
               }
            } else if (var10.equals("With")) {
               if (var9.master.getClass() == (class$org$mozilla$javascript$NativeWith != null ? class$org$mozilla$javascript$NativeWith : (class$org$mozilla$javascript$NativeWith = class$("org.mozilla.javascript.NativeWith")))) {
                  return NativeWith.newWithSpecial(var0, var5, var9, var6 ^ true);
               }
            } else if (var10.equals("exec")) {
               if (var9.master.getClass() == (class$org$mozilla$javascript$NativeScript != null ? class$org$mozilla$javascript$NativeScript : (class$org$mozilla$javascript$NativeScript = class$("org.mozilla.javascript.NativeScript")))) {
                  return ((NativeScript)var3).exec(var0, ScriptableObject.getTopLevelScope(var1));
               }

               RegExpProxy var11 = var0.getRegExpProxy();
               if (var11 != null && var11.isRegExp(var3)) {
                  return call(var0, var2, var3, var5, var1);
               }
            }
         }
      } else if (var2 instanceof NativeJavaMethod) {
         return call(var0, var2, var3, var5, var1);
      }

      return var6 ? call(var0, var2, var4, var5, var1) : newObject(var0, var2, var5, var1);
   }

   public static Object callSpecial(Context var0, Object var1, Object var2, Object[] var3, Scriptable var4, Scriptable var5, String var6, int var7) throws JavaScriptException {
      return callOrNewSpecial(var0, var5, var1, var2, var4, var3, true, var6, var7);
   }

   static void checkDeprecated(Context var0, String var1) {
      int var2 = var0.getLanguageVersion();
      if (var2 >= 140 || var2 == 0) {
         String var3 = getMessage1("msg.deprec.ctor", var1);
         if (var2 != 0) {
            throw Context.reportRuntimeError(var3);
         }

         Context.reportWarning(var3);
      }

   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public static int cmp_LE(Object var0, Object var1) {
      if (var0 instanceof Scriptable) {
         var0 = ((Scriptable)var0).getDefaultValue(NumberClass);
      }

      if (var1 instanceof Scriptable) {
         var1 = ((Scriptable)var1).getDefaultValue(NumberClass);
      }

      if (var0 instanceof String && var1 instanceof String) {
         return toString(var0).compareTo(toString(var1)) <= 0 ? 1 : 0;
      } else {
         double var2 = toNumber(var0);
         if (var2 != var2) {
            return 0;
         } else {
            double var4 = toNumber(var1);
            if (var4 != var4) {
               return 0;
            } else {
               return var2 <= var4 ? 1 : 0;
            }
         }
      }
   }

   public static Boolean cmp_LEB(Object var0, Object var1) {
      return cmp_LE(var0, var1) == 1 ? Boolean.TRUE : Boolean.FALSE;
   }

   public static int cmp_LT(Object var0, Object var1) {
      if (var0 instanceof Scriptable) {
         var0 = ((Scriptable)var0).getDefaultValue(NumberClass);
      }

      if (var1 instanceof Scriptable) {
         var1 = ((Scriptable)var1).getDefaultValue(NumberClass);
      }

      if (var0 instanceof String && var1 instanceof String) {
         return toString(var0).compareTo(toString(var1)) < 0 ? 1 : 0;
      } else {
         double var2 = toNumber(var0);
         if (var2 != var2) {
            return 0;
         } else {
            double var4 = toNumber(var1);
            if (var4 != var4) {
               return 0;
            } else {
               return var2 < var4 ? 1 : 0;
            }
         }
      }
   }

   public static Boolean cmp_LTB(Object var0, Object var1) {
      return cmp_LT(var0, var1) == 1 ? Boolean.TRUE : Boolean.FALSE;
   }

   public static NativeFunction createFunctionObject(Scriptable var0, Class var1, Context var2, boolean var3) {
      Constructor[] var4 = var1.getConstructors();
      NativeFunction var5 = null;
      Object[] var6 = new Object[]{var0, var2};

      try {
         var5 = (NativeFunction)var4[0].newInstance(var6);
      } catch (InstantiationException var8) {
         throw WrappedException.wrapException(var8);
      } catch (IllegalAccessException var9) {
         throw WrappedException.wrapException(var9);
      } catch (IllegalArgumentException var10) {
         throw WrappedException.wrapException(var10);
      } catch (InvocationTargetException var11) {
         throw WrappedException.wrapException(var11);
      }

      var5.setPrototype(ScriptableObject.getClassPrototype(var0, "Function"));
      var5.setParentScope(var0);
      String var7 = var5.getFunctionName();
      if (var3 && var7 != null && var7.length() != 0 && !var7.equals("anonymous")) {
         setProp(var0, var7, var5, var0);
      }

      return var5;
   }

   public static Object delete(Object var0, Object var1) {
      String var2 = getStringId(var1);
      boolean var3 = var2 != null ? ScriptableObject.deleteProperty((Scriptable)var0, var2) : ScriptableObject.deleteProperty((Scriptable)var0, getIntId(var1));
      return var3 ? Boolean.TRUE : Boolean.FALSE;
   }

   public static Scriptable enterWith(Object var0, Scriptable var1) {
      return new NativeWith(var1, toObject(var1, var0));
   }

   public static boolean eq(Object var0, Object var1) {
      Object var2 = var0;
      Object var3 = var1;

      while(true) {
         while(true) {
            while(true) {
               while(true) {
                  Class var4 = getTypeOfValue(var0);
                  Class var5 = getTypeOfValue(var1);
                  if (var4 == var5) {
                     if (var4 == UndefinedClass) {
                        return true;
                     }

                     if (var4 == NumberClass) {
                        return ((Number)var0).doubleValue() == ((Number)var1).doubleValue();
                     }

                     if (var4 != StringClass && var4 != BooleanClass) {
                        if (var4 == ScriptableClass) {
                           if (var0 == var1) {
                              return true;
                           }

                           if (var0 instanceof Wrapper && var1 instanceof Wrapper) {
                              return ((Wrapper)var0).unwrap() == ((Wrapper)var1).unwrap();
                           }

                           return false;
                        }

                        throw new RuntimeException();
                     }

                     return var2.equals(var3);
                  }

                  if (var0 == null && var1 == Undefined.instance) {
                     return true;
                  }

                  if (var0 == Undefined.instance && var1 == null) {
                     return true;
                  }

                  if (var4 == NumberClass && var5 == StringClass) {
                     return ((Number)var0).doubleValue() == toNumber(var1);
                  }

                  if (var4 == StringClass && var5 == NumberClass) {
                     return toNumber(var0) == ((Number)var1).doubleValue();
                  }

                  if (var4 != BooleanClass) {
                     if (var5 != BooleanClass) {
                        if (var4 != StringClass && var4 != NumberClass || var5 != ScriptableClass || var1 == null) {
                           if (var4 != ScriptableClass || var0 == null || var5 != StringClass && var5 != NumberClass) {
                              return false;
                           }

                           var0 = toPrimitive(var0);
                           var2 = var0;
                        } else {
                           var1 = toPrimitive(var1);
                           var3 = var1;
                        }
                     } else {
                        var1 = new Double(toNumber(var1));
                        var3 = var1;
                     }
                  } else {
                     var0 = new Double(toNumber(var0));
                     var2 = var0;
                  }
               }
            }
         }
      }
   }

   public static Boolean eqB(Object var0, Object var1) {
      return eq(var0, var1) ? Boolean.TRUE : Boolean.FALSE;
   }

   private static RuntimeException errorWithClassName(String var0, Object var1) {
      return Context.reportRuntimeError1(var0, var1.getClass().getName());
   }

   public static String escapeString(String var0) {
      String var1 = "\bb\ff\nn\rr\tt\u000bv\"\"''";
      StringBuffer var2 = new StringBuffer(var0.length());

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         char var4 = var0.charAt(var3);
         if (var4 >= ' ' && var4 <= '~' && var4 != '"') {
            var2.append(var4);
         } else {
            int var5;
            if ((var5 = var1.indexOf(var4)) >= 0) {
               var2.append("\\");
               var2.append(var1.charAt(var5 + 1));
            } else {
               String var6;
               if (var4 < 256) {
                  var6 = Integer.toHexString(var4);
                  if (var6.length() == 1) {
                     var2.append("\\x0");
                     var2.append(var6);
                  } else {
                     var2.append("\\x");
                     var2.append(var6);
                  }
               } else {
                  var6 = Integer.toHexString(var4);
                  var2.append("\\u");

                  for(int var7 = var6.length(); var7 < 4; ++var7) {
                     var2.append('0');
                  }

                  var2.append(var6);
               }
            }
         }
      }

      return var2.toString();
   }

   public static Scriptable getBase(Scriptable var0, String var1) {
      for(Scriptable var2 = var0; var2 != null; var2 = var2.getParentScope()) {
         Scriptable var4 = var2;

         do {
            if (var4.get(var1, var2) != Scriptable.NOT_FOUND) {
               return var2;
            }

            var4 = var4.getPrototype();
         } while(var4 != null);
      }

      throw NativeGlobal.constructError(Context.getContext(), "ReferenceError", getMessage1("msg.is.not.defined", var1), var0);
   }

   public static NativeCall getCurrentActivation(Context var0) {
      return var0.currentActivation;
   }

   public static Object getElem(Object var0, Object var1, Scriptable var2) {
      int var3;
      String var4;
      if (var1 instanceof Number) {
         double var5 = ((Number)var1).doubleValue();
         var3 = (int)var5;
         var4 = (double)var3 == var5 ? null : toString(var1);
      } else {
         var4 = toString(var1);
         long var8 = indexFromString(var4);
         if (var8 >= 0L) {
            var3 = (int)var8;
            var4 = null;
         } else {
            var3 = 0;
         }
      }

      Scriptable var9 = var0 instanceof Scriptable ? (Scriptable)var0 : toObject(var2, var0);
      Scriptable var6 = var9;
      Object var7;
      if (var4 != null) {
         if (var4.equals("__proto__")) {
            return var9.getPrototype();
         } else if (var4.equals("__parent__")) {
            return var9.getParentScope();
         } else {
            while(var6 != null) {
               var7 = var6.get(var4, var9);
               if (var7 != Scriptable.NOT_FOUND) {
                  return var7;
               }

               var6 = var6.getPrototype();
            }

            return Undefined.instance;
         }
      } else {
         while(var6 != null) {
            var7 = var6.get(var3, var9);
            if (var7 != Scriptable.NOT_FOUND) {
               return var7;
            }

            var6 = var6.getPrototype();
         }

         return Undefined.instance;
      }
   }

   public static Object getElem(Scriptable var0, int var1) {
      for(Scriptable var2 = var0; var2 != null; var2 = var2.getPrototype()) {
         Object var3 = var2.get(var1, var0);
         if (var3 != Scriptable.NOT_FOUND) {
            return var3;
         }
      }

      return Undefined.instance;
   }

   private static ScriptableObject getGlobal(Context var0) {
      try {
         Class var1 = loadClassName("org.mozilla.javascript.tools.shell.Global");
         Class[] var2 = new Class[]{class$org$mozilla$javascript$Context != null ? class$org$mozilla$javascript$Context : (class$org$mozilla$javascript$Context = class$("org.mozilla.javascript.Context"))};
         Constructor var3 = var1.getConstructor(var2);
         Object[] var4 = new Object[]{var0};
         return (ScriptableObject)var3.newInstance(var4);
      } catch (ClassNotFoundException var5) {
      } catch (NoSuchMethodException var6) {
      } catch (InvocationTargetException var7) {
      } catch (IllegalAccessException var8) {
      } catch (InstantiationException var9) {
      }

      return new ImporterTopLevel(var0);
   }

   static int getIntId(Object var0) {
      if (var0 instanceof Number) {
         double var4 = ((Number)var0).doubleValue();
         int var3 = (int)var4;
         return (double)var3 == var4 ? var3 : 0;
      } else {
         String var1 = toString(var0);
         long var2 = indexFromString(var1);
         return var2 >= 0L ? (int)var2 : 0;
      }
   }

   public static String getMessage(String var0, Object[] var1) {
      return Context.getMessage(var0, var1);
   }

   public static String getMessage0(String var0) {
      return Context.getMessage0(var0);
   }

   public static String getMessage1(String var0, Object var1) {
      return Context.getMessage1(var0, var1);
   }

   public static String getMessage2(String var0, Object var1, Object var2) {
      return Context.getMessage2(var0, var1, var2);
   }

   public static Scriptable getParent(Object var0) {
      Scriptable var1;
      try {
         var1 = (Scriptable)var0;
      } catch (ClassCastException var2) {
         return null;
      }

      return var1 == null ? null : getThis(var1.getParentScope());
   }

   public static Scriptable getParent(Object var0, Scriptable var1) {
      Scriptable var2;
      if (var0 instanceof Scriptable) {
         var2 = (Scriptable)var0;
      } else {
         var2 = toObject(var1, var0);
      }

      if (var2 == null) {
         throw NativeGlobal.typeError0("msg.null.to.object", var1);
      } else {
         return var2.getParentScope();
      }
   }

   public static Object getProp(Object var0, String var1, Scriptable var2) {
      Scriptable var3;
      if (var0 instanceof Scriptable) {
         var3 = (Scriptable)var0;
      } else {
         var3 = toObject(var2, var0);
      }

      if (var3 != null && var3 != Undefined.instance) {
         Scriptable var6 = var3;

         do {
            Object var5 = var6.get(var1, var3);
            if (var5 != Scriptable.NOT_FOUND) {
               return var5;
            }

            var6 = var6.getPrototype();
         } while(var6 != null);

         return Undefined.instance;
      } else {
         String var4 = var3 == null ? "msg.null.to.object" : "msg.undefined";
         throw NativeGlobal.constructError(Context.getContext(), "ConversionError", getMessage0(var4), var2);
      }
   }

   public static Scriptable getProto(Object var0, Scriptable var1) {
      Scriptable var2;
      if (var0 instanceof Scriptable) {
         var2 = (Scriptable)var0;
      } else {
         var2 = toObject(var1, var0);
      }

      if (var2 == null) {
         throw NativeGlobal.typeError0("msg.null.to.object", var1);
      } else {
         return var2.getPrototype();
      }
   }

   public static RegExpProxy getRegExpProxy(Context var0) {
      return var0.getRegExpProxy();
   }

   static String getStringId(Object var0) {
      if (var0 instanceof Number) {
         double var4 = ((Number)var0).doubleValue();
         int var3 = (int)var4;
         return (double)var3 == var4 ? null : toString(var0);
      } else {
         String var1 = toString(var0);
         long var2 = indexFromString(var1);
         return var2 >= 0L ? null : var1;
      }
   }

   public static Scriptable getThis(Scriptable var0) {
      while(var0 instanceof NativeWith) {
         var0 = var0.getPrototype();
      }

      if (var0 instanceof NativeCall) {
         var0 = ScriptableObject.getTopLevelScope(var0);
      }

      return var0;
   }

   public static Object getTopLevelProp(Scriptable var0, String var1) {
      Scriptable var2 = ScriptableObject.getTopLevelScope(var0);

      Object var3;
      do {
         var3 = var2.get(var1, var2);
         if (var3 != Scriptable.NOT_FOUND) {
            return var3;
         }

         var2 = var2.getPrototype();
      } while(var2 != null);

      return var3;
   }

   private static Class getTypeOfValue(Object var0) {
      if (var0 == null) {
         return ScriptableClass;
      } else if (var0 == Undefined.instance) {
         return UndefinedClass;
      } else if (var0 instanceof Scriptable) {
         return ScriptableClass;
      } else {
         return var0 instanceof Number ? NumberClass : var0.getClass();
      }
   }

   static boolean hasProp(Scriptable var0, String var1) {
      Scriptable var2 = var0;

      while(!var2.has(var1, var0)) {
         var2 = var2.getPrototype();
         if (var2 == null) {
            return false;
         }
      }

      return true;
   }

   public static boolean in(Object var0, Object var1, Scriptable var2) {
      if (!(var1 instanceof Scriptable)) {
         throw NativeGlobal.typeError0("msg.instanceof.not.object", var2);
      } else {
         String var3 = getStringId(var0);
         return var3 != null ? ScriptableObject.hasProperty((Scriptable)var1, var3) : ScriptableObject.hasProperty((Scriptable)var1, getIntId(var0));
      }
   }

   private static long indexFromString(String var0) {
      boolean var1 = true;
      int var2 = var0.length();
      if (var2 > 0) {
         int var3 = 0;
         boolean var4 = false;
         int var5 = var0.charAt(0);
         if (var5 == 45 && var2 > 1) {
            var5 = var0.charAt(1);
            var3 = 1;
            var4 = true;
         }

         var5 -= 48;
         if (var5 >= 0 && var5 <= 9 && var2 <= (var4 ? 11 : 10)) {
            int var6 = -var5;
            int var7 = 0;
            ++var3;
            if (var6 != 0) {
               while(var3 != var2 && (var5 = var0.charAt(var3) - 48) >= 0 && var5 <= 9) {
                  var7 = var6;
                  var6 = 10 * var6 - var5;
                  ++var3;
               }
            }

            if (var3 == var2 && (var7 > -214748364 || var7 == -214748364 && var5 <= (var4 ? 8 : 7))) {
               return 4294967295L & (long)(var4 ? var6 : -var6);
            }
         }
      }

      return -1L;
   }

   public static Enumeration initEnum(Object var0, Scriptable var1) {
      Scriptable var2 = toObject(var1, var0);
      return new IdEnumeration(var2);
   }

   public static NativeFunction initFunction(NativeFunction var0, Scriptable var1, String var2, Context var3, boolean var4) {
      var0.setPrototype(ScriptableObject.getClassPrototype(var1, "Function"));
      var0.setParentScope(var1);
      if (var4) {
         setName(var1, var0, var1, var2);
      }

      return var0;
   }

   public static Scriptable initScript(Context var0, Scriptable var1, NativeFunction var2, Scriptable var3, boolean var4) {
      String[] var5 = var2.argNames;
      if (var5 != null) {
         ScriptableObject var6;
         try {
            var6 = (ScriptableObject)var1;
         } catch (ClassCastException var10) {
            var6 = null;
         }

         Scriptable var7 = var1;
         if (var4) {
            for(var7 = var1; var7 instanceof NativeWith; var7 = var7.getParentScope()) {
            }
         }

         int var8 = var5.length;

         while(true) {
            while(true) {
               String var9;
               do {
                  if (var8-- == 0) {
                     return var1;
                  }

                  var9 = var5[var8];
               } while(hasProp(var1, var9));

               if (var6 != null && !var4) {
                  var6.defineProperty(var9, (Object)Undefined.instance, 4);
               } else {
                  var7.put(var9, var7, Undefined.instance);
               }
            }
         }
      } else {
         return var1;
      }
   }

   public static Scriptable initVarObj(Context var0, Scriptable var1, NativeFunction var2, Scriptable var3, Object[] var4) {
      NativeCall var5 = new NativeCall(var0, var1, var2, var3, var4);
      String[] var6 = var2.argNames;
      if (var6 != null) {
         for(int var7 = var2.argCount; var7 != var6.length; ++var7) {
            String var8 = var6[var7];
            var5.put(var8, var5, Undefined.instance);
         }
      }

      return var5;
   }

   public static boolean instanceOf(Scriptable var0, Object var1, Object var2) {
      if (!(var2 instanceof Scriptable)) {
         throw NativeGlobal.typeError0("msg.instanceof.not.object", var0);
      } else {
         return !(var1 instanceof Scriptable) ? false : ((Scriptable)var2).hasInstance((Scriptable)var1);
      }
   }

   protected static boolean jsDelegatesTo(Scriptable var0, Scriptable var1) {
      for(Scriptable var2 = var0.getPrototype(); var2 != null; var2 = var2.getPrototype()) {
         if (var2.equals(var1)) {
            return true;
         }
      }

      return false;
   }

   public static Scriptable leaveWith(Scriptable var0) {
      return var0.getParentScope();
   }

   public static Class loadClassName(String var0) throws ClassNotFoundException {
      try {
         ClassLoader var1 = DefiningClassLoader.getContextClassLoader();
         if (var1 != null) {
            return var1.loadClass(var0);
         }
      } catch (SecurityException var2) {
      } catch (ClassNotFoundException var3) {
      }

      return Class.forName(var0);
   }

   public static void main(String var0, String[] var1) throws JavaScriptException {
      Context var2 = Context.enter();
      ScriptableObject var3 = getGlobal(var2);
      Scriptable var4 = var2.newArray(var3, var1);
      var3.defineProperty("arguments", (Object)var4, 2);

      try {
         Class var7 = loadClassName(var0);
         Script var8 = (Script)var7.newInstance();
         var8.exec(var2, var3);
         return;
      } catch (ClassNotFoundException var13) {
      } catch (InstantiationException var14) {
      } catch (IllegalAccessException var15) {
      } finally {
         Context.exit();
      }

      throw new RuntimeException("Error creating script object");
   }

   public static Object name(Scriptable var0, String var1) {
      for(Scriptable var2 = var0; var2 != null; var2 = var2.getParentScope()) {
         Scriptable var4 = var2;

         do {
            Object var5 = var4.get(var1, var2);
            if (var5 != Scriptable.NOT_FOUND) {
               return var5;
            }

            var4 = var4.getPrototype();
         } while(var4 != null);
      }

      throw NativeGlobal.constructError(Context.getContext(), "ReferenceError", getMessage1("msg.is.not.defined", var1.toString()), var0);
   }

   public static Boolean neB(Object var0, Object var1) {
      return eq(var0, var1) ? Boolean.FALSE : Boolean.TRUE;
   }

   public static Scriptable newObject(Context var0, Object var1, Object[] var2, Scriptable var3) throws JavaScriptException {
      try {
         Function var4 = (Function)var1;
         if (var4 != null) {
            return var4.construct(var0, var3, var2);
         }
      } catch (ClassCastException var5) {
      }

      throw NativeGlobal.typeError1("msg.isnt.function", toString(var1), var3);
   }

   public static Scriptable newObject(Context var0, Scriptable var1, String var2, Object[] var3) {
      Object var4 = null;

      try {
         return var0.newObject(var1, var2, var3);
      } catch (NotAFunctionException var6) {
         var4 = var6;
      } catch (PropertyException var7) {
         var4 = var7;
      } catch (JavaScriptException var8) {
         var4 = var8;
      }

      throw Context.reportRuntimeError(((Throwable)var4).getMessage());
   }

   public static Scriptable newObjectSpecial(Context var0, Object var1, Object[] var2, Scriptable var3) throws JavaScriptException {
      return (Scriptable)callOrNewSpecial(var0, var3, var1, (Object)null, (Object)null, var2, false, (String)null, -1);
   }

   public static Scriptable newScope() {
      return new NativeObject();
   }

   public static Object nextEnum(Enumeration var0) {
      return !var0.hasMoreElements() ? null : var0.nextElement();
   }

   public static String numberToString(double var0, int var2) {
      if (var0 != var0) {
         return "NaN";
      } else if (var0 == Double.POSITIVE_INFINITY) {
         return "Infinity";
      } else if (var0 == Double.NEGATIVE_INFINITY) {
         return "-Infinity";
      } else if (var0 == 0.0) {
         return "0";
      } else if (var2 >= 2 && var2 <= 36) {
         if (var2 != 10) {
            return DToA.JS_dtobasestr(var2, var0);
         } else {
            StringBuffer var3 = new StringBuffer();
            DToA.JS_dtostr(var3, 0, 0, var0);
            return var3.toString();
         }
      } else {
         throw Context.reportRuntimeError1("msg.bad.radix", Integer.toString(var2));
      }
   }

   public static Object[] padArguments(Object[] var0, int var1) {
      if (var1 < var0.length) {
         return var0;
      } else {
         Object[] var3 = new Object[var1];

         int var2;
         for(var2 = 0; var2 < var0.length; ++var2) {
            var3[var2] = var0[var2];
         }

         while(var2 < var1) {
            var3[var2] = Undefined.instance;
            ++var2;
         }

         return var3;
      }
   }

   public static void popActivation(Context var0) {
      NativeCall var1 = var0.currentActivation;
      if (var1 != null) {
         var0.currentActivation = var1.caller;
         var1.caller = null;
      }

   }

   public static Object postDecrement(Object var0) {
      Double var1;
      if (var0 instanceof Number) {
         var1 = new Double(((Number)var0).doubleValue() - 1.0);
      } else {
         var1 = new Double(toNumber(var0) - 1.0);
      }

      return var1;
   }

   public static Object postDecrement(Object var0, String var1, Scriptable var2) {
      Scriptable var3;
      if (var0 instanceof Scriptable) {
         var3 = (Scriptable)var0;
      } else {
         var3 = toObject(var2, var0);
      }

      if (var3 == null) {
         throw NativeGlobal.typeError0("msg.null.to.object", var2);
      } else {
         Scriptable var4 = var3;

         do {
            Object var5 = var4.get(var1, var3);
            if (var5 != Scriptable.NOT_FOUND) {
               Double var6;
               if (var5 instanceof Number) {
                  var6 = new Double(((Number)var5).doubleValue() - 1.0);
                  var4.put(var1, var3, var6);
                  return var5;
               } else {
                  var6 = new Double(toNumber(var5) - 1.0);
                  var4.put(var1, var3, var6);
                  return new Double(toNumber(var5));
               }
            }

            var4 = var4.getPrototype();
         } while(var4 != null);

         return Undefined.instance;
      }
   }

   public static Object postDecrement(Scriptable var0, String var1) {
      for(Scriptable var2 = var0; var2 != null; var2 = var2.getParentScope()) {
         Scriptable var4 = var2;

         do {
            Object var5 = var4.get(var1, var2);
            if (var5 != Scriptable.NOT_FOUND) {
               Double var6;
               if (var5 instanceof Number) {
                  var6 = new Double(((Number)var5).doubleValue() - 1.0);
                  var4.put(var1, var2, var6);
                  return var5;
               }

               var6 = new Double(toNumber(var5) - 1.0);
               var4.put(var1, var2, var6);
               return new Double(toNumber(var5));
            }

            var4 = var4.getPrototype();
         } while(var4 != null);
      }

      throw NativeGlobal.constructError(Context.getContext(), "ReferenceError", getMessage1("msg.is.not.defined", var1), var0);
   }

   public static Object postDecrementElem(Object var0, Object var1, Scriptable var2) {
      Object var3 = getElem(var0, var1, var2);
      if (var3 == Undefined.instance) {
         return Undefined.instance;
      } else {
         double var4 = toNumber(var3);
         Double var6 = new Double(var4 - 1.0);
         setElem(var0, var1, var6, var2);
         return new Double(var4);
      }
   }

   public static Object postIncrement(Object var0) {
      Double var1;
      if (var0 instanceof Number) {
         var1 = new Double(((Number)var0).doubleValue() + 1.0);
      } else {
         var1 = new Double(toNumber(var0) + 1.0);
      }

      return var1;
   }

   public static Object postIncrement(Object var0, String var1, Scriptable var2) {
      Scriptable var3;
      if (var0 instanceof Scriptable) {
         var3 = (Scriptable)var0;
      } else {
         var3 = toObject(var2, var0);
      }

      if (var3 == null) {
         throw NativeGlobal.typeError0("msg.null.to.object", var2);
      } else {
         Scriptable var4 = var3;

         do {
            Object var5 = var4.get(var1, var3);
            if (var5 != Scriptable.NOT_FOUND) {
               Double var6;
               if (var5 instanceof Number) {
                  var6 = new Double(((Number)var5).doubleValue() + 1.0);
                  var4.put(var1, var3, var6);
                  return var5;
               } else {
                  var6 = new Double(toNumber(var5) + 1.0);
                  var4.put(var1, var3, var6);
                  return new Double(toNumber(var5));
               }
            }

            var4 = var4.getPrototype();
         } while(var4 != null);

         return Undefined.instance;
      }
   }

   public static Object postIncrement(Scriptable var0, String var1) {
      for(Scriptable var2 = var0; var2 != null; var2 = var2.getParentScope()) {
         Scriptable var4 = var2;

         do {
            Object var5 = var4.get(var1, var2);
            if (var5 != Scriptable.NOT_FOUND) {
               Double var6;
               if (var5 instanceof Number) {
                  var6 = new Double(((Number)var5).doubleValue() + 1.0);
                  var4.put(var1, var2, var6);
                  return var5;
               }

               var6 = new Double(toNumber(var5) + 1.0);
               var4.put(var1, var2, var6);
               return new Double(toNumber(var5));
            }

            var4 = var4.getPrototype();
         } while(var4 != null);
      }

      throw NativeGlobal.constructError(Context.getContext(), "ReferenceError", getMessage1("msg.is.not.defined", var1), var0);
   }

   public static Object postIncrementElem(Object var0, Object var1, Scriptable var2) {
      Object var3 = getElem(var0, var1, var2);
      if (var3 == Undefined.instance) {
         return Undefined.instance;
      } else {
         double var4 = toNumber(var3);
         Double var6 = new Double(var4 + 1.0);
         setElem(var0, var1, var6, var2);
         return new Double(var4);
      }
   }

   public static Scriptable runScript(Script var0) {
      Context var1 = Context.enter();
      ScriptableObject var2 = getGlobal(var1);

      try {
         var0.exec(var1, var2);
      } catch (JavaScriptException var8) {
         throw new Error(var8.toString());
      } finally {
         Context.exit();
      }

      return var2;
   }

   public static Boolean seqB(Object var0, Object var1) {
      return shallowEq(var0, var1) ? Boolean.TRUE : Boolean.FALSE;
   }

   public static void setCurrentActivation(Context var0, NativeCall var1) {
      var0.currentActivation = var1;
   }

   public static Object setElem(Object var0, Object var1, Object var2, Scriptable var3) {
      int var4;
      String var5;
      if (var1 instanceof Number) {
         double var6 = ((Number)var1).doubleValue();
         var4 = (int)var6;
         var5 = (double)var4 == var6 ? null : toString(var1);
      } else {
         var5 = toString(var1);
         long var8 = indexFromString(var5);
         if (var8 >= 0L) {
            var4 = (int)var8;
            var5 = null;
         } else {
            var4 = 0;
         }
      }

      Scriptable var9 = var0 instanceof Scriptable ? (Scriptable)var0 : toObject(var3, var0);
      Scriptable var7 = var9;
      if (var5 != null) {
         if (var5.equals("__proto__")) {
            return setProto(var0, var2, var3);
         } else if (var5.equals("__parent__")) {
            return setParent(var0, var2, var3);
         } else {
            while(!var7.has(var5, var9)) {
               var7 = var7.getPrototype();
               if (var7 == null) {
                  var9.put(var5, var9, var2);
                  return var2;
               }
            }

            var7.put(var5, var9, var2);
            return var2;
         }
      } else {
         while(!var7.has(var4, var9)) {
            var7 = var7.getPrototype();
            if (var7 == null) {
               var9.put(var4, var9, var2);
               return var2;
            }
         }

         var7.put(var4, var9, var2);
         return var2;
      }
   }

   public static Object setElem(Scriptable var0, int var1, Object var2) {
      Scriptable var3 = var0;

      while(!var3.has(var1, var0)) {
         var3 = var3.getPrototype();
         if (var3 == null) {
            var0.put(var1, var0, var2);
            return var2;
         }
      }

      var3.put(var1, var0, var2);
      return var2;
   }

   public static Object setName(Scriptable var0, Object var1, Scriptable var2, String var3) {
      if (var0 != null) {
         return setProp(var0, var3, var1, var2);
      } else {
         Scriptable var4 = var2;

         do {
            var0 = var4;
            var4 = var4.getParentScope();
         } while(var4 != null);

         var0.put(var3, var0, var1);
         return var1;
      }
   }

   public static Object setParent(Object var0, Object var1, Scriptable var2) {
      Scriptable var3;
      if (var0 instanceof Scriptable) {
         var3 = (Scriptable)var0;
      } else {
         var3 = toObject(var2, var0);
      }

      Scriptable var4 = var1 == null ? null : toObject(var2, var1);

      for(Scriptable var5 = var4; var5 != null; var5 = var5.getParentScope()) {
         if (var5 == var3) {
            throw Context.reportRuntimeError1("msg.cyclic.value", "__parent__");
         }
      }

      if (var3 == null) {
         throw NativeGlobal.typeError0("msg.null.to.object", var2);
      } else {
         var3.setParentScope(var4);
         return var4;
      }
   }

   public static Object setProp(Object var0, String var1, Object var2, Scriptable var3) {
      Scriptable var4;
      if (var0 instanceof Scriptable) {
         var4 = (Scriptable)var0;
      } else {
         var4 = toObject(var3, var0);
      }

      if (var4 == null) {
         throw NativeGlobal.typeError0("msg.null.to.object", var3);
      } else {
         Scriptable var5 = var4;

         while(!var5.has(var1, var4)) {
            var5 = var5.getPrototype();
            if (var5 == null) {
               var4.put(var1, var4, var2);
               return var2;
            }
         }

         var5.put(var1, var4, var2);
         return var2;
      }
   }

   public static Object setProto(Object var0, Object var1, Scriptable var2) {
      Scriptable var3;
      if (var0 instanceof Scriptable) {
         var3 = (Scriptable)var0;
      } else {
         var3 = toObject(var2, var0);
      }

      Scriptable var4 = var1 == null ? null : toObject(var2, var1);

      for(Scriptable var5 = var4; var5 != null; var5 = var5.getPrototype()) {
         if (var5 == var3) {
            throw Context.reportRuntimeError1("msg.cyclic.value", "__proto__");
         }
      }

      if (var3 == null) {
         throw NativeGlobal.typeError0("msg.null.to.object", var2);
      } else {
         var3.setPrototype(var4);
         return var4;
      }
   }

   public static boolean shallowEq(Object var0, Object var1) {
      Class var2 = getTypeOfValue(var0);
      if (var2 != getTypeOfValue(var1)) {
         return false;
      } else if (var2 != StringClass && var2 != BooleanClass) {
         if (var2 == NumberClass) {
            return ((Number)var0).doubleValue() == ((Number)var1).doubleValue();
         } else if (var2 == ScriptableClass) {
            if (var0 == var1) {
               return true;
            } else if (var0 instanceof Wrapper && var1 instanceof Wrapper) {
               return ((Wrapper)var0).unwrap() == ((Wrapper)var1).unwrap();
            } else {
               return false;
            }
         } else {
            return var2 == UndefinedClass;
         }
      } else {
         return var0.equals(var1);
      }
   }

   public static Boolean sneB(Object var0, Object var1) {
      return shallowEq(var0, var1) ? Boolean.FALSE : Boolean.TRUE;
   }

   static double stringToNumber(String var0, int var1, int var2) {
      char var3 = '9';
      char var4 = 'a';
      char var5 = 'A';
      int var6 = var0.length();
      if (var2 < 10) {
         var3 = (char)(48 + var2 - 1);
      }

      if (var2 > 10) {
         var4 = (char)(97 + var2 - 10);
         var5 = (char)(65 + var2 - 10);
      }

      double var8 = 0.0;

      int var7;
      int var11;
      for(var7 = var1; var7 < var6; ++var7) {
         char var10 = var0.charAt(var7);
         if (var10 >= '0' && var10 <= var3) {
            var11 = var10 - 48;
         } else if (var10 >= 'a' && var10 < var4) {
            var11 = var10 - 97 + 10;
         } else {
            if (var10 < 'A' || var10 >= var5) {
               break;
            }

            var11 = var10 - 65 + 10;
         }

         var8 = var8 * (double)var2 + (double)var11;
      }

      if (var1 == var7) {
         return NaN;
      } else {
         if (var8 >= 9.007199254740992E15) {
            if (var2 == 10) {
               try {
                  return Double.valueOf(var0.substring(var1, var7));
               } catch (NumberFormatException var18) {
                  return NaN;
               }
            }

            if (var2 == 2 || var2 == 4 || var2 == 8 || var2 == 16 || var2 == 32) {
               BinaryDigitReader var19 = new BinaryDigitReader(var2, var0, var1, var7);
               var8 = 0.0;

               do {
                  var11 = var19.getNextBinaryDigit();
               } while(var11 == 0);

               if (var11 == 1) {
                  var8 = 1.0;

                  for(int var12 = 52; var12 != 0; --var12) {
                     var11 = var19.getNextBinaryDigit();
                     if (var11 < 0) {
                        return var8;
                     }

                     var8 = var8 * 2.0 + (double)var11;
                  }

                  int var13 = var19.getNextBinaryDigit();
                  if (var13 >= 0) {
                     double var14 = 2.0;

                     int var16;
                     int var17;
                     for(var16 = 0; (var17 = var19.getNextBinaryDigit()) >= 0; var14 *= 2.0) {
                        var16 |= var17;
                     }

                     var8 += (double)(var13 & (var11 | var16));
                     var8 *= var14;
                  }
               }
            }
         }

         return var8;
      }
   }

   public static boolean toBoolean(Object var0) {
      if (var0 == null) {
         return false;
      } else {
         if (var0 instanceof Scriptable) {
            if (Context.getContext().isVersionECMA1()) {
               return var0 != Undefined.instance;
            }

            var0 = ((Scriptable)var0).getDefaultValue(BooleanClass);
            if (var0 instanceof Scriptable) {
               throw errorWithClassName("msg.primitive.expected", var0);
            }
         }

         if (var0 instanceof String) {
            return ((String)var0).length() != 0;
         } else if (!(var0 instanceof Number)) {
            if (var0 instanceof Boolean) {
               return (Boolean)var0;
            } else {
               throw errorWithClassName("msg.invalid.type", var0);
            }
         } else {
            double var1 = ((Number)var0).doubleValue();
            return var1 == var1 && var1 != 0.0;
         }
      }
   }

   public static boolean toBoolean(Object[] var0, int var1) {
      return var1 < var0.length ? toBoolean(var0[var1]) : false;
   }

   public static int toInt32(double var0) {
      double var2 = 4.294967296E9;
      double var4 = 2.147483648E9;
      if (var0 == var0 && var0 != 0.0 && var0 != Double.POSITIVE_INFINITY && var0 != Double.NEGATIVE_INFINITY) {
         var0 = Math.IEEEremainder(var0, var2);
         var0 = var0 >= 0.0 ? var0 : var0 + var2;
         return var0 >= var4 ? (int)(var0 - var2) : (int)var0;
      } else {
         return 0;
      }
   }

   public static int toInt32(Object var0) {
      double var1 = 4.294967296E9;
      double var3 = 2.147483648E9;
      if (var0 instanceof Byte) {
         return ((Number)var0).intValue();
      } else {
         double var5 = toNumber(var0);
         if (var5 == var5 && var5 != 0.0 && var5 != Double.POSITIVE_INFINITY && var5 != Double.NEGATIVE_INFINITY) {
            var5 = Math.IEEEremainder(var5, var1);
            var5 = var5 >= 0.0 ? var5 : var5 + var1;
            return var5 >= var3 ? (int)(var5 - var1) : (int)var5;
         } else {
            return 0;
         }
      }
   }

   public static int toInt32(Object[] var0, int var1) {
      return var1 < var0.length ? toInt32(var0[var1]) : 0;
   }

   public static double toInteger(double var0) {
      if (var0 != var0) {
         return 0.0;
      } else if (var0 != 0.0 && var0 != Double.POSITIVE_INFINITY && var0 != Double.NEGATIVE_INFINITY) {
         return var0 > 0.0 ? Math.floor(var0) : Math.ceil(var0);
      } else {
         return var0;
      }
   }

   public static double toInteger(Object var0) {
      return toInteger(toNumber(var0));
   }

   public static double toInteger(Object[] var0, int var1) {
      return var1 < var0.length ? toInteger(var0[var1]) : 0.0;
   }

   public static double toNumber(Object var0) {
      if (var0 == null) {
         return 0.0;
      } else {
         if (var0 instanceof Scriptable) {
            var0 = ((Scriptable)var0).getDefaultValue(NumberClass);
            if (var0 != null && var0 instanceof Scriptable) {
               throw errorWithClassName("msg.primitive.expected", var0);
            }
         }

         if (var0 instanceof String) {
            return toNumber((String)var0);
         } else if (var0 instanceof Number) {
            return ((Number)var0).doubleValue();
         } else if (var0 instanceof Boolean) {
            return (Boolean)var0 ? 1.0 : 0.0;
         } else {
            throw errorWithClassName("msg.invalid.type", var0);
         }
      }
   }

   public static double toNumber(String var0) {
      int var1 = var0.length();

      for(int var2 = 0; var2 != var1; ++var2) {
         char var3 = var0.charAt(var2);
         if (!Character.isWhitespace(var3)) {
            if (var3 == '0' && var2 + 2 < var1 && Character.toLowerCase(var0.charAt(var2 + 1)) == 'x') {
               return stringToNumber(var0, var2 + 2, 16);
            }

            if ((var3 == '+' || var3 == '-') && var2 + 3 < var1 && var0.charAt(var2 + 1) == '0' && Character.toLowerCase(var0.charAt(var2 + 2)) == 'x') {
               double var10 = stringToNumber(var0, var2 + 3, 16);
               return var3 == '-' ? -var10 : var10;
            }

            int var4;
            char var5;
            for(var4 = var1 - 1; Character.isWhitespace(var5 = var0.charAt(var4)); --var4) {
            }

            if (var5 == 'y') {
               if (var3 == '+' || var3 == '-') {
                  ++var2;
               }

               if (var2 + 7 == var4 && var0.regionMatches(var2, "Infinity", 0, 8)) {
                  return var3 == '-' ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
               }

               return NaN;
            }

            String var6 = var0.substring(var2, var4 + 1);

            for(int var7 = var6.length() - 1; var7 >= 0; --var7) {
               char var8 = var6.charAt(var7);
               if ((var8 < '0' || var8 > '9') && var8 != '.' && var8 != 'e' && var8 != 'E' && var8 != '+' && var8 != '-') {
                  return NaN;
               }
            }

            try {
               return Double.valueOf(var6);
            } catch (NumberFormatException var9) {
               return NaN;
            }
         }
      }

      return 0.0;
   }

   public static double toNumber(Object[] var0, int var1) {
      return var1 < var0.length ? toNumber(var0[var1]) : NaN;
   }

   public static Scriptable toObject(Scriptable var0, Object var1) {
      return toObject(var0, var1, (Class)null);
   }

   public static Scriptable toObject(Scriptable var0, Object var1, Class var2) {
      if (var1 == null) {
         throw NativeGlobal.typeError0("msg.null.to.object", var0);
      } else if (var1 instanceof Scriptable) {
         if (var1 == Undefined.instance) {
            throw NativeGlobal.typeError0("msg.undef.to.object", var0);
         } else {
            return (Scriptable)var1;
         }
      } else {
         String var3 = var1 instanceof String ? "String" : (var1 instanceof Number ? "Number" : (var1 instanceof Boolean ? "Boolean" : null));
         if (var3 == null) {
            Object var6 = NativeJavaObject.wrap(var0, var1, var2);
            if (var6 instanceof Scriptable) {
               return (Scriptable)var6;
            } else {
               throw errorWithClassName("msg.invalid.type", var1);
            }
         } else {
            Object[] var4 = new Object[]{var1};
            var0 = ScriptableObject.getTopLevelScope(var0);
            Scriptable var5 = newObject(Context.getContext(), var0, var3, var4);
            return var5;
         }
      }
   }

   public static Object toPrimitive(Object var0) {
      if (var0 != null && var0 instanceof Scriptable) {
         Object var1 = ((Scriptable)var0).getDefaultValue((Class)null);
         if (var1 != null && var1 instanceof Scriptable) {
            throw NativeGlobal.typeError0("msg.bad.default.value", var0);
         } else {
            return var1;
         }
      } else {
         return var0;
      }
   }

   public static String toString(double var0) {
      return numberToString(var0, 10);
   }

   public static String toString(Object var0) {
      while(var0 != null) {
         if (var0 instanceof Scriptable) {
            var0 = ((Scriptable)var0).getDefaultValue(StringClass);
            if (var0 == Undefined.instance || !(var0 instanceof Scriptable)) {
               continue;
            }

            throw errorWithClassName("msg.primitive.expected", var0);
         }

         if (var0 instanceof Number) {
            return numberToString(((Number)var0).doubleValue(), 10);
         }

         return var0.toString();
      }

      return "null";
   }

   public static String toString(Object[] var0, int var1) {
      return var1 < var0.length ? toString(var0[var1]) : "undefined";
   }

   public static char toUint16(Object var0) {
      long var1 = 65536L;
      double var3 = toNumber(var0);
      if (var3 == var3 && var3 != 0.0 && var3 != Double.POSITIVE_INFINITY && var3 != Double.NEGATIVE_INFINITY) {
         var3 = Math.IEEEremainder(var3, (double)var1);
         var3 = var3 >= 0.0 ? var3 : var3 + (double)var1;
         return (char)((int)Math.floor(var3));
      } else {
         return '\u0000';
      }
   }

   public static long toUint32(double var0) {
      double var2 = 4.294967296E9;
      if (var0 == var0 && var0 != 0.0 && var0 != Double.POSITIVE_INFINITY && var0 != Double.NEGATIVE_INFINITY) {
         if (var0 > 0.0) {
            var0 = Math.floor(var0);
         } else {
            var0 = Math.ceil(var0);
         }

         var0 = Math.IEEEremainder(var0, var2);
         var0 = var0 >= 0.0 ? var0 : var0 + var2;
         return (long)Math.floor(var0);
      } else {
         return 0L;
      }
   }

   public static long toUint32(Object var0) {
      return toUint32(toNumber(var0));
   }

   public static String typeof(Object var0) {
      if (var0 == Undefined.instance) {
         return "undefined";
      } else if (var0 == null) {
         return "object";
      } else if (var0 instanceof Scriptable) {
         return var0 instanceof Function ? "function" : "object";
      } else if (var0 instanceof String) {
         return "string";
      } else if (var0 instanceof Number) {
         return "number";
      } else if (var0 instanceof Boolean) {
         return "boolean";
      } else {
         throw errorWithClassName("msg.invalid.type", var0);
      }
   }

   public static String typeofName(Scriptable var0, String var1) {
      Scriptable var2 = bind(var0, var1);
      return var2 == null ? "undefined" : typeof(getProp(var2, var1, var0));
   }

   public static Object unwrapJavaScriptException(JavaScriptException var0) {
      return var0.value;
   }

   public static Object unwrapWrappedException(WrappedException var0) {
      Throwable var1 = var0.getWrappedException();
      if (var1 instanceof JavaScriptException) {
         return ((JavaScriptException)var1).value;
      } else {
         throw var0;
      }
   }
}
