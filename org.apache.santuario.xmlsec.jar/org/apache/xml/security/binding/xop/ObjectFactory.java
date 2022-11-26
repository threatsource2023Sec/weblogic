package org.apache.xml.security.binding.xop;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
   private static final QName _Include_QNAME = new QName("http://www.w3.org/2004/08/xop/include", "Include");

   public Include createInclude() {
      return new Include();
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2004/08/xop/include",
      name = "Include"
   )
   public JAXBElement createInclude(Include value) {
      return new JAXBElement(_Include_QNAME, Include.class, (Class)null, value);
   }
}
