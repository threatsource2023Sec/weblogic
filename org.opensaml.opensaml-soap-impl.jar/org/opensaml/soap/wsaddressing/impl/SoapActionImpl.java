package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.schema.impl.XSURIImpl;
import org.opensaml.soap.wsaddressing.SoapAction;

public class SoapActionImpl extends XSURIImpl implements SoapAction {
   public SoapActionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
