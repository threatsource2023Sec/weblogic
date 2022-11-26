package org.mozilla.javascript;

import java.lang.reflect.InvocationTargetException;

public class JavaScriptException extends Exception {
   Object value;
   // $FF: synthetic field
   static Class class$java$lang$Throwable;

   public JavaScriptException(Object var1) {
      super(ScriptRuntime.toString(var1));
      this.value = var1;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public String getMessage() {
      return ScriptRuntime.toString(this.value);
   }

   public Object getValue() {
      return this.value != null && this.value instanceof Wrapper ? ((Wrapper)this.value).unwrap() : this.value;
   }

   static JavaScriptException wrapException(Scriptable var0, Throwable var1) {
      if (var1 instanceof InvocationTargetException) {
         var1 = ((InvocationTargetException)var1).getTargetException();
      }

      if (var1 instanceof JavaScriptException) {
         return (JavaScriptException)var1;
      } else {
         Object var2 = NativeJavaObject.wrap(var0, var1, class$java$lang$Throwable != null ? class$java$lang$Throwable : (class$java$lang$Throwable = class$("java.lang.Throwable")));
         return new JavaScriptException(var2);
      }
   }
}
