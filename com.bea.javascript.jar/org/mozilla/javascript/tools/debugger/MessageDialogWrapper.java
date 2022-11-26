package org.mozilla.javascript.tools.debugger;

import java.awt.Component;
import javax.swing.JOptionPane;

class MessageDialogWrapper {
   static void showMessageDialog(Component var0, String var1, String var2, int var3) {
      if (var1.length() > 60) {
         StringBuffer var4 = new StringBuffer();
         int var5 = var1.length();
         int var6 = 0;

         for(int var7 = 0; var7 < var5; ++var6) {
            char var8 = var1.charAt(var7);
            var4.append(var8);
            if (Character.isWhitespace(var8)) {
               int var10000 = var5 - var7;

               int var10;
               for(var10 = var7 + 1; var10 < var5 && !Character.isWhitespace(var1.charAt(var10)); ++var10) {
               }

               if (var10 < var5) {
                  int var11 = var10 - var7;
                  if (var6 + var11 > 60) {
                     var4.append('\n');
                     var6 = 0;
                  }
               }
            }

            ++var7;
         }

         var1 = var4.toString();
      }

      JOptionPane.showMessageDialog(var0, var1, var2, var3);
   }
}
