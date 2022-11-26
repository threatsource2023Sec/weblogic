package weblogic.xml.process;

import weblogic.utils.NestedException;

public class DTDParsingException extends NestedException {
   public DTDParsingException() {
   }

   public DTDParsingException(String msg) {
      super(msg);
   }

   public DTDParsingException(Throwable th) {
      super(th);
   }

   public DTDParsingException(String msg, Throwable th) {
      super(msg, th);
   }
}
