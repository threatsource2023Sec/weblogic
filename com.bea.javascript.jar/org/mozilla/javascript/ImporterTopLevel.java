package org.mozilla.javascript;

public class ImporterTopLevel extends ScriptableObject {
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$ImporterTopLevel;

   /** @deprecated */
   public ImporterTopLevel() {
      this.init();
   }

   public ImporterTopLevel(Context var1) {
      var1.initStandardObjects(this);
      this.init();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public Object get(String var1, Scriptable var2) {
      Object var3 = super.get(var1, var2);
      if (var3 != Scriptable.NOT_FOUND) {
         return var3;
      } else if (var1.equals("_packages_")) {
         return var3;
      } else {
         Object var4 = ScriptableObject.getProperty(var2, "_packages_");
         if (var4 == Scriptable.NOT_FOUND) {
            return var3;
         } else {
            Context var5 = Context.enter();
            Object[] var6 = var5.getElements((Scriptable)var4);
            Context.exit();

            for(int var7 = 0; var7 < var6.length; ++var7) {
               NativeJavaPackage var8 = (NativeJavaPackage)var6[var7];
               Object var9 = var8.getPkgProperty(var1, var2, false);
               if (var9 != null && !(var9 instanceof NativeJavaPackage)) {
                  if (var3 != Scriptable.NOT_FOUND) {
                     throw Context.reportRuntimeError2("msg.ambig.import", var3.toString(), var9.toString());
                  }

                  var3 = var9;
               }
            }

            return var3;
         }
      }
   }

   public String getClassName() {
      return "global";
   }

   public static void importClass(Context var0, Scriptable var1, Object[] var2, Function var3) {
      for(int var4 = 0; var4 < var2.length; ++var4) {
         Object var5 = var2[var4];
         if (!(var5 instanceof NativeJavaClass)) {
            throw Context.reportRuntimeError1("msg.not.class", Context.toString(var5));
         }

         String var6 = ((NativeJavaClass)var5).getClassObject().getName();
         String var7 = var6.substring(var6.lastIndexOf(46) + 1);
         Object var8 = var1.get(var7, var1);
         if (var8 != Scriptable.NOT_FOUND && var8 != var5) {
            throw Context.reportRuntimeError1("msg.prop.defined", var7);
         }

         var1.put(var7, var1, var5);
      }

   }

   public static void importPackage(Context var0, Scriptable var1, Object[] var2, Function var3) {
      Object var5 = var1.get("_packages_", var1);
      Scriptable var4;
      if (var5 == Scriptable.NOT_FOUND) {
         var4 = var0.newArray(var1, 0);
         var1.put("_packages_", var1, var4);
      } else {
         var4 = (Scriptable)var5;
      }

      for(int var6 = 0; var6 < var2.length; ++var6) {
         Object var7 = var2[var6];
         if (!(var7 instanceof NativeJavaPackage)) {
            throw Context.reportRuntimeError1("msg.not.pkg", Context.toString(var7));
         }

         Object[] var8 = var0.getElements(var4);

         for(int var9 = 0; var9 < var8.length; ++var9) {
            if (var7 == var8[var9]) {
               var7 = null;
               break;
            }
         }

         if (var7 != null) {
            var4.put(var8.length, var4, var7);
         }
      }

   }

   private void init() {
      String[] var1 = new String[]{"importClass", "importPackage"};

      try {
         this.defineFunctionProperties(var1, class$org$mozilla$javascript$ImporterTopLevel != null ? class$org$mozilla$javascript$ImporterTopLevel : (class$org$mozilla$javascript$ImporterTopLevel = class$("org.mozilla.javascript.ImporterTopLevel")), 2);
      } catch (PropertyException var2) {
         throw new Error();
      }
   }
}
