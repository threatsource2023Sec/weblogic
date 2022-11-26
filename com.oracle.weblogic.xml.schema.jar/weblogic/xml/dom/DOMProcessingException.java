package weblogic.xml.dom;

import java.io.PrintWriter;
import weblogic.utils.NestedException;

public class DOMProcessingException extends NestedException {
   public DOMProcessingException() {
   }

   public DOMProcessingException(String msg) {
      super(msg);
   }

   public DOMProcessingException(Throwable th) {
      super(th);
   }

   public DOMProcessingException(String msg, Throwable th) {
      super(msg, th);
   }

   public void writeErrorCondition(PrintWriter pw) {
      pw.println("[Begin DOMProcessingException:");
      pw.println("End DOMProcessingException]");
   }
}
