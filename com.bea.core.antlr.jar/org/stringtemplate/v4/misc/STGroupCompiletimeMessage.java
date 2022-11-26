package org.stringtemplate.v4.misc;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.stringtemplate.v4.ST;

public class STGroupCompiletimeMessage extends STMessage {
   public Token token;
   public String srcName;

   public STGroupCompiletimeMessage(ErrorType error, String srcName, Token t, Throwable cause) {
      this(error, srcName, t, cause, (Object)null);
   }

   public STGroupCompiletimeMessage(ErrorType error, String srcName, Token t, Throwable cause, Object arg) {
      this(error, srcName, t, cause, arg, (Object)null);
   }

   public STGroupCompiletimeMessage(ErrorType error, String srcName, Token t, Throwable cause, Object arg, Object arg2) {
      super(error, (ST)null, cause, (Object)arg, arg2);
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
      } else if (re != null) {
         line = re.line;
         charPos = re.charPositionInLine;
      }

      String filepos = line + ":" + charPos;
      return this.srcName != null ? this.srcName + " " + filepos + ": " + String.format(this.error.message, this.arg, this.arg2) : filepos + ": " + String.format(this.error.message, this.arg, this.arg2);
   }
}
