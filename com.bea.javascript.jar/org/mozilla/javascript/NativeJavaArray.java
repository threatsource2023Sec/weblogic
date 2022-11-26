package org.mozilla.javascript;

import java.lang.reflect.Array;

public class NativeJavaArray extends NativeJavaObject {
   Object array;
   int length;
   Class cls;
   Scriptable prototype;

   public NativeJavaArray(Scriptable var1, Object var2) {
      super(var1, (Object)null, (Class)ScriptRuntime.ObjectClass);
      Class var3 = var2.getClass();
      if (!var3.isArray()) {
         throw new RuntimeException("Array expected");
      } else {
         this.array = var2;
         this.length = Array.getLength(var2);
         this.cls = var3.getComponentType();
      }
   }

   public Object get(int var1, Scriptable var2) {
      return var1 >= 0 && var1 < this.length ? NativeJavaObject.wrap(this, Array.get(this.array, var1), this.cls) : Undefined.instance;
   }

   public Object get(String var1, Scriptable var2) {
      if (var1.equals("length")) {
         return new Integer(this.length);
      } else {
         Object var3 = super.get(var1, var2);
         if (var3 == Scriptable.NOT_FOUND && !ScriptRuntime.hasProp(this.getPrototype(), var1)) {
            throw Context.reportRuntimeError2("msg.java.member.not.found", this.array.getClass().getName(), var1);
         } else {
            return var3;
         }
      }
   }

   public String getClassName() {
      return "JavaArray";
   }

   public Object getDefaultValue(Class var1) {
      if (var1 != null && var1 != ScriptRuntime.StringClass) {
         if (var1 == ScriptRuntime.BooleanClass) {
            return Boolean.TRUE;
         } else {
            return var1 == ScriptRuntime.NumberClass ? ScriptRuntime.NaNobj : this;
         }
      } else {
         return this.array.toString();
      }
   }

   public Object[] getIds() {
      Object[] var1 = new Object[this.length];
      int var2 = this.length;

      while(true) {
         --var2;
         if (var2 < 0) {
            return var1;
         }

         var1[var2] = new Integer(var2);
      }
   }

   public Scriptable getPrototype() {
      if (this.prototype == null) {
         this.prototype = ScriptableObject.getClassPrototype(this.getParentScope(), "Array");
      }

      return this.prototype;
   }

   public boolean has(int var1, Scriptable var2) {
      return var1 >= 0 && var1 < this.length;
   }

   public boolean has(String var1, Scriptable var2) {
      return var1.equals("length") || super.has(var1, var2);
   }

   public boolean hasInstance(Scriptable var1) {
      if (!(var1 instanceof Wrapper)) {
         return false;
      } else {
         Object var2 = ((Wrapper)var1).unwrap();
         return this.cls.isInstance(var2);
      }
   }

   public void put(int var1, Scriptable var2, Object var3) {
      if (var1 >= 0 && var1 < this.length) {
         Array.set(this.array, var1, NativeJavaObject.coerceType(this.cls, var3));
      } else {
         super.put(var1, var2, var3);
      }
   }

   public void put(String var1, Scriptable var2, Object var3) {
      if (!var1.equals("length")) {
         super.put(var1, var2, var3);
      }

   }

   public Object unwrap() {
      return this.array;
   }

   public static NativeJavaArray wrap(Scriptable var0, Object var1) {
      return new NativeJavaArray(var0, var1);
   }
}
