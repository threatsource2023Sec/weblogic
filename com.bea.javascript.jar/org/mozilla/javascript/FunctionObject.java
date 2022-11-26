package org.mozilla.javascript;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Vector;

public class FunctionObject extends NativeFunction {
   private static final String INVOKER_MASTER_CLASS = "org.mozilla.javascript.optimizer.InvokerImpl";
   static Invoker invokerMaster = newInvokerMaster();
   private static final short VARARGS_METHOD = -1;
   private static final short VARARGS_CTOR = -2;
   private static boolean sawSecurityException;
   static Method[] methodsCache;
   Method method;
   Constructor ctor;
   private Class[] types;
   Invoker invoker;
   private short parmsLength;
   private short lengthPropertyValue;
   private boolean hasVoidReturn;
   private boolean isStatic;
   private boolean useDynamicScope;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$Context;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$Scriptable;

   public FunctionObject(String var1, Member var2, Scriptable var3) {
      String var4;
      if (var2 instanceof Constructor) {
         this.ctor = (Constructor)var2;
         this.isStatic = true;
         this.types = this.ctor.getParameterTypes();
         var4 = this.ctor.getName();
      } else {
         this.method = (Method)var2;
         this.isStatic = Modifier.isStatic(this.method.getModifiers());
         this.types = this.method.getParameterTypes();
         var4 = this.method.getName();
      }

      super.functionName = var1;
      short var5;
      if (this.types.length != 4 || !this.types[1].isArray() && !this.types[2].isArray()) {
         this.parmsLength = (short)this.types.length;

         for(int var6 = 0; var6 < this.parmsLength; ++var6) {
            Class var7 = this.types[var6];
            if (var7 != ScriptRuntime.ObjectClass && var7 != ScriptRuntime.StringClass && var7 != ScriptRuntime.BooleanClass && !ScriptRuntime.NumberClass.isAssignableFrom(var7) && !(class$org$mozilla$javascript$Scriptable != null ? class$org$mozilla$javascript$Scriptable : (class$org$mozilla$javascript$Scriptable = class$("org.mozilla.javascript.Scriptable"))).isAssignableFrom(var7) && var7 != Boolean.TYPE && var7 != Byte.TYPE && var7 != Short.TYPE && var7 != Integer.TYPE && var7 != Float.TYPE && var7 != Double.TYPE) {
               throw Context.reportRuntimeError1("msg.bad.parms", var4);
            }
         }

         var5 = this.parmsLength;
      } else {
         if (this.types[1].isArray()) {
            if (!this.isStatic || this.types[0] != (class$org$mozilla$javascript$Context != null ? class$org$mozilla$javascript$Context : (class$org$mozilla$javascript$Context = class$("org.mozilla.javascript.Context"))) || this.types[1].getComponentType() != ScriptRuntime.ObjectClass || this.types[2] != ScriptRuntime.FunctionClass || this.types[3] != Boolean.TYPE) {
               throw Context.reportRuntimeError1("msg.varargs.ctor", var4);
            }

            this.parmsLength = -2;
         } else {
            if (!this.isStatic || this.types[0] != (class$org$mozilla$javascript$Context != null ? class$org$mozilla$javascript$Context : (class$org$mozilla$javascript$Context = class$("org.mozilla.javascript.Context"))) || this.types[1] != ScriptRuntime.ScriptableClass || this.types[2].getComponentType() != ScriptRuntime.ObjectClass || this.types[3] != ScriptRuntime.FunctionClass) {
               throw Context.reportRuntimeError1("msg.varargs.fun", var4);
            }

            this.parmsLength = -1;
         }

         var5 = 1;
      }

      this.lengthPropertyValue = (short)var5;
      this.hasVoidReturn = this.method != null && this.method.getReturnType() == Void.TYPE;
      super.argCount = (short)var5;
      this.setParentScope(var3);
      this.setPrototype(ScriptableObject.getFunctionPrototype(var3));
      Context var8 = Context.getCurrentContext();
      this.useDynamicScope = var8 != null && var8.hasCompileFunctionsWithDynamicScope();
   }

   public void addAsConstructor(Scriptable var1, Scriptable var2) {
      this.setParentScope(var1);
      this.setPrototype(ScriptableObject.getFunctionPrototype(var1));
      this.setImmunePrototypeProperty(var2);
      var2.setParentScope(this);
      boolean var3 = true;
      ScriptableObject.defineProperty(var2, "constructor", this, 7);
      String var4 = var2.getClassName();
      ScriptableObject.defineProperty(var1, var4, this, 2);
      this.setParentScope(var1);
   }

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      if (this.parmsLength < 0) {
         return this.callVarargs(var1, var3, var4, false);
      } else {
         if (!this.isStatic) {
            label82: {
               Class var5 = this.method != null ? this.method.getDeclaringClass() : this.ctor.getDeclaringClass();

               do {
                  if (var5.isInstance(var3)) {
                     break label82;
                  }

                  var3 = var3.getPrototype();
               } while(var3 != null && this.useDynamicScope);

               throw NativeGlobal.typeError1("msg.incompat.call", super.functionName, var2);
            }
         }

         int var6;
         Object[] var11;
         if (this.parmsLength == var4.length) {
            var11 = var4;
            var6 = this.types == null ? this.parmsLength : 0;
         } else {
            var11 = new Object[this.parmsLength];
            var6 = 0;
         }

         Object var7;
         while(var6 < this.parmsLength) {
            var7 = var6 < var4.length ? var4[var6] : Undefined.instance;
            if (this.types != null) {
               var7 = convertArg(this, var7, this.types[var6]);
            }

            var11[var6] = var7;
            ++var6;
         }

         try {
            var7 = this.method == null ? this.ctor.newInstance(var11) : this.doInvoke(var3, var11);
            return this.hasVoidReturn ? Undefined.instance : var7;
         } catch (InvocationTargetException var8) {
            throw JavaScriptException.wrapException(var2, var8);
         } catch (IllegalAccessException var9) {
            throw WrappedException.wrapException(var9);
         } catch (InstantiationException var10) {
            throw WrappedException.wrapException(var10);
         }
      }
   }

   private Object callVarargs(Context var1, Scriptable var2, Object[] var3, boolean var4) throws JavaScriptException {
      try {
         if (this.parmsLength == -1) {
            Object[] var11 = new Object[]{var1, var2, var3, this};
            Object var13 = this.doInvoke((Object)null, var11);
            return this.hasVoidReturn ? Undefined.instance : var13;
         } else {
            Boolean var5 = var4 ? Boolean.TRUE : Boolean.FALSE;
            Object[] var12 = new Object[]{var1, var3, this, var5};
            return this.method == null ? this.ctor.newInstance(var12) : this.doInvoke((Object)null, var12);
         }
      } catch (InvocationTargetException var8) {
         Throwable var6 = var8.getTargetException();
         if (var6 instanceof EvaluatorException) {
            throw (EvaluatorException)var6;
         } else if (var6 instanceof EcmaError) {
            throw (EcmaError)var6;
         } else {
            Object var7 = var2 == null ? this : var2;
            throw JavaScriptException.wrapException((Scriptable)var7, var6);
         }
      } catch (IllegalAccessException var9) {
         throw WrappedException.wrapException(var9);
      } catch (InstantiationException var10) {
         throw WrappedException.wrapException(var10);
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

   public Scriptable construct(Context var1, Scriptable var2, Object[] var3) throws JavaScriptException {
      Scriptable var4;
      if (this.method != null && this.parmsLength != -2) {
         if (this.method != null && !this.isStatic) {
            try {
               var4 = (Scriptable)this.method.getDeclaringClass().newInstance();
            } catch (IllegalAccessException var6) {
               throw WrappedException.wrapException(var6);
            } catch (InstantiationException var7) {
               throw WrappedException.wrapException(var7);
            }

            var4.setPrototype(this.getClassPrototype());
            var4.setParentScope(this.getParentScope());
            Object var8 = this.call(var1, var2, var4, var3);
            return var8 != null && var8 != Undefined.instance && var8 instanceof Scriptable ? (Scriptable)var8 : var4;
         } else {
            return super.construct(var1, var2, var3);
         }
      } else {
         if (this.method != null) {
            var4 = (Scriptable)this.callVarargs(var1, (Scriptable)null, var3, true);
         } else {
            var4 = (Scriptable)this.call(var1, var2, (Scriptable)null, var3);
         }

         if (var4.getPrototype() == null) {
            var4.setPrototype(this.getClassPrototype());
         }

         if (var4.getParentScope() == null) {
            Scriptable var5 = this.getParentScope();
            if (var4 != var5) {
               var4.setParentScope(var5);
            }
         }

         return var4;
      }
   }

   public static Object convertArg(Scriptable var0, Object var1, Class var2) {
      if (var2 == ScriptRuntime.StringClass) {
         return ScriptRuntime.toString(var1);
      } else if (var2 != ScriptRuntime.IntegerClass && var2 != Integer.TYPE) {
         if (var2 != ScriptRuntime.BooleanClass && var2 != Boolean.TYPE) {
            if (var2 != ScriptRuntime.DoubleClass && var2 != Double.TYPE) {
               if (var2 == ScriptRuntime.ScriptableClass) {
                  return ScriptRuntime.toObject(var0, var1);
               } else if (var2 == ScriptRuntime.ObjectClass) {
                  return var1;
               } else {
                  throw Context.reportRuntimeError1("msg.cant.convert", var2.getName());
               }
            } else {
               return new Double(ScriptRuntime.toNumber(var1));
            }
         } else {
            return ScriptRuntime.toBoolean(var1) ? Boolean.TRUE : Boolean.FALSE;
         }
      } else {
         return new Integer(ScriptRuntime.toInt32(var1));
      }
   }

   private final Object doInvoke(Object var1, Object[] var2) throws IllegalAccessException, InvocationTargetException {
      Invoker var3 = invokerMaster;
      if (var3 != null) {
         if (this.invoker == null) {
            this.invoker = var3.createInvoker(this.method, this.types);
         }

         try {
            return this.invoker.invoke(var1, var2);
         } catch (RuntimeException var5) {
            throw new InvocationTargetException(var5);
         }
      } else {
         return this.method.invoke(var1, var2);
      }
   }

   public static Method[] findMethods(Class var0, String var1) {
      return findMethods(getMethodList(var0), var1);
   }

   static Method[] findMethods(Method[] var0, String var1) {
      Vector var2 = null;
      Method var3 = null;

      for(int var4 = 0; var4 < var0.length; ++var4) {
         if (var0[var4] != null && var0[var4].getName().equals(var1)) {
            if (var3 == null) {
               var3 = var0[var4];
            } else {
               if (var2 == null) {
                  var2 = new Vector(5);
                  var2.addElement(var3);
               }

               var2.addElement(var0[var4]);
            }
         }
      }

      Method[] var5;
      if (var2 == null) {
         if (var3 == null) {
            return null;
         } else {
            var5 = new Method[]{var3};
            return var5;
         }
      } else {
         var5 = new Method[var2.size()];
         var2.copyInto(var5);
         return var5;
      }
   }

   public int getLength() {
      return this.lengthPropertyValue;
   }

   static Method[] getMethodList(Class var0) {
      Method[] var1 = methodsCache;
      if (var1 != null && var1[0].getDeclaringClass() == var0) {
         return var1;
      } else {
         Method[] var2 = null;

         try {
            if (!sawSecurityException) {
               var2 = var0.getDeclaredMethods();
            }
         } catch (SecurityException var8) {
            sawSecurityException = true;
         }

         if (var2 == null) {
            var2 = var0.getMethods();
         }

         int var3 = 0;

         for(int var4 = 0; var4 < var2.length; ++var4) {
            label60: {
               boolean var10000;
               if (sawSecurityException) {
                  if (var2[var4].getDeclaringClass() != var0) {
                     break label60;
                  }

                  var10000 = false;
               } else {
                  var10000 = Modifier.isPublic(var2[var4].getModifiers()) ^ true;
               }

               if (!var10000) {
                  ++var3;
                  continue;
               }
            }

            var2[var4] = null;
         }

         Method[] var5 = new Method[var3];
         int var6 = 0;

         for(int var7 = 0; var7 < var2.length; ++var7) {
            if (var2[var7] != null) {
               var5[var6++] = var2[var7];
            }
         }

         if (var5.length > 0 && Context.isCachingEnabled) {
            methodsCache = var5;
         }

         return var5;
      }
   }

   boolean isVarArgsConstructor() {
      return this.parmsLength == -2;
   }

   boolean isVarArgsMethod() {
      return this.parmsLength == -1;
   }

   private static Invoker newInvokerMaster() {
      try {
         Class var0 = ScriptRuntime.loadClassName("org.mozilla.javascript.optimizer.InvokerImpl");
         return (Invoker)var0.newInstance();
      } catch (ClassNotFoundException var1) {
      } catch (IllegalAccessException var2) {
      } catch (InstantiationException var3) {
      } catch (SecurityException var4) {
      }

      return null;
   }

   static void setCachingEnabled(boolean var0) {
      if (!var0) {
         methodsCache = null;
         invokerMaster = null;
      } else if (invokerMaster == null) {
         invokerMaster = newInvokerMaster();
      }

   }

   public void setLength(short var1) {
      this.lengthPropertyValue = var1;
   }
}
