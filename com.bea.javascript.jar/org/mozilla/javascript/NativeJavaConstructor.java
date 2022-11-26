package org.mozilla.javascript;

import java.lang.reflect.Constructor;

public class NativeJavaConstructor extends NativeFunction implements Function {
   Constructor constructor;

   public NativeJavaConstructor(Constructor var1) {
      this.constructor = var1;
      super.functionName = "<init>" + NativeJavaMethod.signature(var1);
   }

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      if (this.constructor == null) {
         throw new RuntimeException("No constructor defined for call");
      } else {
         return NativeJavaClass.constructSpecific(var1, var2, this, this.constructor, var4);
      }
   }

   Constructor getConstructor() {
      return this.constructor;
   }

   public String toString() {
      return "[JavaConstructor " + this.constructor.getName() + "]";
   }
}
