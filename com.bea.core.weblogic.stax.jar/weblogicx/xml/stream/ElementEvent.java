package weblogicx.xml.stream;

import org.xml.sax.Locator;

public abstract class ElementEvent extends XMLEvent {
   private String name;
   private String namespaceURI;
   private String qualifiedName;

   public ElementEvent(Object source, Locator locator, String name, String namespaceURI, String qualifiedName) {
      super(source, locator);
      this.name = name;
      this.namespaceURI = namespaceURI;
      this.qualifiedName = qualifiedName;
   }

   public String getName() {
      return this.name;
   }

   public String getNamespaceURI() {
      return this.namespaceURI;
   }

   public String getQualifiedName() {
      return this.qualifiedName;
   }

   public String toString() {
      return "[" + this.getClass().getName() + ": " + this.qualifiedName + " (" + this.namespaceURI + ")]";
   }
}
