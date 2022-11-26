package weblogic.xml.babel.baseparser;

import java.io.IOException;

public class ParserConstraintException extends IOException {
   public ParserConstraintException(String s) {
      super(s);
   }

   public ParserConstraintException(String s, Throwable t) {
      super(s, t);
   }
}
