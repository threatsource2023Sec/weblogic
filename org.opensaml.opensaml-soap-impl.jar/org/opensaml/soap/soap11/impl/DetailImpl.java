package org.opensaml.soap.soap11.impl;

import org.opensaml.soap.common.AbstractExtensibleSOAPObject;
import org.opensaml.soap.soap11.Detail;

public class DetailImpl extends AbstractExtensibleSOAPObject implements Detail {
   protected DetailImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
