package org.apache.velocity.runtime.directive;

import org.apache.velocity.runtime.parser.ParseException;

public class MacroParseException extends ParseException {
   public MacroParseException(String msg) {
      super(msg);
   }
}
