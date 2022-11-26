package org.mozilla.javascript;

import java.lang.reflect.Method;

public class NativeJavaPackage extends ScriptableObject {
   static final String[] commonPackages = new String[]{"java.lang", "java.lang.reflect", "java.io", "java.math", "java.util", "java.util.zip", "java.text", "java.text.resources", "java.applet"};
   private String packageName;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$NativeJavaPackage;

   public NativeJavaPackage(String var1) {
      this.packageName = var1;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   void forcePackage(String var1) {
      int var3 = var1.indexOf(46);
      if (var3 == -1) {
         var3 = var1.length();
      }

      String var4 = var1.substring(0, var3);
      Object var5 = super.get(var4, this);
      NativeJavaPackage var2;
      if (var5 != null && var5 instanceof NativeJavaPackage) {
         var2 = (NativeJavaPackage)var5;
      } else {
         String var6 = this.packageName.length() == 0 ? var4 : this.packageName + "." + var4;
         var2 = new NativeJavaPackage(var6);
         var2.setParentScope(this);
         var2.setPrototype(super.prototype);
         super.put(var4, this, var2);
      }

      if (var3 < var1.length()) {
         var2.forcePackage(var1.substring(var3 + 1));
      }

   }

   public Object get(int var1, Scriptable var2) {
      return Scriptable.NOT_FOUND;
   }

   public Object get(String var1, Scriptable var2) {
      return this.getPkgProperty(var1, var2, true);
   }

   public String getClassName() {
      return "JavaPackage";
   }

   public Object getDefaultValue(Class var1) {
      return this.toString();
   }

   synchronized Object getPkgProperty(String var1, Scriptable var2, boolean var3) {
      Object var4 = super.get(var1, var2);
      if (var4 != Scriptable.NOT_FOUND) {
         return var4;
      } else {
         String var5 = this.packageName.length() == 0 ? var1 : this.packageName + "." + var1;
         Context var6 = Context.getContext();
         SecuritySupport var7 = var6.getSecuritySupport();

         Object var8;
         try {
            if (var7 != null && !var7.visibleToScripts(var5)) {
               throw new ClassNotFoundException();
            }

            Class var11 = ScriptRuntime.loadClassName(var5);
            var8 = NativeJavaClass.wrap(ScriptableObject.getTopLevelScope(this), var11);
            ((Scriptable)var8).setParentScope(this);
            ((Scriptable)var8).setPrototype(super.prototype);
         } catch (ClassNotFoundException var10) {
            if (var3) {
               NativeJavaPackage var9 = new NativeJavaPackage(var5);
               var9.setParentScope(this);
               var9.setPrototype(super.prototype);
               var8 = var9;
            } else {
               var8 = null;
            }
         }

         if (var8 != null) {
            super.put(var1, var2, var8);
         }

         return var8;
      }
   }

   public boolean has(String var1, int var2, Scriptable var3) {
      return true;
   }

   public static Scriptable init(Scriptable var0) throws PropertyException {
      NativeJavaPackage var1 = new NativeJavaPackage("");
      var1.setPrototype(ScriptableObject.getObjectPrototype(var0));
      var1.setParentScope(var0);
      NativeJavaPackage var2 = (NativeJavaPackage)var1.get("java", var1);
      ScriptableObject var3 = (ScriptableObject)var0;
      var3.defineProperty("Packages", (Object)var1, 2);
      var3.defineProperty("java", (Object)var2, 2);

      for(int var4 = 0; var4 < commonPackages.length; ++var4) {
         var1.forcePackage(commonPackages[var4]);
      }

      Method[] var5 = FunctionObject.findMethods(class$org$mozilla$javascript$NativeJavaPackage != null ? class$org$mozilla$javascript$NativeJavaPackage : (class$org$mozilla$javascript$NativeJavaPackage = class$("org.mozilla.javascript.NativeJavaPackage")), "jsFunction_getClass");
      FunctionObject var6 = new FunctionObject("getClass", var5[0], var3);
      var3.defineProperty("getClass", (Object)var6, 2);
      return var1;
   }

   public static Scriptable jsFunction_getClass(Context var0, Scriptable var1, Object[] var2, Function var3) {
      if (var2.length > 0 && var2[0] instanceof Wrapper) {
         Scriptable var4 = ScriptableObject.getTopLevelScope(var1);
         Class var5 = ((Wrapper)var2[0]).unwrap().getClass();
         String var6 = "Packages." + var5.getName();
         int var7 = 0;

         while(true) {
            int var8 = var6.indexOf(46, var7);
            String var9 = var8 == -1 ? var6.substring(var7) : var6.substring(var7, var8);
            Object var10 = var4.get(var9, var4);
            if (!(var10 instanceof Scriptable)) {
               break;
            }

            var4 = (Scriptable)var10;
            if (var8 == -1) {
               return var4;
            }

            var7 = var8 + 1;
         }
      }

      throw Context.reportRuntimeError(Context.getMessage0("msg.not.java.obj"));
   }

   public void put(int var1, Scriptable var2, Object var3) {
      throw Context.reportRuntimeError0("msg.pkg.int");
   }

   public void put(String var1, Scriptable var2, Object var3) {
   }

   public String toString() {
      return "[JavaPackage " + this.packageName + "]";
   }
}
