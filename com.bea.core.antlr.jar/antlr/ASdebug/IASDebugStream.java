package antlr.ASdebug;

import antlr.Token;

public interface IASDebugStream {
   String getEntireText();

   TokenOffsetInfo getOffsetInfo(Token var1);
}
