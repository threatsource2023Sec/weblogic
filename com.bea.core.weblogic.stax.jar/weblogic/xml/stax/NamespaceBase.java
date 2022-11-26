package weblogic.xml.stax;

import javax.xml.stream.events.Namespace;

public class NamespaceBase extends AttributeBase implements Namespace {
   boolean declaresDefaultNamespace = false;

   public NamespaceBase(String prefix, String namespaceURI) {
      super("xmlns", prefix, namespaceURI);
      this.declaresDefaultNamespace = false;
   }

   public NamespaceBase(String namespaceURI) {
      super("", "xmlns", namespaceURI);
      this.declaresDefaultNamespace = true;
   }

   public boolean isNamespace() {
      return true;
   }

   public int getEventType() {
      return 13;
   }

   public String getPrefix() {
      return this.declaresDefaultNamespace ? "" : super.getLocalName();
   }

   public String getNamespaceURI() {
      return super.getValue();
   }

   public boolean isDefaultNamespaceDeclaration() {
      return this.declaresDefaultNamespace;
   }

   public String toString() {
      return this.declaresDefaultNamespace ? "xmlns='" + this.getNamespaceURI() + "'" : "xmlns:" + this.getPrefix() + "='" + this.getNamespaceURI() + "'";
   }
}
