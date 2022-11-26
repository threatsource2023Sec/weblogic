package org.mozilla.javascript;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Vector;

public class NativeJavaMethod extends NativeFunction implements Function {
   static final int PREFERENCE_EQUAL = 0;
   static final int PREFERENCE_FIRST_ARG = 1;
   static final int PREFERENCE_SECOND_ARG = 2;
   static final int PREFERENCE_AMBIGUOUS = 3;
   private static final boolean debug = false;
   Method[] methods;

   public NativeJavaMethod() {
      super.functionName = null;
   }

   public NativeJavaMethod(Method var1, String var2) {
      this.methods = new Method[1];
      this.methods[0] = var1;
      super.functionName = var2;
   }

   public NativeJavaMethod(Method[] var1) {
      this.methods = var1;
      super.functionName = var1[0].getName();
   }

   public void add(Method var1) {
      if (super.functionName == null) {
         super.functionName = var1.getName();
      } else if (!super.functionName.equals(var1.getName())) {
         throw new RuntimeException("internal method name mismatch");
      }

      int var2 = this.methods == null ? 0 : this.methods.length;
      Method[] var3 = new Method[var2 + 1];

      for(int var4 = 0; var4 < var2; ++var4) {
         var3[var4] = this.methods[var4];
      }

      var3[var2] = var1;
      this.methods = var3;
   }

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      if (this.methods.length == 0) {
         throw new RuntimeException("No methods defined for call");
      } else {
         Method var5 = (Method)findFunction(this.methods, var4);
         if (var5 == null) {
            Class var14 = this.methods[0].getDeclaringClass();
            String var15 = var14.getName() + "." + super.functionName + "(" + scriptSignature(var4) + ")";
            throw Context.reportRuntimeError1("msg.java.no_such_method", var15);
         } else {
            Class[] var6 = var5.getParameterTypes();

            for(int var7 = 0; var7 < var4.length; ++var7) {
               var4[var7] = NativeJavaObject.coerceType(var6[var7], var4[var7]);
            }

            Object var8;
            if (Modifier.isStatic(var5.getModifiers())) {
               var8 = null;
            } else {
               Scriptable var9 = var3;

               while(!(var9 instanceof Wrapper)) {
                  var9 = var9.getPrototype();
                  if (var9 == null) {
                     throw Context.reportRuntimeError1("msg.nonjava.method", super.functionName);
                  }
               }

               var8 = ((Wrapper)var9).unwrap();
            }

            try {
               Object var16 = var5.invoke(var8, var4);
               Class var10 = var5.getReturnType();
               Object var11 = NativeJavaObject.wrap(var2, var16, var10);
               if (var11 == Undefined.instance) {
                  return var11;
               } else {
                  return var11 == null && var10 == Void.TYPE ? Undefined.instance : var11;
               }
            } catch (IllegalAccessException var12) {
               throw Context.reportRuntimeError(var12.getMessage());
            } catch (InvocationTargetException var13) {
               throw JavaScriptException.wrapException(var2, var13);
            }
         }
      }
   }

   public String decompile(Context var1, int var2, boolean var3) {
      StringBuffer var4 = new StringBuffer();
      if (!var3) {
         var4.append("function ");
         var4.append(this.getFunctionName());
         var4.append("() {");
      }

      var4.append("/*\n");
      this.toString(var4);
      var4.append(var3 ? "*/\n" : "*/}\n");
      return var4.toString();
   }

   static Member findFunction(Member[] var0, Object[] var1) {
      if (var0.length == 0) {
         return null;
      } else {
         boolean var2 = var0[0] instanceof Method;
         Member var3 = null;
         Class[] var4 = null;
         Vector var5 = null;

         for(int var6 = 0; var6 < var0.length; ++var6) {
            Member var7 = var0[var6];
            Class[] var8 = var2 ? ((Method)var7).getParameterTypes() : ((Constructor)var7).getParameterTypes();
            if (var8.length == var1.length) {
               int var9;
               if (var4 != null) {
                  var9 = preferSignature(var1, var8, var4);
                  if (var9 == 3) {
                     if (var5 == null) {
                        var5 = new Vector();
                     }

                     var5.addElement(var7);
                  } else if (var9 == 1) {
                     var3 = var7;
                     var4 = var8;
                  } else if (var9 == 0 && Modifier.isStatic(var3.getModifiers()) && var3.getDeclaringClass().isAssignableFrom(var7.getDeclaringClass())) {
                     var3 = var7;
                     var4 = var8;
                  }
               } else {
                  for(var9 = 0; var9 < var8.length && NativeJavaObject.canConvert(var1[var9], var8[var9]); ++var9) {
                  }

                  if (var9 == var8.length) {
                     var3 = var7;
                     var4 = var8;
                  }
               }
            }
         }

         if (var5 == null) {
            return var3;
         } else {
            int var10;
            for(int var13 = var5.size() - 1; var13 >= 0; --var13) {
               Member var14 = (Member)var5.elementAt(var13);
               Class[] var16 = var2 ? ((Method)var14).getParameterTypes() : ((Constructor)var14).getParameterTypes();
               var10 = preferSignature(var1, var16, var4);
               if (var10 == 1) {
                  var3 = var14;
                  var4 = var16;
                  var5.removeElementAt(var13);
               } else if (var10 == 2) {
                  var5.removeElementAt(var13);
               }
            }

            if (var5.size() > 0) {
               StringBuffer var15 = new StringBuffer();
               boolean var17 = var3 instanceof Constructor;
               var5.addElement(var3);

               for(var10 = 0; var10 < var5.size(); ++var10) {
                  if (var10 != 0) {
                     var15.append(", ");
                  }

                  Member var11 = (Member)var5.elementAt(var10);
                  if (!var17) {
                     Class var12 = ((Method)var11).getReturnType();
                     var15.append(var12);
                     var15.append(' ');
                  }

                  var15.append(signature(var11));
               }

               String var18;
               Object[] var19;
               if (var17) {
                  var19 = new Object[]{var3.getName(), scriptSignature(var1), var15.toString()};
                  var18 = Context.getMessage("msg.constructor.ambiguous", var19);
               } else {
                  var19 = new Object[]{var3.getDeclaringClass().getName(), var3.getName(), scriptSignature(var1), var15.toString()};
                  var18 = Context.getMessage("msg.method.ambiguous", var19);
               }

               throw Context.reportRuntimeError(var18);
            } else {
               return var3;
            }
         }
      }
   }

   Method[] getMethods() {
      return this.methods;
   }

   static String javaSignature(Class var0) {
      if (var0 == null) {
         return "null";
      } else {
         return var0.isArray() ? javaSignature(var0.getComponentType()) + "[]" : var0.getName();
      }
   }

   static String javaSignature(Class[] var0) {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var2 != 0) {
            var1.append(',');
         }

         var1.append(javaSignature(var0[var2]));
      }

      return var1.toString();
   }

   public static int preferConversion(Object var0, Class var1, Class var2) {
      int var3 = NativeJavaObject.getConversionWeight(var0, var1);
      int var4 = NativeJavaObject.getConversionWeight(var0, var2);
      if (var3 == 0 && var4 == 0) {
         if (var1.isAssignableFrom(var2)) {
            return 2;
         }

         if (var2.isAssignableFrom(var1)) {
            return 1;
         }
      } else {
         if (var3 < var4) {
            return 1;
         }

         if (var3 > var4) {
            return 2;
         }
      }

      return 3;
   }

   public static int preferSignature(Object[] var0, Class[] var1, Class[] var2) {
      int var3 = 0;

      for(int var4 = 0; var4 < var0.length; ++var4) {
         Class var5 = var1[var4];
         Class var6 = var2[var4];
         if (var5 != var6) {
            var3 |= preferConversion(var0[var4], var5, var6);
            if (var3 == 3) {
               break;
            }
         }
      }

      return var3;
   }

   private static void printDebug(String var0, Member var1, Object[] var2) {
   }

   static String scriptSignature(Object var0) {
      if (var0 == null) {
         return "null";
      } else {
         Class var1 = var0.getClass();
         if (var1 == ScriptRuntime.UndefinedClass) {
            return "undefined";
         } else if (var1 == ScriptRuntime.BooleanClass) {
            return "boolean";
         } else if (var1 == ScriptRuntime.StringClass) {
            return "string";
         } else if (ScriptRuntime.NumberClass.isAssignableFrom(var1)) {
            return "number";
         } else if (var0 instanceof Wrapper) {
            return ((Wrapper)var0).unwrap().getClass().getName();
         } else if (var0 instanceof Scriptable) {
            return var0 instanceof Function ? "function" : "object";
         } else {
            return javaSignature(var1);
         }
      }
   }

   static String scriptSignature(Object[] var0) {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var2 != 0) {
            var1.append(',');
         }

         var1.append(scriptSignature(var0[var2]));
      }

      return var1.toString();
   }

   static String signature(Member var0) {
      Class[] var1;
      if (var0 instanceof Method) {
         var1 = ((Method)var0).getParameterTypes();
         return var0.getName() + "(" + javaSignature(var1) + ")";
      } else {
         var1 = ((Constructor)var0).getParameterTypes();
         return "(" + javaSignature(var1) + ")";
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   private void toString(StringBuffer var1) {
      for(int var2 = 0; var2 < this.methods.length; ++var2) {
         var1.append(javaSignature(this.methods[var2].getReturnType()));
         var1.append(' ');
         var1.append(signature(this.methods[var2]));
         var1.append('\n');
      }

   }
}
