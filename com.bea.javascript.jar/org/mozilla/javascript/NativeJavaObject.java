package org.mozilla.javascript;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

public class NativeJavaObject implements Scriptable, Wrapper {
   static final int JSTYPE_UNDEFINED = 0;
   static final int JSTYPE_NULL = 1;
   static final int JSTYPE_BOOLEAN = 2;
   static final int JSTYPE_NUMBER = 3;
   static final int JSTYPE_STRING = 4;
   static final int JSTYPE_JAVA_CLASS = 5;
   static final int JSTYPE_JAVA_OBJECT = 6;
   static final int JSTYPE_JAVA_ARRAY = 7;
   static final int JSTYPE_OBJECT = 8;
   public static final byte CONVERSION_TRIVIAL = 1;
   public static final byte CONVERSION_NONTRIVIAL = 0;
   public static final byte CONVERSION_NONE = 99;
   protected Scriptable prototype;
   protected Scriptable parent;
   protected Object javaObject;
   protected JavaMembers members;
   private Hashtable fieldAndMethods;
   static Class jsObjectClass;
   static Constructor jsObjectCtor;
   static Method jsObjectGetScriptable;

   public NativeJavaObject(Scriptable var1, Object var2, Class var3) {
      this.parent = var1;
      this.javaObject = var2;
      Class var4 = var2 != null ? var2.getClass() : var3;
      this.members = JavaMembers.lookupClass(var1, var4, var3);
      this.fieldAndMethods = this.members.getFieldAndMethodsObjects(this, var2, false);
   }

   public NativeJavaObject(Scriptable var1, Object var2, JavaMembers var3) {
      this.parent = var1;
      this.javaObject = var2;
      this.members = var3;
   }

   Object callConverter(String var1) throws JavaScriptException {
      Function var2 = this.getConverter(var1);
      return var2 == null ? this.javaObject.toString() : this.callConverter(var2);
   }

   Object callConverter(Function var1) throws JavaScriptException {
      return var1.call(Context.getContext(), var1.getParentScope(), this, ScriptRuntime.emptyArgs);
   }

   public static boolean canConvert(Object var0, Class var1) {
      int var2 = getConversionWeight(var0, var1);
      return var2 < 99;
   }

   static Object coerceToJSObject(Class var0, Scriptable var1) {
      if (ScriptRuntime.ScriptableClass.isAssignableFrom(var0)) {
         return var1;
      } else {
         try {
            Object[] var2 = new Object[]{var1};
            return jsObjectCtor.newInstance(var2);
         } catch (InstantiationException var3) {
            throw new EvaluatorException("error generating JSObject wrapper for " + var1);
         } catch (IllegalArgumentException var4) {
            throw new EvaluatorException("JSObject constructor doesn't want [Scriptable]!");
         } catch (InvocationTargetException var5) {
            throw WrappedException.wrapException(var5.getTargetException());
         } catch (IllegalAccessException var6) {
            throw new EvaluatorException("JSObject constructor is protected/private!");
         }
      }
   }

   static Object coerceToNumber(Class var0, Object var1) {
      Class var2 = var1.getClass();
      if (var0 != Character.TYPE && var0 != ScriptRuntime.CharacterClass) {
         if (var0 != ScriptRuntime.ObjectClass && var0 != ScriptRuntime.DoubleClass && var0 != Double.TYPE) {
            double var3;
            double var5;
            if (var0 != ScriptRuntime.FloatClass && var0 != Float.TYPE) {
               if (var0 != ScriptRuntime.IntegerClass && var0 != Integer.TYPE) {
                  if (var0 != ScriptRuntime.LongClass && var0 != Long.TYPE) {
                     if (var0 != ScriptRuntime.ShortClass && var0 != Short.TYPE) {
                        if (var0 != ScriptRuntime.ByteClass && var0 != Byte.TYPE) {
                           return new Double(toDouble(var1));
                        } else {
                           return var2 == ScriptRuntime.ByteClass ? var1 : new Byte((byte)((int)toInteger(var1, ScriptRuntime.ByteClass, -128.0, 127.0)));
                        }
                     } else {
                        return var2 == ScriptRuntime.ShortClass ? var1 : new Short((short)((int)toInteger(var1, ScriptRuntime.ShortClass, -32768.0, 32767.0)));
                     }
                  } else if (var2 == ScriptRuntime.LongClass) {
                     return var1;
                  } else {
                     var3 = Double.longBitsToDouble(4890909195324358655L);
                     var5 = Double.longBitsToDouble(-4332462841530417152L);
                     return new Long(toInteger(var1, ScriptRuntime.LongClass, var5, var3));
                  }
               } else {
                  return var2 == ScriptRuntime.IntegerClass ? var1 : new Integer((int)toInteger(var1, ScriptRuntime.IntegerClass, -2.147483648E9, 2.147483647E9));
               }
            } else if (var2 == ScriptRuntime.FloatClass) {
               return var1;
            } else {
               var3 = toDouble(var1);
               if (!Double.isInfinite(var3) && !Double.isNaN(var3) && var3 != 0.0) {
                  var5 = Math.abs(var3);
                  if (var5 < 1.401298464324817E-45) {
                     return new Float(var3 > 0.0 ? 0.0 : -0.0);
                  } else {
                     return var5 > 3.4028234663852886E38 ? new Float(var3 > 0.0 ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY) : new Float((float)var3);
                  }
               } else {
                  return new Float((float)var3);
               }
            }
         } else {
            return var2 == ScriptRuntime.DoubleClass ? var1 : new Double(toDouble(var1));
         }
      } else {
         return var2 == ScriptRuntime.CharacterClass ? var1 : new Character((char)((int)toInteger(var1, ScriptRuntime.CharacterClass, 0.0, 65535.0)));
      }
   }

   public static Object coerceType(Class var0, Object var1) {
      // $FF: Couldn't be decompiled
   }

   public void delete(int var1) {
   }

   public void delete(String var1) {
   }

   public Object get(int var1, Scriptable var2) {
      throw this.members.reportMemberNotFound(Integer.toString(var1));
   }

   public Object get(String var1, Scriptable var2) {
      if (this.fieldAndMethods != null) {
         Object var3 = this.fieldAndMethods.get(var1);
         if (var3 != null) {
            return var3;
         }
      }

      return this.members.get(this, var1, this.javaObject, false);
   }

   public String getClassName() {
      return "JavaObject";
   }

   public static int getConversionWeight(Object var0, Class var1) {
      int var2 = getJSTypeCode(var0);
      int var3 = 99;
      switch (var2) {
         case 0:
            if (var1 == ScriptRuntime.StringClass || var1 == ScriptRuntime.ObjectClass) {
               var3 = 1;
            }
            break;
         case 1:
            if (!var1.isPrimitive()) {
               var3 = 1;
            }
            break;
         case 2:
            if (var1 == Boolean.TYPE) {
               var3 = 1;
            } else if (var1 == ScriptRuntime.BooleanClass) {
               var3 = 2;
            } else if (var1 == ScriptRuntime.ObjectClass) {
               var3 = 3;
            } else if (var1 == ScriptRuntime.StringClass) {
               var3 = 4;
            }
            break;
         case 3:
            if (var1.isPrimitive()) {
               if (var1 == Double.TYPE) {
                  var3 = 1;
               } else if (var1 != Boolean.TYPE) {
                  var3 = 1 + getSizeRank(var1);
               }
            } else if (var1 == ScriptRuntime.StringClass) {
               var3 = 9;
            } else if (var1 == ScriptRuntime.ObjectClass) {
               var3 = 10;
            } else if (ScriptRuntime.NumberClass.isAssignableFrom(var1)) {
               var3 = 2;
            }
            break;
         case 4:
            if (var1 == ScriptRuntime.StringClass) {
               var3 = 1;
            } else if (var1 == ScriptRuntime.ObjectClass) {
               var3 = 2;
            } else if (var1.isPrimitive() && var1 != Boolean.TYPE) {
               if (var1 == Character.TYPE) {
                  var3 = 3;
               } else {
                  var3 = 4;
               }
            }
            break;
         case 5:
            if (var1 == ScriptRuntime.ClassClass) {
               var3 = 1;
            } else if (var1 == ScriptRuntime.ObjectClass) {
               var3 = 3;
            } else if (var1 == ScriptRuntime.StringClass) {
               var3 = 4;
            }
            break;
         case 6:
         case 7:
            if (var1 == ScriptRuntime.StringClass) {
               var3 = 2;
            } else if (var1.isPrimitive() && var1 != Boolean.TYPE) {
               var3 = var2 == 7 ? 0 : 2 + getSizeRank(var1);
            } else {
               Object var4 = var0;
               if (var0 instanceof Wrapper) {
                  var4 = ((Wrapper)var0).unwrap();
               }

               if (var1.isInstance(var4)) {
                  var3 = 0;
               }
            }
            break;
         case 8:
            if (var0 instanceof NativeArray && var1.isArray()) {
               var3 = 1;
            } else if (var1 == ScriptRuntime.ObjectClass) {
               var3 = 2;
            } else if (var1 == ScriptRuntime.StringClass) {
               var3 = 3;
            } else if (var1.isPrimitive() || var1 != Boolean.TYPE) {
               var3 = 3 + getSizeRank(var1);
            }
      }

      return var3;
   }

   Function getConverter(String var1) {
      Object var2 = this.get(var1, this);
      return var2 instanceof Function ? (Function)var2 : null;
   }

   public Object getDefaultValue(Class var1) {
      if (var1 != null && var1 != ScriptRuntime.StringClass) {
         try {
            if (var1 == ScriptRuntime.BooleanClass) {
               return this.callConverter("booleanValue");
            }

            if (var1 == ScriptRuntime.NumberClass) {
               return this.callConverter("doubleValue");
            }
         } catch (JavaScriptException var2) {
         }

         throw Context.reportRuntimeError0("msg.default.value");
      } else {
         return this.javaObject.toString();
      }
   }

   public Object[] getIds() {
      return this.members.getIds(false);
   }

   static int getJSTypeCode(Object var0) {
      if (var0 == null) {
         return 1;
      } else if (var0 == Undefined.instance) {
         return 0;
      } else if (var0 instanceof Scriptable) {
         if (var0 instanceof NativeJavaClass) {
            return 5;
         } else if (var0 instanceof NativeJavaArray) {
            return 7;
         } else {
            return var0 instanceof NativeJavaObject ? 6 : 8;
         }
      } else {
         Class var1 = var0.getClass();
         if (var1 == ScriptRuntime.StringClass) {
            return 4;
         } else if (var1 == ScriptRuntime.BooleanClass) {
            return 2;
         } else if (var0 instanceof Number) {
            return 3;
         } else if (var1 == ScriptRuntime.ClassClass) {
            return 5;
         } else {
            return var1.isArray() ? 7 : 6;
         }
      }
   }

   public Scriptable getParentScope() {
      return this.parent;
   }

   public Scriptable getPrototype() {
      return this.prototype == null && this.javaObject.getClass() == ScriptRuntime.StringClass ? ScriptableObject.getClassPrototype(this.parent, "String") : this.prototype;
   }

   static int getSizeRank(Class var0) {
      if (var0 == Double.TYPE) {
         return 1;
      } else if (var0 == Float.TYPE) {
         return 2;
      } else if (var0 == Long.TYPE) {
         return 3;
      } else if (var0 == Integer.TYPE) {
         return 4;
      } else if (var0 == Short.TYPE) {
         return 5;
      } else if (var0 == Character.TYPE) {
         return 6;
      } else if (var0 == Byte.TYPE) {
         return 7;
      } else {
         return var0 == Boolean.TYPE ? 99 : 8;
      }
   }

   public boolean has(int var1, Scriptable var2) {
      return false;
   }

   public boolean has(String var1, Scriptable var2) {
      return this.members.has(var1, false);
   }

   public boolean hasInstance(Scriptable var1) {
      return false;
   }

   public static void initJSObject() {
      jsObjectClass = null;

      try {
         jsObjectClass = Class.forName("netscape.javascript.JSObject");
         Class[] var0 = new Class[]{ScriptRuntime.ScriptableClass};
         jsObjectCtor = jsObjectClass.getConstructor(var0);
         jsObjectGetScriptable = jsObjectClass.getMethod("getScriptable");
      } catch (ClassNotFoundException var1) {
      } catch (NoSuchMethodException var2) {
      }

   }

   public void put(int var1, Scriptable var2, Object var3) {
      throw this.members.reportMemberNotFound(Integer.toString(var1));
   }

   public void put(String var1, Scriptable var2, Object var3) {
      if (this.prototype != null && !this.members.has(var1, false)) {
         this.prototype.put(var1, this.prototype, var3);
      } else {
         this.members.put(this, var1, this.javaObject, var3, false);
      }

   }

   static void reportConversionError(Object var0, Class var1) {
      throw Context.reportRuntimeError2("msg.conversion.not.allowed", var0.toString(), NativeJavaMethod.javaSignature(var1));
   }

   public void setParentScope(Scriptable var1) {
      this.parent = var1;
   }

   public void setPrototype(Scriptable var1) {
      this.prototype = var1;
   }

   static double toDouble(Object var0) {
      if (var0 instanceof Number) {
         return ((Number)var0).doubleValue();
      } else if (var0 instanceof String) {
         return ScriptRuntime.toNumber((String)var0);
      } else if (var0 instanceof Scriptable) {
         return var0 instanceof Wrapper ? toDouble(((Wrapper)var0).unwrap()) : ScriptRuntime.toNumber(var0);
      } else {
         Method var1;
         try {
            var1 = var0.getClass().getMethod("doubleValue", (Class[])null);
         } catch (NoSuchMethodException var4) {
            var1 = null;
         } catch (SecurityException var5) {
            var1 = null;
         }

         if (var1 != null) {
            try {
               return ((Number)var1.invoke(var0, (Object[])null)).doubleValue();
            } catch (IllegalAccessException var2) {
               reportConversionError(var0, Double.TYPE);
            } catch (InvocationTargetException var3) {
               reportConversionError(var0, Double.TYPE);
            }
         }

         return ScriptRuntime.toNumber(var0.toString());
      }
   }

   static long toInteger(Object var0, Class var1, double var2, double var4) {
      double var6 = toDouble(var0);
      if (Double.isInfinite(var6) || Double.isNaN(var6)) {
         reportConversionError(ScriptRuntime.toString(var0), var1);
      }

      if (var6 > 0.0) {
         var6 = Math.floor(var6);
      } else {
         var6 = Math.ceil(var6);
      }

      if (var6 < var2 || var6 > var4) {
         reportConversionError(ScriptRuntime.toString(var0), var1);
      }

      return (long)var6;
   }

   public Object unwrap() {
      return this.javaObject;
   }

   public static Object wrap(Scriptable var0, Object var1, Class var2) {
      if (var1 == null) {
         return var1;
      } else {
         Context var3 = Context.getCurrentContext();
         if (var3 != null && var3.wrapHandler != null) {
            Object var4 = var3.wrapHandler.wrap(var0, var1, var2);
            if (var4 != null) {
               return var4;
            }
         }

         Class var5 = var1.getClass();
         if (var2 != null && var2.isPrimitive()) {
            if (var2 == Void.TYPE) {
               return Undefined.instance;
            } else {
               return var2 == Character.TYPE ? new Integer((Character)var1) : var1;
            }
         } else if (var5.isArray()) {
            return NativeJavaArray.wrap(var0, var1);
         } else {
            return var1 instanceof Scriptable ? var1 : new NativeJavaObject(var0, var1, var2);
         }
      }
   }
}
