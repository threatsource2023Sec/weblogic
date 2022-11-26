package org.mozilla.javascript;

public class Delegator implements Function {
   protected Scriptable obj = null;

   public Delegator() {
   }

   public Delegator(Scriptable var1) {
      this.obj = var1;
   }

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      return ((Function)this.obj).call(var1, var2, var3, var4);
   }

   public Scriptable construct(Context var1, Scriptable var2, Object[] var3) throws JavaScriptException {
      if (this.obj == null) {
         try {
            Delegator var4 = (Delegator)this.getClass().newInstance();
            var4.setDelegee((Scriptable)var3[0]);
            return var4;
         } catch (Exception var5) {
            var5.printStackTrace();
            System.exit(0);
            return null;
         }
      } else {
         return ((Function)this.obj).construct(var1, var2, var3);
      }
   }

   public void delete(int var1) {
      this.obj.delete(var1);
   }

   public void delete(String var1) {
      this.obj.delete(var1);
   }

   public Object get(int var1, Scriptable var2) {
      return this.obj.get(var1, var2);
   }

   public Object get(String var1, Scriptable var2) {
      return this.obj.get(var1, var2);
   }

   public String getClassName() {
      return this.obj.getClassName();
   }

   public Object getDefaultValue(Class var1) {
      return var1 != null && var1 != ScriptRuntime.ScriptableClass && var1 != ScriptRuntime.FunctionClass ? this.obj.getDefaultValue(var1) : this;
   }

   public Scriptable getDelegee() {
      return this.obj;
   }

   public Object[] getIds() {
      return this.obj.getIds();
   }

   public Scriptable getParentScope() {
      return this.obj.getParentScope();
   }

   public Scriptable getPrototype() {
      return this.obj.getPrototype();
   }

   public boolean has(int var1, Scriptable var2) {
      return this.obj.has(var1, var2);
   }

   public boolean has(String var1, Scriptable var2) {
      return this.obj.has(var1, var2);
   }

   public boolean hasInstance(Scriptable var1) {
      return this.obj.hasInstance(var1);
   }

   public void put(int var1, Scriptable var2, Object var3) {
      this.obj.put(var1, var2, var3);
   }

   public void put(String var1, Scriptable var2, Object var3) {
      this.obj.put(var1, var2, var3);
   }

   public void setDelegee(Scriptable var1) {
      this.obj = var1;
   }

   public void setParentScope(Scriptable var1) {
      this.obj.setParentScope(var1);
   }

   public void setPrototype(Scriptable var1) {
      this.obj.setPrototype(var1);
   }
}
