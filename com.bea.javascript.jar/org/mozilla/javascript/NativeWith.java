package org.mozilla.javascript;

public class NativeWith implements Scriptable, IdFunctionMaster {
   private static final int Id_constructor = 1;
   private Scriptable prototype;
   private Scriptable parent;
   private Scriptable constructor;
   private boolean prototypeFlag;

   public NativeWith() {
   }

   public NativeWith(Scriptable var1, Scriptable var2) {
      this.parent = var1;
      this.prototype = var2;
   }

   public void delete(int var1) {
      this.prototype.delete(var1);
   }

   public void delete(String var1) {
      this.prototype.delete(var1);
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      if (this.prototypeFlag && var1 == 1) {
         throw Context.reportRuntimeError1("msg.cant.call.indirect", "With");
      } else {
         throw IdFunction.onBadMethodId(this, var1);
      }
   }

   public Object get(int var1, Scriptable var2) {
      if (var2 == this) {
         var2 = this.prototype;
      }

      return this.prototype.get(var1, var2);
   }

   public Object get(String var1, Scriptable var2) {
      if (var2 == this) {
         var2 = this.prototype;
      }

      return this.prototype.get(var1, var2);
   }

   public String getClassName() {
      return "With";
   }

   public Object getDefaultValue(Class var1) {
      return this.prototype.getDefaultValue(var1);
   }

   public Object[] getIds() {
      return this.prototype.getIds();
   }

   public Scriptable getParentScope() {
      return this.parent;
   }

   public Scriptable getPrototype() {
      return this.prototype;
   }

   public boolean has(int var1, Scriptable var2) {
      if (var2 == this) {
         var2 = this.prototype;
      }

      return this.prototype.has(var1, var2);
   }

   public boolean has(String var1, Scriptable var2) {
      if (var2 == this) {
         var2 = this.prototype;
      }

      return this.prototype.has(var1, var2);
   }

   public boolean hasInstance(Scriptable var1) {
      return this.prototype.hasInstance(var1);
   }

   public static void init(Context var0, Scriptable var1, boolean var2) {
      NativeWith var3 = new NativeWith();
      var3.prototypeFlag = true;
      IdFunction var4 = new IdFunction(var3, "constructor", 1);
      var4.initAsConstructor(var1, var3);
      if (var2) {
         var4.sealObject();
      }

      var3.setParentScope(var4);
      var3.setPrototype(ScriptableObject.getObjectPrototype(var1));
      ScriptableObject.defineProperty(var1, "With", var4, 2);
   }

   public int methodArity(int var1) {
      return this.prototypeFlag && var1 == 1 ? 0 : -1;
   }

   public static Object newWithSpecial(Context var0, Object[] var1, Function var2, boolean var3) {
      if (!var3) {
         throw Context.reportRuntimeError1("msg.only.from.new", "With");
      } else {
         ScriptRuntime.checkDeprecated(var0, "With");
         Scriptable var4 = ScriptableObject.getTopLevelScope(var2);
         NativeWith var5 = new NativeWith();
         var5.setPrototype(var1.length == 0 ? ScriptableObject.getClassPrototype(var4, "Object") : ScriptRuntime.toObject(var4, var1[0]));
         var5.setParentScope(var4);
         return var5;
      }
   }

   public void put(int var1, Scriptable var2, Object var3) {
      if (var2 == this) {
         var2 = this.prototype;
      }

      this.prototype.put(var1, var2, var3);
   }

   public void put(String var1, Scriptable var2, Object var3) {
      if (var2 == this) {
         var2 = this.prototype;
      }

      this.prototype.put(var1, var2, var3);
   }

   public void setParentScope(Scriptable var1) {
      this.parent = var1;
   }

   public void setPrototype(Scriptable var1) {
      this.prototype = var1;
   }
}
