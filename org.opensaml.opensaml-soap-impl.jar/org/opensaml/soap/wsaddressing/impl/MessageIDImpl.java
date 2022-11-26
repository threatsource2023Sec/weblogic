package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.MessageID;

public class MessageIDImpl extends AttributedURIImpl implements MessageID {
   public MessageIDImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
