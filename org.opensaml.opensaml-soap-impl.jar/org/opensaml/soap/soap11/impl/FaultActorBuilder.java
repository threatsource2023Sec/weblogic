package org.opensaml.soap.soap11.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.common.SOAPObjectBuilder;
import org.opensaml.soap.soap11.FaultActor;

public class FaultActorBuilder extends AbstractXMLObjectBuilder implements SOAPObjectBuilder {
   public FaultActor buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new FaultActorImpl(namespaceURI, localName, namespacePrefix);
   }

   public FaultActor buildObject() {
      return this.buildObject((String)null, "faultactor", (String)null);
   }
}
