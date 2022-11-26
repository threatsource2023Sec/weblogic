package org.opensaml.soap.soap11.impl;

import org.opensaml.core.xml.schema.impl.XSURIImpl;
import org.opensaml.soap.soap11.FaultActor;

public class FaultActorImpl extends XSURIImpl implements FaultActor {
   protected FaultActorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
