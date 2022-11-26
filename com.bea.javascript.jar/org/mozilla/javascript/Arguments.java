package org.mozilla.javascript;

class Arguments extends ScriptableObject {
   private NativeCall activation;
   private Object[] args;
   private boolean hasCaller;

   public Arguments(NativeCall var1) {
      this.activation = var1;
      Scriptable var2 = var1.getParentScope();
      this.setParentScope(var2);
      this.setPrototype(ScriptableObject.getObjectPrototype(var2));
      this.args = var1.getOriginalArguments();
      int var3 = this.args.length;
      NativeFunction var4 = var1.funObj;
      this.defineProperty("length", (Object)(new Integer(var3)), 2);
      this.defineProperty("callee", (Object)var4, 2);
      this.hasCaller = var1.funObj.version <= 130 && var1.funObj.version != 0;
   }

   public void delete(int var1) {
      if (var1 >= 0 && var1 < this.args.length) {
         NativeFunction var2 = this.activation.funObj;
         if (var1 < var2.argCount) {
            this.activation.delete(var2.argNames[var1]);
         } else {
            this.args[var1] = Undefined.instance;
         }
      }

   }

   public void delete(String var1) {
      if (var1.equals("caller")) {
         this.hasCaller = false;
      }

      super.delete(var1);
   }

   public Object get(int var1, Scriptable var2) {
      if (var1 >= 0 && var1 < this.args.length) {
         NativeFunction var3 = this.activation.funObj;
         return var1 < var3.argCount ? this.activation.get(var3.argNames[var1], this.activation) : this.args[var1];
      } else {
         return super.get(var1, var2);
      }
   }

   public Object get(String var1, Scriptable var2) {
      if (this.hasCaller && var1.equals("caller")) {
         NativeCall var3 = this.activation.caller;
         return var3 != null && var3.originalArgs != null ? var3.get("arguments", var3) : null;
      } else {
         return super.get(var1, var2);
      }
   }

   public String getClassName() {
      return "Arguments";
   }

   public boolean has(int var1, Scriptable var2) {
      Object[] var3 = this.activation.getOriginalArguments();
      return var1 >= 0 && var1 < var3.length || super.has(var1, var2);
   }

   public boolean has(String var1, Scriptable var2) {
      return this.hasCaller && var1.equals("caller") || super.has(var1, var2);
   }

   public void put(int var1, Scriptable var2, Object var3) {
      if (var1 >= 0 && var1 < this.args.length) {
         NativeFunction var4 = this.activation.funObj;
         if (var1 < var4.argCount) {
            this.activation.put(var4.argNames[var1], this.activation, var3);
         } else {
            this.args[var1] = var3;
         }

      } else {
         super.put(var1, var2, var3);
      }
   }

   public void put(String var1, Scriptable var2, Object var3) {
      if (var1.equals("caller")) {
         this.hasCaller = false;
      }

      super.put(var1, var2, var3);
   }
}
