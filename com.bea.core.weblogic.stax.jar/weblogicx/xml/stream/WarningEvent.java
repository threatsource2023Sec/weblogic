package weblogicx.xml.stream;

import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

public class WarningEvent extends ExceptionEvent {
   public WarningEvent(Object source, Locator locator, SAXParseException saxpe) {
      super(source, locator, saxpe);
   }
}
