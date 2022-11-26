package org.mozilla.javascript;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class FieldAndMethods extends NativeJavaMethod {
   private Field field;
   private Object javaObject;
   private String name;

   FieldAndMethods(Method[] var1, Field var2, String var3) {
      super(var1);
      this.field = var2;
      this.name = var3;
   }

   public Object clone() {
      FieldAndMethods var1 = new FieldAndMethods(super.methods, this.field, this.name);
      var1.javaObject = this.javaObject;
      return var1;
   }

   public Object getDefaultValue(Class var1) {
      if (var1 == ScriptRuntime.FunctionClass) {
         return this;
      } else {
         Object var2;
         Class var3;
         try {
            var2 = this.field.get(this.javaObject);
            var3 = this.field.getType();
         } catch (IllegalAccessException var4) {
            throw Context.reportRuntimeError1("msg.java.internal.private", this.getName());
         }

         var2 = NativeJavaObject.wrap(this, var2, var3);
         if (var2 instanceof Scriptable) {
            var2 = ((Scriptable)var2).getDefaultValue(var1);
         }

         return var2;
      }
   }

   Field getField() {
      return this.field;
   }

   String getName() {
      return this.field == null ? this.name : this.field.getName();
   }

   void setJavaObject(Object var1) {
      this.javaObject = var1;
   }
}
