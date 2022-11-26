package weblogic.xml.stax.events;

import javax.xml.namespace.QName;

public class NamedEvent extends BaseEvent {
   private QName name;

   public NamedEvent() {
   }

   public NamedEvent(QName name) {
      this.name = name;
   }

   public NamedEvent(String localName) {
      this.name = new QName(localName);
   }

   public NamedEvent(String prefix, String namespaceURI, String localName) {
      this.name = new QName(namespaceURI, localName, prefix);
   }

   public QName getName() {
      return this.name;
   }

   public void setName(QName n) {
      this.name = n;
   }

   public String nameAsString() {
      if ("".equals(this.name.getNamespaceURI())) {
         return this.name.getLocalPart();
      } else {
         return this.name.getPrefix() != null && !this.name.getPrefix().equals("") ? "['" + this.name.getNamespaceURI() + "']:" + this.name.getPrefix() + ":" + this.name.getLocalPart() : "['" + this.name.getNamespaceURI() + "']:" + this.name.getLocalPart();
      }
   }
}
