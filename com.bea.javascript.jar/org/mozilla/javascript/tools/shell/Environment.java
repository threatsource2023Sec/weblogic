package org.mozilla.javascript.tools.shell;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class Environment extends ScriptableObject {
   private Environment thePrototypeInstance = null;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$tools$shell$Environment;

   public Environment() {
      if (this.thePrototypeInstance == null) {
         this.thePrototypeInstance = this;
      }

   }

   public Environment(ScriptableObject var1) {
      this.setParentScope(var1);
      Object var2 = ScriptRuntime.getTopLevelProp(var1, "Environment");
      if (var2 != null && var2 instanceof Scriptable) {
         Scriptable var3 = (Scriptable)var2;
         this.setPrototype((Scriptable)var3.get("prototype", var3));
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

   private Object[] collectIds() {
      Properties var1 = System.getProperties();
      Enumeration var2 = var1.propertyNames();
      Vector var3 = new Vector();

      while(var2.hasMoreElements()) {
         var3.addElement(var2.nextElement());
      }

      Object[] var4 = new Object[var3.size()];
      var3.copyInto(var4);
      return var4;
   }

   public static void defineClass(ScriptableObject var0) {
      try {
         ScriptableObject.defineClass(var0, class$org$mozilla$javascript$tools$shell$Environment != null ? class$org$mozilla$javascript$tools$shell$Environment : (class$org$mozilla$javascript$tools$shell$Environment = class$("org.mozilla.javascript.tools.shell.Environment")));
      } catch (Exception var2) {
         throw new Error(var2.getMessage());
      }
   }

   public Object get(String var1, Scriptable var2) {
      if (this == this.thePrototypeInstance) {
         return super.get(var1, var2);
      } else {
         String var3 = System.getProperty(var1);
         return var3 != null ? ScriptRuntime.toObject(this.getParentScope(), var3) : Scriptable.NOT_FOUND;
      }
   }

   public Object[] getAllIds() {
      return this == this.thePrototypeInstance ? super.getAllIds() : this.collectIds();
   }

   public String getClassName() {
      return "Environment";
   }

   public Object[] getIds() {
      return this == this.thePrototypeInstance ? super.getIds() : this.collectIds();
   }

   public boolean has(String var1, Scriptable var2) {
      if (this == this.thePrototypeInstance) {
         return super.has(var1, var2);
      } else {
         return System.getProperty(var1) != null;
      }
   }

   public void put(String var1, Scriptable var2, Object var3) {
      if (this == this.thePrototypeInstance) {
         super.put(var1, var2, var3);
      } else {
         System.getProperties().put(var1, ScriptRuntime.toString(var3));
      }

   }
}
