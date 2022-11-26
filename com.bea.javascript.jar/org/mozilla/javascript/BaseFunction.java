package org.mozilla.javascript;

public class BaseFunction extends IdScriptable implements Function {
   private static final int Id_length = 1;
   private static final int Id_arity = 2;
   private static final int Id_name = 3;
   private static final int Id_prototype = 4;
   private static final int Id_arguments = 5;
   private static final int MAX_INSTANCE_ID = 5;
   private static final int Id_constructor = 6;
   private static final int Id_toString = 7;
   private static final int Id_apply = 8;
   private static final int Id_call = 9;
   private static final int MAX_PROTOTYPE_ID = 9;
   protected String functionName;
   private Object prototypeProperty;
   private int prototypePropertyAttrs = 2;
   private boolean prototypeFlag;

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      return Undefined.instance;
   }

   public Scriptable construct(Context var1, Scriptable var2, Object[] var3) throws JavaScriptException {
      NativeObject var4 = new NativeObject();
      var4.setPrototype(this.getClassPrototype());
      var4.setParentScope(this.getParentScope());
      Object var5 = this.call(var1, var2, var4, var3);
      return (Scriptable)(var5 instanceof Scriptable && var5 != Undefined.instance ? (Scriptable)var5 : var4);
   }

   public String decompile(Context var1, int var2, boolean var3) {
      StringBuffer var4 = new StringBuffer();
      if (!var3) {
         var4.append("function ");
         var4.append(this.getFunctionName());
         var4.append("() {\n\t");
      }

      var4.append("[native code, arity=");
      var4.append(this.getArity());
      var4.append("]\n");
      if (!var3) {
         var4.append("}\n");
      }

      return var4.toString();
   }

   protected void deleteIdValue(int var1) {
      if (var1 == 4) {
         this.prototypeProperty = Scriptable.NOT_FOUND;
      } else {
         super.deleteIdValue(var1);
      }
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      if (this.prototypeFlag) {
         switch (var1) {
            case 6:
               return jsConstructor(var3, var4, var6);
            case 7:
               return jsFunction_toString(var3, var5, var6);
            case 8:
               return jsFunction_apply(var3, var4, var5, var6);
            case 9:
               return jsFunction_call(var3, var4, var5, var6);
         }
      }

      return super.execMethod(var1, var2, var3, var4, var5, var6);
   }

   protected void fillConstructorProperties(Context var1, IdFunction var2, boolean var3) {
      var2.setPrototype(this);
   }

   NativeCall getActivation(Context var1) {
      for(NativeCall var2 = var1.currentActivation; var2 != null; var2 = var2.caller) {
         if (var2.getFunctionObject() == this) {
            return var2;
         }
      }

      return null;
   }

   private Object getArguments() {
      NativeCall var1 = this.getActivation(Context.getContext());
      return var1 == null ? null : var1.get("arguments", var1);
   }

   public int getArity() {
      return 0;
   }

   public String getClassName() {
      return "Function";
   }

   protected Scriptable getClassPrototype() {
      Object var1 = this.getPrototypeProperty();
      if (var1 == null || !(var1 instanceof Scriptable) || var1 == Undefined.instance) {
         var1 = ScriptableObject.getClassPrototype(this, "Object");
      }

      return (Scriptable)var1;
   }

   public String getFunctionName() {
      if (this.functionName == null) {
         return "";
      } else {
         if (this.functionName.equals("anonymous")) {
            Context var1 = Context.getCurrentContext();
            if (var1 != null && var1.getLanguageVersion() == 120) {
               return "";
            }
         }

         return this.functionName;
      }
   }

   protected int getIdDefaultAttributes(int var1) {
      switch (var1) {
         case 1:
         case 2:
         case 3:
            return 7;
         case 4:
            return this.prototypePropertyAttrs;
         case 5:
            return 0;
         default:
            return super.getIdDefaultAttributes(var1);
      }
   }

   protected String getIdName(int var1) {
      switch (var1) {
         case 1:
            return "length";
         case 2:
            return "arity";
         case 3:
            return "name";
         case 4:
            return "prototype";
         case 5:
            return "arguments";
         default:
            if (this.prototypeFlag) {
               switch (var1) {
                  case 6:
                     return "constructor";
                  case 7:
                     return "toString";
                  case 8:
                     return "apply";
                  case 9:
                     return "call";
               }
            }

            return null;
      }
   }

   protected Object getIdValue(int var1) {
      switch (var1) {
         case 1:
            return this.wrap_int(this.getLength());
         case 2:
            return this.wrap_int(this.getArity());
         case 3:
            return this.getFunctionName();
         case 4:
            return this.getPrototypeProperty();
         case 5:
            return this.getArguments();
         default:
            return super.getIdValue(var1);
      }
   }

   public int getLength() {
      return 0;
   }

   private Object getPrototypeProperty() {
      Object var1 = this.prototypeProperty;
      if (var1 == null) {
         synchronized(this){}

         try {
            var1 = this.prototypeProperty;
            if (var1 == null) {
               this.setupDefaultPrototype();
               var1 = this.prototypeProperty;
            }
         } catch (Throwable var4) {
            throw var4;
         }
      } else if (var1 == IdScriptable.NULL_TAG) {
         var1 = null;
      }

      return var1;
   }

   protected boolean hasIdValue(int var1) {
      if (var1 == 4) {
         return this.prototypeProperty != Scriptable.NOT_FOUND;
      } else if (var1 == 5) {
         return this.getActivation(Context.getContext()) != null;
      } else {
         return super.hasIdValue(var1);
      }
   }

   public boolean hasInstance(Scriptable var1) {
      Object var2 = ScriptableObject.getProperty(this, "prototype");
      if (var2 instanceof Scriptable && var2 != Undefined.instance) {
         return ScriptRuntime.jsDelegatesTo(var1, (Scriptable)var2);
      } else {
         throw NativeGlobal.typeError1("msg.instanceof.bad.prototype", this.functionName, var1);
      }
   }

   static void init(Context var0, Scriptable var1, boolean var2) {
      BaseFunction var3 = new BaseFunction();
      var3.prototypeFlag = true;
      var3.functionName = "";
      var3.prototypePropertyAttrs = 7;
      var3.addAsPrototype(9, var0, var1, var2);
   }

   private static Object jsConstructor(Context var0, Scriptable var1, Object[] var2) {
      int var3 = var2.length;
      StringBuffer var4 = new StringBuffer();

      int var5;
      for(var5 = 0; var5 < var3 - 1; ++var5) {
         if (var5 > 0) {
            var4.append(',');
         }

         var4.append(ScriptRuntime.toString(var2[var5]));
      }

      String var6 = var3 == 0 ? "" : ScriptRuntime.toString(var2[var5]);
      String var7 = "function (" + var4.toString() + ") {" + var6 + "}";
      int[] var8 = new int[1];
      String var9 = Context.getSourcePositionFromStack(var8);
      if (var9 == null) {
         var9 = "<eval'ed string>";
         var8[0] = 1;
      }

      Object var10 = var0.getSecurityDomainForStackDepth(4);
      Scriptable var11 = ScriptableObject.getTopLevelScope(var1);
      int var12 = var0.getOptimizationLevel();
      var0.setOptimizationLevel(-1);

      NativeFunction var13;
      try {
         var13 = (NativeFunction)var0.compileFunction(var11, var7, var9, var8[0], var10);
      } finally {
         var0.setOptimizationLevel(var12);
      }

      var13.functionName = "anonymous";
      var13.setPrototype(ScriptableObject.getFunctionPrototype(var11));
      var13.setParentScope(var11);
      return var13;
   }

   private static Object jsFunction_apply(Context var0, Scriptable var1, Scriptable var2, Object[] var3) throws JavaScriptException {
      if (var3.length != 2) {
         return jsFunction_call(var0, var1, var2, var3);
      } else {
         Object var4 = var2.getDefaultValue(ScriptRuntime.FunctionClass);
         Scriptable var5 = var3[0] == null ? ScriptableObject.getTopLevelScope(var2) : ScriptRuntime.toObject(var1, var3[0]);
         Object[] var6;
         if (var3.length > 1) {
            if (!(var3[1] instanceof NativeArray) && !(var3[1] instanceof Arguments)) {
               throw NativeGlobal.typeError0("msg.arg.isnt.array", var2);
            }

            var6 = var0.getElements((Scriptable)var3[1]);
         } else {
            var6 = ScriptRuntime.emptyArgs;
         }

         return ScriptRuntime.call(var0, var4, var5, var6, var5);
      }
   }

   private static Object jsFunction_call(Context var0, Scriptable var1, Scriptable var2, Object[] var3) throws JavaScriptException {
      Object var4 = var2.getDefaultValue(ScriptRuntime.FunctionClass);
      Scriptable var5;
      if (var3.length == 0) {
         var5 = ScriptRuntime.toObject(var1, var4);
         Scriptable var7 = var5.getParentScope();
         return ScriptRuntime.call(var0, var4, var7, ScriptRuntime.emptyArgs, var7);
      } else {
         var5 = var3[0] == null ? ScriptableObject.getTopLevelScope(var2) : ScriptRuntime.toObject(var1, var3[0]);
         Object[] var6 = new Object[var3.length - 1];
         System.arraycopy(var3, 1, var6, 0, var6.length);
         return ScriptRuntime.call(var0, var4, var5, var6, var5);
      }
   }

   private static Object jsFunction_toString(Context var0, Scriptable var1, Object[] var2) {
      int var3 = ScriptRuntime.toInt32(var2, 0);
      Object var4 = var1.getDefaultValue(ScriptRuntime.FunctionClass);
      if (var4 instanceof BaseFunction) {
         return ((BaseFunction)var4).decompile(var0, var3, false);
      } else {
         throw NativeGlobal.typeError1("msg.incompat.call", "toString", var1);
      }
   }

   protected int mapNameToId(String var1) {
      byte var2 = 0;
      String var3 = null;
      switch (var1.length()) {
         case 4:
            var3 = "name";
            var2 = 3;
            break;
         case 5:
            var3 = "arity";
            var2 = 2;
            break;
         case 6:
            var3 = "length";
            var2 = 1;
         case 7:
         case 8:
         default:
            break;
         case 9:
            char var4 = var1.charAt(0);
            if (var4 == 'a') {
               var3 = "arguments";
               var2 = 5;
            } else if (var4 == 'p') {
               var3 = "prototype";
               var2 = 4;
            }
      }

      if (var3 != null && var3 != var1 && !var3.equals(var1)) {
         var2 = 0;
      }

      if (var2 == 0 && this.prototypeFlag) {
         var2 = 0;
         var3 = null;
         switch (var1.length()) {
            case 4:
               var3 = "call";
               var2 = 9;
               break;
            case 5:
               var3 = "apply";
               var2 = 8;
            case 6:
            case 7:
            case 9:
            case 10:
            default:
               break;
            case 8:
               var3 = "toString";
               var2 = 7;
               break;
            case 11:
               var3 = "constructor";
               var2 = 6;
         }

         if (var3 != null && var3 != var1 && !var3.equals(var1)) {
            var2 = 0;
         }

         return var2;
      } else {
         return var2;
      }
   }

   protected int maxInstanceId() {
      return 5;
   }

   public int methodArity(int var1) {
      if (this.prototypeFlag) {
         switch (var1) {
            case 6:
            case 7:
            case 8:
            case 9:
               return 1;
         }
      }

      return super.methodArity(var1);
   }

   protected void setIdValue(int var1, Object var2) {
      if (var1 == 4) {
         this.prototypeProperty = var2 != null ? var2 : IdScriptable.NULL_TAG;
      } else {
         super.setIdValue(var1, var2);
      }
   }

   public void setImmunePrototypeProperty(Object var1) {
      this.prototypeProperty = var1 != null ? var1 : IdScriptable.NULL_TAG;
      this.prototypePropertyAttrs = 7;
   }

   private void setupDefaultPrototype() {
      NativeObject var1 = new NativeObject();
      boolean var2 = true;
      var1.defineProperty("constructor", this, 7);
      this.prototypeProperty = var1;
      Scriptable var3 = ScriptableObject.getObjectPrototype(this);
      if (var3 != var1) {
         var1.setPrototype(var3);
      }

   }
}
