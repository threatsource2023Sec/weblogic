package org.stringtemplate.v4.misc;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.stringtemplate.v4.ST;

public class STLexerMessage extends STMessage {
   public String msg;
   public Token templateToken;
   public String srcName;

   public STLexerMessage(String srcName, String msg, Token templateToken, Throwable cause) {
      super(ErrorType.LEXER_ERROR, (ST)null, cause, (Object)null);
      this.msg = msg;
      this.templateToken = templateToken;
      this.srcName = srcName;
   }

   public String toString() {
      RecognitionException re = (RecognitionException)this.cause;
      int line = re.line;
      int charPos = re.charPositionInLine;
      if (this.templateToken != null) {
         int templateDelimiterSize = 1;
         if (this.templateToken.getType() == 5) {
            templateDelimiterSize = 2;
         }

         line += this.templateToken.getLine() - 1;
         charPos += this.templateToken.getCharPositionInLine() + templateDelimiterSize;
      }

      String filepos = line + ":" + charPos;
      return this.srcName != null ? this.srcName + " " + filepos + ": " + String.format(this.error.message, this.msg) : filepos + ": " + String.format(this.error.message, this.msg);
   }
}
