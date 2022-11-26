package antlr.ASdebug;

import antlr.Token;
import antlr.TokenStream;

public final class ASDebugStream {
   public static String getEntireText(TokenStream var0) {
      if (var0 instanceof IASDebugStream) {
         IASDebugStream var1 = (IASDebugStream)var0;
         return var1.getEntireText();
      } else {
         return null;
      }
   }

   public static TokenOffsetInfo getOffsetInfo(TokenStream var0, Token var1) {
      if (var0 instanceof IASDebugStream) {
         IASDebugStream var2 = (IASDebugStream)var0;
         return var2.getOffsetInfo(var1);
      } else {
         return null;
      }
   }
}
