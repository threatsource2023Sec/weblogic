package org.mozilla.javascript;

public class IdFunction extends BaseFunction {
   public static final int FUNCTION_ONLY = 0;
   public static final int CONSTRUCTOR_ONLY = 1;
   public static final int FUNCTION_AND_CONSTRUCTOR = 2;
   protected IdFunctionMaster master;
   protected int methodId;
   protected int functionType = 0;

   public IdFunction(IdFunctionMaster var1, String var2, int var3) {
      super.functionName = var2;
      this.master = var1;
      this.methodId = var3;
   }

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      return this.functionType != 1 ? this.master.execMethod(this.methodId, this, var1, var2, var3, var4) : Undefined.instance;
   }

   public Scriptable construct(Context var1, Scriptable var2, Object[] var3) throws JavaScriptException {
      if (this.functionType != 0) {
         Scriptable var4 = (Scriptable)this.master.execMethod(this.methodId, this, var1, var2, (Scriptable)null, var3);
         this.postConstruction(var4);
         return var4;
      } else {
         return Undefined.instance;
      }
   }

   public String decompile(Context var1, int var2, boolean var3) {
      StringBuffer var4 = new StringBuffer();
      if (!var3) {
         var4.append("function ");
         var4.append(this.getFunctionName());
         var4.append("() { ");
      }

      var4.append("[native code for ");
      if (this.master instanceof Scriptable) {
         Scriptable var5 = (Scriptable)this.master;
         var4.append(var5.getClassName());
         var4.append('.');
      }

      var4.append(this.getFunctionName());
      var4.append(", arity=");
      var4.append(this.getArity());
      var4.append(var3 ? "]\n" : "] }\n");
      return var4.toString();
   }

   public final int functionType() {
      return this.functionType;
   }

   public int getArity() {
      int var1 = this.master.methodArity(this.methodId);
      if (var1 < 0) {
         throw onBadMethodId(this.master, this.methodId);
      } else {
         return var1;
      }
   }

   public int getLength() {
      return this.getArity();
   }

   public Scriptable getPrototype() {
      Scriptable var1 = super.getPrototype();
      if (var1 == null) {
         var1 = ScriptableObject.getFunctionPrototype(this.getParentScope());
         this.setPrototype(var1);
      }

      return var1;
   }

   public void initAsConstructor(Scriptable var1, Scriptable var2) {
      this.setFunctionType(2);
      this.setParentScope(var1);
      this.setImmunePrototypeProperty(var2);
   }

   static RuntimeException onBadMethodId(IdFunctionMaster var0, int var1) {
      return new RuntimeException("BAD FUNCTION ID=" + var1 + " MASTER=" + var0);
   }

   private void postConstruction(Scriptable var1) {
      if (var1.getPrototype() == null) {
         var1.setPrototype(this.getClassPrototype());
      }

      if (var1.getParentScope() == null) {
         Scriptable var2 = this.getParentScope();
         if (var1 != var2) {
            var1.setParentScope(var2);
         }
      }

   }

   public void setFunctionType(int var1) {
      this.functionType = var1;
   }
}
