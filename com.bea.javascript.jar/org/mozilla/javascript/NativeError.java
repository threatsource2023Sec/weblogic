package org.mozilla.javascript;

public class NativeError extends IdScriptable {
   private static final int Id_message = 1;
   private static final int Id_name = 2;
   private static final int MAX_INSTANCE_ID = 2;
   private static final int Id_constructor = 3;
   private static final int Id_toString = 4;
   private static final int MAX_PROTOTYPE_ID = 4;
   private Object messageValue;
   private Object nameValue;
   private boolean prototypeFlag;

   public NativeError() {
      this.messageValue = Scriptable.NOT_FOUND;
      this.nameValue = Scriptable.NOT_FOUND;
   }

   protected void deleteIdValue(int var1) {
      if (var1 == 1) {
         this.messageValue = Scriptable.NOT_FOUND;
      } else if (var1 == 2) {
         this.nameValue = Scriptable.NOT_FOUND;
      } else {
         super.deleteIdValue(var1);
      }
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      if (this.prototypeFlag) {
         if (var1 == 3) {
            return jsConstructor(var3, var6, var2, var5 == null);
         }

         if (var1 == 4) {
            return this.realThis(var5, var2).toString();
         }
      }

      return super.execMethod(var1, var2, var3, var4, var5, var6);
   }

   public String getClassName() {
      return "Error";
   }

   protected int getIdDefaultAttributes(int var1) {
      return var1 != 1 && var1 != 2 ? super.getIdDefaultAttributes(var1) : 0;
   }

   protected String getIdName(int var1) {
      if (var1 == 1) {
         return "message";
      } else if (var1 == 2) {
         return "name";
      } else {
         if (this.prototypeFlag) {
            if (var1 == 3) {
               return "constructor";
            }

            if (var1 == 4) {
               return "toString";
            }
         }

         return null;
      }
   }

   protected Object getIdValue(int var1) {
      if (var1 == 1) {
         return this.messageValue;
      } else {
         return var1 == 2 ? this.nameValue : super.getIdValue(var1);
      }
   }

   public String getMessage() {
      Object var1 = this.messageValue;
      return ScriptRuntime.toString(var1 != Scriptable.NOT_FOUND ? var1 : Undefined.instance);
   }

   public String getName() {
      Object var1 = this.nameValue;
      return ScriptRuntime.toString(var1 != Scriptable.NOT_FOUND ? var1 : Undefined.instance);
   }

   protected boolean hasIdValue(int var1) {
      if (var1 == 1) {
         return this.messageValue != Scriptable.NOT_FOUND;
      } else if (var1 == 2) {
         return this.nameValue != Scriptable.NOT_FOUND;
      } else {
         return super.hasIdValue(var1);
      }
   }

   public static void init(Context var0, Scriptable var1, boolean var2) {
      NativeError var3 = new NativeError();
      var3.prototypeFlag = true;
      var3.messageValue = "";
      var3.nameValue = "Error";
      var3.addAsPrototype(4, var0, var1, var2);
   }

   private static Object jsConstructor(Context var0, Object[] var1, Function var2, boolean var3) {
      NativeError var4 = new NativeError();
      if (var1.length >= 1) {
         var4.messageValue = ScriptRuntime.toString(var1[0]);
      }

      var4.setPrototype(ScriptableObject.getClassPrototype(var2, "Error"));
      return var4;
   }

   protected int mapNameToId(String var1) {
      byte var2 = 0;
      String var3 = null;
      int var4 = var1.length();
      if (var4 == 4) {
         var3 = "name";
         var2 = 2;
      } else if (var4 == 7) {
         var3 = "message";
         var2 = 1;
      }

      if (var3 != null && var3 != var1 && !var3.equals(var1)) {
         var2 = 0;
      }

      if (var2 == 0 && this.prototypeFlag) {
         var2 = 0;
         var3 = null;
         var4 = var1.length();
         if (var4 == 8) {
            var3 = "toString";
            var2 = 4;
         } else if (var4 == 11) {
            var3 = "constructor";
            var2 = 3;
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
      return 2;
   }

   public int methodArity(int var1) {
      if (this.prototypeFlag) {
         if (var1 == 3) {
            return 1;
         }

         if (var1 == 4) {
            return 0;
         }
      }

      return super.methodArity(var1);
   }

   private NativeError realThis(Scriptable var1, IdFunction var2) {
      while(!(var1 instanceof NativeError)) {
         var1 = this.nextInstanceCheck(var1, var2, true);
      }

      return (NativeError)var1;
   }

   protected void setIdValue(int var1, Object var2) {
      if (var1 == 1) {
         this.messageValue = var2;
      } else if (var1 == 2) {
         this.nameValue = var2;
      } else {
         super.setIdValue(var1, var2);
      }
   }

   public String toString() {
      return this.getName() + ": " + this.getMessage();
   }
}
