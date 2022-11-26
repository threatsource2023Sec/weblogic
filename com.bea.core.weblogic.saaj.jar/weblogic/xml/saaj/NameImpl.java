package weblogic.xml.saaj;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;

final class NameImpl extends QName implements Name {
   private static final long serialVersionUID = 2513950391398405202L;

   NameImpl(String localName) {
      super(localName);
   }

   NameImpl(QName name) {
      this(name.getLocalPart(), name.getPrefix(), name.getNamespaceURI());
   }

   NameImpl(String local, String prefix, String uri) {
      super(uri, local, prefix == null ? "" : prefix);
   }

   public String getLocalName() {
      return this.getLocalPart();
   }

   public String getQualifiedName() {
      String prefix = this.getPrefix();
      return prefix != null && prefix.length() != 0 ? prefix + ":" + this.getLocalPart() : this.getLocalPart();
   }

   public String getURI() {
      return this.getNamespaceURI();
   }
}
