package weblogicx.xml.stream;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;

public class StartElementEvent extends ElementEvent {
   private Attributes attributes;

   public StartElementEvent(Object source, Locator locator, String name, String namespaceURI, String qualifiedName, Attributes attributes) {
      super(source, locator, name, namespaceURI, qualifiedName);
      this.attributes = attributes;
   }

   public Attributes getAttributes() {
      return this.attributes;
   }

   public String toString() {
      String s = super.toString();

      for(int i = 0; i < this.attributes.getLength(); ++i) {
         s = s + "\n\t[uri->" + this.attributes.getURI(i) + " ln->" + this.attributes.getLocalName(i) + " qn->" + this.attributes.getQName(i) + " val->" + this.attributes.getValue(i) + "]";
      }

      return s;
   }
}
