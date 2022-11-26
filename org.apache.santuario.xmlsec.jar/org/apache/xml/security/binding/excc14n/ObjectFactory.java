package org.apache.xml.security.binding.excc14n;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
   private static final QName _InclusiveNamespaces_QNAME = new QName("http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces");

   public InclusiveNamespaces createInclusiveNamespaces() {
      return new InclusiveNamespaces();
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2001/10/xml-exc-c14n#",
      name = "InclusiveNamespaces"
   )
   public JAXBElement createInclusiveNamespaces(InclusiveNamespaces value) {
      return new JAXBElement(_InclusiveNamespaces_QNAME, InclusiveNamespaces.class, (Class)null, value);
   }
}
