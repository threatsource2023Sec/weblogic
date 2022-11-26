package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.Action;

public class ActionBuilder extends AbstractWSAddressingObjectBuilder {
   public Action buildObject() {
      return (Action)this.buildObject(Action.ELEMENT_NAME);
   }

   public Action buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ActionImpl(namespaceURI, localName, namespacePrefix);
   }
}
