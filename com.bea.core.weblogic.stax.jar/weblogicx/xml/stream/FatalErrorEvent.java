package weblogicx.xml.stream;

import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

public class FatalErrorEvent extends ExceptionEvent {
   public FatalErrorEvent(Object source, Locator locator, SAXParseException saxpe) {
      super(source, locator, saxpe);
   }
}
