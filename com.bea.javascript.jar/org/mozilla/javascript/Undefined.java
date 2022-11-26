package org.mozilla.javascript;

public class Undefined implements Scriptable {
   public static final Scriptable instance = new Undefined();

   public void delete(int var1) {
      throw this.reportError();
   }

   public void delete(String var1) {
      throw this.reportError();
   }

   public Object get(int var1, Scriptable var2) {
      throw this.reportError();
   }

   public Object get(String var1, Scriptable var2) {
      throw this.reportError();
   }

   public short getAttributes(int var1, Scriptable var2) {
      throw this.reportError();
   }

   public short getAttributes(String var1, Scriptable var2) {
      throw this.reportError();
   }

   public String getClassName() {
      return "undefined";
   }

   public Object getDefaultValue(Class var1) {
      if (var1 == ScriptRuntime.StringClass) {
         return "undefined";
      } else if (var1 == ScriptRuntime.NumberClass) {
         return ScriptRuntime.NaNobj;
      } else {
         return var1 == ScriptRuntime.BooleanClass ? Boolean.FALSE : this;
      }
   }

   public Object[] getIds() {
      throw this.reportError();
   }

   public Scriptable getParentScope() {
      throw this.reportError();
   }

   public Scriptable getPrototype() {
      throw this.reportError();
   }

   public boolean has(int var1, Scriptable var2) {
      return false;
   }

   public boolean has(String var1, Scriptable var2) {
      return false;
   }

   public boolean hasInstance(Scriptable var1) {
      throw this.reportError();
   }

   public boolean instanceOf(Scriptable var1) {
      return false;
   }

   public void put(int var1, Scriptable var2, Object var3) {
      throw this.reportError();
   }

   public void put(String var1, Scriptable var2, Object var3) {
      throw this.reportError();
   }

   private RuntimeException reportError() {
      return Context.reportRuntimeError0("msg.undefined");
   }

   public void setAttributes(int var1, Scriptable var2, short var3) {
      throw this.reportError();
   }

   public void setAttributes(String var1, Scriptable var2, short var3) {
      throw this.reportError();
   }

   public void setParentScope(Scriptable var1) {
      throw this.reportError();
   }

   public void setPrototype(Scriptable var1) {
      throw this.reportError();
   }
}
