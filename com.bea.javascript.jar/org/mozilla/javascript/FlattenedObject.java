package org.mozilla.javascript;

import java.util.Enumeration;
import java.util.Hashtable;

/** @deprecated */
public class FlattenedObject {
   private Scriptable obj;

   /** @deprecated */
   public FlattenedObject(Scriptable var1) {
      this.obj = var1;
   }

   /** @deprecated */
   public Object call(Context var1, Scriptable var2, Object[] var3) throws NotAFunctionException, JavaScriptException {
      if (!(this.obj instanceof Function)) {
         throw new NotAFunctionException();
      } else {
         return ScriptRuntime.call(var1, this.obj, var2, var3, (Function)this.obj);
      }
   }

   /** @deprecated */
   public Object callMethod(Object var1, Object[] var2) throws PropertyException, NotAFunctionException, JavaScriptException {
      if (!this.hasProperty(var1)) {
         throw PropertyException.withMessage0("msg.prop.not.found");
      } else {
         Object var3 = this.getProperty(var1);
         if (var3 instanceof FlattenedObject) {
            return ((FlattenedObject)var3).call(Context.getContext(), this.obj, var2);
         } else {
            throw new NotAFunctionException();
         }
      }
   }

   /** @deprecated */
   public Scriptable construct(Context var1, Object[] var2) throws NotAFunctionException, JavaScriptException {
      if (!(this.obj instanceof Function)) {
         throw new NotAFunctionException();
      } else {
         return ScriptRuntime.newObject(var1, (Object)this.obj, (Object[])var2, (Scriptable)null);
      }
   }

   /** @deprecated */
   public boolean deleteProperty(Object var1) {
      String var2 = ScriptRuntime.getStringId(var1);
      if (var2 == null) {
         int var5 = ScriptRuntime.getIntId(var1);
         Scriptable var4 = getBase(this.obj, var5);
         if (var4 == null) {
            return true;
         } else {
            var4.delete(var5);
            return var4.has(var5, var4) ^ true;
         }
      } else {
         Scriptable var3 = getBase(this.obj, var2);
         if (var3 == null) {
            return true;
         } else {
            var3.delete(var2);
            return var3.has(var2, var3) ^ true;
         }
      }
   }

   private static Scriptable getBase(Scriptable var0, int var1) {
      for(Scriptable var2 = var0; var2 != null; var2 = var2.getPrototype()) {
         if (var2.has(var1, var0)) {
            return var2;
         }
      }

      return null;
   }

   private static Scriptable getBase(Scriptable var0, String var1) {
      for(Scriptable var2 = var0; var2 != null; var2 = var2.getPrototype()) {
         if (var2.has(var1, var0)) {
            return var2;
         }
      }

      return null;
   }

   /** @deprecated */
   public Object[] getIds() {
      Hashtable var1 = new Hashtable(11);

      for(Scriptable var2 = this.obj; var2 != null; var2 = var2.getPrototype()) {
         Object[] var3 = var2.getIds();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            var1.put(var3[var4], Boolean.TRUE);
         }
      }

      Enumeration var7 = var1.keys();
      Object[] var5 = new Object[var1.size()];

      Object var8;
      for(int var6 = 0; var7.hasMoreElements(); var5[var6++] = var8) {
         var8 = var7.nextElement();
      }

      return var5;
   }

   /** @deprecated */
   public Scriptable getObject() {
      return this.obj;
   }

   /** @deprecated */
   public Object getProperty(Object var1) {
      String var2 = ScriptRuntime.getStringId(var1);
      int var3 = var2 == null ? ScriptRuntime.getIntId(var1) : 0;
      Scriptable var4 = this.obj;

      do {
         Object var5 = var2 == null ? var4.get(var3, this.obj) : var4.get(var2, this.obj);
         if (var5 != Scriptable.NOT_FOUND) {
            if (var5 instanceof Scriptable) {
               return new FlattenedObject((Scriptable)var5);
            }

            return var5;
         }

         var4 = var4.getPrototype();
      } while(var4 != null);

      return Undefined.instance;
   }

   /** @deprecated */
   public boolean hasProperty(Object var1) {
      String var2 = ScriptRuntime.toString(var1);
      String var3 = ScriptRuntime.getStringId(var2);
      if (var3 == null) {
         return getBase(this.obj, ScriptRuntime.getIntId(var2)) != null;
      } else {
         return getBase(this.obj, var3) != null;
      }
   }

   /** @deprecated */
   public void putProperty(Object var1, Object var2) {
      String var3 = ScriptRuntime.getStringId(var1);
      if (var2 instanceof FlattenedObject) {
         var2 = ((FlattenedObject)var2).getObject();
      }

      Scriptable var4;
      if (var3 == null) {
         int var5 = ScriptRuntime.getIntId(var1);
         var4 = getBase(this.obj, var5);
         if (var4 == null) {
            var4 = this.obj;
         }

         var4.put(var5, this.obj, var2);
      } else {
         var4 = getBase(this.obj, var3);
         if (var4 == null) {
            var4 = this.obj;
         }

         var4.put(var3, this.obj, var2);
      }
   }
}
