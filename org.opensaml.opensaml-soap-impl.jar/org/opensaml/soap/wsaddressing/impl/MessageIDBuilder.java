package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.MessageID;

public class MessageIDBuilder extends AbstractWSAddressingObjectBuilder {
   public MessageID buildObject() {
      return (MessageID)this.buildObject(MessageID.ELEMENT_NAME);
   }

   public MessageID buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new MessageIDImpl(namespaceURI, localName, namespacePrefix);
   }
}
