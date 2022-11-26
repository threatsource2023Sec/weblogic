package org.opensaml.soap.soap11.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.common.SOAPObjectBuilder;
import org.opensaml.soap.soap11.FaultCode;

public class FaultCodeBuilder extends AbstractXMLObjectBuilder implements SOAPObjectBuilder {
   public FaultCode buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new FaultCodeImpl(namespaceURI, localName, namespacePrefix);
   }

   public FaultCode buildObject() {
      return this.buildObject((String)null, "faultcode", (String)null);
   }
}
