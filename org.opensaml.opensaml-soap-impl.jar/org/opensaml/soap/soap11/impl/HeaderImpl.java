package org.opensaml.soap.soap11.impl;

import org.opensaml.soap.common.AbstractExtensibleSOAPObject;
import org.opensaml.soap.soap11.Header;

public class HeaderImpl extends AbstractExtensibleSOAPObject implements Header {
   protected HeaderImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
