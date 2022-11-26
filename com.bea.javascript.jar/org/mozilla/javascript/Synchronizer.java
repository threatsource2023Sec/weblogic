package org.mozilla.javascript;

public class Synchronizer extends Delegator {
   public Synchronizer(Scriptable var1) {
      super(var1);
   }

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      synchronized(var3){}

      Object var5;
      try {
         var5 = ((Function)super.obj).call(var1, var2, var3, var4);
      } catch (Throwable var9) {
         throw var9;
      }

      return var5;
   }
}
