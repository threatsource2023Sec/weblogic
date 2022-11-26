package weblogic.xml.process;

import org.xml.sax.SAXException;

public class SAXProcessorException extends SAXException {
   public SAXProcessorException(String msg) {
      super(msg);
   }

   public SAXProcessorException(Exception e) {
      super(e);
   }

   public SAXProcessorException(String msg, Exception e) {
      super(msg, e);
   }
}
