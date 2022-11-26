package weblogic.application.compiler.utils;

import weblogic.utils.NestedException;

public class ApplicationNameScannerException extends NestedException {
   public ApplicationNameScannerException(Throwable nested) {
      super(nested);
   }

   public ApplicationNameScannerException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
