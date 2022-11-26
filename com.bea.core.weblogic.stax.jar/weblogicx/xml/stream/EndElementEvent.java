package weblogicx.xml.stream;

import org.xml.sax.Locator;

public class EndElementEvent extends ElementEvent {
   public EndElementEvent(Object source, Locator locator, String name, String namespaceURI, String qualifiedName) {
      super(source, locator, name, namespaceURI, qualifiedName);
   }
}
