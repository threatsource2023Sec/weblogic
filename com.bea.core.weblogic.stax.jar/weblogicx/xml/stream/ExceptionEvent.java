package weblogicx.xml.stream;

import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

public class ExceptionEvent extends XMLEvent {
   private SAXParseException saxpe;

   public ExceptionEvent(Object source, Locator locator, SAXParseException saxpe) {
      super(source, locator);
      this.saxpe = saxpe;
   }

   public SAXParseException getException() {
      return this.saxpe;
   }
}
