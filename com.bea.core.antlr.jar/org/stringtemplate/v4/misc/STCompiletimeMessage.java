package org.stringtemplate.v4.misc;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.stringtemplate.v4.ST;

public class STCompiletimeMessage extends STMessage {
   public Token templateToken;
   public Token token;
   public String srcName;

   public STCompiletimeMessage(ErrorType error, String srcName, Token templateToken, Token t) {
      this(error, srcName, templateToken, t, (Throwable)null);
   }

   public STCompiletimeMessage(ErrorType error, String srcName, Token templateToken, Token t, Throwable cause) {
      this(error, srcName, templateToken, t, cause, (Object)null);
   }

   public STCompiletimeMessage(ErrorType error, String srcName, Token templateToken, Token t, Throwable cause, Object arg) {
      this(error, srcName, templateToken, t, cause, arg, (Object)null);
   }

   public STCompiletimeMessage(ErrorType error, String srcName, Token templateToken, Token t, Throwable cause, Object arg, Object arg2) {
      super(error, (ST)null, cause, (Object)arg, arg2);
      this.templateToken = templateToken;
      this.token = t;
      this.srcName = srcName;
   }

   public String toString() {
      RecognitionException re = (RecognitionException)this.cause;
      int line = 0;
      int charPos = -1;
      if (this.token != null) {
         line = this.token.getLine();
         charPos = this.token.getCharPositionInLine();
         if (this.templateToken != null && !this.templateToken.getInputStream().equals(this.token.getInputStream())) {
            int templateDelimiterSize = 1;
            if (this.templateToken.getType() == 5 || this.templateToken.getType() == 6) {
               templateDelimiterSize = 2;
            }

            line += this.templateToken.getLine() - 1;
            charPos += this.templateToken.getCharPositionInLine() + templateDelimiterSize;
         }
      }

      String filepos = line + ":" + charPos;
      return this.srcName != null ? this.srcName + " " + filepos + ": " + String.format(this.error.message, this.arg, this.arg2) : filepos + ": " + String.format(this.error.message, this.arg, this.arg2);
   }
}
