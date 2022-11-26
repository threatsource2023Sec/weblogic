package weblogic.xml.babel.baseparser;

import java.io.Serializable;
import weblogic.xml.babel.scanner.Token;

public class ParseException extends Exception implements Serializable {
   private static final long serialVersionUID = -7879664454860538136L;
   int line;
   transient Token token;

   public ParseException() {
      this.line = 0;
      this.token = null;
   }

   public ParseException(String s) {
      super(s);
      this.line = 0;
      this.token = null;
   }

   public ParseException(String s, int line, Token token) {
      super(s);
      this.line = line;
      this.token = token;
   }

   public String toString() {
      return this.token != null ? "Error at Line:" + this.line + ", token:" + this.token + this.getMessage() : "Error at Line:" + this.line + " " + this.getMessage();
   }
}
