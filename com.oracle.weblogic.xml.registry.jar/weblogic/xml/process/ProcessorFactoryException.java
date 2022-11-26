package weblogic.xml.process;

import weblogic.utils.NestedException;

public class ProcessorFactoryException extends NestedException {
   public ProcessorFactoryException() {
   }

   public ProcessorFactoryException(String msg) {
      super(msg);
   }

   public ProcessorFactoryException(Throwable th) {
      super(th);
   }

   public ProcessorFactoryException(String msg, Throwable th) {
      super(msg, th);
   }
}
