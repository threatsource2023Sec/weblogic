package org.opensaml.soap.soap11.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.common.SOAPObjectBuilder;
import org.opensaml.soap.soap11.FaultString;

public class FaultStringBuilder extends AbstractXMLObjectBuilder implements SOAPObjectBuilder {
   public FaultString buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new FaultStringImpl(namespaceURI, localName, namespacePrefix);
   }

   public FaultString buildObject() {
      return this.buildObject((String)null, "faultstring", (String)null);
   }
}
