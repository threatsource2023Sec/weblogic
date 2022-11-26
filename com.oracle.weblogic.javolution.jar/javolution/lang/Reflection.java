package javolution.lang;

import java.lang.reflect.InvocationTargetException;
import javolution.JavolutionError;
import javolution.util.StandardLog;

public final class Reflection {
   private Reflection() {
   }

   public static Class getClass(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var6) {
         try {
            ClassLoader var2 = Thread.currentThread().getContextClassLoader();
            return Class.forName(var0, true, var2);
         } catch (ClassNotFoundException var5) {
            try {
               ClassLoader var3 = ClassLoader.getSystemClassLoader();
               return Class.forName(var0, true, var3);
            } catch (ClassNotFoundException var4) {
               StandardLog.warning(var0 + " not found");
               return null;
            }
         }
      } catch (Throwable var7) {
         StandardLog.error(var7);
         return null;
      }
   }

   public static Constructor getConstructor(String var0) {
      int var1 = var0.indexOf(40) + 1;
      if (var1 < 0) {
         throw new IllegalArgumentException("Parenthesis '(' not found");
      } else {
         int var2 = var0.indexOf(41);
         if (var2 < 0) {
            throw new IllegalArgumentException("Parenthesis ')' not found");
         } else {
            String var3 = var0.substring(0, var1 - 1);
            Class var4 = getClass(var3);
            if (var4 == null) {
               return null;
            } else {
               String var5 = var0.substring(var1, var2);
               if (var5.length() == 0) {
                  return new DefaultConstructor(var4);
               } else {
                  Class[] var6 = classesFor(var5);
                  if (var6 != null) {
                     try {
                        return new ReflectConstructor(var4.getConstructor(var6), var0);
                     } catch (NoSuchMethodException var8) {
                        return null;
                     }
                  } else {
                     return null;
                  }
               }
            }
         }
      }
   }

   public static Method getMethod(String var0) {
      int var1 = var0.indexOf(40) + 1;
      if (var1 < 0) {
         throw new IllegalArgumentException("Parenthesis '(' not found");
      } else {
         int var2 = var0.indexOf(41);
         if (var2 < 0) {
            throw new IllegalArgumentException("Parenthesis ')' not found");
         } else {
            int var3 = var0.substring(0, var1).lastIndexOf(46) + 1;

            try {
               String var4 = var0.substring(0, var3 - 1);
               Class var5 = getClass(var4);
               if (var5 == null) {
                  return null;
               } else {
                  String var6 = var0.substring(var3, var1 - 1);
                  String var7 = var0.substring(var1, var2);
                  Class[] var8 = classesFor(var7);
                  return var8 == null ? null : new ReflectMethod(var5.getMethod(var6, var8), var0);
               }
            } catch (Throwable var9) {
               return null;
            }
         }
      }
   }

   private static Class[] classesFor(String var0) {
      var0 = var0.trim();
      if (var0.length() == 0) {
         return new Class[0];
      } else {
         int var1 = 0;

         for(int var2 = 0; var2 < var0.length(); ++var2) {
            if (var0.charAt(var2) == ',') {
               ++var1;
            }
         }

         Class[] var6 = new Class[var1 + 1];
         int var3 = 0;

         for(int var4 = 0; var4 < var1; ++var4) {
            int var5 = var0.indexOf(44, var3);
            var6[var4] = classFor(var0.substring(var3, var5).trim());
            if (var6[var4] == null) {
               return null;
            }

            var3 = var5 + 1;
         }

         var6[var1] = classFor(var0.substring(var3).trim());
         if (var6[var1] == null) {
            return null;
         } else {
            return var6;
         }
      }
   }

   private static Class classFor(String var0) {
      int var1 = var0.indexOf("[]");
      if (var1 >= 0) {
         if (var0.indexOf("[][]") >= 0) {
            if (var0.indexOf("[][][]") >= 0) {
               if (var0.indexOf("[][][][]") >= 0) {
                  throw new UnsupportedOperationException("The maximum array dimension is 3");
               } else {
                  return getClass("[[[" + descriptorFor(var0.substring(0, var1)));
               }
            } else {
               return getClass("[[" + descriptorFor(var0.substring(0, var1)));
            }
         } else {
            return getClass("[" + descriptorFor(var0.substring(0, var1)));
         }
      } else if (var0.equals("boolean")) {
         return Boolean.TYPE;
      } else if (var0.equals("byte")) {
         return Byte.TYPE;
      } else if (var0.equals("char")) {
         return Character.TYPE;
      } else if (var0.equals("short")) {
         return Short.TYPE;
      } else if (var0.equals("int")) {
         return Integer.TYPE;
      } else if (var0.equals("long")) {
         return Long.TYPE;
      } else if (var0.equals("float")) {
         return Float.TYPE;
      } else {
         return var0.equals("double") ? Double.TYPE : getClass(var0);
      }
   }

   private static String descriptorFor(String var0) {
      if (var0.equals("boolean")) {
         return "Z";
      } else if (var0.equals("byte")) {
         return "B";
      } else if (var0.equals("char")) {
         return "C";
      } else if (var0.equals("short")) {
         return "S";
      } else if (var0.equals("int")) {
         return "I";
      } else if (var0.equals("long")) {
         return "J";
      } else if (var0.equals("float")) {
         return "F";
      } else {
         return var0.equals("double") ? "D" : "L" + var0.replace('.', '/') + ";";
      }
   }

   public abstract static class Method {
      private static final Object[] NO_ARG = new Object[0];
      private final Object[] array1 = new Object[1];
      private final Object[] array2 = new Object[2];
      private final Object[] array3 = new Object[3];
      private final Object[] array4 = new Object[4];

      protected abstract Object execute(Object var1, Object[] var2);

      public final Object invoke(Object var1) {
         return this.execute(var1, NO_ARG);
      }

      public final Object invoke(Object var1, Object var2) {
         synchronized(this) {
            this.array1[0] = var2;
            return this.execute(var1, this.array1);
         }
      }

      public final Object invoke(Object var1, Object var2, Object var3) {
         synchronized(this) {
            this.array2[0] = var2;
            this.array2[1] = var3;
            return this.execute(var1, this.array2);
         }
      }

      public final Object invoke(Object var1, Object var2, Object var3, Object var4) {
         synchronized(this) {
            this.array3[0] = var2;
            this.array3[1] = var3;
            this.array3[2] = var4;
            return this.execute(var1, this.array3);
         }
      }

      public final Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5) {
         synchronized(this) {
            this.array4[0] = var2;
            this.array4[1] = var3;
            this.array4[2] = var4;
            this.array4[3] = var5;
            return this.execute(var1, this.array4);
         }
      }
   }

   public abstract static class Constructor {
      private static final Object[] NO_ARG = new Object[0];
      private final Object[] array1 = new Object[1];
      private final Object[] array2 = new Object[2];
      private final Object[] array3 = new Object[3];
      private final Object[] array4 = new Object[4];

      protected abstract Object allocate(Object[] var1);

      public final Object newInstance() {
         return this.allocate(NO_ARG);
      }

      public final Object newInstance(Object var1) {
         synchronized(this) {
            this.array1[0] = var1;
            return this.allocate(this.array1);
         }
      }

      public final Object newInstance(Object var1, Object var2) {
         synchronized(this) {
            this.array2[0] = var1;
            this.array2[1] = var2;
            return this.allocate(this.array2);
         }
      }

      public final Object newInstance(Object var1, Object var2, Object var3) {
         synchronized(this) {
            this.array3[0] = var1;
            this.array3[1] = var2;
            this.array3[2] = var3;
            return this.allocate(this.array3);
         }
      }

      public final Object newInstance(Object var1, Object var2, Object var3, Object var4) {
         synchronized(this) {
            this.array4[0] = var1;
            this.array4[1] = var2;
            this.array4[2] = var3;
            this.array4[3] = var4;
            return this.allocate(this.array4);
         }
      }
   }

   private static final class ReflectMethod extends Method {
      private final java.lang.reflect.Method _value;
      private final String _signature;

      public ReflectMethod(java.lang.reflect.Method var1, String var2) {
         this._value = var1;
         this._signature = var2;
      }

      public Object execute(Object var1, Object[] var2) {
         try {
            return this._value.invoke(var1, var2);
         } catch (IllegalArgumentException var4) {
            throw new JavolutionError("Illegal argument for " + this._signature + " method", var4);
         } catch (IllegalAccessException var5) {
            throw new JavolutionError("Illegal access error for " + this._signature + " method", var5);
         } catch (InvocationTargetException var6) {
            throw new JavolutionError("Invocation exception for " + this._signature + " method", (InvocationTargetException)var6.getTargetException());
         }
      }

      public String toString() {
         return this._signature + " method";
      }
   }

   private static final class ReflectConstructor extends Constructor {
      private final java.lang.reflect.Constructor _value;
      private final String _signature;

      public ReflectConstructor(java.lang.reflect.Constructor var1, String var2) {
         this._value = var1;
         this._signature = var2;
      }

      public Object allocate(Object[] var1) {
         try {
            return this._value.newInstance(var1);
         } catch (IllegalArgumentException var3) {
            throw new JavolutionError("Illegal argument for " + this._signature + " constructor", var3);
         } catch (InstantiationException var4) {
            throw new JavolutionError("Instantiation error for " + this._signature + " constructor", var4);
         } catch (IllegalAccessException var5) {
            throw new JavolutionError("Illegal access error for " + this._signature + " constructor", var5);
         } catch (InvocationTargetException var6) {
            throw new JavolutionError("Invocation exception  for " + this._signature + " constructor", (InvocationTargetException)var6.getTargetException());
         }
      }

      public String toString() {
         return this._signature + " constructor";
      }
   }

   private static class DefaultConstructor extends Constructor {
      final Class _class;

      DefaultConstructor(Class var1) {
         this._class = var1;
      }

      public Object allocate(Object[] var1) {
         try {
            return this._class.newInstance();
         } catch (InstantiationException var3) {
            throw new JavolutionError("Instantiation error for " + this._class.getName() + " default constructor", var3);
         } catch (IllegalAccessException var4) {
            throw new JavolutionError("Illegal access error for " + this._class.getName() + " constructor", var4);
         }
      }

      public String toString() {
         return this._class + " default constructor";
      }
   }
}
