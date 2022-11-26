package weblogicx.xml.stream;

import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

public class ErrorEvent extends ExceptionEvent {
   public ErrorEvent(Object source, Locator locator, SAXParseException saxpe) {
      super(source, locator, saxpe);
   }
}
