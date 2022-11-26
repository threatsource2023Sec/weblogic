package org.mozilla.javascript;

public class ListenerArray {
   public static Object[] add(Object[] var0, Object var1) {
      if (var0 == null) {
         var0 = new Object[1];
      } else {
         int var2 = var0.length;
         Object[] var3 = new Object[var2 + 1];
         System.arraycopy(var0, 0, var3, 1, var2);
         var0 = var3;
      }

      var0[0] = var1;
      return var0;
   }

   public static Object[] remove(Object[] var0, Object var1) {
      if (var0 != null) {
         int var2 = var0.length;

         for(int var3 = 0; var3 != var2; ++var3) {
            if (var0[var3] == var1) {
               if (var2 == 1) {
                  var0 = null;
               } else {
                  Object[] var4 = new Object[var2 - 1];
                  System.arraycopy(var0, 0, var4, 0, var3);
                  System.arraycopy(var0, var3 + 1, var4, var3, var2 - 1 - var3);
                  var0 = var4;
               }
               break;
            }
         }
      }

      return var0;
   }
}
