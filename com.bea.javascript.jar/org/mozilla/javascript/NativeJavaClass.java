package org.mozilla.javascript;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Hashtable;

public class NativeJavaClass extends NativeJavaObject implements Function {
   private Hashtable fieldAndMethods;
   private Scriptable parent;

   public NativeJavaClass(Scriptable var1, Class var2) {
      super(var1, var2, (JavaMembers)JavaMembers.lookupClass(var1, var2, var2));
      this.fieldAndMethods = super.members.getFieldAndMethodsObjects(this, super.javaObject, true);
   }

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      if (var4.length == 1 && var4[0] instanceof Scriptable) {
         Class var5 = this.getClassObject();
         Scriptable var6 = (Scriptable)var4[0];

         do {
            if (var6 instanceof Wrapper) {
               Object var7 = ((Wrapper)var6).unwrap();
               if (var5.isInstance(var7)) {
                  return var6;
               }
            }

            var6 = var6.getPrototype();
         } while(var6 != null);
      }

      return this.construct(var1, var2, var4);
   }

   public Scriptable construct(Context var1, Scriptable var2, Object[] var3) throws JavaScriptException {
      Class var4 = this.getClassObject();
      int var5 = var4.getModifiers();
      String var9;
      if (!Modifier.isInterface(var5) && !Modifier.isAbstract(var5)) {
         Constructor[] var12 = super.members.getConstructors();
         Member var13 = NativeJavaMethod.findFunction(var12, var3);
         Constructor var14 = (Constructor)var13;
         if (var14 == null) {
            var9 = NativeJavaMethod.scriptSignature(var3);
            throw Context.reportRuntimeError2("msg.no.java.ctor", var4.getName(), var9);
         } else {
            return constructSpecific(var1, var2, this, var14, var3);
         }
      } else {
         Scriptable var6 = ScriptableObject.getTopLevelScope(this);
         String var7 = "";

         try {
            Object var8 = var6.get("JavaAdapter", var6);
            if (var8 != Scriptable.NOT_FOUND) {
               Function var15 = (Function)var8;
               Object[] var10 = new Object[]{this, var3[0]};
               return var15.construct(var1, var6, var10);
            }
         } catch (Exception var11) {
            var9 = var11.getMessage();
            if (var9 != null) {
               var7 = var9;
            }
         }

         throw Context.reportRuntimeError2("msg.cant.instantiate", var7, var4.getName());
      }
   }

   public static Scriptable constructSpecific(Context var0, Scriptable var1, Scriptable var2, Constructor var3, Object[] var4) throws JavaScriptException {
      Scriptable var5 = ScriptableObject.getTopLevelScope(var2);
      Class var6 = var3.getDeclaringClass();
      Class[] var7 = var3.getParameterTypes();

      for(int var8 = 0; var8 < var4.length; ++var8) {
         var4[var8] = NativeJavaObject.coerceType(var7[var8], var4[var8]);
      }

      try {
         return (Scriptable)NativeJavaObject.wrap(var5, var3.newInstance(var4), var6);
      } catch (InstantiationException var12) {
         throw Context.reportRuntimeError2("msg.cant.instantiate", var12.getMessage(), var6.getName());
      } catch (IllegalArgumentException var13) {
         String var10 = NativeJavaMethod.scriptSignature(var4);
         String var11 = var3.toString();
         throw Context.reportRuntimeError3("msg.bad.ctor.sig", var13.getMessage(), var11, var10);
      } catch (InvocationTargetException var14) {
         throw JavaScriptException.wrapException(var1, var14);
      } catch (IllegalAccessException var15) {
         throw Context.reportRuntimeError1("msg.java.internal.private", var15.getMessage());
      }
   }

   public Object get(String var1, Scriptable var2) {
      if (var1.equals("prototype")) {
         return null;
      } else {
         Object var3 = Scriptable.NOT_FOUND;
         if (this.fieldAndMethods != null) {
            var3 = this.fieldAndMethods.get(var1);
            if (var3 != null) {
               return var3;
            }
         }

         if (super.members.has(var1, true)) {
            var3 = super.members.get(this, var1, super.javaObject, true);
         } else {
            try {
               String var4 = this.getClassObject().getName() + '$' + var1;
               Class var5 = ScriptRuntime.loadClassName(var4);
               NativeJavaClass var6 = wrap(ScriptableObject.getTopLevelScope(this), var5);
               var6.setParentScope(this);
               var3 = var6;
            } catch (ClassNotFoundException var7) {
               throw super.members.reportMemberNotFound(var1);
            } catch (IllegalArgumentException var8) {
               throw super.members.reportMemberNotFound(var1);
            }
         }

         return var3;
      }
   }

   public String getClassName() {
      return "JavaClass";
   }

   public Class getClassObject() {
      return (Class)super.unwrap();
   }

   public Object getDefaultValue(Class var1) {
      if (var1 != null && var1 != ScriptRuntime.StringClass) {
         if (var1 == ScriptRuntime.BooleanClass) {
            return Boolean.TRUE;
         } else {
            return var1 == ScriptRuntime.NumberClass ? ScriptRuntime.NaNobj : this;
         }
      } else {
         return this.toString();
      }
   }

   public Object[] getIds() {
      return super.members.getIds(true);
   }

   public boolean has(String var1, Scriptable var2) {
      return super.members.has(var1, true);
   }

   public boolean hasInstance(Scriptable var1) {
      if (var1 instanceof Wrapper && !(var1 instanceof NativeJavaClass)) {
         Object var2 = ((Wrapper)var1).unwrap();
         return this.getClassObject().isInstance(var2);
      } else {
         return false;
      }
   }

   public void put(String var1, Scriptable var2, Object var3) {
      super.members.put(this, var1, super.javaObject, var3, true);
   }

   public String toString() {
      return "[JavaClass " + this.getClassObject().getName() + "]";
   }

   public static NativeJavaClass wrap(Scriptable var0, Class var1) {
      return new NativeJavaClass(var0, var1);
   }
}
